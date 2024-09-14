/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import command.EditarCommand;
import javax.swing.table.DefaultTableModel;
import model.Usuario;
import observer.Observer;
import service.NotificadoraService;
import service.UsuarioService;
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
    private NotificadoraService notificadoraService;
    private JDesktopPane pane;
    
    private UsuarioState estadoAtual;
    private EditarPresenter editarPresenter;

    public RegistrosPresenter(Usuario model, JDesktopPane pane, UsuarioService service) {
        this.model = model;
        this.service = service;
        this.notificadoraService = new NotificadoraService();
        
        this.pane = pane;
        criarView();
    }

    public void criarView() {
        
        view = new RegistrosView();
        view.setVisible(false);
        pane.add(view);
        atualizarView();
        
        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuarios();
            }
        });

        view.getBtnLimpar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparBusca();
            }
        });

        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirUsuario();
            }
        });

        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
            }
        });
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
                    tableModel.addRow(new Object[] {
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

    private void trocarParaEstadoEdicao(Usuario usuario) throws Exception {
        this.editarPresenter = new EditarPresenter(pane, usuario, service);
        this.estadoAtual = new EdicaoState(null, editarPresenter); 
        
        var editarCommand = new EditarCommand((EdicaoState) estadoAtual);
        editarCommand.executar();
    }
    
    public void atualizarView() {
        List<Usuario> usuarioList = service.listarUsuarios();
        DefaultTableModel tableModel = (DefaultTableModel) view.getTbUsuarios().getModel();
        tableModel.setRowCount(0);

        for (Usuario usuario : usuarioList) {
            usuario.setNumeroNotificacoesTotal(notificadoraService.countNotificacoesPorDestinatario(usuario.getId()));
            usuario.setNumeroNotificacoesLidas(notificadoraService.countNotificacoesLidasPorDestinatario(usuario.getId()));

            if (!usuario.isAdministrador() && usuario.getIsAutorizado()) {
                tableModel.addRow(new Object[] {
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
