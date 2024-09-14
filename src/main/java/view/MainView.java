package view;

import java.awt.event.ActionListener;
import javax.swing.*;
import model.Usuario;
import singleton.UsuarioLogadoSingleton;
import observer.Observer;
import presenter.*;
import service.*;

/**
 * @author 
 * Pedro Henrique Passos Rocha
 * Catterina Salvador
 * João Victor Mascarenhas
 */

public class MainView extends javax.swing.JFrame implements Observer {

    private final JLabel lblNomeUsuarioLogado = new JLabel();   
    private final JLabel lblTipoUsuarioLogado = new JLabel();
    private Usuario usuarioLogado;
    private UsuarioService usuarioService;
    private LogService logService;
    
    private CadastroPresenter cadastroPresenter;
    private RegistrosPresenter registrosPresenter;
    private SolicitacoesPresenter solicitacoesPresenter;
    private EnviarNotificacaoPresenter enviarNotificacaoPresenter;
    private InformacoesUsuarioPresenter informacoesUsuarioPresenter;
    private NotificacoesPresenter notificacoesPresenter;
    
    public MainView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.logService = new LogService();
        
        configuraLookAndFeel();  
        initComponents();
        initInternalFrames();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JDesktopPane();
        toolBar = new javax.swing.JToolBar();
        btnNotificacoes = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuConta = new javax.swing.JMenu();
        btnInformacoesConta = new javax.swing.JMenuItem();
        btnSair = new javax.swing.JMenuItem();
        menuUsuarios = new javax.swing.JMenu();
        NovoUsuario = new javax.swing.JMenuItem();
        RegistrosDeUsuario = new javax.swing.JMenuItem();
        SolicitacoesDosUsuarios = new javax.swing.JMenuItem();
        btnEnviarNotificacao = new javax.swing.JMenuItem();
        menuConfigurar = new javax.swing.JMenu();
        ConfigurarLog = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Notificadora");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setType(java.awt.Window.Type.POPUP);

        mainPane.setBackground(new java.awt.Color(232, 232, 232));
        mainPane.setEnabled(false);
        mainPane.setMaximumSize(new java.awt.Dimension(1461, 846));
        mainPane.setMinimumSize(new java.awt.Dimension(1461, 846));

        toolBar.setEnabled(false);

