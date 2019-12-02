package person.cobee.highperformanceprogramming.c1d3d5concurrutil.forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-12-02
 */
public class MyJob extends RecursiveTask<String> {

    private List<String> urls;
    private int start; // 开始下标
    private int end; // 结束下标

    public MyJob(List<String> urls, int start, int end) {
        this.urls = urls;
        this.start = start;
        this.end = end;
    }

    @Override
    protected String compute() {
        int count = end - start; // 集合中有多少个元素
        if(count <= 10){ // 直接执行
            StringBuilder sbuff = new StringBuilder();
            for(int i = start; i < end; i++){
                sbuff.append("访问网站：").append(urls.get(i)).append("\r\n");
            }
            return sbuff.toString();
        }else{
            // 继续拆分
            int x = (start + end) / 2;
            MyJob job1 = new MyJob(urls, start, x);
            job1.fork(); // 执行任务
            MyJob job2 = new MyJob(urls, x, end);
            job2.fork(); // 执行任务
            // 获取返回结果
            StringBuilder sbuff = new StringBuilder();
            sbuff.append(job1.join());
            sbuff.append(job2.join());
            return sbuff.toString();
        }
    }

}
