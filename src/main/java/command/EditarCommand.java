package command;

import state.EdicaoState;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas 
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
