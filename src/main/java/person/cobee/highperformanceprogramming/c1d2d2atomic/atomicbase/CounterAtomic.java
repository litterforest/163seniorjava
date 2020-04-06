package person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class CounterAtomic {

    volatile AtomicInteger i = new AtomicInteger(0);

    public void add(){
        i.getAndIncrement();
    }

}
