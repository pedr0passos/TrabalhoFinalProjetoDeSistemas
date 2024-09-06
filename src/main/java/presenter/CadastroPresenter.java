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

/**
 *
 * @author pedro
 */

public class CadastroPresenter {
    
    private final List<Observer> observers = new ArrayList<>();
    
    private final Usuario model;
    private CadastroView view;
    private final UsuarioService service;
    
    public CadastroPresenter(Usuario model, JDesktopPane panel, UsuarioService service) {
        this.model = model;
        this.service = service;
        
        criarView();
        panel.add(view);
    }
    
    public final void criarView() {
        view = new CadastroView(); 
        view.setVisible(true);
        view.getBotaoSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = view.getTxtNomeUsuario().getText();
                    if (service.buscarUsuarioPorNome(username).isEmpty()) {
                        String senha = new String(view.getTxtSenha().getPassword());
                        String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());

                        if (username.isEmpty() || senha.isEmpty()) {
                            JOptionPane.showMessageDialog(view, "Nome de usuário e senha são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else if(!senha.equals(confirmarSenha)){
                            JOptionPane.showMessageDialog(view, "Senhas diferentes na confirmação.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        var usuario = new Usuario(username, senha, false);
                        service.cadastrarUsuario(usuario);
                        notificarObservadores();
                        JOptionPane.showMessageDialog(view, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE); 
                        limparDados();
                    } else {
                        JOptionPane.showMessageDialog(view, "Já existe um usuário com esse nome.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();
                }
            }
        });
    }
    
    private void limparDados() {
        view.getTxtNomeUsuario().setText("");
        view.getTxtSenha().setText("");
        view.getTxtConfirmarSenha().setText("");
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
