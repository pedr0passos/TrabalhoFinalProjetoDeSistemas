/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package state;

import command.Command;
import command.EditarCommand;
import presenter.*;

/**
 *
 * @author pedro
 */

public class EdicaoState extends UsuarioState {
    
    private Command editarCommand;
    
    public EdicaoState(
            VisualizarUsuarioPresenter visualizarUsuarioPresenter, 
            EditarPresenter editarPresenter, 
            ConfirmarExclusaoPresenter confirmarExclusaoPresenter) 
    {
        super(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);
        inicializaCommand();
    }
    
    @Override
    public void visualizar() throws Exception {
        throw new UnsupportedOperationException("Não é possível inserir no estado de edição.");
    }

    @Override
    public void editar() throws Exception {
        editarCommand.executar();
    }

    @Override
    public void deletar() throws Exception {
        throw new UnsupportedOperationException("Não é possível deletar no estado de edição.");
    }

    public void inicializaTela() {
        editarPresenter.criarView(); 
    }
    
    private void inicializaCommand() {
        this.editarCommand = new EditarCommand(this);
    }
}
