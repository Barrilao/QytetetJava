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
public class TituloPropiedad {
    private String nombre;
    private boolean hipotecada = false;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private Casilla casilla = null;
    private Jugador propietario = null;
    

    TituloPropiedad(String nombre, int alquilerBase, 
            float factorRevalorizacion, int hipotecaBase, int precioEdificar) {
        this.nombre = nombre;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Casilla getCasilla() { //mod. auxiliar para poder hacer cuantasCasasHotelesTengo() en Jugador
        return this.casilla;
    }

    boolean isHipotecada() {
        return hipotecada;
    }

    int getAlquilerBase() {
        return alquilerBase;
    }

    double getFactorRevalorizacion() {
        return factorRevalorizacion;
    }

    int getHipotecaBase() {
        return hipotecaBase;
    }
    
     public Jugador getPropietario(){
        return propietario;
    }

    int getPrecioEdificar() {
        return precioEdificar;
    }

    void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }
    
    void setCasilla(Casilla casilla){ 
        this.casilla = casilla;
    }
    
    void setPropietario(Jugador propietario){
        this.propietario = propietario;
    }
    
    void cobrarAlquiler(int coste)  { 
        this.propietario.modificarSaldo(-coste);
    }
    
    boolean propietarioEncarcelado()  { //"sin implementar"
        //throw new UnsupportedOperationException("Sin implementar");
        return this.propietario.isEncarcelado();
    }
    
    boolean tengoPropietario()  {
        return this.propietario != null;
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" + "nombre=" + nombre + ", "
                + "hipotecada=" + hipotecada + ", alquilerBase=" + 
                alquilerBase + ", factorRevalorizacion=" + 
                factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + 
                ", precioEdificar=" + precioEdificar + '}';
    }
    
}
