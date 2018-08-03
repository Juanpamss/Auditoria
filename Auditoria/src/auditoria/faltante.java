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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan Pa
 */
public class faltante extends javax.swing.JFrame {

    /**
     * Creates new form faltante
     */
    ArrayList<String[]> datosToken;

    public faltante() {
        initComponents();
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
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Analizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tipo de integridad", "Tabla 1", "Tabla 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );

        jLabel1.setText("Faltan las siguientes relaciones de integridad:");

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
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jLabel1))))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(58, 58, 58))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        this.dispose();
        main main = new main();
        main.setVisible(true);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String queryActuales = "SELECT\n"
                + "OBJECT_NAME(parent_object_id) AS TableName,\n"
                + "type_desc AS ConstraintType\n"
                + "FROM sys.objects\n"
                + "WHERE type_desc LIKE '%CONSTRAINT' and\n"
                + "type_desc not like 'CHECK%' and\n"
                + "type_desc not like 'DEFAULT%'";

        /*String queryForaneas = "SELECT  \n"
                + "    tab1.name AS [table],\n"
                + "	REPLACE(obj.name,obj.name, 'FOREIGN_KEY_CONSTRAINT')\n"
                + "FROM sys.foreign_key_columns fkc\n"
                + "INNER JOIN sys.objects obj\n"
                + "    ON obj.object_id = fkc.constraint_object_id\n"
                + "INNER JOIN sys.tables tab1\n"
                + "    ON tab1.object_id = fkc.parent_object_id\n"
                + "INNER JOIN sys.schemas sch\n"
                + "    ON tab1.schema_id = sch.schema_id\n"
                + "INNER JOIN sys.columns col1\n"
                + "    ON col1.column_id = parent_column_id AND col1.object_id = tab1.object_id\n"
                + "INNER JOIN sys.tables tab2\n"
                + "    ON tab2.object_id = fkc.referenced_object_id\n"
                + "INNER JOIN sys.columns col2\n"
                + "    ON col2.column_id = referenced_column_id AND col2.object_id = tab2.object_id";*/
        res = coneccionBDD.baseDatos.consulta(queryActuales);

        try {

            datosToken = new ArrayList<>();

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

        } catch (Exception e) {
        }

        imprimir();

        //analizar();
    }

    public void imprimir() {

        /*System.out.println("\nDEBERIAN: \n");

        int i = 0;
        for (String[] dato : obligatorias.datosComparar) {
            System.out.println(i);
            for (String dat : dato) {
                System.out.println(dat);
            }
            i++;

        }*/
        //System.out.println("\nACTUALES: \n");
        //System.out.println(this.datosToken.size());
        /*int j = 0;
        for (String[] dato : datosToken) {
            System.out.println(j);
            for (String dat : dato) {
                System.out.println(dat);
            }
            j++;

        }*/
        analizar();

    }

    public void analizar() {

        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

        modelo.setRowCount(0);

        System.out.println(datosToken.size());

        boolean hay = false;

        for (int i = 0; i < obligatorias.datosComparar.size(); i++) {

            String auxActualComparar[] = obligatorias.datosComparar.get(i);

            for (int j = 0; j < datosToken.size(); j++) {

                String auxActualTokens[] = datosToken.get(j);

                /*System.out.println("\nCOMPARAR: \n");
                System.out.println(auxActualComparar[0]);
                System.out.println(auxActualComparar[1]);
                System.out.println(auxActualComparar[2]);

                System.out.println("\nACTUAL: \n");
                System.out.println(auxActualTokens[0]);
                System.out.println(auxActualTokens[1]);*/
                if (auxActualComparar[0].equals(auxActualTokens[0]) && auxActualComparar[2].equals(auxActualTokens[1]) || auxActualComparar[1].equals(auxActualTokens[0]) && auxActualComparar[2].equals(auxActualTokens[1])) {

                    hay = true;

                    break;

                } else {

                }

            }

            if (!hay) {

                System.out.println("No hay: " + auxActualComparar[2] + " en: " + auxActualComparar[0] + " con: " + auxActualComparar[1]);

                Object rowData[] = new Object[3];

                rowData[0] = auxActualComparar[2];
                rowData[1] = auxActualComparar[0];
                rowData[2] = auxActualComparar[1];

                modelo.addRow(rowData);

            }

        }

        jTable1.setModel(modelo);

    }

    public void generarReporte(String destino) throws Exception {
        BufferedWriter bfw = new BufferedWriter(new FileWriter(destino + ".txt"));

        System.out.println(jTable1.getColumnCount());

        bfw.write("LOG");
        bfw.newLine();
        bfw.write("Integridades Referenciales Faltantes");
        bfw.newLine();
        
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            bfw.newLine();

            bfw.write("No hay: " + jTable1.getValueAt(i, 0).toString() + " en: " + jTable1.getValueAt(i, 1).toString() + " con: " + jTable1.getValueAt(i, 2).toString());

        }
        bfw.close();
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


    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(faltante.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(faltante.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(faltante.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(faltante.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new faltante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}