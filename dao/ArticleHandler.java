/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author pdyst
 */
public class ArticleHandler {
    private static ArticleHandler instance;
    
    private ArticleHandler() {
        
    }
    
    public static ArticleHandler getInstance() {
        if (instance == null) {
            instance = new ArticleHandler();
        }
        return instance;
    }
}
