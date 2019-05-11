package org.intervalos.intervalos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import org.intervalos.intervalos.data.Data;
import org.intervalos.intervalos.data.Data2;
import org.intervalos.intervalos.data.InfoFuncion;
import org.intervalos.intervalos.data.SingleLocalDateDeserializer;
import org.intervalos.intervalos.data.SingleLocalTimeDeserializer;

/**
 *
 * @author Víctor
 */
public class FuncionesFrame extends javax.swing.JFrame {
    private Data2 data;

    /**
     * Creates new form FuncionesFrame
     */
    public FuncionesFrame() {
        initComponents();
    }
    
    public void open(File file) throws IOException {
        new LoaderData(file).execute();
    }

    public void setData(Data2 data) {
        this.data = data;
        Set<LocalDate> fechas = data.getProgramacion().keySet();
        datePicker.getMonthView().setLowerBound(Date.from(Collections.min(fechas).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        datePicker.getMonthView().setUpperBound(Date.from(Collections.max(fechas).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        datePicker.getMonthView().setFirstDisplayedDay(datePicker.getMonthView().getLowerBound());
    }
    
    public class LoaderData extends SwingWorker<Data2, Object> {
        private File file;

        public LoaderData(File file) {
            this.file = file;
        }
        
        @Override
        protected Data2 doInBackground() throws Exception {
            try(FileInputStream input=new FileInputStream(file)) {
                /* Parsear json */
                ObjectMapper objectMapper=new ObjectMapper();
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addDeserializer(LocalTime.class, new SingleLocalTimeDeserializer());
                simpleModule.addKeyDeserializer(LocalDate.class, new SingleLocalDateDeserializer());
                objectMapper.registerModule(simpleModule);
                Data2 data = objectMapper.readValue(input, Data2.class);
                return data;
            }
        }

        @Override
        protected void done() {
            try {
                setData(get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(FuncionesFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public class Loader extends SwingWorker<Map<String, List<Funcion>>, Object> {
        private File file;

        public Loader(File file) {
            this.file = file;
        }
        
        @Override
        protected Map<String, List<Funcion>> doInBackground() throws Exception {
            Map<String, List<Funcion>> funcionesPorLugar=new LinkedHashMap<>();
            try(FileInputStream input=new FileInputStream(file)) {
                /* Parsear json */
                ObjectMapper objectMapper=new ObjectMapper();
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addDeserializer(LocalTime.class, new SingleLocalTimeDeserializer());
                objectMapper.registerModule(simpleModule);
                Data data = objectMapper.readValue(input, Data.class);
                for (Map.Entry<String, List<InfoFuncion>> entry : data.getFunciones().entrySet()) {
                    for (InfoFuncion infoFuncion : entry.getValue()) {
                        Funcion funcion=new Funcion()
                            .pelicula(data.getPeliculas().get(infoFuncion.getIdPelicula()))
                            .inicia(infoFuncion.getInicio());
                        if(!funcionesPorLugar.containsKey(entry.getKey())) {
                            funcionesPorLugar.put(entry.getKey(), new LinkedList<>());
                        }
                        funcionesPorLugar.get(entry.getKey()).add(funcion);
                    }
                }
                return funcionesPorLugar;
            }
        }

        @Override
        protected void done() {
            try {
                panelFunciones.setIntervalosPorLugar(get());
            } catch (InterruptedException ex) {
                Logger.getLogger(FuncionesFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(FuncionesFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        panelFunciones = new org.intervalos.intervalos.PanelFunciones();
        jButton2 = new javax.swing.JButton();
        labelFecha = new javax.swing.JLabel();
        datePicker = new org.jdesktop.swingx.JXDatePicker();

        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "JSON File";
            }

        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Funciones");

        panelFunciones.setBackground(new java.awt.Color(255, 255, 255));
        panelFunciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelFuncionesLayout = new javax.swing.GroupLayout(panelFunciones);
        panelFunciones.setLayout(panelFuncionesLayout);
        panelFuncionesLayout.setHorizontalGroup(
            panelFuncionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFuncionesLayout.setVerticalGroup(
            panelFuncionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );

        jButton2.setText("Load Data");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        labelFecha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFecha.setText("Fecha");

        datePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datePickerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFunciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(datePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(labelFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFunciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                open(fileChooser.getSelectedFile());
            } catch (IOException ex) {
                Logger.getLogger(FuncionesFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void datePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datePickerActionPerformed
        Date input = datePicker.getDate();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Map<String, Map<String, List<LocalTime>>> selected = data.getProgramacion().get(date);
        Map<String, List<Funcion>> funcionesPorSala=new HashMap<>();
        for (Map.Entry<String, Map<String, List<LocalTime>>> funcionesPorPeliculaPorLugar : selected.entrySet()) {
            List<Funcion> funciones=new LinkedList<>();
            for (Map.Entry<String, List<LocalTime>> funcionesPorPelicula : funcionesPorPeliculaPorLugar.getValue().entrySet()) {
                for (LocalTime inicio : funcionesPorPelicula.getValue()) {
                    Pelicula pelicula = data.getPeliculas().get(funcionesPorPelicula.getKey());
                    if(pelicula == null) {
//                        throw new IllegalArgumentException(String.format("No existe tal película: %s", funcionesPorPelicula.getKey()));
                        JOptionPane.showMessageDialog(this, String.format("No existe tal película: %s", funcionesPorPelicula.getKey()), "Atención", JOptionPane.ERROR_MESSAGE);
                    }
                    funciones.add(new Funcion().pelicula(pelicula).inicia(inicio));
                }
            }
            funcionesPorSala.put(funcionesPorPeliculaPorLugar.getKey(), funciones);
        }
        panelFunciones.setIntervalosPorLugar(funcionesPorSala);
        labelFecha.setText(date.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")));
    }//GEN-LAST:event_datePickerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FuncionesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FuncionesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FuncionesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FuncionesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FuncionesFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker datePicker;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel labelFecha;
    private org.intervalos.intervalos.PanelFunciones panelFunciones;
    // End of variables declaration//GEN-END:variables
}
