/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.Control;
import dao.ArticleHandler;
import dao.CaseHandler;
import dao.CostumerHandler;
import dao.DBHandler;
import dao.EmployeeHandler;
import dao.TaskHandler;
import factories.PanelFactory;
import interfaces.PanelInterface;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.Timer;
import listeners.SearchListListener;
import model.Article;
import model.Case;
import model.Contact;
import model.Costumer;
import model.Employee;
import model.Log;
import model.Task;

/**
 *
 * @author Simon
 */
public class GUIView extends javax.swing.JFrame {

    private ArrayList<PanelInterface> cases;
    private ArrayList<CasePanel> casePanels;
    private Case c;
    private Calendar cal;
    private CardLayout cl;
    private static Control control;
    private Costumer costSearchSelected;
    private DefaultListModel listModel;
    private DefaultListModel caseResponsibleListModel;
    private DefaultListModel allEmployeesListModel;
    private DefaultListModel listModelObjects;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
    private Employee employee;
    private JList list;
    private JList caseResponsibleList;
    private JList allEmployeesList;
    private boolean loggedIn = false;
    private final Timer updateTimer;
    private LoginView lw;
    private JTextField[] customerFields;

    /**
     * Creates new form GUI
     *
     * @param control
     */
    public GUIView(Control control) {
        initComponents();
        lw = new LoginView(this);
        lw.setAlwaysOnTop(true);
        lw.setResizable(false);
        this.setEnabled(false);
        lw.setVisible(true);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.control = control;
        employee = null;
        while (!loggedIn) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {

            }
        }
        JTextField[] textFields = {customerListNameField, customerListContactfield, customerListPhoneField, customerListEmailField, customerListAddressField};
        customerFields = textFields;
        int adminTab = tabbedPane.indexOfTab("Admin");
        tabbedPane.setEnabledAt(adminTab, false);
        if (employee.isAdmin()) {
            tabbedPane.setEnabledAt(adminTab, true);
        }
        listModel = new DefaultListModel();
        listModelObjects = new DefaultListModel();
        list = new JList(listModel);
        list.setSize(160, 130);
        list.setVisible(false);
        costScrollSearch.add(list);
        costScrollSearch.setVisible(false);
        caseResponsibleListModel = new DefaultListModel();
        caseResponsibleList = new JList(caseResponsibleListModel);
        caseResponsibleList.setVisible(true);
        caseResponsibleList.setSize(caseResponsibleScrollPane.getWidth(), caseResponsibleScrollPane.getHeight());
        caseResponsibleScrollPane.add(caseResponsibleList);
        caseResponsibleScrollPane.setViewportView(caseResponsibleList);
        allEmployeesListModel = new DefaultListModel();
        allEmployeesList = new JList(allEmployeesListModel);
        allEmployeesList.setSize(allEmployeesScrollPane.getWidth(), allEmployeesScrollPane.getHeight());
        allEmployeesList.setVisible(true);
        allEmployeesScrollPane.add(allEmployeesList);
        allEmployeesScrollPane.setViewportView(allEmployeesList);
        cal = Calendar.getInstance();
        cl = (CardLayout) cardPanel.getLayout();
        try {
            cases = CaseHandler.getInstance().getCasesNewest();
            PanelFactory.getInstance().createPanels(cases, newestCasesPanel, this, "CasePanel", customerFields);
            PanelFactory.getInstance().createPanels(CaseHandler.getInstance().getMyCases(employee), myCasesPanel, this, "CasePanel", customerFields);
            PanelFactory.getInstance().createPanels(CaseHandler.getInstance().getFinishedCases(), finishedCasesPanel, this, "CasePanel", customerFields);
            PanelFactory.getInstance().createPanels(CostumerHandler.getInstance().selectAllCostumer(), showAllCustomerPanel, this, "CostumerPanel", customerFields);
            PanelFactory.getInstance().createPanels(EmployeeHandler.getInstance().selectAllEmployees(), employeeListPanel, this, "EmployeePanel", customerFields);
            repaint();
            revalidate();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        try {
            TaskHandler.getInstance().fillTaskNameBox(createCaseTaskBox);
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(this, "Kunne ikke hente opgave listen.");
        }
        costScrollSearch.setDoubleBuffered(false);
        updateTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                update();
            }
        });
        updateTimer.start();
    }

    public void update() {
        if (myCasesPanel.getComponents() != null) {
            for (Component casePanel : myCasesPanel.getComponents()) {
                CasePanel cp = (CasePanel) casePanel;
                cp.update();
            }
        }
        if (newestCasesPanel.getComponents() != null) {
            for (Component casePanel : newestCasesPanel.getComponents()) {
                CasePanel cp = (CasePanel) casePanel;
                cp.update();
            }
        }
        if (finishedCasesPanel.getComponents() != null) {
            for (Component casePanel : finishedCasesPanel.getComponents()) {
                CasePanel cp = (CasePanel) casePanel;
                cp.update();
            }
        }
        if (showAllCustomerPanel.getComponents() != null) {
            for (Component customerPanel : showAllCustomerPanel.getComponents()) {
                CustomerPanel cp = (CustomerPanel) customerPanel;
                cp.update();
            }
        }
        if (employeeListPanel.getComponents() != null) {
            for (Component employeePanel : employeeListPanel.getComponents()) {
                EmployeePanel ep = (EmployeePanel) employeePanel;
                ep.update();
            }
        }
        if (articleDisplayPanel.getComponents() != null) {
            for (Component articlePanel : articleDisplayPanel.getComponents()) {
                ArticlePanel ap = (ArticlePanel) articlePanel;
                ap.update();
            }
        }
        repaint();
        revalidate();
    }

    public void setUserControl(Control control, Employee employee) {
        GUIView.control = control;
        this.employee = employee;
        lw.dispose();
        this.setVisible(true);
        this.setEnabled(true);
        this.requestFocus();
        loggedIn = true;
    }

    public void editCaseSetup() {
        listModelObjects.clear();
        allEmployeesListModel.clear();
        caseResponsibleListModel.clear();
        try {
            PanelFactory.getInstance().createPanels(c.getArticles(), articleDisplayPanel, this, "ArticlePanel", customerFields);

            for (PanelInterface emp : c.getCaseResponsible()) {
                Employee e = (Employee) emp;
                caseResponsibleListModel.addElement(e);
            }
            ArrayList<PanelInterface> allEmps = EmployeeHandler.getInstance().selectAllEmployeesSorted(c.getCaseResponsible());

            for (PanelInterface emp : allEmps) {
                Employee e = (Employee) emp;
                allEmployeesListModel.addElement(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        if (employee.checkAddedMyCases(c) == true) {
            addToMyCasesCheckBox.setSelected(true);
        } else {
            addToMyCasesCheckBox.setSelected(false);
        }
        if (c.isFinished()) {
            caseFinishedCheckBox.setSelected(true);
        } else {
            caseFinishedCheckBox.setSelected(false);
        }
        caseBeginDateLabel.setText(dateFormat.format(c.getCreatedAt()));
        costumerNameLabel.setText(c.getCustomer().getCostumerName());
        phoneCostumerLabel.setText("" + c.getCustomer().getPhone());
        emailCostumerLabel.setText(c.getCustomer().getEmail());
        lastUpdatedField.setText(dateFormat.format(c.getLastUpdated()));
        caseDescriptionEditPanel.setText(c.getDescription());
        caseNmbEditPanel.setText("" + c.getKonsNmb());
        caseNameEditField.setText(c.getCaseName());
        adressCostumerLabel.setText(c.getCustomer().getAddress());
        editCaseOfferNmbField.setText("" + c.getOfferNmb());
    }

    public JList getEditArticleTaskList() {
        return editArticleTaskList;
    }
    
    public JTextField getArticleDescription() {
        return articleDescriptionEditField;
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
        caseSearchButton = new javax.swing.JButton();
        articleTypeSField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        caseDisplayPane = new javax.swing.JTabbedPane();
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
        searchOfferNmbField = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        newCasePanel = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        newCaseNameField = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        newCaseContactField = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        newCasePhoneField = new javax.swing.JTextField();
        newCaseEmailField = new javax.swing.JTextField();
        newCaseAddressField = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        newCaseMuseumNmbField = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        newCaseAcroField = new javax.swing.JTextField();
        costumerTypeBox = new javax.swing.JComboBox<>();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        addContactNameField = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        addContactPhoneField = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        addContactEmailField = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        newContactList = new javax.swing.JList<>();
        addContactButton = new javax.swing.JButton();
        existingCostumerCheckBox = new javax.swing.JCheckBox();
        findCostumerField = new javax.swing.JTextField();
        selectCostumerButton = new javax.swing.JButton();
        jLabel97 = new javax.swing.JLabel();
        caseCreationNameField = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        newCaseDescription = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        newCaseOfferNmbField = new javax.swing.JTextField();
        jSeparator17 = new javax.swing.JSeparator();
        newArticleButton = new javax.swing.JButton();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        articleLocationField = new javax.swing.JTextField();
        createArticleConsNmbField = new javax.swing.JTextField();
        newArticleMuseumsNmbField = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        createCaseArticleDescriptionArea = new javax.swing.JTextArea();
        createTaskNameButton = new javax.swing.JButton();
        createCaseTaskBox = new javax.swing.JComboBox<>();
        jLabel104 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        taskDescriptionArea = new javax.swing.JTextArea();
        createCaseAddTaskButton = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        createCaseTaskList = new javax.swing.JList<>();
        createCasebutton = new javax.swing.JButton();
        jLabel105 = new javax.swing.JLabel();
        newArticleNameField = new javax.swing.JTextField();
        costScrollSearch = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        articleAmountField = new javax.swing.JTextField();
        moreArticlesCheckBox = new javax.swing.JCheckBox();
        newArticleTypeField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        newCaseArticleList = new javax.swing.JList<>();
        customerListPanel = new javax.swing.JPanel();
        customerListScrollPane = new javax.swing.JScrollPane();
        showAllCustomerPanel = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel66 = new javax.swing.JLabel();
        customerListNameField = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        customerListContactfield = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        customerListPhoneField = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        customerListEmailField = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        customerListAddressField = new javax.swing.JTextField();
        findCustomerEditableCheckBox = new javax.swing.JCheckBox();
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
        passwordMatchLabel = new javax.swing.JLabel();
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
        newEmployeeInitialsField = new javax.swing.JTextField();
        newEmployeeUsernameField = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        newEmployeePassword1Field = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        newEmployeePassword2Field = new javax.swing.JTextField();
        createNewEmployeeButton = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jScrollPane8 = new javax.swing.JScrollPane();
        employeeListPanel = new javax.swing.JPanel();
        deactiveEmployee = new javax.swing.JButton();
        resetPasswordButton = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        editCasePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        articleDisplayPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        editArticleTaskList = new javax.swing.JList();
        editAddTaskButton = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        editTaskStatusBox = new javax.swing.JComboBox();
        editTaskRefreshButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        allEmployeesAddButton = new javax.swing.JButton();
        articleDescriptionEditField = new javax.swing.JTextField();
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
        caseFinishedCheckBox = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        costumerNameLabel = new javax.swing.JLabel();
        contactPersonCostumerLabel = new javax.swing.JLabel();
        phoneCostumerLabel = new javax.swing.JLabel();
        emailCostumerLabel = new javax.swing.JLabel();
        adressCostumerLabel = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        caseBeginDateLabel = new javax.swing.JLabel();
        caseEndedDateLabel = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        caseNmbEditPanel = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        caseNameEditField = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        employeeLastUpdateField = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        lastUpdatedField = new javax.swing.JTextField();
        editPanelBackButton = new javax.swing.JButton();
        addToMyCasesCheckBox = new javax.swing.JCheckBox();
        editCaseOfferNmbField = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        editCaseLogButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        caseResponsibleScrollPane = new javax.swing.JScrollPane();
        caseResponsibleRemoveButton = new javax.swing.JButton();
        allEmployeesScrollPane = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cardPanel.setLayout(new java.awt.CardLayout());

        tabbedPane.setPreferredSize(new java.awt.Dimension(1415, 720));

        jLabel1.setText("Sags nr.");

        jLabel2.setText("Sagsnavn");

        caseSearchButton.setText("Søg");
        caseSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseSearchButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Genstandstype");

        myCasesTab.setPreferredSize(new java.awt.Dimension(740, 630));

        myCasesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        myCasesPanel.setName("myCasesP"); // NOI18N

        javax.swing.GroupLayout myCasesPanelLayout = new javax.swing.GroupLayout(myCasesPanel);
        myCasesPanel.setLayout(myCasesPanelLayout);
        myCasesPanelLayout.setHorizontalGroup(
            myCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        myCasesPanelLayout.setVerticalGroup(
            myCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
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
            .addComponent(myCasesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );

        caseDisplayPane.addTab("Mine sager", myCasesTab);

        NewestCasesTab.setPreferredSize(new java.awt.Dimension(740, 675));

        newestCasesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        newestCasesScrollPane.setHorizontalScrollBar(null);

        newestCasesPanel.setName("newestCasesP"); // NOI18N
        newestCasesPanel.setPreferredSize(new java.awt.Dimension(738, 699));

        javax.swing.GroupLayout newestCasesPanelLayout = new javax.swing.GroupLayout(newestCasesPanel);
        newestCasesPanel.setLayout(newestCasesPanelLayout);
        newestCasesPanelLayout.setHorizontalGroup(
            newestCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        newestCasesPanelLayout.setVerticalGroup(
            newestCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );

        newestCasesScrollPane.setViewportView(newestCasesPanel);

        javax.swing.GroupLayout NewestCasesTabLayout = new javax.swing.GroupLayout(NewestCasesTab);
        NewestCasesTab.setLayout(NewestCasesTabLayout);
        NewestCasesTabLayout.setHorizontalGroup(
            NewestCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newestCasesScrollPane)
        );
        NewestCasesTabLayout.setVerticalGroup(
            NewestCasesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newestCasesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );

        caseDisplayPane.addTab("Nye sager", NewestCasesTab);

        finishedCasesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        finishedCasesPanel.setName("finishedCasesP"); // NOI18N

        javax.swing.GroupLayout finishedCasesPanelLayout = new javax.swing.GroupLayout(finishedCasesPanel);
        finishedCasesPanel.setLayout(finishedCasesPanelLayout);
        finishedCasesPanelLayout.setHorizontalGroup(
            finishedCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        finishedCasesPanelLayout.setVerticalGroup(
            finishedCasesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
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
            .addComponent(finishedCasesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );

        caseDisplayPane.addTab("Afsluttede sager", finishedCasesTab);

        jLabel60.setText("Kons nr.");

        jLabel83.setText("Tilbuds nr.");

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
                            .addComponent(searchOfferNmbField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(konsNmbField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(articleTypeSField, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(caseNameSField)
                            .addComponent(caseNmbSField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel60)
                            .addComponent(jLabel83))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 493, Short.MAX_VALUE)
                .addComponent(caseDisplayPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        caseHandlingPanelLayout.setVerticalGroup(
            caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(caseHandlingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(articleTypeSField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(konsNmbField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(caseHandlingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchOfferNmbField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel83))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(caseSearchButton))
                    .addComponent(caseDisplayPane, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Sagsliste", caseHandlingPanel);

        newCasePanel.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.setLayout(null);

        jLabel84.setFont(new java.awt.Font("Sylfaen", 0, 24)); // NOI18N
        jLabel84.setText("Opret ny sag");
        newCasePanel.add(jLabel84);
        jLabel84.setBounds(10, 10, 140, 30);

        jLabel85.setText("Ny kunde");
        newCasePanel.add(jLabel85);
        jLabel85.setBounds(20, 50, 110, 14);

        jLabel86.setText("Navn");
        newCasePanel.add(jLabel86);
        jLabel86.setBounds(20, 80, 150, 14);

        newCaseNameField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCaseNameField);
        newCaseNameField.setBounds(20, 100, 150, 25);

        jLabel87.setText("Kontaktperson");
        newCasePanel.add(jLabel87);
        jLabel87.setBounds(20, 140, 150, 14);

        newCaseContactField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCaseContactField);
        newCaseContactField.setBounds(20, 160, 150, 25);

        jLabel88.setText("Tlf. nummer");
        newCasePanel.add(jLabel88);
        jLabel88.setBounds(200, 80, 150, 14);

        newCasePhoneField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCasePhoneField);
        newCasePhoneField.setBounds(200, 100, 150, 25);

        newCaseEmailField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCaseEmailField);
        newCaseEmailField.setBounds(200, 160, 150, 25);

        newCaseAddressField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCaseAddressField);
        newCaseAddressField.setBounds(380, 100, 150, 25);

        jLabel89.setText("Email");
        newCasePanel.add(jLabel89);
        jLabel89.setBounds(200, 140, 150, 14);

        jLabel90.setText("Adresse");
        newCasePanel.add(jLabel90);
        jLabel90.setBounds(380, 80, 150, 14);

        jLabel91.setText("Museums nr.");
        newCasePanel.add(jLabel91);
        jLabel91.setBounds(380, 140, 80, 14);

        newCaseMuseumNmbField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCaseMuseumNmbField);
        newCaseMuseumNmbField.setBounds(380, 160, 80, 25);

        jLabel92.setText("Akronym");
        newCasePanel.add(jLabel92);
        jLabel92.setBounds(480, 140, 70, 14);

        newCaseAcroField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCaseAcroField);
        newCaseAcroField.setBounds(480, 160, 70, 25);

        costumerTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kundetype", "Privat", "Kvote" }));
        costumerTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costumerTypeBoxActionPerformed(evt);
            }
        });
        newCasePanel.add(costumerTypeBox);
        costumerTypeBox.setBounds(476, 10, 100, 20);
        newCasePanel.add(jSeparator11);
        jSeparator11.setBounds(20, 190, 1330, 10);

        jSeparator16.setOrientation(javax.swing.SwingConstants.VERTICAL);
        newCasePanel.add(jSeparator16);
        jSeparator16.setBounds(620, 10, 10, 170);

        jLabel93.setFont(new java.awt.Font("Sylfaen", 0, 24)); // NOI18N
        jLabel93.setText("Tilføj kontakt");
        newCasePanel.add(jLabel93);
        jLabel93.setBounds(630, 10, 150, 30);

        jLabel94.setText("Navn");
        newCasePanel.add(jLabel94);
        jLabel94.setBounds(630, 80, 100, 14);

        addContactNameField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(addContactNameField);
        addContactNameField.setBounds(630, 100, 100, 25);

        jLabel95.setText("Tlf. nummer");
        newCasePanel.add(jLabel95);
        jLabel95.setBounds(630, 140, 100, 14);

        addContactPhoneField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(addContactPhoneField);
        addContactPhoneField.setBounds(630, 160, 100, 25);

        jLabel96.setText("Email");
        newCasePanel.add(jLabel96);
        jLabel96.setBounds(750, 140, 160, 14);

        addContactEmailField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(addContactEmailField);
        addContactEmailField.setBounds(750, 160, 160, 25);

        newContactList.setModel(new DefaultListModel());
        newContactList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane12.setViewportView(newContactList);

        newCasePanel.add(jScrollPane12);
        jScrollPane12.setBounds(1020, 30, 120, 150);

        addContactButton.setText("Tilføj");
        addContactButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContactButtonActionPerformed(evt);
            }
        });
        newCasePanel.add(addContactButton);
        addContactButton.setBounds(930, 159, 80, 23);

        existingCostumerCheckBox.setText("Eksisterende kunde");
        existingCostumerCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingCostumerCheckBoxActionPerformed(evt);
            }
        });
        newCasePanel.add(existingCostumerCheckBox);
        existingCostumerCheckBox.setBounds(20, 200, 250, 23);

        findCostumerField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findCostumerFieldKeyReleased(evt);
            }
        });
        newCasePanel.add(findCostumerField);
        findCostumerField.setBounds(20, 230, 160, 24);

        selectCostumerButton.setText("Vælg");
        selectCostumerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCostumerButtonActionPerformed(evt);
            }
        });
        newCasePanel.add(selectCostumerButton);
        selectCostumerButton.setBounds(180, 229, 80, 23);

        jLabel97.setText("Sagsnavn");
        newCasePanel.add(jLabel97);
        jLabel97.setBounds(20, 310, 160, 14);

        caseCreationNameField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(caseCreationNameField);
        caseCreationNameField.setBounds(20, 330, 160, 25);

        jLabel98.setText("Sags beskrivelse");
        newCasePanel.add(jLabel98);
        jLabel98.setBounds(20, 370, 160, 14);

        newCaseDescription.setPreferredSize(new java.awt.Dimension(6, 25));
        newCaseDescription.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                newCaseDescriptionMousePressed(evt);
            }
        });
        newCasePanel.add(newCaseDescription);
        newCaseDescription.setBounds(20, 390, 160, 25);

        jLabel99.setText("Tilbuds nr.");
        newCasePanel.add(jLabel99);
        jLabel99.setBounds(20, 430, 160, 14);

        newCaseOfferNmbField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newCaseOfferNmbField);
        newCaseOfferNmbField.setBounds(20, 450, 160, 25);

        jSeparator17.setOrientation(javax.swing.SwingConstants.VERTICAL);
        newCasePanel.add(jSeparator17);
        jSeparator17.setBounds(280, 200, 10, 470);

        newArticleButton.setText("Ny genstand");
        newArticleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newArticleButtonActionPerformed(evt);
            }
        });
        newCasePanel.add(newArticleButton);
        newArticleButton.setBounds(300, 550, 120, 23);

        jLabel100.setText("Antal genstande");
        newCasePanel.add(jLabel100);
        jLabel100.setBounds(300, 230, 100, 14);

        jLabel101.setText("Kons nr.");
        newCasePanel.add(jLabel101);
        jLabel101.setBounds(300, 290, 100, 14);

        jLabel102.setText("Museums nr.");
        newCasePanel.add(jLabel102);
        jLabel102.setBounds(300, 320, 100, 14);

        articleLocationField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(articleLocationField);
        articleLocationField.setBounds(420, 260, 120, 25);

        createArticleConsNmbField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(createArticleConsNmbField);
        createArticleConsNmbField.setBounds(420, 290, 120, 25);

        newArticleMuseumsNmbField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newArticleMuseumsNmbField);
        newArticleMuseumsNmbField.setBounds(420, 320, 120, 25);

        jLabel103.setText("Genstands beskrivelse");
        newCasePanel.add(jLabel103);
        jLabel103.setBounds(300, 410, 220, 14);

        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        createCaseArticleDescriptionArea.setColumns(20);
        createCaseArticleDescriptionArea.setRows(5);
        jScrollPane14.setViewportView(createCaseArticleDescriptionArea);

        newCasePanel.add(jScrollPane14);
        jScrollPane14.setBounds(300, 440, 220, 96);

        createTaskNameButton.setText("Ny opgave");
        createTaskNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTaskNameButtonActionPerformed(evt);
            }
        });
        newCasePanel.add(createTaskNameButton);
        createTaskNameButton.setBounds(780, 200, 110, 23);

        createCaseTaskBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opgaver" }));
        createCaseTaskBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCaseTaskBoxActionPerformed(evt);
            }
        });
        newCasePanel.add(createCaseTaskBox);
        createCaseTaskBox.setBounds(900, 201, 110, 20);

        jLabel104.setText("Opgave beskrivelse");
        newCasePanel.add(jLabel104);
        jLabel104.setBounds(780, 250, 230, 14);

        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setToolTipText("");

        taskDescriptionArea.setColumns(20);
        taskDescriptionArea.setRows(5);
        jScrollPane15.setViewportView(taskDescriptionArea);

        newCasePanel.add(jScrollPane15);
        jScrollPane15.setBounds(780, 270, 230, 100);

        createCaseAddTaskButton.setText("Tilføj");
        createCaseAddTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCaseAddTaskButtonActionPerformed(evt);
            }
        });
        newCasePanel.add(createCaseAddTaskButton);
        createCaseAddTaskButton.setBounds(780, 388, 80, 23);

        createCaseTaskList.setModel(new DefaultListModel());
        createCaseTaskList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        createCaseTaskList.setToolTipText("");
        jScrollPane16.setViewportView(createCaseTaskList);

        newCasePanel.add(jScrollPane16);
        jScrollPane16.setBounds(1020, 200, 100, 210);

        createCasebutton.setText("Opret sag");
        createCasebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCasebuttonActionPerformed(evt);
            }
        });
        newCasePanel.add(createCasebutton);
        createCasebutton.setBounds(1240, 640, 90, 23);

        jLabel105.setText("Genstands navn");
        newCasePanel.add(jLabel105);
        jLabel105.setBounds(300, 350, 110, 14);

        newArticleNameField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newArticleNameField);
        newArticleNameField.setBounds(420, 350, 120, 25);
        newCasePanel.add(costScrollSearch);
        costScrollSearch.setBounds(20, 250, 160, 40);

        jLabel3.setText("Placering");
        newCasePanel.add(jLabel3);
        jLabel3.setBounds(300, 260, 80, 14);

        articleAmountField.setEditable(false);
        articleAmountField.setText("1");
        articleAmountField.setPreferredSize(new java.awt.Dimension(12, 25));
        newCasePanel.add(articleAmountField);
        articleAmountField.setBounds(420, 230, 120, 25);

        moreArticlesCheckBox.setText("Opret flere genstande");
        moreArticlesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreArticlesCheckBoxActionPerformed(evt);
            }
        });
        newCasePanel.add(moreArticlesCheckBox);
        moreArticlesCheckBox.setBounds(300, 200, 240, 23);

        newArticleTypeField.setPreferredSize(new java.awt.Dimension(6, 25));
        newCasePanel.add(newArticleTypeField);
        newArticleTypeField.setBounds(420, 380, 120, 25);

        jLabel6.setText("Genstands type");
        newCasePanel.add(jLabel6);
        jLabel6.setBounds(300, 380, 110, 14);

        newCaseArticleList.setModel(new DefaultListModel());
        newCaseArticleList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        newCaseArticleList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                newCaseArticleListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(newCaseArticleList);

        newCasePanel.add(jScrollPane3);
        jScrollPane3.setBounds(560, 230, 190, 180);

        tabbedPane.addTab("Opret sag", newCasePanel);

        customerListScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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

        customerListNameField.setEditable(false);
        customerListNameField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel67.setText("Kontaktperson");

        customerListContactfield.setEditable(false);
        customerListContactfield.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel68.setText("Tlf.");

        customerListPhoneField.setEditable(false);
        customerListPhoneField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel69.setText("Email");

        customerListEmailField.setEditable(false);
        customerListEmailField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel71.setText("Adresse");

        customerListAddressField.setEditable(false);
        customerListAddressField.setPreferredSize(new java.awt.Dimension(10, 24));

        findCustomerEditableCheckBox.setText("Ændre kundens oplysninger");
        findCustomerEditableCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findCustomerEditableCheckBoxActionPerformed(evt);
            }
        });

        findCustomerSaveInfoButton.setText("Gem");
        findCustomerSaveInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findCustomerSaveInfoButtonActionPerformed(evt);
            }
        });

        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jScrollPane10.setHorizontalScrollBar(null);

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
                                .addComponent(customerListNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                .addComponent(jLabel67)
                                .addComponent(customerListContactfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel68)
                                .addComponent(customerListPhoneField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel69)
                                .addComponent(customerListEmailField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(customerListAddressField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(findCustomerEditableCheckBox)
                            .addComponent(findCustomerSaveInfoButton)
                            .addComponent(jLabel71))
                        .addGap(121, 121, 121)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel73))
                .addGap(18, 18, 18)
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(352, Short.MAX_VALUE))
        );
        customerListPanelLayout.setVerticalGroup(
            customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerListPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
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
                                .addComponent(jSeparator13, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(customerListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(customerListPanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel66)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerListNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel67)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerListContactfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerListPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerListEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerListAddressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(findCustomerEditableCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        jLabel44.setText("Log ind");

        jLabel45.setText("Nyt brugernavn");

        newUsernameField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel46.setText("Ny kode");

        newPasswordField1.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel47.setText("Gentag ny kode");

        newPasswordField2.setPreferredSize(new java.awt.Dimension(10, 24));

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

        editEmailField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel50.setText("Tlf.");

        editPhoneField.setPreferredSize(new java.awt.Dimension(10, 24));

        savePersonalInfoButton.setText("Gem");
        savePersonalInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePersonalInfoButtonActionPerformed(evt);
            }
        });

        passwordMatchLabel.setForeground(new java.awt.Color(255, 0, 0));
        passwordMatchLabel.setText("Adgangskoder er ikke ens");
        passwordMatchLabel.setVisible(false);

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
                        .addGroup(employeeSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel47)
                            .addComponent(jLabel45)
                            .addComponent(jLabel44)
                            .addComponent(newUsernameField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel46)
                            .addComponent(newPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(newPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(saveLoginInfoButton)
                            .addComponent(passwordMatchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(885, Short.MAX_VALUE))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordMatchLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
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

        dbUserField.setEditable(false);
        dbUserField.setPreferredSize(new java.awt.Dimension(10, 24));

        dbPasswordField.setEditable(false);
        dbPasswordField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel15.setText("URL");

        dbUrlField.setEditable(false);
        dbUrlField.setPreferredSize(new java.awt.Dimension(10, 24));

        dbInfoCheckBox.setText("Ja, jeg ved, at jeg ændrer i databasens oplysninger");
        dbInfoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbInfoCheckBoxActionPerformed(evt);
            }
        });

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

        newEmployeeFirstnameField.setPreferredSize(new java.awt.Dimension(10, 24));

        newEmployeeLastnameField.setPreferredSize(new java.awt.Dimension(10, 24));

        newEmployeeTypeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Medarbejder type", "Admin", "Medarbejder", "Projekt ansat" }));

        jLabel54.setText("Email");

        newEmployeeEmailField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel55.setText("Tlf.");

        newEmployeePhoneField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel56.setText("Brugernavn");

        jLabel57.setText("Initialer");

        newEmployeeInitialsField.setPreferredSize(new java.awt.Dimension(10, 24));

        newEmployeeUsernameField.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel58.setText("Kode");

        newEmployeePassword1Field.setPreferredSize(new java.awt.Dimension(10, 24));

        jLabel59.setText("Gentag kode");

        newEmployeePassword2Field.setPreferredSize(new java.awt.Dimension(10, 24));

        createNewEmployeeButton.setText("Opret");
        createNewEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewEmployeeButtonActionPerformed(evt);
            }
        });

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout employeeListPanelLayout = new javax.swing.GroupLayout(employeeListPanel);
        employeeListPanel.setLayout(employeeListPanelLayout);
        employeeListPanelLayout.setHorizontalGroup(
            employeeListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        employeeListPanelLayout.setVerticalGroup(
            employeeListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );

        jScrollPane8.setViewportView(employeeListPanel);

        deactiveEmployee.setText("Deaktiver medarbejder");
        deactiveEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deactiveEmployeeActionPerformed(evt);
            }
        });

        resetPasswordButton.setText("Nulstil adgangskode");
        resetPasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPasswordButtonActionPerformed(evt);
            }
        });

        jLabel79.setText("Nulstilte adgangskoder er");

        jLabel80.setText("medarbejderens initialer + 123");

        javax.swing.GroupLayout adminPanelLayout = new javax.swing.GroupLayout(adminPanel);
        adminPanel.setLayout(adminPanelLayout);
        adminPanelLayout.setHorizontalGroup(
            adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adminPanelLayout.createSequentialGroup()
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
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
                                        .addComponent(dbPasswordField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dbUserField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(dbInfoCheckBox)
                                    .addComponent(jLabel14))
                                .addGap(58, 58, 58)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel54)
                                    .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(newEmployeePassword1Field, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                        .addComponent(newEmployeeUsernameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                        .addComponent(newEmployeeLastnameField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(newEmployeePhoneField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                                    .addComponent(newEmployeePassword2Field, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(newEmployeeTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(newEmployeeInitialsField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel57)))
                                    .addComponent(createNewEmployeeButton)
                                    .addComponent(jLabel56))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(resetPasswordButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deactiveEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 134, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
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
                            .addComponent(newEmployeeInitialsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGap(29, 29, 29)
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                            .addGroup(adminPanelLayout.createSequentialGroup()
                                .addComponent(resetPasswordButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel79)
                                .addGap(3, 3, 3)
                                .addComponent(jLabel80)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deactiveEmployee)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(136, 136, 136))
        );

        tabbedPane.addTab("Admin", adminPanel);

        cardPanel.add(tabbedPane, "card2");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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

        editArticleTaskList.setModel(new DefaultListModel());
        editArticleTaskList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        editArticleTaskList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                editArticleTaskListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(editArticleTaskList);

        editAddTaskButton.setText("Tilføj opgave");
        editAddTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editAddTaskButtonActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("LiSong Pro", 0, 24)); // NOI18N
        jLabel21.setText("Genstandsliste");

        jLabel22.setText("Opgaver");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vælg opgave" }));

        jLabel23.setText("Status");

        editTaskStatusBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vælg status", "Ikke startet", "Igang", "Færdig" }));

        editTaskRefreshButton.setText("Opdater");
        editTaskRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTaskRefreshButtonActionPerformed(evt);
            }
        });

        jLabel24.setText("Sags ansvarlige");

        allEmployeesAddButton.setText("Tilføj");
        allEmployeesAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allEmployeesAddButtonActionPerformed(evt);
            }
        });

        articleDescriptionEditField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                articleDescriptionEditFieldMousePressed(evt);
            }
        });

        jLabel25.setText("Genstandsbeskrivelse");

        jLabel26.setText("Sags tilbud");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sagens status", "Tilbud sendt", "Tilbud accepteret", "Tilbud afvist" }));

        jLabel27.setText("Sags beskrivelse");

        caseDescriptionEditPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                caseDescriptionEditPanelMousePressed(evt);
            }
        });

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

        caseFinishedCheckBox.setText("Sagen er afsluttet");

        jLabel30.setText("Kunde oplysninger");

        jLabel31.setText("Navn:");

        jLabel32.setText("Kontaktperson:");

        jLabel33.setText("Tlf.:");

        jLabel34.setText("Email:");

        jLabel35.setText("Adresse:");

        costumerNameLabel.setText("navn");

        contactPersonCostumerLabel.setText("kontaktperson");

        phoneCostumerLabel.setText("nummer");

        emailCostumerLabel.setText("email");

        adressCostumerLabel.setText("adresse");

        jButton5.setText("Opdater");

        jLabel37.setText("Oprettelses dato:");

        jLabel38.setText("Afsluttelses dato:");

        caseBeginDateLabel.setText("sag oprettet");

        caseEndedDateLabel.setText("sag afsluttet");

        jLabel39.setText("Sag nr.");

        caseNmbEditPanel.setEditable(false);

        jLabel40.setText("Sag Navn");

        jLabel41.setText("af");

        employeeLastUpdateField.setEditable(false);
        employeeLastUpdateField.setText("Majbritt Pedersen");
        employeeLastUpdateField.setPreferredSize(new java.awt.Dimension(119, 24));

        jLabel42.setText("Sidst opdateret d.");

        lastUpdatedField.setEditable(false);
        lastUpdatedField.setPreferredSize(new java.awt.Dimension(10, 24));

        editPanelBackButton.setText("Tilbage");
        editPanelBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPanelBackButtonActionPerformed(evt);
            }
        });

        addToMyCasesCheckBox.setText("Tilføj til mine sager");

        jLabel82.setText("Tilbuds nr.");

        editCaseLogButton.setText("Log");
        editCaseLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCaseLogButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Medarbejder");

        caseResponsibleRemoveButton.setText("Fjern");
        caseResponsibleRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseResponsibleRemoveButtonActionPerformed(evt);
            }
        });

        allEmployeesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout editCasePanelLayout = new javax.swing.GroupLayout(editCasePanel);
        editCasePanel.setLayout(editCasePanelLayout);
        editCasePanelLayout.setHorizontalGroup(
            editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCasePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addGap(491, 491, 491)
                                        .addComponent(jLabel42)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lastUpdatedField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel41)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(employeeLastUpdateField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(saveChangesEditCaseButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editCaseLogButton)
                                        .addGap(0, 124, Short.MAX_VALUE))
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel25)
                                                    .addComponent(articleDescriptionEditField, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jLabel22)
                                                                    .addComponent(editAddTaskButton))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(editTaskStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(editTaskRefreshButton)
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
                                                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jLabel5)
                                                                    .addComponent(allEmployeesAddButton)
                                                                    .addComponent(allEmployeesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jLabel24)
                                                                    .addComponent(caseResponsibleScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(caseResponsibleRemoveButton))))
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCasePanelLayout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jLabel82)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(editCaseOfferNmbField, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(32, 32, 32)
                                                        .addComponent(jLabel39)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                                        .addComponent(caseNmbEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel40)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(caseNameEditField, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                                                    .addComponent(jLabel35))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(costumerNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(contactPersonCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                                                    .addComponent(phoneCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(emailCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(adressCostumerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCasePanelLayout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jLabel37)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(caseBeginDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addContainerGap())))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCasePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addToMyCasesCheckBox)
                                    .addGroup(editCasePanelLayout.createSequentialGroup()
                                        .addComponent(jLabel38)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(caseEndedDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(caseFinishedCheckBox))
                                .addContainerGap())))
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addComponent(editPanelBackButton)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                    .addComponent(caseNameEditField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCaseOfferNmbField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCasePanelLayout.createSequentialGroup()
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(caseBeginDateLabel)
                                            .addComponent(jLabel37)))
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
                                            .addComponent(editTaskStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editTaskRefreshButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editAddTaskButton)))
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(articleDescriptionEditField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(caseResponsibleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(allEmployeesScrollPane)))
                            .addGroup(editCasePanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(caseEndedDateLabel)
                                    .addComponent(jLabel38))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addToMyCasesCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(caseFinishedCheckBox)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(allEmployeesAddButton)
                            .addComponent(caseResponsibleRemoveButton)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(editCasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editPanelBackButton)
                    .addComponent(jLabel42)
                    .addComponent(lastUpdatedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(employeeLastUpdateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveChangesEditCaseButton)
                    .addComponent(editCaseLogButton))
                .addContainerGap(118, Short.MAX_VALUE))
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
            JPanel displayPanel = PanelFactory.getInstance().getSelectedTabPanel(caseDisplayPane);
            cases = CaseHandler.getInstance().searchCases(employee, displayPanel, caseNmbSField.getText(),
                    caseNameSField.getText(), konsNmbField.getText(), searchOfferNmbField.getText(), articleTypeSField.getText());
            if (cases != null) {
                PanelFactory.getInstance().createPanels(cases, displayPanel, this, "CasePanel", customerFields);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kunne ikke foretage søgningen.");
        }
    }//GEN-LAST:event_caseSearchButtonActionPerformed

    private void changeDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeDbButtonActionPerformed
        DBHandler.getInstance().setConfig(dbUrlField.getText(), dbUserField.getText(), dbPasswordField.getText());
    }//GEN-LAST:event_changeDbButtonActionPerformed

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
        //Undo "setters" til objektets oprindelige tilstand - Command --
        //Ændringerne skal gemmes, inden man trykker okay
        int choice = JOptionPane.showConfirmDialog(this, "Er du sikker på, at du vil gemme?", "Gem ændringer", 2);
        if (choice == JOptionPane.OK_OPTION) {
            Object[] eCRs = caseResponsibleListModel.toArray();
            try {
                EmployeeHandler.getInstance().saveCaseResponsibles(eCRs, c);
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
            //Problem i CaseHandler eller check my cases 
            if (addToMyCasesCheckBox.isSelected()) {
                try {
//                if (employee.checkAddedMyCases(c) == false) {
//                CaseHandler.getInstance().addToMyCases(employee, c);
                    c.setCaseName(caseNameEditField.getText());
//                CaseHandler.getInstance().editCase(c);
                    CaseHandler.getInstance().completeTransaction();
                    employeeLastUpdateField.setText(employee.getFullName());
                    lastUpdatedField.setText("" + dateFormat.format(cal.getTime()));
//                }
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            } else {
                try {
//                if (employee.checkAddedMyCases(c)) {
//                CaseHandler.getInstance().deleteMyCase(c.getKonsNmb(), employee.getEmployeeID());
                    c.setCaseName(caseNameEditField.getText());
//                CaseHandler.getInstance().editCase(c);
                    CaseHandler.getInstance().completeTransaction();
                    employeeLastUpdateField.setText(employee.getFullName());
                    lastUpdatedField.setText("" + dateFormat.format(cal.getTime()));
//                }
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
//            cl.previous(cardPanel);
        } else {
            try {
                CaseHandler.getInstance().cancelLastAction();
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_saveChangesEditCaseButtonActionPerformed

    private void saveLoginInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLoginInfoButtonActionPerformed
        try {
            String username = newUsernameField.getText();
            if (username.isEmpty()) {
                username = employee.getUsername();
            }
            boolean passwordsMatch = EmployeeHandler.getInstance().changePasswordAndUsername(username, newPasswordField1.getText(), newPasswordField2.getText(), employee);
            if (!passwordsMatch) {
                passwordMatchLabel.setVisible(true);
            } else {
                passwordMatchLabel.setVisible(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kunne ikke ændre kodeord og/eller brugernavn");
        }
    }//GEN-LAST:event_saveLoginInfoButtonActionPerformed

    private void createNewEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewEmployeeButtonActionPerformed
        try {
            ArrayList<PanelInterface> newMyCases = new ArrayList<>();
            String employeeType = (String) newEmployeeTypeBox.getSelectedItem();
            boolean admin = false;
            boolean partTime = false;
            if (employeeType.equals("Admin")) {
                admin = true;
            } else if (employeeType.equals("Projekt ansat")) {
                partTime = true;
            }
            Employee newE = new Employee(EmployeeHandler.getInstance().generateEmployeeID(), newEmployeeUsernameField.getText(), newEmployeePassword2Field.getText(), newEmployeeFirstnameField.getText(),
                    newEmployeeLastnameField.getText(), newEmployeeInitialsField.getText(), Integer.parseInt(newEmployeePhoneField.getText()), newEmployeeEmailField.getText(), admin, partTime, true, newMyCases);
            EmployeeHandler.getInstance().saveEmployee(newE);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }//GEN-LAST:event_createNewEmployeeButtonActionPerformed

    private void resetPasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPasswordButtonActionPerformed
        Employee employeePReset = null;
        for (Component comp : employeeListPanel.getComponents()) {
            EmployeePanel ep = (EmployeePanel) comp;
            if (ep.isSelected()) {
                employeePReset = ep.getEmployee();
            }
        }
        if (employeePReset != null) {
            employeePReset.setPassword(employeePReset.getInitials() + "123");
            try {
                EmployeeHandler.getInstance().resetPassword(employeePReset);
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }

    }//GEN-LAST:event_resetPasswordButtonActionPerformed

    private void deactiveEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deactiveEmployeeActionPerformed
        Employee employeePReset = null;
        for (Component comp : employeeListPanel.getComponents()) {
            EmployeePanel ep = (EmployeePanel) comp;
            if (ep.isSelected()) {
                employeePReset = ep.getEmployee();
            }
        }
        if (employeePReset != null) {
            employeePReset.setActive(false);
            try {
                EmployeeHandler.getInstance().deactivateEmployee(employeePReset);
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_deactiveEmployeeActionPerformed

    private void editCaseLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCaseLogButtonActionPerformed
        LogWindow logWindow = new LogWindow(c, editCasePanel);
    }//GEN-LAST:event_editCaseLogButtonActionPerformed

    private void createCaseAddTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCaseAddTaskButtonActionPerformed
        String taskName = (String) createCaseTaskBox.getSelectedItem();
        DefaultListModel articleModel = ((DefaultListModel) newCaseArticleList.getModel());
        Article selectedArticle = (Article) articleModel.getElementAt(newCaseArticleList.getSelectedIndex());
        if (selectedArticle != null) {
            if (!(taskName.equalsIgnoreCase("Opgaver"))) {
                Task task = new Task("Ikke startet", taskName, taskDescriptionArea.getText(), selectedArticle.getArticleID());
                selectedArticle.addTask(task);
                ((DefaultListModel) createCaseTaskList.getModel()).addElement(task);
            } else {
                JOptionPane.showMessageDialog(this, "Der er ikke valgt en opgave");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Der er ikke valgt en genstand at tilføje opgaven til.");
        }
    }//GEN-LAST:event_createCaseAddTaskButtonActionPerformed

    private void createCasebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCasebuttonActionPerformed
        ArrayList<Log> logs = new ArrayList<>();
        DefaultListModel articleModel = (DefaultListModel) newCaseArticleList.getModel();
        DefaultListModel contactModel = (DefaultListModel) newContactList.getModel();
        Costumer customer = null;
        if (existingCostumerCheckBox.isSelected()) {
            customer = costSearchSelected;
        } else {
            try {
                String customerAcro = "";
                int museumsNmb = 0;
                ArrayList<Contact> contacts = null;
                if (costumerTypeBox.getSelectedItem().equals("Kvote")) {
                    customerAcro = newCaseAcroField.getText();
                    museumsNmb = Integer.parseInt(newCaseMuseumNmbField.getText());
                    Contact[] contactArray = (Contact[]) contactModel.toArray();
                    contacts = new ArrayList<>(Arrays.asList(contactArray));
                }
                customer = new Costumer(CostumerHandler.getInstance().generateCostumerID(), newCaseNameField.getText(), customerAcro, museumsNmb, Integer.parseInt(newCasePhoneField.getText()),
                        newCaseEmailField.getText(), newCaseAddressField.getText(), contacts);
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        try {
            int konsNmb = CaseHandler.getInstance().generateKonsNmb();
            Log log = new Log(employee, CaseHandler.getInstance().getCaseID(), "oprettede ", caseCreationNameField.getText(), "", "", new java.sql.Date(cal.getTimeInMillis()));
            System.out.println(employee.getFullName());
            logs.add(log);
            Article[] articleArray = (Article[]) articleModel.toArray();
            ArrayList<PanelInterface> articles = new ArrayList<>(Arrays.asList(articleArray));
            for (PanelInterface article : articles) {
                Article a = (Article) article;
                a.setCaseKonsNmb(konsNmb);
            }
            String offerNmb = newCaseOfferNmbField.getText();
            if (Integer.parseInt(offerNmb) <= 0) {
                offerNmb = null;
            }
            Case newCase = new Case(0, konsNmb, Integer.parseInt(offerNmb), caseCreationNameField.getText(), newCaseDescription.getText(),
                    articles, false, cal.getTime(), cal.getTime(), customer, logs, null);
            boolean caseCreated = CaseHandler.getInstance().saveCase(newCase, employee, existingCostumerCheckBox.isSelected());
            if (caseCreated) {
                JOptionPane.showMessageDialog(this, "Sag oprettet.");
                newCaseNameField.setText("");
                newCaseContactField.setText("");
                newCasePhoneField.setText("");
                newCaseEmailField.setText("");
                newCaseAddressField.setText("");
                newCaseMuseumNmbField.setText("");
                newCaseAcroField.setText("");
                addContactNameField.setText("");
                addContactPhoneField.setText("");
                addContactEmailField.setText("");
                costumerTypeBox.setSelectedItem("Kundetype");
                articleModel.clear();
                contactModel.clear();
                costSearchSelected = null;
                caseCreationNameField.setText("");
                newCaseDescription.setText("");
                newCaseOfferNmbField.setText("");
                articleAmountField.setText("");
                articleLocationField.setText("");
                createArticleConsNmbField.setText("");
                newArticleMuseumsNmbField.setText("");
                newArticleNameField.setText("");
                createCaseArticleDescriptionArea.setText("");
                createCaseTaskBox.setSelectedItem("Opgaver");
                taskDescriptionArea.setText("");
                ((DefaultListModel) createCaseTaskList.getModel()).clear();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tilbuds nr. må kun indeholde gyldige tal.");
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }//GEN-LAST:event_createCasebuttonActionPerformed

    private void existingCostumerCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCostumerCheckBoxActionPerformed
        if (existingCostumerCheckBox.isSelected()) {
            newCaseNameField.setEnabled(false);
            newCaseContactField.setEnabled(false);
            newCasePhoneField.setEnabled(false);
            newCaseEmailField.setEnabled(false);
            newCaseAddressField.setEnabled(false);
            newCaseMuseumNmbField.setEnabled(false);
            newCaseAcroField.setEnabled(false);
            addContactNameField.setEnabled(false);
            addContactPhoneField.setEnabled(false);
            addContactEmailField.setEnabled(false);
            addContactButton.setEnabled(false);
            costumerTypeBox.setEnabled(false);
        } else {
            newCaseNameField.setEnabled(true);
            newCaseContactField.setEnabled(true);
            newCasePhoneField.setEnabled(true);
            newCaseEmailField.setEnabled(true);
            newCaseAddressField.setEnabled(true);
            newCaseMuseumNmbField.setEnabled(true);
            newCaseAcroField.setEnabled(true);
            addContactNameField.setEnabled(true);
            addContactPhoneField.setEnabled(true);
            addContactEmailField.setEnabled(true);
            addContactButton.setEnabled(true);
            costumerTypeBox.setEnabled(true);
        }
    }//GEN-LAST:event_existingCostumerCheckBoxActionPerformed

    private void selectCostumerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCostumerButtonActionPerformed
        costSearchSelected = (Costumer) listModel.getElementAt(list.getSelectedIndex());
    }//GEN-LAST:event_selectCostumerButtonActionPerformed

    private void newArticleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newArticleButtonActionPerformed
        ArrayList<Task> tasks = null;
        Article article = null;
        int articleID = 0;
        try {
            articleID = ArticleHandler.getInstance().generateArticleID();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        int amount = Integer.parseInt(articleAmountField.getText());
        if (amount > 0) {
        if (amount > 1) {
            for (int i = 0; i < amount; i++) {
                tasks = new ArrayList<>();
            if (articleID != 0) {
                article = new Article((articleID + i), newArticleNameField.getText(), 0, createCaseArticleDescriptionArea.getText(),
                        newArticleTypeField.getText(), articleLocationField.getText(), Integer.parseInt(newArticleMuseumsNmbField.getText()), 0, tasks);
                if (article != null) {
                    ((DefaultListModel) newCaseArticleList.getModel()).addElement(article);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Kunne ikke oprette genstande.");
            }
            }
        } else {
            tasks = new ArrayList<>();
            if (articleID != 0) {
                article = new Article(articleID, newArticleNameField.getText(), 0, createCaseArticleDescriptionArea.getText(),
                        newArticleTypeField.getText(), articleLocationField.getText(), Integer.parseInt(newArticleMuseumsNmbField.getText()), 0, tasks);
                if (article != null) {
                    ((DefaultListModel) newCaseArticleList.getModel()).addElement(article);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Kunne ikke oprette genstand.");
            }
        }
        } else {
            JOptionPane.showMessageDialog(this, "Antal genstande må ikke indeholde ugyldige værdier.");
        }

    }//GEN-LAST:event_newArticleButtonActionPerformed

    private void createTaskNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createTaskNameButtonActionPerformed
        String newTaskName = JOptionPane.showInputDialog(this, "Angiv et navn til den nye opgave");
        try {
            createCaseTaskBox.addItem(newTaskName);
            TaskHandler.getInstance().saveTaskName(newTaskName);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kunne ikke gemme den nye opgave til databasen.");
        }
    }//GEN-LAST:event_createTaskNameButtonActionPerformed

    private void findCostumerFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findCostumerFieldKeyReleased
        String search = findCostumerField.getText();
        if (!(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
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
                        costScrollSearch.setBounds(20, 250, 160, costumers.size() * 25);
                        costScrollSearch.setViewportView(list);
                        costScrollSearch.setVisible(true);
                        list.setVisible(true);
                        list.revalidate();
                        list.repaint();
                        list.ensureIndexIsVisible(0);
                    } catch (SQLException ex) {
                        System.out.println(ex.getLocalizedMessage());
                    }
                }
            } else {
                costScrollSearch.setVisible(false);
                list.setVisible(false);
            }
        }
    }//GEN-LAST:event_findCostumerFieldKeyReleased

    private void newCaseDescriptionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newCaseDescriptionMousePressed
        DescriptionBox dBox = new DescriptionBox(newCasePanel, newCaseDescription);
    }//GEN-LAST:event_newCaseDescriptionMousePressed

    private void caseResponsibleRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caseResponsibleRemoveButtonActionPerformed
        allEmployeesListModel.addElement(caseResponsibleList.getSelectedValue());
        caseResponsibleListModel.remove(caseResponsibleList.getSelectedIndex());
    }//GEN-LAST:event_caseResponsibleRemoveButtonActionPerformed

    private void allEmployeesAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allEmployeesAddButtonActionPerformed
        caseResponsibleListModel.addElement(allEmployeesList.getSelectedValue());
        allEmployeesListModel.remove(allEmployeesList.getSelectedIndex());
    }//GEN-LAST:event_allEmployeesAddButtonActionPerformed

    private void createCaseTaskBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCaseTaskBoxActionPerformed

    }//GEN-LAST:event_createCaseTaskBoxActionPerformed

    private void costumerTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costumerTypeBoxActionPerformed
        String costumerType = (String) costumerTypeBox.getSelectedItem();
        if (costumerType.equals("Privat")) {
            newCaseAcroField.setEnabled(false);
            addContactNameField.setEnabled(false);
            addContactPhoneField.setEnabled(false);
            addContactEmailField.setEnabled(false);
            addContactButton.setEnabled(false);
            newContactList.setEnabled(false);
        } else {
            newCaseAcroField.setEnabled(true);
            addContactNameField.setEnabled(true);
            addContactPhoneField.setEnabled(true);
            addContactEmailField.setEnabled(true);
            addContactButton.setEnabled(true);
            newContactList.setEnabled(true);
        }
    }//GEN-LAST:event_costumerTypeBoxActionPerformed

    private void savePersonalInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePersonalInfoButtonActionPerformed
        String email = editEmailField.getText();
        if (email.isEmpty()) {
            email = employee.getEmail();
        }
        employee.setEmail(email);
        String phone = editPhoneField.getText();
        if (phone.matches("[0-9]+") && phone.length() == 8 && !(phone.isEmpty())) {
            employee.setPhone(Integer.parseInt(phone));
        } else {
            JOptionPane.showMessageDialog(this, "Telefon nr. må kun indeholde tal og 8 cifre");
        }
        try {
            EmployeeHandler.getInstance().changePersonalInfo(employee);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kunne ikke ændre personlige indstillinger.");
        }
    }//GEN-LAST:event_savePersonalInfoButtonActionPerformed

    private void findCustomerSaveInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findCustomerSaveInfoButtonActionPerformed
        String error = "Kunne ikke ændre kundeoplyninger.";
        Costumer customerToEdit = null;
        for (Component customerPanel : showAllCustomerPanel.getComponents()) {
            CustomerPanel cp = (CustomerPanel) customerPanel;
            if (cp.isSelected()) {
                customerToEdit = cp.getCustomer();
            }
        }
        if (customerToEdit != null) {
            boolean succeed = true;
            String customerName = customerListNameField.getText();
            if (!customerName.isEmpty()) {
                customerToEdit.setCostumerName(customerName);
            }
            String phone = customerListPhoneField.getText();
            if (!phone.isEmpty()) {
                if (phone.matches("[0-9]+") && phone.length() == 8) {
                    customerToEdit.setPhone(Integer.parseInt(phone));
                } else {
                    succeed = false;
                    error = error + "\nTjek at tlf. indeholder et gyldigt nummer";
                }
            }
            String email = customerListEmailField.getText();
            if (!email.isEmpty()) {
                customerToEdit.setEmail(email);
            }
            String address = customerListAddressField.getText();
            if (!address.isEmpty()) {
                customerToEdit.setAddress(address);
            }
            if (succeed) {
                try {
                    CostumerHandler.getInstance().editCustomer(customerToEdit);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, error);
                }
            } else {
                JOptionPane.showMessageDialog(this, error);
            }
        }
    }//GEN-LAST:event_findCustomerSaveInfoButtonActionPerformed

    private void editArticleTaskListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_editArticleTaskListValueChanged
        editTaskStatusBox.setSelectedItem(((Task) editArticleTaskList.getSelectedValue()).getStatus());
    }//GEN-LAST:event_editArticleTaskListValueChanged

    private void editTaskRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTaskRefreshButtonActionPerformed
        if (((String) editTaskStatusBox.getSelectedItem()).equalsIgnoreCase("Vælg status")) {
            JOptionPane.showMessageDialog(this, "Vælg en status, før du opdaterer opgaven.");
        } else {
            ((Task) editArticleTaskList.getSelectedValue()).setStatus((String) editTaskStatusBox.getSelectedItem());
        }
    }//GEN-LAST:event_editTaskRefreshButtonActionPerformed

    private void editAddTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editAddTaskButtonActionPerformed
        Article selectedArticle = null;
        for (Component articlePanel : articleDisplayPanel.getComponents()) {
            ArticlePanel ap = (ArticlePanel) articlePanel;
            if (ap.isSelected()) {
                selectedArticle = ap.getA();
            }
        }
        if (selectedArticle != null) {
            String taskName = JOptionPane.showInputDialog("Angiv navn på opgaven: ");
            String description = JOptionPane.showInputDialog("Angiv beskrivelse af opgaven: ");
            Task task = new Task("Ikke startet", taskName, description, selectedArticle.getArticleID());
            selectedArticle.addTask(task);
            ((DefaultListModel) editArticleTaskList.getModel()).addElement(task);
            editArticleTaskList.repaint();
        }
    }//GEN-LAST:event_editAddTaskButtonActionPerformed

    private void caseDescriptionEditPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_caseDescriptionEditPanelMousePressed
        DescriptionBox dBox = new DescriptionBox(editCasePanel, caseDescriptionEditPanel);
    }//GEN-LAST:event_caseDescriptionEditPanelMousePressed

    private void articleDescriptionEditFieldMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_articleDescriptionEditFieldMousePressed
        DescriptionBox dBox = new DescriptionBox(editCasePanel, articleDescriptionEditField);
    }//GEN-LAST:event_articleDescriptionEditFieldMousePressed

    private void dbInfoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbInfoCheckBoxActionPerformed
        if (dbInfoCheckBox.isSelected()) {
            dbUserField.setEditable(true);
            dbPasswordField.setEditable(true);
            dbUrlField.setEditable(true);
        } else {
            dbUserField.setEditable(false);
            dbPasswordField.setEditable(false);
            dbUrlField.setEditable(false);
        }
    }//GEN-LAST:event_dbInfoCheckBoxActionPerformed

    private void findCustomerEditableCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findCustomerEditableCheckBoxActionPerformed
        if (findCustomerEditableCheckBox.isSelected()) {
            customerListNameField.setEditable(true);
            customerListContactfield.setEditable(true);
            customerListPhoneField.setEditable(true);
            customerListEmailField.setEditable(true);
            customerListAddressField.setEditable(true);
        } else {
            customerListNameField.setEditable(false);
            customerListContactfield.setEditable(false);
            customerListPhoneField.setEditable(false);
            customerListEmailField.setEditable(false);
            customerListAddressField.setEditable(false);
        }
    }//GEN-LAST:event_findCustomerEditableCheckBoxActionPerformed

    private void moreArticlesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreArticlesCheckBoxActionPerformed
        if (moreArticlesCheckBox.isSelected()) {
            articleAmountField.setEditable(true);
        } else {
            articleAmountField.setEditable(false);
        }
    }//GEN-LAST:event_moreArticlesCheckBoxActionPerformed

    private void addContactButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addContactButtonActionPerformed
        Contact newContact = new Contact(addContactNameField.getText(), Integer.parseInt(addContactPhoneField.getText()), addContactEmailField.getText());
        ((DefaultListModel) newContactList.getModel()).addElement(newContact);
        addContactNameField.setText("");
        addContactPhoneField.setText("");
        addContactEmailField.setText("");
    }//GEN-LAST:event_addContactButtonActionPerformed

    private void newCaseArticleListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_newCaseArticleListValueChanged
        DefaultListModel taskModel = (DefaultListModel) createCaseTaskList.getModel();
        taskModel.clear();
        DefaultListModel articleModel = ((DefaultListModel) newCaseArticleList.getModel());
        Article selectedArticle = (Article) articleModel.getElementAt(newCaseArticleList.getSelectedIndex());
        if (selectedArticle.getTasks().size() > 0) {
            for (Task t : selectedArticle.getTasks()) {
                taskModel.addElement(t);
            }
        }
    }//GEN-LAST:event_newCaseArticleListValueChanged

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
            java.util.logging.Logger.getLogger(GUIView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIView(control).setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NewestCasesTab;
    private javax.swing.JButton addContactButton;
    private javax.swing.JTextField addContactEmailField;
    private javax.swing.JTextField addContactNameField;
    private javax.swing.JTextField addContactPhoneField;
    private javax.swing.JCheckBox addToMyCasesCheckBox;
    private javax.swing.JPanel adminPanel;
    private javax.swing.JLabel adressCostumerLabel;
    private javax.swing.JButton allEmployeesAddButton;
    private javax.swing.JScrollPane allEmployeesScrollPane;
    private javax.swing.JTextField articleAmountField;
    private javax.swing.JTextField articleDescriptionEditField;
    private javax.swing.JPanel articleDisplayPanel;
    private javax.swing.JTextField articleLocationField;
    private javax.swing.JTextField articleTypeSField;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JLabel caseBeginDateLabel;
    private javax.swing.JTextField caseCreationNameField;
    private javax.swing.JTextField caseDescriptionEditPanel;
    private javax.swing.JTabbedPane caseDisplayPane;
    private javax.swing.JLabel caseEndedDateLabel;
    private javax.swing.JCheckBox caseFinishedCheckBox;
    private javax.swing.JPanel caseHandlingPanel;
    private javax.swing.JTextField caseNameEditField;
    private javax.swing.JTextField caseNameSField;
    private javax.swing.JTextField caseNmbEditPanel;
    private javax.swing.JTextField caseNmbSField;
    private javax.swing.JButton caseResponsibleRemoveButton;
    private javax.swing.JScrollPane caseResponsibleScrollPane;
    private javax.swing.JButton caseSearchButton;
    private javax.swing.JButton changeDbButton;
    private javax.swing.JLabel contactPersonCostumerLabel;
    private javax.swing.JScrollPane costScrollSearch;
    private javax.swing.JLabel costumerNameLabel;
    private javax.swing.JComboBox<String> costumerTypeBox;
    private javax.swing.JTextField createArticleConsNmbField;
    private javax.swing.JButton createCaseAddTaskButton;
    private javax.swing.JTextArea createCaseArticleDescriptionArea;
    private javax.swing.JComboBox<String> createCaseTaskBox;
    private javax.swing.JList<String> createCaseTaskList;
    private javax.swing.JButton createCasebutton;
    private javax.swing.JButton createNewEmployeeButton;
    private javax.swing.JButton createTaskNameButton;
    private javax.swing.JTextField customerListAddressField;
    private javax.swing.JTextField customerListContactfield;
    private javax.swing.JTextField customerListEmailField;
    private javax.swing.JTextField customerListNameField;
    private javax.swing.JPanel customerListPanel;
    private javax.swing.JTextField customerListPhoneField;
    private javax.swing.JScrollPane customerListScrollPane;
    private javax.swing.JCheckBox dbInfoCheckBox;
    private javax.swing.JTextField dbPasswordField;
    private javax.swing.JTextField dbUrlField;
    private javax.swing.JTextField dbUserField;
    private javax.swing.JButton deactiveEmployee;
    private javax.swing.JButton editAddTaskButton;
    private javax.swing.JList editArticleTaskList;
    private javax.swing.JButton editCaseLogButton;
    private javax.swing.JTextField editCaseOfferNmbField;
    private javax.swing.JPanel editCasePanel;
    private javax.swing.JTextField editEmailField;
    private javax.swing.JButton editPanelBackButton;
    private javax.swing.JTextField editPhoneField;
    private javax.swing.JButton editTaskRefreshButton;
    private javax.swing.JComboBox editTaskStatusBox;
    private javax.swing.JLabel emailCostumerLabel;
    private javax.swing.JTextField employeeLastUpdateField;
    private javax.swing.JPanel employeeListPanel;
    private javax.swing.JPanel employeeSettingsPanel;
    private javax.swing.JCheckBox existingCostumerCheckBox;
    private javax.swing.JTextField findCostumerField;
    private javax.swing.JCheckBox findCustomerEditableCheckBox;
    private javax.swing.JButton findCustomerSaveInfoButton;
    private javax.swing.JPanel finishedCasesPanel;
    private javax.swing.JScrollPane finishedCasesScrollPane;
    private javax.swing.JPanel finishedCasesTab;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JList jList5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField konsNmbField;
    private javax.swing.JTextField lastUpdatedField;
    private javax.swing.JCheckBox moreArticlesCheckBox;
    private javax.swing.JPanel myCasesPanel;
    private javax.swing.JScrollPane myCasesScrollPane;
    private javax.swing.JPanel myCasesTab;
    private javax.swing.JButton newArticleButton;
    private javax.swing.JTextField newArticleMuseumsNmbField;
    private javax.swing.JTextField newArticleNameField;
    private javax.swing.JTextField newArticleTypeField;
    private javax.swing.JTextField newCaseAcroField;
    private javax.swing.JTextField newCaseAddressField;
    private javax.swing.JList<String> newCaseArticleList;
    private javax.swing.JTextField newCaseContactField;
    private javax.swing.JTextField newCaseDescription;
    private javax.swing.JTextField newCaseEmailField;
    private javax.swing.JTextField newCaseMuseumNmbField;
    private javax.swing.JTextField newCaseNameField;
    private javax.swing.JTextField newCaseOfferNmbField;
    private javax.swing.JPanel newCasePanel;
    private javax.swing.JTextField newCasePhoneField;
    private javax.swing.JList<String> newContactList;
    private javax.swing.JTextField newEmployeeEmailField;
    private javax.swing.JTextField newEmployeeFirstnameField;
    private javax.swing.JTextField newEmployeeInitialsField;
    private javax.swing.JTextField newEmployeeLastnameField;
    private javax.swing.JTextField newEmployeePassword1Field;
    private javax.swing.JTextField newEmployeePassword2Field;
    private javax.swing.JTextField newEmployeePhoneField;
    private javax.swing.JComboBox newEmployeeTypeBox;
    private javax.swing.JTextField newEmployeeUsernameField;
    private javax.swing.JTextField newPasswordField1;
    private javax.swing.JTextField newPasswordField2;
    private javax.swing.JTextField newUsernameField;
    private javax.swing.JPanel newestCasesPanel;
    private javax.swing.JScrollPane newestCasesScrollPane;
    private javax.swing.JLabel passwordMatchLabel;
    private javax.swing.JLabel phoneCostumerLabel;
    private javax.swing.JButton resetPasswordButton;
    private javax.swing.JButton saveChangesEditCaseButton;
    private javax.swing.JButton saveLoginInfoButton;
    private javax.swing.JButton savePersonalInfoButton;
    private javax.swing.JTextField searchOfferNmbField;
    private javax.swing.JButton selectCostumerButton;
    private javax.swing.JPanel showAllCustomerPanel;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextArea taskDescriptionArea;
    // End of variables declaration//GEN-END:variables
}
