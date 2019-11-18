package person.cobee.highperformanceprogramming.c1d2d3sync;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁：
 * 1，AtomicInteger通过自旋cas操作来修改整数的值
 * 2，如下，通过自旋来实现一个悲观锁
 *
 * @author cobee
 * @since 2019-11-18
 */
public class SpinLock {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        while(!atomicReference.compareAndSet(null, thread)){

        }
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        while(!atomicReference.compareAndSet(thread, null)){

        }
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        spinLock.lock();

        spinLock.unlock();
    }

}
