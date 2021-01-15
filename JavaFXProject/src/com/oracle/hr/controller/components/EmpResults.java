package com.oracle.hr.controller.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EmpResults extends GridPane {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField salaryField;

    @FXML
    private DatePicker hireDateField;

    @FXML
    private ComboBox<String> managerField;

    @FXML
    private ComboBox<String> jobField;

    @FXML
    private TextField commissionField;

    @FXML
    private ComboBox<String> departmentField;

    public EmpResults(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/components/emp_results.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getSalaryField() {
        return salaryField;
    }

    public DatePicker getHireDateField() {
        return hireDateField;
    }

    public ComboBox<String> getManagerField() {
        return managerField;
    }

    public ComboBox<String> getJobField() {
        return jobField;
    }

    public TextField getCommissionField() {
        return commissionField;
    }

    public ComboBox<String> getDepartmentField() {
        return departmentField;
    }
}
