/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import model.ClienteModel;
import observer.Observer;
import view.LoginView;

public class LoginPresenter implements Observer {
    
    private final List<Observer> observers = new ArrayList<>();
    
    private final ClienteModel model;
    private LoginView view;
    
    public LoginPresenter(ClienteModel model, JDesktopPane panel) {
        this.model = model;
        criarView();
        panel.add(view);
    }
    
    public final void criarView() {
    view = new LoginView(); 
    view.setVisible(true);
    view.getBotaoLogin().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                
            } catch ( NumberFormatException exception) {
                exception.getStackTrace();                    
            }
        }
    });
}
    
    @Override
    public void update() {
        
    }
}
