package person.cobee.highperformanceprogramming.c1d3d2aqs;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写分离的思想，自己实现一个线程安全的HashMap
 *
 * @author cobee
 * @since 2020-04-13
 */
public class MyConcurrentHashMap {

    private HashMap<String, Object> map = new HashMap();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();

    public Object get(String key){
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Object put(String key, Object value){
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public Set<String> allKeys(){
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    public void clear(){
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyConcurrentHashMap myConcurrentHashMap = new MyConcurrentHashMap();
        for(int i = 0; i < 1000; i++){
            final int ii = i;
            Thread th = new Thread(() -> {
                if(ii % 2 == 0){
                    System.out.println("获取" + myConcurrentHashMap.get(String.valueOf(ii - 1)));
                }else{
                    System.out.println("加入第" + ii + "个元素");
                    myConcurrentHashMap.put(String.valueOf(ii), "第" + ii + "个元素");
                }
                if(ii % 100 == 0){
                    System.out.println(myConcurrentHashMap.allKeys());
                }else{
                    myConcurrentHashMap.clear();
                }
            });
            th.start();
        }
        Thread.sleep(1000L);
        System.out.println("集合中总共：" + myConcurrentHashMap.allKeys().size() + "个元素");
    }

}
