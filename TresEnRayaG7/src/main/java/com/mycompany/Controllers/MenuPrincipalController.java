package com.mycompany.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    public void jugar(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/ModoDejuegos.fxml"));
        Parent ModoDeJuegoParent = loader.load();
        Scene ModoDeJuegoScene = new Scene(ModoDeJuegoParent,730,480);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ModoDeJuegoScene);
        window.show();
    }

    @FXML
    public void exitApp(MouseEvent event) {
        Platform.exit();
    }

    
}
