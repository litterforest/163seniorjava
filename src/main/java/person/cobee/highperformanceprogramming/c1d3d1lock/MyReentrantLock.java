package person.cobee.highperformanceprogramming.c1d3d1lock;

import person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase.CounterUnSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-12
 */
public class MyReentrantLock implements Lock {

    private AtomicReference<Thread> owner = new AtomicReference<>(null);
    private AtomicInteger count = new AtomicInteger(0);
    private LinkedBlockingQueue<Thread> awaits = new LinkedBlockingQueue<>(); // 阻塞队列

    @Override
    public void lock() {
        if(!tryLock()){
            awaits.offer(Thread.currentThread()); //第一次没抢到锁，把当前线程放入到等待队列
            for(;;){
                Thread headTh = awaits.peek(); // 查看一下队列头部的线程，是否为当前被唤醒的线程，防止伪唤醒
                if(headTh == Thread.currentThread()){
                    if(!tryLock()){
                        LockSupport.park(); // 当前线程进入阻塞状态
                    }else{ // 如果抢到锁，则从阻塞队列里面删除
                        awaits.poll();
                        break;
                    }
                }else{ // 伪唤醒
                    LockSupport.park();
                }
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        int oldCount = count.get();
        if(oldCount != 0){ // 如果是同一个线程，则可重入，否则获取不到锁，因为锁已经被别的线程拿走
            Thread th = owner.get();
            if(th == Thread.currentThread()){
                count.set(oldCount + 1);
                return true;
            }else{
                return false;
            }
        } else { // 如果等于0说明可以使用cas进行抢锁
            if(count.compareAndSet(oldCount, oldCount + 1)){
                owner.set(Thread.currentThread());
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if(tryUnlock()){ // 解锁成功
            Thread head = awaits.peek();
            LockSupport.unpark(head);
        }
    }

    private boolean tryUnlock(){
        if(owner.get() == Thread.currentThread()){ // 判断是否是当前线程持有锁
            int ct = count.get();
            int nextct = ct - 1;
            count.set(nextct);
            if(nextct == 0){
                owner.compareAndSet(Thread.currentThread(), null);
                return true;
            }else{
                return false;
            }
        }else{
            throw new IllegalMonitorStateException();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
