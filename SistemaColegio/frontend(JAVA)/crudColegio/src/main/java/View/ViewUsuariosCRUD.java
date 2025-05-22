/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;
import Modelos.ModeloUsuario;
import Service.ServiceUsuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author Admin
 */
public class ViewUsuariosCRUD extends javax.swing.JInternalFrame {
 
private boolean passwordVisible = false;
    private ServiceUsuario serviceUsuario;

    public ViewUsuariosCRUD() {
        this.serviceUsuario = new ServiceUsuario(); // <-- PRIMERO
        initComponents();
 
        this.cargarUsuariosEnTabla();
        txtIdUsuario.setVisible(false);
        setPlaceholders();
        
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
        lblSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int opcion = javax.swing.JOptionPane.showConfirmDialog(
                    null,
                    "¿Estás seguro de que deseas salir del formulario?",
                    "Confirmación de salida",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
                );
                if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
           // Mostrar/ocultar contraseña
        txtVerContrasena1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordVisible = !passwordVisible;
                if (passwordVisible) {
                    RepeatPass.setEchoChar((char) 0);
                    txtVerContrasena1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ojo.png")));
                } else {
                    RepeatPass.setEchoChar('•');
                    txtVerContrasena1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-ojo.png")));
                }
            }
        });
        //seleccionar fila
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting() && tblUsuarios.getSelectedRow() != -1) {
        int fila = tblUsuarios.getSelectedRow();
        txtIdUsuario.setText(tblUsuarios.getValueAt(fila, 0).toString());
        Nombre.setText(tblUsuarios.getValueAt(fila, 1).toString());
        Apellido.setText(tblUsuarios.getValueAt(fila, 2).toString());
        Usuario.setText(tblUsuarios.getValueAt(fila, 3).toString());
        txtCorreo.setText(tblUsuarios.getValueAt(fila, 4).toString());
        RepeatPass.setText("");
        txtPassword.setText("");
    }
});
    }
