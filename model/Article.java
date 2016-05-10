/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pdyst
 */
public class Article {
    private String name;
    private int caseID;
    private String objectType;
    private String owner;
    private int konsNmb;

    public Article(String name, int caseID, String objectType, /*String owner,*/ int konsNmb) {
        this.name = name;
        this.caseID = caseID;
        this.objectType = objectType;
        //this.owner = owner;
        this.konsNmb = konsNmb;
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

//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getKonsNmb() {
        return konsNmb;
    }

    public void setKonsNmb(int konsNmb) {
        this.konsNmb = konsNmb;
    }

    
    
    
}
