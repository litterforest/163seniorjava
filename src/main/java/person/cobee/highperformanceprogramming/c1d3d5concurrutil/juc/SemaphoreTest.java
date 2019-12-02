package person.cobee.highperformanceprogramming.c1d3d5concurrutil.juc;

import java.util.concurrent.Semaphore;

/**
 * 信号量，主要用于限流
 *
 * @author cobee
 * @since 2019-12-02
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                try {
                    semaphore.acquire(); // 获得锁
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(); // 释放锁
                }
            }).start();
        }
    }

}
