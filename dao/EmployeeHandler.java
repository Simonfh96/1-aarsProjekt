/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.PanelInterface;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Case;
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
        String sql = "SELECT * FROM employee WHERE username LIKE '" + username + "' AND userPassword = '" + password + "'";
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
    
    public Employee getEmployee(String username, String password) throws SQLException {
        Employee employee = null;
        boolean loggedIn = checkLogin(username, password);
        if (loggedIn == true) {
            try {
                String statement;
                statement = "SELECT * FROM employee WHERE username LIKE '" + username + "' AND userPassword = '" + password + "'";
                ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
                while (rs.next()) {
                    ArrayList<PanelInterface> myCases = new ArrayList<>();
                    employee = new Employee(rs.getInt("employee_id"), rs.getString("username"), rs.getString("userPassword"),
                            rs.getString("firstName"), rs.getString("lastName"),
                            rs.getInt("phone"), rs.getString("email"), rs.getBoolean("admin"), rs.getBoolean("partTime"), rs.getBoolean("active"), myCases);
                    myCases = CaseHandler.getInstance().getMyCases(employee);
                    employee.setMyCases(myCases);
                }
            } catch (SQLException ex) {
                
            }
        }
        return employee;
    }
    
    public Employee getEmployee(int employeeID) throws SQLException {
        Employee employee = null;
        PreparedStatement ps = null;
        String getEmployee = "SELECT * FROM employee WHERE employee_id = ?";
        ps = DBHandler.getInstance().getConn().prepareStatement(getEmployee);
        ps.setInt(1, employeeID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ArrayList<PanelInterface> myCases = new ArrayList<>();
            employee = new Employee(rs.getInt("employee_id"), rs.getString("username"), rs.getString("userPassword"),
                    rs.getString("firstName"), rs.getString("lastName"),
                    rs.getInt("phone"), rs.getString("email"), rs.getBoolean("admin"), rs.getBoolean("partTime"), rs.getBoolean("active"), myCases);
            myCases = CaseHandler.getInstance().getMyCases(employee);
            employee.setMyCases(myCases);
        }
        return employee;
    }
    
    public void changePasswordAndUsername(String username, String password, Employee e) throws SQLException {
        PreparedStatement ps = null;
        int employeeID = e.getEmployeeID();
        String updateLoginInfo = "UPDATE employee SET userName = ?, userPassword = ? WHERE employee_id = " + employeeID;
        ps = DBHandler.getInstance().conn.prepareStatement(updateLoginInfo);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();
        
    }
    
    public void deactivateEmployee(Employee e) throws SQLException {
        PreparedStatement ps = null;
        String setActive = "UPDATE employee SET active = ? WHERE employee_id = " + e.getEmployeeID();
        ps = DBHandler.getInstance().conn.prepareStatement(setActive);
        ps.setBoolean(1, e.isActive());
        ps.executeUpdate();
    }
    
    public void saveEmployee(Employee e) throws SQLException {
        CallableStatement cs = null;
        cs = DBHandler.getInstance().conn.prepareCall("{CALL AddEmployee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        cs.setInt(1, e.getEmployeeID());
        cs.setString(2, e.getUsername());
        cs.setString(3, e.getPassword());
        cs.setString(4, e.getFirstName());
        cs.setString(5, e.getLastName());
        cs.setInt(6, e.getPhone());
        cs.setString(7, e.getEmail());
        cs.setBoolean(8, e.isAdmin());
        cs.setBoolean(9, e.isPartTime());
        cs.setBoolean(10, e.isActive());
        cs.execute();
    }
    
    public int generateEmployeeID() throws SQLException {
        int employeeID = 0;
        String statement;
        statement = "SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        if (rs.next()) {
            employeeID = rs.getInt("employee_id") + 1;
        }
        return employeeID;
    }
    
    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }
}
