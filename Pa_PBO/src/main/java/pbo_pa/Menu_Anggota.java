/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pbo_pa;
import pbo_pa.Koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Lenovo
 */
public class Menu_Anggota extends javax.swing.JFrame {
public class NonEditableTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
    /**
     * Creates new form Menu_Anggota
     */
    public Menu_Anggota() {
        initComponents();
         initData();
    }
     private void performSearch() {
        String searchBy = combocari.getSelectedItem().toString();
        String keyword = jTextField1.getText();

        try {
            Connection connection = Koneksi.KoneksiDB();
            if (connection != null) {
                String query;

             if ("kode_ruangan".equals(searchBy)) {
    query = "SELECT * FROM ruangan WHERE kode_ruangan LIKE ?";
} else if ("tari_id".equals(searchBy)) {
    query = "SELECT * FROM ruangan WHERE tari_id LIKE ?"; 
} else {
    
    JOptionPane.showMessageDialog(null, "Pilihan pencarian tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    preparedStatement.setString(1, "%" + keyword + "%");
    ResultSet resultSet = preparedStatement.executeQuery();


                 
                    int columnCount = resultSet.getMetaData().getColumnCount();

                   
                    DefaultTableModel model = new DefaultTableModel();
                    for (int i = 1; i <= columnCount; i++) {
                        model.addColumn(resultSet.getMetaData().getColumnName(i));
                    }

                   
                    while (resultSet.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = resultSet.getObject(i);
                        }
                        model.addRow(row);
                    }

                  
                    jTable1.setModel(model);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat mencari data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
private String selectedTarian = null;
private void showSelectedTarianData(String selectedTarian) {
        try {
            Connection connection = Koneksi.KoneksiDB();  

          
            String queryBuktiDaftar = "SELECT tarian, nama_pelatih, jam_mulai, jam_selesai FROM bukti_daftar WHERE tarian = ?";
            PreparedStatement preparedStatementBuktiDaftar = connection.prepareStatement(queryBuktiDaftar);
            preparedStatementBuktiDaftar.setString(1, selectedTarian);
            ResultSet resultSetBuktiDaftar = preparedStatementBuktiDaftar.executeQuery();

            
            int columnCountBuktiDaftar = resultSetBuktiDaftar.getMetaData().getColumnCount();

           
            DefaultTableModel modelBuktiDaftar = new DefaultTableModel();
            for (int i = 1; i <= columnCountBuktiDaftar; i++) {
                modelBuktiDaftar.addColumn(resultSetBuktiDaftar.getMetaData().getColumnName(i));
            }

         
            while (resultSetBuktiDaftar.next()) {
                Object[] row = new Object[columnCountBuktiDaftar];
                for (int i = 1; i <= columnCountBuktiDaftar; i++) {
                    row[i - 1] = resultSetBuktiDaftar.getObject(i);
                }
                modelBuktiDaftar.addRow(row);
            }

           
            jTable1.setModel(modelBuktiDaftar);

            resultSetBuktiDaftar.close();
            preparedStatementBuktiDaftar.close();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initData(String danceCode) {
        try {
            Connection connection = Koneksi.KoneksiDB();  
            String query = "SELECT tarian, nama_pelatih, jam_mulai, jam_selesai FROM bukti_daftar WHERE tari_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, danceCode);
            ResultSet resultSet = preparedStatement.executeQuery();

     
            int columnCount = resultSet.getMetaData().getColumnCount();

         
            DefaultTableModel model = new DefaultTableModel();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(resultSet.getMetaData().getColumnName(i));
            }

   
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

         
            jTable1.setModel(model);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   private void initData() {
    try {
        Connection connection = Koneksi.KoneksiDB();  
     
        String queryBuktiDaftar = "SELECT kode_pendaftaran, tanggal_mendaftar, instruktur_tari_id_instruktur, anggota_nik, tari_id FROM bukti_daftar";
        PreparedStatement preparedStatementBuktiDaftar = connection.prepareStatement(queryBuktiDaftar);
        ResultSet resultSetBuktiDaftar = preparedStatementBuktiDaftar.executeQuery();

       
        int columnCountBuktiDaftar = resultSetBuktiDaftar.getMetaData().getColumnCount();

       
        NonEditableTableModel modelBuktiDaftar = new NonEditableTableModel();
        for (int i = 1; i <= columnCountBuktiDaftar; i++) {
            modelBuktiDaftar.addColumn(resultSetBuktiDaftar.getMetaData().getColumnName(i));
        }

        
        while (resultSetBuktiDaftar.next()) {
            Object[] row = new Object[columnCountBuktiDaftar];
            for (int i = 1; i <= columnCountBuktiDaftar; i++) {
                row[i - 1] = resultSetBuktiDaftar.getObject(i);
            }
            modelBuktiDaftar.addRow(row);
        }


        jTable1.setModel(modelBuktiDaftar);

        resultSetBuktiDaftar.close();
        preparedStatementBuktiDaftar.close();

      
        String queryRuangan = "SELECT kode_ruangan, nama_ruangan, instruktur_tari_id_instruktur, tari_id, jam_pinjam, jam_selesai FROM ruangan";
        PreparedStatement preparedStatementRuangan = connection.prepareStatement(queryRuangan);
        ResultSet resultSetRuangan = preparedStatementRuangan.executeQuery();

       
        int columnCountRuangan = resultSetRuangan.getMetaData().getColumnCount();

       
        NonEditableTableModel modelRuangan = new NonEditableTableModel();
        for (int i = 1; i <= columnCountRuangan; i++) {
            modelRuangan.addColumn(resultSetRuangan.getMetaData().getColumnName(i));
        }

       
        while (resultSetRuangan.next()) {
            Object[] row = new Object[columnCountRuangan];
            for (int i = 1; i <= columnCountRuangan; i++) {
                row[i - 1] = resultSetRuangan.getObject(i);
            }
            modelRuangan.addRow(row);
        }

      
        jTable1.setModel(modelRuangan);

        resultSetRuangan.close();
        preparedStatementRuangan.close();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        btncari = new javax.swing.JButton();
        combocari = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "kode ruangan", "nama_ruangan", "id_instruktur", "tari_id", "jam mulai", "jam selesai"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btncari.setText("cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        combocari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "kode_ruangan", "tari_id" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(418, 418, 418)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(combocari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btncari)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combocari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Login login= new Login();
       login.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
//      int selectedRow = jTable1.getSelectedRow();
//
//    if (selectedRow != -1) {
//        // Get the selected tarian from the selected row
//        selectedTarian = jTable1.getValueAt(selectedRow, 0).toString();
//
//        // Show data for the selected tarian
//        showSelectedTarianData(selectedTarian);
//    }

    }//GEN-LAST:event_jTable1MouseClicked

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
      performSearch();
    }//GEN-LAST:event_btncariActionPerformed
 
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
            java.util.logging.Logger.getLogger(Menu_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_Anggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncari;
    private javax.swing.JComboBox<String> combocari;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
