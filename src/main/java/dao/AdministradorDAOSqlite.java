/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Administrador;
import model.Solicitacao;

/**
 *
 * @author Catterina Salvador
 */
public class AdministradorDAOSqlite implements AdministradorDAO{

    private Connection conexao;

    public AdministradorDAOSqlite() {
        
        //IMPLEMENTAR SINGLETON

        String url = "jdbc:sqlite:db/database.db";
        try {
            conexao = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }

    @Override
    public void inserir(Administrador admin) {
        String sql = "INSERT INTO administrador (id, id_usuario) VALUES (?, ?);";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String idUsuarioString = admin.getId().toString();
            String idString = admin.getId().toString();

            stmt.setString(1, idString); 
            stmt.setString(2, idUsuarioString);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    @Override
    public void aprovarSolicitacao(Solicitacao solicitacao) {
        String sql = "UPDATE usuarios SET aprovado = 1 WHERE id = ?;";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String idStr = solicitacao.getIdUsuario().toString();

            stmt.setString(1, idStr);  
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro aprovar usuário: " + e.getMessage());
        }
    }

}
