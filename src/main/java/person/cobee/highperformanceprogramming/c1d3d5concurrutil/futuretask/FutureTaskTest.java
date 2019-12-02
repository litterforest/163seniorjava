package person.cobee.highperformanceprogramming.c1d3d5concurrutil.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一个FutureTask实例只能使用一次，futuretask的get方法会阻塞当前线程
 *
 * @author cobee
 * @since 2019-12-02
 */
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = new HelloWorldCallable();
        Callable<String> callable2 = new HelloWorld2Callable();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);
        new Thread(futureTask).start();
        new Thread(futureTask2).start();
        String s2 = futureTask2.get();
        System.out.println("first request done");
        String s = futureTask.get();
        System.out.println("second request done");
        System.out.println(s);
        System.out.println(s2);
    }

}
