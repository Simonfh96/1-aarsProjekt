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
 * @author Tanja
 */
public class Costumer implements PanelInterface {
    private String costumerName;
    private String mAcro;
    private int mNumb;
    private int phone;
    private String email;
    private String address;
    private String cityOfZip;
    private ArrayList<Contact> contacts;
    private int costumerID;
    //Hvad bruges customers til?
    private ArrayList<Costumer> customers;

    public Costumer(String costumerName, String mAcro, int mNumb, int phone, String email, String address, String cityOfZip, ArrayList<Contact> contacts, int costumerID) {
        this.costumerName = costumerName;
        this.mAcro = mAcro;
        this.mNumb = mNumb;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.cityOfZip = cityOfZip;
        this.contacts = contacts;
        this.costumerID = costumerID;
    }
    
    @Override
    public String toString() {
        return costumerName;
    }

    public ArrayList<Costumer> getCustomers() {
        return customers;
    }
    
    
    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public String getmAcro() {
        return mAcro;
    }

    public void setmAcro(String mAcro) {
        this.mAcro = mAcro;
    }

    public int getmNumb() {
        return mNumb;
    }

    public void setmNumb(int mNumb) {
        this.mNumb = mNumb;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCostumerID() {
        return costumerID;
    }

    public String getAddress() {
        return address;
    }

    public String getCityOfZip() {
        return cityOfZip;
    }
    
    
}
