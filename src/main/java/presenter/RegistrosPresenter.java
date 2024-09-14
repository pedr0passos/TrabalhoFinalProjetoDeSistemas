/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import javax.swing.table.DefaultTableModel;
import singleton.UsuarioLogadoSingleton;
import service.UsuarioService;
import observer.Observer;
import java.awt.event.*;

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

    private final Usuario model;
    private RegistrosView view;
    private UsuarioService service;
    private JDesktopPane pane;
    private UsuarioState estadoAtual;
    private EditarPresenter editarPresenter;

    public RegistrosPresenter(Usuario model, JDesktopPane pane, UsuarioService service) {
        this.model = model;
        this.service = service;
        this.pane = pane;
        criarView();
        pane.add(view);
    }

    public void criarView() {
        
        view = new RegistrosView();
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
                    exception.getStackTrace();
                }
            }
        });
        view.getBtnLimpar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!view.getTxtBuscarUsuario().getText().isEmpty()) {
                        view.getTxtBuscarUsuario().setText("");
                    }
                    atualizarView();
                } catch (NumberFormatException exception) {
                    exception.getStackTrace();
                }
            }
        });
        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                var tabela = view.getTbUsuarios();
                if (tabela.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                    UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0);

                    if (idUsuario.equals(UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getId())) {
                        JOptionPane.showMessageDialog(view, "Não é possível excluir a si mesmo", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        service.deletarUsuario(idUsuario);
                        atualizarView();
                    }
                }
            }
        });
        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var tabela = view.getTbUsuarios();
                if (tabela.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                    UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0);
                    var usuario = service.buscarUsuarioPorId(idUsuario.toString());
                    try {
                        trocarParaEstadoEdicao(usuario.get());
                    } catch (Exception ex) {
                        Logger.getLogger(RegistrosPresenter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void trocarParaEstadoEdicao(Usuario usuario) throws Exception {
        this.editarPresenter = new EditarPresenter(pane, usuario, service);
        this.estadoAtual = new EdicaoState(null, editarPresenter); 
        estadoAtual.editar();
    }
    
    public void atualizarView() {
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

    private void mostrarEditarView(Usuario usuario) {
        editarPresenter = new EditarPresenter(pane, usuario, service);
    }

    public void setVisible() {
        this.view.setVisible(true);
    }

    @Override
    public void update() {
        atualizarView();
    }
}
