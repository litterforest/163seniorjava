package person.cobee.highperformanceprogramming.c1d3d1lock;

import person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase.CounterUnSafe;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-13
 */
public class MyReentrantLockTest {

    volatile int i = 0;
    private MyReentrantLock lock = new MyReentrantLock();

    public void add(){
        lock.lock();
        i++;
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        MyReentrantLockTest ct = new MyReentrantLockTest();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Thread th = new Thread(() -> {
                for(int j = 0; j < 10000; j++){
                    ct.add();
                }
                System.out.println("done");
            });
            threads.add(th);
            th.start();
        }
        for(Thread thread : threads){
            thread.join();
        }
        System.out.println(ct.i);
    }

}
