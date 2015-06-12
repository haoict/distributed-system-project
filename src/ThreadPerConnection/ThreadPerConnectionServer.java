/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ThreadPerConnectionServer {

    public static void main(String[] args) {
        try {
            //Step 1: Create Server Socket
            ServerSocket server = new ServerSocket(9999);
            int index = 0;

            while (true) {
                System.out.println("Wait for client to connect....");
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

class HandlerServer extends Thread {

    private Socket socket = null;
    private int index = 0;

    //Step 3.1: Receive paramater from master
    public HandlerServer(Socket socket, int index) {
        this.socket = socket;
        this.index = index;
    }

    @Override
    public void run() {
        try {
            //Step 3.2: Attend client's request
            System.out.println("----------------------------------------");
            System.out.println("Thread-" + index + " serves connection from " + socket.getInetAddress() + ":" + socket.getPort());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String val = in.readLine() ;
            while (val != null) {
                long toCalculate = Long.parseLong(val);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Thread-" + index + " is checking if " + toCalculate + " is prime");
                boolean f = isPrime(toCalculate);

                System.out.println("Thread-" + index + " is sending result");
                out.println("" + f);
                System.out.println("----------------------------------------");
                val = in.readLine();
            }
            
            //Step 3.3: Disconnect when finish
            
            socket.shutdownOutput(); // Sends the 'FIN' on the network
            socket.getOutputStream().close();
            socket.getInputStream().close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private boolean isPrime(long n) {
        //check if n is a multiple of 2
        if (n == 1) return false;
        if (n == 2) return true;
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }
}
