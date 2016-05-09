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
    private String description;
    private boolean finished;
    //private ArrayList<Article> articles; underret David angående, hvilken rækkefølge de skal oprettes i,
    //om case skal laves først og dertil have articles sat med en setter, eller om de skal oprettes først,
    // og kaldes som en parameter i constructor'en
    private Date lastUpdated;
    private Date createdAt;
    private Costumer customer;

    public Case(int konsNmb, String caseName, String description, /*boolean finished,*//*ArrayList<Article> articles,*/ Date lastUpdated, Date createdAt, Costumer customer) {
        this.konsNmb = konsNmb;
        this.caseName = caseName;
        this.description = description;
        //this.articles = articles;
        this.lastUpdated = lastUpdated;
        this.createdAt = createdAt;
        this.customer = customer;
//        this.finished = finished;
    }

    public int getKonsNmb() {
        return konsNmb;
    }

    public String getCaseName() {
        return caseName;
    }

    public String getDescription() {
        return description;
    }
    
    public boolean isFinished() {
        return finished;
    }

//    public ArrayList<Article> getArticles() {
//        return articles;
//    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Costumer getCustomer() {
        return customer;
    }

    
    
    
    
}
