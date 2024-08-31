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
import model.Usuario;
import observer.Observer;
import view.LoginView;

public class LoginPresenter {
    
    private final List<Observer> observers = new ArrayList<>();
    
    private final Usuario model;
    private LoginView view;
    
    public LoginPresenter(Usuario model, JDesktopPane panel) {
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
}
