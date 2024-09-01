/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UsuarioDAO;
import dao.UsuarioDAOSqlite;
import model.Usuario;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Joao Victor
 */
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioService(){
        this.usuarioDAO = new UsuarioDAOSqlite();
    }
    
    public void cadastrarUsuario(Usuario usuario){
        usuarioDAO.inserir(usuario);
    }
    
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }
    
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listar();
    }
    
    public void atualizarUsuario(Usuario usuario) {
        usuarioDAO.atualizar(usuario);
    }
    
    public void deletarUsuario(Long id) {
        usuarioDAO.deletar(id);
    }
}
