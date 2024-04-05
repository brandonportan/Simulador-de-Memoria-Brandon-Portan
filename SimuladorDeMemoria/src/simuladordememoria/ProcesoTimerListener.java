/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author LENOVO
 */
public class ProcesoTimerListener implements ActionListener {
    private Proceso proceso;
    private ModeloV2 model;
    
    public ProcesoTimerListener(Proceso proceso, ModeloV2 model){
        
        this.proceso = proceso;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        proceso.actualizarTiempoTranscurrido();
        model.setValueAt( proceso.getTiempoTranscurrido(), proceso.getId() - 1, 5);
    }
    
    
}
