
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Clase que se encarga de manejar los paneles.
 * Esto maneja la interfaz grafica que representan las particiones donde se ingresan los procesos
 * @author LENOVO
 */
public class PanelParticion extends JPanel{
    private String proceso;
    private String numeroParticion;
    private String tamanio;
    private String porcentajeUso;
    private String tamanioProceso;
    private boolean enUso=false;
    
    public PanelParticion() {
        super();
        this.setPreferredSize(new Dimension(25, 25));
    }
    
     public void setEnUso(boolean enUso) {
        this.enUso = enUso;
        this.repaint();
    }
     
     public boolean getEnUso(){
         return this.enUso;
     }
     
     /**
      * Este metodo se encarga de alistar los valores del texto en el panel de la intefaz grafica
      * @param proceso
      * @param numeroParticion
      * @param tamanio
      * @param porcentajeUso
      * @param tamanioProceso 
      */
     public void setTexto(String proceso, String numeroParticion, String tamanio, String porcentajeUso, String tamanioProceso) {
        this.proceso = proceso;
        this.numeroParticion = numeroParticion;
        this.tamanio = tamanio;
        this.porcentajeUso = porcentajeUso;
        this.tamanioProceso = tamanioProceso;
        this.repaint();
    }
     
     /**
      * Hace el cambio tanto de los colores como del texto en la interfaz grafica
      * @param g 
      */
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (enUso) {
            this.setBackground(Color.YELLOW);
        } else {
            this.setBackground(Color.GREEN);
        }
        g.setFont(new Font("Arial", Font.BOLD, 11));
        
        g.setColor(Color.BLACK);
        g.drawString(this.proceso, 10, 30);
        g.drawString(this.numeroParticion, 10, 50);
        g.drawString("TPart: "+this.tamanio, 10, 70);
        g.drawString("TPro: "+ this.tamanioProceso, 10, 90);
        g.drawString(this.porcentajeUso, 10, 110);
        
    }
}
