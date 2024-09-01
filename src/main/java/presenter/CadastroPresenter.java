/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import model.Usuario;
import observer.Observer;
import service.UsuarioService;

import view.CadastroView;
import view.LoginView;

/**
 *
 * @author pedro
 */

public class CadastroPresenter {
    
    private final List<Observer> observers = new ArrayList<>();
    
    private final Usuario model;
    private CadastroView cadastroView;
    private LoginView loginView;
    private final UsuarioService usuarioService;
    
    public CadastroPresenter(Usuario model, JDesktopPane panel) {
        this.model = model;
        this.usuarioService = new UsuarioService();
        criarView();
        panel.add(cadastroView);
        panel.add(loginView);
    }
    
    public final void criarView() {
        cadastroView = new CadastroView(); 
        loginView = new LoginView();
        cadastroView.setVisible(false);
        cadastroView.getBotaoSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = cadastroView.getTxtNomeUsuario().getText();
                    String senha = new String(cadastroView.getTxtSenha().getPassword());
                    String confirmarSenha = new String(cadastroView.getTxtConfirmarSenha().getPassword());
                    
                    if (username.isEmpty() || senha.isEmpty()) {
                        JOptionPane.showMessageDialog(cadastroView, "Nome de usuário e senha são obrigatórios.");
                        return;
                    }else if(!senha.equals(confirmarSenha)){
                        JOptionPane.showMessageDialog(cadastroView, "Senha invalida");
                        return;
                    }
                    
                    model.setUsername(username);
                    model.setSenha(senha);
                    
                    usuarioService.cadastrarUsuario(model);
                    
                    notificarObservadores();
                    
                    JOptionPane.showMessageDialog(cadastroView, "Usuário cadastrado com sucesso!");
                    cadastroView.dispose();
                    
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();
                }
            }
        });
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
