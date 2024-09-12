package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JDesktopPane;
import model.Usuario;
import service.UsuarioService;
import view.InformacoesUsuarioView;

public class InformacoesUsuarioPresenter {

    private final Usuario model;
    private InformacoesUsuarioView view;
    private final UsuarioService service;
    private final JDesktopPane panel;

    public InformacoesUsuarioPresenter(Usuario model, JDesktopPane panel, UsuarioService service) {
        this.model = model;
        this.panel = panel;
        this.service = service;
        
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
                    new AlterarSenhaPresenter(model, panel, service);
                } catch (Exception exception) { // Mudança de NumberFormatException para Exception
                    System.err.println("Erro ao alterar senha: " + exception.getMessage());
                    exception.printStackTrace(); // Log completo do erro para debug
                }
            }
        });
    }

}
