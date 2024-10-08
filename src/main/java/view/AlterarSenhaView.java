/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Joao Victor
 */
public class AlterarSenhaView extends javax.swing.JInternalFrame {

    /**
     * Creates new form AlterarSenhaView
     */
    public AlterarSenhaView() {
        initComponents();
        txtNomeUsuario.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalvarUsuario = new javax.swing.JButton();
        txtConfirmarSenha = new javax.swing.JPasswordField();
        lblConfirmarSenha = new javax.swing.JLabel();
        txtSenhaNova = new javax.swing.JPasswordField();
        lblSenhaNova = new javax.swing.JLabel();
        lblNomeUsuario = new javax.swing.JLabel();
        txtNomeUsuario = new javax.swing.JTextField();
        lblSenhaAtual = new javax.swing.JLabel();
        txtSenhaAtual = new javax.swing.JPasswordField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Alterar Senha");
        setMaximumSize(new java.awt.Dimension(513, 248));
        setMinimumSize(new java.awt.Dimension(513, 248));

        btnSalvarUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSalvarUsuario.setText("Salvar");

        txtConfirmarSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblConfirmarSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConfirmarSenha.setText("Confirmar Senha:");

        txtSenhaNova.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSenhaNova.setMaximumSize(new java.awt.Dimension(64, 22));

        lblSenhaNova.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSenhaNova.setText("Senha Nova:");

        lblNomeUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNomeUsuario.setText("Nome:");

        txtNomeUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNomeUsuario.setEnabled(false);
        txtNomeUsuario.setMaximumSize(new java.awt.Dimension(64, 22));
        txtNomeUsuario.setMinimumSize(new java.awt.Dimension(64, 22));
        txtNomeUsuario.setName(""); // NOI18N
        txtNomeUsuario.setPreferredSize(new java.awt.Dimension(64, 22));

        lblSenhaAtual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSenhaAtual.setText("Senha Atual:");

        txtSenhaAtual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSenhaAtual.setMaximumSize(new java.awt.Dimension(64, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSenhaAtual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblNomeUsuario)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNomeUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSalvarUsuario)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblConfirmarSenha)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtConfirmarSenha))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblSenhaNova)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtSenhaNova, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtNomeUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblNomeUsuario))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenhaAtual)
                    .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenhaNova)
                    .addComponent(txtSenhaNova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmarSenha)
                    .addComponent(txtConfirmarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnSalvarUsuario)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JPasswordField getTxtSenhaAtual() {
        return txtSenhaAtual;
    }

    public JPasswordField getTxtSenhaNova() {
        return txtSenhaNova;
    }

    public JButton getBtnSalvarUsuario() {
        return btnSalvarUsuario;
    }


    public JPasswordField getTxtConfirmarSenha() {
        return txtConfirmarSenha;
    }

    public JTextField getTxtNomeUsuario() {
        return txtNomeUsuario;
    }

    public JPasswordField getTxtSenha() {
        return txtSenhaNova;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvarUsuario;
    private javax.swing.JLabel lblConfirmarSenha;
    private javax.swing.JLabel lblNomeUsuario;
    private javax.swing.JLabel lblSenhaAtual;
    private javax.swing.JLabel lblSenhaNova;
    private javax.swing.JPasswordField txtConfirmarSenha;
    private javax.swing.JTextField txtNomeUsuario;
    private javax.swing.JPasswordField txtSenhaAtual;
    private javax.swing.JPasswordField txtSenhaNova;
    // End of variables declaration//GEN-END:variables
}
