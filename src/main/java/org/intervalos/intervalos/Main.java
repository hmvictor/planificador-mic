package org.intervalos.intervalos;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Víctor
 */
public class Main {
    
    public static void main(String[] args) {
        List<Intervalo> intervalos=Arrays.asList(
            new Intervalo().start(12,45).duration(142),
            new Intervalo().start(15,45).duration(85),
            new Intervalo().start(17,45).duration(142),
            new Intervalo().start(20,45).duration(85),
            
            new Intervalo().start(12,00).duration(114),
            new Intervalo().start(15,00).duration(110),
            new Intervalo().start(17,30).duration(114),
            new Intervalo().start(20,00).duration(110)
        );
        
        int counter=0;
        for (Intervalo intervalo : intervalos) {
            System.out.printf("%s-%s ", intervalo.getStartTime(), intervalo.getEndTime());
            if(counter%4 == 3) {
                System.out.println("");
            }
            counter++;
        }
        
//        List<Funcion> funciones=Arrays.asList(
//            Funcion.en("Sala2").inicia(12, 45).duracion(142),
//            Funcion.en("Sala2").inicia(15,45).duracion(85),
//            Funcion.en("Sala2").inicia(17,45).duracion(142),
//            Funcion.en("Sala2").inicia(20,45).duracion(85),
//
//            Funcion.en("Sala3").inicia(12,00).duracion(114),
//            Funcion.en("Sala3").inicia(15,00).duracion(110),
//            Funcion.en("Sala3").inicia(17,30).duracion(114),
//            Funcion.en("Sala3").inicia(20,00).duracion(110)
//        );
        
        
        
    }
    
}
