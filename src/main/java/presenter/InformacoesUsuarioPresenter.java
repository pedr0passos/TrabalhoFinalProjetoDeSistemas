package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JDesktopPane;
import model.Usuario;
import service.UsuarioService;
import view.InformacoesUsuarioView;
import service.LogService;

public class InformacoesUsuarioPresenter {

    private final Usuario model;
    private InformacoesUsuarioView view;
    private final UsuarioService service;
    private final JDesktopPane panel;
    private final LogService logService;

    public InformacoesUsuarioPresenter(Usuario model, JDesktopPane panel, UsuarioService service, LogService logService) {
        
        this.model = model;
        this.panel = panel;
        this.service = service;
        this.logService = logService;

        configurarView();
        panel.add(view);
    }

    private void configurarView() {
        view = new InformacoesUsuarioView(); 
        view.setVisible(true);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = model.getDataCriacao().format(formatter);
        
        view.getTxtDataCriacao().setText(dataFormatada);
        view.getTxtNome().setText(model.getUserName());
        view.getTxtTipo().setText(model.getTipo());
        
        view.getBtnTrocarSenha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AlterarSenhaPresenter(model, panel, service, logService);
                } catch (Exception exception) { // Mudança de NumberFormatException para Exception
                    System.err.println("Erro ao alterar senha: " + exception.getMessage());
                    exception.printStackTrace(); // Log completo do erro para debug
                }
            }
        });
    }

}
