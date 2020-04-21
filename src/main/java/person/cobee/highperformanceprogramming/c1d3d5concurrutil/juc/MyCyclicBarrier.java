package person.cobee.highperformanceprogramming.c1d3d5concurrutil.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义循环栅栏
 *
 * @author cobee
 * @since 2020-04-21
 */
public class MyCyclicBarrier {

    private final int parties;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private volatile int count = 0;
    private volatile Object generation = new Object();

    public MyCyclicBarrier(int parties){
        this.parties = parties;
    }

    public void await(){
        lock.lock();
        try {
            Object myGeneration = generation; // 保存当前的年代
            if(++count == parties){
                nextGeneration(); // 新开一个年代
                return;
            }else{
                for(;;){
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(myGeneration != generation)
                        return;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void nextGeneration(){
        condition.signalAll(); // 把当前年代的线程都唤醒
        count = 0;
        generation = new Object();
    }

}
