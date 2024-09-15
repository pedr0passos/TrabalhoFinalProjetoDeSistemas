package presenter;

import javax.swing.JDesktopPane;
import log.Log;
import model.*;
import service.*;
import singleton.UsuarioLogadoSingleton;
import view.*;

/**
 * Classe responsável por apresentar os detalhes de um usuário selecionado.
 */
public class VisualizarUsuarioPresenter {

    private VisualizarUsuarioView view;
    private final Usuario model;
    private final JDesktopPane desktopPane;
    private final NotificadoraService service;
    private final LogService logService = new LogService();

    public VisualizarUsuarioPresenter(JDesktopPane desktopPane, Usuario model, NotificadoraService service) {
        this.desktopPane = desktopPane;
        this.model = model;
        this.service = service;
    }

    public void criarView() {
        view = new VisualizarUsuarioView();
        desktopPane.add(view);
        view.setVisible(true);

        preencherDadosUsuario();
    }

    private void preencherDadosUsuario() {

        logService.configuraLog();
        Log log = logService.getLog();
        registrarLog(log, null);
        var notificacoesLidas = service.countNotificacoesLidasPorDestinatario(model.getId());
        model.setNumeroNotificacoesLidas(notificacoesLidas);

        view.setTitle(model.getUserName());

        view.getTxtNome().setText(model.getUserName());
        view.getTxtDataCriacao().setText(model.getDataCriacao().toString());
        view.getTxtTipo().setText(model.getTipo());
        view.getTxtTotalNotificaoes().setText(String.valueOf(service.countNotificacoesPorDestinatario(model.getId())));
        view.getTxtNotificacoesLidas().setText(String.valueOf(service.countNotificacoesLidasPorDestinatario(model.getId())));

        view.getTxtNome().setEditable(false);
        view.getTxtDataCriacao().setEditable(false);
        view.getTxtTipo().setEditable(false);
        view.getTxtTotalNotificaoes().setEditable(false);
        view.getTxtNotificacoesLidas().setEditable(false);
    }
    
    private void registrarLog(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Visualizar usuário" : "Ao visualizar usuario",
                model.getUserName(),
                UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                mensagemErro == null,
                mensagemErro
            );
        }
    }
}
