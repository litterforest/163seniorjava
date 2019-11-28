package person.cobee.highperformanceprogramming.c1d3d4;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 线程安全，元素有顺序，去重，适用于读操作远大于写操作的场景
 *
 * @author cobee
 * @since 2019-11-27
 */
public class CopyOnWriteArraySetTest {

    public static void main(String[] args) {
        // 对元素去重，有顺序
        CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        copyOnWriteArraySet.add("c");
        copyOnWriteArraySet.add("c");
        copyOnWriteArraySet.add("a");
        copyOnWriteArraySet.add("d");
        for(String str : copyOnWriteArraySet){
            System.out.println(str);
        }
    }

}
