/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.*;
import java.util.*;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.Main;
import model.Usuario;
import observer.Observer;
import service.UsuarioService;
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

    public RegistrosPresenter(Usuario model, JDesktopPane pane, UsuarioService service) {
        this.model = model;
        this.service = service;
        
        criarView();
        pane.add(view);
    }
    
    public void criarView() {
        view = new RegistrosView(); 
        view.setVisible(true);
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
                        if (usuario.getUserName().toLowerCase().contains(nomeBusca.toLowerCase())) {
                            tableModel.addRow(new Object[] {
                                usuario.getId(),
                                usuario.getUserName(),
                                usuario.getDataCriacao()
                            });
                        }
                    }
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });
        view.getBtnLimpar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!view.getTxtBuscarUsuario().getText().isEmpty()) 
                        view.getTxtBuscarUsuario().setText("");                    
                    atualizarView();
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });
        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                var tabela = view.getTbUsuarios();
                if(tabela.getSelectedRow() != -1){
                    DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                    UUID idUsuario = (UUID) model.getValueAt(tabela.getSelectedRow(), 0);

                    if(idUsuario.equals(Main.getUsuario().getId())){
                        JOptionPane.showMessageDialog(view, "Não é possível excluir a si mesmo", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        service.deletarUsuario(idUsuario);
                        atualizarView();
                    }
                }
            }
        });
    }
    
    public void atualizarView() {
        List<Usuario> usuarioList = service.listarUsuarios();
        DefaultTableModel tableModel = (DefaultTableModel) view.getTbUsuarios().getModel();
        tableModel.setRowCount(0);

        for (Usuario usuario : usuarioList) {
            tableModel.addRow(new Object[] {
                usuario.getId(),
                usuario.getUserName(),
                usuario.getDataCriacao()
            });
        }
    }

    @Override
    public void update() {
        atualizarView();
    }
}
