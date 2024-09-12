package dao;

import model.Solicitacao;
import java.util.List;

public interface SolicitacaoDAO {
    
    void inserir(Solicitacao solicitacao);
    
     void deletar(String idSolicitacao);
    
    List<Solicitacao> listarTodas(); 
    
    Solicitacao buscarPorNomeUsuario(String nomeUsuario); 
    
    void atualizarAprovacao(String idSolicitacao, boolean aprovado);
    
}
