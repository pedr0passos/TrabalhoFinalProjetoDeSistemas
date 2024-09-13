/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import log.Log;
import model.Notificacao;
import model.Usuario;
import observer.*;
import service.LogService;
import service.NotificadoraService;
import service.UsuarioService;
import view.EnviarNotificacaoView;
import view.MainView;

/**
 *
 * @author Catterina Salvador
 */
public class EnviarNotificacaoPresenter {
    private EnviarNotificacaoView view;
    private final List<Observer> observers = new ArrayList<>();
    private NotificadoraService service;
    private UsuarioService usuarioService;
    private final JDesktopPane desktopPane;
    private final MainView mainView;
    private final LogService logService;

    
    public EnviarNotificacaoPresenter(JDesktopPane desktopPane, NotificadoraService service, UsuarioService usuarioService, MainView mainView, LogService logService){
        this.service = service;
        this.desktopPane = desktopPane;
        this.mainView = mainView;
        this.logService = logService;
        this.usuarioService = usuarioService;
        
        criarView();
    }
    public final void criarView(){
        view = new EnviarNotificacaoView();
        view.setVisible(true);
        desktopPane.add(view);
        atualizarView();
        
        view.getBtnEnviar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Log log = logService.getLog();
                
                String username = view.getJSelectUsuario().getSelectedItem().toString();
                String titulo = view.getTxtTitulo().getText();
                String mensagem = view.getTxtMensagemNotificacao().getText();
                
                Usuario usuario = usuarioService.buscarUsuarioPorNome(username).get();
                
                Notificacao novaNotificacao = new Notificacao(usuario.getId(), titulo, mensagem);
                service.enviarNotificacao(novaNotificacao);
                
            }
        });
    }
    
    public void atualizarView(){
        List<Usuario> usuarioList = usuarioService.listarUsuarios();
        view.getJSelectUsuario().removeAll();
        for(Usuario usuario : usuarioList){
            view.getJSelectUsuario().addItem(usuario.getUserName());
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
