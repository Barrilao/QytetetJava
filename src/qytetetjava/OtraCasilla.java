/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qytetetjava;

/**
 *
 * @author Barri
 */
public class OtraCasilla extends Casilla{
    public OtraCasilla(int numeroCasilla, TipoCasilla tipo) {
        super(numeroCasilla, tipo);
    }
    
    @Override
    public int getNumHoteles() {    //sin implementar
        throw new UnsupportedOperationException("Sin implementar");
    }

    @Override
    public int getNumCasas() {  //sin implementar
        throw new UnsupportedOperationException("Sin implementar");
    }

    @Override
    public String toString() {
        return super.toString() + "OtraCasilla{" + '}';
    }
}
