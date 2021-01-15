package emprecordmanagement;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MgrMainController implements Initializable {

    private TextField txteid;
    private TextField txtename;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("2 Scene 2 ===> here");
        System.out.println("come here to menu exit from scene 2");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EmpMain.fxml"));
        loader.load();

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Employee Management");
        stage.setScene(new Scene(p));
        stage.setMaximized(true);
        stage.show();

        Stage stage1 = (Stage) tblvmgr.getScene().getWindow();
        stage1.close();

    }

    @FXML
    private TableView<Managers> tblvmgr;

    @FXML
    private TableColumn<Managers, Integer> coleid;
    @FXML
    private TableColumn<Managers, String> colename;
    @FXML
    private MenuItem mainmenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayMgrRecords();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
            return conn;
        } catch (Exception e) {
            return null;
        }

    }

    public ObservableList<Managers> getManagerList() {
        ObservableList<Managers> emplist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT DISTINCT E.ENAME,E.EMPNO FROM EMP E JOIN EMP Y ON E.EMPNO=Y.MGR";

        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            Managers emp;
            while (rs.next()) {
                emp = new Managers(rs.getInt("empno"), rs.getString("ename"));
                emplist.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return emplist;
    }

    public void displayMgrRecords() {
        ObservableList<Managers> listemp = getManagerList();
        coleid.setCellValueFactory(new PropertyValueFactory<Managers, Integer>("eid"));
        colename.setCellValueFactory(new PropertyValueFactory<Managers, String>("ename"));
        tblvmgr.setItems(listemp);

    }

}
