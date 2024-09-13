package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Administrador;
import model.Solicitacao;

public class AdministradorDAOSqlite implements AdministradorDAO {

    private static Connection conexao;

    // Implementando Singleton para a conexão
    private static Connection getConexao() {
        if (conexao == null) {
            String url = "jdbc:sqlite:db/database.db";
            try {
                conexao = DriverManager.getConnection(url);
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao conectar ao banco de dados: " + ex.getMessage());
            }
        }
        return conexao;
    }

    @Override
    public void inserir(Administrador admin) {
        String sql = "INSERT INTO Administrador (id, id_usuario) VALUES (?, ?);";
        try (PreparedStatement stmt = getConexao().prepareStatement(sql)) {

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
        try (PreparedStatement stmt = getConexao().prepareStatement(sql)) {

            String idStr = solicitacao.getIdUsuario().toString();

            stmt.setString(1, idStr);  
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao aprovar solicitação: " + e.getMessage());
        }
    }
}
