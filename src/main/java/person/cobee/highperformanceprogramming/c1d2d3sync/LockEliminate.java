package person.cobee.highperformanceprogramming.c1d2d3sync;

/**
 * 锁消除实验，在单线程中jit编译器会把synchronized关键字去掉
 *
 * @author cobee
 * @since 2020-04-09
 */
public class LockEliminate {

    public void test(){
        // 在单线程使用的情况下，Jit编译器会进行优化，把synchronized去掉，代码进行无锁运行。
        StringBuffer sbuff = new StringBuffer();
        sbuff.append("a");
        sbuff.append("b");
        sbuff.append("c");
    }

    public static void main(String[] args) {
        for(int i = 0; i < 1000000; i++){
            new LockEliminate().test();
        }
    }

}
