package org.intervalos.intervalos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Víctor
 */
public class Data {
    private Map<String, Pelicula> peliculas=new HashMap<>();
    
//    @JsonDeserialize(using = FuncionSerializer.class)
    private Map<String, List<InfoFuncion>> funciones=new HashMap<>();

    public Map<String, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Map<String, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Map<String, List<InfoFuncion>> getFunciones() {
        return funciones;
    }

    public void setFunciones(Map<String, List<InfoFuncion>> funciones) {
        this.funciones = funciones;
    }

}
