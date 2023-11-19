/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pbo_pa;
import pbo_pa.Koneksi;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class Data_Ruangan extends javax.swing.JFrame {
private final Koneksi koneksi= new Koneksi();
  private Map<String, String> tariInstrukturMapping = new HashMap<>();
  private Map<String, String> tariIdMapping = new HashMap<>();
    /**
     * Creates new form Data_Ruangan
     */
    // Tambahkan inisialisasi pada konstruktor
public Data_Ruangan() {
    initComponents();
    fillTableWithModifiedData();
    
  
    tariInstrukturMapping.put("M111", "M101");
tariInstrukturMapping.put("T111", "T101");

tariIdMapping.put("modern (M111)", "M111");
tariIdMapping.put("tradisional (T111)", "T111");

       
    }

private void fillTableWithModifiedData() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    try {
        Connection conn = koneksi.KoneksiDB();
        if (conn != null) {
            String query = "SELECT kode_ruangan, nama_ruangan, instruktur_tari_id_instruktur, tari_id, jam_pinjam, jam_selesai FROM ruangan";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String kodeRuangan = resultSet.getString("kode_ruangan");
                        String namaRuangan = resultSet.getString("nama_ruangan");
                        String instrukturTariId = resultSet.getString("instruktur_tari_id_instruktur");
                        String tariId = resultSet.getString("tari_id");
                        String jamPinjam = resultSet.getString("jam_pinjam");
                        String jamSelesai = resultSet.getString("jam_selesai");

                        model.addRow(new Object[]{kodeRuangan, namaRuangan, instrukturTariId, tariId, jamPinjam, jamSelesai});
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saat mengambil data ruangan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

}
 private void updateInstrukturCombo(String jenisTari) {
    DefaultComboBoxModel<String> modelInstruktur = new DefaultComboBoxModel<>();

    if ("tradisional (T111)".equals(jenisTari)) {
        modelInstruktur.addElement("T101");
    } else if ("modern (M111)".equals(jenisTari)) {
        modelInstruktur.addElement("M101");
    } else {
        
        JOptionPane.showMessageDialog(null, "Jenis tari tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    comboinstruktur.setModel(modelInstruktur);
}
private boolean isRoomNameUnique(String namaRuangan) {
    try {
        Connection conn = koneksi.KoneksiDB();
        if (conn != null) {
            String query = "SELECT * FROM ruangan WHERE nama_ruangan = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, namaRuangan);
                ResultSet resultSet = preparedStatement.executeQuery();

                return !resultSet.next(); 
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        return false;
}
}
 private void combotariActionPerformed(java.awt.event.ActionEvent evt) {
     
        String selectedTari = combotari.getSelectedItem().toString();
        updateInstrukturCombo(selectedTari);
    }
   
   
   private void getAnggotaData() {
    try {
        Connection conn = koneksi.KoneksiDB();
        if (conn != null) {
            String query = "SELECT a.nama, b.tari_id, b.instruktur_tari_id_instruktur " +
                           "FROM anggota a " +
                           "JOIN bukti_daftar b ON a.nik = b.anggota_nik";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                String nama = resultSet.getString("nama");
                String tariId = resultSet.getString("tari_id");
                String instrukturTariId = resultSet.getString("instruktur_tari_id_instruktur");

                model.addRow(new Object[]{nama, tariId, instrukturTariId});
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saat mengambil data anggota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
   private boolean isTimeSlotAvailable(String kodeRuangan, String jamPinjam, String jamSelesai) {
    try {
        Connection conn = koneksi.KoneksiDB();
        if (conn != null) {
            String query = "SELECT * FROM ruangan " +
                           "WHERE kode_ruangan = ? " +
                           "AND ((jam_pinjam <= ? AND jam_selesai >= ?) OR (jam_pinjam <= ? AND jam_selesai >= ?))";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, kodeRuangan);
                preparedStatement.setString(2, jamPinjam);
                preparedStatement.setString(3, jamPinjam);
                preparedStatement.setString(4, jamSelesai);
                preparedStatement.setString(5, jamSelesai);

                ResultSet resultSet = preparedStatement.executeQuery();

                return !resultSet.next();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        return false; 
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        combotari = new javax.swing.JComboBox<>();
        txtkode = new javax.swing.JTextField();
        txtruangan = new javax.swing.JTextField();
        txtpinjam = new javax.swing.JTextField();
        txtselesai = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        comboinstruktur = new javax.swing.JComboBox<>();
        btntambah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "kode ruangan", "ruangan", "instruktur_tari", "jenis tari", "jam pinjam", "jam selesai"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("kode ruangan");

        jLabel2.setText("ruangan");

        jLabel3.setText("jenis tari");

        jLabel4.setText("jam pinjam");

        jLabel5.setText("jam selesai");

        combotari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "modern (M111)", "tradisional (T111)", " " }));

        jButton1.setText("kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("instruktur_tari");

        comboinstruktur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M101", "T101" }));
        comboinstruktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboinstrukturActionPerformed(evt);
            }
        });

        btntambah.setText("tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        btnhapus.setText("hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6))))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtruangan, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtpinjam, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                        .addGap(115, 115, 115))
                                    .addComponent(txtselesai))
                                .addGap(22, 22, 22))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboinstruktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combotari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnhapus)
                        .addGap(85, 85, 85)
                        .addComponent(btntambah)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtruangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(comboinstruktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addComponent(jLabel3))
                            .addComponent(combotari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtselesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btntambah)
                    .addComponent(btnhapus))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Menu_Instruktur menu_instruktur=new Menu_Instruktur();
       menu_instruktur.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void comboinstrukturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboinstrukturActionPerformed
       
    }//GEN-LAST:event_comboinstrukturActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
 
    try {
        String selectedTari = combotari.getSelectedItem().toString();
        System.out.println("Selected Tari: " + selectedTari);

        String jenisTariId = tariIdMapping.get(selectedTari);
        System.out.println("Jenis Tari ID: " + jenisTariId);

        String instrukturTariNama = comboinstruktur.getSelectedItem().toString();
        System.out.println("Selected Instruktur Tari: " + instrukturTariNama);

        String instrukturTariId = tariInstrukturMapping.get(jenisTariId);
        System.out.println("Instruktur Tari ID: " + instrukturTariId);

        Connection conn = koneksi.KoneksiDB();

        Map<String, String> instrukturMapping = new HashMap<>();
        instrukturMapping.put("M101", "M101");
        instrukturMapping.put("T101", "T101");
        String instrukturTariIdFromMapping = instrukturMapping.get(instrukturTariNama);
        comboinstruktur.setSelectedItem(tariInstrukturMapping.get(selectedTari));

        if (instrukturTariIdFromMapping != null) {
            String query = "SELECT id_instruktur FROM instruktur_tari WHERE id_instruktur = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, instrukturTariIdFromMapping);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String kodeRuangan = txtkode.getText();
                    String namaRuangan = txtruangan.getText();
                    String jamPinjam = txtpinjam.getText();
                    String jamSelesai = txtselesai.getText();

                    
                    if (kodeRuangan.isEmpty() || namaRuangan.isEmpty() || jamPinjam.isEmpty() || jamSelesai.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Semua data harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }

                 
                    if (!isRoomNameUnique(namaRuangan)) {
                        JOptionPane.showMessageDialog(null, "Nama ruangan sudah ada. Pilih nama ruangan yang berbeda.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    query = "INSERT INTO ruangan (kode_ruangan, nama_ruangan, instruktur_tari_id_instruktur, tari_id, jam_pinjam, jam_selesai) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = conn.prepareStatement(query)) {
                        insertStatement.setString(1, kodeRuangan);
                        insertStatement.setString(2, namaRuangan);
                        insertStatement.setString(3, instrukturTariIdFromMapping);
                        insertStatement.setString(4, jenisTariId); 
                        insertStatement.setString(5, jamPinjam);
                        insertStatement.setString(6, jamSelesai);

                        int rowsInserted = insertStatement.executeUpdate();

                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(null, "Data ruangan berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            // Refresh tabel dengan data yang dimodifikasi
                            fillTableWithModifiedData();
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal menambahkan data ruangan.", "Gagal", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Instruktur tari tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Instruktur tari tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }


    }//GEN-LAST:event_btntambahActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
      
    int selectedRowIndex = jTable1.getSelectedRow();

    
    if (selectedRowIndex == -1) {
        JOptionPane.showMessageDialog(null, "Pilih baris yang akan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    
    String kodeRuangan = jTable1.getValueAt(selectedRowIndex, 0).toString();

    
    int option = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus ruangan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION) {
       
        if (deleteRuangan(kodeRuangan)) {
            JOptionPane.showMessageDialog(null, "Data ruangan berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
           
            fillTableWithModifiedData();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data ruangan.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private boolean deleteRuangan(String kodeRuangan) {
    try {
        Connection conn = koneksi.KoneksiDB();
        if (conn != null) {
            String query = "DELETE FROM ruangan WHERE kode_ruangan = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, kodeRuangan);

                int rowsDeleted = preparedStatement.executeUpdate();

                return rowsDeleted > 0;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        return false; 
    }

    }//GEN-LAST:event_btnhapusActionPerformed

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
            java.util.logging.Logger.getLogger(Data_Ruangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data_Ruangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data_Ruangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data_Ruangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Data_Ruangan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btntambah;
    private javax.swing.JComboBox<String> comboinstruktur;
    private javax.swing.JComboBox<String> combotari;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtkode;
    private javax.swing.JTextField txtpinjam;
    private javax.swing.JTextField txtruangan;
    private javax.swing.JTextField txtselesai;
    // End of variables declaration//GEN-END:variables
}
