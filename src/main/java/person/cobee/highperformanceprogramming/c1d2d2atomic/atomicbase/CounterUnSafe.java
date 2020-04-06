package person.cobee.highperformanceprogramming.c1d2d2atomic.atomicbase;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 自己实现的线程安全的程序计数器
 *
 * @author cobee
 * @since 2020-04-06
 */
public class CounterUnSafe {

    volatile int i;
    private static final Unsafe unsafe;
    private static final long valueOffset;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
            Field iField = CounterUnSafe.class.getDeclaredField("i");
            valueOffset = unsafe.objectFieldOffset(iField); // 获取i值的偏移量的引用
        } catch (Exception ex) { throw new Error(ex); }
    }

    public void add(){
        // 自旋操作，直到把值set进去为止
        for(;;){
            int intVolatile = unsafe.getIntVolatile(this, valueOffset);
            if(unsafe.compareAndSwapInt(this, valueOffset, intVolatile, intVolatile + 1))
                break;
        }
    }

}
