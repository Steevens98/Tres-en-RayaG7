/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Modelos;

/**
 *
 * @author gutav
 */
public class Resultado {

    private TipoResul tipoResul;
    private int cantidad;

    public Resultado(TipoResul tipoResul, int cantidad) {
        this.tipoResul = tipoResul;
        this.cantidad = cantidad;
    }

    public TipoResul getTipoResul() {
        return tipoResul;
    }

    public void setTipoResul(TipoResul tipoResul) {
        this.tipoResul = tipoResul;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
