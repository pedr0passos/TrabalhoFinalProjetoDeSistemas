/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;
import model.Notificacao;
import service.NotificadoraService;
import singleton.UsuarioLogadoSingleton;

import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import log.Log;
import main.Main;
import model.Usuario;
import service.LogService;
import view.NotificacoesView;

/**
 *
 * @author pedro
 */

public class NotificacoesPresenter {
    
    private Usuario model;
    private NotificacoesView view;
    private final LogService logService= new LogService();
    private JDesktopPane panel;
    private NotificadoraService service;
    
    public NotificacoesPresenter( JDesktopPane panel, NotificadoraService service) {
        this.panel = panel;       
        this.service = service;
        
        gerarView();
        view.getBtnBuscar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    String nomeBusca = view.getTxtBuscarNotificacoes().getText().trim();
            
                    if (nomeBusca.isEmpty()) {
                        atualizarView(); 
                        return;
                    }
                    List<Notificacao> notificacoes = service.buscarPorIdDestinatario(UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getId());
                    DefaultTableModel tableModel = (DefaultTableModel) view.getTbNotificacoes().getModel();
                    tableModel.setRowCount(0); 
                   
                    for (Notificacao notificacao : notificacoes) {
                        if (notificacao.getTitulo().toLowerCase().contains(nomeBusca.toLowerCase())) {
                            tableModel.addRow(new Object[] {
                                notificacao.getId().toString(),
                                notificacao.getDataCriacao().toString(),
                                notificacao.getTitulo(),
                                notificacao.getConteudo()
                            });
                        }
                    }
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });
        
        view.getBtnVisualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logService.configuraLog();
                Log log = logService.getLog();
                var row = view.getTbNotificacoes().getSelectedRow();
                if(row != -1){
                    Notificacao notificacao = new Notificacao();
                    UUID id = UUID.fromString(view.getTbNotificacoes().getValueAt(row, 0).toString());
                    service.lerNotificacao(id);
                    registrarLog(log, null);
                    new VisualizacaoNotificacoesPresenter(id, service, panel);
                }
            }
        });
    }
    
    private void gerarView() {
        view = new NotificacoesView();
        panel.add(view);
        view.setVisible(false);
    }
    
    public void setVisible(){
        view.setVisible(true);
    }
    
    public void atualizarView(){
        this.model = UsuarioLogadoSingleton.getInstancia().getUsuarioLogado();
        List<Notificacao> notificacoes = service.buscarPorIdDestinatario(UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getId());
        
        DefaultTableModel tableModel = (DefaultTableModel) view.getTbNotificacoes().getModel();
        tableModel.setRowCount(0);
        
        for (Notificacao notificacao : notificacoes){
            tableModel.addRow(new Object[] {
                notificacao.getId().toString(),
                notificacao.getDataCriacao().toString(),
                notificacao.getTitulo(),
                notificacao.getConteudo()
            });
        }
    }
     private void registrarLog(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                    mensagemErro == null ? "Mensagem visualizada" : "Mensagem visualizada",
                    UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                    UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                    mensagemErro == null,
                    mensagemErro
            );
        }
    }
}
