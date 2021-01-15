/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_ems_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Desmond
 */
public class FXMLDocumentController implements Initializable {

    private Label label;

    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField delEmpTxt;

    private Boolean check;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        System.out.println("Triggered Delete");
        int x = Integer.parseInt(delEmpTxt.getText());
        AddNewEmpController test = new AddNewEmpController();

        ObservableList<Employees> listemp = test.getEmployeeList();

        for (int i = 0; i < listemp.size(); i++) {
            if (listemp.get(i).getEmpID() == x) {
                test.removeRecord(x);
            } else {
//                System.out.println("Invalid Employee Number Entered!");
            }
        }

        
        // Closing the Current Scene.
        Stage stage2 = (Stage) confirmBtn.getScene().getWindow();
        stage2.close();
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) throws Exception {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
