package com.oracle.hr.controller.pages;

import com.oracle.hr.bean.Employee;
import com.oracle.hr.controller.components.EmpTableView;
import com.oracle.hr.service.EmployeeService;
import com.oracle.hr.service.EmployeeServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeesViewController implements Initializable {

    private EmployeeService employeeService;

    @FXML
    EmpTableView<Employee> empTableView;

    @FXML
    VBox empViewVBox;

    public EmployeesViewController(){
        employeeService = EmployeeServiceImpl.getInstance();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empTableView = new EmpTableView();
        empViewVBox.getChildren().add(empTableView);
        empTableView.getItems().addAll(employeeService.getEmployeeList());
//        empTableView.setOnMouseClicked(e -> System.out.println(empTableView.getSelectionModel().getSelectedItem()));

    }
}
