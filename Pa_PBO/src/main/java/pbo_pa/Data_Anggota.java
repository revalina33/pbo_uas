/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pbo_pa;import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;  
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Data_Anggota extends javax.swing.JFrame {
    private   Koneksi koneksi = new Koneksi();
    private boolean databaru;
    private int rowSelected = -1; 

    public Data_Anggota() {
        initComponents();
        databaru = true;
        getAnggotaData();
        fillTableWithModifiedData();
    }

private void fillTableWithModifiedData() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    try {
        Connection conn = koneksi.KoneksiDB();
        if (conn != null) {
            String query = "SELECT a.nik, a.nama, COALESCE(b.tari_id, 'Tidak Diketahui') AS tari_id " +
                           "FROM anggota a " +
                           "LEFT JOIN bukti_daftar b ON a.nik = b.anggota_nik";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            model.setRowCount(0);

            while (resultSet.next()) {
                String nik = resultSet.getString("nik");
                String nama = resultSet.getString("nama");
                String tariId = resultSet.getString("tari_id");

                model.addRow(new Object[]{nik, nama, tariId});
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saat mengambil data anggota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



   private void refresh() {
    txtnik.setText("");
    txtnama.setText("");
    txttari.setText("");
    rowSelected = -1;
}

   private String getDanceType(String tariId) {
    switch (tariId) {
        case "M111":
            return "Modern";
        case "T111":
            return "Tradisional";
        default:
            return "Tidak Diketahui";
    }
}

    private void getAnggotaData() {
        try {
            Connection conn = koneksi.KoneksiDB();
            if (conn != null) {
            String query = "SELECT a.nik, a.nama, b.tari_id " +
               "FROM anggota a " +
               "JOIN bukti_daftar b ON a.nik = b.anggota_nik";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);

                 while (resultSet.next()) {
                String nik = resultSet.getString("nik");
                String nama = resultSet.getString("nama");
                String tariId = resultSet.getString("tari_id");

                String jenisTari = tariId.equals("M111") ? "Modern" : (tariId.equals("T111") ? "Tradisional" : "Tidak Diketahui");

                boolean isRowPresent = false;
                int rowIndex = -1;

              
                for (int i = 0; i < model.getRowCount(); i++) {
                    String existingNik = model.getValueAt(i, 0).toString();
                    if (existingNik.equals(nik)) {
                        isRowPresent = true;
                        rowIndex = i;
                        break;
                    }
                }

              
                if (isRowPresent) {
                    model.setValueAt(nama, rowIndex, 1);
                    model.setValueAt(jenisTari, rowIndex, 2);
                } else {
                    // If the row is not present, add it to the table
                    model.addRow(new Object[]{nik, nama, jenisTari});
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saat mengambil data anggota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btncari = new javax.swing.JButton();
        combocari = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txttari = new javax.swing.JTextField();
        txtnik = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "NIK", "nama", "tari_id"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btncari.setText("cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        combocari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nama", "NIK", "id_tari" }));

        jLabel1.setText("nama");

        jLabel2.setText("jenis tari");

        jButton2.setText("kembali");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("hapus");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtnik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnikActionPerformed(evt);
            }
        });

        jLabel3.setText("NIK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(56, 56, 56)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addComponent(txttari, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtnama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnik, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btncari)
                                .addGap(32, 32, 32)
                                .addComponent(combocari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jTextField1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combocari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncari, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txttari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(136, 136, 136))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton5))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       Menu_Instruktur menu_instruktur=new Menu_Instruktur();
       menu_instruktur.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
                               
    try {
        databaru = false;
        int row = jTable1.getSelectedRow();
        String nik = jTable1.getModel().getValueAt(row, 0).toString(); 

        Connection conn = koneksi.KoneksiDB();
        String query = "SELECT a.nama, b.tari_id " +
                       "FROM anggota a " +
                       "LEFT JOIN bukti_daftar b ON a.nik = b.anggota_nik " +
                       "WHERE a.nik = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, nik);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                txtnik.setText(nik);
                txtnama.setText(resultSet.getString("nama"));
                String tariId = resultSet.getString("tari_id");

              
                txttari.setText(getDanceType(tariId));

                rowSelected = row;
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saat mengambil data anggota: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        getAnggotaData();
    }

           

    

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
   try {
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            String nikAnggota = jTable1.getValueAt(row, 0).toString(); 

            try {
                Connection conn = koneksi.KoneksiDB();
                if (conn != null) {
                    String queryHapusBuktiDaftar = "DELETE FROM bukti_daftar WHERE anggota_nik = ?";
                    String queryHapusAnggota = "DELETE FROM anggota WHERE nik = ?";
                    try (PreparedStatement preparedStatementHapusBuktiDaftar = conn.prepareStatement(queryHapusBuktiDaftar);
                         PreparedStatement preparedStatementHapusAnggota = conn.prepareStatement(queryHapusAnggota)) {
                        preparedStatementHapusBuktiDaftar.setString(1, nikAnggota);
                        preparedStatementHapusAnggota.setString(1, nikAnggota);

                        int rowsDeletedBuktiDaftar = preparedStatementHapusBuktiDaftar.executeUpdate();
                        int rowsDeletedAnggota = preparedStatementHapusAnggota.executeUpdate();

                        if (rowsDeletedBuktiDaftar > 0 && rowsDeletedAnggota > 0) {
                            JOptionPane.showMessageDialog(null, "Data anggota berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            // Perbarui tampilan tabel setelah penghapusan
                            fillTableWithModifiedData();
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal menghapus data anggota.", "Gagal", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saat terhubung ke database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih anggota yang ingin dihapus.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }



    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtnikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnikActionPerformed

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
                               
    String searchBy = combocari.getSelectedItem().toString();
    String keyword = jTextField1.getText();

    try {
        Connection conn = koneksi.KoneksiDB();
        if (conn != null) {
            String query;

            if ("nama".equals(searchBy)) {
                query = "SELECT a.nik, a.nama, COALESCE(b.tari_id, 'Tidak Diketahui') AS tari_id " +
                        "FROM anggota a " +
                        "LEFT JOIN bukti_daftar b ON a.nik = b.anggota_nik " +
                        "WHERE a.nama LIKE ?";
            } else if ("NIK".equals(searchBy)) {
                query = "SELECT a.nik, a.nama, COALESCE(b.tari_id, 'Tidak Diketahui') AS tari_id " +
                        "FROM anggota a " +
                        "LEFT JOIN bukti_daftar b ON a.nik = b.anggota_nik " +
                        "WHERE a.nik LIKE ?";
            } else if ("id_tari".equals(searchBy)) {
                query = "SELECT a.nik, a.nama, COALESCE(b.tari_id, 'Tidak Diketahui') AS tari_id " +
                        "FROM anggota a " +
                        "LEFT JOIN bukti_daftar b ON a.nik = b.anggota_nik " +
                        "WHERE COALESCE(b.tari_id, 'Tidak Diketahui') LIKE ?";
            } else {
                // Kondisi default jika pilihan tidak dikenali
                JOptionPane.showMessageDialog(null, "Pilihan pencarian tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + keyword + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);

                while (resultSet.next()) {
                    String nik = resultSet.getString("nik");
                    String nama = resultSet.getString("nama");
                    String tariId = resultSet.getString("tari_id");

                    model.addRow(new Object[]{nik, nama, tariId});
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saat mencari data anggota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

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
            java.util.logging.Logger.getLogger(Data_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Data_Anggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncari;
    private javax.swing.JComboBox<String> combocari;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnik;
    private javax.swing.JTextField txttari;
    // End of variables declaration//GEN-END:variables
}   
