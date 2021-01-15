/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emprecordmanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sridh
 */
public class EmpMainController implements Initializable {

    @FXML
    private TextField txteid;
    @FXML
    private TextField txtename;
    @FXML
    private TextField txtmemgr;
    @FXML
    private TextField txtesal;
    @FXML
    private TextField txtedept;
    @FXML
    private DatePicker txtedoj;
    @FXML
    private TextField txtejob;
    @FXML
    private Button btnaddemp;
    @FXML
    private Button btnupdateemp;

    @FXML
    private TableView<Employees> tblvemp;
    @FXML
    private TableColumn<Employees, Integer> coleid;
    @FXML
    private TableColumn<Employees, String> colename;
    @FXML
    private TableColumn<Employees, Integer> colemgr;
    @FXML
    private TableColumn<Employees, String> colejob;
    @FXML
    private TableColumn<Employees, Integer> colesal;
    @FXML
    private TableColumn<Employees, Integer> colecomm;
    @FXML
    private TableColumn<Employees, Integer> coledept;
    @FXML
    private TableColumn<Employees, String> coledoj;
    @FXML
    private TextField txtecomm;
    @FXML
    private TableColumn<?, ?> coldelete;
    @FXML
    private TableColumn<?, ?> coledit;
    @FXML
    private TableColumn<?, ?> colcheckbox;
    @FXML
    private CheckBox checkboxall;
    @FXML
    private AnchorPane rootpane;
    @FXML
    private Button btnchart;
    @FXML
    private ChoiceBox choicebox;
    private final ArrayList<Depts> depts = new ArrayList<>();
    private final ArrayList<Employees> managers = new ArrayList<>();
    @FXML
    private TableColumn<?, ?> coldept;
    @FXML
    private ChoiceBox choiceboxmgr;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnaddemp) {
            createRecords();
        } else if (event.getSource() == btnupdateemp) {
            editRecord();
        } 
    }
        @FXML
        
    public void sceneButtonAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chart.fxml"));
        Parent root = loader.load();
        ChartController chart = loader.getController();
      //  chart.myFunction(txtCalculator.getText());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add("resources/css/emp.css");
        ObservableList<Employees> listemp = getCheckedList();
        chart.ShowBar(listemp);
        stage.setTitle("Chart");
        stage.show();

