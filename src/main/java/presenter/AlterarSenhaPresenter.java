package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import model.Usuario;
import observer.Observer;
import service.*;
import view.AlterarSenhaView;
import log.Log;

public class AlterarSenhaPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private final Usuario model;
    private AlterarSenhaView view;

    private final ValidadorSenhaService validadorService;
    private ValidadorTrocarSenhaService validadorSenhaService;
    private final UsuarioService service;
    private final LogService logService = new LogService();

    public AlterarSenhaPresenter(Usuario model, JDesktopPane panel, UsuarioService service) {
        this.model = model;
        this.service = service;
        this.validadorService = new ValidadorSenhaService();

        criarView();
        panel.add(view);
    }

    private void criarView() {
        view = new AlterarSenhaView();
        view.setVisible(true);
        view.toFront();
        view.getTxtNomeUsuario().setText(model.getUserName());
        configurarListeners();
    }

    private void configurarListeners() {
        view.getBtnSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarAlteracaoSenha();
            }
        });
    }

    private void processarAlteracaoSenha() {
        logService.configuraLog();
        Log log = logService.getLog();

        try {
            String senhaAtual = new String(view.getTxtSenhaAtual().getPassword());
            String senha = new String(view.getTxtSenha().getPassword());
            String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());
            var usuarioSalvo = service.buscarUsuarioPorNome(view.getTxtNomeUsuario().getText());

            validadorSenhaService = new ValidadorTrocarSenhaService(validadorService, view, log, senhaAtual, usuarioSalvo.get().getSenha(), senha, confirmarSenha);
            if (!validadorSenhaService.validar()) {
                registrarLog(log, "senhas incompativeis");
                return;
            }

            model.setSenha(senha);
            service.atualizarUsuario(model);
            notificarObservadores();
            registrarLog(log, null);
            JOptionPane.showMessageDialog(view, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCamposSenha();

        } catch (Exception exception) {
            registrarLog(log, "Erro inesperado");
            exception.printStackTrace();
        }
    }

    private void limparCamposSenha() {
        view.getTxtSenha().setText("");
        view.getTxtConfirmarSenha().setText("");
    }

    private void registrarLog(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Alterar senha de usuário" : "Ao alterar senha",
                view.getTxtNomeUsuario().getText(),
                model.getUserName(),
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
