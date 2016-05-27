/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.PanelInterface;
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
                System.out.println(statement);
                ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
                //bliver ikke oprettet med en arraylist derfor nullpointer exception
                while (rs.next()) {
                    ArrayList<PanelInterface> myCases = new ArrayList<>();
                    employee = new Employee(rs.getInt("employee_id"), rs.getString("username"), rs.getString("userPassword"),
                            rs.getString("firstName") + " " + rs.getString("lastName"),
                            rs.getInt("phone"), rs.getString("email"), rs.getBoolean("admin"), rs.getBoolean("partTime"), myCases);
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
                    rs.getString("firstName") + " " + rs.getString("lastName"),
                    rs.getInt("phone"), rs.getString("email"), rs.getBoolean("admin"), rs.getBoolean("partTime"), myCases);
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

    public void deactivateEmployee(Employee employee) {

    }
    
    public void createEmployee(Employee employee) throws SQLException {
        String statement;
        statement = "INSERT INTO employee (userName, userPassword, firstName, lastName, phone, email, admin, partTime)"
                + "VALUES ('" + employee.getUsername() + "','" + employee.getPassword() + "','"
                + employee.getName();
    }
    
    /*
    public void saveCostumer(Costumer costumer) throws SQLException {
        String statement;
        statement = "INSERT INTO customer (costumerName,  museumAcro, museumNmb, phone, email)"
                + " VALUES ( '" + costumer.getCostumerName()
                + "','" + costumer.getmAcro() + "','" + costumer.getmNumb() + "','"
                + costumer.getPhone() + "','" + costumer.getEmail() + "')";
        DBHandler.getInstance().conn.createStatement().executeUpdate(statement);
    }
    */
    

    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }
}
