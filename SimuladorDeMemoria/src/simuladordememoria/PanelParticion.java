
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public class PanelParticion extends JPanel{
    private String proceso;
    private String numeroParticion;
    private String tamanio;
    private String porcentajeUso;
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
     
     public void setTexto(String proceso, String numeroParticion, String tamanio, String porcentajeUso) {
        this.proceso = proceso;
        this.numeroParticion = numeroParticion;
        this.tamanio = tamanio;
        this.porcentajeUso = porcentajeUso;
        this.repaint();
    }
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (enUso) {
            this.setBackground(Color.YELLOW);
        } else {
            this.setBackground(Color.GREEN);
        }
        g.setColor(Color.BLACK);
        g.drawString(this.proceso, 10, 20);
        g.drawString(this.numeroParticion, 10, 30);
        g.drawString(this.tamanio, 10, 40);
        g.drawString(this.porcentajeUso, 10, 50);
        
    }
}
