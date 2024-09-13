package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.Usuario;
import singleton.ConexaoSingleton;
import model.Administrador;
import singleton.ConexaoSingleton;

/**
 * @author 
 * Catterina Vittorazzi Salvador
 * Pedro Henrique Passos Rocha
 * João Victor Mascarenhas 
 */

public class UsuarioDAOSqlite implements UsuarioDAO {

    private Connection conexao;

    public UsuarioDAOSqlite() {
        this.conexao = ConexaoSingleton.getInstance().getConnection();
    }

    @Override
    public void inserir(Usuario usuario) {
        String sqlUsuario = "INSERT INTO Usuario (id, username, senha, data_criacao, tipo, administrador, is_autorizado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario)) {
            stmtUsuario.setString(1, usuario.getId().toString());
            stmtUsuario.setString(2, usuario.getUserName());
            stmtUsuario.setString(3, usuario.getSenha());
            stmtUsuario.setString(4, usuario.getDataCriacao().toString()); // Alterado de setBoolean para setString
            stmtUsuario.setString(5, usuario.getTipo());
            stmtUsuario.setBoolean(6, usuario.isAdministrador());
            stmtUsuario.setBoolean(7, usuario.getIsAutorizado()); // Incluindo o campo is_autorizado

            stmtUsuario.executeUpdate();

            if (usuario instanceof Administrador) {
                String sqlAdmin = "INSERT INTO Administrador (id, id_usuario) VALUES (?, ?)";
                try (PreparedStatement stmtAdmin = conexao.prepareStatement(sqlAdmin)) {
                    stmtAdmin.setString(1, ((Administrador) usuario).getId().toString());
                    stmtAdmin.setString(2, usuario.getId().toString());
                    stmtAdmin.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage());
        }
    }


    @Override
    public Optional<Usuario> buscarPorId(String id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapResultSetToUsuario(rs);
                    return Optional.of(usuario);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário pelo ID: " + e.getMessage());
        }
    }

    @Override
    public Optional<Usuario> buscarPorNome(String nome) {
        String sql = "SELECT * FROM Usuario WHERE username = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapResultSetToUsuario(rs);
                    return Optional.of(usuario);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário pelo nome: " + e.getMessage());
        }
    }


    @Override
    public List<Usuario> listar() {
        String sql = "SELECT * FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = mapResultSetToUsuario(rs);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os usuários: " + e.getMessage());
        }
        return usuarios;
    }


    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET username = ?, senha = ?, tipo = ?, administrador = ?, is_autorizado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUserName());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipo());
            stmt.setBoolean(4, usuario.isAdministrador());
            stmt.setBoolean(5, usuario.getIsAutorizado()); // Incluindo o campo is_autorizado
            stmt.setString(6, usuario.getId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }


    @Override
    public void deletar(UUID id) {
        String sqlUsuario = "DELETE FROM Usuario WHERE id = ?";
        String sqlAdmin = "DELETE FROM Administrador WHERE id_usuario = ?";

        try (PreparedStatement stmtAdmin = conexao.prepareStatement(sqlAdmin);
             PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario)) {

            stmtAdmin.setString(1, id.toString());
            stmtAdmin.executeUpdate();

            stmtUsuario.setString(1, id.toString());
            stmtUsuario.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    @Override
    public boolean possuiUsuario() {
        String sql = "SELECT COUNT(*) AS total FROM Usuario";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0; // Retorna true se houver pelo menos um usuário
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de usuários: " + e.getMessage());
        }

        return false; // Retorna false se não houver nenhum usuário
    }

    
    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.fromString(rs.getString("id")));
        usuario.setUsername(rs.getString("username"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setDataCriacao(LocalDate.parse(rs.getString("data_criacao")));
        usuario.setTipo(rs.getString("tipo"));
        usuario.setAdministrador(rs.getBoolean("administrador"));
        usuario.setIsAutorizado(rs.getBoolean("is_autorizado")); // Incluindo o campo is_autorizado

        return usuario;
    }

}
