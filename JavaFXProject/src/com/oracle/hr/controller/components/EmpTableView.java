package com.oracle.hr.controller.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class EmpTableView<T> extends TableView<T> {

    @FXML
    TableColumn employeeIdColumn;

    @FXML
    TableColumn firstNameColumn;

    @FXML
    TableColumn lastNameColumn;

    @FXML
    TableColumn emailColumn;

    @FXML
    TableColumn phoneColumn;

    @FXML
    TableColumn hireDateColumn;

    @FXML
    TableColumn salaryColumn;

    @FXML
    TableColumn commissionColumn;

    @FXML
    TableColumn managerColumn;

    @FXML
    TableColumn departmentColumn;

    public EmpTableView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/components/emp_tableview.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TableColumn getEmployeeIdColumn() {
        return employeeIdColumn;
    }

    public TableColumn getFirstNameColumn() {
        return firstNameColumn;
    }

    public TableColumn getLastNameColumn() {
        return lastNameColumn;
    }

    public TableColumn getEmailColumn() {
        return emailColumn;
    }

    public TableColumn getPhoneColumn() {
        return phoneColumn;
    }

    public TableColumn getHireDateColumn() {
        return hireDateColumn;
    }

    public TableColumn getSalaryColumn() {
        return salaryColumn;
    }

    public TableColumn getCommissionColumn() {
        return commissionColumn;
    }

    public TableColumn getManagerColumn() {
        return managerColumn;
    }

    public TableColumn getDepartmentColumn() {
        return departmentColumn;
    }
}
