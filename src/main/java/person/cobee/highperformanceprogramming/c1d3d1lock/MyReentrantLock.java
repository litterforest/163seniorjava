package person.cobee.highperformanceprogramming.c1d3d1lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-12
 */
public class MyReentrantLock implements Lock {

    private AtomicReference<Thread> owner = new AtomicReference<>(null);
    private AtomicInteger count = new AtomicInteger(0);
    private LinkedBlockingQueue<Thread> awaits = new LinkedBlockingQueue<>(); // 等待队列

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
