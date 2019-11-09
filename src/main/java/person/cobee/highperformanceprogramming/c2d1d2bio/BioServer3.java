package person.cobee.highperformanceprogramming.c2d1d2bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 接收浏览器发过来的请求，获取请求中的内容，返回响应结果。
 *
 * @author cobee
 * @since 2019-11-09
 */
public class BioServer3 {

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
                    // 响应结果 200
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                    outputStream.write("Hello World".getBytes());
                    outputStream.flush();
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
