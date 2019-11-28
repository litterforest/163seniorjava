package person.cobee.highperformanceprogramming.c1d3d4.set;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-28
 */
public class ConcurrentSkipListSetTest {

    public static void main(String[] args) {
        ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
        set.add("aa");
        set.add("ca");
        set.add("aa");
        set.add("da");
        System.out.println(set.toString());
    }

}
