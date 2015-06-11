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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hao
 */
public class PrimeChecker {

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
