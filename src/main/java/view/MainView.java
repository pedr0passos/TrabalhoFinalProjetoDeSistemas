package view;

import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;

/**
 * @author 
 * Pedro Henrique Passos Rocha
 * Catterina Salvador
 */

public class MainView extends javax.swing.JFrame {

    public MainView() {
        configuraLookAndFeel();    
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        menuBar = new javax.swing.JMenuBar();
        btnMenuConfigurar = new javax.swing.JMenu();
        mItemConfigurarLog = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Notificadora");
        setResizable(false);

        mainPane.setBackground(new java.awt.Color(232, 232, 232));

        jToolBar1.setRollover(true);

        mainPane.setLayer(jToolBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
        mainPane.setLayout(mainPaneLayout);
        mainPaneLayout.setHorizontalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1450, Short.MAX_VALUE)
        );
        mainPaneLayout.setVerticalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPaneLayout.createSequentialGroup()
                .addGap(0, 635, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(mainPane, java.awt.BorderLayout.CENTER);

        menuBar.setDoubleBuffered(true);

        btnMenuConfigurar.setText("Configurar");

        mItemConfigurarLog.setText("Configurar Log");
        btnMenuConfigurar.add(mItemConfigurarLog);

        menuBar.add(btnMenuConfigurar);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    public void addConfigurarLogActionListener(ActionListener listener) {
        mItemConfigurarLog.addActionListener(listener);
    }
    
    public JDesktopPane getMainPane() {
        return mainPane;
    }
    
    public static void configuraLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }    
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu btnMenuConfigurar;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem mItemConfigurarLog;
    private javax.swing.JDesktopPane mainPane;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
