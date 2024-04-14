/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import java.util.LinkedList;

/**
 *
 * @author LENOVO
 */
public class Particion {
    private int tamanio;
    private PanelParticion panel;
    private Proceso procesoAsignado;
    private LinkedList<Proceso> procesosEnCola;
    
    public Particion(int tamanio){
        this.tamanio = tamanio;
        this.procesosEnCola = new LinkedList();
    }
    
    public void setTamanio(int tamanio){
        this.tamanio = tamanio;
    }
    
    public int getTamanio(){
        return this.tamanio;
    }
    
    public void setPanel(PanelParticion panel){
        this.panel = panel;
    }
    
    public PanelParticion getPanel(){
        return this.panel;
    }
    
    public Proceso getProcesoAsignado(){
        return this.procesoAsignado;
    }
    
    public void setProcesoAsignado(Proceso procesoAsignado){
        this.procesoAsignado = procesoAsignado;
    }
    
    public LinkedList<Proceso> getProcesosEnCola(){
        return this.procesosEnCola;
    }
}
