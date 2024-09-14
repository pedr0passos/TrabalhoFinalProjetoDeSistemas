/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import command.EditarCommand;
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
    private ConfirmarExclusaoPresenter confirmarExclusaoPresenter;

    public RegistrosPresenter(Usuario model, JDesktopPane pane, UsuarioService service) {
        this.model = model;
        this.service = service;
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
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0);

            if (idUsuario.equals(UsuarioLogadoSingleton.getInstancia().getUsuarioLogado().getId())) {
                JOptionPane.showMessageDialog(view, "Não é possível excluir a si mesmo", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                mostraConfirmarExclusao(idUsuario);
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
    
    private void mostraConfirmarExclusao(UUID idUsuario){
        confirmarExclusaoPresenter =  new ConfirmarExclusaoPresenter(pane ,service, idUsuario);
        confirmarExclusaoPresenter.adicionarObserver(this);
    }

    public void setVisible() {
        this.view.setVisible(true);
    }

    @Override
    public void update() {
        atualizarView();
    }
}
