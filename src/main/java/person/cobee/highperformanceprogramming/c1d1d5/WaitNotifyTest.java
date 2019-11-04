package person.cobee.highperformanceprogramming.c1d1d5;

/**
 * 线程之间通信协作，要按照wait -> notify的顺序调用
 *
 * @author cobee
 * @since 2019-11-04
 */
public class WaitNotifyTest {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("执行线程代码");
            synchronized (lock) {
                try {
                    System.out.println("线程进入等待状态");
                    //Thread.currentThread().wait();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("线程代码执行完毕");
        });
        thread.start();
        Thread.sleep(1000L);
        synchronized (lock){
            lock.notify();
        }
        System.out.println("主线程执行完毕");
    }

}
