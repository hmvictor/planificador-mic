package org.intervalos.intervalos;

import java.time.LocalTime;

/**
 *
 * @author Víctor
 */
public class Funcion {
    private Pelicula pelicula;
    private Intervalo intervalo=new Intervalo();
    
    public Funcion() {
        
    }

    public Funcion pelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        return this;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }
    
    public Funcion inicia(int hora, int minuto) {
        intervalo.start(hora, minuto);
        return this;
    }
    
    public Funcion inicia(LocalTime inicio) {
        intervalo.start(inicio);
        return this;
    }

    public Intervalo getIntervalo() {
        return intervalo.duration(pelicula.getDuracion());
    }

}
