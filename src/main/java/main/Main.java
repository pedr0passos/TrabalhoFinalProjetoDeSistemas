package main;

import service.LogService;
import view.ConfiguracaoView;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import model.Usuario;
import singleton.UsuarioLogadoSingleton;
import presenter.*;
import service.*;
import view.*;

/**
 * @author Pedro Henrique Passos Rocha
 * @author Catterina Vittorazzi Salvador
 * @author João Victor Mascarenhas
 */

public class Main {
    
    public static void main(String[] args) {
        
        var usuarioService = new UsuarioService();

        var logService = new LogService(); 
        
        var usuarioLogado = UsuarioLogadoSingleton.getInstancia();
        
        var usuario = usuarioLogado.getUsuarioLogado();
        
        if (usuario == null) {
            usuario = new Usuario();
            usuarioLogado.setUsuarioLogado(usuario);
        }
        
        var mainView = new MainView(logService);
        var panel = mainView.getMainPane();
        
        mainView.setVisible(true);
        
        var configView = new ConfiguracaoView();
        initView(configView, panel);
        
        var configPresenter = new ConfiguracaoPresenter(usuarioService, configView, logService);

        mainView.addConfigurarLogActionListener(evt -> configView.setVisible(true));
        
        logService.configuraLog(configView.getcBoxLog().getSelectedItem().toString());
        var loginPresenter = new LoginPresenter(panel, usuarioService, mainView, logService);
        
        loginPresenter.adicionarObserver(mainView);
    }
    
    public static void initView(JInternalFrame internalFrame, JDesktopPane desktopPane) {
        desktopPane.add(internalFrame);
        internalFrame.setVisible(false);
    }
}
