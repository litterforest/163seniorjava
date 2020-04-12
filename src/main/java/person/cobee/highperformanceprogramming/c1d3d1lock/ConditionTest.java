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
    // 阻塞队列
    private static Lock lock = new ReentrantLock();
    // 等待集合
    private static Condition await = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread() + ":进入等待状态");
            try {
                await.await(); // 进入等待状态，并且释放锁。被唤醒后会重新抢锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                lock.unlock();
            }
            System.out.println(Thread.currentThread() + ":等待结束");
        });
        th.start();
        Thread.sleep(300L);
        try {
            lock.lock();
            System.out.println("唤醒等待线程");
            await.signal();
        } finally {
            lock.unlock();
        }
    }

}
