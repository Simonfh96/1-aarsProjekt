/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Costumer;

/**
 *
 * @author pdyst
 */
public class CostumerHandler {
    private static CostumerHandler instance;
    
    private CostumerHandler() {
    }

    public Costumer getCostumer(int costumerID) throws SQLException {
        Costumer costumer = null;   
        String statement;
        statement = "SELECT * FROM costumer WHERE costumer_id = '" + costumerID + "';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            costumer = new Costumer(rs.getString("costumerName"), rs.getString("museumAcro"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"), rs.getInt("costumer_id"));
        }
        return costumer;
    } 
    
    public static CostumerHandler getInstance() {
        if (instance == null) {
            instance = new CostumerHandler();
        }
        return instance;
    }

}