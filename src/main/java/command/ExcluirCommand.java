package command;

import state.ExcluirState;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas 
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
