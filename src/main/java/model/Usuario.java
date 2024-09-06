/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author pedro
 * @author catterina
 */
public class Usuario {
    
    private UUID id;
    private String userName;
    private String senha;
    private String tipo;
    private boolean administrador;
    private LocalDate dataCriacao;
    private ArrayList<Notificacao> notificacoes;
    
    public Usuario() {}
    
    public Usuario(String userName, String senha, boolean administrador) {
        this.id = UUID.randomUUID();
        this.userName = userName;
        this.senha = senha;
        this.dataCriacao = LocalDate.now();
        this.administrador = administrador;
        if (administrador)
            this.tipo = "Administrador";
        else 
            this.tipo = "Usuário";
    }

    public UUID getId() {
        return this.id;
    }
    
    public String getUserName() {
        return userName;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public ArrayList<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setUsername(String userName) {
        this.userName = userName;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public void adicionarNotificacao(Notificacao novaNotificacao) {
        notificacoes.add(novaNotificacao);
    }
    
    private void lerNotificacao() {
        
    }
}
