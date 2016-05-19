/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Task;

/**
 *
 * @author pdysted
 */
public class TaskHandler {
    private static TaskHandler instance;
    
    private TaskHandler() {
        
    }
    
    public void saveTask(Task task) {
        
    }
    
    public static TaskHandler getInstance() {
        if (instance == null) {
            instance = new TaskHandler();
        }
        return instance;
    }
}
