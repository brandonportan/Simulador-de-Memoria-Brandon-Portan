/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordememoria;

import javax.swing.table.DefaultTableModel;

/**
 * Clase que crea un Modelo para la tabla de procesos creada en Ventana2
 * @author LENOVO
 */
public class ModeloV2 extends DefaultTableModel{
    

    
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void addColumn(Object columnName) {
        super.addColumn("ID");
        super.addColumn("Nombre");
        super.addColumn("Duracion");
        super.addColumn("Memoria Requerida");
        super.addColumn("Estado");
        super.addColumn("Tiempo Transcurrido");
    }
    
}
