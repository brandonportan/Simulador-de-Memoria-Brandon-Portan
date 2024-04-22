/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import javax.swing.Timer;

/**
 * Clase que representa un Proceso
 * @author LENOVO
 */
public class Proceso {
    private int id;
    private String nombre;
    private int duracion;
    private int memoriaRequerida;
    private int estado;
    private int tiempoTranscurrido; 
    private Particion mejorParticion;
    private int tiempoEjecutando;

    
    public Proceso(int id, String nombre, int duracion, int memoriaRequerida) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.memoriaRequerida = memoriaRequerida;
        this.estado = 0;
        this.tiempoTranscurrido = 0;
    }
    // Setters y Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getMemoriaRequerida() {
        return memoriaRequerida;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public void setTiempoTranscurrido(int tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    
    public Particion getMejorPartcion(){
        return this.mejorParticion;
    }
    
    public void setMejorParticion(Particion mejorParticion){
        this.mejorParticion = mejorParticion;
    }
    
    public int getTiempoEjecutando(){
        return this.tiempoEjecutando;
    }
    
    public void actualizarTiempoTranscurrido(){
        this.tiempoTranscurrido++;
        if(this.estado == 1)
            this.tiempoEjecutando++;
    }
}
