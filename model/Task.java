/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pdysted
 */
public class Task {
    private String status;
    private String name;
    private String description;
    private int articleID;

    public Task(String status, String name, String description, int articleID) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.articleID = articleID;
    }
    
    @Override
    public String toString() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }
    
    
    
}
