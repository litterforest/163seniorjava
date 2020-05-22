package person.cobee.distributesys.第一章分布式应用协调.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 使用zk实现配置中心
 *
 * @author cobee
 * @since 2020-05-22
 */
public class ConfigCenterDemo {

    /**
     * 一个配置项对应一个节点数据
     * @param nodePath
     * @param nodeVal
     */
    public static void createConfig(String nodePath, String nodeVal){
        ZkClient zkClient = new ZkClient("192.168.174.130:2181");
        zkClient.setZkSerializer(new SerializableSerializer());
        if(zkClient.exists(nodePath)){
            zkClient.writeData(nodePath, nodeVal);
        }else{
            zkClient.createPersistent(nodePath, true);
            zkClient.writeData(nodePath, nodeVal);
        }
        zkClient.close();
    }

    /**
     * 一个节点存放整个配置文件的数据
     * @param nodePath
     * @param configPath
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void createConfig2(String nodePath, String configPath) throws URISyntaxException, IOException {
        ZkClient zkClient = new ZkClient("192.168.174.130:2181");
        zkClient.setZkSerializer(new SerializableSerializer());
        Path path = Paths.get(new URI("file:///" + configPath));
        byte[] dataByts = Files.readAllBytes(path);
        if(zkClient.exists(nodePath)){
            zkClient.writeData(nodePath, dataByts);
        }else{
            zkClient.createPersistent(nodePath, dataByts);
        }
        zkClient.close();
    }

    /**
     * 读取配置中心的数据
     * @param nodePath
     */
    public static void getConfig1(String nodePath){
        ZkClient zkClient = new ZkClient("192.168.174.130:2181");
        zkClient.setZkSerializer(new SerializableSerializer());
        Object resultObj = zkClient.readData(nodePath);
        // 监听配置修改过后的新数据
        zkClient.subscribeDataChanges(nodePath, new IZkDataListener(){
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("数据被修改过：" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("数据被删除了");
            }
        });
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ConfigCenterDemo.createConfig("/cobee/config1", "11");
        ConfigCenterDemo.createConfig2("/cobee/config2", "E:/git/seniorjava163/pom.xml");
        ConfigCenterDemo.getConfig1("/cobee/config1");
        ConfigCenterDemo.createConfig("/cobee/config1", "112");
        Thread.sleep(3600 * 1000
        );
    }

}
