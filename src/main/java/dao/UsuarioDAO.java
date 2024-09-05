package dao;

import java.util.List;
import java.util.Optional;
import model.Usuario;

/**
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas 
 * @author Catterina Salvador
 */
public interface UsuarioDAO {
    
    void inserir(Usuario usuario);
    
    List<Usuario> listar();
    
    Optional<Usuario> buscarPorId(String id);
    
    Optional<Usuario> buscarPorNome(String nome);
    
    void atualizar(Usuario usuario);
    
    void deletar(Long id);
}
