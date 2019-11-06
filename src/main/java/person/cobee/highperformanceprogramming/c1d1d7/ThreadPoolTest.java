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
        threadPoolTest.threadPoolTest2();
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

}
