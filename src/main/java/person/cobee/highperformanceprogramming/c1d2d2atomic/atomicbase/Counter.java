package person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class Counter {

    volatile int i;

    public void add(){
        i++; // 非原子性操作，出现数据不一致的情况
    }

}
