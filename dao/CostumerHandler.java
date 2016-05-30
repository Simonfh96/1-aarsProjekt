    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.PanelInterface;
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
            ArrayList<Contact> contacts = ContactHandler.getInstance().getContacts(rs.getInt("costumer_id"));
            Costumer costumer = new Costumer(rs.getString("costumerName"), rs.getString("acronym"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"),
                    rs.getString("address"), rs.getString("zipCode") + ", " + rs.getString("cityName"), contacts, rs.getInt("costumer_id"));
            costumers.add(costumer);
        }

        return costumers;
    }

    public Costumer getCostumer(int costumerID) throws SQLException {
        Costumer costumer = null;
        String statement;
        statement = "SELECT * FROM costumer LEFT JOIN zipCodes ON costumer.zipCode = zipCodes.zipCode WHERE costumer_id = '" + costumerID + "';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            ArrayList<Contact> contacts = ContactHandler.getInstance().getContacts(costumerID);
            costumer = new Costumer(rs.getString("costumerName"), rs.getString("acronym"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"),
                    rs.getString("address"), rs.getString("zipCode") + ", " + rs.getString("cityName"), contacts, rs.getInt("costumer_id"));
        }
        return costumer;
    }
    
    public ArrayList<PanelInterface> selectAllCostumer() throws SQLException {
        ArrayList<PanelInterface> costumers = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM costumer LEFT JOIN zipCodes ON costumer.zipCode = zipCodes.zipCode";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            ArrayList<Contact> contacts = ContactHandler.getInstance().getContacts(rs.getInt("costumer_id"));
            Costumer costumer = new Costumer(rs.getString("costumerName"), rs.getString("acronym"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"),
                    rs.getString("address"), rs.getString("zipCode") + ", " + rs.getString("cityName"), contacts, rs.getInt("costumer_id"));
            costumers.add(costumer);

        }
        return costumers;
    }

    public void saveCostumer(Costumer costumer) throws SQLException {
        String statement;
        statement = "INSERT INTO customer (costumerName,  museumAcro, museumNmb, phone, email)"
                + " VALUES ( '" + costumer.getCostumerName()
                + "','" + costumer.getmAcro() + "','" + costumer.getmNumb() + "','"
                + costumer.getPhone() + "','" + costumer.getEmail() + "')";
        DBHandler.getInstance().conn.createStatement().executeUpdate(statement);
    }

    public static CostumerHandler getInstance() {
        if (instance == null) {
            instance = new CostumerHandler();
        }
        return instance;
    }

}
