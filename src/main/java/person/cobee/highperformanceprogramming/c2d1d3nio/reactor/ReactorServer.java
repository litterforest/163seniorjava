package person.cobee.highperformanceprogramming.c2d1d3nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.Random;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-14
 */
public class ReactorServer {

    private static ServerSocketChannel serverSocketChannel;
    // 1、创建多个线程 - accept处理reactor线程 (accept线程)
    private static ReactorThread[] mainReactorThreads = new ReactorThread[1];
    // 2、创建多个线程 - io处理reactor线程  (I/O线程)
    private static ReactorThread[] subReactorThreads = new ReactorThread[8];

    public static void main(String[] args) throws Exception {
        // 1, 创建accept和io两组线程
        // 1.1, 创建IO线程,负责处理客户端连接以后socketChannel的IO读写
        for (int i = 0; i < subReactorThreads.length; i++) {
            subReactorThreads[i] = new IOReactorThread();
        }
        // 1.2, 创建mainReactor线程, 只负责处理serverSocketChannel
        for (int i = 0; i < mainReactorThreads.length; i++) {
            mainReactorThreads[i] = new AcceptReactorThread(subReactorThreads);
        }
        // 2、 创建serverSocketChannel，注册到mainReactor线程上的selector上
        // 2.1、 创建ServerSocketChannel
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        // 2.2、 将serverSocketChannel注册到selector
        int index = new Random().nextInt(mainReactorThreads.length);
        mainReactorThreads[index].doStart();
        SelectionKey selectionKey = mainReactorThreads[index].register(serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        // 3、 为serverSocketChannel绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("启动完成，端口8080");
    }

}
