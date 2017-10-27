package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSender {

    public static void main(String[] args) throws IOException {
        FileSender client = new FileSender();
        SocketChannel socketChannel = client.CreateChannel();
        client.sendFile(socketChannel);
    }

    private void sendFile(SocketChannel socketChannel) throws IOException {
        //Читаем файл
        Path path = Paths.get("C:/Users/Admin/Desktop/text.txt");
        FileChannel inChannel = FileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) > 0){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
    }

    private SocketChannel CreateChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        SocketAddress socketAddress = new InetSocketAddress("localhost", 9000);
        socketChannel.connect(socketAddress);
        return socketChannel;
    }
}
