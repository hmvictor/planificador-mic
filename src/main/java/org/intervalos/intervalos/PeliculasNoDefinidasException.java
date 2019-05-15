/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intervalos.intervalos;

import java.util.Set;

/**
 *
 * @author Víctor
 */
public class PeliculasNoDefinidasException extends RuntimeException {
    private Set<String> peliculasNoEncontradas;

    public PeliculasNoDefinidasException(Set<String> peliculasNoEncontradas) {
        this.peliculasNoEncontradas = peliculasNoEncontradas;
    }

    public Set<String> getPeliculasNoEncontradas() {
        return peliculasNoEncontradas;
    }

}
