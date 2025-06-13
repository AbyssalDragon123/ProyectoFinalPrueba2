/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;
import Service.ServiceUsuario;
import Modelos.ModeloUsuario;
import static Modelos.SesionUsuario.token;
import java.awt.Color;
import java.awt.Font;
import java.security.MessageDigest;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
/**
 *
 * @author Admin
 */
public class ViewUsuarioCRUD extends javax.swing.JInternalFrame {
     private boolean passwordVisible = false;
     private String correoOriginal = "";
private String token; // Debes recibirlo desde el login o menú principal
    private ServiceUsuario serviceUsuario;
    private DefaultTableModel modeloTabla;

    public ViewUsuarioCRUD(String token) {
        this.token = token;
        initComponents();
        setPlaceholders();
        serviceUsuario = new ServiceUsuario(token);
        txtIdUsuarios.setVisible(false);
        configurarComboBoxRoles();
        cargarUsuariosEnTabla();
        agregarListenerTabla();
        
        // Mostrar/ocultar contraseña
        txtVerContrasena.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordVisible = !passwordVisible;
                if (passwordVisible) {
                    txtPassword.setEchoChar((char) 0);
                    txtVerContrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ojo.png")));
                } else {
                    txtPassword.setEchoChar('•');
                    txtVerContrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-ojo.png")));
                }
            }
        });
    }

    private void configurarComboBoxRoles() {
        cmbRol.removeAllItems();
        cmbRol.addItem("admin");
        cmbRol.addItem("director");
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
            // Nombre
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
                if (txtNombre.getText().isEmpty()) {
                    txtApellido.setText("Ingrese el Apellido");
                    txtApellido.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
        // Contraseña
        txtPassword.setText("********");
        txtPassword.setHorizontalAlignment(JTextField.CENTER);
        txtPassword.setForeground(Color.decode("#A3C6BC"));
        txtPassword.setEchoChar((char) 0);
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (String.valueOf(txtPassword.getPassword()).equals("********")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.WHITE);
                    txtPassword.setEchoChar('*');
                }
            }
            public void focusLost(FocusEvent evt) {
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("********");
                    txtPassword.setForeground(Color.decode("#A3C6BC"));
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });
            // Nombre
        txtUsername.setText("Ingrese el Usuario");
        txtUsername.setHorizontalAlignment(JTextField.CENTER);
        txtUsername.setForeground(Color.decode("#A3C6BC"));
        txtUsername.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtUsername.getText().equals("Ingrese el Usuario")) {
                    txtUsername.setText("");
                    txtUsername.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.setText("Ingrese el Usuario");
                    txtUsername.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
            // Nombre
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
           
        }
    private void cargarUsuariosEnTabla() {
        try {
            List<ModeloUsuario> usuarios = serviceUsuario.getUsuarios();

            String[] columnas = {"ID", "Nombre", "Apellido", "Username", "Correo", "Rol"};

            modeloTabla = new DefaultTableModel(columnas, 0);

            for (ModeloUsuario usuario : usuarios) {
                Object[] fila = {
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getUsername(),
                    usuario.getCorreo(),
                    usuario.getRol()
                };
                modeloTabla.addRow(fila);
            }

            tblUsuarios.setModel(modeloTabla);
            tblUsuarios.removeColumn(tblUsuarios.getColumnModel().getColumn(0));

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(new Color(56, 91, 81));
            headerRenderer.setForeground(new Color(245, 236, 213));
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            headerRenderer.setFont(tblUsuarios.getTableHeader().getFont().deriveFont(Font.BOLD));
            for (int i = 0; i < tblUsuarios.getColumnModel().getColumnCount(); i++) {
                tblUsuarios.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }

            tblUsuarios.setRowHeight(40);
            jScrollPane2.getViewport().setBackground(new Color(87, 142, 126));

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

            for (int i = 1; i < tblUsuarios.getColumnCount(); i++) {
                tblUsuarios.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void agregarListenerTabla() {
    tblUsuarios.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting() && tblUsuarios.getSelectedRow() != -1) {
            int filaVista = tblUsuarios.getSelectedRow();
            int filaModelo = tblUsuarios.convertRowIndexToModel(filaVista);

            String id = modeloTabla.getValueAt(filaModelo, 0).toString();
            String nombre = modeloTabla.getValueAt(filaModelo, 1).toString();
            String apellido = modeloTabla.getValueAt(filaModelo, 2).toString();
            String username = modeloTabla.getValueAt(filaModelo, 3).toString();
            String correo = modeloTabla.getValueAt(filaModelo, 4).toString();
            String rol = modeloTabla.getValueAt(filaModelo, 5).toString().trim();

            txtIdUsuarios.setText(id);
            txtNombre.setText(nombre);
            txtApellido.setText(apellido);
            txtUsername.setText(username);
            txtCorreo.setText(correo);

            correoOriginal = correo;  // Guarda el correo original aquí

            // Seleccionar el rol en el combo ignorando mayúsculas/minúsculas
            boolean rolEncontrado = false;
            for (int i = 0; i < cmbRol.getItemCount(); i++) {
                if (cmbRol.getItemAt(i).equalsIgnoreCase(rol)) {
                    cmbRol.setSelectedIndex(i);
                    rolEncontrado = true;
                    break;
                }
            }
            if (!rolEncontrado) {
                cmbRol.setSelectedIndex(0); // valor por defecto
            }

            txtPassword.setText("");
        }
    });
}

    private void limpiarCampos() {
        txtIdUsuarios.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtUsername.setText("");
        txtCorreo.setText("");
        txtPassword.setText("");
        cmbRol.setSelectedIndex(0);
    }
     // Función para validar correo Gmail válido
