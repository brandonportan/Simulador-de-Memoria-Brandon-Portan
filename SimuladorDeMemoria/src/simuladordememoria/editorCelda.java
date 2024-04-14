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
import java.awt.Color;
import java.awt.Component;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class editorCelda extends DefaultCellEditor {

    public int suma;
    public boolean hayExcepcion;
    private DefaultTableModel model;
    private int totalMemoria;
    private JTextField textField;
    private JLabel errorLabel;
    private int valorAnterior = 0;

    public editorCelda(JLabel errorLabel, DefaultTableModel model) {
        super(new JTextField());
        this.errorLabel = errorLabel;
        this.suma = 0;
        this.model = model;
        textField = (JTextField) getComponent();
        textField.addActionListener(e -> stopCellEditing());
       /* textField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarValor();
            }
        });*/
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
       try {
            
            valorAnterior = Integer.parseInt(value.toString());
        } catch (NumberFormatException | NullPointerException e) {
            valorAnterior = 0;
        }
        System.out.println("Valor anterior set: " + valorAnterior);
        obtenerValor();
        return c;
    }
    
    

    @Override
    public boolean stopCellEditing() {
        if(validarValor(obtenerValor())){
            return super.stopCellEditing();
        }else{
            return false;
        }
        
    }

    private int obtenerValor() {
        int valor = 0;
        try {

            
            if (textField.getText().trim().length() != 0) {
                valor = Integer.parseInt(textField.getText());
                
            }
          /*  if(this.suma + valor > this.totalMemoria){
                return -1001;
            }*/

        } catch (NumberFormatException e) {
            this.errorLabel.setForeground(Color.red);
            this.errorLabel.setText("El valor debe ser un número entero ");
            return -1002;
        } catch (Exception ex) {
            this.errorLabel.setText(ex.getMessage());
        }
        System.out.println("Valor retornado: " + valor);
            return valor;

    }

    public void setSuma(int valor) {
        System.out.println("Suma seteada "+ valor);
        this.suma = valor;
    }

    public void setMemoria(int valor) {
        System.out.println("Memoria seteada "+ valor);
        this.totalMemoria = valor;
    }

    public boolean validarBlancosTabla() {
    boolean esValido = true;
    for (int i = 0; i < model.getRowCount(); i++) {
        Object valorCelda = model.getValueAt(i, 1);
        if (!(valorCelda != null && !valorCelda.toString().trim().isEmpty())) {
            esValido = false;
        }
    }
    if(!esValido){
        this.errorLabel.setForeground(Color.red);
    this.errorLabel.setText("Hay valores en blanco en la tabla");
    return esValido;
    }else{
        return esValido;
    }
    
 }
    
   private boolean validarValor(int valor) {   
            if(valor == -1001){
                this.errorLabel.setText("La suma de particiones sobrepasa a la memoria total");
                return false;
            }
            if(valor == -1002){
                this.errorLabel.setText("Debe utilizar numeros enteros");
                return false;
            }
            if(valor < 0){
               this.errorLabel.setText("El valor no puede ser negativo");
               return false;
            }
            if(valor <1 && this.textField.getText().trim().length() !=0){
                this.errorLabel.setText("El valor debe ser mayor o igual a 1");
                return false;
            }
            
            /*if((this.suma + valor)> this.totalMemoria){
                System.out.println("Suma vealuada: " + (this.suma+valor));
                this.errorLabel.setText("La suma sobrepasa a la memoria total: suma = "+this.suma);
                return false;
            }*/
            
            this.errorLabel.setText("");
            return true;
    }
   
   public boolean validarSumaTotal(){
       if(this.suma < this.totalMemoria){
           this.errorLabel.setForeground(Color.red);
           this.errorLabel.setText("No se ha cubierto el total de memoria: Suma = " + this.suma);
           return false;
       }
       if(this.suma > this.totalMemoria){
           this.errorLabel.setForeground(Color.red);
           this.errorLabel.setText("La suma sobrepasa el tamaño de memoria: Suma = " + this.suma);
           return false;
       }
       return true;
       
       
   }
   
   public LinkedList<Particion> obtenerLista(){
       LinkedList lista;
        lista = new LinkedList();
        
        for(int i=0; i< this.model.getRowCount(); i++){
            int valorCelda =Integer.parseInt( model.getValueAt(i, 1).toString());
            Particion p = new Particion(valorCelda);
            lista.add(p);
            
        }
        System.out.println("Lista: "+ lista);
        return lista;
   }
  
    
    /*  if (valor < 0) {
                this.hayExcepcion = true;
                throw new Exception("El valor no puede ser negativo");
            }
            if (valor < 1 && this.textField.getText().trim().length() != 0) {
                this.hayExcepcion = true;
                throw new Exception("El valor debe ser mayor o igual a 1");
            }*/
}
