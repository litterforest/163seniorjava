package person.cobee.highperformanceprogramming.c1d1d5;

import java.util.concurrent.locks.LockSupport;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-04
 */
public class ParkUnParkTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("线程代码执行，消耗5秒");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程等待");
            LockSupport.park();
//            LockSupport.park();
//            LockSupport.park();
            System.out.println("线程执行完毕");
        });
        thread.start();
        Thread.sleep(1000L);
        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
//        LockSupport.unpark(thread);
        System.out.println("主线程执行完毕");
    }

}
