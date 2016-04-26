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
 * @author Simon
 */
public class Case {
    private int konsNmb;
    private String caseName;
    private ArrayList<String> objects;
    private Date lastUpdated;
    private Date createdAt;
    private Customer customer;

    public Case(int konsNmb, String caseName, ArrayList<String> objects, Date lastUpdated, Date createdAt, Customer customer) {
        this.konsNmb = konsNmb;
        this.caseName = caseName;
        this.objects = objects;
        this.lastUpdated = lastUpdated;
        this.createdAt = createdAt;
        this.customer = customer;
    }
    
    
}
