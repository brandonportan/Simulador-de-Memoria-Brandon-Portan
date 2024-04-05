/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

/**
 *
 * @author LENOVO
 */
public class Particion {
    private int tamanio;
    private PanelParticion panel;
    
    public Particion(int tamanio){
        this.tamanio = tamanio;

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
}
