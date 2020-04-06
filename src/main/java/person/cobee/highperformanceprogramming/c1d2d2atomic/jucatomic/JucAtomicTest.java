package person.cobee.highperformanceprogramming.c1d2d2atomic.jucatomic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class JucAtomicTest {

    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        atomicBoolean.getAndSet(true);
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
        System.out.println(atomicIntegerArray.getAndIncrement(0));
        System.out.println(atomicIntegerArray.get(0));
    }

}
