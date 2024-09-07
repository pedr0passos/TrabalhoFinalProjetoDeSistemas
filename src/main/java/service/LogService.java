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
    public LogService(/*algo pra settar o tipo do log*/){
        //logica de pegar o tipo do log
    }
    public Log configuraLog() {
        if (true) {//ta true pq nao sei pegar a condição ainda
            log = new CsvAdapter(new CsvLog());
        } else if (false) {//ta false pq nao sei pegar a condição ainda
            log = new JsonAdapter(new JsonLog());
        }
        return log;
    }

}
