package org.intervalos.intervalos.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.intervalos.intervalos.Pelicula;

/**
 *
 * @author Víctor
 */
public class Data2 {
    private Map<String, Pelicula> peliculas;
//    @JsonDeserialize(keyUsing = SingleLocalDateDeserializer.class)
    private Map<LocalDate, Map<String, Map<String, List<LocalTime>>>> programacion;

    public Map<String, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Map<String, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Map<LocalDate, Map<String, Map<String, List<LocalTime>>>> getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Map<LocalDate, Map<String, Map<String, List<LocalTime>>>> programacion) {
        this.programacion = programacion;
    }
    
}
