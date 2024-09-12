package main;

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
        var mainView = new MainView(usuario);
        var panel = mainView.getMainPane();
        
        mainView.setVisible(true);
        
        var configView = new ConfiguracaoView();
        initView(configView, panel);
        
        mainView.addConfigurarLogActionListener(evt -> configView.setVisible(true));
        
        var loginPresenter = new LoginPresenter(usuario, panel, usuarioService, mainView);
        
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
