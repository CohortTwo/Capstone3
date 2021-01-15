/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empmgtfxapp;

import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author chiangyong
 */
public class FXMLEmpMgtController implements Initializable {

    @FXML
    private Button AddEmpRecBtn;
    @FXML
    private Button EditEmpRecBtn;
    @FXML
    private Button DelEmpRecBtn;
    @FXML
    private Button QryEmpRecBtn;
    @FXML
    private Label lblTitle;
    @FXML
    private Button ExitBtn;
  //  private Label lblText;
    @FXML
    private TextField txtEmpID;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtHDate;
    @FXML
    private ChoiceBox sel_JobID;
    @FXML
    private TextField txtSalary;
    @FXML
    private TextField txtCommPCT;
    @FXML
    private ChoiceBox sel_MgrID;
    @FXML
    private ChoiceBox sel_DeptID;
    @FXML
    private TableView<EmpRecord> tableEmp;
    @FXML
    private TableColumn<EmpRecord, Integer> col_ID;
    @FXML
    private TableColumn<EmpRecord, String> col_Name;
    @FXML
    private TableColumn<EmpRecord, String> col_JobID;
    @FXML
    private TableColumn<EmpRecord, Float> col_Salary;
    @FXML
    private TableColumn<EmpRecord, String> col_HireDate;
    @FXML
    private TableColumn<EmpRecord, Integer> col_MgrID;
    @FXML
    private TableColumn<EmpRecord, Integer> col_DeptID;
    @FXML
    private TableColumn<EmpRecord, Float> col_CommPCT;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane topPane;

    EmpMgtFXapp dBase = new EmpMgtFXapp();
    Connection conn = dBase.getConnected();
    Statement st;
    @FXML
    private Button btnConfirm;
   
