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
import log.Log;
import model.Usuario;
import observer.Observer;
import service.LogService;
import service.UsuarioService;
import view.CadastroView;
import view.ConfiguracaoView;
import view.MainView;

/**
 *
 * @author pedro
 */
public class CadastroPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private final Usuario model;
    private CadastroView view;
    private final UsuarioService service;
    private final JDesktopPane desktopPane;
    private final MainView mainView;
    private final LogService logService; // Adicionando o LogService
    private LoginPresenter lP;

    public CadastroPresenter(Usuario model, JDesktopPane desktopPane, UsuarioService service, MainView mainView, LogService logService) {
        this.model = model;
        this.service = service;
        this.desktopPane = desktopPane;
        this.mainView = mainView;
        this.logService = logService; // Inicializando o LogService

        criarView();
        desktopPane.add(view);
    }

    public final void criarView() {
        view = new CadastroView();
        view.setVisible(true);
        view.getBotaoSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log log = logService.getLog(); // Obtendo a instância de Log

                try {
                    String username = view.getTxtNomeUsuario().getText();
                    if (service.buscarUsuarioPorNome(username).isEmpty()) {
                        String senha = new String(view.getTxtSenha().getPassword());
                        String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());

                        if (username.isEmpty() || senha.isEmpty()) {
                            JOptionPane.showMessageDialog(view, "Nome de usuário e senha são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                            if (log != null) {
                                log.gravarLog("Erro: Inclusão de usuário", username, model.getTipo(), false, "Campos Invalidos"); // Registrar log
                            }
                            return;
                        } else if (!senha.equals(confirmarSenha)) {
                            JOptionPane.showMessageDialog(view, "Senhas diferentes na confirmação.", "Erro", JOptionPane.ERROR_MESSAGE);
                            if (log != null) {
                                log.gravarLog("Erro: Inclusão de usuário", username, model.getTipo(), false, "Senhas diferentes"); // Registrar log
                            }
                            return;
                        }
                        var usuario = new Usuario(username, senha, false);
                        service.cadastrarUsuario(usuario);
                        notificarObservadores();

                        if (log != null) {
                            log.gravarLog("Cadastro de usuário", username, model.getTipo(), true, null); // Registrar log
                        }

                        JOptionPane.showMessageDialog(view, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        limparDados();
                        view.dispose();
                        voltarLogin(); // Redireciona para a tela de login após o cadastro
                    } else {
                        if (log != null) {
                            log.gravarLog("Erro: Inclusão de usuário", username, model.getTipo(), false, "Já existe um usuário com esse nome."); // Registrar log
                        }
                        JOptionPane.showMessageDialog(view, "Já existe um usuário com esse nome.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(view, "Erro inesperado", "Erro", JOptionPane.ERROR_MESSAGE);
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

    private void voltarLogin() {
        lP = new LoginPresenter(model, desktopPane, service, mainView, logService); // Passando o LogService para o LoginPresenter
    }

    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
