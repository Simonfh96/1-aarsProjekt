
import control.Control;
import view.GUIView;

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
        
        GUIView gui = null;
        gui = new GUIView(control);
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
            java.util.logging.Logger.getLogger(GUIView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

}
