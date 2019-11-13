package person.cobee.highperformanceprogramming.c2d1d3nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * nio服务器的标准写法
 *
 * @author cobee
 * @since 2019-11-11
 */
public class NioServer3 {

    private static List<SocketChannel> channels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 设置成非阻塞
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverSocketChannel);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        System.out.println("nio服务器启动");
        while(true){
            // select方法有阻塞效果,直到有事件通知才会有返回
            selector.select();
            // 获取事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历事件，处理事件
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                // 连接建立事件
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.attachment();
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 对建立的连接进行读事件监听
                    socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
                    System.out.println("收到新连接[" + socketChannel.getRemoteAddress() + "]");
                }
                // 监听读取事件
                if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.attachment();
                    try {
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        if (socketChannel.read(readBuffer) == 0) {
                            // 等于0,代表这个通道没有数据需要处理,那就待会再处理
                            continue;
                        }
                        while (socketChannel.isOpen() && socketChannel.read(readBuffer) != -1) {
                            // 在长连接的情况下判断数据读取有没有结束，此处做一个简单判断，如果position大于0，表示读取到数据并结束
                            if (readBuffer.position() > 0) break;
                        }
                        if (readBuffer.position() == 0) continue;
                        readBuffer.flip(); // 转换成读取模式
                        byte[] bytes = new byte[readBuffer.limit()];
                        readBuffer.get(bytes);
                        System.out.println(socketChannel.getRemoteAddress() + ":" + new String(bytes));

                        // 返回响应数据
                        String str = "HTTP/1.1 200 OK\r\nContent-Length: 11\r\n\r\nHello World";
                        ByteBuffer writeBuffer = ByteBuffer.wrap(str.getBytes());
                        while (writeBuffer.hasRemaining()) {
                            socketChannel.write(writeBuffer);
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                        selectionKey.cancel();
                    }
                }
            }
            selector.selectNow();
        }
    }

}
