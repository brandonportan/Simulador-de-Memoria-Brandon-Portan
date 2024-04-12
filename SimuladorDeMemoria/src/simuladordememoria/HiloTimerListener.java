/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 *
 * @author LENOVO
 */
public class HiloTimerListener implements ActionListener{
    private Hilo hiloPrincipal;
    
    public HiloTimerListener(Hilo hiloPrincipal){
        this.hiloPrincipal = hiloPrincipal;
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        
    }
    
    private void primerAjuste(){
        
    }
    
    private LinkedList<Particion> obtenerParticionesLibres(){
        LinkedList<Particion> tParticiones = new LinkedList();
        
        for(Particion particion : this.hiloPrincipal.getListaParticiones()){
            if(!particion.getPanel().getEnUso()){
                tParticiones.add(particion);
            }
        }
        
        return tParticiones;
    }
    
    private LinkedList<Proceso> obtenerProcesosEjecutando(){
        LinkedList<Proceso> tProcesos = new LinkedList();
        
        for(Proceso proceso : this.hiloPrincipal.getListaProcesos()){
            if(proceso.getEstado() == 1){
                tProcesos.add(proceso);
            }
        }
        
        return tProcesos;
    }
    
    private void ingresarProceso(Proceso proceso, Particion particion){
        float porcentaje = (float)(100/(particion.getTamanio() / proceso.getMemoriaRequerida()));
        particion.getPanel().setEnUso(true);
        
        particion.getPanel().setTexto(proceso.getNombre(), 
                            String.valueOf(this.hiloPrincipal.getListaParticiones().indexOf(particion)+1), 
                            String.valueOf(particion.getTamanio()), 
                            String.valueOf(porcentaje)+"%");
        
        proceso.setEstado(1);
        this.hiloPrincipal.getModelo().setValueAt( "Ejecutando", proceso.getId() - 1, 4);
    }
    
    private void expulsarProcesos(){
        
    }
    
    private void expulsarProceso(Proceso proceso){
        
    }
    
    
    
    
}
