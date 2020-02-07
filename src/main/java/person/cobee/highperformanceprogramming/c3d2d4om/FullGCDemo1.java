package person.cobee.highperformanceprogramming.c3d2d4om;

/**
 * 频繁调用system.gc导致fullgc次数过多
 * 使用server模式运行 开启GC日志
 * -Xmx512m -server -verbose:gc -XX:+PrintGCDetails
 * 显式禁用System.gc方法，忽略它。 -XX:+DisabledExplicitGC
 * @author cobee
 * @since 2019-12-17
 */
public class FullGCDemo1 {

    public static void main(String[] args) throws InterruptedException {
        byte[] tmp1 = null;
        for (int i = 0; i < 1000; i++) {
            byte[] tmp = new byte[1024 * 1024 * 256]; // 256兆
            tmp1 = tmp;
            // System.gc(); // 8G堆 128兆。full GC
            System.out.println("running...");
            Thread.sleep(2000L);
        }
    }

}
