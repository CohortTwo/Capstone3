/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empmgtfxapp;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author chiangyong
 */
public class EmpMgtFXapp extends Application {
    
    @Override
    public void start(Stage priStage) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLEmpMgt.fxml"));
        
        Scene scene = new Scene(root);
        
        priStage.setTitle("Organisation Management System");
        priStage.getIcons().add(new Image("/image/iconTest.png"));
        priStage.setScene(scene);
        priStage.show();
    }

    public Connection getConnected(){
        try 
        {
            Connection conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@localhost:1521:prod", "heng", "heng");
        
           return conn; 
        }
        
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
