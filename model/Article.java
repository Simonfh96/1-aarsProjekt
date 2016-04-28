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
    private int ID;
    private String objectType;
    private String owner;
    private int consNumber;

    public Article(String name, int ID, String objectType, String owner, int consNumber) {
        this.name = name;
        this.ID = ID;
        this.objectType = objectType;
        this.owner = owner;
        this.consNumber = consNumber;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

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

    public int getConsNumber() {
        return consNumber;
    }

    public void setConsNumber(int consNumber) {
        this.consNumber = consNumber;
    }
    
    
}
