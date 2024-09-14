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
        List<Solicitacao> solicitacoesParaAprovar = getSolicitacoesSelecionadas();  // Captura as solicitações selecionadas

        if (solicitacoesParaAprovar.isEmpty()) {  // Verifica se não há solicitações selecionadas
            JOptionPane.showMessageDialog(view, "Nenhuma solicitação selecionada. Selecione pelo menos uma para aprovar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;  // Sai do método se não houver seleções
        }

        for (Solicitacao solicitacao : solicitacoesParaAprovar) {
            if (!solicitacao.isAprovada()) {
                service.aprovarSolicitacao(solicitacao.getNomeUsuario());
                registrarLog(log, erro, solicitacao);
            }
        }

        atualizarView();  // Atualiza a tabela após as aprovações
        JOptionPane.showMessageDialog(view, "Solicitações aprovadas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void recusarSolicitacoes(Log log, String erro) {
        List<Solicitacao> solicitacoesParaRecusar = getSolicitacoesSelecionadas();  // Captura as solicitações selecionadas

        if (solicitacoesParaRecusar.isEmpty()) {  // Verifica se não há solicitações selecionadas
            JOptionPane.showMessageDialog(view, "Nenhuma solicitação selecionada. Selecione pelo menos uma para recusar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;  // Sai do método se não houver seleções
        }

        for (Solicitacao solicitacao : solicitacoesParaRecusar) {
            service.removerSolicitacao(solicitacao.getNomeUsuario());
            registrarLog(log, erro, solicitacao);
            usuarioService.deletarUsuario(solicitacao.getIdUsuario());
        }

        atualizarView();  // Atualiza a tabela após as recusas
        JOptionPane.showMessageDialog(view, "Solicitações recusadas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }


    private List<Solicitacao> getSolicitacoesSelecionadas() {
        List<Solicitacao> solicitacoesSelecionadas = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) view.getTbSolicitacoes().getModel();

        // Percorre todas as linhas da tabela
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 0);  // Verifica o valor da coluna checkbox
            if (isSelected != null && isSelected) {  // Se está marcado
                String nomeUsuario = (String) model.getValueAt(i, 1);  // Pega o nome do usuário
                LocalDate dataSolicitacao = LocalDate.parse((String) model.getValueAt(i, 2));  // Pega a data de solicitação

                // Busca o usuário e cria uma nova instância de Solicitacao
                var usuario = usuarioService.buscarUsuarioPorNome(nomeUsuario);
                Solicitacao solicitacao = new Solicitacao(UUID.randomUUID(), usuario.get(), dataSolicitacao, false);
                solicitacoesSelecionadas.add(solicitacao);
            }
        }

        return solicitacoesSelecionadas;  // Retorna todas as solicitações que foram selecionadas pelas checkboxes
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
        List<Solicitacao> solicitacoesSelecionadas = getSolicitacoesSelecionadas();  // Obtem todas as solicitações selecionadas pelas checkboxes

        for (Solicitacao solicitacao : solicitacoesSelecionadas) {
            if (solicitacao != null) {
                registrarLog(log, erro, solicitacao);  // Registra o log para cada solicitação corretamente selecionada
            } else {
                registrarLog(log, "Erro na seleção de usuário", solicitacao);  // Registra o erro se a solicitação for nula (embora esse caso seja improvável aqui)
            }
        }
    }


}
