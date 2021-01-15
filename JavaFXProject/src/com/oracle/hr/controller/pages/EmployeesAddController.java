package com.oracle.hr.controller.pages;

import com.oracle.hr.bean.Department;
import com.oracle.hr.bean.Employee;
import com.oracle.hr.controller.components.EmpResults;
import com.oracle.hr.service.DepartmentService;
import com.oracle.hr.service.DepartmentServiceImpl;
import com.oracle.hr.service.EmployeeService;
import com.oracle.hr.service.EmployeeServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeesAddController implements Initializable {

    @FXML
    VBox empAddVbox;

    EmpResults empResults;

    @FXML
    Button addButton;

    DepartmentService departmentService;

    EmployeeService employeeService;

    public EmployeesAddController(){
        employeeService = EmployeeServiceImpl.getInstance();
        departmentService = DepartmentServiceImpl.getInstance();
    }

    @FXML
    private void addEmployee(){
        Map<String,List<Employee>> employeeMap = employeeService.getEmployeeList().stream().collect(Collectors.groupingBy(e -> e.getFirstName() + " " + e.getLastName()));
        Employee employee = new Employee(empResults.getFirstNameField().getText(),
                empResults.getLastNameField().getText(),
                empResults.getEmailField().getText(),
                empResults.getPhoneField().getText(),
                Date.from(empResults.getHireDateField().getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                empResults.getJobField().getValue(),
                Integer.valueOf(empResults.getSalaryField().getText()),
                Double.valueOf(empResults.getCommissionField().getText()),
                employeeMap.get(empResults.getManagerField().getValue()).stream().findAny().get().getEmployeeId(),
                departmentService.findDepartmentFromDepartmentName(empResults.getDepartmentField().getValue()).getDepartmentId()
                );

        employeeService.createEmployee(employee);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empResults = new EmpResults();
        List<Employee> employeeList = employeeService.getEmployeeList();
        empResults.getDepartmentField().getItems().clear();
        empResults.getJobField().getItems().clear();
        empResults.getDepartmentField().getItems().addAll(
                employeeList.stream().map(e -> e.getDepartment().getDepartmentName()).collect(Collectors.toSet()));
        empResults.getJobField().getItems().addAll(employeeList.stream().map(e -> e.getJobId()).collect(Collectors.toSet()));
        empResults.getManagerField().getItems().addAll(employeeService.getManagerList().stream().map(e -> e.getFirstName() + " " + e.getLastName()).collect(Collectors.toSet()));
        empAddVbox.getChildren().add(empResults);
    }
}
