/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.util.ArrayList;
import model.Log;

/**
 *
 * @author pdysted
 */
public class LogHandler {
    private static LogHandler instance;
    
    private LogHandler() {
        
    }
    
    public void writeLogToFile(ArrayList<Log> logs) throws IOException {
        
    }
    
    public void saveLog(Log log) {
        
    }
    
    public static LogHandler getInstance() {
        if (instance == null) {
            instance = new LogHandler();
        }
        return instance;
    }
}
