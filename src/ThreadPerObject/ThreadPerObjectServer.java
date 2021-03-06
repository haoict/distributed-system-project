/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPerObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadPerObjectServer {
    public static final int CAPACITY = 5;
    public static List<PrimeChecker> available_lst = new ArrayList<PrimeChecker>() {};
    public static List<PrimeChecker> active_lst = new ArrayList<PrimeChecker>() {};
    public static void main(String[] args) {
        try {
            //Step 1: Create Server Socket
            ServerSocket server = new ServerSocket(9999);
            int index = 0;                       
            for (int i = 0; i < CAPACITY; i++) {
                available_lst.add(new PrimeChecker());
            }
            
            while (true) {
                System.out.println("Multithread: Wait for client to connect....");
                //Step 2: Wait for client
                Socket socket = server.accept();

                //Step 3: Create parallel line of execution
                while (available_lst.isEmpty()) {                    
                    System.out.println("Out of service");
                }
                
                System.out.println("a size: " + available_lst.size());
                PrimeChecker primeChecker = available_lst.get(available_lst.size() -1);           
                HandlerServer fh = new HandlerServer(socket, index);
                fh.setPrimeChecker(primeChecker);
                fh.start();
                available_lst.remove(available_lst.size()-1);                
                index++;
                
                System.out.println("**!@!@#!@!#@");
                 
                active_lst.add(primeChecker);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class HandlerServer extends Thread {

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

class PrimeChecker {

    List<Long> primeList = new ArrayList<>();

    public PrimeChecker() {
        primeList.add(2L);
        for (int i = 3; i < 10000; i++) {
            boolean isPrime = true;
            for (int j = 0; j < primeList.size(); j++) {
                if (i % primeList.get(j) == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                primeList.add((long) i);
            }
        }

    }

    public boolean isPrime(long n) {
        if (n == 1) {
            return false;
        }
        if (n == 2) return true;
        for (int i = 0; i < primeList.size(); i++) {
            if (n % primeList.get(i) == 0) {
                return false;
            }
            if (primeList.get(i) * primeList.get(i) > n) {
                return true;
            }
        }
        return true;
    }
}
