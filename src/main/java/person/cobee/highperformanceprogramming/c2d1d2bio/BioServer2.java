package person.cobee.highperformanceprogramming.c2d1d2bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务器升级版
 *
 * @author cobee
 * @since 2019-11-09
 */
public class BioServer2 {

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动。。。");
        while(!serverSocket.isClosed()){
            // 接收客户端请求
            Socket socket = serverSocket.accept();
            System.out.println("客户端:" + socket.toString());
            pool.submit(() -> {
                InputStream inputStream = null;
                try {
                    inputStream = socket.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    String line = null;
                    while((line = bufferedReader.readLine()) != null){    // 阻塞，等待读取数据
                        if(line.length() == 0){
                            break;
                        }
                        System.out.println("客户端["+socket.toString()+"]信息：" + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        serverSocket.close();
    }

}
