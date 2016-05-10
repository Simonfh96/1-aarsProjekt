/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author pdyst
 */
public class EmployeeHandler {
    private static EmployeeHandler instance;
    
    private EmployeeHandler() {
        
    }
    
    
    
    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }
}
