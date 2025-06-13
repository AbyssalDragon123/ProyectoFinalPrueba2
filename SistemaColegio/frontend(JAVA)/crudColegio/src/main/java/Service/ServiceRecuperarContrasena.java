package Service;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ServiceRecuperarContrasena {

    // Método para solicitar código de recuperación (solo correo)
    public static boolean solicitarCodigoRecuperacion(String correo) {
        try {
            URL url = new URL("http://localhost:5148/api/Usuario/solicitar-recuperacion");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            // Enviar solo el correo como JSON string
            String jsonInput = String.format("\"%s\"", correo);

            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else if (responseCode == 404) {
                JOptionPane.showMessageDialog(null, "Correo no encontrado.");
            } else {
                JOptionPane.showMessageDialog(null, "Error del servidor: " + responseCode);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la API: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Método para cambiar contraseña con correo, código y nueva contraseña
    public static boolean cambiarContrasenaConCodigo(String correo, String codigo, String nuevaContrasena) {
        try {
            URL url = new URL("http://localhost:5148/api/Usuario/restablecer-contrasena");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            // Construir JSON con correo, codigo y nuevaContrasena
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("correo", correo);
            jsonInput.put("codigo", codigo);
            jsonInput.put("nuevaContrasena", nuevaContrasena);

            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonInput.toString().getBytes("utf-8"));
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else if (responseCode == 400) {
                JOptionPane.showMessageDialog(null, "Código inválido o expirado.");
            } else {
                JOptionPane.showMessageDialog(null, "Error del servidor: " + responseCode);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la API: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}