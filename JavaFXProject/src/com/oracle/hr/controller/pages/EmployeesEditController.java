package com.oracle.hr.controller.pages;

import com.oracle.hr.bean.Employee;
import com.oracle.hr.controller.components.EmpResults;
import com.oracle.hr.controller.components.EmpTableView;
import com.oracle.hr.service.DepartmentService;
import com.oracle.hr.service.DepartmentServiceImpl;
import com.oracle.hr.service.EmployeeService;
import com.oracle.hr.service.EmployeeServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeesEditController implements Initializable {

    @FXML
    VBox empEditVBox;

    EmpResults empResults;

    EmpTableView<Employee> empTableView;

    @FXML
    Button editButton;

    private EmployeeService employeeService;

    private DepartmentService departmentService;

    public EmployeesEditController() {
        employeeService = EmployeeServiceImpl.getInstance();
        departmentService = DepartmentServiceImpl.getInstance();
        empResults = new EmpResults();
        empTableView = new EmpTableView<>();
    }

    @FXML
    private void updateEmployee(Event e){
        Employee emp = empTableView.getSelectionModel().getSelectedItem();
        emp.setFirstName(empResults.getFirstNameField().getText());
        emp.setLastName(empResults.getLastNameField().getText());
        emp.setEmail(empResults.getEmailField().getText());
        emp.setPhoneNumber(empResults.getPhoneField().getText());
//        emp.setHireDate(Date.from(empResults.getHireDateField().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        emp.setJobId(empResults.getJobField().getValue());
        emp.setSalary(Integer.valueOf(empResults.getSalaryField().getText()));
        emp.setCommisionPct(Double.valueOf(empResults.getCommissionField().getText()));
        emp.setManagerId(Integer.valueOf(empResults.getManagerField().getValue()));
        emp.setDepartmentId(departmentService.findDepartmentFromDepartmentName(empResults.getDepartmentField().getValue()).getDepartmentId());
        employeeService.updateEmployee(emp);
        editButton.setDisable(true);
        loadTableView();
    }

    private void loadTableView(){
        empTableView.getItems().clear();
        empTableView.getItems().addAll(employeeService.getEmployeeList());
    }

    private void addTableViewOnClickListener(){
        empTableView.setOnMouseClicked(mouseEvent -> {
            Employee e = empTableView.getSelectionModel().getSelectedItem();
            empResults.getFirstNameField().setText(e.getFirstName());
            empResults.getLastNameField().setText(e.getLastName());
            empResults.getEmailField().setText(e.getEmail());
            empResults.getPhoneField().setText(e.getPhoneNumber());
            empResults.getSalaryField().setText(String.valueOf(e.getSalary()));
            empResults.getHireDateField().setValue(((java.sql.Date) e.getHireDate()).toLocalDate());
            empResults.getManagerField().setItems(FXCollections.observableArrayList(String.valueOf(e.getManagerId())));
            empResults.getManagerField().getSelectionModel().selectFirst();
            empResults.getJobField().setItems(FXCollections.observableArrayList(e.getJobId()));
            empResults.getJobField().getSelectionModel().selectFirst();
            empResults.getCommissionField().setText(String.valueOf(e.getCommisionPct()));
            empResults.getDepartmentField().setItems(FXCollections.observableArrayList(String.valueOf(e.getDepartment().getDepartmentName())));
            empResults.getDepartmentField().getSelectionModel().selectFirst();
            editButton.setDisable(false);
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTableViewOnClickListener();
        empEditVBox.getChildren().add(empResults);
        empEditVBox.getChildren().add(empTableView);
        loadTableView();
    }
}
