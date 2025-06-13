/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;
import Modelos.ModeloDocente;
import javax.swing.JOptionPane;
import Service.ServiceDocente;
import Modelos.ModeloAula;
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
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class ViewDocente extends javax.swing.JInternalFrame {
private final ServiceDocente serviceDocente = new ServiceDocente();
    /**
     * Creates new form ViewDocente
     */
public ViewDocente() {
        initComponents();
        setPlaceholders();
        cargarDocenteEnTabla();
        txtIdDocente.setVisible(false);
                //seleccionar fila
        tblDocente.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting() && tblDocente.getSelectedRow() != -1) {
        int fila = tblDocente.getSelectedRow();
        txtIdDocente.setText(tblDocente.getValueAt(fila, 0).toString());
        txtNombre.setText(tblDocente.getValueAt(fila, 1).toString());
        txtApellido.setText(tblDocente.getValueAt(fila, 2).toString());
        txtCorreo.setText(tblDocente.getValueAt(fila, 3).toString());
        txtTelefono.setText(tblDocente.getValueAt(fila, 4).toString());
        txtEspecialidad.setText(tblDocente.getValueAt(fila, 5).toString());
    }
});
    }
    
 
 private void limpiarCampos() {
    txtIdDocente.setText("");
    txtNombre.setText("");
    txtApellido.setText("");
    txtCorreo.setText("");
    txtTelefono.setText("");
    txtEspecialidad.setText("");
}
private boolean esCorreoValido(String correo) {
    String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    return correo.matches(regex);
}
private boolean esTelefonoValido(String telefono) {
    return telefono.matches("\\d+"); // Solo dígitos
}
private void cargarDocenteEnTabla() {
    try {
        List<ModeloDocente> listaDocentes = serviceDocente.getDocentes();

        String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Telefono", "Especialidad"};
        Object[][] datos = new Object[listaDocentes.size()][columnas.length];

        for (int i = 0; i < listaDocentes.size(); i++) {
            ModeloDocente docente = listaDocentes.get(i);
            datos[i][0] = docente.getIdDocente();
            datos[i][1] = docente.getNombreDocente();
            datos[i][2] = docente.getApellidoDocente();
            datos[i][3] = docente.getCorreoDocente();
            datos[i][4] = docente.getTelefonoDocente();
            datos[i][5] = docente.getEspecialidadDocente();
        }

        DefaultTableModel model = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDocente.setModel(model);

        // Ocultar columna ID
        tblDocente.getColumnModel().getColumn(0).setMinWidth(0);
        tblDocente.getColumnModel().getColumn(0).setMaxWidth(0);
        tblDocente.getColumnModel().getColumn(0).setWidth(0);

        // Estilo encabezado
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(56, 91, 81)); // Verde oscuro
        headerRenderer.setForeground(new Color(245, 236, 213)); // Texto claro
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setFont(tblDocente.getTableHeader().getFont().deriveFont(Font.BOLD));
        for (int i = 0; i < tblDocente.getColumnModel().getColumnCount(); i++) {
            tblDocente.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        tblDocente.setRowHeight(40);
        for (int i = 1; i < columnas.length; i++) {
            tblDocente.getColumnModel().getColumn(i).setPreferredWidth(150);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al cargar docentes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void setPlaceholders() {
        // Nombre
        txtNombre.setText("Ingrese el Nombre");
        txtNombre.setHorizontalAlignment(JTextField.CENTER);
        txtNombre.setForeground(Color.decode("#A3C6BC"));
        txtNombre.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtNombre.getText().equals("Ingrese el Nombre")) {
                    txtNombre.setText("");
                    txtNombre.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtNombre.getText().isEmpty()) {
                    txtNombre.setText("Ingrese el Nombre");
                    txtNombre.setForeground(Color.decode("#A3C6BC"));
                }
            }
        });   
        // APELLIDO
        txtApellido.setText("Ingrese el Apellido");
        txtApellido.setHorizontalAlignment(JTextField.CENTER);
        txtApellido.setForeground(Color.decode("#A3C6BC"));
        txtApellido.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtApellido.getText().equals("Ingrese el Apellido")) {
                    txtApellido.setText("");
                    txtApellido.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtApellido.getText().isEmpty()) {
                    txtApellido.setText("Ingrese el Apellido");
                    txtApellido.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
         // correo
        txtCorreo.setText("Example@gmail.com");
        txtCorreo.setHorizontalAlignment(JTextField.CENTER);
        txtCorreo.setForeground(Color.decode("#A3C6BC"));
        txtCorreo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtCorreo.getText().equals("Example@gmail.com")) {
                    txtCorreo.setText("");
                    txtCorreo.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtCorreo.getText().isEmpty()) {
                    txtCorreo.setText("Example@gmail.com");
                    txtCorreo.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
        // telefono
        txtTelefono.setText("Ingrese Numero de Telefono");
        txtTelefono.setHorizontalAlignment(JTextField.CENTER);
        txtTelefono.setForeground(Color.decode("#A3C6BC"));
        txtTelefono.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtTelefono.getText().equals("Ingrese Numero de Telefono")) {
                    txtTelefono.setText("");
                    txtTelefono.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtTelefono.getText().isEmpty()) {
                    txtTelefono.setText("Ingrese Numero de Telefono");
                    txtTelefono.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
        // especialidad
        txtEspecialidad.setText("Ingrese la Especialidad");
        txtEspecialidad.setHorizontalAlignment(JTextField.CENTER);
        txtEspecialidad.setForeground(Color.decode("#A3C6BC"));
        txtEspecialidad.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtEspecialidad.getText().equals("Ingrese la Especialidad")) {
                    txtEspecialidad.setText("");
                    txtEspecialidad.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtEspecialidad.getText().isEmpty()) {
                    txtEspecialidad.setText("Ingrese la Especialidad");
                    txtEspecialidad.setForeground(Color.decode("#A3C6BC"));
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
        txtNombre = new javax.swing.JTextField();
        txtIdDocente = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnEDITAR = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocente = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 236, 213));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(61, 61, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos de Docentes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 229, 163));

        txtNombre.setBackground(new java.awt.Color(87, 142, 126));
        txtNombre.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(245, 236, 213));
        txtNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtIdDocente.setBackground(new java.awt.Color(87, 142, 126));
        txtIdDocente.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtIdDocente.setForeground(new java.awt.Color(255, 255, 255));
        txtIdDocente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTelefono.setBackground(new java.awt.Color(87, 142, 126));
        txtTelefono.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(245, 236, 213));
        txtTelefono.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(247, 228, 180));
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(247, 228, 180));
        jLabel2.setText("Telefono:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(247, 228, 180));
        jLabel3.setText("Apellido:");

        txtApellido.setBackground(new java.awt.Color(87, 142, 126));
        txtApellido.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(245, 236, 213));
        txtApellido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(247, 228, 180));
        jLabel4.setText("Especialidad:");

        txtEspecialidad.setBackground(new java.awt.Color(87, 142, 126));
        txtEspecialidad.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtEspecialidad.setForeground(new java.awt.Color(245, 236, 213));
        txtEspecialidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(247, 228, 180));
        jLabel5.setText("Correo:");

        txtCorreo.setBackground(new java.awt.Color(87, 142, 126));
        txtCorreo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(245, 236, 213));
        txtCorreo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        btnRegistrar.setBackground(new java.awt.Color(87, 142, 126));
        btnRegistrar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(245, 236, 213));
        btnRegistrar.setText("Registrarse");
        btnRegistrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnEDITAR.setBackground(new java.awt.Color(87, 142, 126));
        btnEDITAR.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEDITAR.setForeground(new java.awt.Color(245, 236, 213));
        btnEDITAR.setText("Editar");
        btnEDITAR.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEDITAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEDITARActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(txtTelefono))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtIdDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 120, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEDITAR, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtIdDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEDITAR, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(61, 61, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Tabla de Docentes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(245, 236, 213));

        tblDocente.setBackground(new java.awt.Color(87, 142, 126));
        tblDocente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblDocente.setForeground(new java.awt.Color(245, 236, 213));
        tblDocente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblDocente.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tblDocente);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      if (txtIdDocente.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un docente de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este docente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        int idDocente = Integer.parseInt(txtIdDocente.getText());
        boolean eliminado = serviceDocente.eliminarDocente(idDocente);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Docente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDocenteEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el docente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar docente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
          // Validar campos vacíos
    String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    String correo = txtCorreo.getText().trim();
    String telefono = txtTelefono.getText().trim();
    String especialidad = txtEspecialidad.getText().trim();

    if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!esCorreoValido(correo)) {
        JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Correo inválido", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!esTelefonoValido(telefono)) {
        JOptionPane.showMessageDialog(this, "El teléfono solo debe contener números.", "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    ModeloDocente nuevoDocente = new ModeloDocente();
    nuevoDocente.setNombreDocente(nombre);
    nuevoDocente.setApellidoDocente(apellido);
    nuevoDocente.setCorreoDocente(correo);
    nuevoDocente.setTelefonoDocente(telefono);
    nuevoDocente.setEspecialidadDocente(especialidad);

    try {
        ModeloDocente docenteCreado = serviceDocente.crearDocente(nuevoDocente);
        if (docenteCreado != null && docenteCreado.getIdDocente() > 0) {
            JOptionPane.showMessageDialog(this, "Docente registrado exitosamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDocenteEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el docente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al registrar docente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEDITARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEDITARActionPerformed
        if (txtIdDocente.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un docente de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (txtIdDocente.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un docente de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    String correo = txtCorreo.getText().trim();
    String telefono = txtTelefono.getText().trim();
    String especialidad = txtEspecialidad.getText().trim();

    if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!esCorreoValido(correo)) {
        JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Correo inválido", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!esTelefonoValido(telefono)) {
        JOptionPane.showMessageDialog(this, "El teléfono solo debe contener números.", "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        ModeloDocente docenteEditado = new ModeloDocente();
        docenteEditado.setIdDocente(Integer.parseInt(txtIdDocente.getText()));
        docenteEditado.setNombreDocente(nombre);
        docenteEditado.setApellidoDocente(apellido);
        docenteEditado.setCorreoDocente(correo);
        docenteEditado.setTelefonoDocente(telefono);
        docenteEditado.setEspecialidadDocente(especialidad);

        boolean actualizado = serviceDocente.actualizarDocente(docenteEditado);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Docente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDocenteEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el docente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar docente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEDITARActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEDITAR;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDocente;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtIdDocente;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
