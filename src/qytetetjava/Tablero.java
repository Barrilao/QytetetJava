/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qytetetjava;

import java.util.ArrayList;

/**
 *
 * @author srjuan
 */
public class Tablero {
    private ArrayList<Casilla> casillas=new ArrayList();
    private Casilla carcel;

    public Tablero(){
        inicializar();
    }

    Casilla getCarcel() {
        return carcel;
    }
    
    boolean esCasillaCarcel(int numeroCasilla) { 
        if(numeroCasilla==carcel.getNumeroCasilla())
            return true;
         
        else
            return false;
    }
    
    public Casilla obtenerCasillaNumero(int numeroCasilla) { 
       return casillas.get(numeroCasilla);
    }
    
    Casilla obtenerNuevaCasilla(Casilla casilla, int desplazamiento) { 
        int nuevaPosicion= (casilla.getNumeroCasilla()+desplazamiento)%20;
        return casillas.get(nuevaPosicion);
    }

    @Override 
    public String toString() {
        String s="Tablero{" + "casillas= ";
        for (Casilla casilla : casillas) {
            s += casilla.toString() + "\n";
        }
        s += '}';
        return s;
    }
    private void inicializar(){  
       this.casillas.add(new OtraCasilla(0,TipoCasilla.SALIDA));       //Titulos de propiedad
       this.casillas.add(new Calle(1,300,new TituloPropiedad("Gnomeregan",0,0,0,0)));
       this.casillas.add(new Calle(2,300,new TituloPropiedad("Isla de Eco",0,0,0,0)));
       this.casillas.add(new OtraCasilla(3,TipoCasilla.SORPRESA));
       this.casillas.add(new Calle(4,500,new TituloPropiedad("El Exodar",0,0,0,0)));
       this.casillas.add(new OtraCasilla(5,TipoCasilla.CARCEL));
       this.carcel=casillas.get(5);
       this.casillas.add(new Calle(6,700,new TituloPropiedad("Lunargenta",0,0,0,0)));
       this.casillas.add(new Calle(7,700,new TituloPropiedad("Darnassus",0,0,0,0)));
       this.casillas.add(new OtraCasilla(8,TipoCasilla.SORPRESA));
       this.casillas.add(new Calle(9,900,new TituloPropiedad("Cima del Trueno",0,0,0,0)));
       this.casillas.add(new OtraCasilla(10,TipoCasilla.PARKING));
       this.casillas.add(new Calle(11,1100,new TituloPropiedad("Forjaz",0,0,0,0)));
       this.casillas.add(new Calle(12,1100,new TituloPropiedad("Entrañas",0,0,0,0)));
       this.casillas.add(new OtraCasilla(13,TipoCasilla.IMPUESTO));
       this.casillas.add(new Calle(14,1300,new TituloPropiedad("Shattrath",0,0,0,0)));
       this.casillas.add(new OtraCasilla(15,TipoCasilla.JUEZ));
       this.casillas.add(new Calle(16,1500,new TituloPropiedad("Ventormenta",0,0,0,0)));
       this.casillas.add(new Calle(17,1500,new TituloPropiedad("Ogrimmar",0,0,0,0)));
       this.casillas.add(new OtraCasilla(18,TipoCasilla.SORPRESA));
       this.casillas.add(new Calle(19,1750,new TituloPropiedad("Dalaran",0,0,0,0)));
    }
    
}
 


