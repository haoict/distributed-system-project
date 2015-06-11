/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPerRequestServer;

import java.io.IOException;
import java.net.*;

public class ThreadPerRequestServer {

    public static void main(String[] args) {
        try {
            //Step 1: Create Server Socket
            ServerSocket server = new ServerSocket(9999);
            int index = 0;

            while (true) {
                System.out.println("Multithread: Wait for client to connect....");
                //Step 2: Wait for client
                Socket socket = server.accept();

                //Step 3: Create parallel line of execution
                HandlerServer fh = new HandlerServer(socket, index);
                fh.start();
                index++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
