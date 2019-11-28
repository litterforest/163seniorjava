package person.cobee.highperformanceprogramming.c1d3d4.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 并发无界队列，方法操作不加锁，没有阻塞。
 * 批量操作不保证原子性，addAll, removeAll, retainAll, containsAll, equals, and toArray
 * size()方法，每次调用都会遍历整个链表，建议不要频繁调用。
 *
 * @author cobee
 * @since 2019-11-28
 */
public class ConcurrentLinkedQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        // 消费者，1秒消费一个
        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(1000L);
                    String str = q.poll();
                    System.out.println("消费" + str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(3000L);
        // 生产者
        for(int i = 0; i < 6; i++){
            new Thread(() -> {
                String str = Thread.currentThread().getName();
                q.offer(str);
                System.out.println("生产" + str);
            }).start();
        }
    }

}
