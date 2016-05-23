/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Article;

/**
 *
 * @author pdyst
 */
public class ArticleHandler {
    private static ArticleHandler instance;
    
    private ArticleHandler() {
        
    }
    
      public ArrayList<Article> getArticles(int caseKonsNmb) throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        String statement;
        statement = "SELECT * FROM objects WHERE caseKonsNmb = '" + caseKonsNmb + "';";
        ResultSet rs = DBHandler.getInstance().conn.createStatement().executeQuery(statement);
        while (rs.next()) {
            Article article = new Article(rs.getString("objectName"), caseKonsNmb, rs.getString("objectType"), rs.getInt("konsNr"));
            articles.add(article);
        }
        return articles;
    }
    
    public static ArticleHandler getInstance() {
        if (instance == null) {
            instance = new ArticleHandler();
        }
        return instance;
    }
}
