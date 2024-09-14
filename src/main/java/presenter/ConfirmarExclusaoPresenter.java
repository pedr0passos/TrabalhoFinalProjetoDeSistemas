package presenter;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import log.Log;
import model.Usuario;
import observer.Observer;
import service.LogService;
import service.UsuarioService;
import singleton.UsuarioLogadoSingleton;
import view.ConfirmarExclusaoView;

/**
 * 
 * @author João
 */
public class ConfirmarExclusaoPresenter {
     private final List<Observer> observers = new ArrayList<>();

    private final ConfirmarExclusaoView view;
    private final UsuarioService usuarioService;
    private final JDesktopPane desktopPane;
    private final UUID idusuario;
    private final LogService logService = new LogService();

    public ConfirmarExclusaoPresenter(JDesktopPane desktopPane, UsuarioService usuarioService, UUID usuario) {
        this.desktopPane = desktopPane;
        this.usuarioService = usuarioService;
        this.idusuario = usuario;
        this.view = new ConfirmarExclusaoView();
        desktopPane.add(view);
        view.setVisible(true);

        configurarView();
    }

    private void configurarView() {
        view.getBtnSim().addActionListener(new ActionListener() { // Botão "Sim"
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarExclusao();
                notificarObservadores();
            }
        });

        view.getBtnNao().addActionListener(new ActionListener() { // Botão "Não"
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarExclusao();
            }
        });
    }

    private void confirmarExclusao() {
        logService.configuraLog();
        Log log = logService.getLog();
        var usuarioTemp = usuarioService.buscarUsuarioPorId(idusuario.toString());
        try {
            
            usuarioService.deletarUsuario(idusuario);
            JOptionPane.showMessageDialog(view, "Usuário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            registrarLog(log, null, usuarioTemp.get());
            view.dispose();
        } catch (HeadlessException ex) {
            exibirMensagemErro("Erro ao excluir usuário: " + ex.getMessage());
            registrarLog(log, ex.getMessage(),usuarioTemp.get());
        }
    }

    private void cancelarExclusao() {
        view.dispose(); // Fecha a janela sem excluir o usuário
    }

    private void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(view, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void registrarLog(Log log, String mensagemErro, Usuario usuario) {
        
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Exclusão de usuário" : "Erro: Exclusão de usuário",
                usuario.getUserName(),
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
