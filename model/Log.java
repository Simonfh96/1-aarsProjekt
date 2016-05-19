/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author pdysted
 */
public class Log {
    private static ArrayList<Log> logs;
    private final String employee;
    private final String action;
    private final String componentName;
    private final String changedTo;
    private final Date date;

    public Log(Employee employee, String action, String componentName, String changedTo, Date date) {
        this.employee = employee.getName();
        this.action = action;
        this.componentName = componentName;
        this.changedTo = changedTo;
        this.date = date;
    }
    
    @Override
    public String toString() {
        return employee + " " + action + " " + componentName + " " + changedTo + " " + date; 
    }
    
    
}
