/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author pdysted
 */
public class Log {
    private final String employee;
    private final String action;
    private final Date date;
    private final String component;
    private final String changedTo;

    public Log(Employee employee, String action, Date date, String component, String changedTo) {
        this.employee = employee.getName();
        this.action = action;
        this.date = date;
        this.component = component;
        this.changedTo = changedTo;
    }
    
    
}
