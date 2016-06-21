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
    private int checkLogin(String username, String password) throws SQLException {
        int check = -1;
        DBHandler dbh = DBHandler.getInstance();
        Statement stm = (Statement) dbh.getConn().createStatement();
        String sql = "SELECT * FROM employee WHERE username LIKE '" + username + "' AND userPassword = '" + password + "'";
        ResultSet rs = stm.executeQuery(sql);
        if (rs.next()) {
            check = rs.getInt("employee_id");
            
        }
        if (check < 0) {
            System.out.println(username + " " + password);
            System.out.println("wrong password");
        }
        return check;
    }

    public Employee getEmployee(String username, String password) throws SQLException {
        Employee employee = null;
        int id = checkLogin(username, password);
        if (id > 0) {
            employee = getEmployee(id);
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
        ArrayList<PanelInterface> myCases = new ArrayList<>();
        if (rs.next()) {
            employee = new Employee(rs.getInt("employee_id"), rs.getString("username"), rs.getString("userPassword"),
                    rs.getString("firstName"), rs.getString("lastName"), rs.getString("initials"),
                    rs.getInt("phone"), rs.getString("email"), rs.getBoolean("admin"), rs.getBoolean("partTime"), rs.getBoolean("active"), myCases);
        }
        rs.close();

        myCases = CaseHandler.getInstance().getMyCases(employee);
        employee.setMyCases(myCases);

        return employee;
    }

    public ArrayList<PanelInterface> getCaseResponsibles(Case c) throws SQLException {
        PreparedStatement ps = null;
        String getEmployee = "SELECT * FROM caseResponsible LEFT JOIN employee ON caseResponsible.employee_id = employee.employee_id WHERE caseResponsible.employee_id = ?";
        ps = DBHandler.getInstance().getConn().prepareStatement(getEmployee);
        ps.setInt(1, c.getCaseID());
        ResultSet rs = ps.executeQuery();
        ArrayList<PanelInterface> employees = new ArrayList<>();
        while (rs.next()) {
            Employee employee = new Employee(rs.getInt("employee_id"), rs.getString("firstName")
                    + " " + rs.getString("lastName"), rs.getString("initials"));
            employees.add(employee);
        }
        rs.close();
        return employees;
    }

    public boolean changePasswordAndUsername(String username, String password, String password2, Employee e) throws SQLException {
        boolean passwordsMatch = false;
        if (password.equals(password2)) {
            passwordsMatch = true;
        }
        if (passwordsMatch) {
        PreparedStatement ps = null;
        int employeeID = e.getEmployeeID();
        String updateLoginInfo = "UPDATE employee SET userName = ?, "
                + "userPassword = ? WHERE employee_id = " + employeeID;
        ps = DBHandler.getInstance().conn.prepareStatement(updateLoginInfo);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();
        }
        return passwordsMatch;
    }

    public void resetPassword(Employee e) throws SQLException {
        PreparedStatement ps = null;
        int employeeID = e.getEmployeeID();
        String resetPassword = "UPDATE employee SET userPassword = ? WHERE employee_id = " + employeeID;
        ps = DBHandler.getInstance().conn.prepareStatement(resetPassword);
        ps.setString(1, e.getPassword());
        ps.executeUpdate();
    }

    public void deactivateEmployee(Employee e) throws SQLException {
        PreparedStatement ps = null;
        String setActive = "UPDATE employee SET active = ? WHERE employee_id = " + e.getEmployeeID();
        ps = DBHandler.getInstance().conn.prepareStatement(setActive);
        ps.setBoolean(1, e.isActive());
        ps.executeUpdate();
    }
    
    public void changePersonalInfo(Employee e) throws SQLException {
        PreparedStatement ps = null;
        String setActive = "UPDATE employee SET email = ?, phone = ? WHERE employee_id = " + e.getEmployeeID();
        ps = DBHandler.getInstance().conn.prepareStatement(setActive);
        ps.setString(1, e.getEmail());
        ps.setInt(2, e.getPhone());
        ps.executeUpdate();
    }

    public void saveEmployee(Employee e) throws SQLException {
        CallableStatement cs = null;
        cs = DBHandler.getInstance().conn.prepareCall("{CALL AddEmployee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        cs.setInt(1, e.getEmployeeID());
        cs.setString(2, e.getUsername());
        cs.setString(3, e.getPassword());
        cs.setString(4, e.getFirstName());
        cs.setString(5, e.getLastName());
        cs.setString(6, e.getInitials());
        cs.setInt(7, e.getPhone());
        cs.setString(8, e.getEmail());
        cs.setBoolean(9, e.isAdmin());
        cs.setBoolean(10, e.isPartTime());
        cs.setBoolean(11, e.isActive());
        cs.execute();
    }

    public void saveCaseResponsibles(Object[] eCRs, Case c) throws SQLException {
        CallableStatement cs = null;
        if (eCRs.length > 0) {
        for (Object eCR : eCRs) {
            Employee e = (Employee) eCR;
            cs = DBHandler.getInstance().conn.prepareCall("{CALL AddCaseResponsibles(?, ?)}");
            cs.setInt(1, c.getCaseID());
            cs.setInt(2, e.getEmployeeID());
            cs.execute();
        }
        }
    }

    public ArrayList<PanelInterface> selectAllEmployees() throws SQLException {
        ArrayList<PanelInterface> employees = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM employee;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Employee employee = new Employee(rs.getInt("employee_id"), rs.getString("username"), rs.getString("userPassword"),
                    rs.getString("firstName"), rs.getString("lastName"), rs.getString("initials"),
                    rs.getInt("phone"), rs.getString("email"), rs.getBoolean("admin"), rs.getBoolean("partTime"), rs.getBoolean("active"), null);
            employees.add(employee);
        }
        for (PanelInterface employee : employees) {
            Employee e = (Employee) employee;
            ArrayList<PanelInterface> myCases = CaseHandler.getInstance().getMyCases(e);
            e.setMyCases(myCases);
        }
        rs.close();
        return employees;
    }

    public int compareCRTo(ArrayList<PanelInterface> employeesCR, Employee e) {
        int result = -1;
        for (PanelInterface empCR : employeesCR) {
            Employee eCR = (Employee) empCR;
            if (e.compareTo(eCR) == 0) {
                result = 0;
            }
            
        }
        return result;
    }
    
    public ArrayList<PanelInterface> selectAllEmployeesSorted(ArrayList<PanelInterface> caseResponsibles) throws SQLException {
        ArrayList<PanelInterface> allEmps = selectAllEmployees();
        ArrayList<PanelInterface> allEmpsSorted = new ArrayList<>();
        for (PanelInterface emp : allEmps) {
            Employee e = (Employee) emp;
            if (!(compareCRTo(caseResponsibles, e) == 0)) {
                allEmpsSorted.add(e);
            }
        }
        return allEmpsSorted;
    }

    public int generateEmployeeID() throws SQLException {
        int employeeID = 0;
        String statement;
        statement = "SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        if (rs.next()) {
            employeeID = rs.getInt("employee_id") + 1;
        }
        rs.close();
        return employeeID;
    }

    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }
}
