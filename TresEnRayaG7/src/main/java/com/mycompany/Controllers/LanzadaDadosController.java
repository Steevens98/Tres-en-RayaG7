package com.mycompany.Controllers;

import com.mycompany.Modelos.Dado;
import com.mycompany.Modelos.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class LanzadaDadosController implements Initializable {
    @FXML
    private StackPane player;
    @FXML
    private ImageView dado;
    private Dado dice;
    private Jugador p1;
    private Jugador p2;
    private int lanzadas=2;
    @FXML
    private Label mensaje;
    private Resultado r;
    private boolean lanzarCPU = false;
    private int contadorCPULanzamientos = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializar(Jugador j1,Jugador j2,Resultado r){
        this.r=r;
        p1=j1;
        p2=j2;
        dice=new Dado();
        if(p1.isCpu() && p2.isCpu()){
            lanzarCPU();
            System.out.println("lanzarcpu boolean: "+lanzarCPU);
                if (contadorCPULanzamientos < 1) {
                    // Configura un temporizador para ejecutar lanzarCPU() después de un segundo (ajusta según sea necesario)
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            lanzarCPU();  // Llama al segundo lanzarCPU después del temporizador
                            contadorCPULanzamientos++;
                        }
                    }));
                    timeline.play();
                }
            
        }
    }
    @FXML
    private void lanzar(MouseEvent event) {
        if (lanzadas>0&&!dice.isLanzado()) {
            // Lanzar el dado
            ResultadoDado resultado = dice.lanzar();

            // Establecer el GIF durante 1 segundo
            dado.setImage(new Image(dice.getMov())); // Reemplaza con la ruta correcta
            
            // Configurar un retraso de 1 segundo antes de mostrar el resultado
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                mostrarResultado(resultado.getLado(), resultado.getNumero());
                if(dice.repetirTirada(p1, p2)){
                    lanzadas++;
                    dice.reiniciar();
                    Util.mostrarMensaje("Ha ocurrido un empate, jugador 2 repita su lanzamiento", "Numeros iguales");
                    return;
                }
                dice.reiniciar();
                if (!p1.isCpu() && !p2.isCpu()){
                  cambiarMensaje();  
                }
                
                
                // Verificar si todas las lanzadas han sido realizadas
                if (lanzadas == 0) {
                    // Configurar un retraso antes de cambiar de página
                    PauseTransition cambioDePagina = new PauseTransition(Duration.seconds(1));
                    cambioDePagina.setOnFinished(ev -> {
                        try {
                            jugar(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    cambioDePagina.play();
                }
            });
                if (p1.isCpu() || p2.isCpu()) {
                    System.out.println("Prueba para dados");
                // Configurar un temporizador para ejecutar lanzarCPU() después de un segundo (ajusta según sea necesario)
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Prueba para timeline");
                        lanzarCPU();
                    }
                }));
                timeline.play();
                
                }
            pause.play();
            lanzarCPU = true;
        }
    }
    
    private void lanzarCPU(){
        System.out.println("ENTRANDO A LANZAR CPU");
        cambiarMensajeCPU();
        if (lanzadas>0){
            System.out.println("SE CUMPLE EL IF");
             ResultadoDado resultado = dice.lanzar();
             System.out.println("SI LO LANZA");
            // Establecer el GIF durante 1 segundo
            // Configurar un retraso de 1 segundo antes de cambiar la imagen del dado
        PauseTransition pauseBeforeImageChange = new PauseTransition(Duration.seconds(1));
        pauseBeforeImageChange.setOnFinished(event -> {
            // Cambiar la imagen del dado después del retraso
            Platform.runLater(() -> {
                dado.setImage(new Image(dice.getMov()));
            });

        // Configurar un retraso de 1 segundo antes de mostrar el resultado
        PauseTransition pauseBeforeResult = new PauseTransition(Duration.seconds(1));
        pauseBeforeResult.setOnFinished(e -> {
            System.out.println("entra al pause");
            mostrarResultado(resultado.getLado(), resultado.getNumero());
            if(dice.repetirTirada(p1, p2)){
                lanzadas++;
                dice.reiniciar();
                Util.mostrarMensajeCPU("Ha ocurrido un empate, jugador 2 repita su lanzamiento", "Numeros iguales");
               PauseTransition pauseBeforeLanzarCPU = new PauseTransition(Duration.seconds(1.5));
                pauseBeforeLanzarCPU.setOnFinished(eventT -> {
                    lanzarCPU();
                });
                pauseBeforeLanzarCPU.play();
            }

            // Verificar si todas las lanzadas han sido realizadas
            if (lanzadas == 0) {
                // Configurar un retraso antes de cambiar de página
                PauseTransition cambioDePagina = new PauseTransition(Duration.seconds(1));
                cambioDePagina.setOnFinished(ev -> {
                    try {
                        jugar();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                cambioDePagina.play();
            }
        });

        System.out.println("OCURRE LO DE LA PAUSA");
        pauseBeforeResult.play();
    });

    // Iniciar el retraso antes de cambiar la imagen
    pauseBeforeImageChange.play();
        }
    }
    
    private void mostrarResultado(String resultado,int numero) {
        // Mostrar el resultado obtenido después del retraso de 1 segundo
        if(p1.isCpu() || p2.isCpu()){
            System.out.println("muestra el resultado en cpu");
        }
        dado.setImage(new Image(resultado));
        if(lanzadas==2){
            p1.setDado(numero);
        }
        else{
            p2.setDado(numero);
        }
        lanzadas--;
    }
    private void cambiarMensaje(){
        String msj = mensaje.getText();
        char og = msj.charAt(9);
        char nuevo = '2';
        String newMsj = msj.substring(0,8)+nuevo+msj.substring(9);
        mensaje.setText(newMsj);
    }
    
    private void cambiarMensajeCPU(){
        String msj = mensaje.getText();
        char og = msj.charAt(9);
        String nuevo = "Computadora";
        String newMsj = nuevo+msj.substring(9);
        mensaje.setText(newMsj);
    }
    
    private void jugar(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/Tablero3EnRaya.fxml"));
        Parent tableroParent = loader.load();
        Scene tableroScene = new Scene(tableroParent,730,480);
        Tablero3EnRayaController tableroController = loader.getController();
        tableroController.inicializar(p1, p2,r);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableroScene);
        window.show();
    }
    
    private void jugar() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/Tablero3EnRaya.fxml"));
        Parent tableroParent = loader.load();
        Scene tableroScene = new Scene(tableroParent,730,480);
        Tablero3EnRayaController tableroController = loader.getController();
        System.out.println(p1.getTipoSimbolo());
        System.out.println(p2.getTipoSimbolo());
        tableroController.inicializar(p1, p2,r);
        Stage window = (Stage) mensaje.getScene().getWindow();
        window.setScene(tableroScene);
        window.show();
    }
}
