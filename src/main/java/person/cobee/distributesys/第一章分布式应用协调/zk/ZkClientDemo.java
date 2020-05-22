package person.cobee.distributesys.第一章分布式应用协调.zk;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class ZkClientDemo {

	public static void main(String[] args) {
//		deleteNode("/mike/a");
		createOrUpdateNode("/mike/a", "kkkyyyy");
	}

	public static void createOrUpdateNode(String nodePath, String value){
		ZkClient client = new ZkClient("192.168.174.130:2181");
		client.setZkSerializer(new SerializableSerializer());
		if (client.exists(nodePath)) {
			client.writeData(nodePath, value);
		} else {
			client.createPersistent(nodePath, value);
		}

		String readResult = client.readData(nodePath);
		System.out.println("readResult:" + readResult);
		client.close();
	}

	public static void deleteNode(String nodePath){
		ZkClient client = new ZkClient("192.168.174.130:2181");
		client.setZkSerializer(new SerializableSerializer());
		client.delete(nodePath);
		System.out.println("deleteNode:" + nodePath);
		client.close();
	}
}
