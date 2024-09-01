/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Usuario;

/**
 * @author Catterina Vittorazzi Salvador
 * @author Pedro Henrique Passos Rocha
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
        String sql = "INSERT INTO usuarios (username, senha) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getSenha());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1)); // Assume que o ID é um Long
                } else {
                    throw new SQLException("Falha ao inserir usuário, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage());
        }
    }
    
    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setSenha(rs.getString("senha"));
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
    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setSenha(rs.getString("senha"));
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
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getSenha());
            stmt.setLong(3, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
    
    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

}
