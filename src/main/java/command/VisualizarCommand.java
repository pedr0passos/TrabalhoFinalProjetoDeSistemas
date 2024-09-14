package command;

import state.VisualizacaoState;

public class VisualizarCommand implements Command{

    private final VisualizacaoState estado;

    public VisualizarCommand(VisualizacaoState estado) {
        this.estado = estado;
    }

    public void executar() {
        estado.inicializaTela();
    }
}
