/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.LoginController;
import Service.ServiceLogin;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
/**
 *
 * @author Admin
 */
public class ViewRecuperarContrasena extends javax.swing.JFrame {

private String correoGuardado; // Para guardar el correo ingresado
    private boolean passwordVisible = false;
private LoginController loginController; // Controlador para manejar sesión

    public ViewRecuperarContrasena(LoginController loginController) {
initComponents();
        this.loginController = loginController;

        // Registrar esta ventana para que se cierre automáticamente cuando expire la sesión
        this.loginController.registrarVentanas(
            List.of(this), // Ventanas abiertas
            new ArrayList<>() // Internal frames si tienes, sino vacío
        );
        setPlaceholders();;
        verificarSesion();
    }

    // Ejemplo de método que detecta expiración de sesión (puedes llamarlo tras cada acción que requiera token válido)
    private void verificarSesion() {
        boolean sesionExpirada = false;

        // Aquí tu lógica para detectar si la sesión expiró, por ejemplo:
        // si el token ya no es válido o recibiste un error 401 de la API
        // sesionExpirada = ...

        if (sesionExpirada) {
            JOptionPane.showMessageDialog(this, "Tu sesión ha expirado. Se cerrará esta ventana.");
            loginController.cerrarSesion(); // Esto cerrará esta ventana y abrirá el login
        }
    

        // Al iniciar, solo habilitar correo y botón solicitar código
        NuevaContrasena.setEnabled(false);
        repetircontrasena.setEnabled(false);
        cambiarContrasenaButton.setEnabled(false);

        // Listener para solicitar código
        solicitarCodigoButton.addActionListener(e -> {
            String correo = correoField.getText().trim();
            if (!correo.isEmpty()) {
                solicitarCodigo(correo);
            } else {
                JOptionPane.showMessageDialog(ViewRecuperarContrasena.this, "Por favor, ingrese su correo electrónico.");
            }
        });

        // Listener para cambiar contraseña (solo uno)
        cambiarContrasenaButton.addActionListener(e -> {
            String correo = correoField.getText().trim();
            String codigo = NuevaContrasena.getText().trim();
            String nuevaPassword = new String(repetircontrasena.getPassword());

            if (!correo.isEmpty() && !codigo.isEmpty() && !nuevaPassword.isEmpty()) {
                cambiarContrasena(correo, codigo, nuevaPassword);
            } else {
                JOptionPane.showMessageDialog(ViewRecuperarContrasena.this, "Por favor, complete todos los campos.");
            }
        });

         // Mostrar/ocultar contraseña
        txtvernuevacontrasena.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordVisible = !passwordVisible;
                if (passwordVisible) {
                    repetircontrasena.setEchoChar((char) 0);
                    txtvernuevacontrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ojo.png")));
                } else {
                    repetircontrasena.setEchoChar('•');
                    txtvernuevacontrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-ojo.png")));
                }
            }
        });

        lblSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Estás seguro de que deseas salir del formulario?",
                    "Confirmación de salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    private void setPlaceholders() {
        // Código
        NuevaContrasena.setText("Ingrese su código");
        NuevaContrasena.setHorizontalAlignment(JTextField.CENTER);
        NuevaContrasena.setForeground(Color.decode("#BEFDE7"));
        NuevaContrasena.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (NuevaContrasena.getText().equals("Ingrese su código")) {
                    NuevaContrasena.setText("");
                    NuevaContrasena.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (NuevaContrasena.getText().isEmpty()) {
                    NuevaContrasena.setText("Ingrese su código");
                    NuevaContrasena.setForeground(Color.decode("#BEFDE7"));
                }
            }
        });

        // Correo
        correoField.setText("Ingrese su correo");
        correoField.setHorizontalAlignment(JTextField.CENTER);
        correoField.setForeground(Color.decode("#BEFDE7"));
        correoField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (correoField.getText().equals("Ingrese su correo")) {
                    correoField.setText("");
                    correoField.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (correoField.getText().isEmpty()) {
                    correoField.setText("Ingrese su correo");
                    correoField.setForeground(Color.decode("#BEFDE7"));
                }
            }
        });

        // Nueva Contraseña
        repetircontrasena.setText("********");
        repetircontrasena.setHorizontalAlignment(JTextField.CENTER);
        repetircontrasena.setForeground(Color.decode("#BEFDE7"));
        repetircontrasena.setEchoChar((char) 0);
        repetircontrasena.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (String.valueOf(repetircontrasena.getPassword()).equals("********")) {
                    repetircontrasena.setText("");
                    repetircontrasena.setForeground(Color.WHITE);
                    repetircontrasena.setEchoChar('*');
                }
            }
            public void focusLost(FocusEvent evt) {
                if (String.valueOf(repetircontrasena.getPassword()).isEmpty()) {
                    repetircontrasena.setText("********");
                    repetircontrasena.setForeground(Color.decode("#BEFDE7"));
                    repetircontrasena.setEchoChar((char) 0);
                }
            }
        });
    }
private boolean esCorreoValido(String correo) {
    // Expresión regular simple para validar correo electrónico
    String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    return correo.matches(regex);
}

