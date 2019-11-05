package person.cobee.highperformanceprogramming.c1d1d6;

/**
 * 线程封闭技术
 *
 * @author cobee
 * @since 2019-11-05
 */
public class ThreadLocalTest {

    static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        tl.set("主线程设值：123");
        System.out.println("主线程设值：123");
        Thread thread1 = new Thread(() -> {
            String str = tl.get();
            System.out.println("线程1获取到的值：" + str);
            tl.set("线程1设值：456");
            System.out.println("线程1设值：456");
        });
        thread1.start();
        Thread.sleep(5000L);
        String str = tl.get();
        System.out.println("主线程获取到的值：" + str);
    }

}
