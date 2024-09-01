/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author pedro
 */

public class LoginView extends javax.swing.JInternalFrame {

    public LoginView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogin = new javax.swing.JLabel();
        txtNomeUsuario = new javax.swing.JTextField();
        txtSenha = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        lblNomeUsuario = new javax.swing.JLabel();
        btnCadastrar = new javax.swing.JButton();
        lblNaoPossuiConta = new javax.swing.JLabel();
        btnEntrar = new javax.swing.JButton();

        setTitle("Entrar");
        setMaximumSize(new java.awt.Dimension(310, 418));
        setMinimumSize(new java.awt.Dimension(310, 418));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblLogin.setText("Login");
        getContentPane().add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, -1));

        txtNomeUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(txtNomeUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 176, 34));

        txtSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 176, 34));

        lblSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSenha.setText("Senha:");
        getContentPane().add(lblSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        lblNomeUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNomeUsuario.setText("Nome de Usuário:");
        getContentPane().add(lblNomeUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        btnCadastrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setToolTipText("");
        getContentPane().add(btnCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 100, 30));

        lblNaoPossuiConta.setText("Não possui uma conta?");
        getContentPane().add(lblNaoPossuiConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, -1, -1));

        btnEntrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEntrar.setText("Entrar");
        getContentPane().add(btnEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 80, 30));

        setBounds(0, 0, 318, 518);
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnCadastrar() {
        return btnCadastrar;
    }

    public JTextField getTxtNomeUsuario() {
        return txtNomeUsuario;
    }

    public JTextField getTxtSenha() {
        return txtSenha;
    }

    public JButton getBotaoLogin() {
        return btnCadastrar;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNaoPossuiConta;
    private javax.swing.JLabel lblNomeUsuario;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JTextField txtNomeUsuario;
    private javax.swing.JTextField txtSenha;
    // End of variables declaration//GEN-END:variables
}