// Closing the Current Scene.
        
        Stage stage1 = (Stage)  rootpane.getScene().getWindow();
        stage1.close();


    }
    
    @FXML
    private void checkBoxAll() {

        System.out.println(checkboxall);
        ObservableList<Employees> listemp = getEmployeeList();
        if (checkboxall.isSelected()) {

            listemp.forEach(Employees -> Employees.getCb().setSelected(true));
        } else {
            listemp.forEach(Employees -> Employees.getCb().setSelected(false));
        }
        tblvemp.setItems(listemp);
    }
    
    private ObservableList<Employees> getCheckedList(){
        ObservableList<Employees> listemp = tblvemp.getItems();
        return listemp.filtered(Employees -> Employees.getCb().isSelected());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  loadData();
        displayEmpRecords();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
            return conn;
        } catch (SQLException e) {
            return null;
        }

    }

    public ObservableList<Employees> getEmployeeList() {
        
  
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        
        String query = "Select empno, ename, Job, Mgr, deptno, sal, comm, TO_CHAR(hiredate, 'dd/mm/yyyy') hiredate from emp ";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            Employees emp;
            Button btndelete, btnedit;
            CheckBox cb;
            ImageView iv1, iv2;
            while (rs.next()) {

                emp = new Employees(rs.getInt("empno"), rs.getString("ename"), rs.getString("Job"), rs.getInt("Mgr"), rs.getInt("deptno"), rs.getInt("sal"), rs.getInt("comm"), rs.getString("hiredate"));
                
//  Delete button add action 
                btndelete = new Button("");
                iv1 = new ImageView(new Image("resources/images/delete.png"));
                iv1.setFitHeight(26);
                iv1.setFitWidth(26);
                btndelete.setGraphic(iv1);
                btndelete.setAccessibleText(String.valueOf(rs.getInt("empno")));
                btndelete.setOnMouseClicked(handler);
                emp.setDelete(btndelete);
                
 //  Edit button add action 
                btnedit = new Button("");
                iv2 = new ImageView(new Image("resources/images/edit.png"));
                iv2.setFitHeight(26);
                iv2.setFitWidth(26);
                btnedit.setGraphic(iv2);
                btnedit.setAccessibleText(String.valueOf(rs.getInt("empno")));
                btnedit.setOnMouseClicked(edithandler);
                emp.setEdit(btnedit);
                
                String department = findDnameByDeptNo(depts, emp.getEdeptno());
                emp.setDept(department);

// CheckBox
                cb = new CheckBox();
                emp.setCb(cb);
                
                emplist.add(emp);
                // create manager list with job title as Manager
                if("Manager".equals(emp.getJob())) managers.add(emp);
            }
        } catch (SQLException ex) {
            System.out.println("getEmployeeList error");
        } finally{
                  loadData();
        }
            
        return emplist;
        
    }
   //Creating EventHandler   
    EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {  
  
        @Override  
        public void handle(MouseEvent event) {  
            Button xx = (Button) event.getSource();     
            removeRecordbyID(xx.getAccessibleText());
        }  
          
    };  
       //Creating EventHandler   
    EventHandler<MouseEvent> edithandler = new EventHandler<MouseEvent>() {  
  
        @Override  
        public void handle(MouseEvent event) {

            Button xx = (Button) event.getSource();
            int id = Integer.parseInt(xx.getAccessibleText());

            ObservableList<Employees> listemp = getEmployeeList();
            FilteredList<Employees> emp = listemp.filtered(Employees -> Employees.getEid() == id);

            txteid.setText("" + emp.get(0).getEid());
            txtename.setText(emp.get(0).getEname());
            txtejob.setText(emp.get(0).getJob());
            txtmemgr.setText("" + emp.get(0).getMgr());
            txtedept.setText("" + emp.get(0).getEdeptno());
            txtecomm.setText("" + emp.get(0).getComm());
            txtesal.setText("" + emp.get(0).getSal());
            String department = findDnameByDeptNo(depts, emp.get(0).getEdeptno());
            choicebox.setValue(department);
            String xmanager =findMgrNameByEmpNo(managers, emp.get(0).getMgr());
            
            choiceboxmgr.setValue(xmanager);
            int yyyy = Integer.parseInt(emp.get(0).getHdate().substring(6, 10));
            int mm = Integer.parseInt(emp.get(0).getHdate().substring(3, 5));
            int dd = Integer.parseInt(emp.get(0).getHdate().substring(0, 2));
            LocalDate date1 = LocalDate.of(yyyy, mm, dd);
            txtedoj.setValue(date1);
        }
    };
    public void displayEmpRecords() {
        ObservableList<Employees> listemp = getEmployeeList();
        coleid.setCellValueFactory(new PropertyValueFactory<>("eid"));
        colename.setCellValueFactory(new PropertyValueFactory<>("ename"));
        colemgr.setCellValueFactory(new PropertyValueFactory<>("mgr"));
        colejob.setCellValueFactory(new PropertyValueFactory<>("job"));
        coledept.setCellValueFactory(new PropertyValueFactory<>("edeptno"));
        coledoj.setCellValueFactory(new PropertyValueFactory<>("hdate"));
        colecomm.setCellValueFactory(new PropertyValueFactory<>("comm"));
        colesal.setCellValueFactory(new PropertyValueFactory<>("sal"));
        coldelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        coledit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        colcheckbox.setCellValueFactory(new PropertyValueFactory<>("cb"));
        coldept.setCellValueFactory(new PropertyValueFactory<>("dept"));
    
        tblvemp.setItems(listemp);

    }

    private void createRecords() {
        int nextNo = getNextEmpNo();
        int edeptno = findDeptNoByDname(depts, (String) choicebox.getValue());
        int mgr = findMgrEmpNoByMname(managers, (String) choiceboxmgr.getValue());
        String query = "INSERT into emp (empno,ename,job,mgr,sal,comm,deptno,hiredate) VALUES (" + nextNo + ",'" + txtename.getText() + "','" + txtejob.getText() + "',"
                + mgr + "," + txtesal.getText() + ","
                + txtecomm.getText() + "," + edeptno+ ",date'" + txtedoj.getValue() + "')";

        executeQuery(query);
         clearScreen();
        displayEmpRecords();
    }
    
    private int getNextEmpNo(){
        Connection conn = getConnection();
        String query = "Select max(empno) nextNo from emp";
        Statement stmt;
        ResultSet rs;
        int nextNo = 1;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                nextNo = rs.getInt("nextNo") + 1;
            }
        } catch (SQLException ex) {
            System.out.println("getNextEmpNo error");
        }
        return nextNo;
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("executeQuery error");
        }
        
    }

    private void editRecord() {
        System.out.println("Hello editing");
        int edeptno = findDeptNoByDname(depts, (String) choicebox.getValue());
        int mgr = findMgrEmpNoByMname(managers, (String) choiceboxmgr.getValue());
        String query = "UPDATE emp set ename = '" + txtename.getText()
                + "', job ='" + txtejob.getText()
                + "', mgr =" + mgr
                + ", sal =" + txtesal.getText()
                + ", comm =" + txtecomm.getText()
                + ", deptno =" + edeptno
                                + ", hiredate =" + "date '" + txtedoj.getValue()
                + "' where empno =" + txteid.getText() + "";
        executeQuery(query);
      System.out.println(query);
      clearScreen();
        displayEmpRecords();
    }

    private void removeRecordbyID(String id) {
        String query = "delete from emp where empno =" + id;
        executeQuery(query);
        displayEmpRecords();
    }

    public void clearScreen()  {
        txteid.setText("");
        txtename.setText("");
        txtejob.setText("");
        txtmemgr.setText("");
        txtedept.setText("");
        txtecomm.setText("");
        txtesal.setText("");
        LocalDate date1 = null;
        txtedoj.setValue(date1);
    }
    
    public void loadData() {

        Connection conn = getConnection();
        String query = "Select deptno, dname, loc from Dept";
        Statement stmt;
        ResultSet rs;
        choicebox.getItems().clear();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                depts.add(new Depts(rs.getInt("deptno"), rs.getString("dname"), rs.getString("loc")));
                choicebox.getItems().add(rs.getString("dname"));
            }
        } catch (SQLException ex) {
            System.out.println("loadData error");
        }
        choiceboxmgr.getItems().clear();
        managers.forEach(Employees -> choiceboxmgr.getItems().add(Employees.getEname()));
        System.out.println(managers);
    }
    public int findDeptNoByDname(ArrayList<Depts> depts, String dname) {
        int i = 0;
        for (Depts dept : depts) {

            if (dept.getDname() == null ? dname == null : dept.getDname().equals(dname)) {
                i = dept.getDeptno();
            }
        }
        return i;
    }
    public String findDnameByDeptNo(ArrayList<Depts> depts, int deptno){
        String name = "";
        for (Depts dept : depts) {

            if(dept.getDeptno()== deptno){
                name = dept.getDname();
            }
        }
        return name;
    }
    public String findMgrNameByEmpNo(ArrayList<Employees> employees, int mgr){
        String mname = "";
        for (Employees employee : employees ) {

            if(employee.getEid()== mgr){
                mname = employee.getEname();
            }
        }
        return mname;
    }
    public int findMgrEmpNoByMname(ArrayList<Employees> employees, String mgr) {
        int i = 0;
        for (Employees employee : employees ) {

            if (employee.getEname() == null ? mgr == null : employee.getEname().equals(mgr)) {
                i = employee.getEid();
            }
        }
        return i;
    }
}
