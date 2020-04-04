package person.cobee.highperformanceprogramming.c1d1d6;

import java.util.HashMap;
import java.util.Map;

/**
 * map类型的线程封闭
 *
 * @author cobee
 * @since 2020-04-04
 */
public class MapThreadLocal {

    private static final ThreadLocal<Map<String, Object>> mapThreadLocal = new ThreadLocal<>();

    public static Object get(String key){
        Map<String, Object> map = mapThreadLocal.get();
        if(map != null){
            return map.get(key);
        }
        return null;
    }

    public static Object set(String key, Object value){
        Map<String, Object> map = mapThreadLocal.get();
        if(map == null){
            map = new HashMap<>();
            mapThreadLocal.set(map);
        }
        return map.put(key, value);
    }

}
