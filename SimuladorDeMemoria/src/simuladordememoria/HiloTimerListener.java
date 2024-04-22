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
 * Esta clase se encarga de la asignacion y expulsion de procesos.
 * Lo ejecutado en actionPerformed se ejecuta cada segundo.
 */
public class HiloTimerListener implements ActionListener {

    private Hilo hiloPrincipal;
    private boolean segundaIteracion;

    public HiloTimerListener(Hilo hiloPrincipal) {
        this.hiloPrincipal = hiloPrincipal;
        this.segundaIteracion = false;
    }

    /**
     * 
     * Metodo que manda a llamar la politica dependiendo del valor ingresado por
     * el usuario
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.hiloPrincipal.getAjuste() == 0) { 
            this.primerAjuste();
        }
        if (this.hiloPrincipal.getAjuste() == 1) {
            this.mejorAjuste();
        }
        this.actualizarTiempoTranscurrido(); //Cada iteracion se actualiza el tiempo transcurrido
    }

    /**
     * Metodo que invoca la asignacion de memoria siguiendo la politica de primer ajuste
     */
    private void primerAjuste() {
        if (!segundaIteracion) { // Se evalua si es la primer iteracion
            // En la primer iteracion se obtiene la lista de procesos completa al igual que las particiones
            LinkedList<Proceso> procesos = this.hiloPrincipal.getListaProcesos();
            LinkedList<Particion> particiones = this.hiloPrincipal.getListaParticiones();

            // Se itera sobre la lista de procesos para llamar al metodo ingresarProceso.
            for (Proceso proceso : procesos) {
                for (Particion particion : particiones) {
                    // Se evalua que se cumplan con los requisitos basados en la politica
                    if (proceso.getMemoriaRequerida() <= particion.getTamanio() && !particion.getPanel().getEnUso()) {
                        this.ingresarProceso(proceso, particion); // Se ingresa el proceso
                        break;
                    }
                    proceso.setEstado(2); // Se actualiza el estado del proceso
                    this.hiloPrincipal.getModelo().setValueAt("En espera", proceso.getId() - 1, 4); // Se actualiza la tabla
                }

            }
            segundaIteracion = true; // Se determina que la primer iteracion termino

        } else {
            this.expulsarProcesos(); // En la segunda iteracion se evalua si hay procesos expulsables

            // Las listas de procesos a tomar solo seran la de procesos en espera. Y las particiones seran las libres
            LinkedList<Proceso> procesosEspera = this.obtenerProcesosEjecutandoYEspera().get(1);
            LinkedList<Particion> particionesLibres = this.obtenerParticionesLibres();

            // Se itera la lista de procesos en espera y la lista de particiones
            for (Proceso proceso : procesosEspera) {
                for (Particion particion : particionesLibres) {
                    if (proceso.getMemoriaRequerida() <= particion.getTamanio() && !particion.getPanel().getEnUso()) {
                        this.ingresarProceso(proceso, particion);
                        break;
                    }
                }
            }

        }

    }


    /**
     * Metodo que invoca la asignacion de memoria siguiendo la politica de mejor ajuste
     */
    private void mejorAjuste() {
        if (!segundaIteracion) {
            // Se obtiene la lista general de procesos en la primer iteracion
            LinkedList<Proceso> procesos = this.hiloPrincipal.getListaProcesos();
            LinkedList<Particion> particiones = this.hiloPrincipal.getListaParticiones();

            // Se itera sobre toda la lista de particiones para obtener el mejor ajuste
            for (Proceso proceso : procesos) {
                int mejorValor = Integer.MAX_VALUE;
                Particion tParticion = new Particion(0);
                for (Particion particion : particiones) {
                    // Se setean variables auxiliares
                    int tamanioProceso = proceso.getMemoriaRequerida();
                    int tamanioParticion = particion.getTamanio();
                    int valor = tamanioParticion - tamanioProceso;

                    // Se hace la evaluacion de la politica de mejor ajuste.
                    if (valor < mejorValor && valor >= 0 && !particion.getPanel().getEnUso()) {
                        tParticion = particion;
                        mejorValor = valor;
                    }
                }
                if(mejorValor != Integer.MAX_VALUE){
                    this.ingresarProceso(proceso, tParticion); // Si se encontro un buen ajuste se ingresara el proceso
                }else{
                    proceso.setEstado(2); // Si no se encuentra un buen ajuste el estado del proceso queda en espera
                    this.hiloPrincipal.getModelo().setValueAt("En espera", proceso.getId() - 1, 4);
                }

            }
            segundaIteracion = true;
        }else{
            this.expulsarProcesos(); // EN la segunda iteracion se evalua si se pueden expulsar procesos

            // Se obtioene la lista de espera de procesos y la lista de particiones libres
            LinkedList<Proceso> procesosEspera = this.obtenerProcesosEjecutandoYEspera().get(1);
            LinkedList<Particion> particionesLibres = this.obtenerParticionesLibres();
            
            // Se itera sobre los proceso utilizando el algoritmo de mejor ajuste
            for(Proceso proceso : procesosEspera){
                int mejorValor = Integer.MAX_VALUE;
                Particion tParticion = new Particion(0);
                
                for(Particion particion : particionesLibres){
                    int tamanioProceso = proceso.getMemoriaRequerida();
                    int tamanioParticion = particion.getTamanio();
                    int valor = tamanioParticion - tamanioProceso;

                    if (valor < mejorValor && valor >= 0 && !particion.getPanel().getEnUso()) {
                        tParticion = particion;
                        mejorValor = valor;
                    }
                }
                if(mejorValor != Integer.MAX_VALUE){
                    this.ingresarProceso(proceso, tParticion);
                }
            }
        }
    }

