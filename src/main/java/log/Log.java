package log;

/**
 * @author Catterina Salvador
 * @author Pedro Henrique Passos Rocha
 */

public interface Log {
    void gravarLog(String operacao, String nome, String usuario, boolean sucesso, String mensagemFalha);
}
