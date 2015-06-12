/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ThreadPerRequestUsingSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 *
 * @author Hao
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 4444;
        SocketChannel channel = SocketChannel.open();

        // we open this channel in non blocking mode
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("localhost", port));

        while (!channel.finishConnect()) {
            // System.out.println("still connecting");
        }
                
        int toCalculate = 2;
        
        for (int i = 1; i < 10; i++) {
            // create new request/write some data into the channel
            CharBuffer buffer = CharBuffer.wrap(String.valueOf(toCalculate));
            while (buffer.hasRemaining()) {
                channel.write(Charset.defaultCharset().encode(buffer));
            }
            Thread.sleep(1000);
            
            // see if any message has been received
            ByteBuffer bufferA = ByteBuffer.allocate(127);
            int count = 0;
            String message = "";
            while ((count = channel.read(bufferA)) > 0) {
                // flip the buffer to start reading
                bufferA.flip();
                message += Charset.defaultCharset().decode(bufferA);
            }
            
            System.out.print("is " +  toCalculate + " a prime?: ");
            if (message.length() > 0) {
                System.out.print(message);
                message = "";
            }
            toCalculate++;
            System.out.print("\n");
        }
    }
}
