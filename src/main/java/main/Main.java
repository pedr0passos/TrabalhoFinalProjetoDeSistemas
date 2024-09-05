package main;

import model.*;
import presenter.*;
import service.UsuarioService;
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
        var mainView = new MainView();
        var panel = mainView.getMainPane();
        
        mainView.setVisible(true);
        
        var loginPresenter = new LoginPresenter(usuario, panel, usuarioService);
        
        loginPresenter.adicionarObserver(mainView);
    }
    
    public static Usuario getUsuario() {
        return usuario;
    }
}