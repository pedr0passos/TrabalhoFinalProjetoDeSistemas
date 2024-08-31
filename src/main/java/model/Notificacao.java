/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pedro
 */
public class Notificacao {
    private Long id;
    private String titulo;
    private String conteudo;
    private boolean lida;
    private static int notificacoesEnviadas;
    private static int notificacoesLidas;

    public Notificacao(Long id, String titulo, String conteudo) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.lida = false;
    }

    public Long getId() {
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

    public void setId(Long id) {
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

    public static void setNotificacoesEnviadas(int notificacoesEnviadas) {
        Notificacao.notificacoesEnviadas = notificacoesEnviadas;
    }

    public static void setNotificacoesLidas(int notificacoesLidas) {
        Notificacao.notificacoesLidas = notificacoesLidas;
    }
}
