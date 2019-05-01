package org.intervalos.intervalos;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.time.LocalTime;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Víctor
 */
@RunWith(Parameterized.class)
public class PanelFuncionesTest {
    private LocalTime time;
    private int duration;
    private Rectangle.Double expectedRectangle;

    public PanelFuncionesTest(LocalTime startTime, int duration, Rectangle2D.Double expectedRectangle) {
        time=startTime;
        this.duration=duration;
        this.expectedRectangle = expectedRectangle;
    }
    
    @Test
    public void deberiaCalcular() {
        PanelFunciones panelFunciones=new PanelFunciones();
        Rectangle.Double rectangle = panelFunciones.getRectangle(new Intervalo().start(time.getHour(), time.getMinute()).duration(duration), 1, 0, 0, 0);
        assertThat(rectangle.getX(), is(expectedRectangle.getX()));
        assertThat(rectangle.getY(), is(expectedRectangle.getY()));
        assertThat(rectangle.getWidth(), is(expectedRectangle.getWidth()));
        assertThat(rectangle.getHeight(), is(expectedRectangle.getHeight()));
    }
    
    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
            {LocalTime.of(12, 45), 0, new Rectangle.Double(12*60+45, 0, 0, 60)},
            {LocalTime.of(0, 40), 0, new Rectangle.Double(40, 0, 0, 60)}
        };
    }
    
}
