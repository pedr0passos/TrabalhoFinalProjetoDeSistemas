package state;

import command.*;
import presenter.*;

/**
 * @author Pedro Henrique Passos Rocha
 * @author Catterina Vittorazzi Salvador
 * @author João Victor Mascarenhas 
 */


public class VisualizacaoState extends UsuarioState {
    
    private Command visualizarCommand;
    
    public VisualizacaoState(VisualizarUsuarioPresenter visualizarUsuarioPresenter, EditarPresenter editarPresenter) {
        super(visualizarUsuarioPresenter, editarPresenter);
        inicializaCommand();
    }
    
    @Override
    public void visualizar() throws Exception {
        visualizarCommand.executar();
    }

    @Override
    public void editar() throws Exception {
        throw new UnsupportedOperationException("Não é possível editar no estado de visualização.");
    }

    @Override
    public void deletar() throws Exception {
        throw new UnsupportedOperationException("Não é possível deletar no estado de visualização.");
    }
    
    public void inicializaTela() {
        visualizarUsuarioPresenter.criarView();
    }
    
    private void inicializaCommand() {
        this.visualizarCommand = new VisualizarCommand(this);
    }
}




