/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import log.*;
import dao.LogDAO;
import dao.LogDAOSqlite;

/**
 *
 * @author Joao Victor
 */
public class LogService {

    private Log log;
    
    public LogService() {
        // Construtor sem argumentos, você configura o log depois
    }

    public void configuraLog() {  
        LogDAO logDAO = new LogDAOSqlite();
        if ("CSV".equalsIgnoreCase(logDAO.retornaTipo())) {
            log = new CsvAdapter(new CsvLog());
        } else if ("Json".equalsIgnoreCase(logDAO.retornaTipo())) {
            log = new JsonAdapter(new JsonLog());
        }
    }
    
    public Log getLog() {
        return log;
    }
}
