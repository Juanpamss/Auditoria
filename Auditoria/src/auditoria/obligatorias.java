/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auditoria;

import static auditoria.anomaliasDatos.res;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan Pa
 */
public class obligatorias extends javax.swing.JFrame {

    /**
     * Creates new form obligatorias
     */
    ArrayList<String[]> datosToken;

    Map<String, String> datosMapa = new HashMap<>();
    
    public static ArrayList<String []> datosComparar;

    public static ArrayList<String[]> getDatosComparar() {
        return datosComparar;
    }

    public obligatorias() {
        initComponents();

    }

    public void obligatorias() {

        String query2 = "SELECT \n"
                + "o.Name AS Table_Name, \n"
                + "c.Name AS Field_Name\n"
                + "FROM sys.columns c \n"
                + "INNER JOIN sys.objects o ON o.object_id = c.object_id\n"
                + "LEFT JOIN  sys.types t on t.user_type_id  = c.user_type_id   \n"
                + "WHERE o.type = 'U'\n"
                + "and c.Name LIKE '%id%'\n"
                + "-- and o.Name = 'YourTableName'\n"
                + "ORDER BY o.Name, c.Name";

        String query = "SELECT \n"
                + "o.Name AS Table_Name, \n"
                + "c.Name AS Field_Name\n"
                + "FROM sys.columns c \n"
                + "INNER JOIN sys.objects o ON o.object_id = c.object_id\n"
                + "LEFT JOIN  sys.types t on t.user_type_id  = c.user_type_id   \n"
                + "WHERE o.type = 'U'\n"
                + "-- and o.Name = 'YourTableName'\n"
                + "ORDER BY o.Name, c.Name";

        res = coneccionBDD.baseDatos.consulta(query2);

        try {

            datosToken = new ArrayList<>();
            
            datosComparar = new ArrayList<>();

            while (res.next()) {

                ResultSetMetaData rsmd = res.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                String aux = "";

                for (int i = 1; i <= columnsNumber; i++) {

                    if (i > 1) {
                        //System.out.print(",");
                    }

                    //System.out.print(res.getString(i));
                    if (i == 1) {

                        aux += res.getString(i) + ",";

                    } else if (i == 2) {

                        aux += res.getString(i);

                    }

                    //tokenizar(res.getString(i));
                }

                //System.out.println(aux);
                tokenizar(aux);

                //datos.put(res.getString(1), res.getString(2));
            }

            analizar();

            //System.out.println(this.datosToken.length);
        } catch (Exception e) {
        }

    }

    public void tokenizar(String dato) {

        StringTokenizer tokens = new StringTokenizer(dato, ",");
        int nDatos = tokens.countTokens();
        int i = 0;

        String auxiliar[] = new String[nDatos];

        while (tokens.hasMoreTokens()) {
            String str = tokens.nextToken();

            auxiliar[i] = str;
            //System.out.println("Arreglo T: " + tokens.nextToken()+tokens.nextToken() );
            i++;
        }

        datosToken.add(auxiliar);

    }

    public void analizar() {

        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        modelo.setRowCount(0);

        //System.out.println(datosToken.size());
        for (int i = 0; i < datosToken.size() - 1; i++) {

            String auxActual[] = datosToken.get(i);

            for (int j = 0; j < datosToken.size() - 1; j++) {

                //String auxActual [] = datosToken.get(i);
                String auxSiguiente[] = datosToken.get(j + 1);

                if (auxActual[0] != (auxSiguiente[0]) && auxActual[1].equals(auxSiguiente[1])) {

                    /*System.out.println("Primero: " + auxActual[1]);
                    System.out.println("Segundo: " + auxSiguiente[1]);*/
                    //System.out.println("Integridad en: " + auxActual[0] + " Con: " + auxSiguiente[0]);

                    Object rowData[] = new Object[4];

                    rowData[0] = auxActual[0];
                    rowData[1] = auxSiguiente[0];
                    rowData[2] = auxActual[1];
                    rowData[3] = "FOREIGN_KEY_CONSTRAINT";
                    
                    String [] auxComparar = new String[3];
                    
                    auxComparar[0] = auxActual[0];
                    auxComparar[1] = auxSiguiente[0];
                    auxComparar[2] = rowData[3].toString();
                    
                    datosComparar.add(auxComparar);

                    modelo.addRow(rowData);

                } else if(auxActual[0] == (auxSiguiente[0]) && auxActual[1].equals(auxSiguiente[1])){

                    Object rowData[] = new Object[4];

                    rowData[0] = auxActual[0];
                    rowData[1] = auxSiguiente[0];
                    rowData[2] = auxActual[1];
                    rowData[3] = "PRIMARY_KEY_CONSTRAINT";
                    
                    String [] auxComparar = new String[3];
                    
                    auxComparar[0] = auxActual[0];
                    auxComparar[1] = auxSiguiente[0];
                    auxComparar[2] = rowData[3].toString();
                    
                    datosComparar.add(auxComparar);

                    modelo.addRow(rowData);
                }

            }

        }

        jTable1.setModel(modelo);

        //cargarTabla();
    }

    public void cargarTabla() {

        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        modelo.setRowCount(0);


        jTable1.setModel(modelo);
    }
    
    public void generarReporte(String destino) throws Exception {
        BufferedWriter bfw = new BufferedWriter(new FileWriter(destino + ".txt"));
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            if (i == 2) {
                bfw.write("\t\t\t\t\t\t\t\t\t\t\t\t");
            }
            bfw.write(jTable1.getColumnName(i));
            bfw.write("\t");
        }

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            bfw.newLine();
            for (int j = 0; j < jTable1.getColumnCount(); j++) {

                if (j == 2) {
                    bfw.write("\t\t\t\t\t\t");
                }

                bfw.write((jTable1.getValueAt(i, j)).toString());
                bfw.write("\t");
            }
        }
        bfw.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Analizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tabla 1", "Tabla 2", "Campo", "Tipo de relación"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setText("Debería existir una relación de integridad entre:");

        jButton2.setText("Generar Reporte");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jMenu1.setText("Acciones");

        jMenuItem1.setText("Inicio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jButton1))))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(29, 29, 29))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.obligatorias();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        this.dispose();
        main main = new main();
        main.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        JFileChooser escoger = new JFileChooser();
        
        escoger.setApproveButtonText("Guardar");
        
        escoger.showOpenDialog(null);
        
        File f = escoger.getSelectedFile();
        
        String nombreAchivo = f.getAbsolutePath();
        
        try {
            generarReporte(nombreAchivo);
        } catch (Exception ex) {
            Logger.getLogger(anomaliasDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(obligatorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(obligatorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(obligatorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(obligatorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new obligatorias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
