
package simuladordememoria;

import javax.swing.JOptionPane;

/**
 * Clase que crea el dialogo en el cual se ingresaran los proceso
 * Tendra dos cajas de texto: Nombre de Proceso y Cantidad de Memoria
 * @author LENOVO
 */
public class Dialogo extends javax.swing.JDialog {


    private int particionMayor;

    public Dialogo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    /**
     * Constructor que permite obtener la particion de mayor valor obtenida en Ventana2
     * @param particionMayor la particion de mayor valor
     */
    public Dialogo(java.awt.Frame parent, boolean modal, int particionMayor) {
        super(parent, modal);
        initComponents();
        this.particionMayor = particionMayor;
        System.out.println("Particion mayor dialogo: " + this.particionMayor);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lbAyuda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Cantidad de Memoria");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lbAyuda.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAyuda)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAceptar)
                            .addGap(18, 18, 18)
                            .addComponent(btnCancelar))
                        .addComponent(txtCantidad)
                        .addComponent(txtNombre)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAyuda)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
/**
 * Valida que la caja de texto nombre  no este vacia
 * @throws Exception si la caja de texto esta en blanco
 */
    private void validarNombre() throws Exception{
        try {
            String nombre;
            if (txtNombre.getText().trim().length() != 0 && txtCantidad.getText() != null) {
                nombre = txtNombre.getText();
            }else{
                JOptionPane.showMessageDialog(this, "Hay valores en blanco", "NOOOO", HEIGHT);
                throw new Exception();
            }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Hubo un error", "Excepcion Desconocida", HEIGHT);
            throw e;
        }

    }

    /**
     * Valida si el texto ingresado en cantidad es un entero y si no es mayor que la particion mas grande
     * @throws NumberFormatException si el numero ingresado es distinto de entero
     * @throws Exception Si la caja de texto esta en blanco o si el valor ingresado es mayor que la particion mas grande
     */
    private void validarCantidad() throws NumberFormatException, Exception{
        try {
            int cantidad;
            if (txtCantidad.getText().trim().length() != 0 && txtCantidad.getText() != null) {
                cantidad = Integer.parseInt(txtCantidad.getText());
            } else {
                JOptionPane.showMessageDialog(this, "Hay valores en blanco", "NOOOO", HEIGHT);
                throw new Exception();
            }

            if (cantidad > this.particionMayor) {
                JOptionPane.showMessageDialog(this, "El valor supera a la particion mas grande", "NOOOO", HEIGHT);
                throw new Exception();
            }
            if (cantidad < 1) {
                JOptionPane.showMessageDialog(this, "El valor debe ser mayor a 1", "NOOOO", HEIGHT);
                throw new Exception();
                
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El valor debe ser un numero entero", "NOOOO", HEIGHT);
            throw e;
            
        }
    }


    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        
        this.setRootPane(null);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
        try{
            validarNombre(); //Se hacen las validaciones de las cajas de texto.
            validarCantidad();
            // Si la ejecucion llega a este punto quiere decir que todo esta validado y el dialogo se cierra.
            this.setVisible(false);
        }catch(NumberFormatException ne){
            
        }catch(Exception e){
            
        }
        
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        lbAyuda.setText("Maximo Permitido: "+this.particionMayor);
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(Dialogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dialogo dialog = new Dialogo(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbAyuda;
    public javax.swing.JTextField txtCantidad;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
