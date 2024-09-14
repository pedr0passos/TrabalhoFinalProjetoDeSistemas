package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import model.Usuario;
import observer.Observer;
import service.LogService;
import service.UsuarioService;
import view.AlterarSenhaView;
import log.Log;
import service.ValidadorSenhaService;

/**
 *
 * @author Joao Victor
 */
public class AlterarSenhaPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private final Usuario model;
    private AlterarSenhaView view;
    
    private final ValidadorSenhaService validadorService;
    private final UsuarioService service;
    private final LogService logService= new LogService();

    public AlterarSenhaPresenter(Usuario model, JDesktopPane panel, UsuarioService service) {
        this.model = model;
        this.service = service;
        logService.configuraLog();
        
        validadorService = new ValidadorSenhaService();
        
        criarView();
        panel.add(view);
    }

    public final void criarView() {

        view = new AlterarSenhaView();
        view.setVisible(true);
        view.toFront(); 
        view.getTxtNomeUsuario().setText(model.getUserName());
        
        view.getBtnSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log log = logService.getLog(); // Obtendo a instância de Log

                try {
                    String senhaAtual = new String(view.getTxtSenhaAtual().getPassword());
                    String senha = new String(view.getTxtSenha().getPassword());
                    String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());
                    var usuarioSalvo = service.buscarUsuarioPorNome(view.getTxtNomeUsuario().getText());

                    if (senha.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Senha é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                        registrarLog(log, "Senha vazia");
                        return;
                    } else if (!senha.equals(confirmarSenha) || !senhaAtual.equals(usuarioSalvo.get().getSenha())) {
                        JOptionPane.showMessageDialog(view, "Senhas diferentes na confirmação.", "Erro", JOptionPane.ERROR_MESSAGE);
                        registrarLog(log, "Senha de confirmação vazia");
                        return;
                    }

                    try {
                        validadorService.validarSenha(senha);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                        registrarLog(log, "Erro de validação da senha");
                        return;
                    }
                    
                    model.setSenha(senha);
                    service.atualizarUsuario(model);

                    notificarObservadores();
                    registrarLog(log, null);
                    JOptionPane.showMessageDialog(view, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparDados();

                } catch (NumberFormatException exception) {
                    registrarLog(log, "Erro inesperado");
                    exception.getStackTrace();
                }
            }
        });
    }

    private void limparDados() {
        // Limpa apenas os campos de senha
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
    
    private void registrarLog(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                    mensagemErro == null ? "Alterar senha de usuário" : "Alterar senha de usuário",
                    view.getTxtNomeUsuario().getText(),
                    model.getUserName(),
                    mensagemErro == null,
                    mensagemErro
            );
        }
    }
}
