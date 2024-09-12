package service;

import dao.*;
import model.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Pedro Henrique Passos Rocha 
 * @author Joao Victor Mascarenhas
 */

public class UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    private final AdministradorDAO administradorDAO;
    private final SolicitacaoDAO solicitacaoDAO;
        
    public UsuarioService(){
        this.usuarioDAO = new UsuarioDAOSqlite();
        this.administradorDAO = new AdministradorDAOSqlite();
        this.solicitacaoDAO = new SolicitacaoDAOSqlite();
    }
    
    public void enviarSolicitacao(Solicitacao solicitacao) {
        solicitacaoDAO.inserir(solicitacao);
    }
    
    public void cadastrarUsuario(Usuario usuario){
        if(listarUsuarios().isEmpty()){
            Administrador newAdmin = new Administrador(usuario.getId());
            administradorDAO.inserir(newAdmin);
        }
        usuarioDAO.inserir(usuario);
    }
    
    public Optional<Usuario> buscarUsuarioPorId(String id) {
        return usuarioDAO.buscarPorId(id);
    }
    
    public Optional<Usuario> buscarUsuarioPorNome(String nome) {
        return usuarioDAO.buscarPorNome(nome);
    }
    
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listar();
    }
    
    public boolean possuiUsuario() {
        return usuarioDAO.possuiUsuario();
    }
    
    public void atualizarUsuario(Usuario usuario) {
        usuarioDAO.atualizar(usuario);
    }
    
    public void deletarUsuario(UUID id) {
        usuarioDAO.deletar(id);
    }
}
