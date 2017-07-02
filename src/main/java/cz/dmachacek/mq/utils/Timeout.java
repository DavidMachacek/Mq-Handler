/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.utils;

/**
 *
 * @author dmachace
 */
public final class Timeout {
    
    public static void timeout() {
        try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
    }
    
    private Timeout() {
        throw new UnsupportedOperationException("Utility class!");
    }
    
}
