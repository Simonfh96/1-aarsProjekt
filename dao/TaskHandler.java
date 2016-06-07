/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Task;

/**
 *
 * @author pdysted
 */
public class TaskHandler {
    private static TaskHandler instance;
    
    private TaskHandler() {
        
    }
    
    public ArrayList<Task> getTasks(int taskID) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        PreparedStatement ps = null;
        String getTasks = "SELECT * FROM tasks WHERE object_id = ?";
        ps = DBHandler.getInstance().getConn().prepareStatement(getTasks);
        ps.setInt(1, taskID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Task task = new Task(rs.getString("tStatus"), rs.getString("description"));
            tasks.add(task);
        }
        rs.close();
        return tasks;
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
    }
    
    public static TaskHandler getInstance() {
        if (instance == null) {
            instance = new TaskHandler();
        }
        return instance;
    }
}
