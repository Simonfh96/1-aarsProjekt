/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author pdysted
 */
public class Log {
    //Log klassen skal indeholde en integer log id, som skal bruges til at referere til
    //ArrayListen med logs, når en case gemmes i databasen
    private Employee employee;
    private int caseID;
    private final String actionMade;
    private final String componentName;
    private final String changedFrom;
    private final String changedTo;
    private final Date date;

    public Log(Employee employee, int caseID, String actionMade, String componentName, String changedFrom, String changedTo, Date date) {
        this.employee = employee;
        this.caseID = caseID;
        this.actionMade = actionMade;
        this.componentName = componentName;
        this.changedFrom = changedFrom;
        this.changedTo = changedTo;
        this.date = date;
    }
    
    @Override
    public String toString() {
        return  employee.getFullName() + " " + actionMade + " " + componentName 
                + " " + changedFrom + " " + changedTo + " " + date + "\n"; 
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public int getCaseID() {
        return caseID;
    }
    
    
    
}
