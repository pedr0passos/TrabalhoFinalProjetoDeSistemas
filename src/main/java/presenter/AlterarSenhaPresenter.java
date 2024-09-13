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

/**
 *
 * @author Joao Victor
 */
public class AlterarSenhaPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private final Usuario model;
    private AlterarSenhaView view;
    private final UsuarioService service;
    private final LogService logService; // Adicionando o LogService

    public AlterarSenhaPresenter(Usuario model, JDesktopPane panel, UsuarioService service, LogService logService) {
        this.model = model;
        this.service = service;
        this.logService = logService; // Inicializando o LogService
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
                    String senha = new String(view.getTxtSenha().getPassword());
                    String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());

                    if (senha.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Senha é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                        if (log != null) {
                            log.gravarLog("Erro: Alteração de senha", model.getUserName(), model.getTipo(), false, "Senha vazia"); // Registrar log
                        }
                        return;
                    } else if (!senha.equals(confirmarSenha)) {
                        JOptionPane.showMessageDialog(view, "Senhas diferentes na confirmação.", "Erro", JOptionPane.ERROR_MESSAGE);
                        if (log != null) {
                            log.gravarLog("Erro: Alteração de senha", model.getUserName(), model.getTipo(), false, "Senha de confirmação vazia"); // Registrar log
                        }
                        return;
                    }

                    // Atualizar a senha do usuário logado diretamente no modelo
                    model.setSenha(senha);

                    // Atualizar o usuário no serviço (presumindo que atualiza no banco de dados ou armazenamento)
                    service.atualizarUsuario(model);

                    notificarObservadores();

                    if (log != null) {
                        log.gravarLog("Alteração de senha", model.getUserName(), model.getTipo(), true, null); // Registrar log
                    }

                    JOptionPane.showMessageDialog(view, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparDados();

                } catch (NumberFormatException exception) {
                    if (log != null) {
                        log.gravarLog("Erro: Alteração de senha", model.getUserName(), model.getTipo(), false, "Erro ao alterar senha"); // Registrar log
                    }
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
}
