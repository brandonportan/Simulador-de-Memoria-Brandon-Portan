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
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Ventana1 extends javax.swing.JFrame {

    DefaultTableModel modelo;
    DefaultTableCellRenderer alinear;
    TableModelListener listener;
    editorCelda editor;
    int memTotal;
    int particiones;
    int ajuste;
    int tiempoMin;
    int tiempoMax;
    LinkedList<Particion> listaParticiones;

    
    private void calcularSumaParticiones() {
        try {
            int suma = 0;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int valor = 0;
                if (modelo.getValueAt(i, 1) != null) {
                    if (!modelo.getValueAt(i, 1).toString().trim().equals("")) {
                        valor = Integer.parseInt(modelo.getValueAt(i, 1).toString());
                        suma += valor;
                        
                    }

                }

            }
           
            editor.setSuma(suma);
            this.exceptionTabla.setForeground(Color.BLACK);
            this.exceptionTabla.setText("Suma actual: " + suma);
            this.exceptionTabla2.setText("Memoria Total: " + this.memTotal);
        } catch (Exception e) {
            exceptionTabla.setText("akfjas;dlkfjasdl");
            e.printStackTrace();
        }

    }

    private void validarTmin() throws NumberFormatException, Exception {
        if (tmin.getText().trim().length() == 0) {
            exceptionTmin.setText("");
            throw new Exception();

        } else {
            exceptionTmin.setText("");
            tiempoMin = Integer.parseInt(tmin.getText());
        }
        if (tiempoMin < 0) {
            exceptionTmin.setText("No pueden haber valores negativos");
            throw new Exception("No pueden haber valores negativos");
        }
        if (tiempoMin < 20 && tmin.getText().trim().length() != 0) {
            exceptionTmin.setText("El minimo debe ser mayor a 20");
            throw new Exception("El minimo debe ser mayor a 20");
        }
    }

    private void validarTmax() throws NumberFormatException, Exception {
        if (tmax.getText().trim().length() == 0) {
            exceptionTmax.setText("");

        } else {
            exceptionTmax.setText("");
            tiempoMax = Integer.parseInt(tmax.getText());
        }
        if (tmin.getText().length() == 0) {
            exceptionTmax.setText("Debe establecer un tiempo minimo primero");
            //exceptionTmin.setText("Debe establecer un tiempo minimo primero");
            throw new Exception("Debe establecer un tiempo minimo primero");
        }
        if (tiempoMax < 0 && tmax.getText().trim().length() != 0) {
            exceptionTmax.setText("No pueden haber valores negativos");
            throw new Exception("No pueden haber valores negativos");
        }
        if (tiempoMax < 40 && tmax.getText().trim().length() != 0) {
            exceptionTmax.setText("El maximo debe ser mayor a 40");
            throw new Exception("El maximo debe ser mayor a 40");
        }
    }

    private void validarTminTmax() throws Exception {
        boolean boxFilled = false;
        if (tmin.getText().trim().length() != 0 && tmax.getText().trim().length() != 0) {
            boxFilled = true;
        }
        if ((tiempoMax - tiempoMin) <= 0 && boxFilled) {
            throw new Exception("El tiempo maximo debe ser mayor al minimo");
        }
        if ((tiempoMax - tiempoMin) < 20 && boxFilled) {
            throw new Exception("El tiempo maximo debe superar por 20 o mas al tiempo minimo");
        }
    }

    private void validarBlancos() throws Exception {
        boolean throwExc = false;
        if (totalMem.getText().trim().length() == 0) {
            exceptionMemoria.setText("Debe colocar un valor valido");
            throwExc = true;
        }
        if (nPart.getText().trim().length() == 0) {
            exceptionParticion.setText("Debe colocar un valor valido");
            throwExc = true;
        }
        if (!primerAjuste.isSelected() && !mejorAjuste.isSelected()) {
            exceptionAjuste.setText("Debe seleccionar un ajuste");
            throwExc = true;
        }
        if (tmin.getText().trim().length() == 0) {
            exceptionTmin.setText("Debe colocar un valor valido");
            throwExc = true;
        }
        if (tmax.getText().trim().length() == 0) {
            exceptionTmax.setText("Debe colocar un valor valido");
            throwExc = true;
        }
        if (throwExc) {
            throw new Exception();
        }
    }

    private void obtenerAjuste() {
        if (primerAjuste.isSelected()) {
            ajuste = 0;
            exceptionAjuste.setText("");
        }
        if (mejorAjuste.isSelected()) {
            ajuste = 1;
            exceptionAjuste.setText("");
        }

    }

    private void validarMemoria() throws NumberFormatException, Exception {
        try {
            if (totalMem.getText().length() == 0) {
                memTotal = 0;
                exceptionMemoria.setText("");
            } else {
                memTotal = Integer.parseInt(totalMem.getText());
            }

            if (memTotal < 20 && totalMem.getText().length() != 0) {
                throw new Exception("Elija un tamaño mas grande de memoria");
            }

        } catch (NumberFormatException e) {
            exceptionMemoria.setText("Los numeros deben ser enteros");
            throw e;
        } catch (Exception ez) {
            exceptionMemoria.setText(ez.getMessage());
            throw ez;
        }
    }

    private void llenarTablaParticiones(int numero) {
        modelo.setRowCount(numero);
        for (int i = 0; i < numero; i++) {
            modelo.setValueAt(i + 1, i, 0);
        }
    }

    private void validarMemoriaParticion() throws NumberFormatException, Exception {

        if (memTotal / particiones < 5 && nPart.getText().trim().length() != 0) {/*Establecemos que el minimo de tamaño por particion debe ser 5**/
            exceptionParticion.setText("Elija un menor numero de particiones");
            throw new Exception("Elija un menor numero de particiones");
        }
    }

    private void validarParticiones() throws NumberFormatException, Exception {
        ///  try {
        particiones = Integer.parseInt(nPart.getText());
        if (nPart.getText().trim().length() == 0) {
            //  partException.setText("");
            throw new Exception("Debe llenar el cuadro");
        } else {
            if (totalMem.getText().trim().length() == 0) {
                throw new Exception("Debe establecer un tamaño de memoria");
            }
        }
        if (particiones <= 1) {
            exceptionParticion.setText("Debe haber mas de 1 particion");
            throw new Exception("Debe haber mas de 1 particion");
        }

        //   } 
    }

    /**
     * Creates new form Ventana1
     */
    public Ventana1() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        algoritmo = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        totalMem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nPart = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaParticion = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        exceptionParticion = new javax.swing.JLabel();
        primerAjuste = new javax.swing.JRadioButton();
        mejorAjuste = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        tmin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tmax = new javax.swing.JTextField();
        btnSig = new javax.swing.JButton();
        exceptionMemoria = new javax.swing.JLabel();
        exceptionAjuste = new javax.swing.JLabel();
        exceptionTmin = new javax.swing.JLabel();
        exceptionTmax = new javax.swing.JLabel();
        exceptionTabla = new javax.swing.JLabel();
        exceptionTabla2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Brandon Portan - Simulador de Memoria");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Total Memoria:");

        totalMem.setName("totalMem"); // NOI18N
        totalMem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                totalMemKeyReleased(evt);
            }
        });

        jLabel2.setText("Cantidad de Particiones");

        nPart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nPartKeyReleased(evt);
            }
        });

        tablaParticion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaParticion);

        jLabel3.setText("Tamaño de Particion:");

        exceptionParticion.setForeground(new java.awt.Color(255, 0, 0));

        algoritmo.add(primerAjuste);
        primerAjuste.setText("Primer Ajuste");
        primerAjuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                primerAjusteActionPerformed(evt);
            }
        });

        algoritmo.add(mejorAjuste);
        mejorAjuste.setText("Mejor Ajuste");
        mejorAjuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mejorAjusteActionPerformed(evt);
            }
        });

        jLabel4.setText("Tiempo Minimo por Proceso");

        tmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tminKeyReleased(evt);
            }
        });

        jLabel6.setText("Tiempo Maximo por Proceso");

        tmax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tmaxKeyReleased(evt);
            }
        });

        btnSig.setText("Siguiente");
        btnSig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSigActionPerformed(evt);
            }
        });

        exceptionMemoria.setForeground(new java.awt.Color(255, 0, 0));

        exceptionAjuste.setForeground(new java.awt.Color(255, 0, 0));

        exceptionTmin.setForeground(new java.awt.Color(255, 0, 0));

        exceptionTmax.setForeground(new java.awt.Color(255, 0, 0));

        exceptionTabla.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(primerAjuste)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mejorAjuste))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(91, 91, 91)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(totalMem, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                    .addComponent(nPart)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tmin, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                    .addComponent(tmax))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(exceptionMemoria)
                                    .addComponent(exceptionParticion)
                                    .addComponent(exceptionTmin)
                                    .addComponent(exceptionTmax)
                                    .addComponent(exceptionTabla)
                                    .addComponent(exceptionTabla2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(exceptionAjuste)))))
                .addContainerGap(301, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSig)
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalMem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exceptionMemoria))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nPart)
                    .addComponent(exceptionParticion))
                .addGap(38, 38, 38)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(exceptionTabla)
                        .addGap(18, 18, 18)
                        .addComponent(exceptionTabla2)
                        .addGap(114, 114, 114)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(primerAjuste)
                    .addComponent(mejorAjuste)
                    .addComponent(exceptionAjuste))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exceptionTmin))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exceptionTmax)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(tmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(btnSig)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column != 0) {
                    return true;
                } else {
                    return false;
                }
            }
            
            

        };
  
        editor = new editorCelda(exceptionTabla, modelo);
        editor.setClickCountToStart(1);

        alinear = new DefaultTableCellRenderer();
        alinear.setHorizontalAlignment(SwingConstants.CENTER);

        modelo.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 1) {
                    calcularSumaParticiones();

                }
            }
        });

        modelo.addColumn("Particion");
        modelo.addColumn("Tamaño");

        tablaParticion.setModel(modelo);
        tablaParticion.getColumnModel().getColumn(0).setCellRenderer(alinear);
        tablaParticion.setDefaultEditor(Object.class, editor);

    }//GEN-LAST:event_formWindowOpened

    private void nPartKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nPartKeyReleased
        try {
            // TODO add your handling code here:

            validarMemoria();

            try {
                validarParticiones();
                validarMemoriaParticion();
                llenarTablaParticiones(particiones);
                tablaParticion.removeEditor();
                exceptionParticion.setText("");
            } catch (NumberFormatException e) {
                if (nPart.getText().trim().length() != 0) {
                    exceptionParticion.setText("Utilizar numeros enteros");

                } else {
                    exceptionParticion.setText("");
                }

            } catch (Exception ex) {
                exceptionParticion.setText(ex.getMessage());
            }

            //  partException.setText("");
        } catch (NumberFormatException e) {
            exceptionMemoria.setText("Los numeros deben ser enteros");
            exceptionParticion.setText("Debe establecer un tamaño de memoria");

        } catch (Exception ez) {
            exceptionMemoria.setText(ez.getMessage());
            exceptionParticion.setText("Debe establecer un tamaño de memoria");
        }
    }//GEN-LAST:event_nPartKeyReleased

    private void btnSigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSigActionPerformed
        // TODO add your handling code here:
        try {
            editor.stopCellEditing();
            boolean blancoTabla = editor.validarBlancosTabla();
            boolean exTabla = editor.validarSumaTotal();
            System.out.println("La suma es: " + editor.suma);
            validarBlancos();
            validarMemoria();
            validarParticiones();
            validarMemoriaParticion();
            if(!exTabla || !blancoTabla)
                throw new Exception();
            
            this.listaParticiones = editor.obtenerLista();
            Ventana2 v2 = new Ventana2(this.memTotal, this.tiempoMin, this.tiempoMax, this.ajuste, this.listaParticiones);
            v2.setVisible(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Revise los errores en el formulario", "Error", NORMAL);
            //  ex.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Revise los errores en el formulario", "Error", NORMAL);
            //  e.printStackTrace();
        }
    }//GEN-LAST:event_btnSigActionPerformed

    private void totalMemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalMemKeyReleased
        try {
            // TODO add your handling code here:
            validarMemoria();
            exceptionMemoria.setText("");
         //   editor.setMemoria(memTotal);
            if (nPart.getText().trim().length() != 0) {
                validarParticiones();
                validarMemoriaParticion();
                llenarTablaParticiones(particiones);
            }
            
            editor.setMemoria(memTotal);
           // editor.revalidarValores();
            exceptionMemoria.setText("");
            exceptionParticion.setText("");
        } catch (Exception ex) {
            //Logger.getLogger(Ventana1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalMemKeyReleased

    private void mejorAjusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mejorAjusteActionPerformed
        // TODO add your handling code here:
        obtenerAjuste();
    }//GEN-LAST:event_mejorAjusteActionPerformed

    private void primerAjusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_primerAjusteActionPerformed
        // TODO add your handling code here:
        obtenerAjuste();
    }//GEN-LAST:event_primerAjusteActionPerformed

    private void tminKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tminKeyReleased
        // TODO add your handling code here:
        try {

            validarTmin();
            try {
                validarTmax();
                validarTminTmax();
            } catch (NumberFormatException es) {
                if (tmax.getText().trim().length() != 0) {
                    exceptionTmax.setText("Utilizar numeros enteros");
                }
            } catch (Exception es2) {
                if (tmax.getText().trim().length() != 0) {
                    exceptionTmax.setText(es2.getMessage());
                }
            }

        } catch (NumberFormatException e) {
            if (tmin.getText().trim().length() != 0) {
                exceptionTmin.setText("Utilizar numeros enteros");
            }
        } catch (Exception ex) {
            exceptionTmin.setText(ex.getMessage());
        }
    }//GEN-LAST:event_tminKeyReleased

    private void tmaxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tmaxKeyReleased
        // TODO add your handling code here:
        try {
            validarTmin();
            try {
                validarTmax();
                validarTminTmax();
            } catch (NumberFormatException es) {
                if (tmax.getText().trim().length() != 0) {
                    exceptionTmax.setText("Utilizar numeros enteros");
                }
            } catch (Exception es2) {
                if (tmax.getText().trim().length() != 0) {
                    exceptionTmax.setText(es2.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            exceptionTmax.setText("Establezca un valor valido de tiempo minimo");
        } catch (Exception es) {
            exceptionTmax.setText("Establezca un valor valido de tiempo minimo");
        }
    }//GEN-LAST:event_tmaxKeyReleased

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        editor.stopCellEditing();

    }//GEN-LAST:event_formMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana1().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup algoritmo;
    private javax.swing.JButton btnSig;
    private javax.swing.JLabel exceptionAjuste;
    private javax.swing.JLabel exceptionMemoria;
    private javax.swing.JLabel exceptionParticion;
    private javax.swing.JLabel exceptionTabla;
    private javax.swing.JLabel exceptionTabla2;
    private javax.swing.JLabel exceptionTmax;
    private javax.swing.JLabel exceptionTmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton mejorAjuste;
    private javax.swing.JTextField nPart;
    private javax.swing.JRadioButton primerAjuste;
    private javax.swing.JTable tablaParticion;
    private javax.swing.JTextField tmax;
    private javax.swing.JTextField tmin;
    private javax.swing.JTextField totalMem;
    // End of variables declaration//GEN-END:variables
}
