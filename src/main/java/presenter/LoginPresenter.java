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
import observer.Observer;
import service.LogService;
import service.UsuarioService;
import view.CadastroView;
import view.LoginView;
import view.MainView;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */
public class LoginPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private final UsuarioService service;
    private CadastroPresenter cadastroPresenter;
    private final MainView mainView;
    private AlterarSenhaPresenter alterarSenhaPresenter;
    private Usuario model;
    private LoginView view;
    private CadastroView cadastroView;
    private final JDesktopPane desktopPane;
    private final LogService logService; // Adicionando o LogService

    public LoginPresenter(Usuario model, JDesktopPane panel, UsuarioService service, MainView mainView, LogService logService) {
        this.model = model;
        this.desktopPane = panel;
        this.service = service;
        this.mainView = mainView;
        this.logService = logService; // Inicializando o LogService

        criarView();
    }

    public final void criarView() {
        
        mostrarView();
        
        view.getBotaoLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    String nomeDigitado = view.getTxtNomeUsuario().getText();
                    String senhaDigitada = getSenha(view.getTxtSenha());
                    
                    Log log = logService.getLog(); // Obtendo a instância de Log

                    if (!camposIsNull(nomeDigitado, senhaDigitada)) {
                        String username = view.getTxtNomeUsuario().getText();
                        var usuario = service.buscarUsuarioPorNome(nomeDigitado);
                        model.setUsername(nomeDigitado);
                        model.setId(usuario.get().getId());
                        if (usuarioEncontrado(usuario)) {
                            if (usuario.get().getSenha().equals(senhaDigitada)) {
                                JOptionPane.showMessageDialog(view, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                model = usuario.get();
                                mainView.getLblNomeUsuarioLogado().setText(model.getUserName());
                                mainView.getLblTipoUsuarioLogado().setText(model.getTipo());
                                view.dispose();
                                logarUsuario();

                                if (log != null) {
                                    log.gravarLog("Login de usuário", username, model.getTipo(), true, null); // Registrar log
                                }

                                mostrarAlterarSenhaView();
                            } else {
                                JOptionPane.showMessageDialog(view, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                                if (log != null) {
                                    log.gravarLog("Erro: Login de usuário", username, model.getTipo(), false, "Senha incorreta"); // Registrar log
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(view, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                            if (log != null) {
                                log.gravarLog("Erro: Login de usuário", username, model.getTipo(), false, "Usuario nao cadastrado"); // Registrar log
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(view, "Os campos de nome e senha não podem estar vazios.", "Erro", JOptionPane.WARNING_MESSAGE);
                        if (log != null) {
                            log.gravarLog("Erro: Login de usuário", nomeDigitado, model.getTipo(), false, "Campo vazio"); // Registrar log
                        }
                    }
                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                }
            }
        });

        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarCadastroView();
                    view.dispose();
                } catch (NumberFormatException exception) {
                    exception.getStackTrace();
                }
            }
        });
    }

    private boolean camposSaoValidos(String nome, String senha) {
        return nome != null && !nome.isEmpty() && senha != null && !senha.isEmpty();
    }

    private boolean senhaCorreta(Usuario usuario, String senhaDigitada) {
        return usuario.getSenha().equals(senhaDigitada);
    }

    private void efetuarLogin(Usuario usuario) {
        JOptionPane.showMessageDialog(view, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        model = usuario;
        view.dispose();
        logarUsuario();
    }

    private void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(view, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
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
        cadastroPresenter = new CadastroPresenter(model, desktopPane, service, mainView, logService); // Passando o LogService
    }

    private void mostrarAlterarSenhaView() {
        alterarSenhaPresenter = new AlterarSenhaPresenter(model, desktopPane, service, logService);
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
        mainView.setUsuario(model);
        notificarObservadores();
    }

    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
