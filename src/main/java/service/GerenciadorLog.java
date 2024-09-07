package service;

import log.Log;

/**
 * @author 
 * Catterina Salvador
 * Pedro Henrique Passos Rocha 
 */

public class GerenciadorLog {
    public void processarLogs(String operacao, Log log) {
        log.gravarLog(operacao);
    }
}
