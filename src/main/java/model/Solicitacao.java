package model;

import java.time.LocalDate;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */

public class Solicitacao {
    private Long id;
    private Usuario usuario;
    private LocalDate dataSolicitacao; 
    private boolean aprovada;

    public Solicitacao(Long id, Usuario usuario, LocalDate dataSolicitacao, boolean aprovada) {
        this.id = id;
        this.usuario = usuario;
        this.dataSolicitacao = dataSolicitacao;
        this.aprovada = aprovada;
    }

    public Long getId() {
        return id;
    }

    public String getNomeUsuario() {
        return usuario.getUsername();
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public boolean isAprovada() {
        return aprovada;
    }
}
