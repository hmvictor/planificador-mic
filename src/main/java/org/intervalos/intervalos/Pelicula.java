package org.intervalos.intervalos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Víctor
 */
public class Pelicula {
    private String nombre;
    @JsonProperty
    private int duracionMinutos;

    public Pelicula(String nombre) {
        this.nombre = nombre;
    }

    public Pelicula() {
    }

    public String getNombre() {
        return nombre;
    }
    
    public Pelicula duracion(int minutos) {
        this.duracionMinutos=minutos;
        return this;
    }

    public int getDuracion() {
        return duracionMinutos;
    }
    
    public Funcion funcion(int hora, int minutos) {
        return new Funcion().pelicula(this).inicia(hora, minutos);
    }
    
}
