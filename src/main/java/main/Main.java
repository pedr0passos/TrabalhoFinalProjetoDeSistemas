package main;

import service.LogService;
import view.ConfiguracaoView;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import model.Usuario;
import presenter.*;
import service.*;
import view.*;

/**
 * @author Pedro Henrique Passos Rocha
 * @author Catterina Vittorazzi Salvador
 * @author João Victor Mascarenhas
 */

public class Main {
    
    private static final Usuario usuario = new Usuario();
    
    public static void main(String[] args) {
        
        var usuarioService = new UsuarioService();
        var logService = new LogService(); // Cria a instância do LogService
        var mainView = new MainView(usuario);
        var panel = mainView.getMainPane();
        
        mainView.setVisible(true);
        
        var configView = new ConfiguracaoView();
        initView(configView, panel);
        
        var configPresenter = new ConfiguracaoPresenter(usuarioService, configView, logService); // Passa o LogService para o ConfiguracaoPresenter

        mainView.addConfigurarLogActionListener(evt -> configView.setVisible(true));
        
        logService.configuraLog(configView.getcBoxLog().getSelectedItem().toString());
        var loginPresenter = new LoginPresenter(usuario, panel, usuarioService, mainView, logService);
        
        loginPresenter.adicionarObserver(mainView);
    }
    
    public static Usuario getUsuario() {
        return usuario;
    }
    
    public static void initView(JInternalFrame internalFrame, JDesktopPane desktopPane) {
        desktopPane.add(internalFrame);
        internalFrame.setVisible(false);
    }
}
