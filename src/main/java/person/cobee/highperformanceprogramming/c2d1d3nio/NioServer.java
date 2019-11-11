package person.cobee.highperformanceprogramming.c2d1d3nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-11
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 设置成非阻塞
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        System.out.println("nio服务器启动");
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                System.out.println("收到新连接[" + socketChannel.getRemoteAddress() + "]");
                socketChannel.configureBlocking(false);  // 设置成非阻塞连接
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while(socketChannel.isOpen() && socketChannel.read(byteBuffer) != -1){
                    // 在长连接的情况下判断数据读取有没有结束，此处做一个简单判断，如果position大于0，表示读取到数据并结束
                    if(byteBuffer.position() > 0) break;
                }
                //if(byteBuffer.position() == 0) continue;
                byteBuffer.flip(); // 转换成读取模式
                byte[] bytes = new byte[byteBuffer.limit()];
                byteBuffer.get(bytes);
                System.out.println(socketChannel.getRemoteAddress() + ":" + new String(bytes));

                // 返回响应数据
                String str = "HTTP/1.1 200 OK\r\nContent-Length: 11\r\n\r\nHello World";
                ByteBuffer writeBuffer = ByteBuffer.wrap(str.getBytes());
                while(writeBuffer.hasRemaining()){
                    socketChannel.write(writeBuffer);
                }
                // 由客户端发出连接关闭请求
//                socketChannel.close();
            }
        }
    }

}
