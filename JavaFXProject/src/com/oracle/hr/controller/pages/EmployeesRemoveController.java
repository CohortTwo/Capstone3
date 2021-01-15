package com.oracle.hr.controller.pages;

import com.oracle.hr.bean.Employee;
import com.oracle.hr.controller.components.EmpResults;
import com.oracle.hr.controller.components.EmpTableView;
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
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EmployeesRemoveController implements Initializable {

    @FXML
    VBox empRemoveVBox;

    EmpResults empResults;

    EmpTableView<Employee> empTableView;

    @FXML
    Button removeButton;

    private EmployeeService employeeService;

    public EmployeesRemoveController() {
        employeeService = EmployeeServiceImpl.getInstance();
        empResults = new EmpResults();
        empTableView = new EmpTableView<>();
    }

    private void disableAllEmpResults(){
        empResults.getFirstNameField().setDisable(true);
        empResults.getLastNameField().setDisable(true);
        empResults.getEmailField().setDisable(true);
        empResults.getPhoneField().setDisable(true);
        empResults.getSalaryField().setDisable(true);
        empResults.getHireDateField().setDisable(true);
        empResults.getManagerField().setDisable(true);
        empResults.getJobField().setDisable(true);
        empResults.getCommissionField().setDisable(true);
        empResults.getDepartmentField().setDisable(true);
    }

    @FXML
    private void removeEmployee(Event e){
        Employee emp = empTableView.getSelectionModel().getSelectedItem();
        employeeService.deleteEmployee(emp);
        loadTableView();
    }

    private void loadTableView(){
        empTableView.getItems().clear();
        empTableView.getItems().addAll(employeeService.getEmployeeList());
    }

    private void addTableViewOnClickListener(){
        empTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
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
                removeButton.setDisable(false);
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableAllEmpResults();
        addTableViewOnClickListener();
        empRemoveVBox.getChildren().add(empResults);
        empRemoveVBox.getChildren().add(empTableView);
        loadTableView();
    }

}
