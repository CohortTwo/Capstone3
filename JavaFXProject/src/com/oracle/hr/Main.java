package com.oracle.hr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader employeesLoader = new FXMLLoader();
        employeesLoader.setLocation(getClass().getResource("fxml/pages/employees_home.fxml"));
        BorderPane root = employeesLoader.load();
        primaryStage.setScene(new Scene(root,1000,600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
