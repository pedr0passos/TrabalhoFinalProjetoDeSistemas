package command;

import state.VisualizacaoState;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas 
 */

public class VisualizarCommand implements Command{

    private final VisualizacaoState estado;

    public VisualizarCommand(VisualizacaoState estado) {
        this.estado = estado;
    }

    @Override
    public void executar() {
        estado.inicializaTela();
    }
}
