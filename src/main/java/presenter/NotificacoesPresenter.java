/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Notificacao;
import service.NotificadoraService;
import singleton.UsuarioLogadoSingleton;

import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import main.Main;
import model.Usuario;
import view.NotificacoesView;

/**
 *
 * @author pedro
 */

public class NotificacoesPresenter {
    
    private Usuario model;
    private NotificacoesView view;
    
    private JDesktopPane panel;
    private NotificadoraService service;
    
    public NotificacoesPresenter(Usuario usuario, JDesktopPane panel, NotificadoraService service) {
        model = usuario;
        this.panel = panel;       
        this.service = service;
        
        gerarView();
        atualizarView();
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
    }
    
    private void gerarView() {
        view = new NotificacoesView();
        panel.add(view);
        view.setVisible(true);
    }
    
    public void atualizarView(){
        List<Notificacao> notificacoes = service.buscarPorIdDestinatario(UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getId());
        
        DefaultTableModel tableModel = (DefaultTableModel) view.getTbNotificacoes().getModel();
        
        for (Notificacao notificacao : notificacoes){
            tableModel.addRow(new Object[] {
                notificacao.getDataCriacao().toString(),
                notificacao.getTitulo(),
                notificacao.getConteudo()
            });
        }
    }
}
