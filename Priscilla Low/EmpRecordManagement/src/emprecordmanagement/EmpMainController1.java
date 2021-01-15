/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emprecordmanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author blueg
 */
public class EmpMainController1 implements Initializable {
    
    @FXML
    private Button goback;
    @FXML
    private BarChart<String, Number> bar1;
    @FXML
    private NumberAxis salY;
    @FXML
    private CategoryAxis deptnoX;
    @FXML
    private MenuBar menubar;
    @FXML
    private Menu menufile;
    @FXML
    private MenuItem fmclose;
    @FXML
    private Menu menuedit;
    @FXML
    private Menu menuhelp;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showBar();

    }    
    
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:prod", "xx", "xx");
            return conn;
        }catch(Exception e){
            return null;
        }
    }
    
    public ObservableList<Employees> getEmployeeList() {
        ObservableList<Employees> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "select * from emp";
        Statement stmt;
        ResultSet rs;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            Employees emp;
            while (rs.next()) {
                String strDate = dateFormat.format(rs.getDate("hiredate"));
                //System.out.println(strDate);
                emp = new Employees(rs.getInt("empno"), rs.getString("ename"), 
                        rs.getString("job"), rs.getInt("mgr"), 
                        strDate, rs.getInt("sal"), 
                        rs.getInt("comm"), rs.getInt("deptno"));
                emplist.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return emplist;
    }


    @FXML
    private void changeScene1(ActionEvent event) throws IOException {
        //navigate from one scene to another with instance referencing the values
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpMain.fxml"));
        Parent root = loader.load();
       
        EmpMainController firstwindow = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Employees Management System");
        stage.show();
        
        Stage stage1 = (Stage) goback.getScene().getWindow();
        stage1.close();
    }
       
    
    private void showBar(){
        String query = "SELECT deptno, sum(sal) FROM emp GROUP BY deptno ORDER BY deptno";
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        try{
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery(query);
            while(rs.next()){
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            bar1.getData().add(series);
        }catch(Exception e){
            e.printStackTrace();
        }
       
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
}
