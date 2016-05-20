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
    private final Employee employee;
    private final String actionMade;
    private final String componentName;
    private final String changedFrom;
    private final String changedTo;
    private final Date date;

    public Log(Employee employee, String actionMade, String componentName, String changedFrom, String changedTo, Date date) {
        this.employee = employee;
        this.actionMade = actionMade;
        this.componentName = componentName;
        this.changedFrom = changedFrom;
        this.changedTo = changedTo;
        this.date = date;
    }
    
    @Override
    public String toString() {
        return  employee.getName() + " " + actionMade + " " + componentName + " " + changedFrom + " " + changedTo + " " + date; 
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getActionMade() {
        return actionMade;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getChangedFrom() {
        return changedFrom;
    }

    public String getChangedTo() {
        return changedTo;
    }

    public Date getDate() {
        return date;
    }
    
    
}
