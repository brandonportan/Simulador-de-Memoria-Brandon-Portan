/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Timer;

/**
 *
 * @author LENOVO
 */
public class Hilo extends Thread{
    private LinkedList<Proceso> listaProcesos;
    private LinkedList<Particion> listaParticiones;
    private int ajuste;
    private Timer timerGlobal;
    private ModeloV2 model;
    
    public Hilo(LinkedList<Proceso> listaProcesos, LinkedList<Particion> listaParticiones, int ajuste, ModeloV2 model){
        this.listaProcesos = listaProcesos;
        this.listaParticiones = listaParticiones;
        this.ajuste = ajuste;
        this.model = model;
    }
    
    public void run(){
        
    }

    
    
    
    public void addProceso(Proceso proceso){
        this.listaProcesos.add(proceso);
    }
    
    public LinkedList<Proceso> getListaProcesos(){
        return this.listaProcesos;
    }
    
    public LinkedList<Particion> getListaParticiones(){
        return this.listaParticiones;
    }
    
    public int getAjuste(){
        return this.ajuste;
    }
    
    public Timer getTimerGlobal(){
        return this.timerGlobal;
    }
    
    public ModeloV2 getModelo(){
        return this.model;
    }
    
}
