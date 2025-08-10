/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Modelos;

/**
 *
 * @author gutav
 */
public class Simbolo {

    private String imagen;
    private Jugador j;
    private int fila;
    private int columna;
    private boolean enArbol;
    private int utilidad;

    public Simbolo(String imagen, Jugador j) {
        this.imagen = imagen;
        this.j = j;
        this.fila = 0;
        this.columna = 0;
        this.enArbol = false;
        this.utilidad = 0;
    }

    public Simbolo(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.imagen = null;
        this.j = null;
        this.enArbol = false;
        this.utilidad = 0;
    }

    public int getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(int utilidad) {
        this.utilidad = utilidad;
    }

    public boolean isEnArbol() {
        return enArbol;
    }

    public void setEnArbol(boolean enArbol) {
        this.enArbol = enArbol;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Jugador getJ() {
        return j;
    }

    public void setJ(Jugador j) {
        this.j = j;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
