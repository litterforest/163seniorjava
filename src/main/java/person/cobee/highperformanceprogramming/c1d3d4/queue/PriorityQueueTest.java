package person.cobee.highperformanceprogramming.c1d3d4.queue;

import java.util.PriorityQueue;

/**
 * 优先级队列，无界队列，非阻塞操作，队列中的元素具有比较功能
 *
 * @author cobee
 * @since 2019-11-28
 */
public class PriorityQueueTest {

    public static void main(String[] args) {
        PriorityQueue<String> q = new PriorityQueue<>();
        q.add("c");
        q.add("a");
        q.add("b");
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
    }

}
