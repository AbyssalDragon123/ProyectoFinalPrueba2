/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;


import Service.ServiceAula;
import Modelos.ModeloAula;
import static Modelos.SesionUsuario.token;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author Admin
 */
public class ViewAula extends javax.swing.JInternalFrame {
private ServiceAula serviceAula = new ServiceAula();
    /**
     * Creates new form ViewAula
     */
    public ViewAula() {
        
        initComponents();
        setPlaceholders();

        txtIdAula.setVisible(false);
        this.cargarAulasEnTabla();
                //seleccionar fila
        tblAula.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting() && tblAula.getSelectedRow() != -1) {
        int fila = tblAula.getSelectedRow();
        txtIdAula.setText(tblAula.getValueAt(fila, 0).toString());
        txtGrado.setText(tblAula.getValueAt(fila, 1).toString());
        txtSeccion.setText(tblAula.getValueAt(fila, 2).toString());
    }
});
    }
    public void ocultarEncabezado(JInternalFrame internalFrame) {
    BasicInternalFrameUI ui = (BasicInternalFrameUI) internalFrame.getUI();
    ui.setNorthPane(null);  // Esto elimina la barra de título (encabezado)
}
    private void setPlaceholders() {
        // grado
        txtGrado.setText("Ingrese el Grado");
        txtGrado.setHorizontalAlignment(JTextField.CENTER);
        txtGrado.setForeground(Color.decode("#F9F3E6"));
        txtGrado.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtGrado.getText().equals("Ingrese el Grado")) {
                    txtGrado.setText("");
                    txtGrado.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtGrado.getText().isEmpty()) {
                    txtGrado.setText("Ingrese el Grado");
                    txtGrado.setForeground(Color.decode("#A3C6BC"));
                }
            }
        });
// seccion
        txtSeccion.setText("Ingrese La Seccion");
        txtSeccion.setHorizontalAlignment(JTextField.CENTER);
        txtSeccion.setForeground(Color.decode("#A3C6BC"));
        txtSeccion.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtSeccion.getText().equals("Ingrese La Seccion")) {
                    txtSeccion.setText("");
                    txtSeccion.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtSeccion.getText().isEmpty()) {
                    txtSeccion.setText("Ingrese La Seccion");
                    txtSeccion.setForeground(Color.decode("#A3C6BC"));
                }
            }
        });
        
    }
