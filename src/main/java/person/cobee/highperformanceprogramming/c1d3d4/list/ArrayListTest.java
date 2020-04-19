package person.cobee.highperformanceprogramming.c1d3d4.list;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-27
 */
public class ArrayListTest {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
        for(int i=0; i < 11; ++i){
            list.add("cobee");
        }

        for(String str : list){
            System.out.println(str);
        }

        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }

    }

}
