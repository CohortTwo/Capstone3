/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

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

/**
 *
 * @author User
 */
public class EmpMainController implements Initializable {

    @FXML
    private Label label;
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
    private TableView<EmployeeClass> tblvemp;
    @FXML
    private TableColumn<EmployeeClass, Integer> coleid;
    @FXML
    private TableColumn<EmployeeClass, String> colename;
    @FXML
    private TableColumn<EmployeeClass, Integer> colemgr;
    @FXML
    private TableColumn<EmployeeClass, String> colejob;
    @FXML
    private TableColumn<EmployeeClass, Integer> colesal;
    @FXML
    private TableColumn<EmployeeClass, Integer> colecomm;
    @FXML
    private TableColumn<EmployeeClass, Integer> coledept;
    @FXML
    private TableColumn<EmployeeClass, Date> coledoj;
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
        } else if (event.getSource() == btnexit) {   /////////
            exitRecord();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayEmpRecords();
    }

    public Connection getConnection() {

        try {
            Connection conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1522:orcl", "hr", "hr");
            System.out.println("You are connected to the database.");
            return conn;
        } catch (SQLException e) {
            return null;
        }
    }

    public ObservableList<EmployeeClass> getEmployeeList() {
        ObservableList<EmployeeClass> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select * from EMP";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(query);
            EmployeeClass emp;
            while (rs.next()) {
                emp = new EmployeeClass(rs.getInt("empno"), rs.getString("ename"), rs.getString("job"), rs.getInt("mgr"), rs.getInt("deptno"), rs.getInt("sal"), rs.getInt("comm"), rs.getDate("hdate"));
                emplist.add(emp);
            }
        } catch (SQLException ex) {
        }
        return emplist;
    }

    public void displayEmpRecords() {
        ObservableList<EmployeeClass> listemp = getEmployeeList();
        coleid.setCellValueFactory(new PropertyValueFactory<>("eid"));
        colename.setCellValueFactory(new PropertyValueFactory<>("ename"));
        colemgr.setCellValueFactory(new PropertyValueFactory<>("mgr"));
        colejob.setCellValueFactory(new PropertyValueFactory<>("job"));
        coledept.setCellValueFactory(new PropertyValueFactory<>("edeptno"));
        coledoj.setCellValueFactory(new PropertyValueFactory<>("hdate"));
        colecomm.setCellValueFactory(new PropertyValueFactory<>("comm"));
        colesal.setCellValueFactory(new PropertyValueFactory<>("sal"));

        tblvemp.setItems(listemp);

    }

    private void createRecords() {
        String query = "INSERT into EMP (empno,ename,job,mgr,sal,comm,deptno,hdate) VALUES (" + txteid.getText() + ", '" + txtename.getText() + "', '" + txtejob.getText() + "', "
                + txtmemgr.getText() + "," + txtesal.getText() + "," + txtecomm.getText() + "," + txtedept.getText() + " ,TO_DATE('" + dp.getValue() + "', 'YYYY-MM-DD' ))";
        executeQuery(query);
        displayEmpRecords();

    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
        }
    }

    private void editRecord() {
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

    private void exitRecord() {
        JFrame frame = new JFrame("Exit");
        if (JOptionPane.showConfirmDialog(frame, "Confirm exit?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
            System.exit(0);
        }
    }
}
