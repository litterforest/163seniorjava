package person.cobee.highperformanceprogramming.c1d2d3sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 递归调用是可重入锁的一个应用场景
 *
 * @author cobee
 * @since 2019-11-21
 */
public class ReentrantLockTest {

    private Lock lock = new ReentrantLock(); // 可重入锁，同一个线程可以多次重入

    public void recursive(){
        lock.lock();
        System.out.println("here i am");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recursive();
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        reentrantLockTest.recursive();
    }

}
