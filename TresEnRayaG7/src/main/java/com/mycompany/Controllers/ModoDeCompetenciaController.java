package com.mycompany.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
public class ModoDeCompetenciaController implements Initializable {
    private ImageView tradicional;
    private int indiceImg = 0;
    private Map<String, String> mapa = new HashMap<>();
    private String[] imagesTempTrad = {"j_vs_j.png", "j_vs_cpu.png", "cpu_vs_cpu.png"};
    private CircularLinkedList<String> imagenesTrad = new CircularLinkedList<>(imagesTempTrad);
    private ListIterator<String> it = imagenesTrad.listIterator();
    private ScaleTransition scaleTransition;
    private Resultado r;
    @FXML
    private ImageView leftarrow;
    @FXML
    private ImageView modes;
    @FXML
    private ImageView rightarrow;
    @FXML
    private Label labelmodes;
    @FXML
    private CheckBox fichas;
    @FXML
    private ImageView returnB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void inicializar(Resultado r){
        this.r=r;
        mapa.put("j_vs_cpu.png", "J vs CPU");
        mapa.put("cpu_vs_cpu.png", "CPU vs CPU");
        mapa.put("j_vs_j.png", "J vs J");
    }
    
    @FXML
    private void restaurarImg(MouseEvent event) {
        scaleTransition.stop(); // Detener la transición actual si está en progreso
        modes.setScaleX(1); // Restablecer la escala en el eje X
        modes.setScaleY(1); // Restablecer la escala en el eje Y
    }

    @FXML
    private void agrandarImg(MouseEvent event) {
        // Escalar la imagen
        scaleTransition = new ScaleTransition(Duration.millis(300), modes);
        scaleTransition.setToX(1.25);
        scaleTransition.setToY(1.25);

        // Reproducir la animación
        scaleTransition.play();
    }

    @FXML
    private void cambiarImgR(MouseEvent event){
        String name = it.next();
        Image img = new Image("com/mycompany/images/"+name);
        modes.setImage(img);
        labelmodes.setText(mapa.get(name));
    }
    
    @FXML
    private void cambiarImgL(MouseEvent event){
        String name = it.previous();
        System.out.println(name);
        Image img = new Image("com/mycompany/images/"+name);
        modes.setImage(img);
        labelmodes.setText(mapa.get(name));
    }
    
     @FXML
    private void handCambiarImgL(MouseEvent event) {
        leftarrow.setCursor(Cursor.HAND);
        
    }

    @FXML
    private void handCambiarImgR(MouseEvent event) {
        rightarrow.setCursor(Cursor.HAND);
    }

    @FXML
    private void handCambiarReturnB(MouseEvent event) {
        this.returnB.setCursor(Cursor.HAND);
    }
    
    @FXML
    private void elegido(MouseEvent event) throws IOException {
        String mode = labelmodes.getText();
        if(mode.equals("J vs CPU")){
            if(!fichas.isSelected()){
                ArrayList<Jugador> players = Jugador.randomizarSimbolos();
                Jugador j1 = players.get(0);
                Jugador j2 = players.get(1);
                j2.setCpu(true);
                if(r.getTipoResul().equals(TipoResul.PorVidas)){
                    j1.setPuntuacion(r.getCantidad());
                    j2.setPuntuacion(r.getCantidad());
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/TipoFichas.fxml"));
                Parent fichasParent = loader.load();
                Scene fichasScene = new Scene(fichasParent,730,480);
                TipoFichasController tipoFichasController = loader.getController();
                tipoFichasController.inicializar(j1, j2,r);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(fichasScene);
                window.show();    
            }
            else{
                boolean cpu1=false;
                boolean cpu2= true;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/SeleccionarSimbolo.fxml"));
                Parent fichasParent = loader.load();
                Scene fichasScene = new Scene(fichasParent,730,480);
                SeleccionarSimboloController seleccionarController = loader.getController();
                seleccionarController.inicializar(r, cpu1, cpu2);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(fichasScene);
                window.show(); 
            }
        }
        else if(mode.equals("CPU vs CPU")){
            if(!fichas.isSelected()){
                ArrayList<Jugador> players = Jugador.randomizarSimbolos();
                Jugador j1 = players.get(0);
                j1.setCpu(true);
                Jugador j2 = players.get(1);
                j2.setCpu(true);
                if(r.getTipoResul().equals(TipoResul.PorVidas)){
                    j1.setPuntuacion(r.getCantidad());
                    j2.setPuntuacion(r.getCantidad());
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/TipoFichas.fxml"));
                Parent fichasParent = loader.load();
                Scene fichasScene = new Scene(fichasParent,730,480);
                TipoFichasController tipoFichasController = loader.getController();
                tipoFichasController.inicializar(j1, j2,r);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(fichasScene);
                window.show();    
            }
            else{
                boolean cpu1= true;
                boolean cpu2=true;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/SeleccionarSimbolo.fxml"));
                Parent fichasParent = loader.load();
                Scene fichasScene = new Scene(fichasParent,730,480);
                SeleccionarSimboloController seleccionarController = loader.getController();
                seleccionarController.inicializar(r, cpu1, cpu2);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(fichasScene);
                window.show(); 
            }
        }
        else{
            if(!fichas.isSelected()){
                ArrayList<Jugador> players = Jugador.randomizarSimbolos();
                Jugador j1 = players.get(0);
                Jugador j2 = players.get(1);
                if(r.getTipoResul().equals(TipoResul.PorVidas)){
                    j1.setPuntuacion(r.getCantidad());
                    j2.setPuntuacion(r.getCantidad());
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/TipoFichas.fxml"));
                Parent fichasParent = loader.load();
                Scene fichasScene = new Scene(fichasParent,730,480);
                TipoFichasController tipoFichasController = loader.getController();
                tipoFichasController.inicializar(j1, j2,r);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(fichasScene);
                window.show();    
            }
            else{
                boolean cpu1=false;
                boolean cpu2= false;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/SeleccionarSimbolo.fxml"));
                Parent fichasParent = loader.load();
                Scene fichasScene = new Scene(fichasParent,730,480);
                SeleccionarSimboloController seleccionarController = loader.getController();
                seleccionarController.inicializar(r, cpu1, cpu2);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(fichasScene);
                window.show(); 
            }
        }
    }

    @FXML
    private void returnS(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/ModoResultados.fxml"));
        Parent Modos_de_juegoParent = loader.load();
        Scene Modos_de_juegoScene = new Scene(Modos_de_juegoParent,730,480);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Modos_de_juegoScene);
        window.show(); 
    }
    
}
