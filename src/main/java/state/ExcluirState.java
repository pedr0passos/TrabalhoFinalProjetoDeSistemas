/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package state;

import command.Command;
import command.ExcluirCommand;
import presenter.ConfirmarExclusaoPresenter;
import presenter.EditarPresenter;
import presenter.VisualizarUsuarioPresenter;

/**
 *
 * @author pedro
 */
public class ExcluirState extends UsuarioState{
    private Command excluirCommand;
    
    public ExcluirState (
            VisualizarUsuarioPresenter visualizarUsuarioPresenter, 
            EditarPresenter editarPresenter, 
            ConfirmarExclusaoPresenter confirmarExclusaoPresenter) 
    {
        super(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);
        inicializaCommand();
    }
    
    @Override
    public void visualizar() throws Exception {
        throw new UnsupportedOperationException("Não é possível inserir no estado de exclusão.");
    }

    @Override
    public void editar() throws Exception {
        throw new UnsupportedOperationException("Não é possível inserir no estado de exclusão.");
    }

    @Override
    public void deletar() throws Exception {
        excluirCommand.executar();
    }

    public void inicializaTela() {
        confirmarExclusaoPresenter.criarView(); 
    }
    
    private void inicializaCommand() {
        this.excluirCommand = new ExcluirCommand(this);
    }
}
