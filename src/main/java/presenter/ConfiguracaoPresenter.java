package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.LogService;
import service.UsuarioService;
import view.ConfiguracaoView;
import dao.LogDAO;
import dao.LogDAOSqlite;

/**
 * @author 
 * Pedro Henrique Passos
 * Catterina Salvador
 */

public class ConfiguracaoPresenter {
    
    private final UsuarioService service;
    private final ConfiguracaoView view;
    private final LogService logService = new LogService(); // Adicionar LogService aqui
    
    public ConfiguracaoPresenter(UsuarioService service, ConfiguracaoView view) {
        this.service = service;
        this.view = view;
        logService.configuraLog();
        
        this.view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarLog();
            }
        }); 
    }
    
    public void alterarLog() {
        String logSelecionado = view.getcBoxLog().getSelectedItem().toString();
        LogDAO logDAO = new LogDAOSqlite();
        System.out.println(logSelecionado);
        logDAO.updateLog(1, logSelecionado);
        logService.configuraLog(); // Configurar o log no LogService
        view.setVisible(false);
    }
    
    public ConfiguracaoView getView() {
        return view;
    }
}
