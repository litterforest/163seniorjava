package person.cobee.highperformanceprogramming.c1d3d4.map;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-28
 */
public class ConcurrentSkipListMapTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        });
        map.put("a", "");
        map.put("c", "");
        map.put("b", "");
        System.out.println(map.keySet().toString());
    }

}
