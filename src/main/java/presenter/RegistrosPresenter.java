/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import command.*;
import javax.swing.table.DefaultTableModel;
import service.*;
import observer.Observer;

import javax.swing.*;
import java.util.*;
import java.util.logging.Level;

import log.Log;
import model.Usuario;
import singleton.UsuarioLogadoSingleton;

import state.*;
import view.RegistrosView;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas
 */
public class RegistrosPresenter implements Observer {

    private RegistrosView view;
    private final UsuarioService service;
    private final JDesktopPane pane;
    private final NotificadoraService notificadoraService;
    private UsuarioState estadoAtual;
    private Usuario user;
    private UsuarioState estadoInicial;
    private final LogService logService = new LogService();
    private EditarPresenter editarPresenter;
    private VisualizarUsuarioPresenter visualizarUsuarioPresenter;
    private ConfirmarExclusaoPresenter confirmarExclusaoPresenter;

    public RegistrosPresenter(Usuario model, JDesktopPane pane, UsuarioService service) {
        this.service = service;
        this.notificadoraService = new NotificadoraService();
        
        this.pane = pane;
        this.view = view = new RegistrosView();
        criarView();
        setupListeners();
        atualizarView();
    }

    private void setupListeners() {
        view.getBtnBuscar().addActionListener(e -> buscarUsuarios());
        view.getBtnLimpar().addActionListener(e -> limparBusca());
        view.getBtnExcluir().addActionListener(e -> excluirUsuario());
        view.getBtnEditar().addActionListener(e -> editarUsuario());
        view.getBtnVisualizar().addActionListener(e -> visualizarUsuario());
    }

    public final void criarView() {
        view.setVisible(false);
        pane.add(view);
        atualizarView();
    }

