package main;

import model.*;
import presenter.*;
import service.UsuarioService;
import view.*;

/**
 * @author Pedro Henrique Passos Rocha
 * 
 */

public class Main {

    public static void main(String[] args) {
        
        var usuarioService = new UsuarioService();
        var usuarioModel = new Usuario();
        var mainView = new MainView();
        var panel = mainView.getMainPane();
        mainView.setVisible(true);
        
        var loginPresenter = new LoginPresenter(usuarioModel, panel, usuarioService);
    }
}