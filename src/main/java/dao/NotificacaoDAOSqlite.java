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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import model.Notificacao;


/**
 *
 * @author Catterina Salvador
 */
public class NotificacaoDAOSqlite implements NotificacaoDAO{
    
    private Connection conexao;
    
    public NotificacaoDAOSqlite() {
        String url = "jdbc:sqlite:db/database.db";
        try {
            conexao = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }
    }

    @Override
    public List<Notificacao> buscarPorIdDestinatario(UUID idUsuarioDestinatario) {
        String sql = "SELECT * FROM notificacao WHERE id_usuario_destinatario = ?";
        List<Notificacao> notificacoes = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, idUsuarioDestinatario.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Notificacao notificacao = new Notificacao();
                    notificacao.setId(UUID.fromString(rs.getString("id")));
                    notificacao.setIdUsuarioDestinatario(UUID.fromString(rs.getString("id_usuario_destinatario")));
                    notificacao.setTitulo(rs.getString("titulo"));
                    notificacao.setConteudo(rs.getString("mensagem"));
                    notificacao.setLida(rs.getBoolean("lida"));
                    notificacao.setDataCriacao(LocalDate.parse(rs.getString("data_criacao")));
                    notificacoes.add(notificacao);
                }
                return notificacoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar notificações: " + e.getMessage());
        }
    }

    @Override
    public void lerNotificacao(UUID idNotificacao) {
        String sql = "UPDATE notificacao SET lida = 1 WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, idNotificacao.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler notificação: " + e.getMessage());
        }
    }

    @Override
    public void enviarNotificacao(Notificacao notificacao) {
        String sql = "INSERT INTO Notificacao (id, id_usuario_destinatario, lida, titulo, mensagem, data_criacao) "
                    + "VALUES (?, ?, ?, ?, ?, ?);";
        
        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, notificacao.getId().toString());
            stmt.setString(2, notificacao.getIdUsuarioDestinatario().toString());
            stmt.setInt(3, notificacao.isLida() ? 1 : 0);
            stmt.setString(4, notificacao.getTitulo());
            stmt.setString(5, notificacao.getConteudo());
            stmt.setString(6, notificacao.getDataCriacao().toString());
            
            stmt.executeUpdate();
            
        } catch (SQLException e){
            throw new RuntimeException("Erro ao enviar notificação: " + e.getMessage());
        }
        
    }

    @Override
    public Optional<Notificacao> buscarPorId(UUID idNotificacao) {
        String sql = "SELECT * FROM Notificacao WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, idNotificacao.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Notificacao notificacao = new Notificacao();
                    notificacao.setId(UUID.fromString(rs.getString("id")));
                    notificacao.setIdUsuarioDestinatario(UUID.fromString(rs.getString("id_usuario_destinatario")));
                    notificacao.setTitulo(rs.getString("titulo"));
                    notificacao.setConteudo(rs.getString("mensagem"));
                    notificacao.setLida(rs.getBoolean("lida"));
                    notificacao.setDataCriacao(LocalDate.parse(rs.getString("data_criacao")));
                    return Optional.of(notificacao);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário pelo ID: " + e.getMessage());
        }
    }
}
