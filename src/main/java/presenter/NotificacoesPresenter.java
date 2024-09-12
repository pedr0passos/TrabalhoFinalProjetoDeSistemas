/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import javax.swing.JDesktopPane;
import model.Usuario;
import service.UsuarioService;
import view.NotificacoesView;

/**
 *
 * @author pedro
 */

public class NotificacoesPresenter {
    
    private Usuario model;
    private NotificacoesView view;
    
    private JDesktopPane panel;
    private UsuarioService service;
    
    public NotificacoesPresenter(Usuario usuario, JDesktopPane panel, UsuarioService service) {
        model = usuario;
        this.panel = panel;       
        this.service = service;
        
        gerarView();
    }
    
    private void gerarView() {
        view = new NotificacoesView();
        panel.add(view);
        view.setVisible(true);
    }
    
}
