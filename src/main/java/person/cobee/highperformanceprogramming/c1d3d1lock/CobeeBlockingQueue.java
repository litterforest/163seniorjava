package person.cobee.highperformanceprogramming.c1d3d1lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自己实现一个阻塞队列，线程安全的队列
 *
 * @author cobee
 * @since 2019-11-20
 */
public class CobeeBlockingQueue {

    private Lock lock = new ReentrantLock();
    private Condition putCondition = lock.newCondition(); // 入队操作的等待队列
    private Condition takeCondition = lock.newCondition(); // 出队操作的等待队列

    private int length; // 队列的长度
    private List list = new ArrayList();

    public CobeeBlockingQueue(int length) {
        this.length = length;
    }

    /**
     * 入队
     * @param obj
     */
    public void put(Object obj){
        lock.lock();
        try {
            while(true){
                if(list.size() < length){ // 队列有空位
                    list.add(obj);
                    System.out.println("put:" + obj);
                    takeCondition.signal();
                    break;
                }else{
                    putCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 出队
     * @return
     */
    public Object take(){
        lock.lock();
        try {
            while(true){
                if(list.size() > 0){ // 队列里有元素
                    Object obj = list.remove(0);
                    System.out.println("take:" + obj);
                    putCondition.signal();
                    return obj;
                }else{
                    takeCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        CobeeBlockingQueue cobeeBlockingQueue = new CobeeBlockingQueue(5);
        Thread th = new Thread(() -> {
            for(int i = 0; i < 20; i++){
                cobeeBlockingQueue.put("元素:" + i);
            }
        });
        th.start();
        Thread.sleep(3000L);
        for(int i = 0; i < 10; i++){
            cobeeBlockingQueue.take();
            Thread.sleep(1000L);
        }
    }

}
