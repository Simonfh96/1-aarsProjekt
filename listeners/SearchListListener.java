/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JList;
import javax.swing.JTextField;
import model.Costumer;

/**
 *
 * @author pdyst
 */
public class SearchListListener implements KeyListener {
    private JTextField textField;
    private JList list;
    
    public SearchListListener(JTextField textField, JList list) {
        this.textField = textField;
        this.list = list;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Costumer costumer = (Costumer)list.getSelectedValue();
            textField.setText(costumer.getCostumerName());
        }
    }
    
    
}
