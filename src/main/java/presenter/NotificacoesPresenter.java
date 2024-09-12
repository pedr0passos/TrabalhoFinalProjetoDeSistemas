/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import model.Notificacao;
import service.NotificadoraService;
import view.MainView;
import view.NotificacoesView;

/**
 *
 * @author pedro
 */
public class NotificacoesPresenter {
    
    private NotificacoesView view;
    private NotificadoraService service; 
    private final MainView mainView;
    private final JDesktopPane desktopPane;
    
    public NotificacoesPresenter(JDesktopPane panel, NotificadoraService service, MainView mainView){
        this.desktopPane = panel;
        this.service = service;
        this.mainView = mainView;
        
        criarView();
        panel.add(view);
    }
    
    public final void criarView(){
        view = new NotificacoesView();
        view.setVisible(true);
        
        
    }
    
    
}
