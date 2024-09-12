package presenter;

import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import model.*;
import observer.Observer;
import service.UsuarioService;
import view.CadastroView;

/**
 *
 * @author pedro
 */

public class CadastroPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private Usuario model;
    private CadastroView view;
    private final UsuarioService service;
    private final boolean possuiUsuario;

    public CadastroPresenter(Usuario model, JDesktopPane panel, UsuarioService service) {
        this.model = model;
        this.service = service;
        this.possuiUsuario = service.possuiUsuario();
        criarView();
        panel.add(view);
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
                try {
                    String username = view.getTxtNomeUsuario().getText();

                    Optional<Usuario> usuarioExistente = service.buscarUsuarioPorNome(username);

                    if (usuarioExistente.isEmpty()) {
                        // Cadastro de um novo usuário com isAutorizado = false
                        String senha = new String(view.getTxtSenha().getPassword());
                        String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());

                        if (username.isEmpty() || senha.isEmpty()) {
                            JOptionPane.showMessageDialog(view, "Nome de usuário e senha são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else if (!senha.equals(confirmarSenha)) {
                            JOptionPane.showMessageDialog(view, "Senhas diferentes na confirmação.", "Erro", JOptionPane.ERROR_MESSAGE);
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
                        limparDados();
                    } else {
                        JOptionPane.showMessageDialog(view, "Já existe um usuário com esse nome.", "Erro", JOptionPane.ERROR_MESSAGE);
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

    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
