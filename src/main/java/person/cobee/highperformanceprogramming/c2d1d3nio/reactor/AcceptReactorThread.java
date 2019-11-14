package person.cobee.highperformanceprogramming.c2d1d3nio.reactor;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-14
 */
public class AcceptReactorThread extends ReactorThread {

    private AtomicInteger incr = new AtomicInteger(0);
    private ReactorThread[] ioReactorThreads;

    public AcceptReactorThread(ReactorThread[] ioReactorThreads) throws IOException {
        super();
        this.ioReactorThreads = ioReactorThreads;
    }

    @Override
    protected void handler(SelectableChannel channel) throws Exception {
        // 只做请求分发，不做具体的数据读取
        ServerSocketChannel ch = (ServerSocketChannel) channel;
        SocketChannel socketChannel = ch.accept();
        socketChannel.configureBlocking(false);
        // 收到连接建立的通知之后，分发给I/O线程继续去读取数据
        int index = incr.getAndIncrement() % ioReactorThreads.length;
        ReactorThread workEventLoop = ioReactorThreads[index];
        workEventLoop.doStart();
        SelectionKey selectionKey = workEventLoop.register(socketChannel);
        selectionKey.interestOps(SelectionKey.OP_READ);
        System.out.println(Thread.currentThread().getName() + "收到新连接 : " + socketChannel.getRemoteAddress());
    }

}
