package person.cobee.highperformanceprogramming.c1d3d4.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * 同步队列，是阻塞的，里面不放置元素，手把手的交换数据，并且是在不同元素之间的操作
 * 可用于做线程同步
 *
 * @author cobee
 * @since 2019-11-28
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> q = new SynchronousQueue<>();
        q.put("a");
//        q.put("b");
//        q.put("c");
        q.take();
//        new Thread(() -> {
//            System.out.println("数据入队。。。");
//            try {
//                Thread.sleep(3000L);
//                q.put("a");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("数据入队完毕。。。");
//        }).start();
//        System.out.println("主线程开始获取数据");
//        System.out.println(q.take());
    }

}
