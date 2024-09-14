package state;

import model.Usuario;
import presenter.*;
import singleton.*;

/**
 *
 * @author pedro
 */

public abstract class UsuarioState {
    
    protected final VisualizarUsuarioPresenter visualizarUsuarioPresenter;
    protected final EditarPresenter editarPresenter;
    protected Usuario usuarioLogado; 
    
    public UsuarioState(VisualizarUsuarioPresenter visualizarUsuarioPresenter, EditarPresenter editarPresenter) {
        this.visualizarUsuarioPresenter = visualizarUsuarioPresenter;
        this.editarPresenter = editarPresenter;
        
        this.usuarioLogado = UsuarioLogadoSingleton.getInstancia().getUsuarioLogado();
    }
    
    public void visualizar() throws Exception {
        throw new RuntimeException("Não é possível inserir.");
    }
    
    public void editar() throws Exception {
        throw new RuntimeException("Não é possível editar.");
    }
    
    public void deletar() throws Exception {
        throw new RuntimeException("Não é possível deletar.");
    }
    
    public void voltar() throws Exception {
        throw new RuntimeException("Não é possível voltar.");
    }
}
