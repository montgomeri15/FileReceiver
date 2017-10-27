package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class FileReciever {

    public static void main(String[] args) throws IOException {
        FileReciever server = new FileReciever();
        SocketChannel socketChannel = server.createServerSocketChannel();
        server.readFileFromSocketChannel(socketChannel);
        }

    private void readFileFromSocketChannel(SocketChannel socketChannel) throws IOException {
        //Пытаемся создать новый файл
        Path path = Paths.get("C:/Users/Admin/Desktop/text.txt");
        FileChannel fileChannel = FileChannel.open(path,
                EnumSet.of(StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE));
        //Размещаем байтбуфер
        ByteBuffer buffer = ByteBuffer.allocate(1024);  //Вместимость буфера 1024
        while (socketChannel.read(buffer) > 0){
            buffer.flip();  //щелчок
            fileChannel.write(buffer);
            buffer.clear();
        }
        fileChannel.close();
        System.out.println("Получение файла успешно!");
        socketChannel.close();
    }

    private SocketChannel createServerSocketChannel() throws IOException {
        ServerSocketChannel serverSocket = null;
        SocketChannel client = null;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        client = serverSocket.accept();
        System.out.println("соединение установлено..." + client.getRemoteAddress());
        return null;
    }
}
