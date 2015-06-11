/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author Hao
 */
public class ThreadPerRequestiClient {

    public static void main(String[] args) {
        Thread[] t = new Thread[5];

        for (int i = 0; i < t.length; i++) {
            t[i] = new HandlerClient("Thread-" + i, i+4);
            t[i].start();
            try {
                Thread.sleep(1000);
            } 
            catch (InterruptedException ie) {
            }
        }
    }
}
