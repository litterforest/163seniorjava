package person.cobee.highperformanceprogramming.c2d1d3nio;

import java.nio.ByteBuffer;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-10
 */
public class BufferTest {

    public static void main(String[] args) {
        // 申请缓存块
//        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        // 使用堆外内存
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
        System.out.printf("bytebuffer的capactity=%d，位置position=%d，限制limit=%d\r\n", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        System.out.printf("bytebuffer的capactity=%d，位置position=%d，限制limit=%d\r\n", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
        // 转换成读的模式
        System.out.println("开始读取数据");
        byteBuffer.flip();
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.get());
        System.out.printf("bytebuffer的capactity=%d，位置position=%d，限制limit=%d\r\n", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
        // 清除已读数据
        System.out.println("清除已读数据");
        byteBuffer.compact();
        System.out.printf("bytebuffer的capactity=%d，位置position=%d，限制limit=%d\r\n", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);
        byteBuffer.put((byte) 6);
        System.out.printf("bytebuffer的capactity=%d，位置position=%d，限制limit=%d\r\n", byteBuffer.capacity(), byteBuffer.position(), byteBuffer.limit());
    }

}
