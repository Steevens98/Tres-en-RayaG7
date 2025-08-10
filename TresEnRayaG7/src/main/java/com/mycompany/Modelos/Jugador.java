
package com.mycompany.Modelos;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author gutav
 */
public class Jugador {
    private int id;
    private String nombre;
    private String tipoSimbolo;
    private int intsimbolo; //x=1 o=2
    private int dado;
    private boolean cpu;
    private int puntuacion;

    public Jugador(int id, String nombre, String tipoSimbolo,int puntuacion) {
        this.id = id;
        this.nombre = nombre;
        this.tipoSimbolo = tipoSimbolo;
        this.dado=0;
        this.cpu=false;
        this.puntuacion=puntuacion;
    }
    public Jugador() {
        this.id = 0;
        this.nombre = null;
        this.tipoSimbolo = null;
        this.dado=0;
        this.cpu=false;
    }
    public Jugador(String nombre) {
        this.id = 0;
        this.nombre = nombre;
        this.tipoSimbolo = null;
        this.dado=0;
        this.cpu=false;
    }
    public Jugador(int id,String tipoS,int puntuacion) {
        this.id = id;
        this.nombre = null;
        this.tipoSimbolo = tipoS;
        this.dado=0;
        this.cpu=false;
        this.puntuacion=puntuacion;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCpu() {
        return cpu;
    }

    public void setCpu(boolean cpu) {
        this.cpu = cpu;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoSimbolo() {
        return tipoSimbolo;
    }

    public void setTipoSimbolo(String tipoSimbolo) {
        this.tipoSimbolo = tipoSimbolo;
    }

    public int getDado() {
        return dado;
    }

    public void setDado(int dado) {
        this.dado = dado;
    }
    
    @Override
    public String toString() {
        return "Jugador{" + "id=" + id + ", nombre=" + nombre + ", tipoSimbolo=" + tipoSimbolo + '}';
    }
    public static ArrayList<Jugador> randomizarSimbolos(){
        ArrayList<Jugador> players= new ArrayList<>();
        ArrayList<String> pieceTypes = new ArrayList<>();
        pieceTypes.add("O");
        pieceTypes.add("X");
        Collections.shuffle(pieceTypes);
        for(int i=0; i<pieceTypes.size();i++){
            Jugador j = new Jugador();
            j.setId(i+1);
            j.setTipoSimbolo(pieceTypes.get(i));
            players.add(j);
        }
        return players;
    }
    public void sumarPuntuacion(int n){
        puntuacion+=n;
    }
    public void restarPuntuacion(int n){
        puntuacion=puntuacion-n;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getIntsimbolo() {
        return intsimbolo;
    }

    public void setIntsimbolo(int intsimbolo) {
        this.intsimbolo = intsimbolo;
    }
    
}
