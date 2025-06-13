 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import ComboItem.ComboItem;
import Modelos.ModeloAsignatura;
import Modelos.ModeloDocente;
import Service.ServiceAsignatura;
import Service.ServiceDocente;
import ComboItem.ComboItemDocente;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Asignatura extends javax.swing.JInternalFrame {

    private final ServiceAsignatura service = new ServiceAsignatura();
    private final ServiceDocente serviceDocente = new ServiceDocente();

    public Asignatura(String token) {
        initComponents();
        setPlaceholders() ;
        txtIdAsignatura.setVisible(false);
        cargarDocentesEnCombo();
        cargarAsignaturasEnTabla();
        agregarSeleccionTablaListener();
    }

 

    private void cargarDocentesEnCombo() {
        try {
            List<ModeloDocente> docentes = serviceDocente.getDocentes();
            ComboItem.removeAllItems();
            for (ModeloDocente d : docentes) {
                String label = d.getIdDocente() + " - " + d.getNombreDocente() + " " + d.getApellidoDocente();
                ComboItem.addItem(new ComboItemDocente(d.getIdDocente(), label));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar docentes: " + ex.getMessage());
        }
    }

    private int obtenerIdDocenteSeleccionado() {
        ComboItemDocente seleccionado = (ComboItemDocente) ComboItem.getSelectedItem();
        if (seleccionado != null) {
            return seleccionado.getId();
        }
        return -1;
    }

    private void seleccionarDocenteEnCombo(int idDocente) {
        for (int i = 0; i < ComboItem.getItemCount(); i++) {
            ComboItemDocente item = ComboItem.getItemAt(i);
            if (item.getId() == idDocente) {
                ComboItem.setSelectedIndex(i);
                return;
            }
        }
        ComboItem.setSelectedIndex(-1);
    }

    private void cargarAsignaturasEnTabla() {
        try {
            List<ModeloAsignatura> lista = service.obtenerAsignatura();
            if (lista == null) {
                JOptionPane.showMessageDialog(this, "No se pudo obtener la lista de asignaturas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        // Cambié el nombre de la columna "Docente" a "ID Docente" para reflejar el cambio
        String[] columnas = {"ID", "Nombre Asignatura", "Descripción", "ID Docente"};
        Object[][] datos = new Object[lista.size()][columnas.length];

            for (int i = 0; i < lista.size(); i++) {
                ModeloAsignatura a = lista.get(i);
                datos[i][0] = a.getIdAsignatura();
                datos[i][1] = a.getNombreAsignatura();
                datos[i][2] = a.getDescripcionAsignatura();
                datos[i][3] = a.getFkIdDocente(); // ID docente oculto
            }

            DefaultTableModel model = new DefaultTableModel(datos, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tblAsignaturas.setModel(model);

            // Ocultar columnas ID Asignatura y ID Docente
            tblAsignaturas.getColumnModel().getColumn(0).setMinWidth(0);
            tblAsignaturas.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAsignaturas.getColumnModel().getColumn(0).setWidth(0);

            // Estilo encabezado
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(new Color(56, 91, 81));
            headerRenderer.setForeground(new Color(245, 236, 213));
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            headerRenderer.setFont(tblAsignaturas.getTableHeader().getFont().deriveFont(Font.BOLD));
            for (int i = 0; i < tblAsignaturas.getColumnModel().getColumnCount(); i++) {
                tblAsignaturas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }

            tblAsignaturas.setRowHeight(40);
            jScrollPane1.getViewport().setBackground(new Color(87, 142, 126));

            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setBackground(new Color(87, 142, 126));
                    setForeground(new Color(245, 236, 213));
                    setHorizontalAlignment(SwingConstants.CENTER);
                    return this;
                }
            };

            for (int i = 1; i < tblAsignaturas.getColumnCount(); i++) {
                tblAsignaturas.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarSeleccionTablaListener() {
tblAsignaturas.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && tblAsignaturas.getSelectedRow() != -1) {
            int fila = tblAsignaturas.getSelectedRow();
            String id = tblAsignaturas.getValueAt(fila, 0).toString();
            System.out.println("Fila seleccionada ID: " + id);  // Para depurar
            txtIdAsignatura.setText(id);
            txtnombre_asignatura.setText(tblAsignaturas.getValueAt(fila, 1).toString());
            txtdescripcion_asignatura.setText(tblAsignaturas.getValueAt(fila, 2).toString());

            int idDocente = Integer.parseInt(tblAsignaturas.getValueAt(fila, 3).toString());
            seleccionarDocenteEnCombo(idDocente);
        }
    });
    }

   

    private void limpiarCampos() {
        txtIdAsignatura.setText("");
        txtnombre_asignatura.setText("");
        txtdescripcion_asignatura.setText("");
        ComboItem.setSelectedIndex(-1);
    }

    private void setPlaceholders() {
        // Nombre
        txtnombre_asignatura.setText("Ingrese la Asignatura");
        txtnombre_asignatura.setHorizontalAlignment(JTextField.CENTER);
        txtnombre_asignatura.setForeground(Color.decode("#A3C6BC"));
        txtnombre_asignatura.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtnombre_asignatura.getText().equals("Ingrese la Asignatura")) {
                    txtnombre_asignatura.setText("");
                    txtnombre_asignatura.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtnombre_asignatura.getText().isEmpty()) {
                    txtnombre_asignatura.setText("Ingrese la Asignatura");
                    txtnombre_asignatura.setForeground(Color.decode("#A3C6BC"));
                }
            }
        });   
        // APELLIDO
        txtdescripcion_asignatura.setText("Ingrese la Descripcion");
        txtdescripcion_asignatura.setHorizontalAlignment(JTextField.CENTER);
        txtdescripcion_asignatura.setForeground(Color.decode("#A3C6BC"));
        txtdescripcion_asignatura.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtdescripcion_asignatura.getText().equals("Ingrese la Descripcion")) {
                    txtdescripcion_asignatura.setText("");
                    txtdescripcion_asignatura.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtdescripcion_asignatura.getText().isEmpty()) {
                    txtdescripcion_asignatura.setText("Ingrese la Descripcion");
                    txtdescripcion_asignatura.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtnombre_asignatura = new javax.swing.JTextField();
        txtIdAsignatura = new javax.swing.JTextField();
        txtdescripcion_asignatura = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ComboItem = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAsignaturas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 236, 213));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(61, 61, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos de Asignatura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(245, 236, 213));

        txtnombre_asignatura.setBackground(new java.awt.Color(87, 142, 126));
        txtnombre_asignatura.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtnombre_asignatura.setForeground(new java.awt.Color(245, 236, 213));
        txtnombre_asignatura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtIdAsignatura.setBackground(new java.awt.Color(87, 142, 126));
        txtIdAsignatura.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtIdAsignatura.setForeground(new java.awt.Color(255, 255, 255));
        txtIdAsignatura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtdescripcion_asignatura.setBackground(new java.awt.Color(87, 142, 126));
        txtdescripcion_asignatura.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtdescripcion_asignatura.setForeground(new java.awt.Color(245, 236, 213));
        txtdescripcion_asignatura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(245, 236, 213));
        jLabel3.setText("Asignatura:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(245, 236, 213));
        jLabel4.setText("Descripción:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(245, 236, 213));
        jLabel5.setText("Docente:");

        ComboItem.setBackground(new java.awt.Color(87, 142, 126));
        ComboItem.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ComboItem.setForeground(new java.awt.Color(245, 236, 213));
        ComboItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtnombre_asignatura, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtdescripcion_asignatura)))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addComponent(txtIdAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ComboItem, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ComboItem, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(txtnombre_asignatura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(txtdescripcion_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(61, 61, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Tabla de Asignatura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(245, 236, 213));

        tblAsignaturas.setBackground(new java.awt.Color(87, 142, 126));
        tblAsignaturas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblAsignaturas.setForeground(new java.awt.Color(245, 236, 213));
        tblAsignaturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblAsignaturas.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tblAsignaturas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(61, 61, 61));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(245, 236, 213));

        btnEliminar.setBackground(new java.awt.Color(87, 142, 126));
        btnEliminar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(245, 236, 213));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(87, 142, 126));
        btnLimpiar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(245, 236, 213));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnAgregar.setBackground(new java.awt.Color(87, 142, 126));
        btnAgregar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(245, 236, 213));
        btnAgregar.setText("Registrarse");
        btnAgregar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(87, 142, 126));
        btnEditar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(245, 236, 213));
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
if (txtIdAsignatura.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione una asignatura de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta asignatura?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        int id = Integer.parseInt(txtIdAsignatura.getText());
        boolean exito = service.eliminarAsignatura(id);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Asignatura eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarAsignaturasEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "ID de asignatura inválido.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar asignatura: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
try {
        String nombre = txtnombre_asignatura.getText().trim();
        String descripcion = txtdescripcion_asignatura.getText().trim();
        int idDocente = obtenerIdDocenteSeleccionado();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la asignatura es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (idDocente == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un docente.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ModeloAsignatura nueva = new ModeloAsignatura();
        nueva.setNombreAsignatura(nombre);
        nueva.setDescripcionAsignatura(descripcion);
        nueva.setFkIdDocente(idDocente);

        boolean exito = service.agregarAsignatura(nueva);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Asignatura agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarAsignaturasEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al agregar asignatura: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
// TODO add your handling code here:
       if (txtIdAsignatura.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione una asignatura de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int idAsignatura = Integer.parseInt(txtIdAsignatura.getText());
        ComboItemDocente docenteSeleccionado = (ComboItemDocente) ComboItem.getSelectedItem();

        if (docenteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un docente.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ModeloAsignatura asignaturaEditada = new ModeloAsignatura();
        asignaturaEditada.setIdAsignatura(idAsignatura);
        asignaturaEditada.setNombreAsignatura(txtnombre_asignatura.getText().trim());
        asignaturaEditada.setDescripcionAsignatura(txtdescripcion_asignatura.getText().trim());
        asignaturaEditada.setFkIdDocente(docenteSeleccionado.getId());

        boolean actualizado = service.actualizarAsignatura(asignaturaEditada);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Asignatura actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarAsignaturasEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar asignatura: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<ComboItemDocente> ComboItem;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAsignaturas;
    private javax.swing.JTextField txtIdAsignatura;
    private javax.swing.JTextField txtdescripcion_asignatura;
    private javax.swing.JTextField txtnombre_asignatura;
    // End of variables declaration//GEN-END:variables
void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}