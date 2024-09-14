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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import log.Log;
import model.Notificacao;
import model.Usuario;
import observer.*;
import service.LogService;
import service.NotificadoraService;
import service.UsuarioService;
import singleton.UsuarioLogadoSingleton;
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
        view.setVisible(false);
        desktopPane.add(view);
        
        view.getBtnEnviar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                Log log = logService.getLog();
                
                String username = view.getJSelectUsuario().getSelectedItem().toString();
                String titulo = view.getTxtTitulo().getText();
                String mensagem = view.getTxtMensagemNotificacao().getText();
                
                if(username.isEmpty()){
                    JOptionPane.showMessageDialog(view, "O nome de usuário é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                    //REGISTRAR LOG DE ERRO
                    return;
                }
                if(titulo.isEmpty()){
                    JOptionPane.showMessageDialog(view, "O título é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                    //REGISTRAR LOG DE ERRO
                    return;
                }
                if(mensagem.isEmpty()){
                    JOptionPane.showMessageDialog(view, "A mensagem é obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);
                    //REGISTRAR LOG DE ERRO
                    return;
                }

                Usuario usuario = usuarioService.buscarUsuarioPorNome(username).get();
                
                Notificacao novaNotificacao = new Notificacao(usuario.getId(), titulo, mensagem);

                service.enviarNotificacao(novaNotificacao);
                view.getTxtTitulo().setText("");
                view.getTxtMensagemNotificacao().setText("");
                JOptionPane.showMessageDialog(view, "Notificação Enviada com Sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                notificarObservadores();
                //Registrar log de SUCESSO
            }
        });
    }
    
    public void atualizarView(){
        List<Usuario> usuarioList = usuarioService.listarUsuarios();
        view.getJSelectUsuario().removeAllItems();
        for(Usuario usuario : usuarioList){
            if(!usuario.getId().equals(UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getId())){
                view.getJSelectUsuario().addItem(usuario.getUserName());
            }
        }
    }
    
    public void setVisible() {
        this.view.setVisible(true);
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
