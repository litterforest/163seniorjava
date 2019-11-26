package person.cobee.highperformanceprogramming.c1d2d3sync.pcmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 面包店
 *
 * @author cobee
 * @since 2019-11-26
 */
public class Store {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add(Thread.currentThread().getName() + "生产面包" + System.currentTimeMillis());
        list.add(Thread.currentThread().getName() + "生产面包" + System.currentTimeMillis());
        list.add(Thread.currentThread().getName() + "生产面包" + System.currentTimeMillis());
        list.add(Thread.currentThread().getName() + "生产面包" + System.currentTimeMillis());
        list.add(Thread.currentThread().getName() + "生产面包" + System.currentTimeMillis());

        for(int i = 0; i < 5; i++){
            ProviderThread providerThread = new ProviderThread(list, 10);
            providerThread.start();
        }

        for(int i = 0; i < 10; i++){
            ConsumerThread consumerThread = new ConsumerThread(list, 10);
            consumerThread.start();
        }

    }

}
