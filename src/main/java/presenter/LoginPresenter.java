package presenter;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;

import model.Usuario;
import observer.Observer;
import view.CadastroView;
import view.LoginView;

public class LoginPresenter {
    
    private final List<Observer> observers = new ArrayList<>();
    
    private final Usuario model;
    private LoginView view;
    private CadastroView cadastroView;
    private JDesktopPane desktopPane;
    
    public LoginPresenter(Usuario model, JDesktopPane panel) {
        this.model = model;
        this.desktopPane = panel;
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
        
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarCadastroView();
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });



    }
    private void mostrarCadastroView() {
        if (cadastroView == null) { // Se a tela de cadastro ainda não foi criada
            cadastroView = new CadastroView(); // Cria a tela de cadastro
            desktopPane.add(cadastroView); // Adiciona a tela de cadastro ao painel
        }
        cadastroView.setVisible(true); // Torna a tela de cadastro visível
        try {
            cadastroView.setSelected(true); // Seleciona a tela de cadastro
        } catch (PropertyVetoException exception) {
            exception.getStackTrace();
        }
    }
    
    public void adicionarObserver(Observer observer) {
        observers.add(observer);
    }
    
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }
    
    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }

    }
}
