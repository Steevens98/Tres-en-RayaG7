/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Modelos;

/**
 *
 * @author gutav
 */
public class Jugada {
    
    private int row;
    private int col;
    private String simbolo;
    private int id;
    private int utilidad;
    private int rowCPU;
    private int colCPU;
    public Jugada(int row, int col, String simbolo, int id) {
        this.row = row;
        this.col = col;
        this.simbolo = simbolo;
        this.id = id;
        this.utilidad=Integer.MIN_VALUE;
        this.rowCPU=-1;
        this.colCPU=-1;
    }
    public Jugada(int row, int col, int id) {
        this.row = row;
        this.col = col;
        this.simbolo =null;
        this.id = id;
        this.utilidad=Integer.MIN_VALUE;
        this.rowCPU=-1;
        this.colCPU=-1;
    }

    public Jugada(int row, int col, String simbolo, int id, int rowCPU, int colCPU) {
        this.row = row;
        this.col = col;
        this.simbolo = simbolo;
        this.id = id;
        this.utilidad = Integer.MIN_VALUE;
        this.rowCPU = rowCPU;
        this.colCPU = colCPU;
    }
    

    public int getRowCPU() {
        return rowCPU;
    }

    public void setRowCPU(int rowCPU) {
        this.rowCPU = rowCPU;
    }

    public int getColCPU() {
        return colCPU;
    }

    public void setColCPU(int colCPU) {
        this.colCPU = colCPU;
    }
    
    public int getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(int utilidad) {
        this.utilidad = utilidad;
    }
    

    public Jugada(int row, int col) {
        this.row = row;
        this.col = col;
        this.utilidad=Integer.MIN_VALUE;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
