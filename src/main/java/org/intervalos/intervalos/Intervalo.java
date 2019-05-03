package org.intervalos.intervalos;

import java.time.LocalTime;

/**
 *
 * @author Víctor
 */
public class Intervalo {
    private LocalTime startTime;
    private long duration;
    
    public Intervalo start(int hour, int minute) {
        startTime=LocalTime.of(hour, minute);
        return this;
    }
    
    public Intervalo start(LocalTime inicio) {
        startTime=inicio;
        return this;
    }

    public Intervalo duration(int minutes) {
        this.duration=minutes;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    
    public LocalTime getEndTime(){
        return startTime.plusMinutes(duration);
    } 

}
