package javafxcompany;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FXCompanyController implements Initializable {

    @FXML
    private TextField txtEmpId;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtSal;
    @FXML
    private TextField txtCom;
    @FXML
    private TableView<Employees> tableEmp;
    @FXML
    private TableColumn<Employees, Integer> colEmpId;
    @FXML
    private TableColumn<Employees, String> colFName;
    @FXML
    private TableColumn<Employees, String> colLName;
    @FXML
    private TableColumn<Employees, String> colMgr;
    @FXML
    private TableColumn<Employees, String> colJobT;
    @FXML
    private TableColumn<Employees, Integer> colSal;
    @FXML
    private TableColumn<Employees, Double> colComm;
    @FXML
    private TableColumn<Employees, String> colDep;
    @FXML
    private TableColumn<Employees, Date> colHdate;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnRemove;
    @FXML
    private ChoiceBox<String> cboxDept;
    @FXML
    private ChoiceBox<String> cboxJobT;
    @FXML
    private ChoiceBox<String> cboxMgr;

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static String sqlCmd;
    int updateEmpId;
    String jobId = "";
    int mgrId = 0;
    int deptId = 0;
    boolean toPercent = false;
    ObservableList<PieChart.Data> chartSalary = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> chartCount = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> chartSalaryPer = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> chartCountPer = FXCollections.observableArrayList();
    int totalSal = 0;
    int totalCount = 0;

    @FXML
    private Button btnLogOut;
    @FXML
    private HBox hbox;

    @FXML
    private Button btnUpdateNext;
    @FXML
    private Button btnAddNext;
    @FXML
    private DatePicker dpicker;
    @FXML
    private Button btnChart;
    @FXML
    private PieChart pieSal;
    @FXML
    private PieChart pieCount;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnAlt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:prod", "hr", "hr");
            conn.setAutoCommit(false);  // need to set autocommit to off to implement savepoint.
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();

            rs = dbm.getTables(null, null, ("JAVADEPARTMENTS").toUpperCase(), null);    // Table name needs to be uppercase.
            if (rs.next()) {
                stmt.executeUpdate("Drop Table JAVADEPARTMENTS");
            }

            rs = dbm.getTables(null, null, ("JAVAEMPLOYEES").toUpperCase(), null);    // Table name needs to be uppercase.
            if (rs.next()) {
                stmt.executeUpdate("Drop Table JAVAEMPLOYEES");
            }

            rs = dbm.getTables(null, null, ("JAVAJOBS").toUpperCase(), null);    // Table name needs to be uppercase.
            if (rs.next()) {
                stmt.executeUpdate("Drop Table JAVAJOBS");
            }

            sqlCmd = "Create Table JavaDepartments as Select department_id, department_name from Departments";
            stmt.executeUpdate(sqlCmd);
            sqlCmd = "Create Table JavaEmployees as Select employee_id, first_name, last_name, manager_id, job_id, salary, commission_pct, department_id, hire_date from Employees";
            stmt.executeUpdate(sqlCmd);
            sqlCmd = "Create Table JavaJobs as Select job_id, job_title from Jobs";
            stmt.executeUpdate(sqlCmd);

            displayEmpRecords();
            displayMgrRecords();
            displayDepRecords();
            displayJobRecords();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static ObservableList<Employees> employeeData() throws SQLException {
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        sqlCmd = "Select e.employee_id, e.first_name, e.last_name, concat(concat(m.first_name, ' '), m.last_name) manager_name, j.job_title, e.salary, e.commission_pct, d.department_name, e.hire_date "
                + "From JavaEmployees e Left Join JavaEmployees m on e.manager_id = m.employee_id"
                + " Left Join JavaDepartments d on e.department_id = d.department_id"
                + " Left Join JavaJobs j on e.job_id = j.job_id Order By e.first_name";

        rs = stmt.executeQuery(sqlCmd);
        while (rs.next()) {
            Employees emp = new Employees(rs.getInt("employee_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("manager_name"),
                    rs.getString("job_title"), rs.getInt("salary"), rs.getDouble("commission_pct"), rs.getString("department_name"), rs.getDate("hire_date"));
            emplist.add(emp);
        }
        return emplist;
    }

    public void displayEmpRecords() {
        try {
            ObservableList<Employees> listemp = employeeData();
            colEmpId.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("EmpId"));
            colFName.setCellValueFactory(new PropertyValueFactory<Employees, String>("firstName"));
            colLName.setCellValueFactory(new PropertyValueFactory<Employees, String>("lastName"));
            colMgr.setCellValueFactory(new PropertyValueFactory<Employees, String>("mgrName"));
            colJobT.setCellValueFactory(new PropertyValueFactory<Employees, String>("jobTitle"));
            colSal.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("sal"));
            colComm.setCellValueFactory(new PropertyValueFactory<Employees, Double>("comm"));
            colDep.setCellValueFactory(new PropertyValueFactory<Employees, String>("deptName"));
            colHdate.setCellValueFactory(new PropertyValueFactory<Employees, Date>("hdate"));

            tableEmp.setItems(listemp);
            tableEmp.getSortOrder().add(colEmpId);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void displayMgrRecords() {
        try {
            ObservableList<String> mgrNames = FXCollections.observableArrayList();
            ObservableList<Employees> listmgr = employeeData();
            for (int i = 0; i < listmgr.size(); i++) {
                mgrNames.add(listmgr.get(i).getFirstName() + " " + listmgr.get(i).getLastName());
            }
            cboxMgr.setItems(mgrNames);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static ObservableList<Departments> departmentData() throws SQLException {
        ObservableList<Departments> deplist = FXCollections.observableArrayList();
        sqlCmd = "Select department_id, department_name From JavaDepartments Order By department_name";
        rs = stmt.executeQuery(sqlCmd);
        while (rs.next()) {
            Departments dept = new Departments(rs.getInt("department_id"), rs.getString("department_name"));
            deplist.add(dept);
        }
        return deplist;
    }

    public void displayDepRecords() {
        try {
            ObservableList<String> depNames = FXCollections.observableArrayList();
            ObservableList<Departments> listdep = departmentData();
            for (int i = 0; i < listdep.size(); i++) {
                depNames.add(listdep.get(i).getDeptName());
            }
            cboxDept.setItems(depNames);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static ObservableList<Jobs> jobData() throws SQLException {
        ObservableList<Jobs> joblist = FXCollections.observableArrayList();
        sqlCmd = "Select job_id, job_title From JavaJobs Order By job_title";

        rs = stmt.executeQuery(sqlCmd);
        while (rs.next()) {
            Jobs job = new Jobs(rs.getString("job_id"), rs.getString("job_title"));
            joblist.add(job);
        }
        return joblist;
    }

    public void displayJobRecords() {
        try {
            ObservableList<String> jobNames = FXCollections.observableArrayList();
            ObservableList<Jobs> listjob = jobData();
            for (int i = 0; i < listjob.size(); i++) {
                jobNames.add(listjob.get(i).getJobTitle());
            }
            cboxJobT.setItems(jobNames);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @FXML
    public void removeRecord() {
        TextInputDialog td = new TextInputDialog("0");
        td.setTitle("DELETE REQUEST");
        td.setHeaderText("Employee ID to delete :");
        Optional<String> res = td.showAndWait();
        if (res.toString().equalsIgnoreCase("Optional.empty")) {
            System.out.println("Cancel button");
        } else {

            String EmpId = td.getEditor().getText();
            txtEmpId.setText(EmpId);
            hbox.setVisible(true);

            fillHboxData();

            if (txtEmpId.getText().isEmpty()) {
                txtEmpId.setText("0");
            }

            int removeEmpId = Integer.parseInt(txtEmpId.getText());
            boolean hasRecord = false;

            try {
                ObservableList<Employees> listemp = employeeData();
                for (int i = 0; i < listemp.size(); i++) {
                    if (removeEmpId == listemp.get(i).getEmpId()) {
                        hasRecord = true;
                        Alert a = new Alert(AlertType.CONFIRMATION);
                        a.setTitle("CONFIRMATION");
                        a.setHeaderText("Confirmation To Remove Record");
                        a.setContentText("Confirm to remove employee ID, " + removeEmpId + " ?");

                        Optional<ButtonType> result = a.showAndWait();
                        if (!result.isPresent() || result.get() != ButtonType.OK) {
                            System.out.println("Cancel");
                        } else {
                            sqlCmd = "Delete From JavaEmployees Where employee_id=" + removeEmpId;
                            stmt.executeUpdate(sqlCmd);
                            displayEmpRecords();
                        }
                        break;
                    }
                }
                if (!hasRecord) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("No such Employee ID");
                    a.show();
                }
            } catch (Exception e) {
                e.getMessage();
            }
            hbox.setVisible(false);
        }
    }

    @FXML
    public void updateRecord() {
        TextInputDialog td = new TextInputDialog("0");
        td.setTitle("UPDATE REQUEST");
        td.setHeaderText("Employee ID to update :");
        Optional<String> res = td.showAndWait();
        if (res.toString().equalsIgnoreCase("Optional.empty")) {
            System.out.println("Cancel button");
        } else {
            String EmpId = td.getEditor().getText();
            txtEmpId.setText(EmpId);
            hbox.setVisible(true);
            txtEmpId.setDisable(true);
            fillHboxData();
            if (txtEmpId.getText().isEmpty()) {
                txtEmpId.setText("0");
            }
            updateEmpId = Integer.parseInt(txtEmpId.getText());
            boolean hasRecord = false;
            try {
                ObservableList<Employees> listemp = employeeData();
                for (int i = 0; i < listemp.size(); i++) {
                    if (updateEmpId == listemp.get(i).getEmpId()) {
                        hasRecord = true;
                        btnUpdateNext.setVisible(true);
                        btnAdd.setVisible(false);
                        btnUpdate.setVisible(false);
                        btnRemove.setVisible(false);
                        btnLogOut.setVisible(false);
                        break;
                    }
                }
                if (!hasRecord) {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setTitle("INFORMATION");
                    a.setHeaderText("Invalid Input");
                    a.setContentText("No such Employee ID");
                    a.show();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    @FXML
    public void addRecord() {
        txtFName.setText("");
        txtLName.setText("");
        cboxMgr.setValue(null);
        cboxJobT.setValue(null);
        txtSal.setText("");
        txtCom.setText("");
        cboxDept.setValue(null);
        dpicker.setValue(LocalDate.now());
        hbox.setVisible(true);
        int maxId = 0;
        try {
            ObservableList<Employees> listemp = employeeData();
            for (int i = 0; i < listemp.size(); i++) {
                maxId = maxId > listemp.get(i).getEmpId() ? maxId : listemp.get(i).getEmpId();
            }
            txtEmpId.setText("" + ++maxId);
            txtEmpId.setDisable(true);
            btnAddNext.setVisible(true);
            btnUpdateNext.setVisible(false);
            btnAdd.setVisible(false);
            btnUpdate.setVisible(false);
            btnRemove.setVisible(false);
            btnLogOut.setVisible(false);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void fillHboxData() {
        if (!txtEmpId.getText().isEmpty()) {
            int empId = Integer.parseInt(txtEmpId.getText());
            boolean hasRecord = false;
            try {
                ObservableList<Employees> listemp = employeeData();
                for (int i = 0; i < listemp.size(); i++) {
                    if (empId == listemp.get(i).getEmpId()) {
                        hasRecord = true;
                        txtFName.setText(listemp.get(i).getFirstName());
                        txtLName.setText(listemp.get(i).getLastName());
                        cboxMgr.setValue(listemp.get(i).getMgrName());
                        cboxJobT.setValue(listemp.get(i).getJobTitle());
                        txtSal.setText("" + listemp.get(i).getSal());
                        txtCom.setText("" + listemp.get(i).getComm());
                        cboxDept.setValue(listemp.get(i).getDeptName());
                        dpicker.setValue(listemp.get(i).getHdate().toLocalDate());
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    @FXML
    public void logOut() {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setContentText("Confirm to log out from this session?");
        Optional<ButtonType> result = a.showAndWait();
        if (!result.isPresent() || result.get() != ButtonType.OK) {
            System.out.println("Log Out Aborted!");
        } else {
            try {
                stmt.executeUpdate("Drop Table JAVAEMPLOYEES");
                stmt.executeUpdate("Drop Table JAVADEPARTMENTS");
                stmt.executeUpdate("Drop Table JAVAJOBS");
                stmt.close();
                conn.close();
                ((Stage) tableEmp.getScene().getWindow()).close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void nextUpdateAction(ActionEvent event) {
        try {
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setTitle("CONFIRMATION");
            a.setHeaderText("Confirmation To Update Record");
            a.setContentText("Confirm to update employee ID, " + updateEmpId + " ?");

            Optional<ButtonType> result = a.showAndWait();
            if (!result.isPresent() || result.get() != ButtonType.OK) {
                System.out.println("Cancel");
            } else {

// get choicebox selected mgr - start
                ObservableList<Employees> listmgr = employeeData();
                for (int j = 0; j < listmgr.size(); j++) {
                    String mgrName = listmgr.get(j).getFirstName() + " " + listmgr.get(j).getLastName();
                    if (mgrName.equalsIgnoreCase(cboxMgr.getValue())) {
                        mgrId = listmgr.get(j).getEmpId();
                        break;
                    }
                }
// get choicebox selected mgr - end                       

// get choicebox selected job - start
                ObservableList<Jobs> listjob = jobData();
                for (int j = 0; j < listjob.size(); j++) {
                    if (listjob.get(j).getJobTitle().equalsIgnoreCase(cboxJobT.getValue())) {
                        jobId = listjob.get(j).getJobId();
                        break;
                    }
                }
// get choicebox selected job - end

// get choicebox selected department - start
                ObservableList<Departments> listdep = departmentData();
                for (int j = 0; j < listdep.size(); j++) {
                    if (listdep.get(j).getDeptName().equalsIgnoreCase(cboxDept.getValue())) {
                        deptId = listdep.get(j).getDeptId();
                        break;
                    }
                }
// get choicebox selected department - end

                sqlCmd = "Update JavaEmployees Set first_name='" + txtFName.getText()
                        + "', last_name='" + txtLName.getText() + "', manager_id=" + mgrId + ", job_id='" + jobId + "', salary=" + Integer.parseInt(txtSal.getText())
                        + ", commission_pct=" + Double.parseDouble(txtCom.getText()) + ", department_id=" + deptId + ", hire_date= TO_DATE('" + dpicker.getValue().toString() + "', 'YYYY-MM-DD')"
                        + " Where employee_id=" + updateEmpId;
                stmt.executeUpdate(sqlCmd);
                displayEmpRecords();
            }
            btnUpdateNext.setVisible(false);
            btnAddNext.setVisible(false);
            btnAdd.setVisible(true);
            btnUpdate.setVisible(true);
            btnRemove.setVisible(true);
            btnLogOut.setVisible(true);
            hbox.setVisible(false);
        } catch (Exception e) {
            Alert a = new Alert(AlertType.WARNING);
            a.setTitle("WARNING");
            a.setHeaderText("Invalid Input");
            a.setContentText("Verify Input Data");
            a.show();
        }
    }

    @FXML
    private void nextAddAction(ActionEvent event) {
        try {
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setTitle("CONFIRMATION");
            a.setHeaderText("Confirmation To Add Record");
            a.setContentText("Confirm to add employee ID, " + Integer.parseInt(txtEmpId.getText()) + " ?");

            Optional<ButtonType> result = a.showAndWait();
            if (!result.isPresent() || result.get() != ButtonType.OK) {
                System.out.println("Cancel");
            } else {

// get choicebox selected mgr - start
                ObservableList<Employees> listmgr = employeeData();
                for (int j = 0; j < listmgr.size(); j++) {
                    String mgrName = listmgr.get(j).getFirstName() + " " + listmgr.get(j).getLastName();
                    if (mgrName.equalsIgnoreCase(cboxMgr.getValue())) {
                        mgrId = listmgr.get(j).getEmpId();
                        break;
                    }
                }
// get choicebox selected mgr - end                       

// get choicebox selected job - start
                ObservableList<Jobs> listjob = jobData();
                for (int j = 0; j < listjob.size(); j++) {
                    if (listjob.get(j).getJobTitle().equalsIgnoreCase(cboxJobT.getValue())) {
                        jobId = listjob.get(j).getJobId();
                        break;
                    }
                }
// get choicebox selected job - end

// get choicebox selected department - start
                ObservableList<Departments> listdep = departmentData();
                for (int j = 0; j < listdep.size(); j++) {
                    if (listdep.get(j).getDeptName().equalsIgnoreCase(cboxDept.getValue())) {
                        deptId = listdep.get(j).getDeptId();
                        break;
                    }
                }
// get choicebox selected department - end

                sqlCmd = "Insert Into JavaEmployees Values(" + Integer.parseInt(txtEmpId.getText()) + ", '" + txtFName.getText()
                        + "', '" + txtLName.getText() + "', " + mgrId + ", '" + jobId + "', " + Integer.parseInt(txtSal.getText())
                        + ", " + Double.parseDouble(txtCom.getText()) + ", " + deptId + ", TO_DATE('" + dpicker.getValue().toString() + "', 'YYYY-MM-DD'))";

                stmt.executeUpdate(sqlCmd);
                displayEmpRecords();
            }
            btnUpdateNext.setVisible(false);
            btnAddNext.setVisible(false);
            btnAdd.setVisible(true);
            btnUpdate.setVisible(true);
            btnRemove.setVisible(true);
            btnLogOut.setVisible(true);
            hbox.setVisible(false);
        } catch (Exception e) {
            Alert a = new Alert(AlertType.WARNING);
            a.setTitle("WARNING");
            a.setHeaderText("Invalid Input");
            a.setContentText("Verify Input Data");
            a.show();
        }
    }

    @FXML
    public void chartDepartment() {
        btnAlt.setVisible(true);
        displayChart();
    }

    @FXML
    private void returnMain(ActionEvent event) {
        tableEmp.setVisible(true);
        pieSal.setVisible(false);
        pieCount.setVisible(false);
        btnReturn.setVisible(false);
        btnAdd.setVisible(true);
        btnUpdate.setVisible(true);
        btnRemove.setVisible(true);
        btnLogOut.setVisible(true);
        btnChart.setVisible(true);
        btnAlt.setVisible(false);
    }

    @FXML
    private void altChart() {
        if (toPercent) {
            displayChart();
            toPercent = false;
        } else {
            displayChartPercent();
            toPercent = true;
        }
    }

    public void displayChart() {
        btnAdd.setVisible(false);
        btnUpdate.setVisible(false);
        btnRemove.setVisible(false);
        btnLogOut.setVisible(false);
        btnChart.setVisible(false);
        tableEmp.setVisible(false);
        pieCount.setVisible(true);
        pieSal.setVisible(true);
        btnReturn.setVisible(true);

        try {
            sqlCmd = "Select d.department_name DEPARTMENT, SUM(e.salary) SALARY, COUNT(*) COUNT From JavaEmployees e Left Join JavaDepartments d on e.department_id = d.department_id Group By d.department_name";
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                totalSal += rs.getInt("SALARY");
                totalCount += rs.getInt("COUNT");
                chartCount.add(new PieChart.Data(rs.getString("DEPARTMENT"), rs.getInt("COUNT")));
                chartSalary.add(new PieChart.Data(rs.getString("DEPARTMENT"), rs.getInt("SALARY")));
            }
            chartSalary.forEach(data
                    -> data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), "= ", "$", data.pieValueProperty()
                            )
                    )
            );
            chartCount.forEach(data
                    -> data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), "= ", data.pieValueProperty()
                            )
                    )
            );
            pieSal.setData(chartSalary);
            pieCount.setData(chartCount);
            toPercent = false;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void displayChartPercent() {
        btnAdd.setVisible(false);
        btnUpdate.setVisible(false);
        btnRemove.setVisible(false);
        btnLogOut.setVisible(false);
        btnChart.setVisible(false);
        tableEmp.setVisible(false);
        pieCount.setVisible(true);
        pieSal.setVisible(true);
        btnReturn.setVisible(true);
        totalSal = 0;
        totalCount = 0;
        ArrayList<String> deps = new ArrayList<>();
        ArrayList<Integer> sals = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        try {
            sqlCmd = "Select d.department_name DEPARTMENT, SUM(e.salary) SALARY, COUNT(*) COUNT From JavaEmployees e Left Join JavaDepartments d on e.department_id = d.department_id Group By d.department_name";
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                totalSal += rs.getInt("SALARY");
                totalCount += rs.getInt("COUNT");
                deps.add(rs.getString("DEPARTMENT"));
                sals.add(rs.getInt("SALARY"));
                counts.add(rs.getInt("COUNT"));
            }
            for (int i = 0; i < deps.size(); i++) {
                chartCountPer.add(new PieChart.Data(deps.get(i), counts.get(i) * 100 / totalCount));
                chartSalaryPer.add(new PieChart.Data(deps.get(i), sals.get(i) * 100 / totalSal));
            }
            chartSalaryPer.forEach(data
                    -> data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), "= ", data.pieValueProperty(), "%"
                            )
                    )
            );
            chartCountPer.forEach(data
                    -> data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), "= ", data.pieValueProperty(), "%"
                            )
                    )
            );
            pieSal.setData(chartSalaryPer);
            pieCount.setData(chartCountPer);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
