package person.cobee.highperformanceprogramming.c1d3d1lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程等待与唤醒
 *
 * @author cobee
 * @since 2019-11-20
 */
public class ConditionTest {

    private static Lock lock = new ReentrantLock();
    // 等待队列
    private static Condition await = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        Thread th = new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread() + ":进入等待状态");
                await.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + ":等待结束");
            lock.unlock();
        });
        th.start();
        Thread.sleep(300L);
        lock.lock();
        System.out.println("唤醒等待线程");
        await.signal();
        lock.unlock();
    }

}
