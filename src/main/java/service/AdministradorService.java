package service;

import dao.AdministradorDAO;
import dao.AdministradorDAOSqlite;
import dao.UsuarioDAO;
import dao.UsuarioDAOSqlite;
import model.Solicitacao;
import model.Usuario;

public class AdministradorService {
    private final AdministradorDAO administradorDAO;
    private final UsuarioDAO usuarioDAO;

    public AdministradorService(){
        this.administradorDAO = new AdministradorDAOSqlite();
        this.usuarioDAO = new UsuarioDAOSqlite();
    }

    public void aprovarSolicitacao(Solicitacao solicitacao){
        administradorDAO.aprovarSolicitacao(solicitacao);
    }

    public void enviarNotificacao() {
        //insere uma notificação no banco de dados
    }
    
    public void CriarUsuario(Usuario usuario) {
        usuarioDAO.inserir(usuario);
    }
    
    public void EditarUsuario(Usuario usuario) {
        usuarioDAO.atualizar(usuario);
    }

    public void ExcluirUsuario(Usuario usuario) {
        usuarioDAO.deletar(usuario.getId());
    }
}
 