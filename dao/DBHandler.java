/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Simon
 */
public class DBHandler {

    private static DBHandler instance;
    private String url;
    private String user;
    private String pswrd;
    private String schema = "/BevaringSjaelland";
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    Properties prop = new Properties();
    OutputStream output = null;
    InputStream input = null;
    Connection conn;

    private DBHandler() {
        try {
            loadConfig();
            connect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setConfig(String url, String user, String password) {
        try {
            output = new FileOutputStream("config.properties");
            //jdbc:mysql://localhost:3306
            //root
            //root
            prop.setProperty("database", url);
            prop.setProperty("dbuser", user);
            prop.setProperty("dbpassword", password);

            // save properties to project root folder
            //Den gemmer den i root mappen, men loader fra /build/classes
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void loadConfig() {
        try {

            String filename = "config.properties";
            input = DBHandler.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            url = prop.getProperty("database");
            pswrd = prop.getProperty("dbpassword");
            user = prop.getProperty("dbuser");
            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(url + schema, user, pswrd);
    }

    public Connection getConn() {
        return conn;
    }

    public static DBHandler getInstance() {
        if (instance == null) {
            instance = new DBHandler();
        }
        return instance;
    }

    public void closeConnection() {
        try {
            DBHandler.getInstance().conn.commit();
            DBHandler.getInstance().conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
