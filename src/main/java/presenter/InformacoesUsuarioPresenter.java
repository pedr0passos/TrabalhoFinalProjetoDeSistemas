package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JDesktopPane;
import model.Usuario;
import service.UsuarioService;
import view.InformacoesUsuarioView;
import service.LogService;
import singleton.UsuarioLogadoSingleton;

public class InformacoesUsuarioPresenter {

    private Usuario model;
    private InformacoesUsuarioView view;
    private final UsuarioService service;
    private final JDesktopPane panel;
    private final LogService logService;

    public InformacoesUsuarioPresenter( JDesktopPane panel, UsuarioService service, LogService logService) {
        
        this.panel = panel;
        this.service = service;
        this.logService = logService;

        configurarView();
    }
    
    public void setVisible(){
        view.setVisible(true);
    }

    private void configurarView() {
        view = new InformacoesUsuarioView(); 
        view.setVisible(false);
        panel.add(view);
        
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
    
    public void atualizarView(){
        this.model = UsuarioLogadoSingleton.getInstancia().getUsuarioLogado();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = model.getDataCriacao().format(formatter);
        
        view.getTxtDataCriacao().setText(dataFormatada);
        view.getTxtNome().setText(model.getUserName());
        view.getTxtTipo().setText(model.getTipo());
    }

}
