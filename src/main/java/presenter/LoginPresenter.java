package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import log.Log;
import model.Usuario;
import singleton.*;
import observer.Observer;
import service.*;
import view.*;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */

public class LoginPresenter {

    private final List<Observer> observers = new ArrayList<>();
    
    private final UsuarioService service;
    private final LogService logService;
    
    private final MainView mainView;
    
    private CadastroPresenter cadastroPresenter;
    private AlterarSenhaPresenter alterarSenhaPresenter;
    
    private Usuario model;
    private LoginView view;
    private final JDesktopPane desktopPane;

    public LoginPresenter(JDesktopPane panel, UsuarioService service, MainView mainView, LogService logService) {
        
        this.model = UsuarioLogadoSingleton.getInstancia().getUsuarioLogado();
        this.desktopPane = panel;
        this.service = service;
        this.mainView = mainView;
        this.logService = logService;

        criarView();
    }

    public final void criarView() {
        
        mostrarView();
        
        view.getBotaoLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCadastroView();
                view.dispose();
            }
        });
    }

    private void realizarLogin() {
        try {
            
            String nomeDigitado = view.getTxtNomeUsuario().getText();
            String senhaDigitada = getSenha(view.getTxtSenha());
            
            Log log = logService.getLog();

            if (camposIsNull(nomeDigitado, senhaDigitada)) {
                exibirMensagemErro("Os campos de nome e senha não podem estar vazios.");
                registrarLog(log, "Campo vazio");
                return;
            }

            Optional<Usuario> usuarioOptional = service.buscarUsuarioPorNome(nomeDigitado);
            if (usuarioEncontrado(usuarioOptional)) {
                Usuario usuario = usuarioOptional.get();
                if (usuario.getSenha().equals(senhaDigitada)) {
                    JOptionPane.showMessageDialog(view, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    UsuarioLogadoSingleton.getInstancia().setUsuarioLogado(usuario);
                    model = usuario;
                    mainView.getLblNomeUsuarioLogado().setText(model.getUserName());
                    mainView.getLblTipoUsuarioLogado().setText(model.getTipo());
                    view.dispose();
                    logarUsuario();
                    registrarLog(log, null);
                } else {
                    exibirMensagemErro("Senha incorreta!");
                    registrarLog(log, "Senha incorreta");
                }
            } else {
                exibirMensagemErro("Usuário não encontrado!");
                registrarLog(log, "Usuário não cadastrado");
            }
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
    }

    private boolean camposIsNull(String nomeDigitado, String senhaDigitada) {
        return nomeDigitado == null || nomeDigitado.trim().isEmpty() || senhaDigitada == null || senhaDigitada.trim().isEmpty();
    }

    private boolean usuarioEncontrado(Optional<Usuario> usuario) {
        return usuario.isPresent();
    }

    private String getSenha(JPasswordField campo) {
        var senhaArray = campo.getPassword();
        return new String(senhaArray);
    }

    private void mostrarView() {
        view = new LoginView();
        desktopPane.add(view);
        view.setVisible(true);
    }

    private void mostrarCadastroView() {
        cadastroPresenter = new CadastroPresenter(model, desktopPane, service, mainView, logService);
    }

    private void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(view, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void registrarLog(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Login de usuário" : "Erro: Login de usuário",
                view.getTxtNomeUsuario().getText(),
                model.getTipo(),
                mensagemErro == null,
                mensagemErro
            );
        }
    }

    public void adicionarObserver(Observer observer) {
        observers.add(observer);
    }

    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    public void logarUsuario() {
        mainView.getLblNomeUsuarioLogado().setText(model.getUserName());
        mainView.getLblTipoUsuarioLogado().setText(model.getTipo());
        notificarObservadores();
    }

    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
