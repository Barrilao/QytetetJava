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
public class Especulador extends Jugador{
    private int fianza;
    static int FactorEspeculador =2;
    
    public Especulador(Jugador jugador,int fianza){
        super(jugador.getNombre());
        this.fianza=fianza;
    }
    
    @Override
    protected void irACarcel(Casilla casilla){
        boolean noCarcel = this.pagarFianza(this.fianza);
        if(!noCarcel){
            this.setCasillaActual(casilla);
            this.setEncarcelado(true);
        }
    }
    
    private boolean pagarFianza(int cantidad){
        boolean tieneSaldo = false;
        if(this.getSaldo()>this.fianza){
            tieneSaldo = true;
            this.modificarSaldo(-cantidad);
        }
        return tieneSaldo;
    }
    
    @Override
    protected void pagarImpuestos(int cantidad){
        this.modificarSaldo(cantidad/2);
    }
    
    @Override
    protected Especulador convertirme(int fianza) {
        return this;
    }
    
    public int getFianza(){
        return this.fianza;
    }
    
    @Override
    public int getFactorEspeculador() {
        return this.FactorEspeculador;
    }
}
