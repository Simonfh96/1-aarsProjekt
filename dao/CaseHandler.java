/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Case;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import model.Costumer;

/**
 *
 * @author pdyst
 */
public class CaseHandler {
    private static CaseHandler instance;
    Calendar cal;
    DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
    private CaseHandler() {
        cal = Calendar.getInstance();
    }

    public ArrayList<Case> getCases() throws SQLException {
        ArrayList<Case> cases = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM cases;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Costumer costumer = CostumerHandler.getInstance().getCostumer(rs.getInt("costumer_id"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), /*rs.getString("object"),*/
                    rs.getDate("lastUpdated"), rs.getDate("createdAt"), costumer);
            cases.add(c);
        }
        return cases;
    }
    
    public ArrayList<Case> searchCases(int konsNmb, String caseName) throws SQLException {
        ArrayList<Case> cases = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM cases WHERE konsNr = '"
                + konsNmb + "' OR caseName LIKE '" + caseName + "%';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Costumer costumer = CostumerHandler.getInstance().getCostumer(rs.getInt("costumer_id"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), /*rs.getString("object"),*/
                    rs.getDate("lastUpdated"), rs.getDate("createdAt"), costumer);
            cases.add(c);
        }
        return cases;

    }

    //Skal benyttes, når man trykker sig ind på et nyt panel via rediger knappen
    public void editCase(Case c) throws SQLException {
        String stmt1;
        String stmt2;
        String stmt3;
        stmt1 = "begin;";
        stmt2 = "update cases set caseName = '" + c.getCaseName() + "', lastUpdated = '" 
                + dateFormat.format(cal.getTime()) + "' where konsNr = " + c.getKonsNmb() + ";";
        stmt3 = "commit;";
        System.out.println(stmt1 + "\n" + stmt2 + "\n" + stmt3);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt1);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt2);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt3);
    }

    /*CustomerHandler og ArticlerHandler der kalder en saveCustomer(Costumer customer) 
    og saveArticles(ArrayList<Article> articles) inde i saveCase(Case c). En getter henter customerid fra
    det Costumer objekt, som Case objektet indeholder, da kunden sagtens kan optræde 2 gange og ikke
    må have 2 forskellige customer id'er, hvorimod articles skal gemmes tilsvarende Case objektets primary key
    */
    public void saveCase(Case c) throws SQLException {
        String statement;
        //Husk ArrayListen af articles
        //ArticleHandler måske?
        //Customer id samt customer objekt?
        statement = "INSERT INTO cases (konsNr, caseName, lastUpdated, createdAt)"
                + " VALUES ( '" + c.getKonsNmb()
                + "','" + c.getCaseName() + "','" + c.getLastUpdated() + "','"
                + c.getCreatedAt() + "')";
        DBHandler.getInstance().conn.createStatement().executeUpdate(statement);
        CostumerHandler.getInstance().saveCostumer(c.getCustomer());
    }
    
    public int generateKonsNmb() throws SQLException {
        int konsNmb = 0;
        String statement;
        statement = "SELECT konsNr FROM cases ORDER BY konsNr DESC LIMIT 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        if (rs.next()) {
            konsNmb = rs.getInt("konsNr") + 1;
        }
        return konsNmb;
    }

    public static CaseHandler getInstance() {
        if (instance == null) {
            instance = new CaseHandler();
        }
        return instance;
    }
}
