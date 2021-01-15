/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_ems_project;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Desmond
 */
public class AddNewEmpController implements Initializable {

    @FXML
    private Label fnameLabel;
    @FXML
    private Label lnameLabel;
    @FXML
    private Label salLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumLabel;
    @FXML
    private Label managerIDLabel;
    @FXML
    private Label jobIDLabel;
    @FXML
    private Label deptIDLabel;
    @FXML
    private Label commLabel;
    @FXML
    private Label eIDLabel;

    @FXML
    private TextField fnameTxt;
    @FXML
    private TextField lnameTxt;
    @FXML
    private TextField salTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField pnTxt;
//    @FXML
//    private TextField mIDTxt;
    @FXML
    private TextField jIDTxt;
//    @FXML
//    private TextField dIDTxt;
    @FXML
    private TextField commTxt;
    @FXML
    private TextField empIDTxt;
    @FXML
    private Button addBtn;
    @FXML
    private Button modBtn;
    @FXML
    private Button delBtn;
    @FXML
    private Label emsLabel;
    @FXML
    private TableView<Employees> tableView;
    @FXML
    private TableColumn<Employees, Integer> col1;
    @FXML
    private TableColumn<Employees, String> col2;
    @FXML
    private TableColumn<Employees, String> col3;
    @FXML
    private TableColumn<Employees, String> col4;
    @FXML
    private TableColumn<Employees, String> col5;
    @FXML
    private TableColumn<Employees, String> col6;
    @FXML
    private TableColumn<Employees, Integer> col7;
    @FXML
    private TableColumn<Employees, Double> col8;
    @FXML
    private TableColumn<Employees, Integer> col9;
    @FXML
    private TableColumn<Employees, Integer> col10;
    @FXML
    private Button rtmBtn;

    @FXML
    private ChoiceBox cb1;
    @FXML
    private ChoiceBox cb2;