private void solicitarCodigo(String correo) {
    if (correo.isEmpty() || correo.equals("Ingrese su correo")) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese su correo electrónico.");
        return;
    }
    if (!esCorreoValido(correo)) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un correo electrónico válido.");
        return;
    }

    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        ProgressDialog progressdialog2;

        @Override
        protected Void doInBackground() throws Exception {
            // Mostrar diálogo con GIF en EDT
            SwingUtilities.invokeAndWait(() -> {
                progressdialog2 = new ProgressDialog((Frame) SwingUtilities.getWindowAncestor(ViewRecuperarContrasena.this), true);
                progressdialog2.startProgress();
            });

            // Ejecutar la petición HTTP
            URL url = new URL("http://localhost:5148/api/Usuario/solicitar-recuperacion");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonInputString = String.format("\"%s\"", correo);

            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonInputString.getBytes("utf-8"));
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                correoGuardado = correo;
            } else {
                throw new Exception("Error al solicitar código. Código: " + responseCode);
            }

            return null;
        }

        @Override
        protected void done() {
            if (progressdialog2 != null) {
                progressdialog2.dispose();
            }
            try {
                get(); // Captura excepciones

                NuevaContrasena.setEnabled(true);
                repetircontrasena.setEnabled(true);
                cambiarContrasenaButton.setEnabled(true);

                correoField.setEnabled(false);
                solicitarCodigoButton.setEnabled(false);

                JOptionPane.showMessageDialog(ViewRecuperarContrasena.this, "Se envió el código a tu correo.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ViewRecuperarContrasena.this, "Error: " + e.getCause().getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    };

    worker.execute();
}
    private void cambiarContrasena(String correo, String codigo, String nuevaPassword) {
       try {
        URL url = new URL("http://localhost:5148/api/Usuario/restablecer-contrasena");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String jsonInputString = String.format(
            "{\"correo\": \"%s\", \"codigo\": \"%s\", \"nuevaContrasena\": \"%s\"}",
            correo, codigo, nuevaPassword
        );

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInputString.getBytes("utf-8"));
        }

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.");

            // Cerrar este formulario
            this.dispose();

            // Abrir formulario de login y controlador
            ViewLogin loginView = new ViewLogin();
            ServiceLogin loginService = new ServiceLogin();
            new Controller.LoginController(loginView, loginService);

        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String mensajeError = response.toString();

                JOptionPane.showMessageDialog(this, "Error al cambiar la contraseña: " + mensajeError);

                // Si el código expiró, permitir reingresar el correo
                if (mensajeError.toLowerCase().contains("expirado")) {
                    correoField.setEnabled(true);
                    solicitarCodigoButton.setEnabled(true);

                    NuevaContrasena.setEnabled(false);
                    repetircontrasena.setEnabled(false);
                    cambiarContrasenaButton.setEnabled(false);

                    NuevaContrasena.setText("Ingrese su código");
                    repetircontrasena.setText("********");
                }
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al cambiar la contraseña: " + ex.getMessage());
        ex.printStackTrace();
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
        correoField = new javax.swing.JTextField();
        NuevaContrasena = new javax.swing.JTextField();
        solicitarCodigoButton = new javax.swing.JButton();
        cambiarContrasenaButton = new javax.swing.JButton();
        repetircontrasena = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtvernuevacontrasena = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(44, 47, 53));

        correoField.setBackground(new java.awt.Color(81, 89, 102));
        correoField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        correoField.setForeground(new java.awt.Color(255, 255, 255));
        correoField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        NuevaContrasena.setBackground(new java.awt.Color(81, 89, 102));
        NuevaContrasena.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        NuevaContrasena.setForeground(new java.awt.Color(255, 255, 255));
        NuevaContrasena.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        solicitarCodigoButton.setBackground(new java.awt.Color(4, 189, 125));
        solicitarCodigoButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        solicitarCodigoButton.setText("Obtener Codigo");
        solicitarCodigoButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        solicitarCodigoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        solicitarCodigoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solicitarCodigoButtonActionPerformed(evt);
            }
        });

        cambiarContrasenaButton.setBackground(new java.awt.Color(4, 189, 125));
        cambiarContrasenaButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cambiarContrasenaButton.setText("Cambiar Contraseña");
        cambiarContrasenaButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cambiarContrasenaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cambiarContrasenaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarContrasenaButtonActionPerformed(evt);
            }
        });

        repetircontrasena.setBackground(new java.awt.Color(81, 89, 102));
        repetircontrasena.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        repetircontrasena.setForeground(new java.awt.Color(255, 255, 255));
        repetircontrasena.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Recuperar Contraseña");

        jSeparator1.setBackground(new java.awt.Color(251, 255, 228));
        jSeparator1.setForeground(new java.awt.Color(251, 255, 228));
        jSeparator1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(4, 189, 125), 4, true));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/correo.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloquear.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seguro.png"))); // NOI18N

        txtvernuevacontrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-ojo.png"))); // NOI18N

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cambiarContrasenaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(solicitarCodigoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(NuevaContrasena))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(correoField, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(repetircontrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtvernuevacontrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSalir)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(correoField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(solicitarCodigoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(NuevaContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(repetircontrasena, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtvernuevacontrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cambiarContrasenaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSalir))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void solicitarCodigoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solicitarCodigoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_solicitarCodigoButtonActionPerformed

    private void cambiarContrasenaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarContrasenaButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cambiarContrasenaButtonActionPerformed

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_lblSalirMouseClicked

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField NuevaContrasena;
    private javax.swing.JButton cambiarContrasenaButton;
    private javax.swing.JTextField correoField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPasswordField repetircontrasena;
    private javax.swing.JButton solicitarCodigoButton;
    private javax.swing.JLabel txtvernuevacontrasena;
    // End of variables declaration//GEN-END:variables
}
