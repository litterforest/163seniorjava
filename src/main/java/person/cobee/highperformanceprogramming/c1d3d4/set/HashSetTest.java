package person.cobee.highperformanceprogramming.c1d3d4.set;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 元素不重复，没有顺序
 *
 * @author cobee
 * @since 2019-11-28
 */
public class HashSetTest {

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("aa");
        hashSet.add("ca");
        hashSet.add("aa");
        hashSet.add("da");
        Iterator<String> iterator = hashSet.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
