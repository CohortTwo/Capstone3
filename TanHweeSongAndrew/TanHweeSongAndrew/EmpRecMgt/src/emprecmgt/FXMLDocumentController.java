package emprecmgt;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
/**
 *
 * @author Tan Hwee Song Andrew 652Z
 */



public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txteid;
    @FXML
    private TextField txtename;    
    @FXML
    private TextField txtejob;
    @FXML
    private TextField txtesal;
    @FXML
    private TextField txtecomm;
    @FXML
    private DatePicker txtedoj;
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
    private TableColumn<Employees, String> coledoj;
    @FXML
    private ChoiceBox cb1;
    @FXML
    private ChoiceBox cb2;
    @FXML
    private Label rec_counter;
    
   
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
        
        if (event.getSource() == btnaddemp) {
            checkNcreateRec();
        } else if (event.getSource() == btnupdateemp) {
            checkNeditRec();
        } else if (event.getSource() == btnremoveemp) {
            checkNDeleteRec();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        displayEmpRecords();
    }    
    
   
      public Connection getConnection() {
        Connection conn;
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager
                    //"jdbc:oracle:thin:@localhost:1521:prod", "andrew", "andrew"
                    .getConnection("jdbc:oracle:thin:@localhost:1521:prod", "andrew", "andrew");
            return conn;
        } catch (Exception e) {
            return null;
        }

    }

    public ObservableList<Employees> getEmployeeList() {
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select * from employees";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            Employees emp;
            while (rs.next()) {
                emp = new Employees(rs.getInt("employee_id"), rs.getString("first_name"), rs.getString("job_id"), rs.getInt("manager_id"), rs.getInt("department_id"), rs.getInt("salary"), rs.getInt("commission_pct"), rs.getDate("hire_date"));
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
        colecomm.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("comm"));
        colesal.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("sal"));
        
        tblvemp.setItems(listemp);
        // total number of records in database
        rec_counter.setText("Number of records : "+listemp.size());
        //check distinct mgr id
        for (int i = 0; i < listemp.size(); i++) {
            int a = listemp.get(i).getMgr();
            if (cb1.getItems().contains(a)) {
//                System.out.println("duplicates");
            } else {
                cb1.getItems().add(a);
            }
        }
                 
        //check distinct dept_id 
        for (int i = 0; i < listemp.size(); i++) {
            int a = listemp.get(i).getEdeptno();
            if (cb2.getItems().contains(a)) {
//                System.out.println("duplicates");
            } else {
                cb2.getItems().add(a);
            }
        }
        
    }

    private void checkNcreateRec(){
            Alert a = new Alert(AlertType.NONE); 
            a.setAlertType(AlertType.CONFIRMATION);
            a.setContentText("Confirmation to add record");
            a.showAndWait().filter(response -> response == ButtonType.OK)
            .ifPresent(response -> createRecords());
            
    }
    
    private void checkNeditRec(){
            Alert a = new Alert(AlertType.NONE); 
            a.setAlertType(AlertType.CONFIRMATION);
            a.setContentText("Confirmation to edit record");
            a.showAndWait().filter(response -> response == ButtonType.OK)
            .ifPresent(response -> editRecord());
            
    }
    private void checkNDeleteRec(){
            Alert a = new Alert(AlertType.NONE); 
            a.setAlertType(AlertType.CONFIRMATION);
            a.setContentText("Confirmation to delete record");
            a.showAndWait().filter(response -> response == ButtonType.OK)
            .ifPresent(response -> removeRecord());
            //createRecords(); 
    }
    
    private void createRecords() {
        

            String query = "INSERT into employees (employee_id,first_name,job_id,manager_id,salary,commission_pct,department_id,last_name,email,hire_date) VALUES (" + txteid.getText() + ",'" + txtename.getText() + "','" + txtejob.getText() + "',"
                + cb1.getValue() + "," + txtesal.getText() + "," + txtecomm.getText() + "," + cb2.getValue() + "," + "'Tan'" + ","  +"123456" +","+ "TO_DATE( '" + txtedoj.getValue()+"', 'YYYY-MM-DD')" + ")";
            executeQuery(query);
            displayEmpRecords();
            System.out.println(txtedoj.getValue());
 
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
        String query = "UPDATE employees set first_name = '" + txtename.getText()
                + "', job_id ='" + txtejob.getText()
                + "', manager_id =" + cb1.getValue()
                + ", salary =" + txtesal.getText()
                + ", commission_pct =" + txtecomm.getText()
                + ", department_id =" + cb2.getValue()
                + " where employee_id =" + txteid.getText() + "";
        executeQuery(query);
      System.out.println(query);
        displayEmpRecords();
    }

    private void removeRecord() {
        String query = "delete from employees where employee_id =" + txteid.getText();
        executeQuery(query);
        displayEmpRecords();
    }

    
    
    @FXML
    private void handleButAct(MouseEvent event) throws Exception {
          System.out.println("hello");
          if (tblvemp.getSelectionModel().getSelectedItem() != null) {
        Employees emp = tblvemp.getSelectionModel().getSelectedItem();
        txteid.setText(String.valueOf(emp.getEid()));
        txtename.setText(emp.getEname());
        txtejob.setText(emp.getJob());
        cb1.setValue(""+emp.getMgr());
        cb2.setValue(""+emp.getEdeptno());
        txtecomm.setText(""+emp.getComm());
        txtesal.setText(""+emp.getSal());
        //txtedoj.setText(emp.getHdate().toLocalDate());
          }
    }

    private void displayReports(ActionEvent event) {
        System.out.println("Reports");
        
    }
    
}
