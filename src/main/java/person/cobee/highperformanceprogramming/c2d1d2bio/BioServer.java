package person.cobee.highperformanceprogramming.c2d1d2bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-09
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动。。。");
        while(!serverSocket.isClosed()){
            // 接收客户端请求
            Socket socket = serverSocket.accept();
            System.out.println("客户端:" + socket.toString());
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = null;
            while((line = bufferedReader.readLine()) != null){    // 阻塞，等待读取数据
                if("bye".equals(line)){
                    break;
                }
                System.out.println("客户端信息：" + line);
            }
            socket.close();
        }
        serverSocket.close();
    }

}
