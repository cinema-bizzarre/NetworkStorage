package client;


import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {

    private final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);


    public static void main(String[] args) throws Exception {

        try{
        new Client().start();
    } finally {
            THREAD_POOL.shutdown();
        }
        }


   public void start() throws Exception{

       SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 9000));
       channel.configureBlocking(false);
       Selector selector = Selector.open();
       channel.register(selector, SelectionKey.OP_CONNECT);
        ByteBuffer buffer = ByteBuffer.allocate(256);
       BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

       for (int i = 0; i < 5; i++) {
           THREAD_POOL.execute(() -> {
               System.out.println("Новый клиент поключился в потоке " + Thread.currentThread().getName());
               try {

                   while (true) {
                       channel.write(ByteBuffer.wrap(String.format(
                               "[%s] Сообщение из потока %s",
                               LocalDateTime.now(),
                               Thread.currentThread().getName()
                       ).getBytes()));
                       Thread.sleep(1000);
                   }
               } catch (IOException | InterruptedException e) {
                   e.printStackTrace();
               }
           });
           SelectionKey key = channel.keyFor(selector);
           key.interestOps(SelectionKey.OP_WRITE);
           selector.wakeup();
       }

       while (true){
           selector.select();
           for (SelectionKey selectionKey : selector.selectedKeys()){
           if (selectionKey.isConnectable()) {
               channel.finishConnect();
               selectionKey.interestOps(SelectionKey.OP_WRITE);
           }else if (selectionKey.isReadable()){
               buffer.clear();
               channel.read(buffer);
               System.out.println("Прочитали =" + new String(buffer.array()));
           }else if (selectionKey.isWritable()){
                String line = queue.poll();
                if (line != null){
                    channel.write(ByteBuffer.wrap(line.getBytes(StandardCharsets.UTF_8)));
                }
                selectionKey.interestOps(SelectionKey.OP_READ);
           }
           }
       }






   }







   }





