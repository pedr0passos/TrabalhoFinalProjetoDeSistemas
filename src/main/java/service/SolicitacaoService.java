package service;

import dao.SolicitacaoDAO;
import dao.SolicitacaoDAOSqlite;
import model.Solicitacao;
import java.util.List;

public class SolicitacaoService {
    
    private final SolicitacaoDAO solicitacaoDAO;

    public SolicitacaoService() {
        this.solicitacaoDAO = new SolicitacaoDAOSqlite();
    }
    
    public void inserirSolicitacao(Solicitacao solicitacao) {
        solicitacaoDAO.inserir(solicitacao);
    }
    
    public List<Solicitacao> listarSolicitacoes() {
        return solicitacaoDAO.listarTodas();
    }
    
    public void aprovarSolicitacao(String nome) {
        var solicitacao =  solicitacaoDAO.buscarPorNomeUsuario(nome);
        if (solicitacao != null) {
            String solicitacaoId = solicitacao.getId().toString();
            solicitacaoDAO.atualizarAprovacao(solicitacaoId, true); 
        }
    }
    
    public void removerSolicitacao(String nome) {
        var solicitacao = solicitacaoDAO.buscarPorNomeUsuario(nome);
        if (solicitacao != null) {
            String solicitacaoId = solicitacao.getId().toString();
            solicitacaoDAO.deletar(solicitacaoId); // Remove a solicitação do banco de dados
        }
    }
}
