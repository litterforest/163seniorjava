package person.cobee.highperformanceprogramming.c1d2d3sync;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁：
 * 1，AtomicInteger通过自旋cas操作来修改整数的值
 * 2，如下，通过cas自旋操作来实现一个悲观锁，也是排它锁，也是不可重入锁。
 * 可以锁住整个线程，是一个很粗粒度的锁
 *
 * 缺点是消耗大量的cpu性能
 *
 * @author cobee
 * @since 2019-11-18
 */
public class SpinLock {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        while(!atomicReference.compareAndSet(null, thread)){
            // 可以记录自旋的次数，次数变多了可以考虑sleep一下
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
