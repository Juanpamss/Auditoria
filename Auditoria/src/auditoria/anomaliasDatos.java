/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auditoria;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan Pa
 */
public class anomaliasDatos extends javax.swing.JFrame {

    
    static ResultSet res;
    /**
     * Creates new form anomaliasDatos
     */
    public anomaliasDatos() {
        initComponents();
    }
    
    public void cargarTabla(String query){
    
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        
        res = coneccionBDD.baseDatos.consulta(query);
        
        try {
            
            while(res.next()){
            
                Vector v = new Vector();
                
                v.add(res.getInt(1));
                v.add(res.getString(2));
                v.add(res.getString(3));
                
                modelo.addRow(v);
                
                jTable1.getColumnModel().getColumn(0).setPreferredWidth(001);
                jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
                jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
                
                jTable1.setModel(modelo);
                
            }
            
        } catch (Exception e) {
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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

        jButton2.setText("Generar Reporte");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Error", "Mensaje", "TimeStamp"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jMenu1.setText("Acciones");

        jMenuItem1.setText("Inicio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)))
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(23, 23, 23))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String queryCrearTabla = "DROP TABLE IF EXISTS [dbo].[dbcc_history]\n" +
"\n" +
"CREATE TABLE [dbo].[dbcc_history](\n" +
"[Error] [int] NULL,\n" +
"[Level] [int] NULL,\n" +
"[State] [int] NULL,\n" +
"[MessageText] [varchar](7000) NULL,\n" +
"[RepairLevel] [int] NULL,\n" +
"[Status] [int] NULL,\n" +
"[DbId] [int] NULL,\n" +
"[DbFragId] [int] NULL,\n" +
"[ObjectId] [int] NULL,\n" +
"[IndexId] [int] NULL,\n" +
"[PartitionID] [int] NULL,\n" +
"[AllocUnitID] [int] NULL,\n" +
"[RidDbId] [int] NULL,\n" +
"[RidPruId] [int] NULL,\n" +
"[File] [int] NULL,\n" +
"[Page] [int] NULL,\n" +
"[Slot] [int] NULL,\n" +
"[RefDbId] [int] NULL,\n" +
"[RefPruId] [int] NULL,\n" +
"[RefFile] [int] NULL,\n" +
"[RefPage] [int] NULL,\n" +
"[RefSlot] [int] NULL,\n" +
"[Allocation] [int] NULL,\n" +
"[TimeStamp] [datetime] NULL CONSTRAINT [DF_dbcc_history_TimeStamp] DEFAULT (GETDATE())\n" +
") ON [PRIMARY]\n";
        
        String queryDropExists = "drop proc if exists [dbo].[usp_CheckDBIntegrity]";
        
        String queryCrearProc = "CREATE PROC [dbo].[usp_CheckDBIntegrity]\n" +
"@database_name SYSNAME=NULL\n" +
"AS\n" +
"IF @database_name IS NULL -- Run against all databases\n" +
"BEGIN\n" +
"   DECLARE database_cursor CURSOR FOR\n" +
"   SELECT name \n" +
"   FROM sys.databases db\n" +
"   WHERE name NOT IN ('master','model','msdb','tempdb') \n" +
"   AND db.state_desc = 'ONLINE'\n" +
"   AND source_database_id IS NULL -- REAL DBS ONLY (Not Snapshots)\n" +
"   AND is_read_only = 0\n" +
"\n" +
"   OPEN database_cursor\n" +
"   FETCH next FROM database_cursor INTO @database_name\n" +
"   WHILE @@FETCH_STATUS=0\n" +
"   BEGIN\n" +
"\n" +
"      INSERT INTO dbcc_history ([Error], [Level], [State], MessageText, RepairLevel, [Status], \n" +
"      [DbId], DbFragId, ObjectId, IndexId, PartitionId, AllocUnitId, RidDbId, RidPruId, [File], Page, Slot, \n" +
"      RefDbId, RefPruId, RefFile, RefPage, RefSlot,Allocation)\n" +
"      EXEC ('dbcc checkdb(''' + @database_name + ''') with tableresults')\n" +
"\n" +
"      FETCH next FROM database_cursor INTO @database_name\n" +
"   END\n" +
"\n" +
"   CLOSE database_cursor\n" +
"   DEALLOCATE database_cursor\n" +
"END \n" +
"\n" +
"ELSE -- run against a specified database (ie: usp_CheckDBIntegrity 'DB Name Here'\n" +
"\n" +
"   INSERT INTO dbcc_history ([Error], [Level], [State], MessageText, RepairLevel, [Status], \n" +
"   [DbId], DbFragId, ObjectId, IndexId, PartitionId, AllocUnitId, RidDbId, RidPruId, [File], Page, Slot, \n" +
"   RefDbId, RefPruId, RefFile, RefPage, RefSlot,Allocation)\n" +
"   EXEC ('dbcc checkdb(''' + @database_name + ''') with tableresults')";
        
        
        String queryEjecutarProc = "EXEC usp_CheckDBIntegrity 'pubs' -- specifies particular database, otherwise ALL DBS\n";
        
        String queryConsultarResultados = "/****** Script for SelectTopNRows command from SSMS  ******/\n" +
"SELECT TOP (1000) [Error]\n" +
"      ,[MessageText]\n" +
"      ,[TimeStamp]\n" +
"  FROM [pubs].[dbo].[dbcc_history]";

        res = coneccionBDD.baseDatos.consulta(queryCrearTabla);
        res = coneccionBDD.baseDatos.consulta(queryDropExists);        
        res = coneccionBDD.baseDatos.consulta(queryCrearProc);
        res = coneccionBDD.baseDatos.consulta(queryEjecutarProc);

        cargarTabla(queryConsultarResultados);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        this.dispose();
        main main = new main();
        main.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(anomaliasDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(anomaliasDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(anomaliasDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(anomaliasDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new anomaliasDatos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
