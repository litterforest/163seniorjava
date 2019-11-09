package person.cobee.highperformanceprogramming.c2d1d2bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-09
 */
public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);
        OutputStream outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        String nextLine = scanner.nextLine();
        outputStream.write(nextLine.getBytes(StandardCharsets.UTF_8));
        scanner.close();
        socket.close();
    }

}
