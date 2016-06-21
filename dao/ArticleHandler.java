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
import model.Article;
import model.Case;
import model.Task;

/**
 *
 * @author pdyst
 */
public class ArticleHandler {
    private static ArticleHandler instance;
    
    private ArticleHandler() {
        
    }
    
      public ArrayList<PanelInterface> getArticles(Case c) throws SQLException {
        ArrayList<PanelInterface> articles = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM objects WHERE caseKonsNmb = '" + c.getKonsNmb() + "';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            ArrayList<Task> tasks = TaskHandler.getInstance().getTasks(rs.getInt("objects_id"));
            Article article = new Article(rs.getInt("objects_id"), rs.getString("objectName"), c.getKonsNmb(), rs.getString("objectType"), 
                    rs.getString("location"), rs.getInt("museumsNmb"), rs.getInt("konsNr"), tasks);
            articles.add(article);
        }
        rs.close();
        return articles;
    }
      
    public void saveArticle(Article a, Case c) throws SQLException {
             String saveArticles;
             saveArticles = "INSERT INTO objects (caseKonsNmb, objectName, objectType, location, museumsNmb, konsNr)"
                + " values (?, ?, ?, ?, ?, ?)";
             PreparedStatement ps = DBHandler.getInstance().conn.prepareStatement(saveArticles);
             ps.setInt(1, c.getKonsNmb());
             ps.setString(2, a.getName());
             ps.setString(3, a.getObjectType());
             ps.setString(4, a.getLocation());
             ps.setInt(5, a.getMuseumsNmb());
             ps.setInt(6, a.getKonsNmb());
             ps.execute(); 
             if (a.getTasks().size() > 0) {
                 for (Task t : a.getTasks()) {
                 TaskHandler.getInstance().saveTask(t);
                 }
             }
             
     }
    
    public int generateArticleID() throws SQLException {
        int articleID = 0;
        String statement = "SELECT objects_id FROM objects ORDER BY objects_id DESC LIMIT 1;";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        if (rs.next()) {
            articleID = rs.getInt("objects_id") + 1;
        }
        rs.close();
        return articleID;
    }
    
    public static ArticleHandler getInstance() {
        if (instance == null) {
            instance = new ArticleHandler();
        }
        return instance;
    }
}
