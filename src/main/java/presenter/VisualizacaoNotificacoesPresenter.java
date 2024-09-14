/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.swing.JDesktopPane;
import model.Notificacao;
import service.LogService;
import service.NotificadoraService;
import view.VisualizacaoNotificacaoView;

/**
 *
 * @author Pedro Henrique
 * @author Catterina
 */
public class VisualizacaoNotificacoesPresenter {
    private final Notificacao model;
    private VisualizacaoNotificacaoView view;
    private final NotificadoraService service;
    private final JDesktopPane desktopPane;

    public VisualizacaoNotificacoesPresenter(UUID idNotificacao, NotificadoraService service, JDesktopPane desktopPane) {
        this.service = service;
        this.model = service.buscarPorId(idNotificacao).get();
        this.desktopPane = desktopPane;
        
        configurarView();
        desktopPane.add(view);
    }
    
    private void configurarView(){
        view = new VisualizacaoNotificacaoView();
        view.setVisible(true);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = model.getDataCriacao().format(formatter);
        
        view.getTxtTitulo().setText(model.getTitulo());
        view.getTxtDataEnvio().setText(dataFormatada);
        view.getTxtConteudo().setText(model.getConteudo());
    }
}