    //Date item here. Bloody nightmare
    @FXML
    private Label hiredDate;
    @FXML
    private DatePicker hdTxt;
    @FXML
    private TableColumn<Employees, Date> col11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        displayEmpRecords();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == addBtn) {
            createRecords();
        } else if (event.getSource() == modBtn) {
            modRecord();
        }
    }

    public Connection getConnection() {
        System.out.println("Triggered getConnection");
        Connection conn;
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:prod", "desmond", "desmond");
            return conn;
        } catch (Exception e) {
            return null;
        }

    }

    public ObservableList<Employees> getEmployeeList() {
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select * from empdupe";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Employees emp;
            while (rs.next()) {
                emp = new Employees(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getDate("hire_date"),
                        rs.getString("job_id"),
                        rs.getInt("salary"),
                        rs.getDouble("commission_pct"),
                        rs.getInt("manager_id"),
                        rs.getInt("department_id"));
                emplist.add(emp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emplist;
    }

    public ObservableList<Departments> getDept() {
        ObservableList<Departments> depChoice = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select department_id from empdupe";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Departments dp;
            while (rs.next()) {
                dp = new Departments(rs.getInt("department_id"));
                depChoice.add(dp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return depChoice;
    }

    public ObservableList<Choices> getChoices() {
        ObservableList<Choices> choicebox = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "Select manager_id from empdupe";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Choices cb;
            while (rs.next()) {
                cb = new Choices(rs.getInt("manager_id"));
                choicebox.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return choicebox;
    }

    @FXML
    public void displayEmpRecords() {
        System.out.println("Triggered displayEmpRecords");
        ObservableList<Employees> listemp = getEmployeeList();
        ObservableList<Choices> listcb = getChoices();
        ObservableList<Departments> listdep = getDept();
        col1.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("empID"));
        col2.setCellValueFactory(new PropertyValueFactory<Employees, String>("fname"));
        col3.setCellValueFactory(new PropertyValueFactory<Employees, String>("lname"));
        col4.setCellValueFactory(new PropertyValueFactory<Employees, String>("email"));
        col5.setCellValueFactory(new PropertyValueFactory<Employees, String>("phoneNum"));
        col6.setCellValueFactory(new PropertyValueFactory<Employees, String>("jobID"));
        col7.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("salary"));
        col8.setCellValueFactory(new PropertyValueFactory<Employees, Double>("CommPCT"));
        col9.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("managerID"));
        col10.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("deptID"));
        col11.setCellValueFactory(new PropertyValueFactory<Employees, Date>("hdate"));

        tableView.setItems(listemp);

        for (int i = 0; i < listcb.size(); i++) {
            int x = listcb.get(i).getManagerID();
            if (cb1.getItems().contains(x)) {
//                System.out.println("Do nothing");
            } else {
                cb1.getItems().add(x);
            }
        }

        for (int i = 0; i < listdep.size(); i++) {
            int x = listdep.get(i).getDepID();
            if (cb2.getItems().contains(x)) {
//                System.out.println("Do nothing");
            } else {
                cb2.getItems().add(x);
            }
        }
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            System.out.println("the query is: " + query);
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createRecords() {
        try {
            Connection conn = getConnection();

            String query = "INSERT into empdupe (Employee_id,first_name,last_name,email,phone_number,job_id,salary,commission_pct,manager_id,department_id,hire_date) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement create = conn.prepareStatement(query);
            create.setInt(1, Integer.parseInt(empIDTxt.getText()));
            create.setString(2, fnameTxt.getText());
            create.setString(3, lnameTxt.getText());
            create.setString(4, emailTxt.getText());
            create.setString(5, pnTxt.getText());
            create.setString(6, jIDTxt.getText());
            create.setInt(7, Integer.parseInt(salTxt.getText()));
            create.setDouble(8, Double.parseDouble(commTxt.getText()));
            create.setInt(9, Integer.parseInt(String.valueOf(cb1.getValue())));
            create.setInt(10, Integer.parseInt(String.valueOf(cb2.getValue())));
            create.setDate(11, Date.valueOf(hdTxt.getValue()));

            create.executeQuery();
            displayEmpRecords();

        } catch (SQLException e) {
            System.out.println("" + e);
        }

    }

    private void modRecord() {
        System.out.println("ModRecord Triggered");
        String query = "UPDATE empdupe set first_name = '" + fnameTxt.getText()
                + "', last_name ='" + lnameTxt.getText()
                + "', job_id ='" + jIDTxt.getText()
                + "', phone_number ='" + pnTxt.getText()
                + "', manager_id =" + cb1.getValue()
                + ", email ='" + emailTxt.getText()
                + "', salary =" + salTxt.getText()
                + ", commission_pct =" + Double.valueOf(commTxt.getText())
                + ", department_id =" + cb2.getValue()
                + ", hire_date = TO_DATE('" + hdTxt.getValue() + "','YYYY-MM-DD')"
                + " where employee_id =" + empIDTxt.getText() + "";

        executeQuery(query);
        System.out.println(query);
        displayEmpRecords();
    }

    public void removeRecord(int x) {
        String query = "delete from empdupe where employee_id =" + x;
        System.out.println("value of x : " + x);
        executeQuery(query);
    }

    @FXML
    private void handleButtonAction3() throws Exception {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Employees selectedPerson = tableView.getSelectionModel().getSelectedItem();
            empIDTxt.setText(String.valueOf(selectedPerson.getEmpID()));
            fnameTxt.setText(selectedPerson.getFname());
            lnameTxt.setText(selectedPerson.getLname());
            salTxt.setText(String.valueOf(selectedPerson.getSalary()));
            emailTxt.setText(selectedPerson.getEmail());
            pnTxt.setText(selectedPerson.getPhoneNum());
            cb1.setValue(selectedPerson.getManagerID());
            jIDTxt.setText(selectedPerson.getJobID());
            cb2.setValue(selectedPerson.getDeptID());
            commTxt.setText(String.valueOf(selectedPerson.getCommPCT()));
            hdTxt.setValue(selectedPerson.getHdate().toLocalDate());
        }

    }

    @FXML
    private void handleButtonAction2(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();

        stage.setTitle("Confirm to delete employee ?");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
