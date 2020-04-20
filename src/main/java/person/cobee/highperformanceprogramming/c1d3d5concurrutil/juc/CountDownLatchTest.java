package person.cobee.highperformanceprogramming.c1d3d5concurrutil.juc;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 线程倒数器
 * 1，正向倒数，主线程等待某些线程处理完后再往下执行。
 * 2，反向倒数，多个子线程在等待，主线程倒数后同时进行。
 *
 * @author cobee
 * @since 2019-12-02
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
//        testReverseCountDown();
        testForwardCountDown();
    }

    private static void testForwardCountDown() throws InterruptedException {
        MyCountDownLatch countDownLatch = new MyCountDownLatch(5);
        for(int i = 0; i < 5; i++){
            new Thread(() -> {
                Random random = new Random();
                int i1 = random.nextInt(1000);
                try {
                    Thread.sleep(i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            }).start();
        }
        countDownLatch.await();
        System.out.println("finish");
    }

    private static void testReverseCountDown() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i = 0; i < 5; i++){
            new Thread(() -> {
                Random random = new Random();
                int i1 = random.nextInt(1000);
                try {
                    Thread.sleep(i1);
                    System.out.println(Thread.currentThread().getName() + "准备完毕");
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + " is running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(3000L);
        countDownLatch.countDown();
        System.out.println("起跑");
    }

}
