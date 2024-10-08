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
import singleton.UsuarioLogadoSingleton;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas 
 */

public class CadastroPresenter {

    private final List<Observer> observers = new ArrayList<>();
    
    
    private Usuario model;
    private CadastroView view;
    private final MainView mainView;
    private final JDesktopPane desktopPane;
    
    private final boolean possuiAdministrador;

    private final UsuarioService service;
    private final ValidadorSenhaService validadorService;
    private final LogService logService = new LogService();
    private final AdministradorService adminService;
    
    private LoginPresenter loginPresenter;
    
    public CadastroPresenter( 
            Usuario model, 
            JDesktopPane desktopPane, 
            UsuarioService service, 
            MainView mainView, 
            boolean criadoPelaMainView,
            AdministradorService adminService) {
        
        this.model = model;
        this.service = service;
        this.possuiAdministrador = adminService.existeAdministrador();
        this.desktopPane = desktopPane;
        this.mainView = mainView;
        this.adminService = adminService;
        this.validadorService = new ValidadorSenhaService();
        
        criarView(criadoPelaMainView);
        desktopPane.add(view);
    }

    public final void criarView(boolean criadoPelaMainView) {
        view = new CadastroView();

        if (criadoPelaMainView) {
            view.setVisible(false);
            view.getBotaoSalvarUsuario().setText("Salvar");
        } else {
            if (possuiAdministrador) {
                view.getBotaoSalvarUsuario().setText("Enviar Solicitação");
            }
            view.setVisible(true);
        } 

        view.getBotaoSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logService.configuraLog();
                Log log = logService.getLog();

                try {
                    
                    String username = view.getTxtNomeUsuario().getText();
                    Optional<Usuario> usuarioExistente = service.buscarUsuarioPorNome(username);

                    if (usuarioExistente.isEmpty()) {

                        String senha = new String(view.getTxtSenha().getPassword());
                        String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());
                        
                        if (username.isEmpty() || senha.isEmpty()) {
                            JOptionPane.showMessageDialog(view, "Nome de usuário e senha são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                            registrarLog(log, "Campos Invalidos");
                            return;
                        } else if (!senha.equals(confirmarSenha)) {
                            JOptionPane.showMessageDialog(view, "Senhas diferentes na confirmação.", "Erro", JOptionPane.ERROR_MESSAGE);
                            registrarLog(log, "Senhas diferentes");
                            return;
                        }

                        try {
                            validadorService.validarSenha(senha);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                            registrarLog(log, "Erro de validação da senha");
                            return;
                        }
                        
                        boolean permissao = false;
                        boolean administrador = true;
                        
                        if (criadoPelaMainView)
                            permissao = true;
                        
                        if (adminService.existeAdministrador())
                            administrador = false;
                        
                        Usuario novoUsuario = new Usuario(username, senha, administrador, permissao);
                        
                        model = novoUsuario;
                        service.cadastrarUsuario(novoUsuario);
                        registrarLog(log, null);

                        boolean enviouNotificacao = false;
                        
                        if (!criadoPelaMainView && possuiAdministrador) {
                            UUID idSolicitacao = UUID.randomUUID();
                            LocalDate dataSolicitacao = LocalDate.now();
                            Solicitacao solicitacao = new Solicitacao(idSolicitacao, novoUsuario, dataSolicitacao, false);
                            service.enviarSolicitacao(solicitacao);
                            JOptionPane.showMessageDialog(view, "Solicitação de cadastro enviada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            enviouNotificacao = true;
                        }
                        
                        if (!enviouNotificacao)
                            JOptionPane.showMessageDialog(view, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        
                        notificarObservadores();
                        limparDados();
                        
                        if (!criadoPelaMainView) {
                            view.setVisible(false);
                            voltarLogin(); 
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(view, "Já existe um usuário com esse nome.", "Erro", JOptionPane.ERROR_MESSAGE);
                        registrarLog(log, "Já existe um usuário com esse nome.");
                    }
                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
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
        loginPresenter = new LoginPresenter(desktopPane, service, mainView);
    }

    public void setVisible() {
        this.view.setVisible(true);
    }
    
    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    private void registrarLog(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                    mensagemErro == null ? "Cadastro de usuário" : "Inclusão de usuário",
                    view.getTxtNomeUsuario().getText(),
                    UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName() == null ? model.getUserName() : UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                    mensagemErro == null,
                    mensagemErro
            );
        }
    }
}
