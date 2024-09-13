/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.NotificacaoDAO;
import dao.NotificacaoDAOSqlite;
import java.util.List;
import java.util.UUID;
import model.Notificacao;

/**
 *
 * @author pedro
 */

public class NotificadoraService {
    private final NotificacaoDAO notificacaoDAO;
     
    public NotificadoraService(){
        this.notificacaoDAO = new NotificacaoDAOSqlite();
    }
     
    public List<Notificacao> buscarPorIdDestinatario(UUID idUsuarioDestinatario){
        return notificacaoDAO.buscarPorIdDestinatario(idUsuarioDestinatario);
    }
    
    public void lerNotificacao(UUID idNotificacao){
        notificacaoDAO.lerNotificacao(idNotificacao);
    }
    
    public Integer countNotificacoesLidasPorDestinatario(UUID idUsuarioDestinatario){
        return notificacaoDAO.countNotificacoesLidasPorDestinatario(idUsuarioDestinatario);
    }
    
    public void enviarNotificacao(Notificacao notificacao){
        notificacaoDAO.enviarNotificacao(notificacao);
    }
    

}
