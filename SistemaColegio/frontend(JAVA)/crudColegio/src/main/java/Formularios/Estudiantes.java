/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Formularios;


import Modelos.ModeloEstudiantes;
import Service.ServiceAlumno;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

  
/**
 *
 * @author Admin
 */

public class Estudiantes extends javax.swing.JInternalFrame {
private void cargarAlumnosDesdeAPI() {
       try {
        // Llamar al servicio que consume la API
        ServiceAlumno serviceAlumno = new ServiceAlumno();
        List<ModeloEstudiantes> alumnos = serviceAlumno.obtenerAlumnos();

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Alumno");  // Esta se ocultará después
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Grado");
        model.addColumn("Gmail");
        model.addColumn("Teléfono");
        model.addColumn("Genero");

        // Agregar los datos al modelo
        for (ModeloEstudiantes alumno : alumnos) {
            model.addRow(new Object[]{
                alumno.getIdAlumno(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getGrado(),
                alumno.getGmail(),
                alumno.getTelefono(),
                alumno.getGenero()
            });
        }

        // Establecer el modelo después de agregar filas
        tblalumnos.setModel(model);

        // Ajustar ancho de columnas
        tblalumnos.getColumnModel().getColumn(1).setPreferredWidth(100); // Nombre
        tblalumnos.getColumnModel().getColumn(2).setPreferredWidth(100); // Apellido
        tblalumnos.getColumnModel().getColumn(3).setPreferredWidth(80);  // Grado
        tblalumnos.getColumnModel().getColumn(4).setPreferredWidth(150); // Gmail
        tblalumnos.getColumnModel().getColumn(5).setPreferredWidth(100); // Teléfono
        tblalumnos.getColumnModel().getColumn(6).setPreferredWidth(80);  // Género

        // Ocultar columna ID (la primera)
        tblalumnos.getColumnModel().getColumn(0).setMinWidth(0);
        tblalumnos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblalumnos.getColumnModel().getColumn(0).setWidth(0);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar los alumnos desde la API: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }

            
   /**
     * 
     */
    public Estudiantes() {
initComponents();

        // Ocultar el idCliente
        txtIdAlumnos.setVisible(false);

        cargarAlumnosDesdeAPI();

        ButtonGroup grupoGenero = new ButtonGroup();
        grupoGenero.add(rbmasculinoAlumnos);
        grupoGenero.add(rbfemeninoAlumnos);

        // Agregar ActionListener al botón Eliminar (¡Una sola vez en el constructor!)
        bteliminarAlumnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si hay una fila seleccionada en la tabla
                int rowSeleccionada = tblalumnos.getSelectedRow();

                // Si una fila está seleccionada, obtener el ID del alumno
                if (rowSeleccionada != -1) {
                    int idAlumno = (int) tblalumnos.getValueAt(rowSeleccionada, 0); // Obtener el ID Alumno de la columna 0

                    // Mostrar mensaje de confirmación
                    int confirmacion = JOptionPane.showConfirmDialog(
                            null,
                            "¿Estás seguro de que deseas eliminar este Alumno?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        try {
                            // Crear instancia para llamar el método del servicio
                            ServiceAlumno eliminar = new ServiceAlumno();
                            boolean exito = eliminar.eliminarAlumno(idAlumno);

                            // Llamar al servicio para eliminar el alumno
                            if (exito) {
                                JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente.");
                                cargarAlumnosDesdeAPI();  // Recargar la tabla con los datos actualizados
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo eliminar el alumno.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error al eliminar el alumno: " + ex.getMessage());
                        }
                    }
                } else {
                    // Si no se ha seleccionado ninguna fila
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un alumno para eliminar.");
                }
            }
        });
    }
 
    public JButton getbtnGuardarAlumno(){
        return btguardarAlumnos;
    }

    public JButton getbtnModificarAlumno(){
        return btnModificarAlumno;
    }

    public JButton getbtnEliminar(){
        return bteliminarAlumnos;
    }

    public JButton getbtlimpiarAlumnos() {
        return btlimpiarAlumnos;
    }

    public JTable getTblAlumno() {
        return tblalumnos;
    }

    public JTextField getTxtIdUsuario(){
        return txtIdAlumnos;
    }

    public JTextField getTxtNombre() {
        return txtnombreAlumno;
    }

    public JTextField getTxtApellido() {
        return txtapellidoAlumno;
    }

    public JTextField getTxtGrado() {
        return txtgradoAlumno;
    }

    public JTextField getTxtGmail() {
        return txtgmailAlumno;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtnombreAlumno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtapellidoAlumno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtgradoAlumno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btlimpiarAlumnos = new javax.swing.JButton();
        rbmasculinoAlumnos = new javax.swing.JRadioButton();
        rbfemeninoAlumnos = new javax.swing.JRadioButton();
        txtgmailAlumno = new javax.swing.JTextField();
        bteliminarAlumnos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblalumnos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btguardarAlumnos = new javax.swing.JButton();
        btnModificarAlumno = new javax.swing.JButton();
        txtIdAlumnos = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos estudiantes"));

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido");

        txtapellidoAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtapellidoAlumnoActionPerformed(evt);
            }
        });

        jLabel4.setText("Grado");

        jLabel5.setText("Gmail");

        jLabel6.setText("Telefono");

        jLabel7.setText("Genero");

        btlimpiarAlumnos.setText("Limpiar campo");
        btlimpiarAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btlimpiarAlumnosActionPerformed(evt);
            }
        });

        rbmasculinoAlumnos.setText("Masculino");

        rbfemeninoAlumnos.setText("Femenino");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btlimpiarAlumnos))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rbmasculinoAlumnos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbfemeninoAlumnos)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtnombreAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtapellidoAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtgradoAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtgmailAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombreAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtapellidoAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtgradoAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtgmailAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(rbmasculinoAlumnos)
                    .addComponent(rbfemeninoAlumnos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btlimpiarAlumnos)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        bteliminarAlumnos.setText("Eliminar");
        bteliminarAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteliminarAlumnosActionPerformed(evt);
            }
        });

        tblalumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Apellido", "Grado", "Gmail", "Telefono", "Genero"
            }
        ));
        jScrollPane1.setViewportView(tblalumnos);

        jLabel8.setText("Clic para seleccionar");

        btguardarAlumnos.setText("Guardar");
        btguardarAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btguardarAlumnosActionPerformed(evt);
            }
        });

        btnModificarAlumno.setText("Modificar");
        btnModificarAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarAlumnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btguardarAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bteliminarAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtIdAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtIdAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btguardarAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bteliminarAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnModificarAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtapellidoAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtapellidoAlumnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtapellidoAlumnoActionPerformed

    private void btguardarAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btguardarAlumnosActionPerformed
    ModeloEstudiantes estudiante = new ModeloEstudiantes();

        try {
            // Validar selección de género
            if (!rbmasculinoAlumnos.isSelected() && !rbfemeninoAlumnos.isSelected()) {
                JOptionPane.showMessageDialog(null, "Por favor selecciona el género del alumno");
                return; // Salir del método si no se seleccionó género
            }

            // Obtener el género
            String genero = rbmasculinoAlumnos.isSelected() ? "masculino" : "femenino";

            // Asignar datos al modelo
            estudiante.setIdAlumno(1); // Puedes ajustar según tu lógica
            estudiante.setNombre(txtnombreAlumno.getText());
            estudiante.setApellido(txtapellidoAlumno.getText());
            estudiante.setGrado(txtgradoAlumno.getText());
            estudiante.setGmail(txtgmailAlumno.getText());
            estudiante.setTelefono(txtTelefono.getText());
            estudiante.setGenero(genero);

            // Llamar al servicio para guardar
            ServiceAlumno servicio = new ServiceAlumno();
            boolean exito = servicio.agregarAlumno(estudiante);

            if (exito) {
                JOptionPane.showMessageDialog(null, "Alumno registrado con éxito");
                cargarAlumnosDesdeAPI(); // refresca la tabla
                LimpiarCampos(); // Limpiar campos luego de guardar
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar Alumno");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }//GEN-LAST:event_btguardarAlumnosActionPerformed




    private void bteliminarAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteliminarAlumnosActionPerformed
 int rowSeleccionada = tblalumnos.getSelectedRow();
    
    if (rowSeleccionada != -1) {
        int idAlumno = (int) tblalumnos.getValueAt(rowSeleccionada, 0);  // Obtener el ID Alumno
        
        int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Estás seguro de que deseas eliminar este Alumno?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                ServiceAlumno eliminar = new ServiceAlumno();
                boolean exito = eliminar.eliminarAlumno(idAlumno);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente.");
                    cargarAlumnosDesdeAPI();  // Recargar la tabla con los datos actualizados
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el alumno.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar el alumno: " + ex.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Por favor, selecciona un alumno para eliminar.");
    }
    }//GEN-LAST:event_bteliminarAlumnosActionPerformed

    private void btlimpiarAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btlimpiarAlumnosActionPerformed
        //limpiar campos del formulario
        
        LimpiarCampos();
     
    }//GEN-LAST:event_btlimpiarAlumnosActionPerformed

    private void btnModificarAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarAlumnoActionPerformed

    }//GEN-LAST:event_btnModificarAlumnoActionPerformed

public void LimpiarCampos (){
        txtnombreAlumno.setText("");
        txtapellidoAlumno.setText("");
        txtgmailAlumno.setText("");
        txtgmailAlumno.setText("");
        txtTelefono.setText("");
    rbmasculinoAlumnos.setSelected(false);
    rbfemeninoAlumnos.setSelected(false);
};
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bteliminarAlumnos;
    private javax.swing.JButton btguardarAlumnos;
    private javax.swing.JButton btlimpiarAlumnos;
    private javax.swing.JButton btnModificarAlumno;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbfemeninoAlumnos;
    private javax.swing.JRadioButton rbmasculinoAlumnos;
    private javax.swing.JTable tblalumnos;
    private javax.swing.JTextField txtIdAlumnos;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtapellidoAlumno;
    private javax.swing.JTextField txtgmailAlumno;
    private javax.swing.JTextField txtgradoAlumno;
    private javax.swing.JTextField txtnombreAlumno;
    // End of variables declaration//GEN-END:variables

    void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   }