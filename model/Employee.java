/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import model.Case;
import interfaces.PanelInterface;
import java.util.ArrayList;

/**
 *
 * @author pdyst
 */
public class Employee {
    private int employeeID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private int phone;
    private String email;
    private boolean admin;
    private boolean partTime;
    private boolean active;
    private ArrayList<PanelInterface> myCases;

    public Employee(int employeeID, String username, String password, String firstName, String lastName, int phone, String email, boolean admin, boolean partTime, boolean active,ArrayList<PanelInterface> myCases) {
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = firstName + " " + lastName;
        this.phone = phone;
        this.email = email;
        this.admin = admin;
        this.partTime = partTime;
        this.active = active;
        this.myCases = myCases;
    }
    
    public boolean checkAddedMyCases(Case c) {
        boolean added = false;
        for (PanelInterface myCase :  myCases) {
            Case mc = (Case) myCase;
            if (c.getKonsNmb()  == mc.getKonsNmb()) {
                added = true;
                System.out.println("already added");
            }
        }
        return added;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    

    public int getEmployeeID() {
        return employeeID;
    }

    public ArrayList<PanelInterface> getMyCases() {
        return myCases;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isPartTime() {
        return partTime;
    }

    public void setMyCases(ArrayList<PanelInterface> myCases) {
        this.myCases = myCases;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    
}
