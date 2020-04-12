package person.cobee.highperformanceprogramming.c1d3d1lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * juc包Lock锁测试，lock()和unlock()要成对出现。最常用的实现类是ReentranLock
 *
 * @author cobee
 * @since 2019-11-20
 */
public class LockTest {

    public static void main(String[] args) throws InterruptedException {
        // 非公平锁，可重入锁
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        lock.unlock(); // 锁没有完全释放，其它线程也不能获取到锁
//        lock.unlock();
//        lock.unlock(); // 单独调用unlock方法，会抛出监视器状态异常
        System.out.println("主线程获取锁");
        Thread th = new Thread(() -> {
            System.out.println(Thread.currentThread() + ":开始获取锁");
//            lock.lock(); // 阻塞获取锁
//            lock.tryLock();  // 获取锁，没有获取到就返回
//            try {
//                lock.tryLock(3L, TimeUnit.SECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + ":成功获取锁");
        });
        th.start();
        Thread.sleep(5000L);
//        System.out.println("执行线程中断");
        th.interrupt();
    }

}
