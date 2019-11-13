package person.cobee.highperformanceprogramming.c2d1d3nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-11
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        // 等待连接完成建立
        while(!socketChannel.finishConnect()){
            Thread.yield();
        }
        // 发送数据到服务器
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        String line = scanner.nextLine();
        ByteBuffer writeBuffer = ByteBuffer.wrap(line.getBytes());
        while(writeBuffer.hasRemaining()){
            socketChannel.write(writeBuffer);
        }

        // 读取服务器返回的响应数据
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        // 网络连接没有关闭
        while(socketChannel.isOpen() && socketChannel.read(readBuffer) != -1){
            if(readBuffer.position() > 0) break;
        }
        readBuffer.flip();
        byte[] cont = new byte[readBuffer.limit()];
        readBuffer.get(cont);
        System.out.println("服务器反回的响应数据是：");
        System.out.println(new String(cont));
        scanner.close();
        socketChannel.close();
    }

}
