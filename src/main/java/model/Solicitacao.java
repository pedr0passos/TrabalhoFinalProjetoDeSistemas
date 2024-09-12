package model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */

public class Solicitacao {
    private UUID id;
    private Usuario usuario;
    private LocalDate dataSolicitacao; 
    private boolean aprovada;

    public Solicitacao(UUID id, Usuario usuario, LocalDate dataSolicitacao, boolean aprovada) {
        this.id = id;
        this.usuario = usuario;
        this.dataSolicitacao = dataSolicitacao;
        this.aprovada = aprovada;
    }
    
    public UUID getId() {
        return id;
    }

    public String getNomeUsuario() {
        return usuario.getUserName();
    }

    public UUID getIdUsuario() {
        return usuario.getId();
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public boolean isAprovada() {
        return aprovada;
    }
}
