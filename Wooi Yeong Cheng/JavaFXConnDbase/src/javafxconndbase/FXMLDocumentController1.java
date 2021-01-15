
package javafxconndbase;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXMLDocumentController1 implements Initializable {
        @FXML
        private Label label2;
        @FXML
        private AnchorPane rootpane;
        @FXML
        private Button btn2;
        
        @FXML
   private void handleButtonAction(ActionEvent event) throws IOException {
        
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
    Parent root = loader.load();
    FXMLDocumentController first = loader.getController();

    Stage stage = new Stage();
    stage.setScene(new Scene(root));

    stage.show();
    
    Stage stage1 = (Stage) btn2.getScene().getWindow();
    stage1.close();
    }
   
   public void myFunction(String str){
        label2.setText(str);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
