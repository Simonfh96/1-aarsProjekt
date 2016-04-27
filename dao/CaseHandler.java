/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Case;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Customer;

/**
 *
 * @author pdyst
 */
public class CaseHandler {

    private CaseHandler() {

    }

    public ArrayList<Case> getCases() throws SQLException {
        ArrayList<Case> cases = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM cases LEFT JOIN costumer on cases.costumer_id = costumer.costumer_id;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Customer customer = new Customer(rs.getString("costumerName"), rs.getString("museumAcro"),
                    rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), /*rs.getString("object"),*/
                    rs.getDate("lastUpdated"), rs.getDate("createdAt"), customer);
            cases.add(c);
        }
        return cases;
    }
    
    /*Lav en overordnet metode, der kalder individuelle søge metoder efter hinanden, som hver returnerer en
    ArrayList, som bliver tilføjet til den overordende ArrayList searchResults, hvis en af felterne for
    Søgekritierne er tomme, vil metoden tjekke for det, og dermed ikke blive kaldt
    Fx: if (!(konsNmb == null)) {
    doSearch(konsNmb);
    }
    */
    public ArrayList<Case> searchCases(int konsNmb) throws SQLException {
        ArrayList<Case> cases = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM cases LEFT JOIN costumer on cases.costumer_id = costumer.costumer_id WHERE konsNr = '" + konsNmb + "';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Customer customer = new Customer(rs.getString("costumerName"), rs.getString("museumAcro"),
            rs.getInt("museumNmb"), rs.getInt("phone"), rs.getString("email"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), /*rs.getString("object"),*/
                    rs.getDate("lastUpdated"), rs.getDate("createdAt"), customer);
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
        stmt2 = "update cases set caseName = '" + c.getCaseName() + "' where konsNr = " + c.getKonsNmb() + ";";
        stmt3 = "commit;";
        System.out.println(stmt1 + "\n" + stmt2 + "\n" + stmt3);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt1);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt2);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt3);
    }

    /*CustomerHandler og ArticlerHandler der kalder en saveCustomer(Customer customer) 
    og saveArticles(ArrayList<Article> articles) inde i saveCase(Case c). En getter henter customerid fra
    det Customer objekt, som Case objektet indeholder, da kunden sagtens kan optræde 2 gange og ikke
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
        statement = "INSERT INTO customer (costumerName,  museumAcro, museumNmb, phone, email)"
                + " VALUES ( '" + c.getCustomer().getCostumerName()
                + "','" + c.getCustomer().getmAcro() + "','" + c.getCustomer().getmNumb() + "','"
                + c.getCustomer().getPhone() + "','" + c.getCustomer().getEmail() + "')";
        DBHandler.getInstance().conn.createStatement().executeUpdate(statement);
    }

    public static CaseHandler getInstance() {
        return CaseHandlerHolder.INSTANCE;
    }

    private static class CaseHandlerHolder {

        private static final CaseHandler INSTANCE = new CaseHandler();
    }
}
