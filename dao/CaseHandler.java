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
        statement = "SELECT * FROM cases;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        int i = 0;
        while (rs.next()) {
            //Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), rs.getString("object"), rs.getDate("createdAt"));
            //cases.add(c);
            i++;
        }
        return cases;
    }

    public ArrayList<Case> searchCases(int konsNmb) throws SQLException {
        ArrayList<Case> cases = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM cases WHERE konsNr = '" + konsNmb + "';";

        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        int i = 0;
        while (rs.next()) {
            //Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), rs.getString("object"), rs.getDate("createdAt"));
            //cases.add(c);
            i++;
        }
        return cases;

    }

    //Skal benyttes, når man trykker sig ind på et nyt panel via rediger knappen
    public void editCase(Case c) throws SQLException {
        String stmt1;
        String stmt2;
        String stmt3;
        stmt1 = "begin;";
        //stmt2 = "update cases set caseName = '" + c.getCaseName() + "' where konsNr = " + c.getCaseNmb() + ";";
        stmt3 = "commit;";
       // System.out.println(stmt1 + "\n" + stmt2 + "\n" + stmt3);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt1);
        //DBHandler.getInstance().conn.createStatement().executeUpdate(stmt2);
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt3);
    }

//    public void saveCase(Case c) throws SQLException {
//        String statement;
//        statement = "INSERT INTO cases (konsNr, caseName, object, createdAt)"
//                + " VALUES ( '" + c.getCaseNmb()
//                + "','" + c.getCaseName() + "','" + "Udskiftes med ArrayList af objekter" + "','"
//                + c.getCreatedAt() + "')";
//        DBHandler.getInstance().conn.createStatement().executeUpdate(statement);
//    }

    public static CaseHandler getInstance() {
        return CaseHandlerHolder.INSTANCE;
    }

    private static class CaseHandlerHolder {

        private static final CaseHandler INSTANCE = new CaseHandler();
    }
}
