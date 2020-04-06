package person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class CounterLock {

    volatile int i;
    Lock lock = new ReentrantLock();

    public void add(){
        lock.lock();
        i++;
        lock.unlock();
    }

}
