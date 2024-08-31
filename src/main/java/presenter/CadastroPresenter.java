/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import model.Usuario;
import observer.Observer;
import view.CadastroView;

/**
 *
 * @author pedro
 */

public class CadastroPresenter {
    
    private final List<Observer> observers = new ArrayList<>();
    
    private final Usuario model;
    private CadastroView view;
    
    public CadastroPresenter(Usuario model, JDesktopPane panel) {
        this.model = model;
        criarView();
        panel.add(view);
    }
    
    public final void criarView() {
        view = new CadastroView(); 
        view.setVisible(false);
        view.getBotaoSalvarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                } catch ( NumberFormatException exception) {
                    exception.getStackTrace();                    
                }
            }
        });
    }
}
