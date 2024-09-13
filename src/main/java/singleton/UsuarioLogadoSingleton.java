package singleton;

import model.Usuario;

/**
 * Classe Singleton para gerenciar o usuário logado no sistema.
 * 
 * @author pedro
 */

public class UsuarioLogadoSingleton {

    private static UsuarioLogadoSingleton instancia;
    private Usuario usuario;
    
    private UsuarioLogadoSingleton() {
        this.usuario = null;
    }
    
    public static UsuarioLogadoSingleton getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioLogadoSingleton();
        }
        return instancia;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioLogado() {
        return this.usuario;
    }

    public boolean isUsuarioLogado() {
        return this.usuario != null;
    }

    public void logout() {
        this.usuario = null;
    }
}
