package emprecordmanagement;

import java.net.URL;
import java.sql.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javafx.scene.control.DatePicker;
import javax.swing.JFrame;


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
    private TextField txtejob;
    @FXML
    private Button btnaddemp;
    @FXML
    private Button btnupdateemp;
    @FXML
    private Button btnremoveemp;
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
    private TableColumn<Employees, Date> coledoj;    /////
    @FXML
    private TextField txtecomm;
    @FXML
    private Button btnexit;
    @FXML
    private DatePicker dp;
    @FXML
    private Label dplbl;
   
    

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnaddemp) {
            createRecords();
        } else if (event.getSource() == btnupdateemp) {
            editRecord();
        } else if (event.getSource() == btnremoveemp) {
            removeRecord();
        }
          else if (event.getSource() == btnexit) {   /////////
            exitRecord();
        }
        
    }
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayEmpRecords();
    }

    public Connection getConnection() {
        
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
                    System.out.println("Connected to database");
            return conn;
        } catch (Exception e) {
            return null;
        }

    }

    public ObservableList<Employees> getEmployeeList() {
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select * from EMP";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            Employees emp;
            while (rs.next()) {
                emp = new Employees(rs.getInt("empno"), rs.getString("ename"), rs.getString("job"), rs.getInt("mgr"), rs.getInt("deptno"), rs.getInt("sal"), rs.getInt("comm"), rs.getDate("hdate"));
                emplist.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return emplist;
    }

  
    
    
    
    
    public void displayEmpRecords() {
        ObservableList<Employees> listemp = getEmployeeList();
        coleid.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("eid"));
        colename.setCellValueFactory(new PropertyValueFactory<Employees, String>("ename"));
        colemgr.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("mgr"));
        colejob.setCellValueFactory(new PropertyValueFactory<Employees, String>("job"));
        coledept.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("edeptno"));
        coledoj.setCellValueFactory(new PropertyValueFactory<Employees, Date>("hdate"));
        colecomm.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("comm"));
        colesal.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("sal"));

        tblvemp.setItems(listemp);

    }    
     
    
        
        
    

    private void createRecords() {
        String query = "INSERT into EMP (empno,ename,job,mgr,sal,comm,deptno,hdate) VALUES (" + txteid.getText() + ", '" + txtename.getText() + "', '" + txtejob.getText() + "', "
                + txtmemgr.getText() + "," + txtesal.getText() + "," + txtecomm.getText() + "," + txtedept.getText() +" ,TO_DATE('"+ dp.getValue()+ "', 'YYYY-MM-DD' ))";
        executeQuery(query);
        displayEmpRecords();
        
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editRecord() {
        System.out.println("Hello editing");
        String query = "UPDATE EMP set ename = '" + txtename.getText()
                + "', job ='" + txtejob.getText()
                + "', mgr =" + txtmemgr.getText()
                + ", sal =" + txtesal.getText()
                + ", comm =" + txtecomm.getText()
                + ", deptno =" + txtedept.getText()
                + " where empno =" + txteid.getText() + "";
        executeQuery(query);
      System.out.println(query);
        displayEmpRecords();
    }

    private void removeRecord() {
        String query = "delete from EMP where empno =" + txteid.getText();
        executeQuery(query);
        displayEmpRecords();
    }

    ///////////////////////////////
    private void exitRecord() {
        ///////////////
        JFrame frame = new JFrame("Exit");
        if(JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION)
        {
        /////////////
        System.exit(0);
    }
    }
    
    
    
    
    
    
    
    
//    @FXML
//    private void handleButtonAction(MouseEvent event) {
//          System.out.println("hello");
//        Employees emp = tblvemp.getSelectionModel().getSelectedItem();
//        txteid.setText(""+emp.getEid());
//        txtename.setText(emp.getEname());
//        txtejob.setText(emp.getJob());
//        txtmemgr.setText(""+emp.getMgr());
//        txtedept.setText(""+emp.getEdeptno());
//        txtecomm.setText(""+emp.getComm());
//        txtesal.setText(""+emp.getSal());
//    }
}