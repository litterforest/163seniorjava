package person.cobee.highperformanceprogramming.c1d3d2aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 满足特定要求的缓存，使用读写锁来实现，使用到了锁降级的功能
 *
 * @author cobee
 * @since 2020-04-13
 */
public class MySpecCache {

    private volatile boolean cacheValid = false; // 缓存数据是否已准备好
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    /**
     * 1，如果缓存可用就读缓存
     * 2，如果缓存不可用就从数据库中读数据，并且写入到缓存当中。
     * 要求，线程拿到数据后，在这期间数据不能变化。就是不能再有写线程修改数据
     * @param key
     */
    public void processCacheData(String key){
        readLock.lock();
        String data = null;
        if(cacheValid){ // 从缓存中获取数据
            data = RedisCache.cacheMap.get(key);
        }else{ // 从数据库中获取数据，大量请求访问数据库会导致雪崩，要做限流
            readLock.unlock();
            writeLock.lock(); // 加写锁，同一时间只有一个线程写操作，其它线程在这里等待
            try {
                if(!cacheValid){ // 二次判断，缓存没有生成才访问数据库
                    data = DataBase.queryData();
                    RedisCache.cacheMap.put(key, data);
                    cacheValid = true;
                }
            } finally {
                readLock.lock(); // 锁降级，在写锁里面增加读锁，保证同一线程中数据不会变化。
                writeLock.unlock();
            }
        }
        // TODO 拿到缓存数据后做其它处理，比如计算或者写入到其它系统
        System.out.println(data);
        readLock.unlock();
    }

    private static class RedisCache {
        static final Map<String, String> cacheMap = new HashMap<>();
    }

    private static class DataBase {
        static String queryData(){
            System.out.println("从数据库中查询数据");
            return "query data from db";
        }
    }

}
