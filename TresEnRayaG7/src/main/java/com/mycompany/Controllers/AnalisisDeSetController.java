package com.mycompany.Controllers;

import com.mycompany.Modelos.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class AnalisisDeSetController implements Initializable {
    @FXML
    private StackPane sp1;
    @FXML
    private Label filaRecom;
    @FXML
    private Label colRecom;
    @FXML
    private GridPane gp;
    private LinkedList<Jugada> jugadasHechas;
    @FXML
    private Button terminarJ;
    @FXML
    private StackPane sp2;
    private Label mensaje;
    private Jugador primero;
    private Jugador segundo;
    private Jugador actual;
    private Simbolo fichaActual;
    private ImageView[][] imageViews = new ImageView[3][3];
    private Button[][] buttons = new Button[3][3];
    private Jugada[][] jugadas = new Jugada[3][3];
    @FXML
    private Button siguienteJ;
    @FXML
    private Label recom;
    private int anchoIm = 80;
    private int altoIm = 80;
    private Resultado r;
    private Jugador j1;
    private Jugador j2;
    private int turno=0;
    private boolean terminar;
    private int cont=0;
    private Jugada recomendada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inicializar(LinkedList<Jugada> jugadas, Jugador j1, Jugador j2,Resultado r){
        this.jugadasHechas=jugadas;
        this.j1=j1;
        this.j2=j2;
        this.r=r;
        this.compararNum();
        turno++;
        this.asignarJActual(turno);
        this.terminar= this.verificarPunt();
        if(terminar){
            Button b = new Button("Reintentar");
            sp2.getChildren().add(b);
            b.setOnMouseClicked(this::reintentarJuego);
        }
        else{
            Button b = new Button("Al siguiente set");
            sp2.getChildren().add(b);
            b.setOnMouseClicked(this::continuar);
        }
    }
    
    private void reintentarJuego(MouseEvent event){
        if(r.getTipoResul().equals(TipoResul.PorVidas)){
            j1.setPuntuacion(r.getCantidad());
            j2.setPuntuacion(r.getCantidad());
        }
        else{
            j1.setPuntuacion(0);
            j2.setPuntuacion(0);
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/Tablero3EnRaya.fxml"));
            Parent tableroParent = loader.load();
            Scene tableroScene = new Scene(tableroParent,680,480);
            Tablero3EnRayaController tableroController = loader.getController();
            tableroController.inicializar(j1, j2,r);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableroScene);
            window.show();   
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void continuar(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/Tablero3EnRaya.fxml"));
            Parent tableroParent = loader.load();
            Scene tableroScene = new Scene(tableroParent,680,480);
            Tablero3EnRayaController tableroController = loader.getController();
            tableroController.inicializar(j1, j2,r);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableroScene);
            window.show();   
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private boolean verificarPunt(){
        if(r.getTipoResul().equals(TipoResul.PorVidas)){
            if(primero.getPuntuacion()==0 || segundo.getPuntuacion()==0){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(primero.getPuntuacion()==r.getCantidad()||segundo.getPuntuacion()==r.getCantidad()){
                return true;
            }
            else if(primero.getPuntuacion()==r.getCantidad()&&segundo.getPuntuacion()==r.getCantidad()){
                return true;
            }
            else{
                return false;
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
           if(primero.isCpu() && !segundo.isCpu()){
               mensaje.setText("Turno de CPU");
           }
           else{
               mensaje.setText("Turno de jugador "+primero.getId()); 
           }
        }
        else{
           if(segundo.isCpu()&&!primero.isCpu()){
                mensaje.setText("Turno de CPU"); 
           }
           else{
               mensaje.setText("Turno de jugador "+segundo.getId()); 
           }
        }
        mensaje.setFont(new javafx.scene.text.Font("Arial", 36));
        mensaje.setPrefWidth(325);
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
                gp.add(b, row, col);
            }
        }
        try{
           this.modificarTablero(jugadasHechas.get(cont)); 
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void modificarTablero(Jugada j) throws Exception{
        this.asignarJActual(turno);
        Jugada[][] copia = new Jugada[3][3];
        for(int i=0;i<jugadas.length;i++){
            for(int k=0; k<jugadas.length;k++){
                copia[i][k]= new Jugada(jugadas[i][k].getRow(),jugadas[i][k].getCol(),jugadas[i][k].getSimbolo(),jugadas[i][k].getId());
            }
        }
        int row = j.getRow();
        int col = j.getCol();
        this.ponerFicha(row, col);
        if(actual.isCpu()){
            this.recom.setText("");
            this.filaRecom.setText("");
            this.colRecom.setText("");
        }
        else{
            this.IA(actual, copia);
            int rowRecom = this.recomendada.getRowCPU();
            int colRecom = this.recomendada.getColCPU();
            if(rowRecom==row && colRecom==col){
                this.recom.setText("Se realizó una jugada recomendada");
                this.filaRecom.setText("");
                this.colRecom.setText("");
            }
            else{
                rowRecom++;
                colRecom++;
                String filaRecomendada="";
                String columnaRecomendada="";
                switch (rowRecom) {
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
                switch (colRecom) {
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
                String f = this.filaRecom.getText();
                String f2 = this.colRecom.getText();
                this.filaRecom.setText(f+" "+columnaRecomendada);
                this.colRecom.setText(f+" "+filaRecomendada);
            }
        }
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
            });
         pause.play(); 
    }
    
    private Simbolo asignarSimbolo(Jugador actual) {
        return new Simbolo("com/mycompany/images/"+actual.getTipoSimbolo()+".png",actual);
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
        System.out.println("Copia");
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
            this.recomendada= jd;
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
                        Tree<Jugada[][]> arb = new Tree(cop);
                        arb.getRootNode().setUtilidad(utilidadCPU-utilidadEnemy);
                        utilidades.add(arb.getRootNode().getUtilidad());
                        nodo.addChildren(arb);
                    }
                }
            }
            
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
                 System.out.println("Utilidad "+u);
                 utilidades2.offer(jg);
            }
            Jugada jg2 = utilidades2.poll();
            utilidades1.offer(jg2); 
        }
        this.recomendada= utilidades1.poll();
    }

    @FXML
    private void terminarJuego(MouseEvent event) throws IOException {
        Util.mostrarMensaje("Ha terminado el juego", "Juego terminado");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tresenrayag7/ModoDeJuegos.fxml"));
        Parent Modos_de_juegoParent = loader.load();
        Scene Modos_de_juegoScene = new Scene(Modos_de_juegoParent,730,480);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Modos_de_juegoScene);
        window.show(); 
    }

    @FXML
    private void siguienteJug(MouseEvent event) throws Exception {
        if(cont<jugadasHechas.size()-1){
            cont++;
            turno++;
            this.visualizarTurno(turno);
            this.asignarJActual(turno);
            this.modificarTablero(jugadasHechas.get(cont));
        }
        else{
            Util.mostrarMensaje("No hay mas jugadas para mostrar", "Sin jugadas");
        }
    }
    
}
