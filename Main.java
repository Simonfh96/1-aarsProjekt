
import control.Control;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pdyst
 */
public class Main {
    private static Control control = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI gui = null;
        try {
            gui = new GUI(control);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        gui.setVisible(true);
        
      
    }
        
}
