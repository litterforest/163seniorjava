package person.cobee.highperformanceprogramming.c1d2d2atomic.jucatomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class LongAdderTest {

    public static void main(String[] args) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Thread th = new Thread(() -> {
                for(int j = 0; j < 10000; j++){
                    longAdder.increment();
                }
                System.out.println("done");
            });
            threads.add(th);
            th.start();
        }
        for(Thread thread : threads){
            thread.join();
        }
        System.out.println(longAdder.sum());
    }

}
