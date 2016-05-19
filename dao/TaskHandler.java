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
public class TaskHandler {
    private static TaskHandler instance;
    
    private TaskHandler() {
        
    }
    
    public static TaskHandler getInstance() {
        if (instance == null) {
            instance = new TaskHandler();
        }
        return instance;
    }
}