private boolean esCorreoValido(String correo) {
    // Regex para validar correos Gmail (puedes ajustar si quieres otros dominios)
    String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(correo);
    return matcher.matches();
}
private boolean correoExiste(String correo, Integer idUsuario) {
        try {
            List<ModeloUsuario> usuarios = serviceUsuario.getUsuarios();
            for (ModeloUsuario usuario : usuarios) {
                if (usuario.getCorreo().equalsIgnoreCase(correo) && (idUsuario == null || usuario.getIdUsuario() != idUsuario)) {
                    return true; // El correo ya existe para otro usuario
                }
            }
            return false; // El correo no existe
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al verificar el correo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return true; // Considerar que existe para evitar errores
        }
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
        txtIdUsuarios = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JPasswordField();
        txtVerContrasena = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnRegistrar4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 236, 213));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(61, 61, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos de Usuarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(245, 236, 213));

        txtNombre.setBackground(new java.awt.Color(87, 142, 126));
        txtNombre.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(245, 236, 213));
        txtNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtIdUsuarios.setBackground(new java.awt.Color(87, 142, 126));
        txtIdUsuarios.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtIdUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        txtIdUsuarios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtUsername.setBackground(new java.awt.Color(87, 142, 126));
        txtUsername.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(245, 236, 213));
        txtUsername.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(245, 236, 213));
        jLabel8.setText("Nombre:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(245, 236, 213));
        jLabel9.setText("Username:");

        txtApellido.setBackground(new java.awt.Color(87, 142, 126));
        txtApellido.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(245, 236, 213));
        txtApellido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(245, 236, 213));
        jLabel10.setText("Contraseña:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(245, 236, 213));
        jLabel12.setText("Correo:");

        txtCorreo.setBackground(new java.awt.Color(87, 142, 126));
        txtCorreo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(245, 236, 213));
        txtCorreo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(245, 236, 213));
        jLabel13.setText("Rol:");

        cmbRol.setBackground(new java.awt.Color(87, 142, 126));
        cmbRol.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        cmbRol.setForeground(new java.awt.Color(245, 236, 213));

        txtPassword.setBackground(new java.awt.Color(87, 142, 126));
        txtPassword.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtVerContrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-ojo.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(245, 236, 213));
        jLabel14.setText("Apellido:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVerContrasena))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtIdUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtIdUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtVerContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo))
                .addGap(30, 30, 30))
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(61, 61, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Tabla de Usuarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(245, 236, 213));

        tblUsuarios.setBackground(new java.awt.Color(87, 142, 126));
        tblUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblUsuarios.setForeground(new java.awt.Color(245, 236, 213));
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblUsuarios.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblUsuarios);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta asignatura?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

        int idUsuario = Integer.parseInt(txtIdUsuarios.getText());

        try {
            serviceUsuario.eliminarUsuario(idUsuario);
            modeloTabla.removeRow(filaSeleccionada);
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegistrar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar4ActionPerformed
 String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    String username = txtUsername.getText().trim();
    String correo = txtCorreo.getText().trim();
    String password = new String(txtPassword.getPassword());
    String rol = cmbRol.getSelectedItem().toString();

    if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || correo.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!esCorreoValido(correo)) {
        JOptionPane.showMessageDialog(this, "El correo debe ser un correo Gmail válido (ejemplo@gmail.com).", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (correoExiste(correo, null)) {
        JOptionPane.showMessageDialog(this, "Este correo ya está registrado.", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    ModeloUsuario nuevoUsuario = new ModeloUsuario(nombre, apellido, username, password, correo, rol);

    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground() throws Exception {
            // Crear y mostrar diálogo en EDT
            SwingUtilities.invokeAndWait(() -> {
                progressDialog = new ProgressDialog((Frame) SwingUtilities.getWindowAncestor(ViewUsuarioCRUD.this), true);
                progressDialog.startProgress();
            });

            // Ejecutar la tarea (registro usuario)
            serviceUsuario.crearUsuario(nuevoUsuario);

            return null;
        }

        @Override
        protected void done() {
            try {
                get(); // para capturar excepciones
                limpiarCampos();
                cargarUsuariosEnTabla();

                // Mostrar mensaje **después** de que el diálogo se cerró
                JOptionPane.showMessageDialog(ViewUsuarioCRUD.this, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ViewUsuarioCRUD.this, "Error al registrar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    };

    worker.execute();
    }//GEN-LAST:event_btnRegistrar4ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
int filaSeleccionada = tblUsuarios.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un usuario para editar.", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idUsuario = Integer.parseInt(txtIdUsuarios.getText());
    String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    String username = txtUsername.getText().trim();
    String correo = txtCorreo.getText().trim();
    String password = new String(txtPassword.getPassword()); // Contraseña en texto plano
    String rol = cmbRol.getSelectedItem().toString();

    if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || correo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos excepto contraseña son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!esCorreoValido(correo)) {
        JOptionPane.showMessageDialog(this, "El correo debe ser un correo Gmail válido (ejemplo@gmail.com).", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (correoExiste(correo, idUsuario)) {
        JOptionPane.showMessageDialog(this, "Este correo ya está registrado para otro usuario.", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Si el correo fue modificado, mostrar diálogo con GIF y hacer la actualización en background
    if (!correo.equalsIgnoreCase(correoOriginal)) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            ProgressDialog progressDialog;

            @Override
            protected Void doInBackground() throws Exception {
                SwingUtilities.invokeAndWait(() -> {
                    progressDialog = new ProgressDialog((Frame) SwingUtilities.getWindowAncestor(ViewUsuarioCRUD.this), true);
                    progressDialog.startProgress();
                });

                String passwordFinal;
                if (password.isEmpty()) {
                    ModeloUsuario usuarioActual = serviceUsuario.getUsuario(idUsuario);
                    passwordFinal = usuarioActual.getPass();
                } else {
                    passwordFinal = password; // Contraseña en texto plano
                }

                ModeloUsuario usuarioEditado = new ModeloUsuario(idUsuario, nombre, apellido, username, passwordFinal, correo, rol); // Enviar password sin hashear
                boolean actualizado = serviceUsuario.actualizarUsuario(usuarioEditado);

                if (!actualizado) {
                    throw new Exception("No se pudo actualizar el usuario.");
                }

                return null;
            }

            @Override
            protected void done() {
                if (progressDialog != null) {
                    progressDialog.dispose();
                }
                try {
                    get();
                    limpiarCampos();
                    cargarUsuariosEnTabla();
                    correoOriginal = correo;
                    JOptionPane.showMessageDialog(ViewUsuarioCRUD.this, "Usuario actualizado y correo enviado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ViewUsuarioCRUD.this, "Error al actualizar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    } else {
        // Si no cambió el correo, actualizar sin diálogo
        try {
            String passwordFinal;
            if (password.isEmpty()) {
                ModeloUsuario usuarioActual = serviceUsuario.getUsuario(idUsuario);
                passwordFinal = usuarioActual.getPass();
            } else {
                passwordFinal = password; // Contraseña en texto plano
            }

            ModeloUsuario usuarioEditado = new ModeloUsuario(idUsuario, nombre, apellido, username, passwordFinal, correo, rol); // Enviar password sin hashear
            boolean actualizado = serviceUsuario.actualizarUsuario(usuarioEditado);

            if (actualizado) {
                limpiarCampos();
                cargarUsuariosEnTabla();
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar4;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtIdUsuarios;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JLabel txtVerContrasena;
    // End of variables declaration//GEN-END:variables
}
