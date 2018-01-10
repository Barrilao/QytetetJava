/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qytetetjava;
import java.util.*;

/**
 *
 * @author Barri
 */
public class Dado {
    
    private static final Dado INSTANCE = new Dado();
    
    private Dado() {}
    
    public static Dado getInstance() {
        return INSTANCE;
    }
    
    int tirar() {  
        int numero = (int) (Math.random() * 6) + 1;
        return numero;
    }
    
    @Override
    public String toString() {
        return "Dado{" + '}';
    }
    
  
}
