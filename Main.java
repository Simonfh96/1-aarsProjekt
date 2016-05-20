
import control.Control;
import model.Employee;
import model.Log;
import view.GUI;
import view.LoginView;

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
        GUI gui = new GUI(control);
        gui.setVisible(true);
        
      
    }
        
}
