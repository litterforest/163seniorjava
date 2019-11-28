package person.cobee.highperformanceprogramming.c1d3d4;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先级队列，具有排序功能，无界队列，具有put()、take()的阻塞方法
 *
 * @author cobee
 * @since 2019-11-28
 */
public class PriorityBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
        q.put("c");
        q.put("b");
        q.put("a");

        System.out.println(q.take());
        System.out.println(q.take());
        System.out.println(q.take());
    }

}
