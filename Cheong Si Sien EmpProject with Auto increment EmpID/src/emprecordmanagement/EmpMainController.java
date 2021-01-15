package emprecordmanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TableColumn<Employees, Double> colecomm;
    @FXML
    private TableColumn<Employees, Integer> coledept;
    @FXML
    private TableColumn<Employees, String> coledoj;
    @FXML
    private TextField txtecomm;
    @FXML
    private DatePicker Datepick;
    @FXML
    private Label labelkey;
    @FXML
    private Button confirmadd;
    @FXML
    private Button confirmedit;
    @FXML
    private MenuBar quitmenu;
    @FXML
    private Menu quitbutton;
    @FXML
    private Menu enquirybutton;

    @FXML
    private MenuItem managerlist;

    @FXML
    private void handleMenuAction(ActionEvent event) throws IOException {

        System.out.println("1 came here scene");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MgrMain.fxml"));
        loader.load();
        MgrMainController temp1 = loader.getController();

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setScene(new Scene(p));
        stage.setTitle("Employee Management");
        stage.show();
        Stage stage1 = (Stage) btnaddemp.getScene().getWindow();
        stage1.close();

    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        System.out.println("2 came here scene");
        System.out.println("hello mouse clicked");

        Employees emp = tblvemp.getSelectionModel().getSelectedItem();
        txteid.setText("" + emp.getEid());
        txtename.setText(emp.getEname());
        txtejob.setText(emp.getJob());
        txtmemgr.setText("" + emp.getMgr());
        txtedept.setText("" + emp.getEdeptno());
        txtecomm.setText("" + emp.getComm());
        txtesal.setText("" + emp.getSal());
        String date = emp.getHdate().substring(0, 11);
        System.out.println("date is :" + date);

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        System.out.println("yyyymmss is" + year + month + day);
        Datepick.setValue(LocalDate.of(year, month, day));
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("3 came here scene");
        if (event.getSource() == quitbutton) {
            Platform.exit();
        } else if (event.getSource() == btnaddemp) {
            dataentry(0);
        } else if (event.getSource() == confirmadd) {
            System.out.println("add confirm clicked");
            createRecords();
        } else if (event.getSource() == confirmedit) {
            System.out.println("edit confirm clicked");
            editRecord();
        } else if (event.getSource() == btnupdateemp) {
            dataentry(1);
        } else if (event.getSource() == btnremoveemp) {
            System.out.println("Come to remove");
            removeRecord();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayEmpRecords();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
            return conn;
        } catch (Exception e) {
            return null;
        }

    }

    public ObservableList<Employees> getEmployeeList() {
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select * from emp";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            Employees emp;
            while (rs.next()) {
                // emp = new Employees(rs.getInt("empno"), rs.getString("ename"), rs.getString("Job"), rs.getInt("Mgr"), rs.getInt("deptno"), rs.getInt("sal"), rs.getInt("comm"), rs.getString("hiredate").substring(0,10));
                emp = new Employees(rs.getInt("empno"), rs.getString("ename"), rs.getString("Job"), rs.getInt("Mgr"), rs.getInt("deptno"), rs.getInt("sal"), rs.getDouble("comm"), rs.getString("hiredate"));
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
        coledoj.setCellValueFactory(new PropertyValueFactory<Employees, String>("hdate"));
        colecomm.setCellValueFactory(new PropertyValueFactory<Employees, Double>("comm"));
        colesal.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("sal"));

        tblvemp.setItems(listemp);
        coleid.setSortType(TableColumn.SortType.DESCENDING);
        tblvemp.getSortOrder().addAll(coleid);

    }

    private void createRecords() {
        // Employee ID is AUTO INCREMENT FIELD IN ORACLE DATABASE
        labelkey.setVisible(false);
        txtename.setEditable(false);
        txteid.setEditable(false);
        txtmemgr.setEditable(false);
        txtesal.setEditable(false);
        txtecomm.setEditable(false);
        txtedept.setEditable(false);
        Datepick.setEditable(false);
        Datepick.getEditor().setDisable(true);
        txtejob.setEditable(false);
        labelkey.setVisible(false);
        txteid.setEditable(false);
        txteid.setText("REFER TO TABLE for the employee ID ===========>>>");
        confirmadd.setVisible(false);
        confirmadd.setDisable(true);

        System.out.println("come to add records module");

        String query = "INSERT into emp (ename,job,mgr,sal,comm,deptno,hiredate) VALUES (" + "'" + txtename.getText() + "','" + txtejob.getText() + "',"
                + txtmemgr.getText() + "," + txtesal.getText() + "," + txtecomm.getText() + "," + txtedept.getText() + ", TO_DATE('" + Datepick.getValue() + "', 'YYYY-MM-DD'))";

        System.out.println("query:" + query);
        executeQuery(query);
        displayEmpRecords();

        btnremoveemp.setDisable(false);
        btnupdateemp.setDisable(false);
        btnaddemp.setDisable(false);

    }

    private void dataentry(int x) {
        if (x == 0) {
            txteid.setText("0");
            confirmadd.setVisible(true);
            confirmadd.setDisable(false);
            labelkey.setVisible(true);
            txtename.setText("");

            txtmemgr.setText("");
            txtesal.setText("");
            txtecomm.setText("");
            txtedept.setText("");

            Datepick.setEditable(true);
            txtejob.setText("");
            txteid.setText("");

            Calendar cal = Calendar.getInstance();
            int mYear = cal.get(Calendar.YEAR);
            int mMonth = cal.get(Calendar.MONTH);
            int mDay = cal.get(Calendar.DAY_OF_MONTH);
            Datepick.setValue(LocalDate.of(mYear, mMonth + 1, mDay));
        } else {
            System.out.println("come to edit.. set labalkey visible false");
            labelkey.setVisible(false);
            confirmedit.setVisible(true);
            confirmedit.setDisable(false);
        }

        btnremoveemp.setDisable(true);
        btnupdateemp.setDisable(true);
        btnaddemp.setDisable(true);

        txtename.setEditable(true);
        txteid.setEditable(false);
        txtmemgr.setEditable(true);
        txtesal.setEditable(true);
        txtecomm.setEditable(true);
        txtedept.setEditable(true);
        Datepick.setEditable(true);
        txtejob.setEditable(true);
        txteid.setEditable(false);

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
//         labelkey.setVisible(false);
//          txtename.setEditable(false);
//          txteid.setEditable(false);
//          txtmemgr.setEditable(false);
//          txtesal.setEditable(false);
//          txtecomm.setEditable(false);
//          txtedept.setEditable(false);
//          Datepick.setEditable(false);
//          txtejob.setEditable(false);
//          txteid.setEditable(false);
//          txteid.setText("REFER TO TABLE for the employee ID ===========>>>");
        //         confirmadd.setVisible(false);
        //         confirmadd.setDisable(true);
        String query = "UPDATE emp set ename = '" + txtename.getText()
                + "', job ='" + txtejob.getText()
                + "', mgr =" + txtmemgr.getText()
                + ", sal =" + txtesal.getText()
                + ", comm =" + txtecomm.getText()
                + ", deptno =" + txtedept.getText()
                + ", hiredate = TO_DATE('" + Datepick.getValue() + "', 'YYYY-MM-DD')"
                + " where empno =" + txteid.getText() + "";
        System.out.println("editng query: " + query);
        if (!(txteid.getText().isEmpty()))
           executeQuery(query);
        System.out.println(query);

        confirmedit.setDisable(true);
        confirmedit.setVisible(true);
        btnremoveemp.setDisable(false);
        btnupdateemp.setDisable(false);
        btnaddemp.setDisable(false);

        displayEmpRecords();
    }

    private void removeRecord() {
        String query = "delete from emp where empno =" + txteid.getText();
        executeQuery(query);
        displayEmpRecords();
    }

}
