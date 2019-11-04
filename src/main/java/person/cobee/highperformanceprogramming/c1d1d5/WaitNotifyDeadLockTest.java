package person.cobee.highperformanceprogramming.c1d1d5;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-04
 */
public class WaitNotifyDeadLockTest {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("线程执行5秒");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock){
                System.out.println("线程进入等待状态");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("线程执行完毕");
        });
        thread.start();
        Thread.sleep(1000L);
        synchronized (lock){
            System.out.println("主线程执行口唤醒操作");
            lock.notify();
        }
        System.out.println("主线程执行完毕");
    }

}
