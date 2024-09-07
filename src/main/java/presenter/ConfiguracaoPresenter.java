package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    public ConfiguracaoPresenter(UsuarioService service, ConfiguracaoView view) {
        this.service = service;
        this.view = view;
        this.view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarLog();
            }
        }); 
    }
    
    public void alterarLog() {
        String logSelecionado = (String) view.getcBoxLog().getSelectedItem();
        // model.setLog(logSelecionado);
        view.setVisible(false);
    }
    
    public ConfiguracaoView getView() {
        return view;
    }
}
