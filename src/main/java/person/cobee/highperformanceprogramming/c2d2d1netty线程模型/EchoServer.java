package person.cobee.highperformanceprogramming.c2d2d1netty线程模型;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * netty服务器的基本使用
 *
 * @author cobee
 * @since 2019-11-26
 */
public class EchoServer {

    // 监听端口号
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws InterruptedException {
        // 创建accept线程组
        EventLoopGroup acceptGroups = new NioEventLoopGroup();
        // 创建i/o线程组
        EventLoopGroup rwGroups = new NioEventLoopGroup();
        // 服务端启动引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(acceptGroups, rwGroups).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
        .handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                p.addLast(new EchoServerHandler());
            }
        });
        // 通过bind启动服务
        ChannelFuture f = serverBootstrap.bind(PORT).sync();
        // 阻塞主线程，知道网络服务被关闭
        f.channel().closeFuture().sync();
        // 关闭线程组
        acceptGroups.shutdownGracefully();
        rwGroups.shutdownGracefully();
    }

}
