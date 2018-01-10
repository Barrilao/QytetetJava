/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qytetetjava;

import InterfazTextualQytetet.*;
import java.util.ArrayList;

/**
 *
 * @author srjuan
 */
public class PruebaQytetet {
    private static ControladorQytetet juego = new ControladorQytetet();
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControladorQytetet k= new ControladorQytetet();
        k.inicializacionJuego();
        k.desarrolloJuego();
        
        
        
        // TODO code application logic here
    }
    
}
