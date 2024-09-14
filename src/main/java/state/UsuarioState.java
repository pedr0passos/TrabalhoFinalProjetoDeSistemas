package state;

import model.Usuario;
import presenter.*;
import singleton.*;

/**
 *
 * @author pedro
 */

public abstract class UsuarioState {
    
    protected final CadastroPresenter cadastroPresenter;
    protected final EditarPresenter editarPresenter;
    protected Usuario usuarioLogado; 
    
    public UsuarioState(CadastroPresenter cadastroPresenter, EditarPresenter editarPresenter) {
        this.cadastroPresenter = cadastroPresenter;
        this.editarPresenter = editarPresenter;
        
        this.usuarioLogado = UsuarioLogadoSingleton.getInstancia().getUsuarioLogado();
    }
    
    public void inserir() throws Exception {
        throw new RuntimeException("Não é possível inserir.");
    }
    
    public void editar() throws Exception {
        throw new RuntimeException("Não é possível editar.");
    }
    
    public void deletar() throws Exception {
        throw new RuntimeException("Não é possível deletar.");
    }
}
