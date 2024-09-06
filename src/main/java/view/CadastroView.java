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
 * @author pedro
 */
public class CadastroView extends javax.swing.JInternalFrame {

    /**
     * Creates new form CadastroView
     */
    public CadastroView() {
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

        lblNomeUsuario = new javax.swing.JLabel();
        lblSenhaUsuario = new javax.swing.JLabel();
        txtNomeUsuario = new javax.swing.JTextField();
        btnSalvarUsuario = new javax.swing.JButton();
        lblConfirmarSenha = new javax.swing.JLabel();
        txtConfirmarSenha = new javax.swing.JPasswordField();
        txtSenha = new javax.swing.JPasswordField();

        setClosable(true);
        setTitle("Cadastrar Usuario");
        setMaximumSize(new java.awt.Dimension(518, 260));
        setMinimumSize(new java.awt.Dimension(518, 260));

        lblNomeUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNomeUsuario.setText("Nome:");

        lblSenhaUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSenhaUsuario.setText("Senha:");

        txtNomeUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtNomeUsuario.setMaximumSize(new java.awt.Dimension(64, 22));
        txtNomeUsuario.setMinimumSize(new java.awt.Dimension(64, 22));
        txtNomeUsuario.setName(""); // NOI18N
        txtNomeUsuario.setPreferredSize(new java.awt.Dimension(64, 22));

        btnSalvarUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSalvarUsuario.setText("Salvar");

        lblConfirmarSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConfirmarSenha.setText("Confirmar Senha:");

        txtSenha.setMaximumSize(new java.awt.Dimension(64, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNomeUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSalvarUsuario)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblConfirmarSenha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtConfirmarSenha))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblSenhaUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(49, Short.MAX_VALUE))
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
                    .addComponent(lblSenhaUsuario)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmarSenha)
                    .addComponent(txtConfirmarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSalvarUsuario)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBotaoSalvarUsuario() {
        return btnSalvarUsuario;
    }
        public JTextField getTxtNomeUsuario() {
        return txtNomeUsuario;
    }

    public JPasswordField getTxtConfirmarSenha() {
        return txtConfirmarSenha;
    }

    public JPasswordField getTxtSenha() {
        return txtSenha;
    }
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvarUsuario;
    private javax.swing.JLabel lblConfirmarSenha;
    private javax.swing.JLabel lblNomeUsuario;
    private javax.swing.JLabel lblSenhaUsuario;
    private javax.swing.JPasswordField txtConfirmarSenha;
    private javax.swing.JTextField txtNomeUsuario;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
