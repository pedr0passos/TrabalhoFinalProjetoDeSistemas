package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import log.Log;
import model.Usuario;
import observer.Observer;
import service.UsuarioService;
import view.EditarView;
import java.util.ArrayList;
import java.util.List;
import service.LogService;
import singleton.UsuarioLogadoSingleton;

/**
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 * @author Catterina Vittorazzi Salvador 
 */

public class EditarPresenter {

    private final List<Observer> observers = new ArrayList<>();
    
    private final UsuarioService service;
    private final LogService logService = new LogService();
    
    private EditarView view;
    private final JDesktopPane desktopPane;
    private Usuario model;

    public EditarPresenter(JDesktopPane panel, Usuario usuario, UsuarioService service) {
        this.desktopPane = panel;
        this.service = service;
        this.model = usuario;
    }

    public void criarView() {
        view = new EditarView();
        desktopPane.add(view);
        view.setVisible(true);
        
        view.getTxtNome().setText(model.getUserName());

        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEdicao();
            }
        });
    }

    public void salvarEdicao() {
        String novoNome = view.getTxtNome().getText();
        logService.configuraLog();
        Log log = logService.getLog();

        if (novoNome == null || novoNome.trim().isEmpty()) {
            exibirMensagemErro("O nome não pode estar vazio.");
            registrarLog(log, "Tentativa de salvar com nome vazio.");
            return;
        }

        try {
            model.setUsername(novoNome);
            service.atualizarUsuario(model);

            JOptionPane.showMessageDialog(view, "Usuário editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            registrarLog(log, null);
            notificarObservadores();

            view.dispose();
        } catch (Exception e) {
            exibirMensagemErro("Erro ao salvar alterações.");
            registrarLog(log, "Erro ao salvar usuário: " + e.getMessage());
        }
    }

    private void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(view, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void registrarLog(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Edição de usuário" : "Erro: Edição de usuário",
                model.getUserName(),
                UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
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

    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
