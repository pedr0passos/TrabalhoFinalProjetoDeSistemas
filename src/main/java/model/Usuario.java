/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author pedro
 * @author catterina
 */
public class Usuario {
    
    private Long id;
    private String userName;
    private String senha;
    private ArrayList<Notificacao> notificacoes;
    
    public Usuario() {}
    
    public Usuario(String userName, String senha) {
        this.userName = userName;
        this.senha = senha;
    }

    public Long getId() {
        return this.id;
    }
    
    public String getUsername() {
        return userName;
    }

    public String getSenha() {
        return senha;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setUsername(String userName) {
        this.userName = userName;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void adicionarNotificacao(Notificacao novaNotificacao) {
        notificacoes.add(novaNotificacao);
    }
    
    private void lerNotificacao() {
        
    }
}
