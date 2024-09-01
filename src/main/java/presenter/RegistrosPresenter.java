/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import model.Usuario;
import observer.Observer;
import view.RegistrosView;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha 
 * @author João Victor Mascarenhas 
 */

public class RegistrosPresenter implements Observer {
    
    private final Usuario model;
    private RegistrosView view;

    public RegistrosPresenter(Usuario model, JDesktopPane pane) {
        this.model = model;
        criarView();
        pane.add(view);
    }
    
    public void criarView() {
        view = new RegistrosView(); 
        view.setVisible(false);
        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });
        view.getBtnLimpar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });
    }
    
    public void atualizarView() {
        
    }
    
    
    @Override
    public void update() {
        atualizarView();
    }
}
