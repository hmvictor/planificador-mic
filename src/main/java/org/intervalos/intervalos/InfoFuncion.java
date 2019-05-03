package org.intervalos.intervalos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalTime;

/**
 *
 * @author Víctor
 */
public class InfoFuncion {
    @JsonProperty("pelicula")
    private String idPelicula;
    
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime inicio;

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }
    
}
