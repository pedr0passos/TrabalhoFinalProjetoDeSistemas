/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import command.*;
import javax.swing.table.DefaultTableModel;

import singleton.UsuarioLogadoSingleton;
import service.*;
import observer.Observer;

import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private UsuarioState estadoAtual;
    private UsuarioState estadoInicial;
    
    private EditarPresenter editarPresenter;
    private VisualizarUsuarioPresenter visualizarUsuarioPresenter;

    public RegistrosPresenter(Usuario model, JDesktopPane pane, UsuarioService service) {
        this.service = service;
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
                if (usuario.getUserName().toLowerCase().contains(nomeBusca.toLowerCase()) && !usuario.isAdministrador()) {
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
            exception.printStackTrace();
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
        var tabela = view.getTbUsuarios();
        if (tabela.getSelectedRow() != -1) {
            var tableModel = (DefaultTableModel) tabela.getModel();
            UUID idUsuario = (UUID) tableModel.getValueAt(tabela.getSelectedRow(), 0);

            if (idUsuario.equals(UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getId())) {
                JOptionPane.showMessageDialog(view, "Não é possível excluir a si mesmo", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                service.deletarUsuario(idUsuario);
                atualizarView();
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
        this.estadoAtual = new EdicaoState(visualizarUsuarioPresenter, editarPresenter); 
        
        var editarCommand = new EditarCommand((EdicaoState) estadoAtual);
        editarCommand.executar();
    }
    
    private void trocarParaEstadoVisualizacao(Usuario usuario) throws Exception {
        this.visualizarUsuarioPresenter = new VisualizarUsuarioPresenter(pane, usuario, new NotificadoraService());
        this.estadoAtual = new VisualizacaoState(visualizarUsuarioPresenter, editarPresenter); 

        var visualizarCommand = new VisualizarCommand((VisualizacaoState) estadoAtual);
        visualizarCommand.executar();
    }

    
    public final void atualizarView() {
        List<Usuario> usuarioList = service.listarUsuarios();
        DefaultTableModel tableModel = (DefaultTableModel) view.getTbUsuarios().getModel();
        tableModel.setRowCount(0);

        for (Usuario usuario : usuarioList) {

            if (!usuario.isAdministrador() && usuario.getIsAutorizado()) {
                tableModel.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getUserName(),
                    usuario.getDataCriacao(),
                    usuario.getNumeroNotificacoesLidas(),
                    usuario.getNumeroNotificacoesLidas()
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
