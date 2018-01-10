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
public class Sorpresa {
    private String texto;
    private int valor;
    private TipoSorpresa tipo;
    Sorpresa(String t, int v, TipoSorpresa ts){
        this.texto=t;
        this.valor=v;
        this.tipo=ts;
    }

    String getTexto() {
        return texto;
    }

    int getValor() {
        return valor;
    }

    TipoSorpresa getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Sorpresa{" + "texto=" + texto + ", valor=" + valor + ", tipo=" 
                + tipo + '}';
    }
    
}
