package person.cobee.highperformanceprogramming.c1d3d5concurrutil.forkjoin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 多线程并发求和
 *
 * @author cobee
 * @since 2020-02-28
 */
public class SumJob extends RecursiveTask<Integer> {

    private List<Integer> elems;
    private int start;
    private int end;

    public SumJob(List<Integer> elems, int start, int end) {
        this.elems = elems;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int interval = end - start;
        // 以10个为一组计算，任务执行
        if(interval <= 10){
            int sum = 0;
            for(int i = start; i < end; i++){
                sum += elems.get(i);
            }
            return sum;
        }else{
            // 继续拆分，直到拆分到最小的单元
            int x = (end + start) / 2;
            SumJob job1 = new SumJob(elems, start, x);
            job1.fork(); // 任务拆分
            SumJob job2 = new SumJob(elems, x, end);
            job2.fork(); // 任务拆分
            // 合并结果
            Integer join1 = job1.join();
            Integer join2 = job2.join();
            return join1 + join2;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        List<Integer> elems = new ArrayList<>();
//        int sum = 0;
//        for(int i = 1; i <= 100; i++){
//            elems.add(i);
//            sum += i;
//        }
//        System.out.println("common sum:" + sum);
//        SumJob job = new SumJob(elems, 0, elems.size());
//        ForkJoinPool forkJoinPool = new ForkJoinPool(5, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
//        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(job);
//        System.out.println("forkJoin sum:" + forkJoinTask.get());

        LocalDate startldt = LocalDate.parse("2020-02-01", DateTimeFormatter.ISO_DATE);
        LocalDate endDate = startldt.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(startldt.format(DateTimeFormatter.ISO_DATE));
        System.out.println(endDate.format(DateTimeFormatter.ISO_DATE));
    }

}
