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
public class Jugador {
    private boolean encarcelado = false;
    private String nombre;
    private int saldo = 7500;
    private Casilla casillaActual = null;
    private ArrayList<TituloPropiedad> propiedades=new ArrayList();
    private Sorpresa cartaLibertad = null;
    static int FactorEspeculador = 1;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }
    
    public int getSaldo(){
        return this.saldo;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades() {
        return this.propiedades;
    }
    
    public String getNombre(){
        return this.nombre;
    }

    public Casilla getCasillaActual() {
        return this.casillaActual;
    }
    
    public TituloPropiedad getPropiedad(int pos){
        return this.propiedades.get(pos);
    }
    
    public boolean isEncarcelado() {
        return this.encarcelado;
    }

    public void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    void setCasillaActual(Casilla casilla) {
        this.casillaActual = casilla;
    }

    void setCartaLibertad(Sorpresa carta) {
        this.cartaLibertad = carta;
    }
    
    public boolean tengoPropiedades() {
        return this.propiedades.size()!= 0;
    }
    
    boolean actualizarPosicion(Casilla casilla) { 
        boolean tengoPropietario = false;
        if(casilla.getNumeroCasilla() < casillaActual.getNumeroCasilla() )
            this.modificarSaldo(1000);
        this.setCasillaActual(casilla);
        
        if(casilla.soyEdificable()){
            Calle calle = (Calle)casilla;
            tengoPropietario = calle.tengoPropietario(); 
        
            if(calle.tengoPropietario()){
                encarcelado = calle.propietarioEncarcelado();
            
                if(!encarcelado){
                   int costeAlquiler = calle.cobrarAlquiler();
                   this.modificarSaldo(-costeAlquiler);
                }
            }
        }
        else if(casilla.getTipo() == TipoCasilla.IMPUESTO){
            int coste = casilla.getCoste();
            this.modificarSaldo(-coste);
        }
       return tengoPropietario;
    }
    
    boolean comprarTitulo() { 
        boolean puedoComprar=false;
        if(this.casillaActual.soyEdificable()){
            Calle calle = (Calle)casillaActual;
            boolean tengoPropietario=calle.tengoPropietario();
            if(!tengoPropietario){
                int costeCompra=this.casillaActual.getCoste();
                if(costeCompra<=this.saldo){
                    TituloPropiedad titulo = calle.asignarPropietario(this);
                    this.propiedades.add(titulo);
                    this.modificarSaldo(-costeCompra);
                    puedoComprar = true;
                }
            }
        }
        return puedoComprar;
    }
    
    Sorpresa devolverCartaLibertad() { 
        Sorpresa aux;
        aux = this.cartaLibertad;
        this.cartaLibertad=null;
        return aux;
    }
    
    void irACarcel(Casilla casilla) { 
        this.setCasillaActual(casilla);
        this.setEncarcelado(true);        
    }
    
    void modificarSaldo(int cantidad) { 
        this.saldo+=cantidad;
    }
    
    int obtenerCapital() {      //dudas
        int total=this.saldo;
        for(TituloPropiedad titulo: this.propiedades){
            int valorPropiedad =0;
            valorPropiedad=titulo.getCasilla().getCoste();
            valorPropiedad+=(titulo.getCasilla().getNumCasas()+titulo.getCasilla().getNumHoteles())*titulo.getPrecioEdificar();
            if(titulo.isHipotecada())
                total-=titulo.getHipotecaBase();
            else{
                total+=valorPropiedad;
            }
        }
        return total;
    }
    
    ArrayList<TituloPropiedad> obtenerPropiedadesHipotecadas(boolean hipotecada) {  //dudas
        ArrayList<TituloPropiedad> respuesta = new ArrayList();
        if(hipotecada){
            for(TituloPropiedad titulo: this.propiedades) {
                if(titulo.isHipotecada())
                    respuesta.add(titulo);
            }
        }
        if(!hipotecada){
            for(TituloPropiedad titulo: this.propiedades){
                if(!titulo.isHipotecada())
                    respuesta.add(titulo);
            }
            
        }
            return respuesta;
    }
    
    void pagarCobrarPorCasaYHotel(int cantidad) { 
        int numeroTotal = this.cuantasCasasHotelesTengo();
        this.modificarSaldo(numeroTotal*cantidad);
    }
    
    boolean pagarLibertad(int cantidad) { 
        boolean tengoSaldo = tengoSaldo(cantidad);
        if(tengoSaldo)
            modificarSaldo(-cantidad);
        return tengoSaldo;
    }
    
    boolean puedoEdificarCasa(Casilla casilla) { 
        boolean esMia = this.esDeMipropiedad(casilla);
        Calle calle = (Calle)casilla;
        if(esMia){
            int costeEdificarCasa = calle.getPrecioEdificar();
            boolean tengoSaldo = this.tengoSaldo(costeEdificarCasa);
            return esMia && tengoSaldo;
        }
        return false;
    }
    
    boolean puedoEdificarHotel(Casilla casilla) { 
        boolean esMia = this.esDeMipropiedad(casilla);
        Calle calle = (Calle)casilla;
        if(esMia){
            int costeEdificarHotel = calle.getPrecioEdificar();
            boolean tengoSaldo = this.tengoSaldo(costeEdificarHotel);
            return esMia && tengoSaldo;
        }
        return false;
    }
    
    boolean puedoHipotecar(Casilla casilla) { 
       return this.esDeMipropiedad(casilla);
    }
    
    boolean puedoPagarHipoteca(Casilla casilla) { //sin implementar
        throw new UnsupportedOperationException("Sin implementar");
    }
    
    boolean puedoVenderPropiedad(Casilla casilla) {
        Calle calle = (Calle)casilla;
        return this.esDeMipropiedad(casilla) && !calle.getTitulo().isHipotecada();
    }
    
    boolean tengoCartaLibertad() { 
        return this.cartaLibertad!=null ? true:false;
    }
    
    void venderPropiedad(Casilla casilla) {
        Calle calle = (Calle)casilla;
        int precioVenta = calle.venderTitulo();
        this.modificarSaldo(precioVenta);
        this.eliminarDeMisPropiedades(casilla);
    }
    
    private int cuantasCasasHotelesTengo() {      
        int suma=0;
        for(TituloPropiedad titulo: this.propiedades) {
            suma+=titulo.getCasilla().getNumCasas()+titulo.getCasilla().getNumHoteles();
        }
        return suma;
    }
    
    private void eliminarDeMisPropiedades(Casilla casilla) {
        Calle calle = (Calle)casilla;
        this.propiedades.remove(calle.getTitulo());
    }
    
    private boolean esDeMipropiedad(Casilla casilla) { 
        boolean aux=false;
         Calle calle = (Calle)casilla;
        for(TituloPropiedad titulo: this.propiedades){
            if(calle.getTitulo()==titulo)
                aux=true;
        }
        return aux;
    }
    
    private boolean tengoSaldo(int cantidad) { 
        return this.saldo>=cantidad;
    }
    
    protected void pagarImpuestos(int cantidad){
        this.modificarSaldo(-cantidad);
    }
    
    protected Especulador convertirme(int fianza){
        Especulador nuevoEspeculador =  new Especulador(this, fianza);
        for(TituloPropiedad propiedad: nuevoEspeculador.getPropiedades())
            propiedad.setPropietario(this);
        
        return nuevoEspeculador;
    }
    
    public int getFactorEspeculador() {
        return this.FactorEspeculador;
    }
    
    @Override
    public String toString() {
        return "Jugador{" + "encarcelado=" + encarcelado + ", nombre=" + nombre + ", saldo=" + saldo + ", casillaActual=" + casillaActual + ", cartaLibertad=" + cartaLibertad + ", propiedades=" + propiedades + '}';
    }

     
}
