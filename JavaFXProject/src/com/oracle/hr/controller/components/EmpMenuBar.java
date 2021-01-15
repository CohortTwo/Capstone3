package com.oracle.hr.controller.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class EmpMenuBar extends MenuBar {

    @FXML
    private Label home;

    @FXML
    private Label add;

    @FXML
    private Label edit;

    @FXML
    private Label remove;

    @FXML
    private Label view;

    public EmpMenuBar(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../fxml/components/emp_menubar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Label getHome() {
        return home;
    }

    public Label getAdd() {
        return add;
    }

    public Label getEdit() {
        return edit;
    }

    public Label getRemove() {
        return remove;
    }

    public Label getView() {
        return view;
    }
}
