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
    
    private CostumerHandler() {
    }
    
    public Costumer getCostumer() throws SQLException {
        Costumer costumer = null;
        String statement;
        statement = "SELECT * FROM cases LEFT JOIN costumer on cases.costumer_id = costumer.costumer_id;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            costumer = new Costumer(rs.getString("costumerName"), rs.getString("museumAcro"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"));
        }
        return costumer;
    }
    
    public static CostumerHandler getInstance() {
        return CostumerHandlerHolder.INSTANCE;
    }
    
    private static class CostumerHandlerHolder {

        private static final CostumerHandler INSTANCE = new CostumerHandler();
    }
}
