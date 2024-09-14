/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author Joao Victor
 */
public interface LogDAO {
    public void updateLog(int id, String tipoLog);
    public String retornaTipo();
}
