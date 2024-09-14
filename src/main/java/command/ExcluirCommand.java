/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import state.ExcluirState;

/**
 *
 * @author Catterina Salvador
 */
public class ExcluirCommand implements Command{

    private final ExcluirState estado;
    
    public ExcluirCommand(ExcluirState estado ) {
        this.estado = estado;
    }
    
    @Override
    public void executar() throws Exception {
        estado.inicializaTela();
    }
    
}