    /**
     * Metodo que obtiene las particiones que no tienen procesos asignados
     * @return tParticiones, un LinkedList con las particiones libres
     */
    private LinkedList<Particion> obtenerParticionesLibres() {
        LinkedList<Particion> tParticiones = new LinkedList();

        for (Particion particion : this.hiloPrincipal.getListaParticiones()) {
            if (!particion.getPanel().getEnUso()) {
                tParticiones.add(particion);
            }
        }

        return tParticiones;
    }

    /**
     * Metodo que obtiene tanto los procesos ejecutando como los procesos en espera
     * @return returnable, un LinkedList que poseera dos LinkedList, el primero para procesos ejecutando
     * y el segundo para procesos en espera
     */
    private LinkedList<LinkedList<Proceso>> obtenerProcesosEjecutandoYEspera() {
        LinkedList<Proceso> tProcesosEjecutando = new LinkedList();
        LinkedList<Proceso> tProcesosEspera = new LinkedList();
        LinkedList<LinkedList<Proceso>> returnable = new LinkedList();

        for (Proceso proceso : this.hiloPrincipal.getListaProcesos()) {

            switch (proceso.getEstado()) {
                case 1:
                    tProcesosEjecutando.add(proceso);
                    break;
                case 2:
                    tProcesosEspera.add(proceso);
                    break;
            }
        }
        returnable.add(tProcesosEjecutando);
        returnable.add(tProcesosEspera);

        return returnable;
    }

    /**
     * Metodo que ingresa el proceso a una particion
     * Este metodo se encarga de cambiar el color del panel y el estado del proceso a ejecutando
     * @param proceso El proceso a ejecutar
     * @param particion La particion a asignar
     */
    private void ingresarProceso(Proceso proceso, Particion particion) {
        float porcentaje = 100.0F * proceso.getMemoriaRequerida()/ particion.getTamanio();
        particion.getPanel().setEnUso(true);
        particion.setProcesoAsignado(proceso);
        proceso.setMejorParticion(particion);

        particion.getPanel().setTexto(proceso.getNombre(),
                String.valueOf(this.hiloPrincipal.getListaParticiones().indexOf(particion) + 1),
                String.valueOf(particion.getTamanio()),
                String.valueOf(porcentaje) + "%",
                String.valueOf(proceso.getMemoriaRequerida()));

        proceso.setEstado(1);
        this.hiloPrincipal.getModelo().setValueAt("Ejecutando", proceso.getId() - 1, 4);
    }

    /**
     * Metodo que expulsa todos los procesos expulsables
     */
    private void expulsarProcesos() {
        LinkedList<Proceso> procesosEjecutando = this.obtenerProcesosEjecutandoYEspera().get(0);

        for (Proceso proceso : procesosEjecutando) {
            int tiempoEjecutando = proceso.getTiempoEjecutando();
            int duracion = proceso.getDuracion();

            if (tiempoEjecutando >= duracion) {
                this.expulsarProceso(proceso);
            }
        }
    }

    /**
     * Metodo que expulsa un proceso en especifico. Se encarga de cambiar el estado del panel y del proceso
     * @param proceso el proceso a expulsar
     */
    private void expulsarProceso(Proceso proceso) {
        proceso.getMejorPartcion().getPanel().setEnUso(false);

        proceso.getMejorPartcion().getPanel().setTexto("", "", "", "", "");

        proceso.setEstado(3);
        this.hiloPrincipal.getModelo().setValueAt("Terminado", proceso.getId() - 1, 4);

        proceso.getMejorPartcion().setProcesoAsignado(null);
    }

    /**
     * Metodo que actualiza el tiempo transcurrido de los procesos
     * Solo actualizara los procesos ejecutando y en espera. No actualizara los proceso terminados
     */
    private void actualizarTiempoTranscurrido() {
        LinkedList<LinkedList<Proceso>> procesos = this.obtenerProcesosEjecutandoYEspera();

        for (Proceso proceso : procesos.get(0)) {
            proceso.actualizarTiempoTranscurrido();
            this.hiloPrincipal.getModelo().setValueAt(proceso.getTiempoTranscurrido(), proceso.getId() - 1, 5);
        }

        for (Proceso proceso : procesos.get(1)) {
            proceso.actualizarTiempoTranscurrido();
            this.hiloPrincipal.getModelo().setValueAt(proceso.getTiempoTranscurrido(), proceso.getId() - 1, 5);
        }
    }
    
    /**
     * Metodo que obtiene especificamente la cola de procesos en espera
     * @return 
     */
    public LinkedList<Proceso> getColaProcesos(){
        return this.obtenerProcesosEjecutandoYEspera().get(1);
    }

}
