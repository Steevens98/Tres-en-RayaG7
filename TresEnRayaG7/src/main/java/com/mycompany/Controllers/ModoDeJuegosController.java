package com.mycompany.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ModoDeJuegosController implements Initializable {
    @FXML
    private ImageView tradicional;
    private int indiceImg = 0;
    private String[] imagesTempTrad = {"tradicional1.png", "tradicional2.png", "tradicional3.png", "tradicional4.png", "tradicional5.png", "tradicional6.png"};
    private CircularLinkedList<String> imagenesTrad = new CircularLinkedList<>(imagesTempTrad);
    private ScaleTransition scaleTransition;
    @FXML
    private ImageView returnB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void cambiarImgT(){
        indiceImg=(indiceImg+1)%imagenesTrad.size();
        String nombreImg = imagenesTrad.get(indiceImg);
        Image img = new Image("com/mycompany/images/"+nombreImg);
        tradicional.setImage(img);
    }
    
    private void restaurarOgT(){
        indiceImg=0;
        Image img = new Image("com/mycompany/images/"+imagenesTrad.get(indiceImg));
        tradicional.setImage(img);
    }

    @FXML
    private void restaurarImg(MouseEvent event) {
        scaleTransition.stop(); // Detener la transición actual si está en progreso
        restaurarOgT();
        tradicional.setScaleX(1); // Restablecer la escala en el eje X
        tradicional.setScaleY(1); // Restablecer la escala en el eje Y
    }

    @FXML
    private void agrandarImg(MouseEvent event) {
        // Escalar la imagen
        scaleTransition = new ScaleTransition(Duration.millis(300), tradicional);
        scaleTransition.setToX(1.25);
        scaleTransition.setToY(1.25);
        // Cambiar de imagen durante la transición de opacidad
        scaleTransition.setOnFinished(e -> cambiarImagenYReproducirAnimacion(scaleTransition));
        // Reproducir la animación
        scaleTransition.play();
    }

    private void cambiarImagenYReproducirAnimacion(ScaleTransition scaleTransition) {
        // Cambiar a la siguiente imagen
        cambiarImgT();
        // Reiniciar las transiciones
        scaleTransition.setRate(0.75);
        // Reproducir la animación nuevamente
        scaleTransition.play();
    }
    
    @FXML
    private void elegidoT(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/ModoResultados.fxml"));
        Parent jugadoresTParent = loader.load();
        Scene jugadoresTScene = new Scene(jugadoresTParent,730,480);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(jugadoresTScene);
        window.show();
    }
    
    @FXML
    private void handCambiarReturnB(MouseEvent event) {
        this.returnB.setCursor(Cursor.HAND);
    }
    
    @FXML
    private void returnS(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/MenuPrincipal.fxml"));
        Parent menuParent = loader.load();
        Scene menuScene = new Scene(menuParent, 730, 480);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }
    
}
