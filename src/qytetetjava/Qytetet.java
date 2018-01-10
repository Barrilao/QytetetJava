/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qytetetjava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/**
 *
 * @author Barri
 */
public class Qytetet {
    
    private static int MAX_JUGADORES = 4;
    private static int MAX_CARTAS = 10;
    private static int MAX_CASILLAS = 20;
    private static int PRECIO_LIBERTAD = 200;
    private static int SALDO_SALIDA = 1000;
    private Dado dado = Dado.getInstance();
    private Sorpresa CartaActual = null;
    private Jugador JugadorActual = null;
    private ArrayList<Sorpresa> mazo = new ArrayList();
    private Tablero tablero = new Tablero();
    private ArrayList<Jugador> jugadores = new ArrayList(); 
    
    private static final Qytetet instance = new Qytetet();
    
    private Qytetet() { }
    
    public static Qytetet getInstance() {
        return instance;
    }

    public Sorpresa getCartaActual() {
        return CartaActual;
    }

    public Jugador getJugadorActual() {
        return JugadorActual;
    }
    
    public boolean aplicarSorpresa(){ 
        boolean tienePropietario = false;
        if(this.CartaActual.getTipo()==TipoSorpresa.PAGARCOBRAR)
           this.JugadorActual.modificarSaldo(this.CartaActual.getValor());
        else if(this.CartaActual.getTipo()==TipoSorpresa.IRACASILLA){
            boolean esCarcel = this.tablero.esCasillaCarcel(this.CartaActual.getValor());
            if(esCarcel)
                this.encarcelarJugador();
            else{
                Casilla nuevaCasilla = this.tablero.obtenerCasillaNumero(this.CartaActual.getValor());
                tienePropietario = this.JugadorActual.actualizarPosicion(nuevaCasilla);
            }
        }
        else if(this.CartaActual.getTipo()==TipoSorpresa.PORCASAHOTEL)
            this.JugadorActual.pagarCobrarPorCasaYHotel(this.CartaActual.getValor());
        else if(this.CartaActual.getTipo()==TipoSorpresa.PORJUGADOR){
            for (Jugador jugador: jugadores) {
                if (jugador != this.JugadorActual) {
                    int cantidad = this.CartaActual.getValor();
                    jugador.modificarSaldo(cantidad);
                    this.JugadorActual.modificarSaldo(-cantidad);
                }
            }
        }
        if(this.CartaActual.getTipo()==TipoSorpresa.SALIRCARCEL)
            this.JugadorActual.setCartaLibertad(this.CartaActual);
        else
            this.mazo.add(this.CartaActual);
        return tienePropietario;  
    }
    
    public boolean cancelarHipoteca(Casilla casilla) { 
        Calle calle = (Calle)casilla;
        if(calle.estaHipotecada()){
            calle.getTitulo().setHipotecada(false);
            return true;
        }
        
        return false;
    }
    
    public boolean comprarTituloPropiedad() { 
        return this.JugadorActual.comprarTitulo();
    }
    
    public boolean edificarCasa(Casilla casilla) { 
        boolean puedoEdificar=false;
        if(casilla.soyEdificable()){
            Calle calle = (Calle)casilla;
            boolean sePuedeEdificar = calle.sePuedeEdificarCasa(this.JugadorActual.getFactorEspeculador());
            if(sePuedeEdificar){
                puedoEdificar = this.JugadorActual.puedoEdificarCasa(casilla);
                if(puedoEdificar){
                    int costeEdificarCasa = calle.edificarCasa();
                    this.JugadorActual.modificarSaldo(-costeEdificarCasa);
                }
            }
        }
        return puedoEdificar;
    }
    
    public boolean edificarHotel(Casilla casilla) { 
        boolean puedoEdificar=false;
        if(casilla.soyEdificable()){
            Calle calle = (Calle)casilla;
            boolean sePuedeEdificar = calle.sePuedeEdificarHotel(this.JugadorActual.getFactorEspeculador());
            if(sePuedeEdificar){
                puedoEdificar = this.JugadorActual.puedoEdificarHotel(casilla);
                if(puedoEdificar){
                    int costeEdificarHotel = calle.edificarHotel();
                    this.JugadorActual.modificarSaldo(-costeEdificarHotel);
                }
            }
        }
        return puedoEdificar;
    }
    
