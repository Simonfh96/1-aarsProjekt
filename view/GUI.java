/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.Control;
import dao.CaseHandler;
import dao.CostumerHandler;
import dao.DBHandler;
import dao.EmployeeHandler;
import factories.PanelFactory;
import interfaces.PanelInterface;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import listeners.SearchListListener;
import model.Article;
import model.Case;
import model.Contact;
import model.Costumer;
import model.Employee;
import model.Task;

/**
 *
 * @author Simon
 */
public class GUI extends javax.swing.JFrame {
    
    private ArrayList<PanelInterface> newCaseArticles = new ArrayList<>();
    private ArrayList<PanelInterface> cases;
    private ArrayList<CasePanel> casePanels;
    private Case c;
    private Calendar cal;
    private CardLayout cl;
    private static Control control;
    private Costumer costSearchSelected;
    private DefaultListModel listModel;
    private DefaultListModel listModelObjects;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
    private Employee employee;
    private JList list;
    private boolean loggedIn = false;
    private LoginView lw;

    /**
     * Creates new form GUI
     *
     * @param control
     */
    public GUI(Control control) throws SQLException {
        initComponents();
        lw = new LoginView(this);
        lw.setAlwaysOnTop(true);
        this.setEnabled(false);
        lw.setVisible(true);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.control = control;
        employee = null;
        System.out.println(articleDisplayPanel.getWidth());
        while (!loggedIn) {
            System.out.println("not");
        }
        System.out.println("now");
        int adminTab = tabbedPane.indexOfTab("Admin");
        tabbedPane.setEnabledAt(adminTab, false);
        if (employee.isAdmin()) {
            tabbedPane.setEnabledAt(adminTab, true);
        }
        listModel = new DefaultListModel();
        listModelObjects = new DefaultListModel();
        list = new JList(listModel);
        list.setSize(206, 163);
        list.setVisible(false);
        costScrollSearch.add(list);
        costScrollSearch.setVisible(true);
        cal = Calendar.getInstance();
        cl = (CardLayout) cardPanel.getLayout();
        try {
            cases = CaseHandler.getInstance().getCasesNewest();
            PanelFactory.getInstance().createPanels(cases, newestCasesPanel, this, "CasePanel");
            PanelFactory.getInstance().createPanels(CaseHandler.getInstance().getMyCases(employee), myCasesPanel, this, "CasePanel");
            PanelFactory.getInstance().createPanels(CaseHandler.getInstance().getFinishedCases(), finishedCasesPanel, this, "CasePanel");
            PanelFactory.getInstance().createPanels(CostumerHandler.getInstance().selectAllCostumer(), showAllCustomerPanel, this, "CostumerPanel");
            PanelFactory.getInstance().createPanels(EmployeeHandler.getInstance().selectAllEmployees(), employeeListPanel, this, "EmployeePanel");
            repaint();
            revalidate();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        costScrollSearch.setDoubleBuffered(false);
    }

    public void setUserControl(Control control, Employee employee) {
        GUI.control = control;
        this.employee = employee;
        lw.dispose();
        this.setVisible(true);
        this.setEnabled(true);
        this.requestFocus();
        loggedIn = true;
    }
    
    public void editCaseSetup() {
        listModelObjects.clear();
        PanelFactory.getInstance().createPanels(c.getArticles(), articleDisplayPanel, this, "ArticlePanel");
        System.out.println("1");
        if (employee.checkAddedMyCases(c) == true) {
            addToMyCasesCheckBox.setSelected(true);
        } else {
            addToMyCasesCheckBox.setSelected(false);
        }
        caseBeginDateLabel.setText(dateFormat.format(c.getCreatedAt()));
        costumerNameLabel.setText(c.getCustomer().getCostumerName());
        phoneCostumerLabel.setText("" + c.getCustomer().getPhone());
        emailCostumerLabel.setText(c.getCustomer().getEmail());
        lastUpdatedField.setText(dateFormat.format(c.getLastUpdated()));
        caseDescriptionEditPanel.setText(c.getDescription());
        caseNmbEditPanel.setText("" + c.getKonsNmb());
        jTextField4.setText(c.getCaseName());
        adressCostumerLabel.setText(c.getCustomer().getAddress());
        zipCodeCostumerLabel.setText(c.getCustomer().getCityOfZip());
    }

    public CardLayout getCl() {
        return cl;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public void setC(Case c) {
        this.c = c;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardPanel = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        caseHandlingPanel = new javax.swing.JPanel();
        caseNmbSField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        caseNameSField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ownerSField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        caseSearchButton = new javax.swing.JButton();
        objectTypeSField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        myCasesTab = new javax.swing.JPanel();
        myCasesScrollPane = new javax.swing.JScrollPane();
        myCasesPanel = new javax.swing.JPanel();
        NewestCasesTab = new javax.swing.JPanel();
        newestCasesScrollPane = new javax.swing.JScrollPane();
        newestCasesPanel = new javax.swing.JPanel();
        finishedCasesTab = new javax.swing.JPanel();
        finishedCasesScrollPane = new javax.swing.JScrollPane();
        finishedCasesPanel = new javax.swing.JPanel();
        konsNmbField = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        newCasePanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        newCaseNameField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        newCaseContactField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        newCasePhoneField = new javax.swing.JTextField();
        newCaseEmailField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        newCaseAdressField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        newCaseZipCodeField = new javax.swing.JTextField();
        costumerTypeBox = new javax.swing.JComboBox();
        existingCostumerCheckBox = new javax.swing.JCheckBox();
        findCostumerField = new javax.swing.JTextField();
        newObjectTypeBox = new javax.swing.JComboBox();
        newObjectButton = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        objectAmountField = new javax.swing.JTextField();
        costScrollSearch = new javax.swing.JScrollPane();
        selectCostumerButton = new javax.swing.JButton();
        createCaseButton = new javax.swing.JButton();
        caseCreationNameField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        newCaseDescription = new javax.swing.JTextField();
        createTaskConsNumberField = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        createCaseMuseumsNrField = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        createCaseLocationBox = new javax.swing.JComboBox<>();
        newObjectNameField = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        createCaseObjectDescriptionArea = new javax.swing.JTextArea();
        jLabel64 = new javax.swing.JLabel();
        createCaseTaskBox = new javax.swing.JComboBox<>();
        createCaseAddTaskButton = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        createCaseTaskList = new javax.swing.JList<>();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        taskDescriptionArea = new javax.swing.JTextArea();
        customerListPanel = new javax.swing.JPanel();
        customerListScrollPane = new javax.swing.JScrollPane();
        showAllCustomerPanel = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel66 = new javax.swing.JLabel();
        findCustomerNameField = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        findCustomerContactInfoField = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        findCustomerPhoneField = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        findCustomerEmailField = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        findCustomerZipCodeField = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        findCustomerAdressField = new javax.swing.JTextField();
        findCustomerEdibleCheckBox = new javax.swing.JCheckBox();
        findCustomerSaveInfoButton = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jList5 = new javax.swing.JList();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        employeeSettingsPanel = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        newUsernameField = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        newPasswordField1 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        newPasswordField2 = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        saveLoginInfoButton = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        editEmailField = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        editPhoneField = new javax.swing.JTextField();
        savePersonalInfoButton = new javax.swing.JButton();
        adminPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        dbUserField = new javax.swing.JTextField();
        dbPasswordField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        dbUrlField = new javax.swing.JTextField();
        dbInfoCheckBox = new javax.swing.JCheckBox();
        changeDbButton = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        newEmployeeFirstnameField = new javax.swing.JTextField();
        newEmployeeLastnameField = new javax.swing.JTextField();
        newEmployeeTypeBox = new javax.swing.JComboBox();
        jLabel54 = new javax.swing.JLabel();
        newEmployeeEmailField = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        newEmployeePhoneField = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        newEmployeeInitialerField = new javax.swing.JTextField();
        newEmployeeUsernameField = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        newEmployeePassword1Field = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        newEmployeePassword2Field = new javax.swing.JTextField();
        createNewEmployeeButton = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        logButton = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        employeeListPanel = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        editCasePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        articleDisplayPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        selectAllArticlesBox = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        caseDescriptionEditPanel = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        saveChangesEditCaseButton = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        costumerNameLabel = new javax.swing.JLabel();
        contactPersonCostumerLabel = new javax.swing.JLabel();
        phoneCostumerLabel = new javax.swing.JLabel();
        emailCostumerLabel = new javax.swing.JLabel();
        adressCostumerLabel = new javax.swing.JLabel();
        zipCodeCostumerLabel = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        caseBeginDateLabel = new javax.swing.JLabel();
        caseEndedDateLabel = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        caseNmbEditPanel = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        employeeLastUpdateField = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        lastUpdatedField = new javax.swing.JTextField();
        editPanelBackButton = new javax.swing.JButton();
        addToMyCasesCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cardPanel.setLayout(new java.awt.CardLayout());

        jLabel1.setText("Sags nr.");

        jLabel2.setText("Sagsnavn");

        jLabel3.setText("Ejer");

        caseSearchButton.setText("Søg");
        caseSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseSearchButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Genstandstype");

        javax.swing.GroupLayout myCasesPanelLayout = new javax.swing.GroupLayout(myCasesPanel);
        myCasesPanel.setLayout(myCasesPanelLayout);
        myCasesPanelLayout.setHorizontalGroup(
            myCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        myCasesPanelLayout.setVerticalGroup(
            myCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );

        myCasesScrollPane.setViewportView(myCasesPanel);

        javax.swing.GroupLayout myCasesTabLayout = new javax.swing.GroupLayout(myCasesTab);
        myCasesTab.setLayout(myCasesTabLayout);
        myCasesTabLayout.setHorizontalGroup(
            myCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(myCasesScrollPane)
        );
        myCasesTabLayout.setVerticalGroup(
            myCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(myCasesScrollPane)
        );

        jTabbedPane2.addTab("Mine sager", myCasesTab);

        newestCasesScrollPane.setHorizontalScrollBar(null);

        javax.swing.GroupLayout newestCasesPanelLayout = new javax.swing.GroupLayout(newestCasesPanel);
        newestCasesPanel.setLayout(newestCasesPanelLayout);
        newestCasesPanelLayout.setHorizontalGroup(
            newestCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
        );
        newestCasesPanelLayout.setVerticalGroup(
            newestCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );

        newestCasesScrollPane.setViewportView(newestCasesPanel);

        javax.swing.GroupLayout NewestCasesTabLayout = new javax.swing.GroupLayout(NewestCasesTab);
        NewestCasesTab.setLayout(NewestCasesTabLayout);
        NewestCasesTabLayout.setHorizontalGroup(
            NewestCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newestCasesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
        );
        NewestCasesTabLayout.setVerticalGroup(
            NewestCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newestCasesScrollPane)
        );

        jTabbedPane2.addTab("Nye sager", NewestCasesTab);

        javax.swing.GroupLayout finishedCasesPanelLayout = new javax.swing.GroupLayout(finishedCasesPanel);
        finishedCasesPanel.setLayout(finishedCasesPanelLayout);
        finishedCasesPanelLayout.setHorizontalGroup(
            finishedCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        finishedCasesPanelLayout.setVerticalGroup(
            finishedCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );

        finishedCasesScrollPane.setViewportView(finishedCasesPanel);

        javax.swing.GroupLayout finishedCasesTabLayout = new javax.swing.GroupLayout(finishedCasesTab);
        finishedCasesTab.setLayout(finishedCasesTabLayout);
        finishedCasesTabLayout.setHorizontalGroup(
            finishedCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(finishedCasesScrollPane)
        );
        finishedCasesTabLayout.setVerticalGroup(
            finishedCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(finishedCasesScrollPane)
        );

        jTabbedPane2.addTab("Afsluttede sager", finishedCasesTab);

        jLabel60.setText("Kons nr.");

        javax.swing.GroupLayout caseHandlingPanelLayout = new javax.swing.GroupLayout(caseHandlingPanel);
        caseHandlingPanel.setLayout(caseHandlingPanelLayout);
        caseHandlingPanelLayout.setHorizontalGroup(
            caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(caseHandlingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(caseSearchButton)
                    .addGroup(caseHandlingPanelLayout.createSequentialGroup()
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(konsNmbField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(objectTypeSField, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(ownerSField)
                            .addComponent(caseNameSField)
                            .addComponent(caseNmbSField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel60))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 410, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        caseHandlingPanelLayout.setVerticalGroup(
            caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(caseHandlingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addGroup(caseHandlingPanelLayout.createSequentialGroup()
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(caseNmbSField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(caseNameSField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ownerSField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(objectTypeSField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(konsNmbField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addGap(43, 43, 43)
                        .addComponent(caseSearchButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabbedPane.addTab("Sagsliste", caseHandlingPanel);

        jLabel5.setFont(new java.awt.Font("LiSong Pro", 0, 36)); // NOI18N
        jLabel5.setText("Opret ny sag");

        jLabel6.setText("Ny kunde");

        jLabel7.setText("Navn");

        jLabel8.setText("Kontaktperson");

        jLabel9.setText("Tlf. Nummer");

        jLabel10.setText("Email");

        jLabel16.setText("Adresse");

        jLabel17.setText("Postnummer");

        costumerTypeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kunde Type", "Privat", "Museum", "Offentlig" }));

        existingCostumerCheckBox.setText("Eksisterende kunde");

        findCostumerField.setText("Find kunde her...");
        findCostumerField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                findCostumerFieldMousePressed(evt);
            }
        });
        findCostumerField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findCostumerFieldKeyReleased(evt);
            }
        });

        newObjectTypeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Genstands type", "Maleri", "Arkæologi" }));

        newObjectButton.setText("Ny Genstand");
        newObjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newObjectButtonActionPerformed(evt);
            }
        });

        jLabel18.setText("Antal genstande:");

        objectAmountField.setText("1");

        costScrollSearch.setHorizontalScrollBar(null);

        selectCostumerButton.setText("Vælg");
        selectCostumerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCostumerButtonActionPerformed(evt);
            }
        });

        createCaseButton.setText("Opret sag");
        createCaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCaseButtonActionPerformed(evt);
            }
        });

        jLabel19.setText("Sagsnavn");

        jLabel20.setText("Beskrivelse");

        newCaseDescription.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                newCaseDescriptionMousePressed(evt);
            }
        });

        jLabel61.setText("Kons nr.");

        jLabel62.setText("Genstands navn");

        createCaseLocationBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Placering" }));

        jLabel63.setText("Museums nr.");

        createCaseObjectDescriptionArea.setColumns(20);
        createCaseObjectDescriptionArea.setRows(5);
        jScrollPane6.setViewportView(createCaseObjectDescriptionArea);

        jLabel64.setText("Genstandsbeskrivelse");

        createCaseTaskBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opgaver" }));

        createCaseAddTaskButton.setText("Tilføj");
        createCaseAddTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCaseAddTaskButtonActionPerformed(evt);
            }
        });

        createCaseTaskList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "liste med", "det valgte", "opgaver" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(createCaseTaskList);

        jSeparator14.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel74.setText("Akronym");

        taskDescriptionArea.setColumns(20);
        taskDescriptionArea.setRows(5);
        jScrollPane5.setViewportView(taskDescriptionArea);

        javax.swing.GroupLayout newCasePanelLayout = new javax.swing.GroupLayout(newCasePanel);
        newCasePanel.setLayout(newCasePanelLayout);
        newCasePanelLayout.setHorizontalGroup(
            newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newCasePanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(newCaseNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(newCaseContactField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(newCasePhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(newCaseEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(newCaseAdressField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel74))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(newCaseZipCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addComponent(costumerTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(existingCostumerCheckBox)
                            .addComponent(findCostumerField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(costScrollSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(caseCreationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(newCaseDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(selectCostumerButton)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(newObjectButton)
                                .addGap(6, 6, 6)
                                .addComponent(newObjectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(objectAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addGap(61, 61, 61)
                                .addComponent(createTaskConsNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(jLabel63)
                                .addGap(40, 40, 40)
                                .addComponent(createCaseMuseumsNrField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(jLabel62)
                                .addGap(23, 23, 23)
                                .addComponent(newObjectNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel64)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(141, 141, 141)
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(createCaseLocationBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createCaseTaskBox, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addComponent(createCaseAddTaskButton))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addGap(339, 339, 339)
                                .addComponent(createCaseButton))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        newCasePanelLayout.setVerticalGroup(
            newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newCasePanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel6)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7)
                        .addGap(6, 6, 6)
                        .addComponent(newCaseNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(6, 6, 6)
                        .addComponent(newCaseContactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(costumerTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(6, 6, 6)
                                .addComponent(newCasePhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addGap(6, 6, 6)
                                .addComponent(newCaseEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(6, 6, 6)
                                .addComponent(newCaseAdressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel74))
                                .addGap(6, 6, 6)
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newCaseZipCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addComponent(existingCostumerCheckBox)
                        .addGap(3, 3, 3)
                        .addComponent(findCostumerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(costScrollSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addGap(7, 7, 7)
                        .addComponent(caseCreationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel20)
                        .addGap(11, 11, 11)
                        .addComponent(newCaseDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(selectCostumerButton))
                    .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171)
                        .addComponent(createCaseButton))
                    .addGroup(newCasePanelLayout.createSequentialGroup()
                        .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newObjectButton)
                                    .addGroup(newCasePanelLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(newObjectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(6, 6, 6)
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newCasePanelLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel18))
                                    .addComponent(objectAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newCasePanelLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel61))
                                    .addComponent(createTaskConsNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newCasePanelLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel63))
                                    .addComponent(createCaseMuseumsNrField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newCasePanelLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel62))
                                    .addComponent(newObjectNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addComponent(jLabel64))
                            .addGroup(newCasePanelLayout.createSequentialGroup()
                                .addGroup(newCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newCasePanelLayout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(createCaseAddTaskButton))
                                    .addGroup(newCasePanelLayout.createSequentialGroup()
                                        .addComponent(createCaseLocationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(createCaseTaskBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        tabbedPane.addTab("Opret sag", newCasePanel);

        javax.swing.GroupLayout showAllCustomerPanelLayout = new javax.swing.GroupLayout(showAllCustomerPanel);
        showAllCustomerPanel.setLayout(showAllCustomerPanelLayout);
        showAllCustomerPanelLayout.setHorizontalGroup(
            showAllCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );
        showAllCustomerPanelLayout.setVerticalGroup(
            showAllCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );

        customerListScrollPane.setViewportView(showAllCustomerPanel);

        jLabel65.setFont(new java.awt.Font("LiSong Pro", 0, 36)); // NOI18N
        jLabel65.setText("Kundeliste");

        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel66.setText("Navn");

        jLabel67.setText("Kontaktperson");

        jLabel68.setText("Tlf.");

        jLabel69.setText("Email");

        jLabel70.setText("Postnummer");

        jLabel71.setText("Adresse");

        findCustomerEdibleCheckBox.setText("Ændre kundens oplysninger");

        findCustomerSaveInfoButton.setText("Gem");

        jList5.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane10.setViewportView(jList5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 196, Short.MAX_VALUE))
        );

        jScrollPane9.setViewportView(jPanel2);

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel72.setFont(new java.awt.Font("LiSong Pro", 0, 36)); // NOI18N
        jLabel72.setText("Kundens sager");

        jLabel73.setFont(new java.awt.Font("LiSong Pro", 0, 36)); // NOI18N
        jLabel73.setText("Kunde oplysninger");

        javax.swing.GroupLayout customerListPanelLayout = new javax.swing.GroupLayout(customerListPanel);
        customerListPanel.setLayout(customerListPanelLayout);
        customerListPanelLayout.setHorizontalGroup(
            customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerListPanelLayout.createSequentialGroup()
                        .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel66)
                                .addComponent(findCustomerNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                .addComponent(jLabel67)
                                .addComponent(findCustomerContactInfoField)
                                .addComponent(jLabel68)
                                .addComponent(findCustomerPhoneField)
                                .addComponent(jLabel69)
                                .addComponent(findCustomerEmailField)
                                .addComponent(jLabel70)
                                .addComponent(findCustomerZipCodeField)
                                .addComponent(jLabel71)
                                .addComponent(findCustomerAdressField))
                            .addComponent(findCustomerEdibleCheckBox)
                            .addComponent(findCustomerSaveInfoButton))
                        .addGap(121, 121, 121)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel73))
                .addGap(18, 18, 18)
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(271, Short.MAX_VALUE))
        );
        customerListPanelLayout.setVerticalGroup(
            customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerListPanelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65)
                    .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel72)
                        .addComponent(jLabel73)))
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerListPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator12)
                                .addComponent(customerListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                                .addComponent(jSeparator13, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(customerListPanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel66)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findCustomerNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel67)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findCustomerContactInfoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findCustomerPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findCustomerEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findCustomerZipCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findCustomerAdressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(findCustomerEdibleCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findCustomerSaveInfoButton))))
                    .addGroup(customerListPanelLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tabbedPane.addTab("Kundekartotek", customerListPanel);

        jLabel43.setFont(new java.awt.Font("LiSong Pro", 0, 36)); // NOI18N
        jLabel43.setText("Mine indstillinger");

        jLabel44.setFont(new java.awt.Font("LiSong Pro", 0, 18)); // NOI18N
        jLabel44.setText("Lod ind");

        jLabel45.setText("Nyt brugernavn");

        jLabel46.setText("Ny kode");

        jLabel47.setText("Gentag ny kode");

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        saveLoginInfoButton.setText("Gem");
        saveLoginInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLoginInfoButtonActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("LiSong Pro", 0, 18)); // NOI18N
        jLabel48.setText("Personlige indtillinger");

        jLabel49.setText("Email");

        jLabel50.setText("Tlf.");

        savePersonalInfoButton.setText("Gem");

        javax.swing.GroupLayout employeeSettingsPanelLayout = new javax.swing.GroupLayout(employeeSettingsPanel);
        employeeSettingsPanel.setLayout(employeeSettingsPanelLayout);
        employeeSettingsPanelLayout.setHorizontalGroup(
            employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeSettingsPanelLayout.createSequentialGroup()
                .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeSettingsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(employeeSettingsPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel47)
                                .addComponent(jLabel45)
                                .addComponent(jLabel44)
                                .addComponent(newUsernameField)
                                .addComponent(jLabel46)
                                .addComponent(newPasswordField1)
                                .addComponent(newPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                            .addComponent(saveLoginInfoButton))
                        .addGap(36, 36, 36)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel48)
                            .addComponent(jLabel50)
                            .addComponent(editEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(savePersonalInfoButton))))
                .addContainerGap(804, Short.MAX_VALUE))
        );
        employeeSettingsPanelLayout.setVerticalGroup(
            employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, employeeSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeSettingsPanelLayout.createSequentialGroup()
                        .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(jLabel48))
                        .addGap(18, 18, 18)
                        .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeSettingsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addGap(18, 18, 18)
                                .addComponent(newUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel46)
                                .addGap(18, 18, 18)
                                .addComponent(newPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel47)
                                .addGap(18, 18, 18)
                                .addComponent(newPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(saveLoginInfoButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE))
                            .addGroup(employeeSettingsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(18, 18, 18)
                                .addComponent(editEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel50)
                                .addGap(18, 18, 18)
                                .addComponent(editPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(savePersonalInfoButton)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(274, 274, 274))
                    .addGroup(employeeSettingsPanelLayout.createSequentialGroup()
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        tabbedPane.addTab("Indstillinger", employeeSettingsPanel);

        jLabel11.setFont(new java.awt.Font("LiSong Pro", 0, 36)); // NOI18N
        jLabel11.setText("Administrator");

        jLabel12.setFont(new java.awt.Font("LiSong Pro", 0, 24)); // NOI18N
        jLabel12.setText("Database oplysninger");

        jLabel13.setText("Bruger");

        jLabel14.setText("Kode");

        jLabel15.setText("URL");

        dbInfoCheckBox.setText("Ja, jeg ved, at jeg ændrer i databasens oplysninger");

        changeDbButton.setText("Ændre Databasen");
        changeDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeDbButtonActionPerformed(evt);
            }
        });

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel51.setFont(new java.awt.Font("LiSong Pro", 0, 24)); // NOI18N
        jLabel51.setText("Opret ny medarbejder");

        jLabel52.setText("Fornavn");

        jLabel53.setText("Efternavn");

        newEmployeeTypeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Medarbejder type", "Admin", "Medarbejder", "Projekt ansat" }));

        jLabel54.setText("Email");

        jLabel55.setText("Tlf.");

        jLabel56.setText("Brugernavn");

        jLabel57.setText("Initialer");

        jLabel58.setText("Kode");

        jLabel59.setText("Gentag kode");

        createNewEmployeeButton.setText("Opret");
        createNewEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewEmployeeButtonActionPerformed(evt);
            }
        });

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        logButton.setText("Log");
        logButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeeListPanelLayout = new javax.swing.GroupLayout(employeeListPanel);
        employeeListPanel.setLayout(employeeListPanelLayout);
        employeeListPanelLayout.setHorizontalGroup(
            employeeListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        employeeListPanelLayout.setVerticalGroup(
            employeeListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 447, Short.MAX_VALUE)
        );

        jScrollPane8.setViewportView(employeeListPanel);

        jButton4.setText("Deaktiver medarbejder");

        jButton6.setText("Nulstil adgangskode");

        javax.swing.GroupLayout adminPanelLayout = new javax.swing.GroupLayout(adminPanel);
        adminPanel.setLayout(adminPanelLayout);
        adminPanelLayout.setHorizontalGroup(
            adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(adminPanelLayout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addGroup(adminPanelLayout.createSequentialGroup()
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(changeDbButton)
                            .addGroup(adminPanelLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(207, 207, 207)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13)
                            .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(dbUrlField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(dbPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dbUserField, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(dbInfoCheckBox)
                            .addComponent(jLabel14))
                        .addGap(58, 58, 58)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(newEmployeePassword1Field, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addComponent(newEmployeeUsernameField, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(adminPanelLayout.createSequentialGroup()
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newEmployeeFirstnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(adminPanelLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(adminPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel52)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel53))
                                            .addComponent(jLabel51)))
                                    .addGroup(adminPanelLayout.createSequentialGroup()
                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(newEmployeeEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel58))
                                        .addGap(26, 26, 26)
                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel59)
                                            .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel55)
                                                .addComponent(newEmployeeLastnameField)
                                                .addComponent(newEmployeePhoneField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                            .addComponent(newEmployeePassword2Field, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(newEmployeeTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newEmployeeInitialerField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel57)))
                            .addComponent(createNewEmployeeButton)
                            .addComponent(jLabel56))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logButton)
                            .addGroup(adminPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
        );
        adminPanelLayout.setVerticalGroup(
            adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel51))
                .addGap(18, 18, 18)
                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminPanelLayout.createSequentialGroup()
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53)
                            .addComponent(jLabel57))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newEmployeeFirstnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newEmployeeLastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newEmployeeInitialerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54)
                            .addComponent(jLabel55))
                        .addGap(18, 18, 18)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newEmployeePhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newEmployeeEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(newEmployeeTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newEmployeeUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newEmployeePassword1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newEmployeePassword2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createNewEmployeeButton))
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(adminPanelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dbUserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dbPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(adminPanelLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(adminPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dbUrlField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dbInfoCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(changeDbButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(adminPanelLayout.createSequentialGroup()
                        .addComponent(logButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8)
                            .addGroup(adminPanelLayout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(136, 136, 136))
        );

        tabbedPane.addTab("Admin", adminPanel);

        cardPanel.add(tabbedPane, "card2");

        javax.swing.GroupLayout articleDisplayPanelLayout = new javax.swing.GroupLayout(articleDisplayPanel);
        articleDisplayPanel.setLayout(articleDisplayPanelLayout);
        articleDisplayPanelLayout.setHorizontalGroup(
            articleDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );
        articleDisplayPanelLayout.setVerticalGroup(
            articleDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(articleDisplayPanel);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Arkæologi skal ha en masse jobs", "Alle andre skal kun ha konservering til rådighed (muligvis selvvalgt)", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jButton1.setText("Gem");

        selectAllArticlesBox.setText("Marker alle");
        selectAllArticlesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllArticlesBoxActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("LiSong Pro", 0, 24)); // NOI18N
        jLabel21.setText("Genstandsliste");

        jLabel22.setText("Opgaver");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vælg opgave" }));

        jLabel23.setText("Status");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vælg status", "Ikke startet", "igang", "færdig" }));

        jButton2.setText("Opdater");

        jLabel24.setText("Sags ansvarlige");

        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Her står alle", "ansatte" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);

        jList4.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "her skal de valgte", "ansatte på denne", "sag stå", "man vælger ved ", "at dobbeltklikke" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList4);

        jButton3.setText("Tilføj");

        jLabel25.setText("Genstandsbeskrivelse");

        jLabel26.setText("Sags tilbud");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sagens status", "Tilbud sendt", "Tilbud accepteret", "Tilbud afvist" }));

        jLabel27.setText("Sags beskrivelse");

        jLabel28.setText("Genstandens Placering");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vælg placering", " " }));

        jLabel29.setText("Andre Oplysninger");

        jCheckBox1.setText("Tilknyttet billede");

        jCheckBox3.setText("Fil i arkivet");

        saveChangesEditCaseButton.setText("Gem ændringer");
        saveChangesEditCaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChangesEditCaseButtonActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Sagen er afsluttet");

        jLabel30.setText("Kunde oplysninger");

        jLabel31.setText("Navn:");

        jLabel32.setText("Kontaktperson:");

        jLabel33.setText("Tlf.:");

        jLabel34.setText("Email:");

        jLabel35.setText("Adresse:");

        jLabel36.setText("Postnummer:");

        costumerNameLabel.setText("navn");

        contactPersonCostumerLabel.setText("kontaktperson");

        phoneCostumerLabel.setText("nummer");

        emailCostumerLabel.setText("email");

        adressCostumerLabel.setText("adresse");

        zipCodeCostumerLabel.setText("postnummer");

        jButton5.setText("Opdater");

        jLabel37.setText("Oprettelses dato:");

        jLabel38.setText("Afsluttelses dato:");

        caseBeginDateLabel.setText("sag oprettet");

        caseEndedDateLabel.setText("sag afsluttet");

        jLabel39.setText("Sag nr.");

        jLabel40.setText("Sag Navn");

        jLabel41.setText("af");

        employeeLastUpdateField.setEditable(false);
        employeeLastUpdateField.setText("Majbritt Pedersen");

        jLabel42.setText("Sidst opdateret d.");

        lastUpdatedField.setEditable(false);

        editPanelBackButton.setText("Tilbage");
        editPanelBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPanelBackButtonActionPerformed(evt);
            }
        });

        addToMyCasesCheckBox.setText("Tilføj til mine sager");

        javax.swing.GroupLayout editCasePanelLayout = new javax.swing.GroupLayout(editCasePanel);
        editCasePanel.setLayout(editCasePanelLayout);
        editCasePanelLayout.setHorizontalGroup(
            editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCasePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editPanelBackButton))
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addComponent(jLabel24))
                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(selectAllArticlesBox)
                                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jLabel22)
                                                                    .addComponent(jButton1))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jButton2)
                                                                    .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel23)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jButton5)
                                                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jLabel28)
                                                                    .addComponent(jCheckBox1)
                                                                    .addComponent(jLabel29)
                                                                    .addComponent(jCheckBox3)))
                                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jButton3)
                                                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCasePanelLayout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jLabel39)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(caseNmbEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCasePanelLayout.createSequentialGroup()
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel37)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(caseBeginDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel38)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(caseEndedDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(24, 24, 24))
                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel27)
                                                            .addComponent(jLabel26))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(caseDescriptionEditPanel)))
                                                    .addComponent(jSeparator5)
                                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel32)
                                                            .addComponent(jLabel33)
                                                            .addComponent(jLabel31)
                                                            .addComponent(jLabel34)
                                                            .addComponent(jLabel35)
                                                            .addComponent(jLabel36))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(costumerNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(contactPersonCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                                            .addComponent(phoneCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(emailCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(adressCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(zipCodeCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                                .addContainerGap())))
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel42)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lastUpdatedField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel41)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(employeeLastUpdateField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(saveChangesEditCaseButton))
                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel25)
                                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(830, 830, 830)
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(addToMyCasesCheckBox)
                                                    .addComponent(jCheckBox4))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addContainerGap())))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCasePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        editCasePanelLayout.setVerticalGroup(
            editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCasePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel26)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(caseNmbEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectAllArticlesBox)
                            .addComponent(jLabel27)
                            .addComponent(caseDescriptionEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addGap(26, 26, 26)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(costumerNameLabel)
                                            .addComponent(jLabel31))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel32)
                                            .addComponent(contactPersonCostumerLabel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel33)
                                            .addComponent(phoneCostumerLabel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel34)
                                            .addComponent(emailCostumerLabel))
                                        .addGap(12, 12, 12)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel35)
                                            .addComponent(adressCostumerLabel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel36)
                                            .addComponent(zipCodeCostumerLabel)))
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addGap(96, 96, 96)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBox1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckBox3))))
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(caseBeginDateLabel)
                                    .addComponent(jLabel37))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(caseEndedDateLabel)
                                    .addComponent(jLabel38))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addToMyCasesCheckBox)
                                .addGap(3, 3, 3)
                                .addComponent(jCheckBox4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(lastUpdatedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41)
                            .addComponent(employeeLastUpdateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveChangesEditCaseButton)
                            .addComponent(editPanelBackButton))
                        .addContainerGap())
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        cardPanel.add(editCasePanel, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void caseSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caseSearchButtonActionPerformed
        try {
            newestCasesPanel.removeAll();
            cases = CaseHandler.getInstance().searchCases(Integer.parseInt(caseNmbSField.getText()), caseNameSField.getText());
            PanelFactory.getInstance().createPanels(cases, newestCasesPanel, this, "CasePanel");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, ex, title, HEIGHT);
            //Eller label med rød tekst
        }
    }//GEN-LAST:event_caseSearchButtonActionPerformed

    private void changeDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeDbButtonActionPerformed
        DBHandler.getInstance().setConfig(dbUrlField.getText(), dbUserField.getText(), dbPasswordField.getText());
    }//GEN-LAST:event_changeDbButtonActionPerformed

    private void findCostumerFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findCostumerFieldKeyReleased
        String search = findCostumerField.getText();
        if (!(search.equals(""))) {
            costScrollSearch.setVisible(true);
            list.setVisible(true);
            if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
                SearchListListener sl = new SearchListListener(findCostumerField, list);
            } else {
                try {
                    listModel.clear();
                    ArrayList<Costumer> costumers = CostumerHandler.getInstance().searchCostumerName(search);
                    if (costumers.size() > 0) {
                        findCostumerField.setText(search + costumers.get(0).getCostumerName().substring(search.length()));
                        findCostumerField.setSelectionStart(search.length());
                    }
                    for (Costumer costumer : costumers) {
                        listModel.addElement(costumer);
                    }
                    listModel.trimToSize();
                    costScrollSearch.setBounds(6, 300, 206, costumers.size() * 25);
                    costScrollSearch.setViewportView(list);
                    costScrollSearch.setVisible(true);
                    list.setVisible(true);
                    list.revalidate();
                    list.repaint();
                    list.ensureIndexIsVisible(0);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            costScrollSearch.setVisible(false);
            list.setVisible(false);
        }

    }//GEN-LAST:event_findCostumerFieldKeyReleased

    private void selectCostumerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCostumerButtonActionPerformed
        costSearchSelected = (Costumer) listModel.getElementAt(list.getSelectedIndex());
    }//GEN-LAST:event_selectCostumerButtonActionPerformed

    private void findCostumerFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_findCostumerFieldMousePressed
        if (findCostumerField.getText().equalsIgnoreCase("Find kunde her...")) {
            findCostumerField.setText("");
        } else {
            findCostumerField.setSelectionStart(0);
        }
    }//GEN-LAST:event_findCostumerFieldMousePressed

    private void createCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCaseButtonActionPerformed
        if (existingCostumerCheckBox.isSelected()) {
            try {
                Case newCase = new Case(CaseHandler.getInstance().generateKonsNmb(), caseCreationNameField.getText(), newCaseDescription.getText(), newCaseArticles, false, cal.getTime(), cal.getTime(), costSearchSelected);
                CaseHandler.getInstance().saveCase(newCase, employee, true);
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //ArrayList af contacts
                //Oprette kontaker til kunden? ContactHandler
                ArrayList<Contact> contacts = new ArrayList<>();
                Costumer costumer = new Costumer(CostumerHandler.getInstance().generateCostumerID(), newCaseNameField.getText(), "museums akronym", 11/*museums nummer*/, Integer.parseInt(newCasePhoneField.getText()), newCaseEmailField.getText(), "Addresse", "4700 zip", contacts);
                Case newCase = new Case(CaseHandler.getInstance().generateKonsNmb(), caseCreationNameField.getText(), newCaseDescription.getText(), newCaseArticles, false, cal.getTime(), cal.getTime(), costumer);
                CaseHandler.getInstance().saveCase(newCase, employee, false);
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        newCaseArticles.clear();
        costSearchSelected = null;
    }//GEN-LAST:event_createCaseButtonActionPerformed

    private void newCaseDescriptionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newCaseDescriptionMousePressed
        for (Component comp : newCasePanel.getComponents()) {
            comp.setVisible(false);
            comp.setEnabled(false);
        }
        DescriptionBox dBox = new DescriptionBox(newCasePanel, newCaseDescription);
        dBox.setBounds(100, 100, 600, 480);
        newCasePanel.add(dBox);
        repaint();
        revalidate();
    }//GEN-LAST:event_newCaseDescriptionMousePressed

    private void editPanelBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPanelBackButtonActionPerformed
        c = null;
        cl.previous(cardPanel);
    }//GEN-LAST:event_editPanelBackButtonActionPerformed

    private void saveChangesEditCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChangesEditCaseButtonActionPerformed
        /*Fyld felterne med info, hvorefter den tjekker, om de er ændret i forhold til dem i databasen
         og ud fra det logger, hvilken felter der er ændret om af hvem
         -Overvej om der skal laves en Log/LogEntry klasse, eller om det blot skal være en ArrayList af Strings
         Eksempel på en log entry:
         Grete Hansen Ændrede i sagsnavn - 15/5/2016 20:54:10
         employee.getName() + "\t" + tingen de foretager sig + komponentet/erne, som de foretager ændriger på
         + tidspunket ændringerne er foretaget
         */

        if (addToMyCasesCheckBox.isSelected()) {
            try {
//                if (employee.checkAddedMyCases(c) == false) {
                CaseHandler.getInstance().addToMyCases(employee, c);
//                    employeeLastUpdateField.setText(employee.getName());
//                    lastUpdatedField.setText("" + dateFormat.format(cal.getTime()));
//                }
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
//                if (employee.checkAddedMyCases(c)) {
                CaseHandler.getInstance().deleteMyCase(c.getKonsNmb(), employee.getEmployeeID());
//                    employeeLastUpdateField.setText(employee.getName());
//                    lastUpdatedField.setText("" + dateFormat.format(cal.getTime()));
//                }
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveChangesEditCaseButtonActionPerformed

    private void saveLoginInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLoginInfoButtonActionPerformed
        try {
            EmployeeHandler.getInstance().changePasswordAndUsername(newUsernameField.getText(), newPasswordField2.getText(), employee);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveLoginInfoButtonActionPerformed

    private void newObjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newObjectButtonActionPerformed
        //Articles kan ikke få adgang til casens kons nr før, derfor bruges nul som startværdi,
        //Der derefter bruger en setter til at hente sagens kons nr, når der trykkes på opret sag
        //Det samme gøres med selve Article objekts kons nr, der genereres ved at tage sagens kons nr
        //Og kører ArrayList'en igennem og lægger iterator til sagens kons nr
        //Tasks skal sættes efter oprettelse af den Article, som de skal knyttes til
        //Det gøres ved at tage den sidste index fra ArrayList'en, så man har den sidst oprettede Article
        ArrayList<Task> tasks = new ArrayList<>();
        Article article = new Article(newObjectNameField.getText(), 0, (String) newObjectTypeBox.getSelectedItem(), 0, tasks);
        newCaseArticles.add(article);
    }//GEN-LAST:event_newObjectButtonActionPerformed

    private void selectAllArticlesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllArticlesBoxActionPerformed
        if (selectAllArticlesBox.isSelected() == true) {

            //Løb alle article panels igennem og sæt deres marker til selected
            for (int i = 0; i < articleDisplayPanel.getComponents().length; i++) {
                ArticlePanel ap = (ArticlePanel) articleDisplayPanel.getComponent(i);
                ap.setSelected(true);
            }
        } else {
            for (int i = 0; i < articleDisplayPanel.getComponents().length; i++) {
                ArticlePanel ap = (ArticlePanel) articleDisplayPanel.getComponent(i);
                ap.setSelected(false);
            }
        }
    }//GEN-LAST:event_selectAllArticlesBoxActionPerformed

    private void logButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logButtonActionPerformed

    private void createNewEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewEmployeeButtonActionPerformed
        try {
            ArrayList<PanelInterface> newMyCases = new ArrayList<>();
            String employeeType = (String) newEmployeeTypeBox.getSelectedItem();
            boolean admin = false;
            boolean partTime = false;
            if (employeeType.equals("Admin")) {
                admin = true;
            } else if (employeeType.equals("Projekt ansat"))  {
                partTime = true;
            }
            Employee newE = new Employee(EmployeeHandler.getInstance().generateEmployeeID(), newEmployeeUsernameField.getText(), newEmployeePassword2Field.getText(), newEmployeeFirstnameField.getText(),
            newEmployeeLastnameField.getText(), Integer.parseInt(newEmployeePhoneField.getText()), newEmployeeEmailField.getText(), admin, partTime, true, newMyCases);
            EmployeeHandler.getInstance().saveEmployee(newE);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        } 
        
    }//GEN-LAST:event_createNewEmployeeButtonActionPerformed

    private void createCaseAddTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCaseAddTaskButtonActionPerformed
        Task task = new Task("Endnu ikke påbegyndt", taskDescriptionArea.getText());
        Article article = (Article)newCaseArticles.get(newCaseArticles.size());
        article.addTask(task);
    }//GEN-LAST:event_createCaseAddTaskButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUI(control).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NewestCasesTab;
    private javax.swing.JCheckBox addToMyCasesCheckBox;
    private javax.swing.JPanel adminPanel;
    private javax.swing.JLabel adressCostumerLabel;
    private javax.swing.JPanel articleDisplayPanel;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JLabel caseBeginDateLabel;
    private javax.swing.JTextField caseCreationNameField;
    private javax.swing.JTextField caseDescriptionEditPanel;
    private javax.swing.JLabel caseEndedDateLabel;
    private javax.swing.JPanel caseHandlingPanel;
    private javax.swing.JTextField caseNameSField;
    private javax.swing.JTextField caseNmbEditPanel;
    private javax.swing.JTextField caseNmbSField;
    private javax.swing.JButton caseSearchButton;
    private javax.swing.JButton changeDbButton;
    private javax.swing.JLabel contactPersonCostumerLabel;
    private javax.swing.JScrollPane costScrollSearch;
    private javax.swing.JLabel costumerNameLabel;
    private javax.swing.JComboBox costumerTypeBox;
    private javax.swing.JButton createCaseAddTaskButton;
    private javax.swing.JButton createCaseButton;
    private javax.swing.JComboBox<String> createCaseLocationBox;
    private javax.swing.JTextField createCaseMuseumsNrField;
    private javax.swing.JTextArea createCaseObjectDescriptionArea;
    private javax.swing.JComboBox<String> createCaseTaskBox;
    private javax.swing.JList<String> createCaseTaskList;
    private javax.swing.JButton createNewEmployeeButton;
    private javax.swing.JTextField createTaskConsNumberField;
    private javax.swing.JPanel customerListPanel;
    private javax.swing.JScrollPane customerListScrollPane;
    private javax.swing.JCheckBox dbInfoCheckBox;
    private javax.swing.JTextField dbPasswordField;
    private javax.swing.JTextField dbUrlField;
    private javax.swing.JTextField dbUserField;
    private javax.swing.JPanel editCasePanel;
    private javax.swing.JTextField editEmailField;
    private javax.swing.JButton editPanelBackButton;
    private javax.swing.JTextField editPhoneField;
    private javax.swing.JLabel emailCostumerLabel;
    private javax.swing.JTextField employeeLastUpdateField;
    private javax.swing.JPanel employeeListPanel;
    private javax.swing.JPanel employeeSettingsPanel;
    private javax.swing.JCheckBox existingCostumerCheckBox;
    private javax.swing.JTextField findCostumerField;
    private javax.swing.JTextField findCustomerAdressField;
    private javax.swing.JTextField findCustomerContactInfoField;
    private javax.swing.JCheckBox findCustomerEdibleCheckBox;
    private javax.swing.JTextField findCustomerEmailField;
    private javax.swing.JTextField findCustomerNameField;
    private javax.swing.JTextField findCustomerPhoneField;
    private javax.swing.JButton findCustomerSaveInfoButton;
    private javax.swing.JTextField findCustomerZipCodeField;
    private javax.swing.JPanel finishedCasesPanel;
    private javax.swing.JScrollPane finishedCasesScrollPane;
    private javax.swing.JPanel finishedCasesTab;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JList jList4;
    private javax.swing.JList jList5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField konsNmbField;
    private javax.swing.JTextField lastUpdatedField;
    private javax.swing.JButton logButton;
    private javax.swing.JPanel myCasesPanel;
    private javax.swing.JScrollPane myCasesScrollPane;
    private javax.swing.JPanel myCasesTab;
    private javax.swing.JTextField newCaseAdressField;
    private javax.swing.JTextField newCaseContactField;
    private javax.swing.JTextField newCaseDescription;
    private javax.swing.JTextField newCaseEmailField;
    private javax.swing.JTextField newCaseNameField;
    private javax.swing.JPanel newCasePanel;
    private javax.swing.JTextField newCasePhoneField;
    private javax.swing.JTextField newCaseZipCodeField;
    private javax.swing.JTextField newEmployeeEmailField;
    private javax.swing.JTextField newEmployeeFirstnameField;
    private javax.swing.JTextField newEmployeeInitialerField;
    private javax.swing.JTextField newEmployeeLastnameField;
    private javax.swing.JTextField newEmployeePassword1Field;
    private javax.swing.JTextField newEmployeePassword2Field;
    private javax.swing.JTextField newEmployeePhoneField;
    private javax.swing.JComboBox newEmployeeTypeBox;
    private javax.swing.JTextField newEmployeeUsernameField;
    private javax.swing.JButton newObjectButton;
    private javax.swing.JTextField newObjectNameField;
    private javax.swing.JComboBox newObjectTypeBox;
    private javax.swing.JTextField newPasswordField1;
    private javax.swing.JTextField newPasswordField2;
    private javax.swing.JTextField newUsernameField;
    private javax.swing.JPanel newestCasesPanel;
    private javax.swing.JScrollPane newestCasesScrollPane;
    private javax.swing.JTextField objectAmountField;
    private javax.swing.JTextField objectTypeSField;
    private javax.swing.JTextField ownerSField;
    private javax.swing.JLabel phoneCostumerLabel;
    private javax.swing.JButton saveChangesEditCaseButton;
    private javax.swing.JButton saveLoginInfoButton;
    private javax.swing.JButton savePersonalInfoButton;
    private javax.swing.JCheckBox selectAllArticlesBox;
    private javax.swing.JButton selectCostumerButton;
    private javax.swing.JPanel showAllCustomerPanel;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextArea taskDescriptionArea;
    private javax.swing.JLabel zipCodeCostumerLabel;
    // End of variables declaration//GEN-END:variables
}