private void cargarUsuariosEnTabla() {
    try {
        // Obtén la lista de usuarios desde el servicio
        java.util.List<ModeloUsuario> listaUsuarios = serviceUsuario.getUsuarios();

        // Define las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Apellido", "Usuario", "Correo", "Rol"};
        Object[][] datos = new Object[listaUsuarios.size()][columnas.length];

        // Llena los datos
        for (int i = 0; i < listaUsuarios.size(); i++) {
            ModeloUsuario u = listaUsuarios.get(i);
            datos[i][0] = u.getIdUsuario();
            datos[i][1] = u.getNombre();
            datos[i][2] = u.getApellido();
            datos[i][3] = u.getUsername();
            datos[i][4] = u.getCorreo();
            datos[i][5] = u.getRol();
        }

        // Crea el modelo de la tabla y asígnalo
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no editables
            }
        };
        tblUsuarios.setModel(model);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    tblUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
    tblUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
    tblUsuarios.getColumnModel().getColumn(0).setWidth(0);
}

    private void setPlaceholders() {
        // Nombre
        Nombre.setText("Ingrese su Nombre");
        Nombre.setHorizontalAlignment(JTextField.CENTER);
        Nombre.setForeground(Color.decode("#BEFDE7"));
        Nombre.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (Nombre.getText().equals("Ingrese su Nombre")) {
                    Nombre.setText("");
                    Nombre.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (Nombre.getText().isEmpty()) {
                    Nombre.setText("Ingrese su Nombre");
                    Nombre.setForeground(Color.decode("#BEFDE7"));
                }
            }
        });
        // Apellido
        Apellido.setText("Ingrese su Apellido");
        Apellido.setHorizontalAlignment(JTextField.CENTER);
        Apellido.setForeground(Color.decode("#BEFDE7"));
        Apellido.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (Apellido.getText().equals("Ingrese su Apellido")) {
                    Apellido.setText("");
                    Apellido.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (Apellido.getText().isEmpty()) {
                    Apellido.setText("Ingrese su Apellido");
                    Apellido.setForeground(Color.decode("#BEFDE7"));
                }
            }
        });
        // Usuario
        Usuario.setText("Ingrese su Usuario");
        Usuario.setForeground(Color.decode("#BEFDE7"));
        Usuario.setHorizontalAlignment(JTextField.CENTER);
        Usuario.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (Usuario.getText().equals("Ingrese su Usuario") || Usuario.getText().equals("Ingrese su usuario")) {
                    Usuario.setText("");
                    Usuario.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (Usuario.getText().isEmpty()) {
                    Usuario.setText("Ingrese su Usuario");
                    Usuario.setForeground(Color.decode("#BEFDE7"));
                }
            }
        });
        // Correo
        txtCorreo.setText("Ingrese su Correo");
        txtCorreo.setHorizontalAlignment(JTextField.CENTER);
        txtCorreo.setForeground(Color.decode("#BEFDE7"));
        txtCorreo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtCorreo.getText().equals("Ingrese su Correo")) {
                    txtCorreo.setText("");
                    txtCorreo.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtCorreo.getText().isEmpty()) {
                    txtCorreo.setText("Ingrese su Correo");
                    txtCorreo.setForeground(Color.decode("#BEFDE7"));
                }
            }
        });
        // Contraseña
        txtPassword.setText("****************");
        txtPassword.setHorizontalAlignment(JTextField.CENTER);
        txtPassword.setForeground(Color.decode("#BEFDE7"));
        txtPassword.setEchoChar((char) 0);
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (String.valueOf(txtPassword.getPassword()).equals("****************")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.WHITE);
                    txtPassword.setEchoChar('•');
                }
            }
            public void focusLost(FocusEvent evt) {
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("****************");
                    txtPassword.setForeground(Color.decode("#BEFDE7"));
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });
        // Repetir Contraseña
        RepeatPass.setText("****************");
        RepeatPass.setHorizontalAlignment(JTextField.CENTER);
        RepeatPass.setForeground(Color.decode("#BEFDE7"));
        RepeatPass.setEchoChar((char) 0);
        RepeatPass.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (String.valueOf(RepeatPass.getPassword()).equals("****************")) {
                    RepeatPass.setText("");
                    RepeatPass.setForeground(Color.WHITE);
                    RepeatPass.setEchoChar('•');
                }
            }
            public void focusLost(FocusEvent evt) {
                if (String.valueOf(RepeatPass.getPassword()).isEmpty()) {
                    RepeatPass.setText("****************");
                    RepeatPass.setForeground(Color.decode("#BEFDE7"));
                    RepeatPass.setEchoChar((char) 0);
                }
            }
        });
    } 
 // Método para limpiar los campos del formulario
        private void limpiarCampos() {
            // Restaurar los placeholders
            Nombre.setText("Ingrese su Nombre");
            Nombre.setForeground(Color.decode("#BEFDE7"));

            Apellido.setText("Ingrese su Apellido");
            Apellido.setForeground(Color.decode("#BEFDE7"));

            Usuario.setText("Ingrese su Usuario");
            Usuario.setForeground(Color.decode("#BEFDE7"));

            txtCorreo.setText("Ingrese su Correo");
            txtCorreo.setForeground(Color.decode("#BEFDE7"));

            txtPassword.setText("****************");
            txtPassword.setForeground(Color.decode("#BEFDE7"));
            txtPassword.setEchoChar((char) 0);

            RepeatPass.setText("****************");
            RepeatPass.setForeground(Color.decode("#BEFDE7"));
            RepeatPass.setEchoChar((char) 0);
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
        jLabel1 = new javax.swing.JLabel();
        Nombre = new javax.swing.JTextField();
        Usuario = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        txtVerContrasena = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Apellido = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        RepeatPass = new javax.swing.JPasswordField();
        txtVerContrasena1 = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        lblSalir = new javax.swing.JLabel();
        btnRegistrar4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(44, 47, 53));
        jPanel1.setPreferredSize(new java.awt.Dimension(889, 631));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registro");

        Nombre.setBackground(new java.awt.Color(81, 89, 102));
        Nombre.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Nombre.setForeground(new java.awt.Color(255, 255, 255));
        Nombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Usuario.setBackground(new java.awt.Color(81, 89, 102));
        Usuario.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Usuario.setForeground(new java.awt.Color(255, 255, 255));
        Usuario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtPassword.setBackground(new java.awt.Color(81, 89, 102));
        txtPassword.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtVerContrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-ojo.png"))); // NOI18N

        jSeparator1.setForeground(new java.awt.Color(4, 189, 125));
        jSeparator1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(4, 189, 125), 4, true));

        Apellido.setBackground(new java.awt.Color(81, 89, 102));
        Apellido.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Apellido.setForeground(new java.awt.Color(255, 255, 255));
        Apellido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtCorreo.setBackground(new java.awt.Color(81, 89, 102));
        txtCorreo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(255, 255, 255));
        txtCorreo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        RepeatPass.setBackground(new java.awt.Color(81, 89, 102));
        RepeatPass.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        RepeatPass.setForeground(new java.awt.Color(255, 255, 255));
        RepeatPass.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtVerContrasena1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-ojo.png"))); // NOI18N

        txtIdUsuario.setBackground(new java.awt.Color(81, 89, 102));
        txtIdUsuario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtIdUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtIdUsuario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblSalir.setBackground(new java.awt.Color(4, 189, 125));
        lblSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(4, 189, 125));
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
        });

        btnRegistrar4.setBackground(new java.awt.Color(4, 189, 125));
        btnRegistrar4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnRegistrar4.setText("Registrarse");
        btnRegistrar4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegistrar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrar4ActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(4, 189, 125));
        btnEditar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(4, 189, 125));
        btnLimpiar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblUsuarios.setBackground(new java.awt.Color(155, 163, 175));
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
        jScrollPane1.setViewportView(tblUsuarios);

        btnEliminar.setBackground(new java.awt.Color(4, 189, 125));
        btnEliminar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(191, 191, 191)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28))
                            .addComponent(jLabel1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(lblSalir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVerContrasena))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(RepeatPass, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVerContrasena1)))
                .addGap(94, 94, 94))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(71, 71, 71))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVerContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addComponent(RepeatPass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtVerContrasena1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSalir)
                .addGap(57, 57, 57))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        // TODO add your handling code here:
        this.dispose(); // Cierra la ventana actual
    }//GEN-LAST:event_lblSalirMouseClicked

    private void btnRegistrar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar4ActionPerformed
        // TODO add your handling code here:
          // Validar campos vacíos
    String nombre = Nombre.getText().trim();
    String apellido = Apellido.getText().trim();
    String usuario = Usuario.getText().trim();
    String correo = txtCorreo.getText().trim();
    String pass = String.valueOf(txtPassword.getPassword()).trim();
    String repeatPass = String.valueOf(RepeatPass.getPassword()).trim();

    // Validaciones básicas
    if (nombre.isEmpty() || nombre.equals("Ingrese su Nombre") ||
        apellido.isEmpty() || apellido.equals("Ingrese su Apellido") ||
        usuario.isEmpty() || usuario.equals("Ingrese su Usuario") ||
        correo.isEmpty() || correo.equals("Ingrese su Correo") ||
        pass.isEmpty() || pass.equals("****************") ||
        repeatPass.isEmpty() || repeatPass.equals("****************")) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!pass.equals(repeatPass)) {
        JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de contraseña", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Crear el modelo de usuario
    ModeloUsuario nuevoUsuario = new ModeloUsuario();
    nuevoUsuario.setNombre(nombre);
    nuevoUsuario.setApellido(apellido);
    nuevoUsuario.setUsername(usuario);
    nuevoUsuario.setCorreo(correo);
    nuevoUsuario.setPass(pass);
    nuevoUsuario.setRol("docente"); // O el rol que corresponda

    try {
        ModeloUsuario usuarioCreado = serviceUsuario.crearUsuario(nuevoUsuario);
        if (usuarioCreado != null && usuarioCreado.getIdUsuario() > 0) {
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.", 
                                         "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos
            limpiarCampos();
            
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnRegistrar4ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        // Validar que haya un usuario seleccionado
    if (txtIdUsuario.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Validar campos
    String nombre = Nombre.getText().trim();
    String apellido = Apellido.getText().trim();
    String usuario = Usuario.getText().trim();
    String correo = txtCorreo.getText().trim();
    String pass = String.valueOf(txtPassword.getPassword()).trim();
    String repeatPass = String.valueOf(RepeatPass.getPassword()).trim();

    if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || correo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!pass.equals(repeatPass)) {
        JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de contraseña", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        ModeloUsuario usuarioEditado = new ModeloUsuario();
        usuarioEditado.setIdUsuario(Integer.parseInt(txtIdUsuario.getText()));
        usuarioEditado.setNombre(nombre);
        usuarioEditado.setApellido(apellido);
        usuarioEditado.setUsername(usuario);
        usuarioEditado.setCorreo(correo);
        usuarioEditado.setPass(pass);
        usuarioEditado.setRol("docente"); // O el rol que corresponda

        boolean actualizado = serviceUsuario.actualizarUsuario(usuarioEditado);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarUsuariosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos() ;
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        // Validar que haya un usuario seleccionado
    if (txtIdUsuario.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        int idUsuario = Integer.parseInt(txtIdUsuario.getText());
        boolean eliminado = serviceUsuario.eliminarUsuario(idUsuario);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarUsuariosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Apellido;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPasswordField RepeatPass;
    private javax.swing.JTextField Usuario;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JLabel txtVerContrasena;
    private javax.swing.JLabel txtVerContrasena1;
    // End of variables declaration//GEN-END:variables
}
