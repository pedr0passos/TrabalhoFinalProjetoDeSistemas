/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.NotificacaoDAO;
import dao.NotificacaoDAOSqlite;
import java.util.List;
import java.util.Optional;
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
    
    public Optional<Notificacao> buscarPorId(UUID idNotificacao){
        return notificacaoDAO.buscarPorId(idNotificacao);
    }
     
    public List<Notificacao> buscarPorIdDestinatario(UUID idUsuarioDestinatario){
        return notificacaoDAO.buscarPorIdDestinatario(idUsuarioDestinatario);
    }
    
    public void lerNotificacao(UUID idNotificacao){
        notificacaoDAO.lerNotificacao(idNotificacao);
    }
    
    public Integer countNotificacoesLidasPorDestinatario(UUID idUsuarioDestinatario){
        List<Notificacao> notificacoes = buscarPorIdDestinatario(idUsuarioDestinatario);
        var count = 0;
        for (Notificacao notificacao : notificacoes){
            if(notificacao.isLida()){
                count++;
            }
        }
        return count;
    }
    
    public Integer countNotificacoesPorDestinatario(UUID idUsuarioDestinatario){
        List<Notificacao> notificacoes = buscarPorIdDestinatario(idUsuarioDestinatario);
        return notificacoes.size();
    }
    
    public void enviarNotificacao(Notificacao notificacao){
        notificacaoDAO.enviarNotificacao(notificacao);
    }
    

}
