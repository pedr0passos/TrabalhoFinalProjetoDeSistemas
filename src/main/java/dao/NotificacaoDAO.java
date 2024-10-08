/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Notificacao;

/**
 *
 * @author Catterina Salvador
 */
public interface NotificacaoDAO {
    
    List<Notificacao> buscarPorIdDestinatario(UUID idUsuarioDestinatario);
    
    void lerNotificacao(UUID idNotificacao);
    
    void enviarNotificacao(Notificacao notificacao);

    public Optional<Notificacao> buscarPorId(UUID idNotificacao);
    
}
