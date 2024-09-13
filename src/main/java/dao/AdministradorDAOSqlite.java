package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Administrador;
import model.Solicitacao;

public class AdministradorDAOSqlite implements AdministradorDAO {

    private static Connection conexao;

    public AdministradorDAOSqlite() {
        this.conexao = ConexaoSingleton.getInstance().getConnection();
    }

    @Override
    public void inserir(Administrador admin) {
        String sql = "INSERT INTO Administrador (id, id_usuario) VALUES (?, ?);";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String idUsuarioString = admin.getIdUsuario().toString();
            String idString = admin.getId().toString();

            stmt.setString(1, idString); 
            stmt.setString(2, idUsuarioString);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir administrador: " + e.getMessage());
        }
    }

    @Override
    public void aprovarSolicitacao(Solicitacao solicitacao) {
        String sql = "UPDATE Usuario SET aprovado = 1 WHERE id = ?;";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String idStr = solicitacao.getIdUsuario().toString();

            stmt.setString(1, idStr);  
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao aprovar solicitação: " + e.getMessage());
        }
    }
    
    @Override
    public boolean existeAdministrador() {
        String sql = "SELECT COUNT(*) AS total FROM Administrador";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0; 
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de administradores: " + e.getMessage());
        }
        return false; 
    }
   

}
