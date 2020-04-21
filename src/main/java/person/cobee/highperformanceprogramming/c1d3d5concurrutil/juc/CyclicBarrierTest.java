package person.cobee.highperformanceprogramming.c1d3d5concurrutil.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.LockSupport;

/**
 * 循环屏障
 *
 * @author cobee
 * @since 2019-12-02
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        MyCyclicBarrier barrier = new MyCyclicBarrier(4);
        for(int i = 0; i < 100; i++){
            new Thread(() -> {
                barrier.await();
                System.out.println("开始上摩天轮");
//                try {
//                    barrier.await();
//                    System.out.println("开始上摩天轮");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
            }).start();
            Thread.sleep(1000L);
        }
    }

}
