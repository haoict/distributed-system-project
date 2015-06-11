/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPerObject;

import ThreadPerRequestServer.*;
import java.io.IOException;
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
