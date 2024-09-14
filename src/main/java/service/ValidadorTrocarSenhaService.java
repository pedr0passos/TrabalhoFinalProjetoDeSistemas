package service;

import javax.swing.JOptionPane;

/**
 * Serviço para validação de entradas relacionadas a senhas.
 */
public class ValidadorTrocarSenhaService {

    private final ValidadorSenhaService validadorService;
    private final Object view;
    private final Object log;
    private final String senhaAtual;
    private final String senhaUsuarioSalvo;
    private final String novaSenha;
    private final String confirmarSenha;

    public ValidadorTrocarSenhaService(
            ValidadorSenhaService validadorService, 
            Object view, 
            Object log,
            String senhaAtual, 
            String senhaUsuarioSalvo, 
            String novaSenha, 
            String confirmarSenha) {
        
        this.validadorService = validadorService;
        this.view = view;
        this.log = log;
        this.senhaAtual = senhaAtual;
        this.senhaUsuarioSalvo = senhaUsuarioSalvo;
        this.novaSenha = novaSenha;
        this.confirmarSenha = confirmarSenha;
    }

    public boolean validar() {
        if (novaSenha.isEmpty()) {
            mostrarMensagem("Senha é obrigatória.", "Erro");
            registrarLog("Senha vazia");
            return false;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            mostrarMensagem("Senhas diferentes na confirmação.", "Erro");
            registrarLog("Senhas não conferem");
            return false;
        }

        if (!senhaAtual.equals(senhaUsuarioSalvo)) {
            mostrarMensagem("Senha atual incorreta.", "Erro");
            registrarLog("Senha atual incorreta");
            return false;
        }

        try {
            validadorService.validarSenha(novaSenha);
        } catch (Exception ex) {
            mostrarMensagem(ex.getMessage(), "Erro de Validação");
            registrarLog("Erro de validação da senha");
            return false;
        }

        return true;
    }

    private void mostrarMensagem(String mensagem, String titulo) {
        JOptionPane.showMessageDialog((java.awt.Component) view, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    private void registrarLog(String mensagemErro) {
        if (log != null) {
            // Supondo que o método de gravação de log seja implementado aqui.
            // Aqui você pode adicionar a lógica de registro do log.
        }
    }
}
