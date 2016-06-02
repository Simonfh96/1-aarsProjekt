/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.PanelInterface;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Article;
import model.Case;
import model.Costumer;
import model.Employee;
import view.ArticlePanel;
import view.CasePanel;
import view.CustomerPanel;
import view.EmployeePanel;
import view.GUI;
//import view.GUI;

/**
 *
 * @author Pdysted
 */
public class PanelFactory {

    PanelInterface panel;
// Skal erstattes med enten et interface som datatype til at returnere de ønskede panels
// Ellers skal en super class bruges, som alle panels extender som subclasses

    private PanelFactory() {
    }

    public static PanelFactory getInstance() {
        return PanelFactoryHolder.INSTANCE;
    }

    private static class PanelFactoryHolder {

        private static final PanelFactory INSTANCE = new PanelFactory();
    }

    //den skal oprette en af de tre typer panels alt efter værdien i switchen
    //
    public void createPanels(ArrayList<PanelInterface> panels, JPanel displayPanel, GUI gui, String type) {
        System.out.println("switch");
        switch (type) {
            case "CasePanel":
                for (int i = 0; i < panels.size(); i++) {
                    CasePanel cp = new CasePanel((Case) panels.get(i), gui);
                    cp.setBounds(0, 52 * i, displayPanel.getWidth() - 3, 50);
                    cp.setBorder(BorderFactory.createLineBorder(Color.black));
                    displayPanel.add(cp);
                    displayPanel.repaint();
                    displayPanel.revalidate();
                }

                break;

            case "ArticlePanel":
                for (int i = 0; i < panels.size(); i++) {
                    ArticlePanel ap = new ArticlePanel((Article) panels.get(i));
                    ap.setBounds(0, 52 * i, displayPanel.getWidth(), 50);
                    ap.setBorder(BorderFactory.createLineBorder(Color.black));
                    displayPanel.add(ap);
                    displayPanel.repaint();
                    displayPanel.revalidate();
                }

                break;
                
            case "CostumerPanel":
                for (int i = 0; i < panels.size(); i++) {
                    CustomerPanel cp = new CustomerPanel((Costumer) panels.get(i));
                    cp.setBounds(0, 31 * i, displayPanel.getWidth(), 29);
                    cp.setBorder(BorderFactory.createLineBorder(Color.black));
                    displayPanel.add(cp);
                    displayPanel.repaint();
                    displayPanel.revalidate();
                }
                break;
            case "EmployeePanel":
                for (int i = 0; i < panels.size(); i++) {
                    EmployeePanel ep = new EmployeePanel((Employee)panels.get(i));
                    ep.setBounds(0, 38 * i, displayPanel.getWidth(), 36);
                    ep.setBorder(BorderFactory.createLineBorder(Color.black));
                    displayPanel.add(ep);
                    displayPanel.repaint();
                    displayPanel.revalidate();
                }
        }

    }
}
