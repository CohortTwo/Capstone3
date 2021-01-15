package com.oracle.hr.controller.pages;

import com.oracle.hr.controller.components.EmpMenuBar;
import com.oracle.hr.controller.components.EmpStatistics;
import com.oracle.hr.service.EmployeeService;
import com.oracle.hr.service.EmployeeServiceImpl;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeesHomeController implements Initializable {

    @FXML
    BorderPane empHomeWindow;

    @FXML
    EmpMenuBar empMenuBar;

    @FXML
    EmpStatistics empStatistics;

    public EmployeesHomeController(){
        this.empStatistics = new EmpStatistics();
        this.empMenuBar = new EmpMenuBar();
    }


    private void loadEmployeeBarChart() {
        empStatistics.getEmployeeBarChart().getData().clear();
        XYChart.Series series = new XYChart.Series();
        EmployeeService employeeService = EmployeeServiceImpl.getInstance();
        List<XYChart.Data> employeeList = employeeService.getEmployeeList().stream()
                .map(e -> new XYChart.Data(e.getFirstName() + " " + e.getLastName(),e.getSalary()))
                .collect(Collectors.toList());
        series.getData().addAll(employeeList);
        series.setName("Last Name");
        empStatistics.getEmployeeBarChart().getData().add(series);
    }


    private void loadEmployeePieChar() {
        empStatistics.getEmployeePieChart().getData().clear();
        EmployeeService employeeService = EmployeeServiceImpl.getInstance();
        List<PieChart.Data> employeeList = employeeService.getEmployeeList().stream()
                .map(e -> new PieChart.Data(e.getFirstName() + " " + e.getLastName(),e.getSalary())).collect(Collectors.toList());
        empStatistics.getEmployeePieChart().getData().addAll(employeeList);
        Integer sum = employeeService.getEmployeeList().stream().map(e -> e.getSalary()).reduce(0, (a, b) -> a + b);
        ObservableList<PieChart.Data> list = empStatistics.getEmployeePieChart().getData();
        list.stream().forEach(d -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText((d.getPieValue()/sum*100.00) + "%");
            Tooltip.install(d.getNode(),tooltip);
        });
    }

    private void addMenuBarOnClickListener(){
        empMenuBar.getAdd().setOnMouseClicked(e -> switchViews(e));
        empMenuBar.getEdit().setOnMouseClicked(e -> switchViews(e));
        empMenuBar.getHome().setOnMouseClicked(e -> switchViews(e));
        empMenuBar.getRemove().setOnMouseClicked(e -> switchViews(e));
        empMenuBar.getView().setOnMouseClicked(e -> switchViews(e));
    }


    private void switchViews(Event e){
        Label label = (Label)(e.getSource());
        String resourceFile;
        switch (label.getText().toLowerCase()){
            case "add":
                resourceFile = "../../fxml/pages/employees_add.fxml";
                break;
            case "edit":
                resourceFile = "../../fxml/pages/employees_edit.fxml";
                break;
            case "remove":
                resourceFile = "../../fxml/pages/employees_remove.fxml";
                break;
            case "view":
                resourceFile = "../../fxml/pages/employees_view.fxml";
                break;
            default:
                empHomeWindow.setCenter(empStatistics);
                return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resourceFile));
        Window window = empMenuBar.getScene().getWindow();
        BorderPane borderPane = (BorderPane) window.getScene().getRoot();
        try {
            borderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmployeeBarChart();
        loadEmployeePieChar();
        addMenuBarOnClickListener();
        empHomeWindow.setTop(empMenuBar);
        empHomeWindow.setCenter(empStatistics);
    }
}
