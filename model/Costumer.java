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
public class Costumer {
    private String costumerName;
    private String mAcro;
    private int mNumb;
    private int phone;
    private String email;

    public Costumer(String costumerName, String mAcro, int mNumb, int phone, String email) {
        this.costumerName = costumerName;
        this.mAcro = mAcro;
        this.mNumb = mNumb;
        this.phone = phone;
        this.email = email;
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
    
    
}
