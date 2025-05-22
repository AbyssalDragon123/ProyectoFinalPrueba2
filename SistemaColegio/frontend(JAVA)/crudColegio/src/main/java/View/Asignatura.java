 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import Modelos.ModeloAsignatura;
import Service.ServiceAsignatura;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Admin
 */
public class Asignatura extends javax.swing.JInternalFrame {


    public Asignatura() {
        initComponents();

        // Inicializar referencias a componentes
        this.tblAsignaturas = tblAsignaturas;
        this.txtIdDocente = txtIdDocente;
        this.txtnombre_asignatura = txtnombre_asignatura;
        this.txtdescripcion_asignatura = txtdescripcion_asignatura;
        this.btnAgregar = btnAgregar;
        this.btnEditar = btnEditar;
        this.btnEliminar = btnEliminar;
        this.btnLimpiar = btnLimpiar;

        // Ocultar el campo ID en el formulario
        txtIdDocente.setVisible(false);

        // Ocultar la columna ID en la tabla
        ocultarColumnaID();

        cargarAsignaturaDesdeAPI();

        // Acción para el botón Agregar
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAsignatura();
            }
        });

        // Acción para el botón Editar
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarAsignatura();
            }
        });

        // Acción para el botón Eliminar
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarAsignatura();
            }
        });

        // Acción para el botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Acción para seleccionar una asignatura de la tabla
        tblAsignaturas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int filaSeleccionada = tblAsignaturas.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idAsignatura = (int) tblAsignaturas.getValueAt(filaSeleccionada, 0);
                    String nombre = (String) tblAsignaturas.getValueAt(filaSeleccionada, 1);
                    String descripcion = (String) tblAsignaturas.getValueAt(filaSeleccionada, 2);

                    txtIdDocente.setText(String.valueOf(idAsignatura));
                    txtnombre_asignatura.setText(nombre);
                    txtdescripcion_asignatura.setText(descripcion);
                }
            }
        });
    }

    // Método para ocultar la columna ID en la tabla
    private void ocultarColumnaID() {
        TableColumn columnaID = tblAsignaturas.getColumnModel().getColumn(0);
        columnaID.setMinWidth(0);
        columnaID.setMaxWidth(0);
        columnaID.setPreferredWidth(0);
    }

    // Método para cargar las asignaturas desde la API
    private void cargarAsignaturaDesdeAPI() {
        try {
            ServiceAsignatura service = new ServiceAsignatura();
            List<ModeloAsignatura> asignaturas = service.obtenerAsignatura();

            // Limpia los datos previos de la tabla
            DefaultTableModel modelo = (DefaultTableModel) tblAsignaturas.getModel();
            modelo.setRowCount(0); // Elimina todas las filas

            // Agrega los datos de las asignaturas a la tabla (solo ID, Nombre, Descripción)
            for (ModeloAsignatura asignatura : asignaturas) {
                Object[] fila = {
                    asignatura.getIdAsignatura(),
                    asignatura.getNombreAsignatura(),
                    asignatura.getDescripcionAsignatura()
                };
                modelo.addRow(fila);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarAsignatura() {
    String nombre = txtnombre_asignatura.getText().trim();
    String descripcion = txtdescripcion_asignatura.getText().trim();

    if (nombre.isEmpty() || descripcion.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        ModeloAsignatura asignatura = new ModeloAsignatura();
        asignatura.setNombreAsignatura(nombre);
        asignatura.setDescripcionAsignatura(descripcion);
        asignatura.setFkIdDocente(1); // Usa el id real del docente

        ServiceAsignatura service = new ServiceAsignatura();
        boolean resultado = service.agregarAsignatura(asignatura);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Asignatura agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarAsignaturaDesdeAPI();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar la asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al agregar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void editarAsignatura() {
    int filaSeleccionada = tblAsignaturas.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor seleccione una asignatura.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String idTexto = txtIdDocente.getText().trim();
    if (idTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se ha seleccionado una asignatura válida.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int idAsignatura;
    try {
        idAsignatura = Integer.parseInt(idTexto);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID de asignatura inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String nombre = txtnombre_asignatura.getText().trim();
    String descripcion = txtdescripcion_asignatura.getText().trim();

    if (nombre.isEmpty() || descripcion.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        ModeloAsignatura asignatura = new ModeloAsignatura();
        asignatura.setIdAsignatura(idAsignatura);
        asignatura.setNombreAsignatura(nombre);
        asignatura.setDescripcionAsignatura(descripcion);
        asignatura.setFkIdDocente(1); // Cambia esto si tienes lógica para el docente

        ServiceAsignatura service = new ServiceAsignatura();
        boolean resultado = service.actualizarAsignatura(asignatura);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Asignatura editada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarAsignaturaDesdeAPI();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al editar la asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al editar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    // Método para eliminar una asignatura
    private void eliminarAsignatura() {
        int filaSeleccionada = tblAsignaturas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una asignatura.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idAsignatura = Integer.parseInt(txtIdDocente.getText());

        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar esta asignatura?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            ServiceAsignatura service = new ServiceAsignatura();
            boolean resultado = service.eliminarAsignatura(idAsignatura);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Asignatura eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarAsignaturaDesdeAPI(); // Recargar los datos de la tabla
                limpiarCampos(); // Limpiar los campos
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        txtIdDocente.setText("");
        txtnombre_asignatura.setText("");
        txtdescripcion_asignatura.setText("");
        tblAsignaturas.clearSelection();
  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnombre_asignatura = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtdescripcion_asignatura = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        txtIdDocente = new javax.swing.JTextField();
        txtid_asignatura1 = new javax.swing.JTextField();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAsignaturas = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 251, 222));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        kGradientPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Datos de Asignatura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        kGradientPanel1.setkEndColor(new java.awt.Color(163, 209, 198));
        kGradientPanel1.setkStartColor(new java.awt.Color(179, 216, 168));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("Asignatura:");

        txtnombre_asignatura.setBackground(new java.awt.Color(255, 251, 222));
        txtnombre_asignatura.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtnombre_asignatura.setBorder(null);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setText("Descripcion:");

        txtdescripcion_asignatura.setBackground(new java.awt.Color(255, 251, 222));
        txtdescripcion_asignatura.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtdescripcion_asignatura.setBorder(null);

        btnLimpiar.setBackground(new java.awt.Color(163, 209, 198));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/clean.png"))); // NOI18N
        btnLimpiar.setText("Limpiar Campos");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        txtIdDocente.setBackground(new java.awt.Color(255, 251, 222));
        txtIdDocente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdDocente.setBorder(null);

        txtid_asignatura1.setBackground(new java.awt.Color(255, 251, 222));
        txtid_asignatura1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtid_asignatura1.setBorder(null);

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnombre_asignatura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdescripcion_asignatura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(txtIdDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(kGradientPanel1Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(txtid_asignatura1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(399, Short.MAX_VALUE)))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txtnombre_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdescripcion_asignatura))
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                    .addContainerGap(125, Short.MAX_VALUE)
                    .addComponent(txtid_asignatura1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)))
        );

        kGradientPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        kGradientPanel2.setkEndColor(new java.awt.Color(163, 209, 198));
        kGradientPanel2.setkStartColor(new java.awt.Color(179, 216, 168));

        btnAgregar.setBackground(new java.awt.Color(163, 209, 198));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregar.setText("Registrar");
        btnAgregar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnEditar.setBackground(new java.awt.Color(163, 209, 198));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnEliminar.setBackground(new java.awt.Color(163, 209, 198));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        kGradientPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tabla de Asignaturas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        kGradientPanel3.setkEndColor(new java.awt.Color(163, 209, 198));
        kGradientPanel3.setkStartColor(new java.awt.Color(179, 216, 168));

        tblAsignaturas.setBackground(new java.awt.Color(255, 251, 222));
        tblAsignaturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Asignatura", "Descripcion"
            }
        ));
        jScrollPane1.setViewportView(tblAsignaturas);

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(30, 30, 30))
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        txtnombre_asignatura.setText("");
        txtdescripcion_asignatura.setText("");
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private javax.swing.JTable tblAsignaturas;
    private javax.swing.JTextField txtIdDocente;
    private javax.swing.JTextField txtdescripcion_asignatura;
    private javax.swing.JTextField txtid_asignatura1;
    private javax.swing.JTextField txtnombre_asignatura;
    // End of variables declaration//GEN-END:variables
void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}