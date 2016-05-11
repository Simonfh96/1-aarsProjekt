/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author pdyst
 */
public class Employee {
    private int employeeID;
    private String username;
    private String password;
    private String name;
    private int phone;
    private String email;
    private boolean admin;
    private boolean partTime;
    private ArrayList<Case> myCases;

    public Employee(int employeeID, String username, String password, String name, int phone, String email, boolean admin, boolean partTime) {
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.admin = admin;
        this.partTime = partTime;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public ArrayList<Case> getMyCases() {
        return myCases;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isPartTime() {
        return partTime;
    }
    
    
    
    
}
