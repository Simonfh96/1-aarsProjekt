    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.PanelInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Contact;
import model.Costumer;

/**
 *
 * @author pdyst
 */
public class CostumerHandler {

    private static CostumerHandler instance;

    private CostumerHandler() {
    }

    public ArrayList<Costumer> searchCostumerName(String name) throws SQLException {
        ArrayList<Costumer> costumers = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM costumer LEFT JOIN zipCodes ON costumer.zipCode = zipCodes.zipCode WHERE costumerName LIKE '" + name + "%';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Costumer costumer = new Costumer(rs.getInt("costumer_id"), rs.getString("costumerName"), rs.getString("acronym"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"),
                    rs.getString("address"), rs.getString("zipCode") + ", " + rs.getString("cityName"), null);
            costumers.add(costumer);
        }
        for (Costumer costumer : costumers) {
            ArrayList<Contact> contacts = ContactHandler.getInstance().getContacts(costumer);
            costumer.setContacts(contacts);
        }
        rs.close();
        return costumers;
    }

    public Costumer getCostumer(int costumerID) throws SQLException {
        Costumer costumer = null;
        String statement;
        statement = "SELECT * FROM costumer LEFT JOIN zipCodes ON costumer.zipCode = zipCodes.zipCode WHERE costumer_id = '" + costumerID + "';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            costumer = new Costumer(rs.getInt("costumer_id"), rs.getString("costumerName"), rs.getString("acronym"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"),
                    rs.getString("address"), rs.getString("zipCode") + ", " + rs.getString("cityName"), null);
        }
        ArrayList<Contact> contacts = ContactHandler.getInstance().getContacts(costumer);
        costumer.setContacts(contacts);
        rs.close();
        return costumer;
    }
    
    public ArrayList<PanelInterface> selectAllCostumer() throws SQLException {
        ArrayList<PanelInterface> costumers = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM costumer LEFT JOIN zipCodes ON costumer.zipCode = zipCodes.zipCode";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            System.out.println("213");
            Costumer costumer = new Costumer(rs.getInt("costumer_id"), rs.getString("costumerName"), rs.getString("acronym"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"),
                    rs.getString("address"), rs.getString("zipCode") + ", " + rs.getString("cityName"), null);
            System.out.println(costumer.toString());
            costumers.add(costumer);

        }
        for (PanelInterface c : costumers) {
            Costumer costumer = (Costumer) c;
            System.out.println(costumer.toString());
            ArrayList<Contact> contacts = ContactHandler.getInstance().getContacts(costumer);
            costumer.setContacts(contacts);
        }
        rs.close();
        return costumers;
    }

    public void saveCostumer(Costumer c, boolean existingCostumer) throws SQLException {
        String saveCostumer;
        saveCostumer = "INSERT INTO costumer (costumer_id, costumerName, acronym, museumNmb, phone, email, address, zipCode, contact_id)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBHandler.getInstance().conn.prepareStatement(saveCostumer);
        ps.setInt(1, c.getCostumerID());
        ps.setString(2, c.getCostumerName());
        ps.setString(3, c.getmAcro());
        ps.setInt(4, c.getmNumb());
        ps.setInt(5, c.getPhone());
        ps.setString(6, c.getEmail());
        ps.setString(7, c.getAddress());
        ps.setInt(8, 1); //Metode der tjekker efter zip code og returner id'et dertil
        ps.setInt(9, c.getCostumerID());
        ps.execute();
        if (c.getContacts().size() > 0) {
            ContactHandler.getInstance().saveContacts(c);
        }
        
    }
    
    public int generateCostumerID() throws SQLException {
        int costumerID = 0;
        String statement;
        statement = "SELECT costumer_id FROM costumer ORDER BY costumer_id DESC LIMIT 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        if (rs.next()) {
            costumerID = rs.getInt("costumer_id") + 1;
        }
        rs.close();
        return costumerID;
    }

    public static CostumerHandler getInstance() {
        if (instance == null) {
            instance = new CostumerHandler();
        }
        return instance;
    }

}
