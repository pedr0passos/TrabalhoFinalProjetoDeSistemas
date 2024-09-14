/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package state;

import presenter.*;

/**
 *
 * @author pedro
 */

public class EdicaoState extends UsuarioState {
    
    public EdicaoState(CadastroPresenter cadastroPresenter, EditarPresenter editarPresenter) {
        super(cadastroPresenter, editarPresenter);
        inicializaTela();
    }
    
    @Override
    public void inserir() throws Exception {
        throw new UnsupportedOperationException("Não é possível inserir no estado de edição.");
    }

    @Override
    public void editar() throws Exception {
        editarPresenter.salvarEdicao(); 
    }

    @Override
    public void deletar() throws Exception {
        throw new UnsupportedOperationException("Não é possível deletar no estado de edição.");
    }

    // Método para inicializar a tela de edição
    public void inicializaTela() {
        editarPresenter.criarView(); // Inicializa a view para edição
    }
}
