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
public class HiloTimerListener implements ActionListener {

    private Hilo hiloPrincipal;
    private boolean segundaIteracion;

    public HiloTimerListener(Hilo hiloPrincipal) {
        this.hiloPrincipal = hiloPrincipal;
        this.segundaIteracion = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.hiloPrincipal.getAjuste() == 0) {
            this.primerAjuste();
        }
        if (this.hiloPrincipal.getAjuste() == 1) {
            this.mejorAjuste();
        }
        this.actualizarTiempoTranscurrido();
    }

    private void primerAjuste() {
        if (!segundaIteracion) {
            LinkedList<Proceso> procesos = this.hiloPrincipal.getListaProcesos();
            LinkedList<Particion> particiones = this.hiloPrincipal.getListaParticiones();

            for (Proceso proceso : procesos) {
                for (Particion particion : particiones) {
                    if (proceso.getMemoriaRequerida() <= particion.getTamanio() && !particion.getPanel().getEnUso()) {
                        this.ingresarProceso(proceso, particion);
                        break;
                    }
                    proceso.setEstado(2);
                    this.hiloPrincipal.getModelo().setValueAt("En espera", proceso.getId() - 1, 4);
                }

            }
            segundaIteracion = true;

        } else {
            this.expulsarProcesos();

            LinkedList<Proceso> procesosEspera = this.obtenerProcesosEjecutandoYEspera().get(1);
            LinkedList<Particion> particionesLibres = this.obtenerParticionesLibres();

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

    /* private void mejorAjuste() { //UTILIZANDO UNA COLA PARA CADA PARTICION
        if (!segundaIteracion) {
            LinkedList<Proceso> procesos = this.hiloPrincipal.getListaProcesos();
            LinkedList<Particion> particiones = this.hiloPrincipal.getListaParticiones();

            for (Proceso proceso : procesos) {
                int mejorValor = Integer.MAX_VALUE;
                Particion tParticion = new Particion(0);
                for (Particion particion : particiones) {
                    int tamanioProceso = proceso.getMemoriaRequerida();
                    int tamanioParticion = particion.getTamanio();
                    int valor = tamanioParticion - tamanioProceso;

                    if (proceso.getId() % 2 == 0) {
                        if (valor <= mejorValor && valor >= 0) {
                            tParticion = particion;
                            mejorValor = valor;
                        }
                    } else {
                        if (valor < mejorValor && valor >= 0) {
                            tParticion = particion;
                            mejorValor = valor;
                        }
                    }

                }
                tParticion.getProcesosEnCola().add(proceso);
                if (tParticion.getProcesosEnCola().indexOf(proceso) != 0) {
                    proceso.setEstado(2);
                    this.hiloPrincipal.getModelo().setValueAt("En espera", proceso.getId() - 1, 4);
                }
            }

            for (Particion particion : particiones) {
                if (!particion.getProcesosEnCola().isEmpty()) {
                    Proceso proceso = particion.getProcesosEnCola().poll();
                    this.ingresarProceso(proceso, particion);
                }
            }
            segundaIteracion = true;

        } else {
            this.expulsarProcesos();

            LinkedList<Particion> particionesLibres = this.obtenerParticionesLibres();

            for (Particion particion : particionesLibres) {
                if (!particion.getProcesosEnCola().isEmpty()) {
                    Proceso proceso = particion.getProcesosEnCola().poll();
                    this.ingresarProceso(proceso, particion);
                }
            }
        }
    }*/
    private void mejorAjuste() {
        if (!segundaIteracion) {
            LinkedList<Proceso> procesos = this.hiloPrincipal.getListaProcesos();
            LinkedList<Particion> particiones = this.hiloPrincipal.getListaParticiones();

            for (Proceso proceso : procesos) {
                int mejorValor = Integer.MAX_VALUE;
                Particion tParticion = new Particion(0);
                for (Particion particion : particiones) {
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
                }else{
                    proceso.setEstado(2);
                    this.hiloPrincipal.getModelo().setValueAt("En espera", proceso.getId() - 1, 4);
                }

            }
            segundaIteracion = true;
        }else{
            this.expulsarProcesos();

            LinkedList<Proceso> procesosEspera = this.obtenerProcesosEjecutandoYEspera().get(1);
            LinkedList<Particion> particionesLibres = this.obtenerParticionesLibres();
            
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

    private LinkedList<Particion> obtenerParticionesLibres() {
        LinkedList<Particion> tParticiones = new LinkedList();

        for (Particion particion : this.hiloPrincipal.getListaParticiones()) {
            if (!particion.getPanel().getEnUso()) {
                tParticiones.add(particion);
            }
        }

        return tParticiones;
    }

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

    private void expulsarProceso(Proceso proceso) {
        proceso.getMejorPartcion().getPanel().setEnUso(false);

        proceso.getMejorPartcion().getPanel().setTexto("", "", "", "", "");

        proceso.setEstado(3);
        this.hiloPrincipal.getModelo().setValueAt("Terminado", proceso.getId() - 1, 4);

        proceso.getMejorPartcion().setProcesoAsignado(null);
    }

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

}
