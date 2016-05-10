package view;

import dao.CaseHandler;
import java.awt.CardLayout;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Article;
import model.Case;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pdysted
 */
public class CasePanel extends javax.swing.JPanel {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private GUI gui;
    private Calendar cal;
    private CardLayout cl;
    private Case c;
    
    /**
     * Creates new form CasePanel
     *
     * 
     * @param c the case object which's getter methods is used to set the labels with info
     * @param gui a reference to the gui, which allows the Panel to navigate through the cardlayout
     */
    public CasePanel(Case c, GUI gui) {
        initComponents();
        cal = Calendar.getInstance();
        this.c = c;
        caseNameLabel.setText(c.getCaseName());
        konsNrLabel.setText("" + c.getKonsNmb());
        if (c.getArticles().size() > 0) {
        objectLabel.setText(c.getArticles().get(0).getName());
        }
        createdAtLabel.setText("Oprettet: "+dateFormat.format(c.getCreatedAt()));
        lastUpdateLabel.setText("Sidst opdateret: "+dateFormat.format(c.getLastUpdated()));
        ownerLabel.setText(c.getCustomer().getCostumerName());
        cl = gui.getCl();
        this.gui = gui;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        caseNameLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        konsNrLabel = new javax.swing.JLabel();
        objectLabel = new javax.swing.JLabel();
        createdAtLabel = new javax.swing.JLabel();
        casePanelEditButton = new javax.swing.JButton();
        ownerLabel = new javax.swing.JLabel();
        lastUpdateLabel = new javax.swing.JLabel();

        caseNameLabel.setText("Case name:");

        jLabel1.setText("Kons nr:");

        konsNrLabel.setText("Nr kons");

        objectLabel.setText("Object: ");

        createdAtLabel.setText("CreatedAt");

        casePanelEditButton.setText("Rediger");
        casePanelEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                casePanelEditButtonActionPerformed(evt);
            }
        });

        ownerLabel.setText("Owner:");

        lastUpdateLabel.setText("LastUpdate");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(konsNrLabel))
                    .addComponent(caseNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(objectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ownerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lastUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(createdAtLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addComponent(casePanelEditButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(caseNameLabel)
                            .addComponent(ownerLabel)
                            .addComponent(createdAtLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(konsNrLabel)
                            .addComponent(lastUpdateLabel)
                            .addComponent(objectLabel)))
                    .addComponent(casePanelEditButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void casePanelEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_casePanelEditButtonActionPerformed
        for (Article a : c.getArticles()) {
            System.out.println(a.toString());
        }
        cl.next(gui.getCardPanel());
        gui.setC(c);
        gui.editCaseSetup();
    }//GEN-LAST:event_casePanelEditButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel caseNameLabel;
    private javax.swing.JButton casePanelEditButton;
    private javax.swing.JLabel createdAtLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel konsNrLabel;
    private javax.swing.JLabel lastUpdateLabel;
    private javax.swing.JLabel objectLabel;
    private javax.swing.JLabel ownerLabel;
    // End of variables declaration//GEN-END:variables
}
