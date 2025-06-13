package Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Modelos.SesionUsuario;
import java.util.Base64;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class TokenService {

    public static class AuthResponse {
        public String token;
        public String rol;
        public String username;
    }

    private static AuthResponse authResponse;
    private static LocalDateTime tokenExpiration;
    private static final String LOGIN_URL = "http://localhost:5148/api/Login/login";

    public static AuthResponse login(String username, String password) {
    try {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDateTime = now.format(formatter);

        System.out.println("? Solicitando nuevo token a las: " + formattedDateTime);

        URL url = new URL(LOGIN_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        InputStreamReader streamReader;
        if (responseCode == 200) {
            streamReader = new InputStreamReader(con.getInputStream(), "utf-8");
        } else {
            streamReader = new InputStreamReader(con.getErrorStream(), "utf-8");
        }

        try (BufferedReader br = new BufferedReader(streamReader)) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Respuesta del servidor: " + response.toString());

            if (responseCode != 200) {
                JOptionPane.showMessageDialog(null, "Error en login: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();

            AuthResponse auth = new AuthResponse();
            auth.token = jsonResponse.get("token").getAsString();
            auth.rol = jsonResponse.get("rol").getAsString();
            auth.username = jsonResponse.get("username").getAsString();

            authResponse = auth;
            SesionUsuario.token = auth.token;
            SesionUsuario.rol = auth.rol;
            SesionUsuario.nombreUsuario = auth.username;

            tokenExpiration = LocalDateTime.now().plusHours(1);

            System.out.println("Token obtenido: " + auth.token);
            return auth;
        }

    } catch (Exception e) {
        System.err.println("Error en login: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al conectar con la API: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }
}

    public static String getToken() {
        if (authResponse != null && tokenExpiration != null && LocalDateTime.now().isBefore(tokenExpiration)) {
            System.out.println("Token aún válido. Retornando token existente.");
            return authResponse.token;
        } else {
            System.out.println("Token expirado o no existente. Necesita autenticación.");
            return null;
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
    }