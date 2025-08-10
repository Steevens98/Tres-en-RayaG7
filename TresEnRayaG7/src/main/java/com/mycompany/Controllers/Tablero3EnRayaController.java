
package com.mycompany.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class Tablero3EnRayaController implements Initializable {
    private Jugador j1;
    private Jugador j2;
    private int puntajeOgJ;
    private Label mensaje;
    private Jugador primero;
    private Jugador segundo;
    private Jugador actual;
    private Simbolo fichaActual;
    @FXML
    private StackPane sp1;
    private int turno=0;
    private LinkedList<Jugada> jugadasHechas;
    private ImageView[][] imageViews = new ImageView[3][3];
    private Button[][] buttons = new Button[3][3];
    private Jugada[][] jugadas = new Jugada[3][3];
    private int currentRow, currentCol;
    private GamePhase currentPhase = STANDBY;
    private Simbolo ficha;
    private int anchoIm = 80;
    private int altoIm = 80;
    private Resultado r;
    private boolean victory;
    private boolean empate;
    private boolean victoryFinal = false;
    private boolean victoryJ;
    private boolean empJ;
    private boolean stopCPUVCPU;
    private int[] index;
    private LinkedList<int[]> coordenadas = new LinkedList<>();
    private Jugada aHacer;
    @FXML
    private GridPane gp;
    @FXML
    private VBox p1;
    @FXML
    private VBox p2;
    @FXML
    private Button bTerminarJ;
    @FXML
    private Button ayudaJ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializar(Jugador p1,Jugador p2,Resultado r){
        this.puntajeOgJ = p1.getPuntuacion();
        this.r=r;
        j1=p1;
        j2=p2;
        this.compararNum();
        turno++;
        visualizarTurno(turno);
        inicializarResultado(j1.getPuntuacion(),j2.getPuntuacion());
        inicializarTablero();
    }
    private void inicializarIA(){
        this.asignarJActual(turno);
        System.out.println("Hola");
        if(actual.isCpu()){
            System.out.println("Hola2");
            this.desOhabilitarBotones(buttons, true);
            try{
                this.JugarCPU();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    private void desOhabilitarBotones(Button[][] b,boolean condicion){
        for(int i=0; i<b.length;i++){
            for(int j=0;j<b.length;j++){
                Button but = b[i][j];
                but.setDisable(condicion);
            }
        }
        this.ayudaJ.setDisable(condicion);
    }
    private void compararNum(){
        if(j1.getDado()>=j2.getDado()){
            primero=j1;
            segundo=j2;
        }
        
        else if(j1.getDado()<j2.getDado()){
            primero=j2;
            segundo=j1;
        }
    }
    
    private boolean verificarJ1Primero(){
        //retorna true cuando el valor del dado de j1 es mayor a j2, verificando que j1 es primero
        //retorna false cuando el valor del dado de j1 es menor a j2, verificando que j2 es primero
        if(j1.getDado()>=j2.getDado()){
            return true;
        }
            return false;
    }
    
    private void visualizarTurno(int t){
        mensaje= new Label();
        sp1.getChildren().clear();
        if(t%2==1){
           if(primero.getNombre()!=null){
               mensaje.setText("Turno de jugador "+primero.getNombre()); 
           }
           else if(primero.isCpu() && segundo.isCpu()){
               mensaje.setText("Turno de CPU "+primero.getId()); 
           }
           else if(primero.isCpu() && !segundo.isCpu()){
               mensaje.setText("Turno de CPU");
           }
           else{
               mensaje.setText("Turno de jugador "+primero.getId()); 
           }
        }
        else{
            if(segundo.getNombre()!=null){
               mensaje.setText("Turno de jugador "+segundo.getNombre()); 
           }
           else if(segundo.isCpu()&&primero.isCpu()){
               mensaje.setText("Turno de CPU "+segundo.getId()); 
           }
           else if(segundo.isCpu()&&!primero.isCpu()){
                mensaje.setText("Turno de CPU"); 
           }
           else{
               mensaje.setText("Turno de jugador "+segundo.getId()); 
           }
        }
        
        mensaje.setFont(new javafx.scene.text.Font("Arial", 36));
        mensaje.setPrefWidth(340);
        mensaje.setPrefHeight(54);
        mensaje.setTextFill(Color.WHITE);
        sp1.getChildren().addAll(mensaje);
    }
    
    private void inicializarTablero(){
        gp.getChildren().clear();
        jugadasHechas = new LinkedList<>();
        for(int row=0; row<3;row++){
            for(int col=0;col<3;col++){
                Button b = new Button();
                b.setPrefWidth(100);
                b.setPrefHeight(86);
                ImageView iv = new ImageView();
                imageViews[row][col] = iv;
                jugadas[row][col]= new Jugada(row,col);
                iv.setUserData(new Simbolo(row,col));
                b.setGraphic(iv);
                b.setStyle("-fx-base: transparent;-fx-focus-color: transparent;-fx-padding: 0px;-fx-margin: 0px");
                if((row==0 && col==0)||(row==0 && col==1)||(row==1 && col==0)||(row==1 && col==1)){
                    b.setStyle("-fx-base: transparent;-fx-border-width: 0 3 3 0; -fx-border-color: transparent white white transparent;-fx-focus-color: transparent;-fx-padding: 0px;-fx-margin: 0px");
                }
                else if((row==0&&col==2)||(row==1 && col==2)){
                    b.setStyle("-fx-base: transparent;-fx-border-width: 0 3 0 0; -fx-border-color: transparent white transparent transparent;-fx-focus-color: transparent;-fx-padding: 0px;-fx-margin: 0px");
                }
                else if((row==2 && col==0)||(row==2 && col==1)){
                    b.setStyle("-fx-base: transparent;-fx-border-width: 0 0 3 0; -fx-border-color: transparent transparent white transparent;-fx-focus-color: transparent;-fx-padding: 0px;-fx-margin: 0px");
                }
                buttons[row][col]=b;
                b.setMinSize(Button.USE_COMPUTED_SIZE, Button.USE_COMPUTED_SIZE);
                b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                b.setOnMouseClicked(this::ponerSimbolo);
                gp.add(b, row, col);
            }
        }
        if(this.j1.isCpu() && this.j2.isCpu()){
            this.ayudaJ.setDisable(true);
            this.inicializarCoordenadas();
            this.CPUvsCPU();
        
        }
        else {
            this.tocaIA();
        }
    }
    
    private void tocaIA(){
        this.asignarJActual(turno);
        if(actual.isCpu() && !victoryFinal){
            this.inicializarIA();
        }
    }
    
    private void reiniciarTablero(int pJ1,int pJ2){
        inicializarResultado(pJ1,pJ2);
        inicializarTablero();
    }
    
    private void inicializarResultado(int puntJ1,int puntJ2){
        p1.getChildren().clear();
        p2.getChildren().clear();
        TipoResul resul = r.getTipoResul();
        if(resul.equals(TipoResul.PorVidas)){
            Label lb1 = new Label();
            lb1.setFont(new Font(24));
            lb1.setTextFill(Color.WHITE);
            if(j1.getNombre()!=null){
                lb1.setText(j1.getNombre());
            }
            else if(j1.isCpu()&&!j2.isCpu()){
                lb1.setText("CPU");
            }
            else if(j1.isCpu()&&j2.isCpu()){
                lb1.setText("CPU "+j1.getId());
            }
            else{
                lb1.setText("J1");
            }
            StackPane root = new StackPane();
            root.getChildren().add(lb1);
            StackPane.setAlignment(lb1, javafx.geometry.Pos.CENTER);
            Label lb2 = new Label();
            lb2.setFont(new Font(24));
            lb2.setTextFill(Color.WHITE);
            if(j2.getNombre()!=null){
                lb2.setText(j2.getNombre());
            }
            else if(j2.isCpu()&&!j1.isCpu()){
                lb2.setText("CPU");
            }
            else if(j2.isCpu()&&j2.isCpu()){
                lb2.setText("CPU "+j2.getId());
            }
            else{
                lb2.setText("J2");
            }
            StackPane root2 = new StackPane();
            root2.getChildren().add(lb2);
            StackPane.setAlignment(lb2, javafx.geometry.Pos.CENTER);
            p1.getChildren().add(root);
            p2.getChildren().add(root2);
            for(int i=0; i<puntJ1;i++){
                StackPane sp = new StackPane();
                Image im = new Image("com/mycompany/images/corazon.png");
                ImageView iv =new ImageView(im);
                iv.setFitHeight(63);
                iv.setFitWidth(54);
                sp.getChildren().add(iv);
                p1.getChildren().add(sp);
            }
            for(int i=0;i<puntJ2;i++){
                StackPane sp2=new StackPane();
                Image im = new Image("com/mycompany/images/corazon.png");
                ImageView iv2 = new ImageView(im);
                iv2.setFitHeight(63);
                iv2.setFitWidth(54);
                sp2.getChildren().add(iv2);
                p2.getChildren().add(sp2);
            }
        }
        else{
           Label lb1 = new Label();
           lb1.setPrefHeight(115);
           Label lb2 = new Label();
           lb2.setPrefHeight(115);
           p1.getChildren().add(lb1);
           p2.getChildren().add(lb2);
           StackPane root = new StackPane();
           StackPane root2 = new StackPane();
           Label lb3 = new Label();
           lb3.setFont(new Font(36));
           lb3.setTextFill(Color.WHITE);
           lb3.setText("J1");
           root.getChildren().add(lb3);
           StackPane.setAlignment(lb3, javafx.geometry.Pos.CENTER);
           Label lb4 = new Label();
           lb4.setFont(new Font(36));
           lb4.setTextFill(Color.WHITE);
           lb4.setText("J2");
           root2.getChildren().add(lb4);
           StackPane.setAlignment(lb4, javafx.geometry.Pos.CENTER);
           p1.getChildren().add(root);
           p2.getChildren().add(root2);
           Label puntuacion = new Label();
           puntuacion.setFont(new Font(36));
           puntuacion.setTextFill(Color.WHITE);
           puntuacion.setText(""+puntJ1);
           StackPane root3 = new StackPane();
           root3.getChildren().add(puntuacion);
           StackPane.setAlignment(puntuacion, Pos.CENTER);
           p1.getChildren().add(root3);
           Label puntuacion2 = new Label();
           puntuacion2.setFont(new Font(36));
           puntuacion2.setTextFill(Color.WHITE);
           puntuacion2.setText(""+puntJ2);
           StackPane root4 = new StackPane();
           root4.getChildren().add(puntuacion2);
           StackPane.setAlignment(puntuacion2, Pos.CENTER);
           p2.getChildren().add(root4);
        }
    }
    private void ponerSimbolo(MouseEvent event) {
        Button b = (Button) event.getSource();
        ImageView iv = (ImageView) b.getGraphic();
        switch(currentPhase){
            case STANDBY:
                asignarJActual(turno);
                fichaActual = asignarSimbolo(actual);
                currentPhase=GamePhase.PUT;
            case PUT:
                boolean estado = ponerFicha(iv);
                this.mostrarMatriz(jugadas);
                System.out.println(estado);
                if(estado){
                    currentPhase=GamePhase.END;
                }
                else{
                    break;
                }
                victory=tresEnRaya();
                empate=empate();
            case END:
                if(!victory&&!empate){
                    this.iniciarNuevoTurno();
                    visualizarTurno(turno);
                    this.inicializarIA();
                    currentPhase= GamePhase.STANDBY;
                }
                else if(victory){
                    this.alertaGanadoraJ("El resultado del set es: Victoria para " + actual.getId() + ", has ganado la partida.", "Ganador");
                }
                else if(empate){
                    this.alertaEmpateJ("El resultado del set es: Empate", "Empate");
                }
        }
        
    }
    public Comparator<Jugador> cmp = new Comparator<>() {
        @Override
        public int compare(Jugador o1, Jugador o2) {
            return o1.getId()-o2.getId();
        }
    };
    
    private void empatePunt(){
        if(r.getTipoResul().equals(TipoResul.PorPuntaje)){
            primero.sumarPuntuacion(1);
            segundo.sumarPuntuacion(1);
            if(verificarJ1Primero()){
                reiniciarTablero(primero.getPuntuacion(), segundo.getPuntuacion());
            }
            else{
                reiniciarTablero(segundo.getPuntuacion(), primero.getPuntuacion());
            }
        }
        else{
            if(verificarJ1Primero()){
                reiniciarTablero(primero.getPuntuacion(), segundo.getPuntuacion());
            }
            else{
                reiniciarTablero(segundo.getPuntuacion(), primero.getPuntuacion());
            }
        }
    }
    
    private boolean victoria(){
        if(r.getTipoResul().equals(TipoResul.PorVidas)){
            System.out.println("Prueba en victoria()");
            if(primero.getPuntuacion()==0 || segundo.getPuntuacion()==0){
                System.out.println("Prueba en el if");
//                Util.mostrarMensaje("Jugador "+actual.getId()+" has ganado el juego","Victoria","Victoria");
            victoryFinal = true;
            this.alertaFinPartida("Jugador "+actual.getId()+" has ganado el juego");
                return true;
            }
        }
        else{
            if(primero.getPuntuacion()==r.getCantidad()||segundo.getPuntuacion()==r.getCantidad()){
                Util.mostrarMensaje("Jugador "+actual.getId()+" has ganado el juego","Victoria","Victoria");
                String msj1="El jugador "+primero.getId()+" ha obtenido: "+primero.getPuntuacion();
                String msj2 ="El jugador "+segundo.getId()+" ha obtenido: "+segundo.getPuntuacion();
//                Util.mostrarMensaje(msj1+"\n"+msj2,"Puntuación","Puntuación");
                victoryFinal = true;
                this.alertaFinPartida(msj1+"\n"+msj2);
                return true;
            }
        }
        return false;
    }
    
    private boolean emp(){
        if(r.getTipoResul().equals(TipoResul.PorPuntaje)){
           if(primero.getPuntuacion()==r.getCantidad()&& segundo.getPuntuacion()==r.getCantidad()){
               String msj = "Habeis empatado";
               this.alertaFinPartida(msj);
               return true;
           }
        }
       return false;
    }
    
    private void modificarPuntuacion(Jugador j,Comparator cmp){
        if(cmp.compare(primero, j)==0 && primero.getId()==1){
            if(r.getTipoResul().equals(TipoResul.PorVidas)){
                segundo.restarPuntuacion(1);
                if(segundo.getPuntuacion()!=0){
                    reiniciarTablero(primero.getPuntuacion(), segundo.getPuntuacion());
                }
            }
            else if(r.getTipoResul().equals(TipoResul.PorPuntaje)){
                primero.sumarPuntuacion(1);
                if(primero.getPuntuacion()!=r.getCantidad()){
                    reiniciarTablero(primero.getPuntuacion(), segundo.getPuntuacion());
                }
            }
        }
        else if(cmp.compare(primero, j)==0 && primero.getId()==2){
            if(r.getTipoResul().equals(TipoResul.PorVidas)){
                segundo.restarPuntuacion(1);
                if(segundo.getPuntuacion()!=0){
                    reiniciarTablero(segundo.getPuntuacion(), primero.getPuntuacion());
                }
            }
            else if(r.getTipoResul().equals(TipoResul.PorPuntaje)){
                primero.sumarPuntuacion(1);
                if(primero.getPuntuacion()!=r.getCantidad()){
                    reiniciarTablero(segundo.getPuntuacion(), primero.getPuntuacion());
                }
            }
        }
        else if(cmp.compare(segundo, j)==0 && segundo.getId()==1 ){
            if(r.getTipoResul().equals(TipoResul.PorVidas)){
                primero.restarPuntuacion(1);
                if(primero.getPuntuacion()!=0){
                    reiniciarTablero(segundo.getPuntuacion(), primero.getPuntuacion());
                }
            }
            else if(r.getTipoResul().equals(TipoResul.PorPuntaje)){
                segundo.sumarPuntuacion(1);
                if(segundo.getPuntuacion()!=r.getCantidad()){
                    reiniciarTablero(segundo.getPuntuacion(), primero.getPuntuacion());
                }
            }
        }
        else if(cmp.compare(segundo, j)==0 && segundo.getId()==2){
            if(r.getTipoResul().equals(TipoResul.PorVidas)){
                primero.restarPuntuacion(1);
                if(primero.getPuntuacion()!=0){
                    reiniciarTablero(primero.getPuntuacion(), segundo.getPuntuacion());
                }
                
            }
            else if(r.getTipoResul().equals(TipoResul.PorPuntaje)){
                segundo.sumarPuntuacion(1);
                if(segundo.getPuntuacion()!=r.getCantidad()){
                    
                }
            }
        }
    }
    
    private void asignarJActual(int t){
        if(t%2==1){
            this.actual=primero;
        }
        else{
            this.actual=segundo;
        }
    }
    
    private Simbolo asignarSimbolo(Jugador actual) {
        return new Simbolo("com/mycompany/images/"+actual.getTipoSimbolo()+".png",actual);
    }
    
    private boolean ponerFicha(ImageView iv){
        //devuelve false cuando la casilla esta ocupada
        boolean estado= false;
        Simbolo s = (Simbolo) iv.getUserData();
        if(s.getImagen()!=null && s.getJ()!=null){
            Util.mostrarMensaje("Esta casilla ya está ocupada. Por favor, elige otra.", "Casilla Ocupada");
            estado=false;
        }
        else{
            s.setImagen(fichaActual.getImagen());
            s.setJ(fichaActual.getJ());
            int row= s.getFila();
            int col= s.getColumna();
            String sb = actual.getTipoSimbolo();
            int id=0;
            Jugada jg = jugadas[row][col];
            jg.setRow(row);
            jg.setCol(col);
            if(sb.equals("X")){
                id=1;
            }
            else{
                id=2;
            }
            jg.setId(id);
            jg.setSimbolo(sb);
            jugadasHechas.add(jg);
            this.mostrarMatriz(this.jugadas);
            iv.setImage(new Image(fichaActual.getImagen()));
            iv.setFitWidth(anchoIm);
            iv.setFitHeight(altoIm);
            iv.setUserData(s);
            estado=true;
        }
        return estado;
        }
    
    private void iniciarNuevoTurno(){
        turno++;
    }
    
    private boolean tresEnRaya(){
        //verifica si ha ocurrido un ganador
        return verificarFila() || verificarColumna() || verificarDiagonalPrincipal() || verificarDiagonalSecundaria();
    }
    
    private boolean verificarSimbolosIguales(Simbolo sb1, Simbolo sb2,Simbolo sb3){
        if(sb1 != null && sb2 != null && sb3 != null){
            if(sb1.getImagen() != null && sb2.getImagen() != null && sb3.getImagen() != null)
            return (sb1.getImagen().compareTo(sb2.getImagen()) == 0) && (sb1.getImagen().compareTo(sb3.getImagen()) == 0);
        }
        return false;
    }
    
    
    private boolean verificarColumna(){
        //verifica si las tres fichas son iguales en la fila
        for(int i = 0; i<imageViews.length;i++){
            ImageView iv1 = imageViews[i][0];
            ImageView iv2 = imageViews[i][1];
            ImageView iv3 = imageViews[i][2];

            Simbolo s1 = (Simbolo) iv1.getUserData();
            Simbolo s2 = (Simbolo) iv2.getUserData();
            Simbolo s3 = (Simbolo) iv3.getUserData();

            if (verificarSimbolosIguales(s1, s2, s3)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean verificarDiagonalPrincipal(){
        //verifica si las tres fichas son iguales en la diagonal
        ImageView iv1 = imageViews[0][0];
        ImageView iv2 = imageViews[1][1];
        ImageView iv3 = imageViews[2][2];

        Simbolo s1 = (Simbolo) iv1.getUserData();
        Simbolo s2 = (Simbolo) iv2.getUserData();
        Simbolo s3 = (Simbolo) iv3.getUserData();

        if (verificarSimbolosIguales(s1, s2, s3)) {
            return true;
        }
        return false;
    }
    
    private boolean verificarDiagonalSecundaria(){
        //verifica si las tres fichas son iguales en la diagonal
        ImageView iv1 = imageViews[0][2];
        ImageView iv2 = imageViews[1][1];
        ImageView iv3 = imageViews[2][0];

        Simbolo s1 = (Simbolo) iv1.getUserData();
        Simbolo s2 = (Simbolo) iv2.getUserData();
        Simbolo s3 = (Simbolo) iv3.getUserData();

        if (verificarSimbolosIguales(s1, s2, s3)) {
            return true;
        }
        return false;
    }
    
    private boolean verificarFila(){
        //verifica si las tres fichas son iguales en la columna
        for(int i = 0; i<imageViews.length;i++){
            ImageView iv1 = imageViews[0][i];
            ImageView iv2 = imageViews[1][i];
            ImageView iv3 = imageViews[2][i];

            Simbolo s1 = (Simbolo) iv1.getUserData();
            Simbolo s2 = (Simbolo) iv2.getUserData();
            Simbolo s3 = (Simbolo) iv3.getUserData();

            if (verificarSimbolosIguales(s1, s2, s3)) {
                return true;
            }
        }
        return false;
    }
    
    public void inicio() {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/MenuPrincipal.fxml"));
        Parent menuDeInicioParent = loader.load();
        Scene menuDeInicioScene = new Scene(menuDeInicioParent, 730, 480);
        // Obtiene el Stage desde cualquier nodo de la escena actual
        Stage currentStage = (Stage) sp1.getScene().getWindow();
        // Cerrar la ventana actual
        currentStage.close();
        Stage window = new Stage();
        window.setTitle("Ventana de Inicio");
        window.setScene(menuDeInicioScene);
        window.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones en caso de error al cargar el FXML
        }
    }
    
    private boolean empate(){
        //verifica si el tablero se ha llenado y ninguno de los dos jugadores ha hecho tres en raya, declarando un empate
        for (int i = 0; i < imageViews.length; i++) {
            for (int j = 0; j < imageViews.length; j++) {
                ImageView iv = imageViews[i][j];
                Simbolo sb = (Simbolo) iv.getUserData();

                if (sb == null || sb.getImagen() == null) {
                    return false; // Si alguna celda no está ocupada, no hay empate
                }
            }
    }
    return true;
    }
    
    public void IA(Jugador j, Jugada[][] tableroActual) throws Exception{
        String s = j.getTipoSimbolo();
        Jugada[][] copia = new Jugada[3][3];
        for(int i=0;i<tableroActual.length;i++){
            for(int k=0; k<tableroActual.length;k++){
                copia[i][k]= new Jugada(tableroActual[i][k].getRow(),tableroActual[i][k].getCol(),tableroActual[i][k].getSimbolo(),tableroActual[i][k].getId());
            }
        }  
        System.out.println("Original");
        this.mostrarMatriz(jugadas);
        System.out.println("Copia");
        this.mostrarMatriz(copia);
        Tree<Jugada[][]> juego= new Tree(copia);
        ArrayList<Integer> rowCPU= new ArrayList<>();
        ArrayList<Integer> colCPU= new ArrayList<>();
        this.llenarTree_Nivel_1(actual,juego, copia, s,rowCPU,colCPU);
        this.llenarTree_Nivel_2(juego, s,rowCPU,colCPU); // por cada arbol de matriz se va llenando el nivel 2
        if(rowCPU.size()==1&&colCPU.size()==1){
            int id=0;
            if(s.equals("X")){
                id=2;
            }
            else{
                id=1;
            }
            int row = rowCPU.get(0);
            System.out.println("Fila "+row);
            int col = colCPU.get(0);
            System.out.println("Columna "+col);
            Jugada jd = jugadas[row][col];
            System.out.println("Fila2 "+jd.getRow());
            System.out.println("Columna2 "+jd.getCol());
            jd.setRowCPU(row);
            jd.setColCPU(col);
            jd.setSimbolo(s);
            jd.setId(id);
            this.aHacer= jd;
        }
        else{
            this.llenarMinimax(juego);
        }
    }
    
    public void llenarTree_Nivel_1(Jugador j,Tree<Jugada[][]> juego, Jugada[][] copia, String s,ArrayList<Integer> fCPU,ArrayList<Integer> cCPU){
        Jugada[][] og = new Jugada[3][3];
        for(int i=0;i<copia.length;i++){
            for(int d=0;d<copia.length;d++){
                og[i][d]= new Jugada(copia[i][d].getRow(),copia[i][d].getCol(),copia[i][d].getSimbolo(),copia[i][d].getId());
            }
        }
        System.out.println("Copia de la copia");
        int id=0;
        if(s.equals("X")){
            id=1;
        }
        else{
            id=2;
        }
        int xAnterior = Integer.MIN_VALUE;
        int yAnterior = Integer.MIN_VALUE;
        ArrayList<Jugada> jAnterior = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                if(og[i][k].getId()==0){
                  Jugada p = og[i][k];
                  Jugada[][] cop = new Jugada[3][3];
                  for (int row = 0; row < 3; row++) {
                      for (int col = 0; col < 3; col++) {
                         cop[row][col] = new Jugada(og[row][col].getRow(),og[row][col].getCol(),og[row][col].getSimbolo(),og[row][col].getId());
                     }
                  }
                  System.out.println("Nivel 1");
                  p.setSimbolo(s);
                  p.setId(id);
                  fCPU.add(p.getRow());
                  cCPU.add(p.getCol());
                  cop[i][k]=p;
                  if(xAnterior!=Integer.MIN_VALUE &&yAnterior!=Integer.MIN_VALUE){
                      cop[xAnterior][yAnterior]= new Jugada(xAnterior,yAnterior);
                      jAnterior.add(cop[xAnterior][yAnterior]);
                      
                  }
                  for(int ñ=0; ñ<jAnterior.size();ñ++){
                    Jugada jd= jAnterior.get(ñ);
                    int fila = jd.getRow();
                    int col = jd.getCol();
                    int id2 = jd.getId();
                    Jugada jgd = cop[fila][col];
                    jgd.setRow(fila);
                    jgd.setCol(col);
                    jgd.setId(0);
                    jgd.setUtilidad(Integer.MIN_VALUE);
                    jgd.setSimbolo(null);
                    cop[fila][col]=jgd;
                }
                  xAnterior=i;
                  yAnterior=k;
                  this.mostrarMatriz(cop);
                  Tree<Jugada[][]> t = new Tree(cop);
                  juego.addChildren(t);
                }
            }   
        }
    }
    
    public void llenarTree_Nivel_2(Tree<Jugada[][]> t, String s,ArrayList<Integer> fCPU,ArrayList<Integer>cCPU){
        // analiza jug 2
        int id=0;
        ArrayList<Integer> utilidades = new ArrayList<>();
        String s2=null;
        if(s.equals("X")){
            id=2;
            s2="O";
        }
        else if(s.equals("O")){
            id=1;
            s2="X";
        }
        int cont=-1;
        for(Tree<Jugada[][]> nodo: t.getRootNode().getChildren()){
            int xAnterior=Integer.MIN_VALUE;
            int yAnterior= Integer.MIN_VALUE;
            cont++;
            ArrayList<Jugada> jAnterior = new ArrayList<>();
            Jugada[][] jg = nodo.getRootNode().getContent();
            int rowCPU = fCPU.get(cont);
            System.out.println("Fila CPU "+rowCPU);
            int colCPU = cCPU.get(cont);
            System.out.println("Columna CPU "+colCPU);
            System.out.println("Nivel2");
            for(int i=0;i<jg.length;i++){
                System.out.println("Arbol");
                for(int j=0;j<jg.length;j++){
                    if(jg[i][j].getId()==0){
                        Jugada p = jg[i][j];
                        Jugada[][] cop = new Jugada[3][3];
                        for (int row = 0; row < 3; row++) {
                            for (int col = 0; col < 3; col++) {
                               cop[row][col] = new Jugada(jg[row][col].getRow(),jg[row][col].getCol(),jg[row][col].getSimbolo(),jg[row][col].getId(),jg[row][col].getRowCPU(),jg[row][col].getColCPU());
                            }
                        }
                        if(xAnterior!=Integer.MIN_VALUE &&yAnterior!=Integer.MIN_VALUE){
                           cop[xAnterior][yAnterior]= new Jugada(xAnterior,yAnterior,0);
                           jAnterior.add(cop[xAnterior][yAnterior]);
                       } 
                        for(int k=0; k<jAnterior.size();k++){
                            Jugada jd= jAnterior.get(k);
                            int fila = jd.getRow();
                            int col = jd.getCol();
                            int id2 = jd.getId();
                            Jugada jgd = cop[fila][col];
                            jgd.setRow(fila);
                            jgd.setCol(col);
                            jgd.setId(0);
                            jgd.setUtilidad(Integer.MIN_VALUE);
                            jgd.setSimbolo(null);
                            cop[fila][col]=jgd;
                        }
                        xAnterior=i;
                        yAnterior=j;
                        p.setSimbolo(s2);
                        p.setId(id);
                        cop[i][j]=p;
                        int utilidadEnemy = this.getUtility(cop, s2);
                        int utilidadCPU = this.getUtility(cop, s);
                        p.setUtilidad(utilidadCPU-utilidadEnemy);
                        p.setRowCPU(rowCPU);
                        p.setColCPU(colCPU);
                        cop[i][j]=p;
                        this.mostrarMatriz(cop);
                        Tree<Jugada[][]> arb = new Tree(cop);
                        arb.getRootNode().setUtilidad(utilidadCPU-utilidadEnemy);
                        utilidades.add(arb.getRootNode().getUtilidad());
                        nodo.addChildren(arb);
                    }
                }
            }
            
        }
    } 
    
    private void mostrarMatriz(Jugada[][] jg){
        for (int i = 0; i < jg.length; i++) {
            for (int j = 0; j < jg[i].length; j++) {
                System.out.print(jg[i][j].getId() + " ");
            }
            System.out.println();
        }
    }
    
    private void mostrarIV(ImageView[][]iv){
        for (int i = 0; i < iv.length; i++) {
            for (int j = 0; j < iv[i].length; j++) {
                System.out.print(iv[i][j].getImage().getUrl() + " ");
            }
            System.out.println();
        }
    }

    public int getUtility(Jugada[][] m, String s){
        return this.calcColsU(s, m)+this.calcRowsU(s, m)+this.calcDiagsU(s, m);
    }

    public int calcRowsU(String s, Jugada[][] m){
        int ret=0;
        for (int i = 0; i < 3; i++) {
            int iter=0;
            for (int j = 0; j < 3; j++) {
                Jugada jd = m[i][j];
                if(jd.getSimbolo()!=null){
                    if(jd.getSimbolo().equals(s)){
                        iter++;
                    }
                }
                else if(jd.getSimbolo() == null ){
                    if(jd.getId()==0){
                        iter++;
                    }
                }
            }
            
            if (iter==3)
                ret++;
            
        }
        return ret;
    }
    
    public int calcColsU(String s, Jugada[][] m){
        int ret=0;
        for (int i = 0; i < 3; i++) {
            int iter=0;
            for (int j = 0; j < 3; j++) {
                Jugada jd = m[i][j];
                if(jd.getSimbolo()!=null){
                    if(jd.getSimbolo().equals(s)){
                        iter++;
                    }
                }
                else if(jd.getSimbolo() == null ){
                    if(jd.getId()==0){
                        iter++;
                    }
                }
            }
            if (iter==3)
                ret++;
            
        }
        return ret;
    }
    
    public int calcDiagsU(String s, Jugada[][] m){
        int ret=0;
        int iter=0;
        for (int i = 0; i < 3; i++) {
            Jugada jd = m[i][i];
            if(jd.getSimbolo()!=null){
                if(jd.getSimbolo().equals(s)){
                    iter++;
                }
            }
            else if(jd.getSimbolo() == null ){
                if(jd.getId()==0){
                    iter++;
                }
            }
        }
        if (iter==3)
                ret++;
        
        int j=2;
        iter=0;
        for (int i = 0; i < 3; i++) {
            Jugada jd = m[i][j];
            if(jd.getSimbolo()!=null){
                if(jd.getSimbolo().equals(s)){
                    iter++;
                    j--;
                }
            }
            else if(jd.getSimbolo() == null ){
                if(jd.getId()==0){
                    iter++;
                    j--;
                }
            }
        }
        if (iter==3)
                ret++;
        return ret;
    }
    
    private Comparator<Jugada> minimo = (Jugada o1, Jugada o2) -> o1.getUtilidad()-o2.getUtilidad();
    
    private Comparator<Jugada> maximo = (Jugada o1, Jugada o2) -> o2.getUtilidad()-o1.getUtilidad();

    private Jugada encontrarJugada(Jugada[][] juego,int utilidad){
        Jugada jg = null;
        String sb=null;
        for(int i=0;i<juego.length;i++){
            for(int j=0; j<juego.length;j++){
                Jugada jd = juego[i][j];
                int util = jd.getUtilidad();
                if(util==utilidad){
                    jg=jd;
                }
            }
        }
        return jg;
    }
    
    public void llenarMinimax(Tree<Jugada[][]> juego) throws Exception{
        if(juego==null || juego.isEmpty()){
            throw new Exception();
        }
        PriorityQueue<Jugada> utilidades1= new PriorityQueue<>(maximo);
        for(Tree<Jugada[][]> nivel1 : juego.getRootNode().getChildren()){// iterando nivel 1
            PriorityQueue<Jugada> utilidades2= new PriorityQueue<>(minimo);
            System.out.println("Nivel2");
            for(Tree<Jugada[][]> nivel2 : nivel1.getRootNode().getChildren()){ //iterando nivel 2
                 TreeNode<Jugada[][]> nodo = nivel2.getRootNode();
                 int u = nodo.getUtilidad();
                 Jugada[][] jd = nodo.getContent();
                 Jugada jg =(encontrarJugada(jd,u));
                 this.mostrarMatriz(jd);
                 System.out.println("Utilidad "+u);
                 utilidades2.offer(jg);
            }
            Jugada jg2 = utilidades2.poll();
            utilidades1.offer(jg2); 
        }
        this.aHacer= utilidades1.poll();
    }
    
    private void JugarCPU() throws Exception{
        
        System.out.println("Hola3");
        this.IA(actual, jugadas);
        System.out.println("Matriz de jugada");
        if(aHacer!=null){
            int row= aHacer.getRowCPU();
            System.out.println("CPUF: "+row);
            int col = aHacer.getColCPU();
            System.out.println("CPUC: "+col);
            ImageView iv = imageViews[row][col];
            Simbolo sb = (Simbolo) iv.getUserData();
            Simbolo act = this.asignarSimbolo(actual);
            Jugada j = jugadas[row][col];
            j.setRow(row);
            j.setCol(col);
            int id=0;
            if(sb.equals("X")){
                id=1;
            }
            else{
                id=2;
            }
            j.setId(id);
            j.setSimbolo(actual.getTipoSimbolo());
            jugadasHechas.add(j);
            this.mostrarMatriz(jugadas);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(event -> {
                    sb.setImagen(act.getImagen());
                    sb.setJ(act.getJ());
                    iv.setUserData(sb);
                    iv.setFitWidth(anchoIm);
                    iv.setFitHeight(altoIm);
                    iv.setImage(new Image(sb.getImagen()));
                });
           pause.play(); 
        }
        PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
        pause2.setOnFinished(event->{
            victory = this.tresEnRaya();
            System.out.println("Victoria "+victory);
            empate = this.empate();
            System.out.println("Empate " + empate);
            if (!victory && !empate) {
                iniciarNuevoTurno();
                visualizarTurno(turno);
                this.desOhabilitarBotones(buttons, false);
                this.tocaIA();
            }
            else if (victory) {
                this.alertaGanadoraCPU("El resultado del set es: Victoria para " + actual.getId() + ", has ganado la partida.", "Ganador");                
            }
            else if (empate) {
                this.alertaEmpateCPU("El resultado del set es: Empate", "Empate");
//                this.victoria();
//                this.emp();
//                empatePunt();
            }
        });
        pause2.play();
        this.mostrarMatriz(jugadas);
        System.out.println("Terminado");
    }
    
    private void ponerFicha(int row,int col){
        ImageView iv = imageViews[row][col];
        Simbolo sb = (Simbolo) iv.getUserData();
        Simbolo act = this.asignarSimbolo(actual);
        Jugada j = jugadas[row][col];
        j.setRow(row);
        j.setCol(col);
        int id=0;
        if(sb.equals("X")){
            id=1;
        }
        else{
            id=2;
        }
        j.setId(id);
        j.setSimbolo(actual.getTipoSimbolo());
        jugadasHechas.add(j);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(event -> {
                sb.setImagen(act.getImagen());
                sb.setJ(act.getJ());
                iv.setUserData(sb);
                iv.setFitWidth(anchoIm);
                iv.setFitHeight(altoIm);
                iv.setImage(new Image(sb.getImagen()));
                this.desOhabilitarBotones(buttons, true);
            });
         pause.play(); 
        PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
        pause2.setOnFinished(event->{
            victory = this.tresEnRaya();
            empate = this.empate();
            if (!victory && !empate) {
                this.desOhabilitarBotones(buttons, false);
                iniciarNuevoTurno();
                visualizarTurno(turno);
                if(primero.isCpu()&&segundo.isCpu()&&!stopCPUVCPU){
                    System.out.println("Ejecutandose el if externo cpu");
                    this.CPUvsCPU();
                }
                else{
                    this.tocaIA();
                }
            }
            else if (victory) {
                
                this.alertaGanadoraCPU("El resultado del set es: Victoria para " + actual.getId() + ", has ganado la partida.", "Ganador");
                
            }
            else if (empate) {
                this.alertaEmpateCPU("El resultado del set es: Empate", "Empate");              
//                this.victoria();
//                this.emp();
//                empatePunt();
            }
            
        });
        pause2.play();
    }
    
    private void alertaFinPartida(String mensaje) {
       DialogPane dialogPane = new DialogPane();
       dialogPane.setHeaderText("Victoria");
        dialogPane.setContentText(mensaje+"\n"+"¿Desea reintentar la partidad desde el inicio o salir al menu?");

        // Agregar botones personalizados al diálogo
        ButtonType buttonTypeReintentar = new ButtonType("Reintentar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeSalir = new ButtonType("Salir", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogPane.getButtonTypes().addAll(buttonTypeReintentar, buttonTypeSalir);

        // Crear el diálogo con el contenido personalizado
        Dialog<Void> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        // Manejar la acción del botón "OK"
        Button reButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeReintentar);
        reButton.addEventFilter(ActionEvent.ACTION, event->{
            this.reintentarDesdeCero();
        });

        // Manejar la acción del botón "Cancel"
        Button salirButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeSalir);
        salirButton.addEventFilter(ActionEvent.ACTION, event -> {
            System.out.println("Se hizo clic en Cancel");
           try {
               salirAlMenu(event);
           } catch (IOException ex) {
               ex.printStackTrace();
           }
            
        });

        // Mostrar el diálogo sin bloquear la aplicación
        dialog.show();
    
    }
    
    public void reintentarDesdeCero() {
        victory = false;
        empate = false;
        currentPhase = STANDBY;
        turno=0;
        this.jugadas=new Jugada[3][3];
        this.imageViews=new ImageView[3][3];
        this.buttons=new Button[3][3];
        this.victoryFinal=false;
        j1.setPuntuacion(puntajeOgJ);
        j2.setPuntuacion(puntajeOgJ);
        primero.setPuntuacion(puntajeOgJ);
        segundo.setPuntuacion(puntajeOgJ);
        this.inicializar(j1, j2, r);
        
    }
    
    public void salirAlMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/MenuPrincipal.fxml"));
        Parent Menu_PrincipalParent = loader.load();
        Scene Menu_PrincipalScene = new Scene(Menu_PrincipalParent,730,480);
        Stage window = (Stage) sp1.getScene().getWindow();
        window.setScene(Menu_PrincipalScene);
        window.show(); 
    }
    
    @FXML
    public void terminarJuego(MouseEvent event) throws IOException {
        Util.mostrarMensaje("Ha terminado el juego", "Juego terminado");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/ModosDeJuegos.fxml"));
        Parent Modos_de_juegoParent = loader.load();
        Scene Modos_de_juegoScene = new Scene(Modos_de_juegoParent,730,480);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Modos_de_juegoScene);
        window.show(); 
    }

    @FXML
    private void obtenerAyuda(MouseEvent event) throws Exception {
        this.IA(actual, jugadas);

        System.out.println("Jugadas");
        this.mostrarMatriz(jugadas);
        if(aHacer!=null){ 
            int rowRecomendada = aHacer.getRowCPU();
            System.out.println(rowRecomendada);
            rowRecomendada++;
            int colRecomendada = aHacer.getColCPU();
            System.out.println(colRecomendada);
            colRecomendada++;
            String filaRecomendada="";
            String columnaRecomendada="";
            switch (rowRecomendada) {
                case 1:
                    filaRecomendada="Primera";
                    break;
                case 2:
                    filaRecomendada="Segunda";
                    break;
                case 3:
                    filaRecomendada="Tercera";
                    break;
                default:
                    break;
            }
            switch (colRecomendada) {
                case 1:
                    columnaRecomendada="Primera";
                    break;
                case 2:
                    columnaRecomendada="Segunda";
                    break;
                case 3:
                    columnaRecomendada="Tercera";
                    break;
                default:
                    break;
            }
            
            this.mostrarAyuda(colRecomendada, rowRecomendada, filaRecomendada, columnaRecomendada);
        }
    }
    

    private void alertaGanadoraJ(String mensaje, String tittle){
       DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(mensaje);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButton);

        // Configurar el botón OK
        Button ok = (Button) dialog.getDialogPane().lookupButton(okButton);
        if(primero.isCpu()&&!segundo.isCpu()|| segundo.isCpu()&& !primero.isCpu()|| !primero.isCpu()&&!segundo.isCpu()){
            ButtonType analizarSet = new ButtonType("Analizar set",ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(analizarSet);
            Button analizarSetB = (Button) dialog.getDialogPane().lookupButton(analizarSet);
            analizarSetB.addEventFilter(ActionEvent.ACTION, event ->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/AnalisisDeSet.fxml"));
                Parent Analisis_de_setParent=null;
                try {
                    Analisis_de_setParent = loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                AnalisisDeSetController controller = loader.getController();
                controller.inicializar(jugadasHechas, j1, j2, r);
                Scene Analisis_de_setScene = new Scene(Analisis_de_setParent,730,480);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(Analisis_de_setScene);
                window.show(); 
            });
        }
        ok.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               // Código a ejecutar después de cerrar la alerta
               System.out.println("Evento OK");
               modificarPuntuacion(actual,cmp);
               if(victoria()){
                   System.out.println("Victoria de la alerta");
                   return;
               }
               currentPhase= GamePhase.STANDBY;
           }
       });

        dialog.show();
    }
    
    private void alertaEmpateJ(String mensaje, String tittle){
       DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(mensaje);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButton);
        if(primero.isCpu()&&!segundo.isCpu()|| segundo.isCpu()&& !primero.isCpu()|| !primero.isCpu()&&!segundo.isCpu()){
            ButtonType analizarSet = new ButtonType("Analizar set",ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(analizarSet);
            Button analizarSetB = (Button) dialog.getDialogPane().lookupButton(analizarSet);
            analizarSetB.addEventFilter(ActionEvent.ACTION, event ->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/AnalisisDeSet.fxml"));
                Parent Analisis_de_setParent=null;
                try {
                    Analisis_de_setParent = loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                AnalisisDeSetController controller = loader.getController();
                controller.inicializar(jugadasHechas, j1, j2, r);
                Scene Analisis_de_setScene = new Scene(Analisis_de_setParent,730,480);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(Analisis_de_setScene);
                window.show(); 
            });
        }
        // Configurar el botón OK
        Button ok = (Button) dialog.getDialogPane().lookupButton(okButton);
        ok.addEventFilter(ActionEvent.ACTION, event -> {
            // Código a ejecutar después de cerrar la alerta
            empatePunt();
                    if(emp()){
                        return;
                    }
                    else if(victoria()){
                        return;
                    }
                    currentPhase= GamePhase.STANDBY;           
        });

        dialog.show();
    }
    
    private void alertaEmpateCPU(String mensaje, String tittle){
       DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(mensaje);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButton);
        if(primero.isCpu()&&!segundo.isCpu()|| segundo.isCpu()&& !primero.isCpu()|| !primero.isCpu()&&!segundo.isCpu()){
            ButtonType analizarSet = new ButtonType("Analizar set",ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(analizarSet);
            Button analizarSetB = (Button) dialog.getDialogPane().lookupButton(analizarSet);
            analizarSetB.addEventFilter(ActionEvent.ACTION, event ->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/AnalisisDeSet.fxml"));
                Parent Analisis_de_setParent=null;
                try {
                    Analisis_de_setParent = loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                AnalisisDeSetController controller = loader.getController();
                controller.inicializar(jugadasHechas, j1, j2, r);
                Scene Analisis_de_setScene = new Scene(Analisis_de_setParent,680,480);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(Analisis_de_setScene);
                window.show(); 
            });
        }

        // Configurar el botón OK
        Button ok = (Button) dialog.getDialogPane().lookupButton(okButton);
        ok.addEventFilter(ActionEvent.ACTION, event -> {
            // Código a ejecutar después de cerrar la alerta
            if(primero.isCpu()&&segundo.isCpu()){
                stopCPUVCPU = true;
            }
            this.emp();
            empatePunt();
            this.victoria();            
        });

        dialog.show();
    }
    
    private void alertaGanadoraCPU(String mensaje, String tittle){
       DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(mensaje);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButton);
        if(primero.isCpu()&&!segundo.isCpu()|| segundo.isCpu()&& !primero.isCpu()|| !primero.isCpu()&&!segundo.isCpu()){
            ButtonType analizarSet = new ButtonType("Analizar set",ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(analizarSet);
            Button analizarSetB = (Button) dialog.getDialogPane().lookupButton(analizarSet);
            analizarSetB.addEventFilter(ActionEvent.ACTION, event ->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/AnalisisDeSet.fxml"));
                Parent Analisis_de_setParent=null;
                try {
                    Analisis_de_setParent = loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                AnalisisDeSetController controller = loader.getController();
                controller.inicializar(jugadasHechas, j1, j2, r);
                Scene Analisis_de_setScene = new Scene(Analisis_de_setParent,680,480);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(Analisis_de_setScene);
                window.show(); 
            });
        }

        // Configurar el botón OK
        Button ok = (Button) dialog.getDialogPane().lookupButton(okButton);
        ok.addEventFilter(ActionEvent.ACTION, event -> {
            // Código a ejecutar después de cerrar la alerta
            if(primero.isCpu()&&segundo.isCpu()){
                stopCPUVCPU = true;
            }
            modificarPuntuacion(actual, cmp);
            this.victoria();
            
        });

        dialog.show();
    }
    
    private void mostrarAyuda(int col,int row, String rowRecom,String colRecom){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        int fila = row-1;
        System.out.println(fila);
        int columna= col-1;
        System.out.println(columna);
        a.setTitle("Ayuda");
        a.setContentText("Fila recomendada: "+colRecom+"\n"+"Columna recomendada: "+rowRecom);
        ButtonType ok = new ButtonType("OK");
        ButtonType poner_ficha = new ButtonType("Poner ficha en la pos recomendada");
        a.getButtonTypes().setAll(ok,poner_ficha);
        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        a.show();
        a.setOnCloseRequest(response ->{
            if(a.getResult()==ok){
                Stage currentStage = (Stage) a.getDialogPane().getScene().getWindow();
                currentStage.close();
            }
            else if(a.getResult()==poner_ficha){
                this.ponerFicha(fila, columna);
            }
        });
    }
    private void inicializarCoordenadas(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int [] jug= {i,j};
                coordenadas.add(jug);
            }
        }
    }
    private void CPUvsCPU() {
        this.asignarJActual(turno);
        int[] rd; 
        if(!coordenadas.isEmpty()){
            System.out.println("Ejecutandose el cpu");
            int pos=getRandomCoordenadas(coordenadas);
            rd=coordenadas.get(pos);
            this.ponerFicha(rd[0], rd[1]);
            coordenadas.remove(pos);
        } 
    }
      
    private int getRandomCoordenadas(LinkedList<int[]> cor){
        
        if(!cor.isEmpty()){
        int siz=cor.size();
        
        Random random = new Random();

        // Para un número entero aleatorio
        int randomInt = random.nextInt(siz);
        
        return randomInt;}
        
        return -1;
    }
    
}
