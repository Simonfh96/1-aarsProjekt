
import control.Control;
import model.Employee;
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
    Employee employee;
    Control control;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Først åbnes et vindue/client, hvor man logger ind,
        //Hvorefter EmployeeHandler henter en employee fra databasen,
        //Som ud fra admin og partime opretter control ud fra en af følgende controls sub-classes:
        //AdminControl, EmployeeControl og PartTimeControl således:
        //Logind skærm åbnes her
//        employee = EmployeeHandler.getInstance().getEmployee(username, password);
//        if (employee.isAdmin()) {
        //control = new AdminControl()
        //}
        //GUI gui = new GUI(control); 
        
        LoginView lw = new LoginView();
        lw.setVisible(true);
    }
        
//    
//    public void login() throws SQLException{
//        
//        employee = EmployeeHandler.getInstance().getEmployee("dasda", "dsa");
//        if (employee.isAdmin()) {
//            control = new AdminControl();
//        }
//        }
//    
}
