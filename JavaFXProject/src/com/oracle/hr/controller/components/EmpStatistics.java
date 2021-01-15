package com.oracle.hr.controller.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

public class EmpStatistics extends ScrollPane {

    @FXML
    private PieChart employeePieChart;

    @FXML
    private BarChart employeeBarChart;

    public EmpStatistics(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/components/emp_statistics.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PieChart getEmployeePieChart() {
        return employeePieChart;
    }

    public BarChart getEmployeeBarChart() {
        return employeeBarChart;
    }
}
