/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.*;
import java.io.*;

/**
 *
 * @author Hao
 */
public class HandlerClient extends Thread {

    private String name;
    private long toCalculate; 

    public HandlerClient() {
        this.toCalculate = 45;
    }

    public HandlerClient(String name) {
        this.name = name;
        this.toCalculate = 45;
    }
    
    public HandlerClient(String name, long toCalculate) {
        this.name = name;
        this.toCalculate = toCalculate;
    }

    @Override
    public void run() {
        try {
            try (Socket socket = new Socket("127.0.0.1", 9999)) {
                System.out.println(name + " connect on port " + socket.getLocalPort());
                
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("" + toCalculate);
            
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                
                String val = in.readLine();
                System.out.println("Is " + toCalculate + " a prime? " + val);
                
                
                InputStream is = socket.getInputStream();
                socket.shutdownOutput(); // Sends the 'FIN' on the network
                while (is.read() >= 0) ; // "read()" returns '-1' when the 'FIN' is reached
                socket.close(); // Now we can close the Socket
                System.out.println(name + " is closed: " + socket.isClosed());              
                
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
