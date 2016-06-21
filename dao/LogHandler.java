/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import model.Employee;
import model.Log;

/**
 *
 * @author pdysted
 */
public class LogHandler implements Runnable {

    private static LogHandler instance;

    private LogHandler() {

    }

    public ArrayList<Log> getCaseLogs(int logID) throws SQLException {
        ArrayList<Log> logs = new ArrayList<>();
        PreparedStatement ps = null;
        String getLogs = "SELECT * FROM log WHERE case_id = ?";
        ps = DBHandler.getInstance().getConn().prepareStatement(getLogs);
        ps.setInt(1, logID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Log log = new Log(null, rs.getInt("case_id"), rs.getString("actionMade"), rs.getString("componentName"),
                    rs.getString("changedFrom"), rs.getString("changedTo"), rs.getDate("timeMade"));
            logs.add(log);
        }
        rs.close();
        for (Log log : logs) {
            Employee employee = EmployeeHandler.getInstance().getEmployee(getEmployeeID());
            log.setEmployee(employee);
        }
        
        return logs;

    }
    
    public int getEmployeeID() throws SQLException {
        int employeeID = 0;
        String statement;
        statement = "SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        if (rs.next()) {
            employeeID = rs.getInt("employee_id");
        }
        rs.close();
        return employeeID;
    }

    public ArrayList<Log> gatherLogs(java.util.Date from, java.util.Date to, JTextArea logTextArea) throws SQLException {
        ArrayList<Log> logs = null;
        PreparedStatement ps = null;
        java.sql.Date sqlFrom = new java.sql.Date(from.getTime());
        java.sql.Date sqlTo = new java.sql.Date(to.getTime());
        String gatherLogs = "SELECT * FROM logs LEFT JOIN employee ON log.employee_id = employee.employee_id "
                + "WHERE timeMade BETWEEN '?' AND '?'";
        ps = DBHandler.getInstance().conn.prepareStatement(gatherLogs);
        ps.setDate(1, sqlFrom);
        ps.setDate(2, sqlTo);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Employee employee = EmployeeHandler.getInstance().getEmployee(rs.getInt("employee_id"));
            Log log = new Log(employee, rs.getInt("case_id"),rs.getString("actionMade"), rs.getString("componentName"), rs.getString("changedFrom"), rs.getString("changedTo"), rs.getDate("timeMade"));
            logTextArea.append(log.toString() + "\n");
            logs.add(log);
        }
        rs.close();
        return logs;
    }

    //Benyt metoden gatherLogs() til at lave en ArrayList at udskrive
    //Kør den eventuelt i en Thread, da længere logs kan tage tid at udskrive
    //Den virker ikke endnu spørg David, hvorvidt man skal benytte sig af en thread eller ej
    public void writeLogToFile(ArrayList<Log> logs) throws IOException {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null);
        PrintWriter pw = new PrintWriter(fc.getSelectedFile() + ".txt");
        for (Log log : logs) {
            pw.print(log.toString());
        }
        pw.close();

    }

    public void saveLog(Log l) throws SQLException {
        CallableStatement cs = null;
        cs = DBHandler.getInstance().conn.prepareCall("{CALL AddLog(?, ?, ?, ?, ?, ?, ?)}");
        cs.setInt(1, l.getEmployee().getEmployeeID());
        cs.setInt(2, l.getCaseID());
        cs.setString(3, l.getActionMade());
        cs.setString(4, l.getComponentName());
        cs.setString(5, l.getChangedFrom());
        cs.setString(6, l.getChangedTo());
        cs.setDate(7, l.getDate());
        cs.execute();
    }

    @Override
    public void run() {
        try {

            Thread.sleep(50);
        } catch (InterruptedException ex) {
            System.out.println("Log udskrivning afbrudt");
        }

    }

    public static LogHandler getInstance() {
        if (instance == null) {
            instance = new LogHandler();
        }
        return instance;
    }
}
