package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {

        InetSocketAddress clientAddress = new InetSocketAddress( "localhost", 1111);
        SocketChannel clientSocketChannel = SocketChannel.open(clientAddress);
        log ("Подключение к серверу через порт 1111");
        ByteBuffer buffer= ByteBuffer.wrap(msg);
        clientSocketChannel.write(buffer);









    }
private static void log (String line){
    System.out.println(str);

}



}
