/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import interfaces.PanelInterface;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Simon
 */
public class Case implements PanelInterface{
    private int konsNmb;
    private String caseName;
    private String description;
    private ArrayList<PanelInterface> articles;
    private boolean finished;
    private Date lastUpdated;
    private Date createdAt;
    private Costumer customer;

    //Sagsnr skal tilføjes, så det kan søges på (Case tables PK)
    public Case(int konsNmb, String caseName, String description, ArrayList<PanelInterface> articles, boolean finished, Date lastUpdated, Date createdAt, Costumer customer) {
        this.konsNmb = konsNmb;
        this.caseName = caseName;
        this.description = description;
        this.articles = articles;
        this.finished = finished;
        this.lastUpdated = lastUpdated;
        this.createdAt = createdAt;
        this.customer = customer;
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

    public ArrayList<PanelInterface> getArticles() {
        return articles;
    }

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
