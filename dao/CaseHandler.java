/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.PanelInterface;
import java.sql.PreparedStatement;
import model.Case;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import model.Article;
import model.Costumer;
import model.Employee;

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
            ArrayList<PanelInterface> articles = ArticleHandler.getInstance().getArticles(rs.getInt("konsNr"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), rs.getString("description"),
                    articles, rs.getBoolean("finished"), rs.getDate("lastUpdated"), rs.getDate("createdAt"), costumer);
            cases.add(c);
        }
        return cases;
    }
    
    //PanelInteface som datatype volder problemer
    public ArrayList<PanelInterface> getCasesNewest() throws SQLException {
        ArrayList<PanelInterface> cases = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM cases ORDER BY lastUpdated DESC LIMIT 10;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Costumer costumer = CostumerHandler.getInstance().getCostumer(rs.getInt("costumer_id"));
            ArrayList<PanelInterface> articles = ArticleHandler.getInstance().getArticles(rs.getInt("konsNr"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), rs.getString("description"),
                    articles, rs.getBoolean("finished"), rs.getDate("lastUpdated"), rs.getDate("createdAt"), costumer);
            cases.add(c);
        }
        return cases;
    }
    
    public ArrayList<PanelInterface> getMyCases(Employee e) throws SQLException {
        ArrayList<PanelInterface> cases = new ArrayList<>();
        int employeeID = e.getEmployeeID();
        PreparedStatement ps = null;
	String selectSQL = "SELECT * FROM myCases LEFT JOIN cases ON myCases.cases_id = cases.case_id WHERE employee_id = ?";
        ps = DBHandler.getInstance().conn.prepareStatement(selectSQL);
        ps.setInt(1, employeeID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Costumer costumer = CostumerHandler.getInstance().getCostumer(rs.getInt("costumer_id"));
            ArrayList<PanelInterface> articles = ArticleHandler.getInstance().getArticles(rs.getInt("konsNr"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), rs.getString("description"),
                    articles, rs.getBoolean("finished"), rs.getDate("lastUpdated"), rs.getDate("createdAt"), costumer);
            cases.add(c);
        }
        
        return cases;
    }

    public ArrayList<PanelInterface> getFinishedCases() throws SQLException {
        ArrayList<PanelInterface> cases = new ArrayList<>();
        String statement;
        //and finished = 0;
        statement = "SELECT * FROM cases WHERE finished = 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Costumer costumer = CostumerHandler.getInstance().getCostumer(rs.getInt("costumer_id"));
            ArrayList<PanelInterface> articles = ArticleHandler.getInstance().getArticles(rs.getInt("konsNr"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), rs.getString("description"),
                    articles, rs.getBoolean("finished"), rs.getDate("lastUpdated"), rs.getDate("createdAt"), costumer);
            cases.add(c);
        }
        return cases;
    }

    public ArrayList<PanelInterface> searchCases(int konsNmb, String caseName) throws SQLException {
        ArrayList<PanelInterface> cases = new ArrayList<>();
//        PreparedStatement ps = null;
//	String selectSQL = "SELECT * FROM cases WHERE konsNr = ? AND caseName LIKE '?%'";
//        ps = DBHandler.getInstance().conn.prepareStatement(selectSQL);
//        ps.setInt(1, konsNmb);
//        ps.setString(2, caseName);
//        ResultSet rs = ps.executeQuery();
        String statement;
        statement = "SELECT * FROM cases WHERE konsNr = "
                + konsNmb + " OR caseName LIKE '" + caseName + "%';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Costumer costumer = CostumerHandler.getInstance().getCostumer(rs.getInt("costumer_id"));
            ArrayList<PanelInterface> articles = ArticleHandler.getInstance().getArticles(rs.getInt("konsNr"));
            Case c = new Case(rs.getInt("konsNr"), rs.getString("caseName"), rs.getString("description"),
                    articles, rs.getBoolean("finished"), rs.getDate("lastUpdated"), rs.getDate("createdAt"), costumer);
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

    /*CustomerHandler og ArticleHandler der kalder en saveCustomer(Costumer customer) 
    og saveArticles(ArrayList<Article> articles) inde i saveCase(Case c). En getter henter customerid fra
    det Costumer objekt, som Case objektet indeholder, da kunden sagtens kan optræde 2 gange og ikke
    må have 2 forskellige customer id'er, hvorimod articles skal gemmes tilsvarende Case objektets primary key
     */
    public void saveCase(Case c, Employee e, boolean existingCostumer) throws SQLException {
        //Husk ArrayListen af articles
        //ArticleHandler måske?
        //Customer id samt customer objekt?
        java.util.Date utilDateConvert = c.getLastUpdated();
        java.sql.Date sqlLastUpdate = new java.sql.Date(utilDateConvert.getTime());
        utilDateConvert = c.getCreatedAt();
        java.sql.Date sqlCreatedAt = new java.sql.Date(utilDateConvert.getTime());
        String saveCase;
        saveCase = "INSERT INTO cases (konsNr, caseName, description, finished, lastUpdated, updateBy, createdAt, costumer_id)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBHandler.getInstance().conn.prepareStatement(saveCase);
        ps.setInt(1, c.getKonsNmb());
        ps.setString(2, c.getCaseName());
        ps.setString(3, c.getDescription());
        ps.setBoolean(4, c.isFinished());
        ps.setDate(5, sqlLastUpdate);
        ps.setString(6, e.getName());
        ps.setDate(7, sqlCreatedAt);
        ps.setInt(8, c.getCustomer().getCostumerID());
        ps.execute();
        CostumerHandler.getInstance().saveCostumer(c.getCustomer(), existingCostumer);
//        ArticleHandler.getInstance().saveArticles();
    }

    public int generateKonsNmb() throws SQLException {
        int konsNmb = 0;
        String statement;
        //Eller det her statement SELECT MAX(konsNr) FROM cases;
        statement = "SELECT konsNr FROM cases ORDER BY konsNr DESC LIMIT 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        if (rs.next()) {
            konsNmb = rs.getInt("konsNr") + 1;
        }
        return konsNmb;
    }

    public void addToMyCases(Employee e, Case c) throws SQLException {
        String statement;
//        statement = "INSERT INTO myCases (konsNr, caseName, description, lastUpdated, createdAt)"
//                + " VALUES ( '" + c.getKonsNmb()
//                + "','" + c.getCaseName() + "','" + c.getDescription() + "','" + c.getLastUpdated() + "','"
//                + c.getCreatedAt() + "')";
//        DBHandler.getInstance().conn.createStatement().executeUpdate(statement);
//        CostumerHandler.getInstance().saveCostumer(c.getCustomer());
            
    }

    /*Metoden må kun benyttes på de sager, som er under fanen mine sager,
    * da det kun bruges til referencer
     */
    public void deleteMyCase(int employeeID, int caseID) throws SQLException {
        String stmt1;
        stmt1 = "DELETE FROM mycases WHERE employee_id = " + employeeID + " AND cases_id = " + caseID + ";";
        DBHandler.getInstance().conn.createStatement().executeUpdate(stmt1);
        //Sikre at den ikke prøver at slette noget, som ikke er der
    }

    public static CaseHandler getInstance() {
        if (instance == null) {
            instance = new CaseHandler();
        }
        return instance;
    }
}
