package person.cobee.distributesys.第一章分布式应用协调.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

/**
 * 使用zk实现分布式队列
 *
 * @author cobee
 * @since 2020-05-29
 */
public class ZkDistributedQueue {

    static final String queueNode = "/queue";

    public static class Producer{
        public void produce(){
            ZkClient zkClient = new ZkClient("192.168.174.130:2181");
            zkClient.setZkSerializer(new SerializableSerializer());
            zkClient.createPersistentSequential(queueNode + "/", null);
            zkClient.close();
        }
    }

    public static class Consumer{
        public void consume(){
            ZkClient zkClient = new ZkClient("192.168.174.130:2181");
            zkClient.setZkSerializer(new SerializableSerializer());
            zkClient.subscribeChildChanges(queueNode, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    if(currentChilds != null && !currentChilds.isEmpty()){
//                        System.out.println(currentChilds);
                        Collections.sort(currentChilds);
                        String firstNode = currentChilds.get(0);
                        boolean deleteResult = zkClient.delete(queueNode + "/" + firstNode);
                        if(deleteResult){
                            // TODO 做一些处理的代码
                            System.out.println(Thread.currentThread().getName() + ":消费了节点" + firstNode);
                        }
                    }
                }
            });
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            zkClient.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        ZkDistributedQueue.Producer producer = new ZkDistributedQueue.Producer();
//        ZkDistributedQueue.Consumer consumer = new ZkDistributedQueue.Consumer();
//        consumer.consume();
//        producer.produce();
//        producer.produce();
//        producer.produce();
//        producer.produce();
//        producer.produce();
        for(int i = 0; i < 50; i++){
            Thread thread = new Thread(() -> {
                ZkDistributedQueue.Producer producer = new ZkDistributedQueue.Producer();
                producer.produce();
            });
            thread.start();
        }
        for(int i = 0; i < 10; i++){
            Thread thread = new Thread(() -> {
                ZkDistributedQueue.Consumer consumer = new ZkDistributedQueue.Consumer();
                consumer.consume();
            });
            thread.start();
        }
        while(true){
            Thread.sleep(1000);
        }
    }

}
