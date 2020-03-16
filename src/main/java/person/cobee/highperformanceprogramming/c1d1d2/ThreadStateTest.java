package person.cobee.highperformanceprogramming.c1d1d2;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-03
 */
public class ThreadStateTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("##############thread1 NEW -> RUNNABLE -> TERMINATED");
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getState().toString());
        });
        System.out.println(thread1.getState().toString());
        thread1.start();
        Thread.sleep(3000L);
        System.out.println(thread1.getState().toString());

        System.out.println();
        System.out.println("##############thread2 NEW -> RUNNABLE -> TIMED_WAITE -> RUNNABLE -> TERMINATED");
        Thread thread2 = new Thread(() -> {
            System.out.println("执行thread2代码并且睡眠2秒");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("睡眠完毕：" + Thread.currentThread().getState().toString());
            System.out.println("thread2线程执行完毕");
        });
        System.out.println("没调start()方法：" + thread2.getState().toString());
        thread2.start();
        System.out.println("调用start()方法：" + thread2.getState().toString());
        Thread.sleep(1000L);
        System.out.println("thread2状态：" + thread2.getState().toString());
        Thread.sleep(2000L);
        System.out.println("thread2状态：" + thread2.getState().toString());

        System.out.println();
        System.out.println("##############thread3 NEW -> RUNNABLE -> BLOCKED -> RUNNABLE -> TERMINATED");
        Thread thread3 = new Thread(() -> {
            synchronized (ThreadStateTest.class) {
                System.out.println("在thread3线程thread3状态：" + Thread.currentThread().getState().toString());
            }
        });
        synchronized (ThreadStateTest.class) {
            System.out.println("没调start()方法：" + thread3.getState().toString());
            thread3.start();
            System.out.println("调用start()方法：" + thread3.getState().toString());
            Thread.sleep(200L);
            System.out.println("在主线程thread3状态：" + thread3.getState().toString());
        }
        Thread.sleep(1000L);
        System.out.println("在主线程thread3状态：" + thread3.getState().toString());
    }

}
