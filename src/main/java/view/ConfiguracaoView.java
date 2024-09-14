package view;

import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * @author 
 * Pedro Henrique Passos Rocha
 * Catterina Salvador
 */

public class ConfiguracaoView extends javax.swing.JInternalFrame {

    public ConfiguracaoView() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLog = new javax.swing.JLabel();
        cBoxLog = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Configuração do Sistema");
        setMinimumSize(new java.awt.Dimension(290, 192));
        setPreferredSize(new java.awt.Dimension(290, 192));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLog.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLog.setText("Log:");
        getContentPane().add(lblLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        cBoxLog.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cBoxLog.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Json", "CSV" }));
        getContentPane().add(cBoxLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 173, -1));

        btnSalvar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSalvar.setText("Salvar");
        getContentPane().add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, -1, -1));

        setBounds(1150, 10, 290, 192);
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public JComboBox<String> getcBoxLog() {
        return cBoxLog;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cBoxLog;
    private javax.swing.JLabel lblLog;
    // End of variables declaration//GEN-END:variables
}
