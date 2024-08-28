/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author Catterina Salvador
 */
public interface UsuarioDAO {
    
    public void inserir(Usuario usuario);
    
    public List<Usuario> listar();
    
    public Usuario buscarPorId(Long id);
    
    public void atualizar(Usuario usuario);
    
    public void deletar(Long id);
    
}
