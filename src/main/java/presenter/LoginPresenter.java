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
    private AlterarSenhaPresenter alterarSenhaPresenter;
    
    private Usuario model;
    private LoginView view;
    private CadastroView cadastroView;
    private final JDesktopPane desktopPane;
    
    public LoginPresenter(Usuario model, JDesktopPane panel, UsuarioService service, MainView mainView) {
        this.model = model;
        this.desktopPane = panel;
        this.service = service;
        this.mainView = mainView;
        
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
                    String nomeDigitado = view.getTxtNomeUsuario().getText();
                    String senhaDigitada = getSenha(view.getTxtSenha());
                    
                    if (!camposIsNull(nomeDigitado, senhaDigitada)) {
                        var usuario = service.buscarUsuarioPorNome(nomeDigitado);
                        //esses dois models abaixo são as informações minimas necessarias a serem passadas para o AlterarSenhaPresenter, sem eles nao funciona passando apenas o model, pois o model é null
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
                                mostrarAlterarSenhaView();
                            } else {
                                JOptionPane.showMessageDialog(view, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(view, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(view, "Os campos de nome e senha não podem estar vazios.", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                } catch ( NumberFormatException exception) {
                    System.out.println(Arrays.toString(exception.getStackTrace()));                   
                }
            }
        });
        
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarCadastroView();
                    view.dispose();
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });
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
    
    private void mostrarCadastroView() {
        cadastroPresenter = new CadastroPresenter(model, desktopPane, service);
    }
    
    private void mostrarAlterarSenhaView() {
        alterarSenhaPresenter = new AlterarSenhaPresenter(model, desktopPane, service);
     }
    
    public void adicionarObserver(Observer observer) {
        observers.add(observer);
    }
    
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }
    
    public void logarUsuario() {
        notificarObservadores();
    }
    
    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }

    }
}
