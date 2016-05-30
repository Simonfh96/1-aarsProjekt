/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Contact;

/**
 *
 * @author Tanja
 */
public class ContactHandler {
    private static ContactHandler instance;

    private ContactHandler() {

    }
    
     public ArrayList<Contact> getContacts(int costumerID) throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
        PreparedStatement ps = null;
        String getContacts = "SELECT * FROM contacts WHERE customer_id = ?";
        ps = DBHandler.getInstance().getConn().prepareStatement(getContacts);
        ps.setInt(1, costumerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Contact contact = new Contact(rs.getString("contactName"), rs.getInt("phone"), rs.getString("eamil"));
            contacts.add(contact);
        }
        return contacts;
    }
    
    public static ContactHandler getInstance() {
        if (instance == null) {
            instance = new ContactHandler();
        }
        return instance;
    }
}