    public boolean hipotecarPropiedad(Casilla casilla) { 
        boolean puedoHipotecarPropiedad = false;
        if(casilla.soyEdificable()){
            Calle calle = (Calle)casilla;
            boolean sePuedeHipotecar = !calle.estaHipotecada();
            if(sePuedeHipotecar){
                boolean puedoHipotecar = this.JugadorActual.puedoHipotecar(casilla);
                puedoHipotecarPropiedad=puedoHipotecar;
                if(puedoHipotecar){
                    int cantidadRecibida = calle.hipotecar();
                    this.JugadorActual.modificarSaldo(cantidadRecibida);
                }
            }
        }
        return puedoHipotecarPropiedad;
    }
    
    public void inicializarJuego(ArrayList<String> nombres) { 
        inicializarJugadores(nombres); 
        inicializarCartasSorpresas();
        inicializarTablero();
        salidaJugadores();
        
    }
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo) { 
        boolean libre = false;
        if(metodo==MetodoSalirCarcel.TIRANDODADO){
            int valorDado = this.dado.tirar();
            if(valorDado>5)
                libre = true;
        }
        else if(metodo==MetodoSalirCarcel.PAGANDOLIBERTAD){
            boolean tengoSaldo= this.JugadorActual.pagarLibertad(PRECIO_LIBERTAD);
            libre = tengoSaldo; 
        }
        if(libre)
            this.JugadorActual.setEncarcelado(false);
        return libre;
    }
    
    public boolean jugar() { 
        int valorDado = this.dado.tirar();
        Casilla casillaPosicion = this.JugadorActual.getCasillaActual();
        Casilla nuevaCasilla = this.tablero.obtenerNuevaCasilla(casillaPosicion, valorDado);
        boolean tienePropietario=this.JugadorActual.actualizarPosicion(nuevaCasilla);
        if(!nuevaCasilla.soyEdificable()){
            if(nuevaCasilla.getTipo()==TipoCasilla.JUEZ)
                this.encarcelarJugador();
            else if(nuevaCasilla.getTipo()==TipoCasilla.SORPRESA){
                this.CartaActual = mazo.get(0);
                 mazo.remove(0);
            }
        }
        return tienePropietario;
    }
        
    ArrayList<Casilla> propiedadesHipotecadasJugador(boolean hipotecadas) { 
        ArrayList<Casilla> casillas = new ArrayList();
        
        for(int i = 0; i < JugadorActual.obtenerPropiedadesHipotecadas(hipotecadas).size(); i++){
            casillas.add(JugadorActual.obtenerPropiedadesHipotecadas(hipotecadas).get(i).getCasilla());
        }
        
        return casillas;    
    }
    
    public Jugador siguienteJugador() {         
        int numero=0;
        for(Jugador jugador: jugadores){
            if(jugador.equals(JugadorActual)){
                if(numero==jugadores.size()-1){
                    JugadorActual =jugadores.get(0);
                    return JugadorActual;
                }
                else {
                    JugadorActual=jugadores.get(numero+1);
                    return JugadorActual;
                }
            }
            numero++;
        }
        return JugadorActual;
    }
    
    public boolean venderPropiedad(Casilla casilla) { 
        Calle calle = (Calle)casilla;
        boolean puedoVender = casilla.soyEdificable()&& this.JugadorActual.puedoVenderPropiedad(casilla)&&!calle.estaHipotecada();
        if(puedoVender)
            this.JugadorActual.venderPropiedad(casilla);
        
        return puedoVender;
    }
    
    void encarcelarJugador() {
        if(!this.JugadorActual.tengoCartaLibertad()){
            Casilla casillaCarcel = this.tablero.getCarcel();
            this.JugadorActual.irACarcel(casillaCarcel);
        }
        else{
            Sorpresa carta = this.JugadorActual.devolverCartaLibertad();
            mazo.add(carta);
        }
        
    }
    
    void inicializarCartasSorpresas() {   
        this.mazo.add(new Sorpresa ("EH, ¿QUE HACES ENTRANDO A LOS BAJOS FONDOS??,"
                + " por tu osadía te enviaremos a la cárcel", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        this.mazo.add(new Sorpresa ("Te libras de esta sólo porque Lady Sylvanas necesita tus servicios, sal de la cárcel",
                0, TipoSorpresa.SALIRCARCEL));
        this.mazo.add(new Sorpresa ("Recibes un correo de Jastor Gallywix que dice: HOOOOLA AMIGO MÍO, gracias por completar el encargo de "
                + "suministros de Explosivos Cierrasílex que te mandé, como agradecimiento te hago entrega de tu recompensa 1000$",
                1000,TipoSorpresa.PAGARCOBRAR));
        this.mazo.add(new Sorpresa ("EH, CAMPESINO, VENIMOS A POR NUESTROS IMPUESTOS, LA GUERRA DE CUENCA DE ARATHI NO SE VA A GANAR SIN "
                + "ESOS 600$",600,TipoSorpresa.PAGARCOBRAR));
        this.mazo.add(new Sorpresa ("Bienvenido a portales Khadgar, atraviese ese portal y viajará directamente a la puerta de la cárcel "
                ,5,TipoSorpresa.IRACASILLA));
        this.mazo.add(new Sorpresa ("Usas tu Piedra Hogar para volver a donde empezaste, ve a la casilla de SALIDA y cobrá los 1000$",0,
                TipoSorpresa.IRACASILLA));
        this.mazo.add(new Sorpresa ("Ya se está acercando La Legión Ardiente, debes reforzar todos tus edificios pagando 125$ por"
                + " cada uno",125,TipoSorpresa.PORCASAHOTEL));
        this.mazo.add(new Sorpresa ("La ciudad de Ventormenta agradece tu construcción de edificios después del Cataclismo, recibe"
                + " 100$ por cada uno de tus edificios",100,TipoSorpresa.PORCASAHOTEL));
        this.mazo.add(new Sorpresa("Ya estamos en el evento de La Semana de los Niños, sabemos que eres una persona solidaria y donas"
                + " 150$ a cada uno de los huerfanos(jugadores)",150,TipoSorpresa.PORJUGADOR));
        this.mazo.add(new Sorpresa("Tus campeones han vuelto de sus misiones, recibes 200$ de cada uno de ellos(jugadores)",
                200,TipoSorpresa.PORJUGADOR));
        this.mazo.add(new Sorpresa("El rey Anduin Wrynn ha escuchado sobre tus hazañas, por lo que te convertirás en especulador", 
                5000, TipoSorpresa.CONVERTIRME));
        this.mazo.add(new Sorpresa("La Jefa de Guerra Sylvanas Brisaveloz te recompensa con el título de Especulador por tus logros en la "
                + "batalla del Valle de Alterac", 3000, TipoSorpresa.CONVERTIRME));
        
        
        Collections.shuffle(this.mazo);
        
        
    }
    
    void inicializarJugadores(ArrayList<String> names) {
        for(String name: names) {
            this.jugadores.add(new Jugador(name));
        }
    }
    
    void inicializarTablero() {
        this.tablero = new Tablero();
    }
    
    void salidaJugadores() { 
        for(Jugador jugador: this.jugadores) {
            jugador.setCasillaActual(tablero.obtenerCasillaNumero(0)); //el saldo no hace falta modificarlo a 7500 ya que se inicializa a 7500
        }
        int aux = (int) (Math.random()*this.jugadores.size());
        JugadorActual=this.jugadores.get(aux);
    }
    
    ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    public Map<String, Integer> obtenerRanking() {
        LinkedHashMap<String, Integer> ranking =
            new LinkedHashMap<>(jugadores.size());
        ArrayList<Jugador> lista = new ArrayList<>(jugadores);

        // Ordena la lista y añade los valores al diccionario "ranking".
        lista.sort((p1, p2) -> p2.obtenerCapital() - p1.obtenerCapital());
        lista.forEach((j) -> ranking.put(j.getNombre(), j.obtenerCapital()));

        return ranking;
  }
   @Override
    public String toString() {
        return "Qytetet{" + "dado=" + dado + ", tablero=" + tablero + ", jugadorActual=" + JugadorActual + ", jugadores=" + jugadores + ", mazo=" + mazo + ", cartaActual=" + CartaActual + '}';
    }
}
