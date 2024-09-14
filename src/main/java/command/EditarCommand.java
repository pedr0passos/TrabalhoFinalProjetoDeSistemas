/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import state.EdicaoState;

/**
 *
 * @author pedro
 */

public class EditarCommand implements Command {
    
    private final EdicaoState estado;
    
    public EditarCommand(EdicaoState estado ) {
        this.estado = estado;
    }
    
    @Override
    public void executar() throws Exception {
        estado.inicializaTela();
    }
    
}
