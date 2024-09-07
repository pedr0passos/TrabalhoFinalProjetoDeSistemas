package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import model.Usuario;
import observer.Observer;
import service.UsuarioService;
import view.AlterarSenhaView;
import log.*;

/**
 *
 * @author Joao Victor
 */
public class AlterarSenhaPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private final Usuario model;
    private AlterarSenhaView view;
    private final UsuarioService service;

    public AlterarSenhaPresenter(Usuario model, JDesktopPane panel, UsuarioService service) {
        this.model = model;
        this.service = service;
        criarView();
        panel.add(view);
    }

    public final void criarView() {
        view = new AlterarSenhaView();
        view.setVisible(true);
        view.toFront(); 
        
        // Exibir o nome do usuário logado
        view.getTxtNomeUsuario().setText(model.getUserName());
        
        view.getBtnAlterarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Log log;
//                if (true) {//ta true pq nao sei pegar a condição ainda
//                    log = new CsvAdapter(new CsvLog());
//                } else if (false) {//ta false pq nao sei pegar a condição ainda
                    log = new JsonAdapter(new JsonLog());
//                }
                try {
                    String senha = new String(view.getTxtSenha().getPassword());
                    String confirmarSenha = new String(view.getTxtConfirmarSenha().getPassword());
                    
                    if (senha.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Senha é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (!senha.equals(confirmarSenha)) {
                        JOptionPane.showMessageDialog(view, "Senhas diferentes na confirmação.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Atualizar a senha do usuário logado diretamente no modelo
                    model.setSenha(senha);
                    
                    // Atualizar o usuário no serviço (presumindo que atualiza no banco de dados ou armazenamento)
                    service.atualizarUsuario(model);

                    notificarObservadores();
                    log.gravarLog("Alteração de senha", model.getUserName(), "admin", true, null);//trocar "admin" para usuario.getTipo quando for implementado tipo
                    JOptionPane.showMessageDialog(view, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparDados();

                } catch (NumberFormatException exception) {
                    log.gravarLog("Alteração de senha", model.getUserName(), "admin", false, "Erro ao alterar senha");//trocar "admin" para usuario.getTipo quando for implementado tipo
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
