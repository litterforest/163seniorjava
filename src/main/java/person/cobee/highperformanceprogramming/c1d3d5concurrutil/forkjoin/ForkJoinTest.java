package person.cobee.highperformanceprogramming.c1d3d5concurrutil.forkjoin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * 任务拆分和结果聚合
 *
 * @author cobee
 * @since 2019-12-02
 */
public class ForkJoinTest {

    public static ForkJoinPool firkJoinPool = new ForkJoinPool(3,
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null,
            true);
    public static ArrayList<String> urls = new ArrayList<>();
    static {
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
        urls.add("http://www.baidu.com");
        urls.add("http://www.sina.com");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyJob job = new MyJob(urls, 0, urls.size());
        ForkJoinTask<String> forkJoinTask = firkJoinPool.submit(job);
        String str = forkJoinTask.get();
        System.out.println(str);
    }

}
