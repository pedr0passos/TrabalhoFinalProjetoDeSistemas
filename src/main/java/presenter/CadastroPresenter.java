package presenter;

import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import model.*;
import observer.Observer;
import service.*;
import view.*;
import log.*;

/**
 *
 * @author pedro
 */

public class CadastroPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private Usuario model;
    private CadastroView view;
    private final UsuarioService service;
    private final JDesktopPane desktopPane;
    private final boolean possuiUsuario;
    private final MainView mainView;
    private final LogService logService; // Adicionando o LogService
    private LoginPresenter loginPresenter;

    public CadastroPresenter(Usuario model, JDesktopPane desktopPane, UsuarioService service, MainView mainView, LogService logService) {
        this.model = model;
        this.service = service;
        this.possuiUsuario = service.possuiUsuario();
        this.desktopPane = desktopPane;
        this.mainView = mainView;
        this.logService = logService; // Inicializando o LogService

        criarView();
        desktopPane.add(view);
    }

    public final void criarView() {
        view = new CadastroView();
        view.setVisible(true);

        if (possuiUsuario) {
            view.getBotaoSalvarUsuario().setText("Enviar Solicitação");
        }

        view.getBotaoSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log log = logService.getLog(); // Obtendo a instância de Log

                try {
                    String username = view.getTxtNomeUsuario().getText();

                    Optional<Usuario> usuarioExistente = service.buscarUsuarioPorNome(username);

                    if (usuarioExistente.isEmpty()) {
                        // Cadastro de um novo usuário com isAutorizado = false
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

                        Usuario novoUsuario = new Usuario(username, senha, false, false);
                        model = novoUsuario;
                        service.cadastrarUsuario(novoUsuario);

                        UUID idSolicitacao = UUID.randomUUID();
                        LocalDate dataSolicitacao = LocalDate.now();
                        Solicitacao solicitacao = new Solicitacao(idSolicitacao, novoUsuario, dataSolicitacao, false);
                        service.enviarSolicitacao(solicitacao);

                        JOptionPane.showMessageDialog(view, "Solicitação de cadastro enviada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        if (log != null) {
                            log.gravarLog("Cadastro de usuário", username, model.getTipo(), true, null); // Registrar log
                        }

                        JOptionPane.showMessageDialog(view, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        limparDados();
                        view.dispose();
                        voltarLogin(); // Redireciona para a tela de login após o cadastro
                    } else {
                        JOptionPane.showMessageDialog(view, "Já existe um usuário com esse nome.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                        if (log != null) {
                            log.gravarLog("Erro: Inclusão de usuário", model.getUserName(), model.getTipo(), false, "Já existe um usuário com esse nome."); // Registrar log
                        }
                        JOptionPane.showMessageDialog(view, "Já existe um usuário com esse nome.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        loginPresenter = new LoginPresenter(desktopPane, service, mainView, logService); 
    }

    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
