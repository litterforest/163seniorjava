package person.cobee.middleware.cache.java;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 使用google的guava来实现缓存功能
 *
 * @author cobee
 * @since 2020-02-07
 */
public class GuavaCacheManager {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cacheManager = CacheBuilder.newBuilder()
                // 同时有8个线程操作
                .concurrencyLevel(8)
                .expireAfterWrite(8, TimeUnit.SECONDS)
                .initialCapacity(10)
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .maximumSize(100)
                .recordStats()
                .removalListener((notification) -> {
                    System.out.println(notification.getKey() + " 被移除了，原因： " + notification.getCause());
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("缓存没有时，从数据库加载" + key);
                        // TODO jdbc的代码~~忽略掉
                        return "cobee " + key;
                    }
                });

        // 第一次读取
        for (int i = 0; i < 20; i++) {
            System.out.println(cacheManager.get("uid" + i));
        }

        // 第二次读取
        for (int i = 0; i < 20; i++) {
            System.out.println(cacheManager.get("uid" + i));
        }
        System.out.println("cache stats:");
        //最后打印缓存的命中率等 情况
        System.out.println(cacheManager.stats().toString());
        Thread.sleep(19000);
    }

}
