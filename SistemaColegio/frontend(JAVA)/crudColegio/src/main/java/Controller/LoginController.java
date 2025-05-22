package Controller;

import View.ViewLogin;
import View.ViewMenuPrincipal;
import Modelos.ModeloLogin;
import Modelos.SesionUsuario;
import Service.ServiceLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {
    private ViewLogin login;
    private ServiceLogin servicio;

    public LoginController(ViewLogin login, ServiceLogin servicio) {
        this.login = login;
        this.servicio = servicio;

        // Mostrar la vista del login
        this.login.setVisible(true);

        // Agregar el listener al botón de login
        this.login.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginActionPerformed(e);
            }
        });
    }

    // Método que se ejecuta al hacer clic en el botón de login
    public void loginActionPerformed(ActionEvent e) {
        String username = login.getTxtUsuario().getText().trim();
        String password = new String(login.jPasswordField().getPassword());

        // Evita login con placeholders o campos vacíos
        if (username.equals("Ingrese su usuario") || password.equals("********") || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(login, "Por favor, ingresa tu usuario y contraseña.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Autenticación mediante el servicio
        ModeloLogin usuario = servicio.autenticar(username, password);

      if (usuario != null) {
    // Guardar en la sesión
    SesionUsuario.nombreUsuario = usuario.getUserName();
    
    // Crear e iniciar el formulario principal
    ViewMenuPrincipal menu = new ViewMenuPrincipal();
    menu.setLocationRelativeTo(null);
    
    // Configurar el listener para cerrar sesión
    menu.configurarCerrarSesion(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Limpiar la sesión
            SesionUsuario.nombreUsuario = null;
            // Cerrar el menú principal
            menu.dispose();
            // Crear nuevo login y su controlador
            ViewLogin nuevoLogin = new ViewLogin();
            ServiceLogin nuevoServicio = new ServiceLogin();
            new LoginController(nuevoLogin, nuevoServicio);
        }
    });
    
    menu.setVisible(true);
    login.dispose();
} else {
    JOptionPane.showMessageDialog(login, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
}
    }
}