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
import java.util.logging.Logger;
import log.Log;
import model.Usuario;

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
                    trocarParaEstadoExclusao(usuario.get());
                } catch (Exception ex) {
                    Logger.getLogger(RegistrosPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void editarUsuario() {
        var tabela = view.getTbUsuarios();
        if (tabela.getSelectedRow() != -1) {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0);
            var usuario = service.buscarUsuarioPorId(idUsuario.toString());
            if (usuario.isPresent()) {
                try {
                    trocarParaEstadoEdicao(usuario.get());
                } catch (Exception ex) {
                    Logger.getLogger(RegistrosPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void visualizarUsuario() {
        var tabela = view.getTbUsuarios();
        if (tabela.getSelectedRow() != -1) { // Verifica se uma linha está selecionada

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0); // Pega o ID do usuário selecionado

            var usuario = service.buscarUsuarioPorId(idUsuario.toString());

            if (usuario.isPresent()) {
                try {
                    trocarParaEstadoVisualizacao(usuario.get());
                } catch (Exception ex) {
                    Logger.getLogger(RegistrosPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Usuário não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Selecione um usuário para visualizar", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void trocarParaEstadoEdicao(Usuario usuario) throws Exception {
        this.editarPresenter = new EditarPresenter(pane, usuario, service);
        this.editarPresenter.adicionarObserver(this);
        this.estadoAtual = new EdicaoState(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);

        var editarCommand = new EditarCommand((EdicaoState) estadoAtual);
        editarCommand.executar();
    }

    private void trocarParaEstadoExclusao(Usuario usuario) throws Exception {
        this.confirmarExclusaoPresenter = new ConfirmarExclusaoPresenter(pane, service, usuario.getId());
        this.confirmarExclusaoPresenter.adicionarObserver(this);
        this.estadoAtual = new ExcluirState(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);

        var excluirCommand = new ExcluirCommand((ExcluirState) estadoAtual);
        excluirCommand.executar();
    }

    private void trocarParaEstadoVisualizacao(Usuario usuario) throws Exception {
        this.visualizarUsuarioPresenter = new VisualizarUsuarioPresenter(pane, usuario, new NotificadoraService());
        this.estadoAtual = new VisualizacaoState(visualizarUsuarioPresenter, editarPresenter, confirmarExclusaoPresenter);

        var visualizarCommand = new VisualizarCommand((VisualizacaoState) estadoAtual);
        visualizarCommand.executar();
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
    
}
