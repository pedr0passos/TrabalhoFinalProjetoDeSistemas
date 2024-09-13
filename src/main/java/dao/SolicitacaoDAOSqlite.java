package dao;

import model.Solicitacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.Usuario;
import singleton.ConexaoSingleton;

public class SolicitacaoDAOSqlite implements SolicitacaoDAO {

    private final Connection conexao;
    
    public SolicitacaoDAOSqlite() {
        this.conexao = ConexaoSingleton.getInstance().getConnection();
    }

    @Override
    public void inserir(Solicitacao solicitacao) {
        String sql = "INSERT INTO Solicitacao (id, idusuario, data_solicitacao, aprovada) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, solicitacao.getId().toString());
            stmt.setString(2, solicitacao.getIdUsuario().toString());
            stmt.setString(3, solicitacao.getDataSolicitacao().toString());
            stmt.setBoolean(4, solicitacao.isAprovada());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir solicitação: " + e.getMessage());
        }
    }
    
    @Override
    public void deletar(String idSolicitacao) {
        String sql = "DELETE FROM Solicitacao WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, idSolicitacao);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Solicitação com id " + idSolicitacao + " não encontrada.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar solicitação: " + e.getMessage(), e);
        }
    }

    
    @Override
    public List<Solicitacao> listarTodas() {
        String sql = "SELECT Solicitacao.id AS solicitacao_id, Solicitacao.id_usuario, Solicitacao.data_solicitacao, Solicitacao.aprovada, " +
                     "Usuario.username, Usuario.senha, Usuario.administrador, Usuario.is_autorizado " +
                     "FROM Solicitacao " +
                     "INNER JOIN Usuario ON Solicitacao.id_usuario = Usuario.id";

        List<Solicitacao> solicitacoes = new ArrayList<>();

        try (var stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("solicitacao_id"));
                UUID idUsuario = UUID.fromString(rs.getString("id_usuario"));
                LocalDate dataSolicitacao = LocalDate.parse(rs.getString("data_solicitacao"));
                boolean aprovada = rs.getBoolean("aprovada");

                // Cria um objeto Usuario usando os dados retornados do JOIN
                String userName = rs.getString("username");
                String senha = rs.getString("senha");
                boolean administrador = rs.getBoolean("administrador");
                boolean isAutorizado = rs.getBoolean("is_autorizado");
                Usuario usuario = new Usuario(userName, senha, administrador, isAutorizado);
                usuario.setId(idUsuario); // Definindo o ID do usuário, caso o construtor não o faça

                // Cria o objeto Solicitacao com o usuário associado
                Solicitacao solicitacao = new Solicitacao(id, usuario, dataSolicitacao, aprovada);
                solicitacoes.add(solicitacao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar solicitações: " + e.getMessage());
        }
        return solicitacoes;
    }

    @Override
    public Solicitacao buscarPorNomeUsuario(String nomeUsuario) {
        String sql = "SELECT Solicitacao.id, Solicitacao.id_usuario, Solicitacao.data_solicitacao, Solicitacao.aprovada, " +
                     "Usuario.username, Usuario.senha, Usuario.administrador, Usuario.is_autorizado, Usuario.data_criacao " +
                     "FROM Solicitacao " +
                     "INNER JOIN Usuario ON Solicitacao.id_usuario = Usuario.id " +
                     "WHERE Usuario.username = ?";

        Solicitacao solicitacao = null; // Inicializa como null

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // Usa `if` em vez de `while` para garantir que apenas uma solicitação seja retornada
                
                UUID id = UUID.fromString(rs.getString("id")); // Ajustado para usar alias 'S'
                UUID idUsuario = UUID.fromString(rs.getString("id_usuario")); // Ajustado para usar alias 'S'
                LocalDate dataSolicitacao = LocalDate.parse(rs.getString("data_solicitacao")); // Ajustado para usar alias 'S'
                boolean aprovada = rs.getBoolean("aprovada"); // Ajustado para usar alias 'S'

                // Cria o objeto Usuario usando as informações retornadas
                String userName = rs.getString("username"); // Ajustado para usar alias 'U'
                String senha = rs.getString("senha"); // Ajustado para usar alias 'U'
                boolean administrador = rs.getBoolean("administrador"); // Ajustado para usar alias 'U'
                Boolean isAutorizado = rs.getBoolean("is_autorizado"); // Ajustado para usar alias 'U'
                LocalDate dataCriacao = LocalDate.parse(rs.getString("data_criacao")); // Ajustado para usar alias 'U'

                // Cria o Usuario com base nos dados da tabela
                Usuario usuario = new Usuario(userName, senha, administrador, isAutorizado);
                usuario.setId(idUsuario); // Define o ID do usuário

                // Cria o objeto Solicitacao com o usuário associado
                solicitacao = new Solicitacao(id, usuario, dataSolicitacao, aprovada);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar solicitação por nome de usuário: " + e.getMessage());
        }

        return solicitacao; // Retorna apenas uma solicitação ou null se não encontrado
    }

    @Override
    public void atualizarAprovacao(String idSolicitacao, boolean aprovado) {
        String selectSQL = "SELECT id_usuario FROM Solicitacao WHERE id = ?";
        String updateUsuarioSQL = "UPDATE Usuario SET is_autorizado = true WHERE id = ?";
        String deleteSolicitacaoSQL = "DELETE FROM Solicitacao WHERE id = ?";

        try (PreparedStatement selectStmt = conexao.prepareStatement(selectSQL);
             PreparedStatement updateUsuarioStmt = conexao.prepareStatement(updateUsuarioSQL);
             PreparedStatement deleteSolicitacaoStmt = conexao.prepareStatement(deleteSolicitacaoSQL)) {

            selectStmt.setString(1, idSolicitacao);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                UUID idUsuario = UUID.fromString(rs.getString("id_usuario"));

                updateUsuarioStmt.setString(1, idUsuario.toString());
                updateUsuarioStmt.executeUpdate();

                deleteSolicitacaoStmt.setString(1, idSolicitacao);
                deleteSolicitacaoStmt.executeUpdate();

            } else {
                throw new RuntimeException("Solicitação com id " + idSolicitacao + " não encontrada.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aprovação: " + e.getMessage(), e);
        }
    }
}
