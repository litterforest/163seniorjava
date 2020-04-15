package person.cobee.highperformanceprogramming.c1d3d3map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-22
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cobee", "hello world");
        hashMap.get("cobee");

        // 高并发线程安全
        ConcurrentHashMap<String, String> concurMap = new ConcurrentHashMap<>();
        concurMap.put("name", "stupid");
        concurMap.get("name");
    }

}
