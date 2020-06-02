package person.cobee.distributesys.DistributedCoordinatedApplication.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * zk分布式锁升级版，不会产生惊群效应
 *
 * @author cobee
 * @since 2020-05-29
 */
public class ZkDistributedLockImproved implements Lock {

    private String lockPath;
    private ZkClient zkClient;
    private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>(); // 可重入的分布式锁
    private ThreadLocal<String> currentPath = new ThreadLocal<>(); // 保存当前生成的节点
    private ThreadLocal<String> beforePath = new ThreadLocal<>(); // 上一个节点

    public ZkDistributedLockImproved(String lockPath, String zkServer){
        super();
        this.lockPath = lockPath;
        this.zkClient = new ZkClient(zkServer);
        zkClient.setZkSerializer(new SerializableSerializer());
        if(!zkClient.exists(lockPath)){
            try {
                zkClient.createPersistent(lockPath, true);
            } catch (RuntimeException e) {
            }
        }
    }

    @Override
    public void lock() {
        if(!tryLock()){
            waitForLock();
            lock();
        }
    }

    private void waitForLock() {
        CountDownLatch cdl = new CountDownLatch(1);
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("----收到节点被删除了-------------");
                cdl.countDown();
            }

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }
        };
        zkClient.subscribeDataChanges(beforePath.get(), listener);
        // 阻塞自己
        if (this.zkClient.exists(beforePath.get())) {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 取消注册
        zkClient.unsubscribeDataChanges(beforePath.get(), listener);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    /**
     * 非阻塞方法
     * @return
     */
    @Override
    public boolean tryLock() {
        Integer count = reentrantCount.get();
        if(count != null){ // 出现重入现像
            if(count > 0){
                reentrantCount.set(++count);
                return true;
            }
        }
        // 生成代表当前的节点
        if(currentPath.get() == null){
            String sequentialNode = zkClient.createEphemeralSequential(lockPath + "/", Thread.currentThread().getName());
            currentPath.set(sequentialNode);
        }
        // 获取所有子节点
        List<String> childNodes = zkClient.getChildren(lockPath);
        // 对子节点进行排序
        Collections.sort(childNodes);
        if(currentPath.get().equals(lockPath + "/" + childNodes.get(0))){
            reentrantCount.set(1);
            return true;
        }
        // 获得到前一个节点
        int currentIdx = childNodes.indexOf(currentPath.get().substring(lockPath.length() + 1));
        beforePath.set(lockPath + "/" + childNodes.get(currentIdx - 1));
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        Integer count = reentrantCount.get();
        if(count > 1){
            reentrantCount.set(--count);
        }else{
            reentrantCount.set(null);
        }
        // 删除临时节点
        zkClient.delete(currentPath.get());
        System.out.println("锁释放了");
    }

    public void releaseConn(){
        if(zkClient != null){
            zkClient.close();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) {
        // 并发数
        int currency = 10;
        // 循环屏障
        CyclicBarrier cb = new CyclicBarrier(currency);
        // 多线程模拟高并发
        for (int i = 0; i < currency; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "---------我准备好---------------");
                    // 等待一起出发
                    try {
                        cb.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    ZkDistributedLockImproved lock = new ZkDistributedLockImproved("/distLock11", "192.168.174.130:2181");
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " 获得锁！");
                        // TODO 模拟事务处理
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                        lock.releaseConn();
                    }
                }
            }).start();
        }
    }

}
