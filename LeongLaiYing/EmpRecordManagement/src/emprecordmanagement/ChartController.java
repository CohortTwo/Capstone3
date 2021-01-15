/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emprecordmanagement;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author LaiYing
 */
public class ChartController implements Initializable {


    @FXML
    private PieChart piesal;

    @FXML
    private BarChart<?, ?> barsal;

    @FXML
    private AnchorPane rootpane;
    @FXML
    private Button btnPie;
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpMain.fxml"));
        Parent root = loader.load();
        EmpMainController emp = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add("resources/css/chart.css");

        stage.show();

// Closing the Current Scene.
        
        Stage stage1 = (Stage)  rootpane.getScene().getWindow();
        stage1.close();

    }
    

    public void ShowBar(ObservableList<Employees> emplist) {
        XYChart.Series set = new XYChart.Series<>();
        barsal.getData().clear();
        emplist.forEach((Employees) -> set.getData().add(new XYChart.Data(Employees.getEname(), Employees.getSal())));
        barsal.getData().addAll(set);
        ShowPie(emplist);
    }

    public void ShowPie(ObservableList<Employees> emplist) {
        
        Double total;

        ObservableList<PieChart.Data> piedata = FXCollections.observableArrayList();
        
        emplist.forEach((Employees) ->
            piedata.add(new PieChart.Data(Employees.getEname(), Employees.getSal())));

        total = piedata.stream()
                .mapToDouble(data -> data.getPieValue())
                .reduce(0, Double::sum);

        piedata.forEach(data -> {
            String percentage = String.format("%.2f%%", (data.getPieValue() * 100 / total));
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
            data.setName(data.getName() + " " + percentage);
            piesal.setData(piedata);
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }

}
