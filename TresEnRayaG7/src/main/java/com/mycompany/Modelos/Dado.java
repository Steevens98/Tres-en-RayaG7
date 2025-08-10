package com.mycompany.Modelos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gutav
 */
public class Dado {
    private Map<Integer,String> numeros;
    private Jugador j1;
    private Jugador j2;
    private String mov;
    private boolean lanzado;
    private ResultadoDado resul;
    public Dado(){
        this.numeros= new HashMap<>();
        this.mov="com/mycompany/images/dadomov.gif";
        this.lanzado=false;
        this.resul=null;
        inicializarNumeros();
    }
    private void inicializarNumeros() {
        // Asignar los números a cada lado del dado
        numeros.put(1, "com/mycompany/images/dados1.png");
        numeros.put(2, "com/mycompany/images/dados2.png");
        numeros.put(3, "com/mycompany/images/dados3.png");
        numeros.put(4, "com/mycompany/images/dados4.png");
        numeros.put(5, "com/mycompany/images/dados5.png");
        numeros.put(6, "com/mycompany/images/dados6.png");
    }
    public ResultadoDado lanzar() {
        // Obtener un número aleatorio entre 1 y 6
        int numeroAleatorio = (int) (Math.random() * 6) + 1;

        // Obtener el movimiento asociado al número lanzado
        String lado = numeros.get(numeroAleatorio);

        // Marcar el dado como lanzado
        lanzado = true;

        // Devolver el movimiento asociado
        return new ResultadoDado(lado,numeroAleatorio);
    }
    public boolean repetirTirada(Jugador j1,Jugador j2){
        if(j2.getDado()==j1.getDado()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public String getMov() {
        return mov;
    }

    public boolean isLanzado() {
        return lanzado;
    }
    public void reiniciar() {
        lanzado = false;
    }
    
}
