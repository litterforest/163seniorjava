package person.cobee.highperformanceprogramming.c1d3d4.list;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 线程安全，有顺序，适用于读操作远大于写操作的场景
 *
 * @author cobee
 * @since 2019-11-27
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        // 适用于读操作大大超过写操作的场景
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("a"); // 对列表进行写操作的时候会复制一份，这样就不会影响到读的操作
        copyOnWriteArrayList.add("b");
        copyOnWriteArrayList.add("c");
        copyOnWriteArrayList.add("d");
    }

}
