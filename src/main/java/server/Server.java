package server;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class Server {


    public static void main(String[] args) throws IOException {

        // открываем селектор
        Selector selector = Selector.open();
        // открываем сокет-канал
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //открываем порт
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8189);
        //прослушиваем соединение
        serverSocketChannel.bind(socketAddress);
        //регулируем режим блокировки канала
        serverSocketChannel.configureBlocking(false);
        int ops = serverSocketChannel.validOps();
        SelectionKey selectionKey = serverSocketChannel.register(selector,ops,0);
        //поддерживаем работу сервера
        while (true){
            log ("Жду нового подключения");
            Selector.select();
            serverKey = Selector.selectionKey();
            Iterator serverIterator = serverKey.Iterator();
            while (serverIterator.hasNext()){
                SelectionKey myKey = serverIterator.next();
                // проверяем готов канал этого ключа принять новое соединение с сокетом
                if(myKey.isAcceptable()) {
                    SocketChannel clientSocketChannel = serverSocketChannel.accept();
                    // регулируем режим блокировки
                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector, SelectionKey.OP_READ);
                    log("Соединение принято" + clientSocketChannel.getLocalAddress() + / n );
                    // проверяем, готов канал этого ключа к чтению
                }else  if (myKey.isReadable()){
                    SocketChannel clientSocketChannel = (SocketChannel) MYKEY.channel ();
                    ByteBuffer serverBuffer = ByteBuffer.allocate (256);
                    clientSocketChannel.read(serverBuffer);




                }
            }

        }


    }

}