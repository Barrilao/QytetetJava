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
public class Calle extends Casilla {
    
    private TituloPropiedad titulo;
    private int numHoteles = 0;
    private int numCasas = 0;
   
    
    public Calle(int numCasilla, int coste, TituloPropiedad titulo){
        super(numCasilla, coste, TipoCasilla.CALLE);
        setTitulo(titulo);
        this.titulo.setCasilla(this);
        
    }
    
    private void setTitulo(TituloPropiedad titulo) {
        this.titulo = titulo;
    }
    
    public int getNumHoteles() {
        return numHoteles;
    }

    public int getNumCasas() {
        return numCasas;
    }
    public TituloPropiedad getTitulo() {
        return titulo;
    }
    
    public void setNumHoteles(int numHoteles) {
        this.numHoteles = numHoteles;
    }

    public void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }
    TituloPropiedad asignarPropietario(Jugador jugador) { 
        this.titulo.setPropietario(jugador);
        
        return this.titulo;
    }
    
    int calcularValorHipoteca() { 
        int cantidadRecibida;
        int hipotecaBase=  this.titulo.getHipotecaBase();
        cantidadRecibida= hipotecaBase + (int)((this.numCasas*0.5*hipotecaBase)+this.numHoteles*hipotecaBase);
        return cantidadRecibida;
    }
    
    int cancelarHipoteca() { //sin implementar
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    int cobrarAlquiler() { 
        int costeAlquilerBase =  this.titulo.getAlquilerBase();
        int costeAlquiler = costeAlquilerBase + (int) (this.numCasas * 0.5 + this.numHoteles * 2);
        this.titulo.cobrarAlquiler(costeAlquiler);
        return costeAlquiler;
    }
    
    int edificarCasa() { 
        this.setNumCasas(this.numCasas+1);
        return this.titulo.getPrecioEdificar();
    }
    
    int edificarHotel() { 
        this.setNumHoteles(this.numHoteles+1);
        this.setNumCasas(0);
        return this.titulo.getPrecioEdificar();
    }
    
    boolean estaHipotecada() { 
        return this.titulo.isHipotecada();
    }
    
    int getCosteHipotecada() { //sin implementar
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    int getPrecioEdificar() { 
        int costeEdificarCasa = titulo.getPrecioEdificar();
        return costeEdificarCasa;
    }
    
    int hipotecar() { 
        this.titulo.setHipotecada(true);
        return this.calcularValorHipoteca();
    }
    
    int precioTotalComprar() { //sin implementar
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    boolean propietarioEncarcelado() { 
       return titulo.propietarioEncarcelado();
    }
    
    boolean sePuedeEdificarCasa(int factorEspeculador) {
        return this.numCasas < 4*factorEspeculador;
    }
    
    boolean sePuedeEdificarHotel(int factorEspeculador) {
        if(this.numCasas>=4)
            return this.numHoteles < 4*factorEspeculador;
        
        return false;
    }
    boolean tengoPropietario() { 
        return titulo.tengoPropietario();
    }
    
    int venderTitulo() {
        int precioCompra = this.coste + (this.numCasas+this.numHoteles)*this.titulo.getPrecioEdificar();
        int precioVenta = (int)(precioCompra + this.titulo.getFactorRevalorizacion()*precioCompra);
        this.titulo.setPropietario(null);
        this.setNumCasas(0);
        this.setNumHoteles(0);
        
        return precioVenta;
    }
    
    void asignarTituloPropiedad() { //sin implementar
    
    }
    
    @Override
    public String toString() {
        return super.toString() + "Calle{" + "titulo=" + titulo + ", numHoteles=" + numHoteles + ", numCasas=" + numCasas + '}';
    }
}
