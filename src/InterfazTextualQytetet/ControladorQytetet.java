/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazTextualQytetet;
import java.util.ArrayList;
import qytetetjava.*;


/**
 *
 * @author Barri
 */
public class ControladorQytetet {
    private Qytetet qytetet = Qytetet.getInstance();
    private Jugador jugadorActual;
    private Casilla casillaActual;
    private VistaTextualQytetet vista = new VistaTextualQytetet();
    
    public void inicializacionJuego(){
        ArrayList<String> nombres = new ArrayList();
        this.qytetet = Qytetet.getInstance();
        nombres = this.vista.obtenerNombreJugadores();
        this.qytetet.inicializarJuego(nombres);
        this.jugadorActual = this.qytetet.getJugadorActual();
        this.casillaActual = this.jugadorActual.getCasillaActual();
        
        System.out.print(qytetet.toString());
        
    }
    
    public void desarrolloJuego(){
        while(this.jugadorActual.getSaldo() >0){
            boolean libre= false;
            System.out.println("\n\nTurno del jugador: " + this.jugadorActual.getNombre() + "\n");
            System.out.println("Está situado en la casilla: " + this.casillaActual + "\n");
            if(this.jugadorActual.isEncarcelado()){
                System.out.println("El jugador "+ this.jugadorActual.getNombre() + " se encuentra en las Mazmorras de Ventormenta.\n"); 
                int tipoSalida = this.vista.menuSalirCarcel();
                if(tipoSalida == 0)
                    libre=this.qytetet.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
                else
                    libre=this.qytetet.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
                if(libre){
                    this.qytetet.getJugadorActual().setEncarcelado(false);
                    this.jugadorActual = this.qytetet.getJugadorActual();
                    System.out.println("Por esta vez te libras jugador: " + this.jugadorActual.getNombre() + "\n");
                }
                else
                    System.out.println("Gusano miserable, no te escaparás " + this.jugadorActual.getNombre() + " vas a pasar a la sombra un largo tiempo JAJAJAJA\n");
            }
            if(!this.jugadorActual.isEncarcelado()){
                System.out.println("Tiras el dado\n");
                boolean tienePropietario=this.qytetet.jugar();
                this.jugadorActual = this.qytetet.getJugadorActual();
                this.casillaActual=this.jugadorActual.getCasillaActual();
                System.out.println("Avanzas hasta la casilla: " + this.casillaActual + "\n");
                if(this.jugadorActual.getSaldo() >0){
                    if(!this.jugadorActual.isEncarcelado()){
                        if(this.casillaActual.getTipo()==TipoCasilla.SORPRESA){
                            System.out.println("Has caido en una casilla sorpresa: " + this.qytetet.getCartaActual() + "\n");
                            tienePropietario = this.qytetet.aplicarSorpresa();    //creo que no cambia a la siguiente carta
                            System.out.println("Se acaba de aplicar la sorpresa :D\n");
                            this.jugadorActual = this.qytetet.getJugadorActual();
                            this.casillaActual = this.jugadorActual.getCasillaActual();
                            if(!this.jugadorActual.isEncarcelado()&&this.jugadorActual.getSaldo() >0){
                                if(this.casillaActual.getTipo()==TipoCasilla.CALLE){
                                    System.out.println("Estas situado en la casilla: " + this.casillaActual + "\n");
                                    if(!tienePropietario){
                                        int elegirQuieroComprar = this.vista.elegirQuieroComprar();
                                        if(elegirQuieroComprar==0){
                                            this.qytetet.comprarTituloPropiedad();
                                            this.jugadorActual = this.qytetet.getJugadorActual();
                                            this.casillaActual = this.jugadorActual.getCasillaActual();
                                            System.out.println("Felicidades, has obtenido la propiedad\n");
                                        }
                                    }
                                }
                            }     
                        }
                        else if(this.casillaActual.getTipo()==TipoCasilla.CALLE){
                            if(!tienePropietario){
                                int elegirQuieroComprar = this.vista.elegirQuieroComprar();
                                if(elegirQuieroComprar==0){
                                    this.qytetet.comprarTituloPropiedad();
                                    this.jugadorActual = this.qytetet.getJugadorActual();
                                    this.casillaActual = this.jugadorActual.getCasillaActual();
                                    System.out.println("Felicidades, has obtenido la propiedad\n");
                                }
                            } 
                        }
                        if(!this.jugadorActual.isEncarcelado()&&this.jugadorActual.getSaldo() >0&&this.jugadorActual.tengoPropiedades()){
                            ArrayList<String> nombrePropiedades = new ArrayList();
                            for (TituloPropiedad p: this.jugadorActual.getPropiedades())
                                nombrePropiedades.add(p.getNombre());
                            int ncasilla = this.vista.menuElegirPropiedad(nombrePropiedades);
                            Casilla casilla = this.jugadorActual.getPropiedad(ncasilla).getCasilla();
                            int opcion = 1;
                            while(opcion!=0&&this.jugadorActual.tengoPropiedades()){
                                opcion = this.vista.menuGestionInmobiliaria();
                                if(opcion==1){
                                    this.qytetet.edificarCasa(casilla);
                                     System.out.println("El numero de casas es " + casilla.getNumCasas() + "\n" );
                                }
                                else if(opcion==2){
                                    this.qytetet.edificarHotel(casilla);
                                    System.out.println("El numero de hoteles es " + casilla.getNumHoteles()+ "\n");
                                }
                                else if(opcion==3)
                                    this.qytetet.venderPropiedad(casilla);
                                else if(opcion==4)
                                    this.qytetet.hipotecarPropiedad(casilla);
                                else if(opcion==5)
                                    this.qytetet.cancelarHipoteca(casilla);
                                this.jugadorActual = this.qytetet.getJugadorActual();
                                this.casillaActual = this.jugadorActual.getCasillaActual();
                                System.out.println("HECHO\n");
                            }
                        }
                    }    
                }
            }
        
            if(this.jugadorActual.getSaldo() >0){
                this.qytetet.siguienteJugador();
                this.jugadorActual = this.qytetet.getJugadorActual();
                this.casillaActual = this.jugadorActual.getCasillaActual();
            }
        }
        System.out.println("FIN DEL JUEGO\n");
        this.qytetet.obtenerRanking();
    }
    
    /*private Casilla elegirPropiedad(ArrayList<Casilla> propiedades){
        throw new UnsupportedOperationException("Sin implementar");
    }
    */
    
}

