package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.LogService;
import service.UsuarioService;
import view.ConfiguracaoView;

/**
 * @author 
 * Pedro Henrique Passos
 * Catterina Salvador
 */

public class ConfiguracaoPresenter {
    
    private final UsuarioService service;
    private final ConfiguracaoView view;
    private final LogService logService; // Adicionar LogService aqui
    
    public ConfiguracaoPresenter(UsuarioService service, ConfiguracaoView view, LogService logService) {
        this.service = service;
        this.view = view;
        this.logService = logService; // Inicializar o LogService
        
        this.view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarLog();
            }
        }); 
    }
    
    public void alterarLog() {
        String logSelecionado = (String) view.getcBoxLog().getSelectedItem();
        logService.configuraLog(logSelecionado.toString()); // Configurar o log no LogService
        view.setVisible(false);
    }
    
    public ConfiguracaoView getView() {
        return view;
    }
}
