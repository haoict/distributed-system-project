/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPerObject;

import ThreadPerRequestServer.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hao
 */
public class HandlerServer extends Thread {

    private Socket socket = null;
    private int index = 0;
    private PrimeChecker primeChecker = null;
    //Step 3.1: Receive paramater from master
    public HandlerServer(Socket socket, int index) {
        this.socket = socket;
        this.index = index;
    }

    public void setPrimeChecker(PrimeChecker primeChecker) {
        this.primeChecker = primeChecker;
    }

    @Override
    public void run() {
        try {
            //Step 3.2: Attend client's request
            System.out.println("----------------------------------------");
            System.out.println("Thread-" + index + " serves connection from " + socket.getInetAddress() + ":" + socket.getPort());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String val = in.readLine();
            long toCalculate = Long.parseLong(val);
             
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Thread-" + index + " is checking if " + toCalculate + " is prime");
            boolean f = primeChecker.isPrime(toCalculate);

            System.out.println("Thread-" + index + " is sending result");
            out.println("" + f);
            System.out.println("----------------------------------------");
            
            //Step 3.3: Disconnect when finish
            socket.getOutputStream().close();
            socket.getInputStream().close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }finally{
          ThreadPerObjectServer.available_lst.add(primeChecker);
          ThreadPerObjectServer.active_lst.remove(primeChecker);
        }
    }

    
}
