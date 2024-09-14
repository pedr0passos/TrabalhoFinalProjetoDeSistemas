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
    
    public EdicaoState(CadastroPresenter cadastroPresenter, EditarPresenter editarPresenter) {
        super(cadastroPresenter, editarPresenter);
        inicializaCommand();
    }
    
    @Override
    public void inserir() throws Exception {
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

    // Método para inicializar a tela de edição
    public void inicializaTela() {
        editarPresenter.criarView(); // Inicializa a view para edição
    }
    
    private void inicializaCommand() {
        this.editarCommand = new EditarCommand(this);
    }
}
