/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import interfaces.PanelInterface;
import java.util.ArrayList;

/**
 *
 * @author pdysted
 */
public class Article implements PanelInterface {
    private int articleID;
    private String name;
    private int caseKonsNmb;
    private String objectType;
    private String location;
    private int museumsNmb;
    private int konsNmb;
    private ArrayList<Task> tasks;

    public Article(int articleID, String name, int caseKonsNmb, String objectType, String location, int museumsNmb, int konsNmb, ArrayList<Task> tasks) {
        this.articleID = articleID;
        this.name = name;
        this.caseKonsNmb = caseKonsNmb;
        this.objectType = objectType;
        this.location = location;
        this.museumsNmb = museumsNmb;
        this.konsNmb = konsNmb;
        this.tasks = tasks;
    }
    
    @Override
    public String toString() {
        return name;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setCaseKonsNmb(int caseKonsNmb) {
        this.caseKonsNmb = caseKonsNmb;
    }

    public int getKonsNmb() {
        return konsNmb;
    }

    public void setKonsNmb(int konsNmb) {
        this.konsNmb = konsNmb;
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getArticleID() {
        return articleID;
    }

    public int getMuseumsNmb() {
        return museumsNmb;
    }

    public void setMuseumsNmb(int museumsNmb) {
        this.museumsNmb = museumsNmb;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
    
    
}
