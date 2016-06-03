/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author pdyst
 */
public class DescriptionBox extends javax.swing.JPanel {
    private JPanel panel;
    private JTextField field;
    
    /**
     * Creates new form DescriptionBox
     * @param panel the JPanel from which the box will be removed, once the description is saved
     * @param field the chosen field, which will be filled with the description when saved
     */
    public DescriptionBox(JPanel panel, JTextField field) {
        initComponents();
        this.setVisible(true);
        this.panel = panel;
        this.field = field;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        descriptionArea.setText(field.getText());
        this.setBounds(100, 100, 600, 480);
        setComponentsVisible(false);
        panel.add(this);
    }
    
    public void setComponentsVisible(boolean visible) {
        for (Component comp : panel.getComponents()) {
            comp.setVisible(visible);
            comp.setEnabled(visible);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        saveDescriptionButton = new javax.swing.JButton();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        descriptionArea.setColumns(20);
        descriptionArea.setRows(5);
        jScrollPane2.setViewportView(descriptionArea);

        saveDescriptionButton.setText("Gem");
        saveDescriptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDescriptionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(saveDescriptionButton)
                .addContainerGap(283, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveDescriptionButton)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveDescriptionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDescriptionButtonActionPerformed
        String description = descriptionArea.getText();
        field.setText(description);
        setComponentsVisible(true);
        this.setVisible(false);
        panel.remove(this);
        panel.repaint();
        panel.revalidate();
    }//GEN-LAST:event_saveDescriptionButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton saveDescriptionButton;
    // End of variables declaration//GEN-END:variables
}
