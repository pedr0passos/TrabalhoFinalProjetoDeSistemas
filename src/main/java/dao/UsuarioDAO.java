/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import java.util.Optional;
import model.Usuario;

/**
 *
 * @author Catterina Salvador
 */
public interface UsuarioDAO {
    
    void inserir(Usuario usuario);
    
    List<Usuario> listar();
    
    Optional<Usuario> buscarPorId(Long id);
    
    void atualizar(Usuario usuario);
    
    void deletar(Long id);
    
}
