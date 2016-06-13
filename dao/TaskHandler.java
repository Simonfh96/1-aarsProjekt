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
import javax.swing.JComboBox;
import model.Task;

/**
 *
 * @author pdysted
 */
public class TaskHandler {
    private static TaskHandler instance;
    
    private TaskHandler() {
        
    }
    
    public void fillTaskNameBox(JComboBox comboBox) throws SQLException {
        PreparedStatement ps = null;
        String getTasks = "SELECT * FROM taskNames";
        ps = DBHandler.getInstance().getConn().prepareStatement(getTasks);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
             comboBox.addItem((String) rs.getString("tName"));
        }
        rs.close();  
    }
    
    public ArrayList<Task> getTasks(int taskID) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        PreparedStatement ps = null;
        String getTasks = "SELECT * FROM tasks WHERE object_id = ?";
        ps = DBHandler.getInstance().getConn().prepareStatement(getTasks);
        ps.setInt(1, taskID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Task task = new Task(rs.getString("tStatus"), rs.getString("tName"), rs.getString("description"), rs.getInt("object_id"));
            tasks.add(task);
        }
        rs.close();
        return tasks;
    }
    
    public void editTask(Task task) {
        
    }
    
    public void saveTask(Task task) throws SQLException {
        String insertTask = "INSERT INTO tasks (tStatus, tName, description, object_id)"
                + " values (?, ?, ?, ?)";
        PreparedStatement ps = DBHandler.getInstance().conn.prepareStatement(insertTask);
        ps.setString(1, task.getStatus());
        ps.setString(2, task.getName());
        ps.setString(3, task.getDescription());
        ps.setInt(4, task.getArticleID());
        ps.execute();
    }
    
    public void saveTaskName(String taskName) throws SQLException {
        String insertTask = "INSERT INTO taskNames (tName)"
                + " values (?)";
        PreparedStatement ps = DBHandler.getInstance().conn.prepareStatement(insertTask);
        ps.setString(1, taskName);
        ps.execute();
    }
    
    public static TaskHandler getInstance() {
        if (instance == null) {
            instance = new TaskHandler();
        }
        return instance;
    }
}
