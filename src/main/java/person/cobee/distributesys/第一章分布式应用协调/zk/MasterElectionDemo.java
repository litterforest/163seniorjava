package person.cobee.distributesys.第一章分布式应用协调.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用zk实现选举机制
 *
 * @author cobee
 * @since 2020-05-23
 */
public class MasterElectionDemo {

    static class Server {
        private String clusterName;
        private String nodeName;
        private String server;
        private String serverInfo;
        private String master;
        private String masterNode;
        private String clusterServers;

        public Server(String clusterName, String nodeName, String server) {
            this.clusterName = clusterName;
            this.nodeName = nodeName;
            this.server = server;
            masterNode = "/" + clusterName + "/master";
            clusterServers = "/" + clusterName + "/servers";
            serverInfo = "name:" + nodeName + ",address:" + server;
            // 争抢master节点
            ZkClient zkClient = new ZkClient("192.168.174.130:2181");
            zkClient.setZkSerializer(new SerializableSerializer());
            Thread currentThread = new Thread(() -> {
                while(true){
                    scrambleMaster(zkClient);
                }
            });
            // 监听主节点
            zkClient.subscribeDataChanges(masterNode, new IZkDataListener() {
                @Override
                public void handleDataChange(String dataPath, Object data) throws Exception {
                }
                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    System.out.println("master节点被删除，重新选举");
                    LockSupport.unpark(currentThread);
                }
            });
            // 监听服务列表
            zkClient.subscribeChildChanges(clusterServers, (parentPath, currentChilds) -> {
                System.out.println("当前在线服务列表：" + currentChilds);
            });
            // 连接上后就注册主机信息
            zkClient.createEphemeral(clusterServers + "/" + nodeName, serverInfo);
            currentThread.start();
        }

        /**
         * 竞选master
         * @param zkClient
         */
        public void scrambleMaster(ZkClient zkClient){
            String nodePath = masterNode;
            try {
                zkClient.createEphemeral(nodePath, serverInfo);
                master = serverInfo;
            } catch (ZkNodeExistsException e) {
                // 读取master节点信息
                master = zkClient.readData(nodePath);
                e.printStackTrace();
            }
            // 说明主节点已经选举出来了，当前线程停止争抢
            if(zkClient.exists(nodePath)){
                System.out.println(nodeName + ":当前集群的主节点是" + master);
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) {
//        Server server = new Server("cluster1", "node1", "192.168.1.11:8080");
//        Server server = new Server("cluster1", "node2", "192.168.1.11:8081");
        Server server = new Server("cluster1", "node3", "192.168.1.11:8082");
    }
}
