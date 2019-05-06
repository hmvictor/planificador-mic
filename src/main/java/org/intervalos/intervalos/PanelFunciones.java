package org.intervalos.intervalos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.text.BreakIterator;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Víctor
 */
public class PanelFunciones extends JPanel {
    private Map<String, List<Funcion>> intervalosPorLugar=new HashMap<>();
    
    private long minMinutes;
    private long maxMinutes;

    public void setIntervalosPorLugar(Map<String, List<Funcion>> intervalosPorLugar) {
        this.intervalosPorLugar = intervalosPorLugar;
        minMinutes=Long.MAX_VALUE;
        maxMinutes=Long.MIN_VALUE;
        for (Map.Entry<String, List<Funcion>> entry : intervalosPorLugar.entrySet()) {
            for (Funcion funcion : entry.getValue()) {
                Intervalo intervalo=funcion.getIntervalo();
                if(intervalo.getEndTime().get(ChronoField.MINUTE_OF_DAY) > maxMinutes) {
                    maxMinutes=intervalo.getEndTime().get(ChronoField.MINUTE_OF_DAY);
                }
                if(intervalo.getStartTime().get(ChronoField.MINUTE_OF_DAY) < minMinutes) {
                    minMinutes=intervalo.getStartTime().get(ChronoField.MINUTE_OF_DAY);
                }
            }
        }
        minMinutes=minMinutes - minMinutes % 30;
        maxMinutes=maxMinutes + (60-maxMinutes % 30);
        SwingUtilities.updateComponentTreeUI(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d=(Graphics2D) g;
        
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int maxWidth=0;
        for (String label : intervalosPorLugar.keySet()) {
            if(fontMetrics.stringWidth(label) > maxWidth) {
                maxWidth=fontMetrics.stringWidth(label);
            }
        }
        int labelWidth=maxWidth+10*2;
        
        
        double scale=(double)getWidth()/((double)(maxMinutes-minMinutes));
        Color[][] colors=new Color[][]{
            new Color[]{new Color(146, 196, 102, 200), new Color(72, 99, 57)}, 
            new Color[]{new Color(140, 172, 209, 200), new Color(40, 63, 76)}, 
            new Color[]{new Color(242, 98, 70, 200), new Color(104, 36, 19)}
        };
        Color currentColor=g2d.getColor();
        Stroke currentStroke = g2d.getStroke();
        
        float dash1[] = {5.0f};
        BasicStroke dashed =
        new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        5.0f, dash1, 0.0f);
        g2d.setStroke(dashed);
        int offsetY=fontMetrics.getHeight()+5*2;
        for(long i=minMinutes; i <= maxMinutes; i+=30) {
            if(i % 60 == 0) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(LocalTime.of((int)i/60%24, (int)i%60).toString(), (float)getScaledX(i, scale, labelWidth), (float)fontMetrics.getHeight());
            }else{
                g2d.setColor(Color.LIGHT_GRAY);
            }
            Line2D.Double axis = new Line2D.Double(getScaledX(i, scale, labelWidth), offsetY, getScaledX(i, scale, labelWidth), getHeight()+offsetY);
            g2d.draw(axis);
        }
        
        
        int y=0;
        g2d.setStroke(currentStroke);
        for (Map.Entry<String, List<Funcion>> entry : intervalosPorLugar.entrySet()) {
            for (Funcion funcion : entry.getValue()) {
                Intervalo intervalo=funcion.getIntervalo();
                Rectangle2D.Double rectangle=getRectangle(intervalo, scale, y, labelWidth, offsetY);
                g2d.setColor(colors[y%colors.length][0]);
                g2d.fill(rectangle);
                g2d.setColor(colors[y%colors.length][1]);
                g2d.draw(rectangle);
                String intervaloString=intervalo.getStartTime().toString()+" - "+intervalo.getEndTime().toString();
                g2d.setColor(Color.BLACK);
                
                LineBreakMeasurer lineBreakMeasurer=new LineBreakMeasurer(new AttributedString(funcion.getPelicula().getNombre()+System.getProperty("line.separator")+intervaloString).getIterator(), BreakIterator.getLineInstance(), g2d.getFontRenderContext());
                TextLayout nextLayout = lineBreakMeasurer.nextLayout(fontMetrics.stringWidth(funcion.getPelicula().getNombre()));
                nextLayout.draw(g2d, (float)(rectangle.getX()+(rectangle.getWidth()-nextLayout.getAdvance())/2), (float)((rectangle.getHeight()-nextLayout.getAscent()*2-nextLayout.getAscent())+rectangle.getY()));
                nextLayout = lineBreakMeasurer.nextLayout(fontMetrics.stringWidth(intervaloString));
                nextLayout.draw(g2d, (float)(rectangle.getX()+(rectangle.getWidth()-nextLayout.getAdvance())/2), (float)((rectangle.getHeight()-nextLayout.getAscent()*2+nextLayout.getAscent())+rectangle.getY()));
            }
            y++;
        }
        
        y=0;
        for (String label : intervalosPorLugar.keySet()) {
            g2d.drawString(label, (labelWidth-fontMetrics.stringWidth(label))/2, y*60+30+fontMetrics.getHeight()/2+offsetY);
            y++;
        }
        
        g2d.setColor(currentColor);
    }

    protected Rectangle2D.Double getRectangle(Intervalo intervalo, double scale, int y, int labelWidth, int offsetY) {
        Rectangle2D.Double rectangle=new Rectangle.Double();
        int height = 60;
        rectangle.setRect(getScaledX(intervalo.getStartTime().get(ChronoField.MINUTE_OF_DAY), scale, labelWidth), y*height+offsetY, getScaledWidth(intervalo.getEndTime().get(ChronoField.MINUTE_OF_DAY)-intervalo.getStartTime().get(ChronoField.MINUTE_OF_DAY), scale), height);
        return rectangle;
    }
    
    public double getScaledX(double sourceX, double scale, int labelWidth) {
        return (sourceX-minMinutes)*scale+labelWidth;
    }
    
    public double getScaledWidth(double sourceWidth, double scale) {
        return sourceWidth*scale;
    }
    
}
