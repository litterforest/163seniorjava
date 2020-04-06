package person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class CounterTest {

    public static void main(String[] args) throws InterruptedException {
        CounterAtomic ct = new CounterAtomic();
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
