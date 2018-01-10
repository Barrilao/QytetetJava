/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qytetetjava;

/**
 *
 * @author srjuan
 */
public abstract class Casilla {
    protected int numeroCasilla;
    protected int coste;
    protected TipoCasilla tipo;

    public Casilla(int numeroCasilla, int coste, TipoCasilla tipo){
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
        this.tipo = tipo;

    }

    public Casilla(int numeroCasilla, TipoCasilla tipo) {
        this.numeroCasilla = numeroCasilla;
        this.tipo = tipo;
    }

    public int getNumeroCasilla() {
        return numeroCasilla;
    }

    public int getCoste() {
        return coste;
    }

    public TipoCasilla getTipo() {
        return tipo;
    }
    
    boolean soyEdificable() {   
        return this.tipo==TipoCasilla.CALLE;
    }
    
    public void setCoste(int coste) {
        this.coste = coste;
    }
    
    //estos dos no se si son correctos
    public abstract int getNumHoteles();

    public abstract int getNumCasas();

 @Override
    public String toString() {
        
        return  "Casilla{" + "numeroCasilla=" + numeroCasilla + ", coste=" + coste + ", tipo=" + tipo +  '}';
    }
}

