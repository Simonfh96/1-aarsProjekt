/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tanja
 */
public class Contact {
    private String conName;
    private int conPhone;
    private String conEmail;

    public Contact(String conName, int conPhone, String conEmail) {
        this.conName = conName;
        this.conPhone = conPhone;
        this.conEmail = conEmail;
    }
    
    @Override
    public String toString() {
        return conName;
    }

    public String getConName() {
        return conName;
    }

    public int getConPhone() {
        return conPhone;
    }

    public String getConEmail() {
        return conEmail;
    }
    
    
}
