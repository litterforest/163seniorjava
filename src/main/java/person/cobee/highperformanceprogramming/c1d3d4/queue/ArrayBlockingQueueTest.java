package person.cobee.highperformanceprogramming.c1d3d4.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 有界队列，先进先出，阻塞方法和非阻塞方法都有。
 *
 * @author cobee
 * @since 2019-11-28
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        // 消费者线程
        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(1000L);
                    String str = q.take();
                    System.out.println("take" + str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(3000L);
        // 生产者线程
        for(int i = 0; i < 6; i++){
            new Thread(() -> {
                String str = Thread.currentThread().getName();
                try {
                    q.put(str);
                    System.out.println("put" + str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
