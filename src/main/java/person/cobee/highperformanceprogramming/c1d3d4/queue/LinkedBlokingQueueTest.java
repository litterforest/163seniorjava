package person.cobee.highperformanceprogramming.c1d3d4.queue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 无界队列，先进先出，有阻塞和非阻塞方法。可用于消费者和生产者模型
 *
 * @author cobee
 * @since 2019-11-28
 */
public class LinkedBlokingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();

        // 消费者线程
        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String str = q.poll(); // 非阻塞的方法
                System.out.println("消费" + str);
            }
        }).start();

        Thread.sleep(3000L);

        // 生产者线程
        for(int i = 0; i < 6; i++){
            new Thread(() -> {
                String str = Thread.currentThread().getName();
                q.offer(str);
                System.out.println("生产" + str);
            }).start();
        }

    }

}
