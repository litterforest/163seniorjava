package person.cobee.highperformanceprogramming.c1d1d7;

import java.util.concurrent.*;

/**
 * 线程池使用
 *
 * @author cobee
 * @since 2019-11-06
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolTest threadPoolTest = new ThreadPoolTest();
//        threadPoolTest.threadPoolTest1();
//        threadPoolTest.threadPoolTest2();
//        threadPoolTest.threadPoolTest3();
//        threadPoolTest.threadPoolTest4();
//        threadPoolTest.threadPoolTest5();
        threadPoolTest.threadPoolTest6();
    }

    /**
     * 线程执行代码
     */
    private void testCommon(ThreadPoolExecutor executor) throws InterruptedException {
        for(int i = 0; i < 15; i++){
            int threadNo = i;
            executor.execute(() -> {
                System.out.println("线程" + threadNo + "开始执行");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + threadNo + "结束执行");
            });
        }
        Thread.sleep(300L);
        System.out.println("线程池大小：" + executor.getPoolSize());
        System.out.println("线程池等待队列大小：" + executor.getQueue().size());
        Thread.sleep(5000L);
        System.out.println("线程池大小：" + executor.getPoolSize());
        System.out.println("线程池等待队列大小：" + executor.getQueue().size());
    }

    /**
     * 测试标准线程池的使用方法
     */
    private void threadPoolTest1() throws InterruptedException {
        // 核心线程数量5，最大线程数量10，空闲线程存活时间5秒，使用无界队列，队列的容量是无限大
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        Future<String> future = threadPoolExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });
        try {
            String result = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        testCommon(threadPoolExecutor);
    }

    /**
     * 测试标准线程池的使用方法
     * 队列数量最大为3，自定义拒绝策略
     */
    private void threadPoolTest2() throws InterruptedException {
        // 核心线程数量5，最大线程数量10，空闲线程存活时间5秒，使用无界队列，队列的容量是无限大
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                (r,e) -> System.out.println("拒绝接收新任务"));
        testCommon(threadPoolExecutor);
        Thread.sleep(15000L);
        System.out.println("线程池大小：" + threadPoolExecutor.getPoolSize());
        System.out.println("线程池等待队列大小：" + threadPoolExecutor.getQueue().size());
        // 停止接收任务，并且关闭线程池
        threadPoolExecutor.shutdown();
    }

    /**
     * 测试标准线程池的使用方法
     * 固定线程池的使用方法，核心线程数量与最大线程数量一致，无界队列
     */
    private void threadPoolTest3() throws InterruptedException {
//         Executors.newFixedThreadPool(5);
        // 核心线程数量5，最大线程数量5，空闲线程存活时间0秒，使用无界队列，队列的容量是无限大
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        testCommon(threadPoolExecutor);
        // 停止接收任务，并且关闭线程池
        threadPoolExecutor.shutdown();
    }

    /**
     * 测试标准线程池的使用方法
     * 缓冲线程池，核心线程数0，最大线程数Integer.MAX_VALUE，空闲线程的存活时间60L，使用同步队列。
     */
    private void threadPoolTest4() throws InterruptedException {
        // Executors.newCachedThreadPool();
        // 核心线程数量0，最大线程数量Integer.MAX_VALUE，空闲线程存活时间60秒，使用同步队列。
        // 同步队列不是真正的队列，当有任务来的时候如果有空闲的线程，则使用。如果没有会新开一个线程
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        testCommon(threadPoolExecutor);
        Thread.sleep(60000L);
        System.out.println("线程池大小：" + threadPoolExecutor.getPoolSize());
        System.out.println("线程池等待队列大小：" + threadPoolExecutor.getQueue().size());
        // 停止接收任务，并且关闭线程池，在任务队列里面的任务会继续执行完毕
        threadPoolExecutor.shutdown();
    }

    /**
     * 测试定时任务线程池的使用方法
     * 任务只执行一次
     */
    private void threadPoolTest5() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        System.out.println("当前时间:" + System.currentTimeMillis());
        // 在多少秒之后执行一次
        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("开始执行时间：" + System.currentTimeMillis());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束执行时间：" + System.currentTimeMillis());
        }, 1L, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.shutdown();
    }

    /**
     * 测试定时任务线程池的使用方法
     * 任务周期性地执行，任务不会并发执行，会等待上一个任务执行完再执行。
     */
    private void threadPoolTest6() throws InterruptedException {
        Executors.newScheduledThreadPool(5);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        System.out.println("当前时间:" + System.currentTimeMillis());
        // 在多少秒之后执行一次
//        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
//            System.out.println("开始执行时间：" + System.currentTimeMillis());
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("结束执行时间：" + System.currentTimeMillis());
//        }, 2L, 1L, TimeUnit.SECONDS);

        // 在多少秒之后执行一次
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            System.out.println("开始执行时间：" + System.currentTimeMillis());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束执行时间：" + System.currentTimeMillis());
        }, 2L, 1L, TimeUnit.SECONDS);
//        scheduledThreadPoolExecutor.shutdown();
    }

}
