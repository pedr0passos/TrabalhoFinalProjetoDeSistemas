/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author pedro
 */
public class Notificacao {
    private UUID id;
    private UUID idUsuarioDestinatario;
    private String titulo;
    private String conteudo;
    private boolean lida;
    private LocalDate dataCriacao;
    private static int notificacoesEnviadas;
    private static int notificacoesLidas;

    public Notificacao(UUID idUsuarioDestinatario, String titulo, String conteudo) {
        this.id = UUID.randomUUID();
        this.idUsuarioDestinatario = idUsuarioDestinatario;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.lida = false;
        this.dataCriacao = LocalDate.now();
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

    public UUID getIdUsuarioDestinatario() {
        return idUsuarioDestinatario;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setIdUsuarioDestinatario(UUID idUsuarioDestinatario) {
        this.idUsuarioDestinatario = idUsuarioDestinatario;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public static void setNotificacoesEnviadas(int notificacoesEnviadas) {
        Notificacao.notificacoesEnviadas = notificacoesEnviadas;
    }

    public static void setNotificacoesLidas(int notificacoesLidas) {
        Notificacao.notificacoesLidas = notificacoesLidas;
    }
}
