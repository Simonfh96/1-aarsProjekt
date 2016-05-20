/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Task;

/**
 *
 * @author pdysted
 */
public class TaskHandler {
    private static TaskHandler instance;
    
    private TaskHandler() {
        
    }
    
    public void editTask(Task task) {
        
    }
    
    public void saveTask(Task task) throws SQLException {
        String insertTask = "INSERT INTO tasks (tStatus, description)"
                + " values (?, ?)";
        PreparedStatement ps = DBHandler.getInstance().conn.prepareStatement(insertTask);
        ps.setString(1, task.getStatus());
        ps.setString(2, task.getDescription());
        ps.execute();
        DBHandler.getInstance().conn.close();
    }
    
    public static TaskHandler getInstance() {
        if (instance == null) {
            instance = new TaskHandler();
        }
        return instance;
    }
}