    Alert a = new Alert(AlertType.NONE);
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnConfirm.setDisable(true);
        String qry = "Select * from emp, departments "
               + "where emp.department_id = departments.department_id";
        dispEmpRecords(qry);
        MgrIDList();
        DeptIDList();
        JobIDList();     
    }


    @FXML
    private void confirmButtonAction(ActionEvent event)
    {
        if(btnConfirm.getText().matches("Confirm Add"))
        {
                   ConfirmAdd();
        }
        else if(btnConfirm.getText().matches("Confirm Update"))
        {
              ConfirmUpdate();
        }
        else if(btnConfirm.getText().matches("Confirm Delete"))
        {
            a.setAlertType(AlertType.CONFIRMATION);
            a.setContentText("Are you confirm deleting the record?");
            a.show();
            ConfirmDelete();
        }
        else if(btnConfirm.getText().matches("Enter Query"))
        {
            EnterQuery();
        }
    }
            
    public ObservableList<EmpRecord> getEmpList(String query) {
        ObservableList<EmpRecord> empList = FXCollections.observableArrayList();
      
//        String qry = "Select * from emp, departments "
//         + "where emp.department_id = departments.department_id";
      
        ResultSet rs;
        String strDate;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            EmpRecord emprec;

            while (rs.next()) {
                
                strDate = dateFormat.format(rs.getDate("hire_date"));
                
                emprec = new EmpRecord(rs.getInt("employee_id"),
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("phone_number"),
                        strDate, rs.getString("job_id"),
                        rs.getFloat("salary"), rs.getFloat("commission_pct"),
                        rs.getInt("manager_id"), rs.getInt("department_id"));

                empList.add(emprec);
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

    //     empList.stream().sorted(Comparator.comparingDouble(EmpRecord::getEID));
       return empList;
    }


    public void dispEmpRecords(String query) {
        ObservableList<EmpRecord> listrec = getEmpList(query);

        col_ID.setCellValueFactory(new PropertyValueFactory<EmpRecord, Integer>("eID"));
        col_Name.setCellValueFactory(new PropertyValueFactory<EmpRecord, String>("efirstName"));
        col_JobID.setCellValueFactory(new PropertyValueFactory<EmpRecord, String>("eJobID"));
        col_Salary.setCellValueFactory(new PropertyValueFactory<EmpRecord, Float>("eSalary"));
        col_HireDate.setCellValueFactory(new PropertyValueFactory<EmpRecord,String>("eHireDate"));
        col_MgrID.setCellValueFactory(new PropertyValueFactory<EmpRecord, Integer>("eMgrID"));
        col_DeptID.setCellValueFactory(new PropertyValueFactory<EmpRecord, Integer>("eDepID"));
        col_CommPCT.setCellValueFactory(new PropertyValueFactory<EmpRecord, Float>("eCommPct"));
        tableEmp.setItems(listrec);
        //System.out.println("EID : " + listrec.get(0).getEID());
    }
    
   
    @FXML
    private void AddEmpRec() throws SQLException
    {
        String qry;
        ClearTextField();
        btnConfirm.setText("Confirm Add");
        int IDemp = maxID("emp")+1;
        txtEmpID.setText(Integer.toString(IDemp));
        
     try
     {
        st = conn.createStatement();
        ResultSet rs;
        qry = "select employee_id,first_name from emp "
                    + "where employee_id =" + Integer.toString(IDemp) 
                    + " order by employee_id";
        rs = st.executeQuery(qry);
        
        if (rs.next()) {
                System.err.println("Employee ID is already taken!");
                System.out.println(rs.getInt("Employee_id") + " \t  " 
                        + rs.getString("first_name"));
            } 
        else 
        {       
                btnConfirm.setText("Confirm Add");
                btnConfirm.setDisable(false);
        }
     }
     catch(SQLException e)
     {
         System.out.println(e);
     }
        
    }
    
    //Add new record method
    private void ConfirmAdd()
    {
        try
        {
            String qry;
            String emailAddr;
            st = conn.createStatement();
            ResultSet rs;
            emailAddr = txtFName.getText().substring(0, 1)
                        +txtLName.getText();
            txtEmail.setText(emailAddr);
            
            qry = "Insert into emp VALUES (" + Integer.parseInt(txtEmpID.getText()) + ", '" 
                            + txtFName.getText()+ "', '"+ txtLName.getText() + "', '" 
                            +txtEmail.getText()+ "', '" + txtPhone.getText() + "', '" 
                            + txtHDate.getText()+ "','"+sel_JobID.getValue()
                            + "', "+txtSalary.getText()+ ", "+txtCommPCT.getText() 
                            + ", "+ sel_MgrID.getValue() + ", " 
                            + sel_DeptID.getValue()+ ")";

            rs = st.executeQuery(qry);
            if(rs.next())
            {
                qry = "Select * from emp, departments "
                    + "where emp.department_id = departments.department_id";
                dispEmpRecords(qry);
                a.setAlertType(AlertType.INFORMATION);
                a.setContentText("Record is successfully added.");
                a.show();
            }
            else
            {
                a.setAlertType(AlertType.ERROR);
                a.setContentText("No Record added.");
                a.show();
            }
            btnConfirm.setText("");
            btnConfirm.setDisable(true);
          //  ClearTextField();
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
    }
    
     
    @FXML
    public void EditEmpRec()
    {
        btnConfirm.setText("Confirm Update");
        btnConfirm.setDisable(false); 
    }
    //Update record method
    private void ConfirmUpdate() {
        String empID = txtEmpID.getText();
        try {
            String qry;
            st = conn.createStatement();
            ResultSet rs;

            qry = "update emp"
                    + " set first_name = '" + txtFName.getText()
                    + "', last_name = '" + txtLName.getText()
                    + "', email = '" + txtEmail.getText()
                    + "', phone_number = '" + txtPhone.getText()
                    + "', hire_date = '" + txtHDate.getText()
                    + "', job_id = '" + sel_JobID.getValue()
                    + "', salary = " + txtSalary.getText()
                    + ", manager_id = " + sel_MgrID.getValue()
                    + ", department_id = " + sel_DeptID.getValue()
                    + ", commission_pct = " + txtCommPCT.getText()
                    + " where employee_id = " + empID;

            rs = st.executeQuery(qry);

            if (rs.next()) {
                qry = "Select * from emp, departments "
                    + "where emp.department_id = departments.department_id";
                dispEmpRecords(qry);
                a.setAlertType(AlertType.INFORMATION);
                a.setContentText("Record is successfully updated.");
                a.show();
            } else {
                a.setAlertType(AlertType.ERROR);
                a.setContentText("No Record added.");
                a.show();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        btnConfirm.setText("");
        btnConfirm.setDisable(true);
    }

    @FXML
    private void DelEmpRec() {
        btnConfirm.setText("Confirm Delete");
        btnConfirm.setDisable(false);
    }

    //Delete record method
    private void ConfirmDelete() {
        String empID = txtEmpID.getText();

        try {
            String qry;
            st = conn.createStatement();
            ResultSet rs;

            qry = "delete from emp where employee_id = "+ empID;
            rs = st.executeQuery(qry);

            if (rs.next()) {
                qry = "Select * from emp, departments "
                  + "where emp.department_id = departments.department_id";
                dispEmpRecords(qry);
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Record is successfully deleted.");
                a.show();
            } else {
                a.setAlertType(AlertType.ERROR);
                a.setContentText("No Record added.");
                a.show();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        
        btnConfirm.setText("");
        btnConfirm.setDisable(true);
    }
    
    @FXML
    private void QueryEmpRec()
    {
        ClearTextField();
        btnConfirm.setText("Enter Query");
        btnConfirm.setDisable(false);
    }
    
    //Enter query method
    private void EnterQuery() {
        boolean queryFlag = true;
        String qry = "";
        String queryType;

        TextInputDialog td = new TextInputDialog("Enter salary or department ormanager");

        td.setHeaderText("Query item");

        td.showAndWait();
        queryType = td.getEditor().getText().toLowerCase();

        if (queryType.matches("salary")) {
            qry = "select * from emp where salary " + txtSalary.getText();
        } else if (queryType.matches("department")) {
            qry = "select * from emp where emp.department_id = "
                    + sel_DeptID.getValue();
        } else if (queryType.matches("manager")) {
            qry = "select * from emp where emp.manager_id = "
                    + sel_MgrID.getValue();
        } else {
            a.setAlertType(AlertType.ERROR);
            a.setContentText("Enter salary or department or manager");
            a.show();
            queryFlag = false;
        }

        try {
            if (queryFlag) {
                dispEmpRecords(qry);

            }

        } catch (Exception e) {
            System.err.println(e);
        }

        btnConfirm.setText("");
        btnConfirm.setDisable(true);

    }
    
    //Find the last Employee ID for new entry  
    public int maxID(String inS) {
        String str;
        if (inS.matches("emp")) {
            str = "Select employee_id from emp "
                    + "where employee_id = (select max(employee_id) from emp)";
        } else if (inS.matches("dept")) {
            str = "select department_id from departments"
                    + " where department_id = (select max(department_id) "
                    + "from departments)";
        } else {
            return 0;
        }

        int idMax = 0;
        try {
            ResultSet rs = st.executeQuery(str);
            rs.next();

            if (inS.matches("emp")) {
                idMax = rs.getInt("employee_id");
                System.out.println("The Last Employee ID is " + idMax);
            } else if (inS.matches("dept")) {
                idMax = rs.getInt("department_id");
                System.out.println("The Last Department ID is " + idMax);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return idMax;
    }
  
    //Mouse over the table cells to select a record
    @FXML
    private void handleButtonAction(MouseEvent event) {
        EmpRecord emprec = tableEmp.getSelectionModel().getSelectedItem();
        txtEmpID.setText("" + emprec.getEID());
        String strDate;
       
        try {
            String qry = "select * from emp "
                    + "where employee_id = " + emprec.getEID();

            ResultSet rs = st.executeQuery(qry);

            if (rs.next()) {
                txtEmpID.setText(Integer.toString(rs.getInt("employee_id")));
                txtFName.setText(rs.getString("first_name"));
                txtLName.setText(rs.getString("last_name"));
                txtEmail.setText(rs.getString("email"));
                txtPhone.setText(rs.getString("phone_number"));
                strDate = dateFormat.format(rs.getDate("hire_date"));
                txtHDate.setText(strDate);
                sel_JobID.setValue(rs.getString("job_id"));
                txtSalary.setText(Float.toString(rs.getFloat("salary")));
                txtCommPCT.setText(Float.toString(rs.getFloat("commission_pct")));
                sel_MgrID.setValue(rs.getInt("manager_id"));
                sel_DeptID.setValue(rs.getInt("department_id"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

    }
   
//Clear all Text Fields
    private void ClearTextField()
    {
        txtEmpID.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtHDate.setText("");
        sel_JobID.setValue("");
        txtSalary.setText("");
        txtCommPCT.setText("");
        sel_MgrID.setValue("");
        sel_DeptID.setValue("");
    }
    
    //Create item list for a Job ID ChoiceBox
    private void JobIDList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.remove(list);
        List<String> sortList = list;
        try {
            String qry = "select job_id from emp";
            ResultSet rs = st.executeQuery(qry);

            while (rs.next()) {
                list.add(rs.getString("job_id"));
            }
            sortList = list.stream().distinct().sorted()
                    .collect(Collectors.toList());
            sel_JobID.getItems().addAll(sortList);

        } catch (SQLException e) {
            System.out.println(e);
        }
    } 
    
    //Create item list for a Manager ID ChoiceBox
    private void MgrIDList() {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        list.remove(list);
        List<Integer> sortList = list;

        try {
            String qry = "select manager_id from emp";
            ResultSet rs = st.executeQuery(qry);

            while (rs.next()) {
                list.add(rs.getInt("manager_id"));
            }
            sortList = list.stream().distinct().sorted()
                    .collect(Collectors.toList());
            sel_MgrID.getItems().addAll(sortList);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    //Create item list for a Department ID ChoiceBox
    private void DeptIDList() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        list.remove(list);
        List<Integer> sortList = list;
        try {
            String qry = "select department_id from emp";
            ResultSet rs = st.executeQuery(qry);

            while (rs.next()) {
                list.add(rs.getInt("department_id"));
            }
            sortList = list.stream().distinct().sorted()   //Using Stream method
                    .collect(Collectors.toList());
            sel_DeptID.getItems().addAll(sortList);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    @FXML
     public void exitRec() throws SQLException {
        st.close();
        conn.close();
        System.exit(0);
    }

}
