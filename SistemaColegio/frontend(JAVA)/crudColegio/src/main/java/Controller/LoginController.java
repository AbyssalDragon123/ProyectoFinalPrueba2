package Controller;

import View.ViewLogin;
import View.ViewMenuPrincipal;
import Modelos.SesionUsuario;
import Service.ServiceLogin;
import Service.TokenService;
import Service.TokenService.AuthResponse;
import View.SplashScreen;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.json.JSONObject;

public class LoginController {

    private ViewLogin login;
    private ServiceLogin servicio;

    // Listas para manejar ventanas abiertas e internal frames
    private List<Window> ventanasAbiertas = new ArrayList<>();
    private List<JInternalFrame> internalFrames = new ArrayList<>();

    public LoginController(ViewLogin login, ServiceLogin servicio) {
        this.login = login;
        this.servicio = servicio;

        this.login.setVisible(true);

        this.login.getBtnLogin().addActionListener(this::loginActionPerformed);
    }

    public void loginActionPerformed(ActionEvent e) {
        String username = login.getTxtUsuario().getText().trim();
        String password = new String(login.getPassword().getPassword());

        try {
            AuthResponse auth = TokenService.login(username, password);

            if (auth != null && auth.token != null && !auth.token.isEmpty()) {
                SesionUsuario.nombreUsuario = auth.username;
                SesionUsuario.token = auth.token;
                SesionUsuario.rol = auth.rol;

                // Crear el menú principal pero NO mostrarlo aún
                ViewMenuPrincipal menu = new ViewMenuPrincipal(auth.username, auth.rol, auth.token, this);

                // Registrar ventanas e internal frames que deben cerrarse al expirar sesión
                registrarVentanas(List.of(menu), new ArrayList<>()); // Agrega internal frames si tienes

                programarCierreSesion(auth.token);

                // Cerrar el login para que no quede abierto en segundo plano
                login.dispose();

                // Mostrar splash screen antes de abrir el menú
                SplashScreen splash = new SplashScreen(menu);
                splash.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(login, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(login, "Error al iniciar sesión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public long obtenerExpiracionToken(String token) {
        try {
            String[] partes = token.split("\\.");
            if (partes.length < 2) return 0;
            String payload = new String(Base64.getUrlDecoder().decode(partes[1]));
            JSONObject jsonPayload = new JSONObject(payload);
            return jsonPayload.getLong("exp"); // tiempo en segundos desde epoch
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Registra las ventanas e internal frames que deben cerrarse al expirar sesión.
     * @param ventanas Lista de ventanas (JFrame, JDialog, etc)
     * @param internals Lista de internal frames
     */
    public void registrarVentanas(List<Window> ventanas, List<JInternalFrame> internals) {
        this.ventanasAbiertas = ventanas;
        this.internalFrames = internals;
    }

    /**
     * Programa el cierre de sesión basado en la expiración del token.
     */
    public void programarCierreSesion(String token) {
        long exp = obtenerExpiracionToken(token);
        long ahora = System.currentTimeMillis() / 1000L; // en segundos
        long delaySegundos = exp - ahora;

        if (delaySegundos <= 0) {
            cerrarSesion();
            return;
        }

        int delayMillis = (int) (delaySegundos * 1000);

        Timer timer = new Timer(delayMillis, e -> cerrarSesion());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Cierra todas las ventanas e internal frames registrados y abre el login.
     */
    public void cerrarSesion() {
        Window ventanaParaMensaje = null;
        if (!ventanasAbiertas.isEmpty()) {
            ventanaParaMensaje = ventanasAbiertas.get(0);
        } else if (login != null && login.isVisible()) {
            ventanaParaMensaje = login;
        }

        if (ventanaParaMensaje != null) {
            JOptionPane.showMessageDialog(ventanaParaMensaje, "Tu sesión ha expirado. Por favor, inicia sesión de nuevo.");
        }

        SesionUsuario.limpiarSesion();

        // Cerrar internal frames
        for (JInternalFrame internal : internalFrames) {
            if (internal.isVisible()) {
                internal.dispose();
            }
        }

        // Cerrar ventanas abiertas
        for (Window ventana : ventanasAbiertas) {
            if (ventana.isVisible()) {
                ventana.dispose();
            }
        }

        // Cerrar login si está abierto
        if (login != null && login.isVisible()) {
            login.dispose();
        }

        // Abrir login limpio
        ViewLogin nuevoLogin = new ViewLogin();
        ServiceLogin nuevoServicio = new ServiceLogin();
        new LoginController(nuevoLogin, nuevoServicio);
    }
}