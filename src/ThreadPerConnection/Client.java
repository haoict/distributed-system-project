/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Hao
 */
public class Client {

    public static void main(String[] args) {
        Thread t = new HandlerClient("Thread-0", 10);
        t.start();
    }
}

class HandlerClient extends Thread {

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
                System.out.println(name + " connect on port " + socket.getLocalPort() + "/IP:" + socket.getInetAddress());
                
                for (int i = 0; i < 10; i++) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("" + toCalculate);

                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));

                    String val = in.readLine();
                    System.out.println("Is " + toCalculate + " a prime? " + val);
                    
                    toCalculate++;
                }
                
                socket.close(); // Now we can close the Socket
                System.out.println(name + " is closed: " + socket.isClosed());   
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

