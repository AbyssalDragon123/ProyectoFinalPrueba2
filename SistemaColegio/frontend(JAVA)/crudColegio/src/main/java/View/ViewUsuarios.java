/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
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
public class ViewUsuarios extends javax.swing.JFrame {
private boolean passwordVisible = false;
    private ServiceUsuario serviceUsuario;

    public ViewUsuarios() {
        setUndecorated(true);
        initComponents();
        txtIdUsuario.setVisible(false);
        setLocationRelativeTo(null);
        setPlaceholders();
        this.serviceUsuario = new ServiceUsuario();
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
    /**
     * Creates new form Registro
     */


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
        btnRegistrar = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        txtVerContrasena = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Apellido = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        RepeatPass = new javax.swing.JPasswordField();
        txtVerContrasena1 = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(44, 47, 53));

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

        btnRegistrar.setBackground(new java.awt.Color(4, 189, 125));
        btnRegistrar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnRegistrar.setText("Registrarse");
        btnRegistrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

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

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("¿Deseas Salir?");

        lblSalir.setBackground(new java.awt.Color(4, 189, 125));
        lblSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(4, 189, 125));
        lblSalir.setText("Click Aqui");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIdUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtVerContrasena))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(RepeatPass, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtVerContrasena1))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(Usuario))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(Apellido))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblSalir)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVerContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(RepeatPass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtVerContrasena1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSalir))
                .addGap(57, 57, 57))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
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
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente. Ahora puede iniciar sesión.", 
                                         "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos
            limpiarCampos();
            
            // Redirigir a la pantalla de login
            ViewLogin login = new ViewLogin();
            login.setVisible(true);
            this.dispose(); // Cierra la ventana actual
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
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
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        // TODO add your handling code here:
        this.dispose(); // Cierra la ventana actual
    }//GEN-LAST:event_lblSalirMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Apellido;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPasswordField RepeatPass;
    private javax.swing.JTextField Usuario;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JLabel txtVerContrasena;
    private javax.swing.JLabel txtVerContrasena1;
    // End of variables declaration//GEN-END:variables

}
