package person.cobee.highperformanceprogramming.c1d3d5concurrutil.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-22
 */
public class MyFutureTask implements Runnable {

    private Callable callable;
    private volatile int state; // 无锁编程，其它线程需要可见性才能查看
    private static final int NEW = 0;
    private static final int RUNNING = 1;
    private static final int FINISHED = 1;
    private AtomicReference<Thread> runner = new AtomicReference<>(); // 保证线程安全
    private LinkedBlockingQueue<Thread> awaiters = new LinkedBlockingQueue<>();
    private volatile Object result;

    public MyFutureTask(Callable callable){
        this.callable = callable;
    }

    @Override
    public void run() {
        // 只有一个线程可以执行代码
        if(state != NEW || !runner.compareAndSet(null, Thread.currentThread())){
            return;
        }
        state = RUNNING;
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = FINISHED;
        }
        // 唤醒等待的线程
        for(;;){
            Thread thread = awaiters.poll();
            if(thread == null)
                break;
            LockSupport.unpark(thread);
        }
    }

    public Object get(){
        if(state != FINISHED){
            awaiters.offer(Thread.currentThread());
            LockSupport.park(); // 当前线程挂起
        }
        while(state != FINISHED){ // 防止伪唤醒
            LockSupport.park(); // 当前线程挂起
        }
        return result;
    }

}
