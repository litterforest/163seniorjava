package person.cobee.highperformanceprogramming.c1d3d4;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-27
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        // 适用于读操作大大超过写操作的场景
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("a"); // 对列表进行写操作的时候会复制一份
        copyOnWriteArrayList.add("b");
        copyOnWriteArrayList.add("c");
        copyOnWriteArrayList.add("d");
    }

}
