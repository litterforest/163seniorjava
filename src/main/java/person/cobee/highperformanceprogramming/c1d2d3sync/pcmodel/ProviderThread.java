package person.cobee.highperformanceprogramming.c1d2d3sync.pcmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产者线程
 *
 * @author cobee
 * @since 2019-11-26
 */
public class ProviderThread extends Thread {

    private List<String> breadList;
    private int length;

    public ProviderThread(List<String> breadList, int length){
        this.breadList = breadList;
        this.length = length;
    }

    @Override
    public void run() {
        while(true){
            synchronized (breadList){
                while(true){
                    if(breadList.size() < length){
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String bread = "面包" + System.currentTimeMillis();
                        breadList.add(bread);
                        System.out.println(Thread.currentThread().getName() + "生产" + bread);
                        breadList.notify();
                        break;
                    }else{
                        try {
                            breadList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
