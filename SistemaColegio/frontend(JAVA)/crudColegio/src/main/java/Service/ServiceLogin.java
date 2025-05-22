package Service;
import Modelos.ModeloLogin;
import Modelos.SesionUsuario; // Importa la clase SesionUsuario
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
/**
 *
 * @author Admin
 */
public class ServiceLogin {
    public static ModeloLogin autenticar(String username, String password) {
        try {
            URL url = new URL("http://localhost:5148/api/Login/login"); //Endpoint
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            //Enviar JSON de credenciales
            
            String jsonInput = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", username, password);
            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }

            // Verificamos la respuesta
            
            if (con.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                // Convertir JSON a objeto Java
                JSONObject json = new JSONObject(response.toString());

                ModeloLogin login = new ModeloLogin();
                login.setUserName(json.getString("username"));
                login.setRol(String.valueOf(json.get("rol"))); // Convertimos el enum a string (si es necesario)
                
                // Guardar datos en la sesión
                SesionUsuario.nombreUsuario = login.getUserName();
                SesionUsuario.rol = login.getRol();
                
                // Si tu API devuelve el ID, también guárdalo
                if (json.has("idUsuario")) {
                    SesionUsuario.idUsuario = json.getInt("idUsuario");
                }
                
                System.out.println("Usuario autenticado: " + username + " Rol: " + login.getRol());

                return login;
                
            } else if (con.getResponseCode() == 401) {
               // JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            } else {
                //JOptionPane.showMessageDialog(null, "Error del servidor: " + con.getResponseCode());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la API: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}