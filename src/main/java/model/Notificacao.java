/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.UUID;

/**
 *
 * @author pedro
 */
public class Notificacao {
    private UUID id;
    private UUID id_usuario_remetente;
    private UUID id_usuario_destinatario;
    private String titulo;
    private String conteudo;
    private boolean lida;
    private static int notificacoesEnviadas;
    private static int notificacoesLidas;

    public Notificacao(Long id, String titulo, String conteudo) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.lida = false;
    }

    public Notificacao() {}

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public boolean isLida() {
        return lida;
    }

    public static int getNotificacoesEnviadas() {
        return notificacoesEnviadas;
    }

    public static int getNotificacoesLidas() {
        return notificacoesLidas;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public UUID getId_usuario_remetente() {
        return id_usuario_remetente;
    }

    public UUID getId_usuario_destinatario() {
        return id_usuario_destinatario;
    }
    

    public static void setNotificacoesEnviadas(int notificacoesEnviadas) {
        Notificacao.notificacoesEnviadas = notificacoesEnviadas;
    }

    public static void setNotificacoesLidas(int notificacoesLidas) {
        Notificacao.notificacoesLidas = notificacoesLidas;
    }
}