private void cargarAulasEnTabla() {
    try {
        List<ModeloAula> listaAula = serviceAula.getAulas(token);

        String[] columnas = {"ID", "Grado", "Sección"};
        Object[][] datos = new Object[listaAula.size()][columnas.length];

        for (int i = 0; i < listaAula.size(); i++) {
            ModeloAula aula = listaAula.get(i);
            datos[i][0] = aula.getIdAula();
            datos[i][1] = aula.getGrado();
            datos[i][2] = aula.getSeccion();
        }

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAula.setModel(model);
//87, 142, 126
        // Ocultar columna ID
        tblAula.getColumnModel().getColumn(0).setMinWidth(0);
        tblAula.getColumnModel().getColumn(0).setMaxWidth(0);
        tblAula.getColumnModel().getColumn(0).setWidth(0);

        // Cambiar color de fondo y texto del encabezado
DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
headerRenderer.setBackground(new Color(56,91,81)); // Verde oscuro
headerRenderer.setForeground(new Color(245, 236, 213)); // Texto claro
headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
headerRenderer.setFont(tblAula.getTableHeader().getFont().deriveFont(Font.BOLD));
tblAula.setRowHeight(40);  // 40 píxeles de alto por fila
tblAula.getColumnModel().getColumn(1).setPreferredWidth(150);  // Columna "Grado"
tblAula.getColumnModel().getColumn(2).setPreferredWidth(150);  // Columna "Sección"
for (int i = 0; i < tblAula.getColumnModel().getColumnCount(); i++) {
    tblAula.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
}

        // Cambiar fondo del viewport del JScrollPane que contiene la tabla
        jScrollPane1.getViewport().setBackground(new Color(87, 142, 126));

        // Renderer personalizado para celdas: fondo verde y texto centrado
        DefaultTableCellRenderer greenCenterRenderer = new DefaultTableCellRenderer() {
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

        tblAula.getColumnModel().getColumn(1).setCellRenderer(greenCenterRenderer);
        tblAula.getColumnModel().getColumn(2).setCellRenderer(greenCenterRenderer);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al cargar aulas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}private void limpiarCampos() {
    txtIdAula.setText("");
    txtGrado.setText("");
    txtSeccion.setText("");
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
        txtGrado = new javax.swing.JTextField();
        txtIdAula = new javax.swing.JTextField();
        txtSeccion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAula = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnRegistrar4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 236, 213));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(796, 661));
        setMinimumSize(new java.awt.Dimension(796, 661));
        setPreferredSize(new java.awt.Dimension(944, 616));

        jPanel1.setBackground(new java.awt.Color(61, 61, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos de Aula", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(245, 236, 213));

        txtGrado.setBackground(new java.awt.Color(87, 142, 126));
        txtGrado.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtGrado.setForeground(new java.awt.Color(245, 236, 213));
        txtGrado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtIdAula.setBackground(new java.awt.Color(87, 142, 126));
        txtIdAula.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtIdAula.setForeground(new java.awt.Color(255, 255, 255));
        txtIdAula.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtSeccion.setBackground(new java.awt.Color(87, 142, 126));
        txtSeccion.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtSeccion.setForeground(new java.awt.Color(245, 236, 213));
        txtSeccion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(245, 236, 213));
        jLabel1.setText("Grado:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(245, 236, 213));
        jLabel2.setText("Sección:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(422, Short.MAX_VALUE)
                .addComponent(txtIdAula, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtGrado)
                        .addComponent(txtSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtIdAula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(txtSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(61, 61, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Tabla de Aula", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(245, 236, 213));

        tblAula.setBackground(new java.awt.Color(87, 142, 126));
        tblAula.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblAula.setForeground(new java.awt.Color(245, 236, 213));
        tblAula.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblAula.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tblAula);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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

        btnRegistrar4.setBackground(new java.awt.Color(87, 142, 126));
        btnRegistrar4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnRegistrar4.setForeground(new java.awt.Color(245, 236, 213));
        btnRegistrar4.setText("Registrarse");
        btnRegistrar4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegistrar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrar4ActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 46, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar4ActionPerformed
  // Validar campos vacíos
String grado = txtGrado.getText().trim();
String seccion = txtSeccion.getText().trim();

if (grado.isEmpty() || seccion.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
    return;
}

// Crear el modelo de aula
ModeloAula nuevaAula = new ModeloAula();
nuevaAula.setGrado(grado);
nuevaAula.setSeccion(seccion);

try {
    ModeloAula aulaCreada = serviceAula.crearAula(nuevaAula, token);
    if (aulaCreada != null && aulaCreada.getIdAula() > 0) {
        JOptionPane.showMessageDialog(this, "Aula registrada exitosamente.", 
                                     "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
        // Limpiar campos
        txtGrado.setText("");
        txtSeccion.setText("");
        // Recargar la tabla
        cargarAulasEnTabla();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo registrar el aula.", "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Error al registrar aula: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
    }//GEN-LAST:event_btnRegistrar4ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       // Validar que haya un aula seleccionada
if (txtIdAula.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Seleccione un aula de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
    return;
}

// Validar campos
String grado = txtGrado.getText().trim();
String seccion = txtSeccion.getText().trim();

if (grado.isEmpty() || seccion.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
    return;
}

try {
    ModeloAula aulaEditada = new ModeloAula();
    aulaEditada.setIdAula(Integer.parseInt(txtIdAula.getText()));
    aulaEditada.setGrado(grado);
    aulaEditada.setSeccion(seccion);

    boolean actualizado = serviceAula.actualizarAula(aulaEditada, token);
    if (actualizado) {
        JOptionPane.showMessageDialog(this, "Aula actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        // Limpiar campos
        txtIdAula.setText("");
        txtGrado.setText("");
        txtSeccion.setText("");
        // Recargar la tabla
        cargarAulasEnTabla();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo actualizar el aula.", "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Error al actualizar aula: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       // Validar que haya un aula seleccionada
if (txtIdAula.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Seleccione un aula de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
    return;
}

int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta aula?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
if (confirm != JOptionPane.YES_OPTION) {
    return;
}

try {
    int idAula = Integer.parseInt(txtIdAula.getText());
    boolean eliminado = serviceAula.eliminarAula(idAula, token);
    if (eliminado) {
        JOptionPane.showMessageDialog(this, "Aula eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        // Limpiar campos
        txtIdAula.setText("");
        txtGrado.setText("");
        txtSeccion.setText("");
        // Recargar la tabla
        cargarAulasEnTabla();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo eliminar el aula.", "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Error al eliminar aula: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos() ;
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAula;
    private javax.swing.JTextField txtGrado;
    private javax.swing.JTextField txtIdAula;
    private javax.swing.JTextField txtSeccion;
    // End of variables declaration//GEN-END:variables
}
