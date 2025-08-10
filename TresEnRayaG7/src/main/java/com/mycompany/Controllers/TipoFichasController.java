package com.mycompany.Controllers;

import com.mycompany.Modelos.Jugador;
import com.mycompany.Modelos.Resultado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class TipoFichasController implements Initializable {
    private Label j1;
    private Label j2;
    private Resultado r;
    
    private Jugador p1;
    private Jugador p2;
    @FXML
    private StackPane sp1;
    @FXML
    private StackPane sp2;
    @FXML
    private BorderPane bp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializar(Jugador player1,Jugador player2, Resultado r){
        this.r=r;
        Image im1 = new Image("com/mycompany/images/trozoJ1.png");
        Image im2 = new Image("com/mycompany/images/trozoJ2.png");
        ImageView iv1 = new ImageView(im1);
        ImageView iv2 = new ImageView(im1);
        p1=player1;
        p2=player2;
        j1=new Label("Jugador "+p1.getId()+" "+p1.getTipoSimbolo());
        j1.setFont(new javafx.scene.text.Font("Arial", 36));
        j1.setPrefWidth(200);
        j1.setPrefHeight(54);
        j2=new Label("Jugador "+p2.getId()+" "+p2.getTipoSimbolo());
        j2.setFont(new javafx.scene.text.Font("Arial", 36));
        j2.setPrefWidth(200);
        j2.setPrefHeight(54);
        sp1.getChildren().addAll(iv1,j1);
        sp1.setTranslateY(90);
        sp2.getChildren().addAll(iv2,j2);
        sp2.setTranslateY(-80);
    }

    @FXML
    private void cambiarPag(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/LanzadaDados.fxml"));
        Parent dadosParent = loader.load();
        Scene dadosScene = new Scene(dadosParent,730,480);
        LanzadaDadosController lanzadaDadosController = loader.getController();
        lanzadaDadosController.inicializar(p1, p2,r);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dadosScene);
        window.show();
    }
    
}
