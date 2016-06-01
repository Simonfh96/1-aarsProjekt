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
    private String name;
    //Udskift med den tilhørende case's kons nr
    private int caseKonsNmb;
    private String objectType;
    //private String owner;
    private int konsNmb;
    private ArrayList<Task> tasks;

    public Article(String name, int caseKonsNmb, String objectType, /*owner*/ int konsNmb, ArrayList<Task> tasks) {
        this.name = name;
        this.caseKonsNmb = caseKonsNmb;
        this.objectType = objectType;
        /*this.owner = owner;*/
        this.konsNmb = konsNmb;
        this.tasks = tasks;
    }
    
    @Override
    public String toString() {
        return name + " -" + "  KonsNr: " + konsNmb;
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
//
//    public String getOwner() {
//        return owner;
//    }
//
//    public void setOwner(String owner) {
//        this.owner = owner;
//    }

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

    
    
    
}
