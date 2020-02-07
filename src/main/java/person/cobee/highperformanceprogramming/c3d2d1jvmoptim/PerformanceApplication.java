package person.cobee.highperformanceprogramming.c3d2d1jvmoptim;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 启动程序，模拟用户请求
// 每100毫秒钟创建150线程，每个线程创建一个1024kb的对象，最多1秒内同时存在1500线程，查看GC的情况
public class PerformanceApplication {
    public static void main(String[] args) {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            for (int i = 0; i < 150; i++) {
                new Thread(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":create byte array...");
                        //  不干活，专门512kb的小对象
                        byte[] temp = new byte[1024 * 1024];
                        Thread.sleep(new Random().nextInt(1000)); // 随机睡眠1秒以内
//                        Thread.sleep(1010L); // 随机睡眠1秒以内
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }
}

// 打包 mvn clean package
// 服务器上运行 performance-1.0.0.jar
// 对象存活在1秒左右的场景，远远超过平时接口的响应时间要求，场景应该为吞吐量优先