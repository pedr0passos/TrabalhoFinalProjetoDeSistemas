package view;

import javax.swing.JButton;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas
 */

public class ConfirmarExclusaoView extends javax.swing.JInternalFrame {

    public ConfirmarExclusaoView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();
        btnSim = new javax.swing.JButton();
        btnNao = new javax.swing.JButton();

        setTitle("Confirmar Exclusão");
        setMaximumSize(new java.awt.Dimension(370, 214));
        setMinimumSize(new java.awt.Dimension(370, 214));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label.setText("Deseja realmente excluir esse Usuário?");
        getContentPane().add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 35, -1, -1));

        btnSim.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSim.setText("Sim");
        getContentPane().add(btnSim, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 73, -1, -1));

        btnNao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNao.setText("Não");
        getContentPane().add(btnNao, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 73, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnNao() {
        return btnNao;
    }

    public JButton getBtnSim() {
        return btnSim;
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNao;
    private javax.swing.JButton btnSim;
    private javax.swing.JLabel label;
    // End of variables declaration//GEN-END:variables
}
