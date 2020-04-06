package person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class CounterSync {

    volatile int i;

    public synchronized void add(){
        i++;
    }

}
