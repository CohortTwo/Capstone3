
package javafxconndbase;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
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

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txteid;
    @FXML
    private TextField txtefname;
    @FXML
    private TextField txtejob;
//    @FXML
//    private TextField txtmemgr;
    @FXML
    private TextField txtesal;
    @FXML
    private TextField txtedept;
    @FXML
    private TextField txtelname;
    @FXML
    private TextField txteemail;
    @FXML
    private TextField txtephone;
//    @FXML
//    private TextField txtehdate;
    @FXML
    private TextField txtecomm;
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
    private TableColumn<Employees, String> colefname;
    @FXML
    private TableColumn<Employees, String> colejob;
    @FXML
    private TableColumn<Employees, Integer> colemgr;
    @FXML
    private TableColumn<Employees, Integer> colesal;
    @FXML
    private TableColumn<Employees, Integer> coledept;
    @FXML
   private TableColumn<Employees, String> colelname;
    @FXML
   private TableColumn<Employees, String> coleemail;
    @FXML
   private TableColumn<Employees, String> colephone;
    @FXML
   private TableColumn<Employees, Date> colehdate;
    @FXML
   private TableColumn<Employees, Float> colecomm;
    @FXML
   private DatePicker txtehdate;
   
    @FXML
      private Button btn;
    @FXML
      private TextField txt1;
    @FXML
      private AnchorPane rootpane;  
    @FXML
    private ChoiceBox txtmemgr;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnaddemp){
            createRecords();
        }else if(event.getSource() == btnupdateemp){
            editRecord();
        }else if(event.getSource() == btnremoveemp){
            removeRecord();
        }
            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayEmpRecords();
    }    
    
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:prod", "wooi", "wooi");
            return conn;        
        }catch (Exception e){
            return null;
        } 
    }
    
    public ObservableList<ManagerID> getChoiceList(){
        ObservableList<ManagerID> choicebox = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select manager_id from employees";   
        try {  
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(query);
            ManagerID mid;
            while (rs.next()) {
                mid = new ManagerID(rs.getInt("manager_id"));
                choicebox.add(mid);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return choicebox;
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
                emp = new Employees(rs.getInt("employee_id"), rs.getString("first_name"), rs.getString("job_id"), rs.getInt("manager_id"), rs.getInt("salary"), rs.getInt("department_id"),rs.getString("last_name"),rs.getString("email"),rs.getString("phone_number"),rs.getFloat("commission_pct"),rs.getDate("hire_date"));
                emplist.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return emplist;
    }
     
     
     
     public void displayEmpRecords() {
        ObservableList<Employees> listemp = getEmployeeList();
        ObservableList<ManagerID> listmd = getChoiceList();
        coleid.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("employee_id"));
        colefname.setCellValueFactory(new PropertyValueFactory<Employees, String>("first_name"));
        colejob.setCellValueFactory(new PropertyValueFactory<Employees, String>("job_id"));
        colemgr.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("manager_id"));
        colesal.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("salary"));
        coledept.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("department_id"));
        colelname.setCellValueFactory(new PropertyValueFactory<Employees, String>("last_name"));
        coleemail.setCellValueFactory(new PropertyValueFactory<Employees, String>("email"));
        colephone.setCellValueFactory(new PropertyValueFactory<Employees, String>("phone_number"));
        colehdate.setCellValueFactory(new PropertyValueFactory<Employees, Date>("hire_date"));
        colecomm.setCellValueFactory(new PropertyValueFactory<Employees, Float>("commission_pct"));

        tblvemp.setItems(listemp);
        
        for (int i =0; i < listmd.size(); i ++){
            int x = listmd.get(i).getManager_id();
            if (txtmemgr.getItems().contains(x)){
                System.out.println("No manager id record");
            }else{
                txtmemgr.getItems().add(x);
            }
        }
    }
     
     
     private void createRecords() {
        String query = "INSERT INTO employees (employee_id,first_name,job_id,manager_id,salary,department_id,last_name,email,phone_number,hire_date,commission_pct) VALUES (" +txteid.getText() + ",'" + txtefname.getText() + "','" + txtejob.getText() + "',"
                + txtmemgr.getValue()+ "," + txtesal.getText() + "," + txtedept.getText()+ ",'" + txtelname.getText()+ "','" + txteemail.getText()+ "','" + txtephone.getText()+ "',TO_DATE('" + txtehdate.getValue()+ "','YYYY-MM-DD')," + txtecomm.getText() + ")";
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
        String query = "UPDATE employees set first_name = '" + txtefname.getText()
                + "', last_name ='" + txtelname.getText()
                + "', email ='" + txteemail.getText()
                + "', job_id ='" + txtejob.getText()
                + "', phone_number ='" + txtephone.getText()
                + "', manager_id =" + txtmemgr.getValue()
                + ", salary =" + txtesal.getText()
                + ", commission_pct =" + txtecomm.getText()
                + ", department_id =" + txtedept.getText()
                + ", hire_date =" + "(TO_DATE('" + txtehdate.getValue()+ "','YYYY-MM-DD'))"
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
     
    private void handleButtonAction(MouseEvent event) {
//          System.out.println("hello");
        Employees emp = tblvemp.getSelectionModel().getSelectedItem();
        txteid.setText(""+emp.getEmployee_id());
        txtefname.setText(emp.getFirst_name());
        txtelname.setText(emp.getLast_name());
        txtejob.setText(emp.getJob_id());
        txtmemgr.setValue(""+emp.getManager_id());
        txteemail.setText(""+emp.getEmail());
        txtephone.setText(""+emp.getPhone_number());
        txtedept.setText(""+emp.getDepartment_id());
        txtecomm.setText(""+emp.getCommission_pct());
        txtesal.setText(""+emp.getSalary());
    }
    
    @FXML
    private void handleButtonAction2(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument_1.fxml"));
        Parent root = loader.load();
        FXMLDocumentController1 second = loader.getController();
        second.myFunction(txt1.getText());
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle(txt1.getText());
         stage.show();

         Stage stage1 = (Stage) btn.getScene().getWindow();
         stage1.close();
    }

    
    @FXML
    private void handleButtonAction3()throws Exception{
        if(tblvemp.getSelectionModel().getSelectedItem()!=null){
            Employees selectedPerson = tblvemp.getSelectionModel().getSelectedItem();
            txteid.setText(String.valueOf(selectedPerson.getEmployee_id()));
            txtefname.setText(selectedPerson.getFirst_name());
            txtelname.setText(selectedPerson.getLast_name());
            txtejob.setText(selectedPerson.getJob_id());
            txtmemgr.setValue(selectedPerson.getManager_id());
            txtesal.setText(String.valueOf(selectedPerson.getSalary()));
            txtedept.setText(String.valueOf(selectedPerson.getDepartment_id()));
            txteemail.setText(selectedPerson.getEmail());
            txtephone.setText(String.valueOf(selectedPerson.getPhone_number()));
            txtecomm.setText(String.valueOf(selectedPerson.getCommission_pct()));  
            txtehdate.setValue(selectedPerson.getHire_date().toLocalDate());
        }   
    }

}
