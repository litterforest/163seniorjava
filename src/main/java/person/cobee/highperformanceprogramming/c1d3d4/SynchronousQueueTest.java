package person.cobee.highperformanceprogramming.c1d3d4;

import java.util.concurrent.SynchronousQueue;

/**
 * 同步队列，是阻塞的，里面不放置元素，手把手的交互数据
 *
 * @author cobee
 * @since 2019-11-28
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> q = new SynchronousQueue<>();
        new Thread(() -> {
            System.out.println("数据入队。。。");
            try {
                q.put("a");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("数据入队完毕。。。");
        }).start();
        System.out.println(q.take());
    }

}
