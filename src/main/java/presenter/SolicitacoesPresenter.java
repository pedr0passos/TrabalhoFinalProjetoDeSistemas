package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import log.Log;
import model.Solicitacao;
import observer.Observer;
import service.LogService;
import service.SolicitacaoService;
import service.UsuarioService;
import singleton.UsuarioLogadoSingleton;
import view.SolicitacoesView;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */
public class SolicitacoesPresenter {

    private final List<Observer> observers = new ArrayList<>();
    private final SolicitacaoService service;
    private final UsuarioService usuarioService;
    private SolicitacoesView view;
    private final LogService logService = new LogService();

    public SolicitacoesPresenter(SolicitacaoService service, UsuarioService usuarioService, JDesktopPane pane) {
        this.service = service;
        this.usuarioService = usuarioService;

        criarView();
        pane.add(view);
    }

    private void criarView() {

        view = new SolicitacoesView();
        view.setVisible(false);
        atualizarView();

        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeBusca = view.getTxtBuscarUsuario().getText().trim();

                    if (nomeBusca.isEmpty()) {
                        atualizarView();
                        return;
                    }

                    List<Solicitacao> solicitacoesList = service.listarSolicitacoes();
                    DefaultTableModel tableModel = (DefaultTableModel) view.getTbSolicitacoes().getModel();
                    tableModel.setRowCount(0);

                    for (Solicitacao solicitacao : solicitacoesList) {
                        if (solicitacao.getNomeUsuario().toLowerCase().contains(nomeBusca.toLowerCase())) {
                            tableModel.addRow(new Object[]{
                                false,
                                solicitacao.getNomeUsuario(),
                                solicitacao.getDataSolicitacao()
                            });
                        }
                    }

                } catch (NumberFormatException exception) {
                    exception.getStackTrace();
                }
            }
        });

        view.getBtnRecusar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logService.configuraLog();
                Log log = logService.getLog();

                try {

                    recusarSolicitacoes(log, null);
                    JOptionPane.showMessageDialog(view, "Solicitações recusadas!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                    registrarLogErrado(log, exception.getMessage());
                }
            }
        });

        view.getBtnAprovar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logService.configuraLog();
                Log log = logService.getLog();

                aprovarSolicitacoes(log, null);
                notificarObservadores();
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

    private void aprovarSolicitacoes(Log log, String erro) {
        int[] selectedRows = view.getTbSolicitacoes().getSelectedRows();
        for (int rowIndex : selectedRows) {
            Solicitacao solicitacao = getSolicitacaoSelecionada(rowIndex);
            if (solicitacao != null && !solicitacao.isAprovada()) {
                service.aprovarSolicitacao(solicitacao.getNomeUsuario());
                registrarLog(log, erro, solicitacao);
            }
        }
        atualizarView();
    }

    private void recusarSolicitacoes(Log log, String erro) {
        int[] selectedRows = view.getTbSolicitacoes().getSelectedRows();
        for (int rowIndex : selectedRows) {
            Solicitacao solicitacao = getSolicitacaoSelecionada(rowIndex);
            if (solicitacao != null) {
                service.removerSolicitacao(solicitacao.getNomeUsuario());
                registrarLog(log, erro, solicitacao);
                usuarioService.deletarUsuario(solicitacao.getIdUsuario());
            }
        }
        atualizarView();
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

    public void adicionarObserver(Observer observer) {
        observers.add(observer);
    }

    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notificarObservadores() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void setVisible() {
        this.view.setVisible(true);
    }

    private void registrarLog(Log log, String mensagemErro, Solicitacao solicitacao) {
        if (log != null) {
            log.gravarLog(
                    mensagemErro == null ? "Permissao de usuario" : "Permissao de usuario",
                    solicitacao.getNomeUsuario(),
                    UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                    mensagemErro == null,
                    mensagemErro
            );
        }
    }

    private void registrarLogErrado(Log log, String erro) {
        int[] selectedRows = view.getTbSolicitacoes().getSelectedRows();
        for (int rowIndex : selectedRows) {
            Solicitacao solicitacao = getSolicitacaoSelecionada(rowIndex);
            if (solicitacao != null) {
                registrarLog(log, erro, solicitacao);
            } else {
                registrarLog(log, "Erro na selecao de usuario", solicitacao);
            }
        }
    }

}
