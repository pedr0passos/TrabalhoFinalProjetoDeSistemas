package presenter;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import view.*;
import model.Usuario;
import observer.Observer;
import service.UsuarioService;

public class LoginPresenter {
    
    private final List<Observer> observers = new ArrayList<>();
    private final UsuarioService service;
    private CadastroPresenter cadastroPresenter;
    private final MainView mainView;
    
    private Usuario model;
    private LoginView view;
    private final JDesktopPane panel;
    
    public LoginPresenter(Usuario model, JDesktopPane panel, UsuarioService service, MainView mainView) {
        this.model = model;
        this.panel = panel;
        this.service = service;
        this.mainView = mainView;
        
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

                if (camposSaoValidos(nomeDigitado, senhaDigitada)) {
                    Optional<Usuario> usuario = service.buscarUsuarioPorNome(nomeDigitado);

                    if (usuario.isEmpty()) {
                        exibirMensagemErro("Usuário não encontrado!");
                        return;
                    }

                    if (!usuario.get().getIsAutorizado()) {
                        exibirMensagemErro("Usuário não autorizado. Solicite autorização ao administrador.");
                        return;
                    }

                    if (!senhaCorreta(usuario.get(), senhaDigitada)) {
                        exibirMensagemErro("Senha incorreta!");
                        return;
                    }

                    efetuarLogin(usuario.get());
                } else {
                    exibirMensagemErro("Os campos de nome e senha não podem estar vazios.");
                }
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
        }});

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
    
    private String getSenha(JPasswordField campo) {
        var senhaArray = campo.getPassword();
        return new String(senhaArray);
    }
    
    private void mostrarView() {
        view = new LoginView();
        panel.add(view);
        view.setVisible(true);
    }
    
    private void mostrarCadastroView() {
        cadastroPresenter = new CadastroPresenter(model, panel, service);
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