    private void buscarUsuarios() {
        try {
            String nomeBusca = view.getTxtBuscarUsuario().getText().trim();

            if (nomeBusca.isEmpty()) {
                atualizarView();
                return;
            }

            List<Usuario> usuarioList = service.listarUsuarios();
            DefaultTableModel tableModel = (DefaultTableModel) view.getTbUsuarios().getModel();
            tableModel.setRowCount(0);

            for (Usuario usuario : usuarioList) {
                usuario.setNumeroNotificacoesTotal(notificadoraService.countNotificacoesPorDestinatario(usuario.getId()));
                usuario.setNumeroNotificacoesLidas(notificadoraService.countNotificacoesLidasPorDestinatario(usuario.getId()));
                if (usuario.getUserName().toLowerCase().contains(nomeBusca.toLowerCase())) {
                    tableModel.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getUserName(),
                        usuario.getDataCriacao(),
                        usuario.getNumeroNotificacoesLidas(),
                        usuario.getNumeroNotificacoesLidas()
                    });
                }
            }
        } catch (NumberFormatException exception) {
            exception.getStackTrace();
        }
    }

    private void limparBusca() {
        try {
            if (!view.getTxtBuscarUsuario().getText().isEmpty()) {
                view.getTxtBuscarUsuario().setText("");
            }
            atualizarView();
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
    }

    private void excluirUsuario() {
        logService.configuraLog();
        Log log = logService.getLog();
        var tabela = view.getTbUsuarios();
        if (tabela.getSelectedRow() != -1) {
            var tableModel = (DefaultTableModel) tabela.getModel();
            UUID idUsuario = (UUID) tableModel.getValueAt(tabela.getSelectedRow(), 0);
            var usuario = service.buscarUsuarioPorId(idUsuario.toString());

            if (usuario.isPresent()) {
                try {
                    user = usuario.get();
                    trocarParaEstadoExclusao(usuario.get());
                    registrarLogExcluir(log, null);
                } catch (Exception ex) {
                    registrarLogExcluir(log, ex.getMessage());
                }
            }
        }
    }

    private void editarUsuario() {
        logService.configuraLog();
        Log log = logService.getLog();
        var tabela = view.getTbUsuarios();
        if (tabela.getSelectedRow() != -1) {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0);
            var usuario = service.buscarUsuarioPorId(idUsuario.toString());
            if (usuario.isPresent()) {
                try {
                    user = usuario.get();
                    trocarParaEstadoEdicao(usuario.get());
                } catch (Exception ex) {
                    registrarLogEditar(log, ex.getMessage());
                }
            }
        }
    }

    private void visualizarUsuario() {
        logService.configuraLog();
        Log log = logService.getLog();
        var tabela = view.getTbUsuarios();
        if (tabela.getSelectedRow() != -1) { // Verifica se uma linha está selecionada

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0); // Pega o ID do usuário selecionado

            var usuario = service.buscarUsuarioPorId(idUsuario.toString());

            if (usuario.isPresent()) {
                try {
                    user = usuario.get();
                    trocarParaEstadoVisualizacao(usuario.get());
                } catch (Exception ex) {
                    registrarLogVisualizar(log, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(view, "Usuário não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
                registrarLogVisualizar(log, "Usuário não encontrado");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Selecione um usuário para visualizar", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            registrarLogVisualizar(log, "nao foi selecionado um usuário para visualizar");
        }
    }

    private void trocarParaEstadoEdicao(Usuario usuario) throws Exception {
        logService.configuraLog();
        Log log = logService.getLog();
        this.editarPresenter = new EditarPresenter(pane, usuario, service);
        this.editarPresenter.adicionarObserver(this);
        this.estadoAtual = new EdicaoState(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);
        
        var editarCommand = new EditarCommand((EdicaoState) estadoAtual);
        editarCommand.executar();
        registrarLogEditar(log, null);
    }

    private void trocarParaEstadoExclusao(Usuario usuario) throws Exception {
        logService.configuraLog();
        Log log = logService.getLog();
        this.confirmarExclusaoPresenter = new ConfirmarExclusaoPresenter(pane, service, usuario.getId());
        this.confirmarExclusaoPresenter.adicionarObserver(this);
        this.estadoAtual = new ExcluirState(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);
        
        var excluirCommand = new ExcluirCommand((ExcluirState) estadoAtual);
        excluirCommand.executar();
        registrarLogExcluir(log, null);
    }

    private void trocarParaEstadoVisualizacao(Usuario usuario) throws Exception {
        logService.configuraLog();
        Log log = logService.getLog();
        this.visualizarUsuarioPresenter = new VisualizarUsuarioPresenter(pane, usuario, new NotificadoraService());
        this.estadoAtual = new VisualizacaoState(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);

        var visualizarCommand = new VisualizarCommand((VisualizacaoState) estadoAtual);
        visualizarCommand.executar();
        registrarLogVisualizar(log, null);
    }

    public final void atualizarView() {
        List<Usuario> usuarioList = service.listarUsuarios();
        DefaultTableModel tableModel = (DefaultTableModel) view.getTbUsuarios().getModel();
        tableModel.setRowCount(0);

        for (Usuario usuario : usuarioList) {
            usuario.setNumeroNotificacoesTotal(notificadoraService.countNotificacoesPorDestinatario(usuario.getId()));
            usuario.setNumeroNotificacoesLidas(notificadoraService.countNotificacoesLidasPorDestinatario(usuario.getId()));

            if (!usuario.isAdministrador() && usuario.getIsAutorizado()) {
                tableModel.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getUserName(),
                    usuario.getDataCriacao(),
                    usuario.getNumeroNotificacoesLidas(),
                    usuario.getNumeroNotificacoesTotal()
                });
            }

        }
    }

    public void setVisible() {
        this.view.setVisible(true);
    }

    @Override
    public void update() {
        atualizarView();
    }
    
    private void registrarLogVisualizar(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Vizualizar usuário" : "Vizualizar usuário",
                user.getUserName(),
                UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                mensagemErro == null,
                mensagemErro
            );
        }
    }
    
    private void registrarLogEditar(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Edicao de usuário" : "Ao editar usuário",
                user.getUserName(),
                UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                mensagemErro == null,
                mensagemErro
            );
        }
    }
    private void registrarLogExcluir(Log log, String mensagemErro) {
        if (log != null) {
            log.gravarLog(
                mensagemErro == null ? "Exclusao de usuário" : "Ao excluir usuario",
                user.getUserName(),
                UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getUserName(),
                mensagemErro == null,
                mensagemErro
            );
        }
    }
}
