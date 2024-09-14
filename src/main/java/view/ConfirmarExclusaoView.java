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

        btnNao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNao.setText("Não");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(label)
                .addContainerGap(68, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(btnSim)
                .addGap(18, 18, 18)
                .addComponent(btnNao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(label)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSim)
                    .addComponent(btnNao))
                .addContainerGap(60, Short.MAX_VALUE))
        );

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
