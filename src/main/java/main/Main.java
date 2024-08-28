package main;

import model.ClienteModel;
import presenter.LoginPresenter;
import view.*;

/**
 * @author Pedro Henrique Passos Rocha
 */

public class Main {
    public static void main(String[] args) {
        
        var mainView = new MainView();
        mainView.setVisible(true);
        
        var clienteModel = new ClienteModel();
        
        var desktopPane = mainView.getMainPane();

        var loginPresenter = new LoginPresenter(clienteModel, desktopPane);
        
    }
}
