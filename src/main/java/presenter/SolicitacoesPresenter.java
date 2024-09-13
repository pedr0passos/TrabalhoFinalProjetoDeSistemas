package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Solicitacao;
import observer.Observer;
import service.SolicitacaoService; 
import service.UsuarioService;
import view.SolicitacoesView;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas 
 */
public class SolicitacoesPresenter implements Observer {

    private final SolicitacaoService service;
    private final UsuarioService usuarioService;
    private SolicitacoesView view;

    public SolicitacoesPresenter(SolicitacaoService service, UsuarioService usuarioService, JDesktopPane pane) {
        this.service = service;
        this.usuarioService = usuarioService;
        
        criarView();
        pane.add(view);
    }

    private void criarView() {
        
        view = new SolicitacoesView(); 
        view.setVisible(true);
        update();
        
        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    
                    String nomeUsuario = view.getTxtBuscarUsuario().getText().trim();
            
                    if (nomeUsuario.isEmpty()) {
                        update(); 
                        return;
                    }

                    List<Solicitacao> solicitacoes = service.listarSolicitacoes();
                    exibirSolicitacoes(solicitacoes);
                    
                } catch (NumberFormatException exception) {
                    exception.printStackTrace(); 
                }
            }
        });
        
        view.getBtnRecusar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    recusarSolicitacoes();
                    JOptionPane.showMessageDialog(view, "Solicitações recusadas!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException exception) {
                    exception.printStackTrace(); 
                }
            }
        });
        
        view.getBtnAprovar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                aprovarSolicitacoes();
                JOptionPane.showMessageDialog(view, "Solicitações Aprovadas!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            }
        });
    }
    
    public void atualizarView() {
        List<Solicitacao> solicitacoes = service.listarSolicitacoes();
        exibirSolicitacoes(solicitacoes);
    }

    private void exibirSolicitacoes(List<Solicitacao> solicitacoes) {
        DefaultTableModel model = (DefaultTableModel) view.getTbSolicitacoes().getModel();
        model.setRowCount(0);

        for (Solicitacao solicitacao : solicitacoes) {
            model.addRow(new Object[]{
                solicitacao.isAprovada(), 
                solicitacao.getNomeUsuario(),
                solicitacao.getDataSolicitacao().toString()
            });
        }
    }

    private void aprovarSolicitacoes() {
        int[] selectedRows = view.getTbSolicitacoes().getSelectedRows();
        for (int rowIndex : selectedRows) {
            Solicitacao solicitacao = getSolicitacaoSelecionada(rowIndex);
            if (solicitacao != null && !solicitacao.isAprovada()) {
                service.aprovarSolicitacao(solicitacao.getNomeUsuario());
            }
        }
        update();
    }
    private void recusarSolicitacoes() {
        int[] selectedRows = view.getTbSolicitacoes().getSelectedRows();
        for (int rowIndex : selectedRows) {
            Solicitacao solicitacao = getSolicitacaoSelecionada(rowIndex);
            if (solicitacao != null) {
                service.removerSolicitacao(solicitacao.getNomeUsuario());
                usuarioService.deletarUsuario(solicitacao.getIdUsuario());
            }
        }
        update();
    }
    
    private Solicitacao getSolicitacaoSelecionada(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) view.getTbSolicitacoes().getModel();
        if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
            
            boolean aprovado = false;
            String usuarioNome = (String) model.getValueAt(rowIndex, 1);
            String dataEnvioStr = (String) model.getValueAt(rowIndex, 2);
            LocalDate dataEnvio = LocalDate.parse(dataEnvioStr);

            var usuario = usuarioService.buscarUsuarioPorNome(usuarioNome);
            
            return new Solicitacao(UUID.randomUUID(), usuario.get(), dataEnvio, aprovado);
        }
        return null;
    }

    @Override
    public void update() {
        atualizarView();
    }
}
