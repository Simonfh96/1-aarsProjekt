/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
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

    public ArrayList<Log> gatherLogs(Date from, Date to) throws SQLException {
        ArrayList<Log> logs = null;
        PreparedStatement ps = null;
        String gatherLogs = "SELECT * FROM logs LEFT JOIN employee ON log.employee_id = employee.employee_id "
                + "WHERE timeMade BETWEEN '?' AND '?'";
        ps = DBHandler.getInstance().conn.prepareStatement(gatherLogs);
        ps.setDate(1, from);
        ps.setDate(2, to);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Employee employee = EmployeeHandler.getInstance().getEmployee(rs.getInt("employee_id"));
            Log log = new Log(employee, rs.getString("actionMade"), rs.getString("componentName"), rs.getString("changedFrom"), rs.getString("changedTo"), rs.getDate("timeMade"));
            logs.add(log);
        }
        return logs;
    }

    //Benyt metoden gatherLogs() til at lave en ArrayList at udskrive
    //Kør den eventuelt i en Thread, da længere logs kan tage tid at udskrive
    public void writeLogToFile(ArrayList<Log> logs) throws IOException {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null);
        PrintWriter pw = new PrintWriter(fc.getSelectedFile() + ".txt");
        for (Log log : logs) {
            pw.print(log.toString());
        }
        pw.close();

    }

    public void saveLog(Log log) throws SQLException {
        String insertLog = "INSERT INTO log (employee_id, actionMade, componentName, changedFrom, changedTo, timeMade)"
                + " values (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBHandler.getInstance().conn.prepareStatement(insertLog);
        ps.setInt(1, log.getEmployee().getEmployeeID());
        ps.setString(2, log.getActionMade());
        ps.setString(3, log.getComponentName());
        ps.setString(4, log.getChangedFrom());
        ps.setString(5, log.getChangedTo());
        ps.setDate(6, (Date) log.getDate());
        ps.execute();
        DBHandler.getInstance().conn.close();
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
