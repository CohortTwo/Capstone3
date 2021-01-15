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
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ChoiceBox;
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

/**
 *
 * @author blueg
 */
public class EmpMainController implements Initializable {
    
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private Label labelempno;
    @FXML
    private Label labelename;
    @FXML
    private Label labeljob;
    @FXML
    private Label labelmgr;
    @FXML
    private Label labelhiredate;
    @FXML
    private Label labelsal;
    @FXML
    private Label labelcomm;
    @FXML
    private Label labeldeptno;
    @FXML
    private TextField tfempno;
    @FXML
    private TextField tfename;
    @FXML
    private ChoiceBox tfjob;
    @FXML
    private ChoiceBox tfmgr;
    @FXML
    private DatePicker tfhiredate;
    @FXML
    private TextField tfsal;
    @FXML
    private TextField tfcomm;
    @FXML
    private ChoiceBox tfdeptno;
    @FXML
    private TableColumn<Employees, Integer> tcolempno;
    @FXML
    private TableColumn<Employees, String> tcolename;
    @FXML
    private TableColumn<Employees, String> tcoljob;
    @FXML
    private TableColumn<Employees, Integer> tcolmgr;
    @FXML
    private TableColumn<Employees, String> tcolhiredate;
    @FXML
    private TableColumn<Employees, Integer> tcolsal;
    @FXML
    private TableColumn<Employees, Integer> tcolcomm;
    @FXML
    private TableColumn<Employees, Integer> tcoldeptno;
    @FXML
    private Button add;
    @FXML
    private Button edit;
    @FXML
    private Button remove;
    @FXML
    private TableView<Employees> tableemp;
    @FXML
    private Button getreports;
    @FXML
    private MenuBar menubar;
    @FXML
    private Menu menufile;
    @FXML
    private MenuItem fmclose;
    @FXML
    private Menu menuedit;
    @FXML
    private Menu menuhelp;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource()==add){
            addRecord();
        }else if(event.getSource()==remove){
            removeRecord();
        }else if(event.getSource()==edit){
            editRecord();
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        displayEmpRecords();
        loadjobs();
        loadmgr();
        loaddeptno();
    }    
    
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:prod", "xx", "xx");
            return conn;
        }catch(Exception e){
            return null;
        }
    }
    
    public ObservableList<Employees> getEmployeeList() {
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "select * from emp";
        Statement stmt;
        ResultSet rs;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            Employees emp;
            while (rs.next()) {
                String strDate = dateFormat.format(rs.getDate("hiredate"));
                //System.out.println(strDate);
                emp = new Employees(rs.getInt("empno"), rs.getString("ename"), 
                        rs.getString("job"), rs.getInt("mgr"), 
                        strDate, rs.getInt("sal"), 
                        rs.getInt("comm"), rs.getInt("deptno"));
                emplist.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return emplist;
    }

    public void displayEmpRecords() {
        ObservableList<Employees> listemp = getEmployeeList();
        tcolempno.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("empno"));
        tcolename.setCellValueFactory(new PropertyValueFactory<Employees, String>("ename"));
        tcoljob.setCellValueFactory(new PropertyValueFactory<Employees, String>("job"));
        tcolmgr.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("mgr"));
        tcolhiredate.setCellValueFactory(new PropertyValueFactory<Employees, String>("hiredate"));
        tcolsal.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("sal"));
        tcolcomm.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("comm"));
        tcoldeptno.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("deptno"));

        tableemp.setItems(listemp);
        

    }
    
    
    private ObservableList loadjobs(){
        list.remove(list);

        list.addAll("PRESIDENT", "MANAGER", "ANALYST","CLERK","SALESMAN");
        tfjob.getItems().addAll(list);
        return list;
    }
    
    private ObservableList loadmgr(){
        list.removeAll(list);

        list.addAll(7839,7566,7902,7698,7788,7782);
        tfmgr.getItems().addAll(list);
        return list;
    }
    
    private ObservableList loaddeptno(){
        list.removeAll(list);

        list.addAll(10,20,30,40);
        tfdeptno.getItems().addAll(list);
        return list;
    }
    
    private void updateQuery(String query) {
        Connection conn = getConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addRecord(){
        String strDate = tfhiredate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String query = "insert into emp (empno,ename,job,mgr,hiredate,sal,comm,deptno) "
                + "values (" + tfempno.getText() + ",'" + tfename.getText() + "','" 
                + tfjob.getValue() + "',"
                + tfmgr.getValue() + ",to_date('" + strDate + "','dd-mm-yyyy'),"
                + tfsal.getText() + "," 
                + tfcomm.getText() + ","+ tfdeptno.getValue() + ")";
        updateQuery(query);
        displayEmpRecords();
    }
    
    private void removeRecord() {
        String query = "delete from emp where empno =" + tfempno.getText();
        updateQuery(query);
        displayEmpRecords();
    }
    
    private void editRecord() {
        String strDate = tfhiredate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String query = "update emp set ename = '" + tfename.getText()
                + "', job ='" + tfjob.getValue()
                + "', mgr =" + tfmgr.getValue()
                + ", hiredate = " + "to_date('" + strDate + "','dd-mm-yyyy')"
                + ", sal =" + tfsal.getText()
                + ", comm =" + tfcomm.getText()
                + ", deptno =" + tfdeptno.getValue()
                + " where empno =" + tfempno.getText() + "";
        updateQuery(query);
        displayEmpRecords();
    }
    

    @FXML
    private void handleMouseClick(MouseEvent event) {        
        
        Employees emp = tableemp.getSelectionModel().getSelectedItem();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(emp.getHiredate(), formatter);       
        
        tfempno.setText(""+emp.getEmpno());
        tfename.setText(emp.getEname());
        tfjob.setValue(emp.getJob());
        tfmgr.setValue(emp.getMgr());
        tfhiredate.setValue(localDate);
        tfsal.setText(""+emp.getSal());
        tfcomm.setText(""+emp.getComm());
        tfdeptno.setValue(emp.getDeptno());
    }

    @FXML
    private void changeScene(ActionEvent event) throws IOException {
        //navigate from one scene to another with instance referencing the values
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpMain_1.fxml"));
        Parent root = loader.load();
       
        EmpMainController1 secondwindow = loader.getController();
        //secondwindow.showBar();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Employees Management System");
        stage.show();
        
        Stage stage1 = (Stage) getreports.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    
}
