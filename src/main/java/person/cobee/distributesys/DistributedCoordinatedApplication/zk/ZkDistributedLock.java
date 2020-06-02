package person.cobee.distributesys.DistributedCoordinatedApplication.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用zk实现简单的分布式锁，缺点是会出现惊群效应
 *
 * @author cobee
 * @since 2020-05-29
 */
public class ZkDistributedLock implements Lock {

    private String lockPath;
    private ZkClient zkClient;
//    private IZkDataListener iZkDataListener;
    private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>(); // 可重入的分布式锁

    public ZkDistributedLock(String lockPath, String zkServer){
        super();
        this.lockPath = lockPath;
        this.zkClient = new ZkClient(zkServer);
//        iZkDataListener = new IZkDataListener() {
//            @Override
//            public void handleDataChange(String dataPath, Object data) throws Exception {
//
//            }
//
//            @Override
//            public void handleDataDeleted(String dataPath) throws Exception {
//                // 争抢创建临时节点，唤醒线程争抢
//                System.out.println("锁节点被删除，唤醒线程争抢");
//                LockSupport.unpark(Thread.currentThread());
//            }
//        };
//        zkClient.subscribeDataChanges(lockPath, iZkDataListener);
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
        zkClient.subscribeDataChanges(lockPath, listener);
        // 阻塞自己
        if (this.zkClient.exists(lockPath)) {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 取消注册
        zkClient.unsubscribeDataChanges(lockPath, listener);
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
        // 争抢锁
        try {
            zkClient.createEphemeral(lockPath);
            reentrantCount.set(1);
        } catch (ZkNodeExistsException e) {
            return false;
        }
        return true;
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
        // 如果是自己删除了就注销监听器
        zkClient.delete(lockPath);
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
        int currency = 50;

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
                    ZkDistributedLock lock = new ZkDistributedLock("/distLock11", "192.168.174.130:2181");

                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " 获得锁！");
                    } finally {
                        lock.unlock();
                        lock.releaseConn();
                    }
                }
            }).start();

        }
    }

}
