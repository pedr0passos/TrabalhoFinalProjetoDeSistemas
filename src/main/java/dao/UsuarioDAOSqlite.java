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

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
 * @author João Victor Mascarenhas 
 */

public class UsuarioDAOSqlite implements UsuarioDAO {
    
    private Connection conexao;
    
    public UsuarioDAOSqlite() {
        String url = "jdbc:sqlite:db/database.db";
        try {
            conexao = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }
    
    @Override
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (id, username, senha,  data_criacao, is_autorizado, tipo, administrador) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String idStr = usuario.getId().toString();
            String dataCriacaoStr = usuario.getDataCriacao().toString();

            stmt.setString(1, idStr); 
            stmt.setString(2, usuario.getUserName());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, dataCriacaoStr); 
            stmt.setInt(5, usuario.getIsAutorizado() ? 1 : 0 );
            stmt.setString(6, usuario.getTipo());
            stmt.setInt(7, usuario.isAdministrador() ? 1 : 0);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    
    @Override
    public Optional<Usuario> buscarPorId(String id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(UUID.fromString(rs.getString("id"))); 
                    usuario.setUsername(rs.getString("username"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setDataCriacao(LocalDate.parse(rs.getString("data_criacao"))); 
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
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(UUID.fromString(rs.getString("id")));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setDataCriacao(LocalDate.parse(rs.getString("data_criacao"))); 
                    usuario.setTipo(rs.getString("tipo"));
                    usuario.setAdministrador(rs.getBoolean("administrador"));
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
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                    usuario.setId(UUID.fromString(rs.getString("id"))); 
                    usuario.setUsername(rs.getString("username"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setDataCriacao(LocalDate.parse(rs.getString("data_criacao"))); 
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os usuários: " + e.getMessage());
        }
        return usuarios;
    }
    
    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET username = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUserName());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
    
    @Override
    public void deletar(UUID id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            String idStr = id.toString();
            stmt.setString(1, idStr);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

}
