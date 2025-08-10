package com.mycompany.Controllers;

import com.mycompany.Modelos.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class SeleccionarSimboloController implements Initializable {
    @FXML
    private StackPane sp1;
    @FXML
    private StackPane sp2;
    private Resultado r;
    private boolean cpu1;
    private boolean cpu2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializar(Resultado r, boolean cpu1,boolean cpu2){
        this.r=r;
        this.cpu1=cpu1;
        this.cpu2=cpu2;
        Image im1 = new Image("com/mycompany/images/papel_simbolo1.png");
        Image im2 = new Image("com/mycompany/images/X.png");
        ImageView iv1 = new ImageView(im1);
        iv1.setFitHeight(350);
        iv1.setFitWidth(350);
        iv1.setTranslateX(15);
        ImageView iv2 = new ImageView(im2);
        iv2.setFitHeight(175);
        iv2.setFitWidth(175);
        iv2.setUserData("X");
        iv2.setOnMouseClicked(this::fichaElegida);
        iv2.setTranslateX(15);
        sp1.getChildren().addAll(iv1,iv2);
        Image im3 = new Image("com/mycompany/images/papel_simbolo2.png");
        Image im4 = new Image("com/mycompany/images/O.png");
        ImageView iv3 = new ImageView(im3);
        iv3.setFitHeight(350);
        iv3.setFitWidth(350);
        ImageView iv4 = new ImageView(im4);
        iv4.setFitHeight(175);
        iv4.setFitWidth(175);
        iv4.setUserData("O");
        iv4.setOnMouseClicked(this::fichaElegida);
        sp2.getChildren().addAll(iv3,iv4);
    }
    private void fichaElegida(MouseEvent event){
        ImageView imageViewClickeado = (ImageView) event.getSource();
        String tipoFicha = (String) imageViewClickeado.getUserData();
        Jugador j1;
        Jugador j2;
        if(tipoFicha.equals("X") && r.getTipoResul().equals(TipoResul.PorVidas)){
            j1 = new Jugador(1,tipoFicha,r.getCantidad());
            j2= new Jugador(2,"O",r.getCantidad());
        }
        else if(tipoFicha.equals("O")&& r.getTipoResul().equals(TipoResul.PorVidas)){
            j1 = new Jugador(1,tipoFicha,r.getCantidad());
            j2= new Jugador(2,"X",r.getCantidad());
        }
        else if(tipoFicha.equals("X")&& r.getTipoResul().equals(TipoResul.PorPuntaje)){
            j1 = new Jugador(1,tipoFicha,0);
            j2= new Jugador(2,"O",0);
        }
        else{
            j1 = new Jugador(1,tipoFicha,0);
            j2= new Jugador(2,"X",0);
        }
        j1.setCpu(cpu1);
        j2.setCpu(cpu2);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/TipoFichas.fxml"));
        Parent fichasParent=null;
        try{
            fichasParent = loader.load();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        Scene fichasScene = new Scene(fichasParent,730,480);
        TipoFichasController tipoFichasController = loader.getController();
        tipoFichasController.inicializar(j1, j2,r);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(fichasScene);
        window.show(); 
    }
    
}
