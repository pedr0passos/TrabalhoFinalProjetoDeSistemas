package view;

import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import model.Usuario;
import presenter.CadastroPresenter;
import service.UsuarioService;

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
        toolBar = new javax.swing.JToolBar();
        btnNotificacoes = new javax.swing.JButton();
        lblNomeUsuarioLogado = new javax.swing.JLabel();
        lblTipoUsuarioLogado = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        botao = new javax.swing.JMenu();
        btnInformacoesConta = new javax.swing.JMenuItem();
        btnSair = new javax.swing.JMenuItem();
        menuUsuarios = new javax.swing.JMenu();
        NovoUsuario = new javax.swing.JMenuItem();
        menuConfigurar = new javax.swing.JMenu();
        ConfigurarLog = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Notificadora");
        setResizable(false);

        mainPane.setBackground(new java.awt.Color(232, 232, 232));
        mainPane.setEnabled(false);

        btnNotificacoes.setText("Notificações");
        btnNotificacoes.setFocusable(false);
        btnNotificacoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNotificacoes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNotificacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotificacoesActionPerformed(evt);
            }
        });
        toolBar.add(btnNotificacoes);

        lblNomeUsuarioLogado.setText("<nome-usuario-logado>");
        toolBar.add(lblNomeUsuarioLogado);

        lblTipoUsuarioLogado.setText("<tipo-usuario-logado>");
        toolBar.add(lblTipoUsuarioLogado);

        mainPane.setLayer(toolBar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
        mainPane.setLayout(mainPaneLayout);
        mainPaneLayout.setHorizontalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1450, Short.MAX_VALUE)
        );
        mainPaneLayout.setVerticalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPaneLayout.createSequentialGroup()
                .addContainerGap(635, Short.MAX_VALUE)
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        getContentPane().add(mainPane, java.awt.BorderLayout.CENTER);

        menuBar.setDoubleBuffered(true);

        botao.setText("Conta");

        btnInformacoesConta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/usuario.png"))); // NOI18N
        btnInformacoesConta.setText("Informações da Conta");
        btnInformacoesConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformacoesContaActionPerformed(evt);
            }
        });
        botao.add(btnInformacoesConta);

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        botao.add(btnSair);

        menuBar.add(botao);

        menuUsuarios.setText("Usuarios");

        NovoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar-usuario.png"))); // NOI18N
        NovoUsuario.setText("Novo Usuario");
        NovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NovoUsuarioActionPerformed(evt);
            }
        });
        menuUsuarios.add(NovoUsuario);

        menuBar.add(menuUsuarios);

        menuConfigurar.setText("Configurar");

        ConfigurarLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/engrenagem.png"))); // NOI18N
        ConfigurarLog.setText("Configurar Log");
        menuConfigurar.add(ConfigurarLog);

        menuBar.add(menuConfigurar);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void NovoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NovoUsuarioActionPerformed
        var cadastroPresenter = new CadastroPresenter(new Usuario(), this.getMainPane(), new UsuarioService());
    }//GEN-LAST:event_NovoUsuarioActionPerformed

    private void btnNotificacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificacoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNotificacoesActionPerformed

    private void btnInformacoesContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformacoesContaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInformacoesContaActionPerformed
    
    public void addConfigurarLogActionListener(ActionListener listener) {
        ConfigurarLog.addActionListener(listener);
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
    private javax.swing.JMenuItem ConfigurarLog;
    private javax.swing.JMenuItem NovoUsuario;
    private javax.swing.JMenu botao;
    private javax.swing.JMenuItem btnInformacoesConta;
    private javax.swing.JButton btnNotificacoes;
    private javax.swing.JMenuItem btnSair;
    private javax.swing.JLabel lblNomeUsuarioLogado;
    private javax.swing.JLabel lblTipoUsuarioLogado;
    private javax.swing.JDesktopPane mainPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuConfigurar;
    private javax.swing.JMenu menuUsuarios;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables

}