        btnNotificacoes.setText("Notificações");
        btnNotificacoes.setEnabled(false);
        btnNotificacoes.setFocusable(false);
        btnNotificacoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNotificacoes.setMargin(new java.awt.Insets(2, 20, 3, 20));
        btnNotificacoes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNotificacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotificacoesActionPerformed(evt);
            }
        });
        toolBar.add(btnNotificacoes);

        mainPane.setLayer(toolBar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
        mainPane.setLayout(mainPaneLayout);
        mainPaneLayout.setHorizontalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1461, Short.MAX_VALUE)
        );
        mainPaneLayout.setVerticalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPaneLayout.createSequentialGroup()
                .addGap(0, 821, Short.MAX_VALUE)
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(mainPane, java.awt.BorderLayout.CENTER);

        menuBar.setDoubleBuffered(true);

        menuConta.setText("Conta");
        menuConta.setEnabled(false);

        btnInformacoesConta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/usuario.png"))); // NOI18N
        btnInformacoesConta.setText("Informações da Conta");
        btnInformacoesConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformacoesContaActionPerformed(evt);
            }
        });
        menuConta.add(btnInformacoesConta);

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        menuConta.add(btnSair);

        menuBar.add(menuConta);

        menuUsuarios.setText("Usuarios");
        menuUsuarios.setEnabled(false);

        NovoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sinal-de-adicao.png"))); // NOI18N
        NovoUsuario.setText("Novo Usuário");
        NovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NovoUsuarioActionPerformed(evt);
            }
        });
        menuUsuarios.add(NovoUsuario);

        RegistrosDeUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/usuario.png"))); // NOI18N
        RegistrosDeUsuario.setText("Registros de Usuário");
        RegistrosDeUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrosDeUsuarioActionPerformed(evt);
            }
        });
        menuUsuarios.add(RegistrosDeUsuario);

        SolicitacoesDosUsuarios.setText("Solicitações dos Usuários");
        SolicitacoesDosUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolicitacoesDosUsuariosActionPerformed(evt);
            }
        });
        menuUsuarios.add(SolicitacoesDosUsuarios);

        btnEnviarNotificacao.setText("Enviar Notificação");
        btnEnviarNotificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarNotificacaoActionPerformed(evt);
            }
        });
        menuUsuarios.add(btnEnviarNotificacao);

        menuBar.add(menuUsuarios);

        menuConfigurar.setText("Configurar");
        menuConfigurar.setEnabled(false);

        ConfigurarLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/engrenagem.png"))); // NOI18N
        ConfigurarLog.setText("Configurar Log");
        ConfigurarLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfigurarLogActionPerformed(evt);
            }
        });
        menuConfigurar.add(ConfigurarLog);

        menuBar.add(menuConfigurar);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void NovoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NovoUsuarioActionPerformed
        cadastroPresenter.setVisible();
    }//GEN-LAST:event_NovoUsuarioActionPerformed

    private void btnNotificacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificacoesActionPerformed
        notificacoesPresenter.setVisible();
        notificacoesPresenter.atualizarView();
    }//GEN-LAST:event_btnNotificacoesActionPerformed

    private void RegistrosDeUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrosDeUsuarioActionPerformed
        registrosPresenter.setVisible();
        registrosPresenter.atualizarView();
    }//GEN-LAST:event_RegistrosDeUsuarioActionPerformed

    private void ConfigurarLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfigurarLogActionPerformed
    }//GEN-LAST:event_ConfigurarLogActionPerformed

    private void btnInformacoesContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformacoesContaActionPerformed
        informacoesUsuarioPresenter.setVisible();
        informacoesUsuarioPresenter.atualizarView();
    }//GEN-LAST:event_btnInformacoesContaActionPerformed

    private void SolicitacoesDosUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolicitacoesDosUsuariosActionPerformed
        solicitacoesPresenter.setVisible();
        solicitacoesPresenter.atualizarView();
    }//GEN-LAST:event_SolicitacoesDosUsuariosActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnEnviarNotificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarNotificacaoActionPerformed
         enviarNotificacaoPresenter.setVisible();
         enviarNotificacaoPresenter.atualizarView();
    }//GEN-LAST:event_btnEnviarNotificacaoActionPerformed
    
    public void initInternalFrames() {
        this.cadastroPresenter = new CadastroPresenter(usuarioLogado, mainPane, new UsuarioService(), this, true, new AdministradorService());
        this.registrosPresenter = new RegistrosPresenter(usuarioLogado, mainPane, new UsuarioService());
        this.solicitacoesPresenter = new SolicitacoesPresenter(new SolicitacaoService(), new UsuarioService(), mainPane);
        this.enviarNotificacaoPresenter = new EnviarNotificacaoPresenter(mainPane, new NotificadoraService(), new UsuarioService(), this);
        this.informacoesUsuarioPresenter = new InformacoesUsuarioPresenter(mainPane, new UsuarioService(), logService);
        this.notificacoesPresenter = new NotificacoesPresenter(mainPane, new NotificadoraService());
        
        
        cadastroPresenter.adicionarObserver(registrosPresenter);
        solicitacoesPresenter.adicionarObserver(registrosPresenter);
        enviarNotificacaoPresenter.adicionarObserver(registrosPresenter);
    }
    
    public void setUsuario (Usuario usuario) {
        usuarioLogado = usuario;
    }
    
    public void addConfigurarLogActionListener(ActionListener listener) {
        ConfigurarLog.addActionListener(listener);
    }
    
    public JLabel getLblNomeUsuarioLogado() {
        return lblNomeUsuarioLogado;
    }

    public JLabel getLblTipoUsuarioLogado() {
        return lblTipoUsuarioLogado;
    }
    
    public JDesktopPane getMainPane() {
        return mainPane;
    }

    public JButton getBtnNotificacoes() {
        return btnNotificacoes;
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

    @Override
    public void update() {
        usuarioLogado = UsuarioLogadoSingleton.getInstancia().getUsuarioLogado();
        menuConta.setEnabled(true);
        menuConfigurar.setEnabled(true);
        
        if (usuarioLogado.isAdministrador()) {
            menuUsuarios.setEnabled(true);
        }

        if (!usuarioLogado.isAdministrador())
            btnNotificacoes.setEnabled(true);
        
        toolBar.add(lblNomeUsuarioLogado);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(lblTipoUsuarioLogado);
        
    }
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ConfigurarLog;
    private javax.swing.JMenuItem NovoUsuario;
    private javax.swing.JMenuItem RegistrosDeUsuario;
    private javax.swing.JMenuItem SolicitacoesDosUsuarios;
    private javax.swing.JMenuItem btnEnviarNotificacao;
    private javax.swing.JMenuItem btnInformacoesConta;
    private javax.swing.JButton btnNotificacoes;
    private javax.swing.JMenuItem btnSair;
    private javax.swing.JDesktopPane mainPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuConfigurar;
    private javax.swing.JMenu menuConta;
    private javax.swing.JMenu menuUsuarios;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables

}
