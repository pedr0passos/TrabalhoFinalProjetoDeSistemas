/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import model.Administrador;
import model.Solicitacao;

/**
 *
 * @author Catterina Salvador
 */
public interface AdministradorDAO {
    public void inserir(Administrador admin);

    public void aprovarSolicitacao(Solicitacao solicitacao);
}
