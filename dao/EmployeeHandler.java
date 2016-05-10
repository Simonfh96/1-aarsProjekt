/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Employee;

/**
 *
 * @author pdyst
 */
public class EmployeeHandler {
    private static EmployeeHandler instance;
    
    private EmployeeHandler() {
        
    }
    //Skriv om private metode kald i rapporten
    private boolean checkLogin(String username, String password) throws SQLException {
        boolean check = false;
        DBHandler dbh = DBHandler.getInstance();
        Statement stm = (Statement) dbh.getConn().createStatement();
        String sql = "SELECT * FROM users WHERE username = '" + username + "' AND pssword = '" + password + "'";
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            check = true;
            System.out.println("works");
        }
        if (check == false) {
            System.out.println(username + " " + password);
            System.out.println("wrong password");
        }
        return check;
    }
    
    public Employee getEmployee(String username, String password) throws SQLException{
        Employee employee = null;
        boolean loggedIn = checkLogin(username, password);
        if (loggedIn) {
        try {
            String statement;
            statement = "SELECT * FROM users WHERE username LIKE '" + username + "' AND pssword = '" + password + "'";
            System.out.println(statement);
            ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);

            while (rs.next()) {
                //employee = new Employee(username, password, username, 0, username, true, true);
            }
        } catch (SQLException ex) {

        }
        }
        return employee;
    }
    
    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }
}
