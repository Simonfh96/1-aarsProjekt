
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
        setTheme();
        
        GUI gui = null;
        gui = new GUI(control);
        gui.setVisible(true);
    }
    
    public static void setTheme() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

}
