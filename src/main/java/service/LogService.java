/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import log.*;

/**
 *
 * @author Joao Victor
 */
public class LogService {

    private Log log;
    
    public LogService() {
        // Construtor sem argumentos, você configura o log depois
    }

    public void configuraLog(String tipoLog) {   
        if ("CSV".equalsIgnoreCase(tipoLog)) {
            log = new CsvAdapter(new CsvLog());
        } else if ("Json".equalsIgnoreCase(tipoLog)) {
            log = new JsonAdapter(new JsonLog());
        }
    }
    
    public Log getLog() {
        return log;
    }
}
