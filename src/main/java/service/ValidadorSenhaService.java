/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.pss.senha.validacao.ValidadorSenha;

/**
 *
 * @author pedro
 */
public class ValidadorSenhaService {
    
    private ValidadorSenha validador;
    
    public ValidadorSenhaService() {
        this.validador = new ValidadorSenha();
    }
    
    public boolean validarSenha(String senha) throws Exception {
        var validacoes = validador.validar(senha);
    
        if (!validacoes.isEmpty()) {
            // Concatena as mensagens de validação, cada uma em uma nova linha
            String mensagemErro = String.join("\n", validacoes);
            throw new Exception("Erro de validação da senha:\n" + mensagemErro);
        }
        
        return true;
    }
}
