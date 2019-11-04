package person.cobee.highperformanceprogramming.c1d1d5;

import java.util.concurrent.locks.LockSupport;

/**
 * {@link LockSupport} park 和 unpark不会释放锁
 *
 * @author cobee
 * @since 2019-11-04
 */
public class ParkUnParkDeadLockTest {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("线程代码执行");
            synchronized (lock){
                System.out.println("线程等待");
                LockSupport.park();
            }
            System.out.println("线程执行完毕");
        });
        thread.start();
        Thread.sleep(1000L);
        synchronized (lock){
            LockSupport.unpark(thread);
        }
        System.out.println("主线程执行完毕");
    }

}
