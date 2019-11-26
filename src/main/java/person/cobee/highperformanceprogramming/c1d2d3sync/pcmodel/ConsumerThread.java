package person.cobee.highperformanceprogramming.c1d2d3sync.pcmodel;

import java.util.List;

/**
 * 消费者线程
 *
 * @author cobee
 * @since 2019-11-26
 */
public class ConsumerThread extends Thread {

    private List<String> breadList;
    private int length;

    public ConsumerThread(List<String> breadList, int length){
        this.breadList = breadList;
        this.length = length;
    }

    @Override
    public void run() {
        while(true){
            synchronized (breadList){
                while(true){
                    if(breadList.size() > 0){
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String bread = breadList.remove(0);
                        System.out.println(Thread.currentThread().getName() + "消费" + bread);
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
