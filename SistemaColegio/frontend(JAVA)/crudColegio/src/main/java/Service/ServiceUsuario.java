package Service;

import Modelos.ModeloUsuario;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class ServiceUsuario {
    private static final String BASE_URL = "http://localhost:5148/api/Usuario";
    private final String token;

    public ServiceUsuario(String token) {
        this.token = token;
        System.out.println("ServiceUsuario token recibido: " + token);
    }

    public void enviarCorreoBienvenida(String destinatario, String usuario, String contrasena) throws MessagingException {
        String remitente = "tuemail@gmail.com"; // Cambia por tu correo
        String clave = "tuClaveApp"; // Cambia por tu clave de app o contraseña

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("Bienvenido a la aplicación");

        String contenido = "Hola " + usuario + ",\n\n" +
                "Tu cuenta ha sido creada exitosamente.\n" +
                "Usuario: " + usuario + "\n" +
                "Contraseña: " + contrasena + "\n\n" +
                "Por seguridad, te recomendamos cambiar tu contraseña en el primer inicio de sesión.\n\n" +
                "Saludos,\nEquipo de soporte";

        message.setText(contenido);

        Transport.send(message);
    }

    public List<ModeloUsuario> getUsuarios() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        conn.disconnect();

        List<ModeloUsuario> usuarios = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            ModeloUsuario usuario = new ModeloUsuario();
            usuario.setIdUsuario(jsonObj.getInt("idUsuario"));
            usuario.setNombre(jsonObj.getString("nombre"));
            usuario.setApellido(jsonObj.getString("apellido"));
            usuario.setUsername(jsonObj.getString("username"));
            usuario.setPass(jsonObj.getString("pass"));
            usuario.setCorreo(jsonObj.optString("correo", ""));
            usuario.setRol(jsonObj.getString("rol"));
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public ModeloUsuario getUsuario(int id) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        if (conn.getResponseCode() == 404) {
            return null;
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        conn.disconnect();

        JSONObject jsonObj = new JSONObject(response.toString());
        ModeloUsuario usuario = new ModeloUsuario();
        usuario.setIdUsuario(jsonObj.getInt("idUsuario"));
        usuario.setNombre(jsonObj.getString("nombre"));
        usuario.setApellido(jsonObj.getString("apellido"));
        usuario.setUsername(jsonObj.getString("username"));
        usuario.setPass(jsonObj.getString("pass"));
        usuario.setCorreo(jsonObj.optString("correo", ""));
        usuario.setRol(jsonObj.getString("rol"));

        return usuario;
    }

    public ModeloUsuario crearUsuario(ModeloUsuario usuario) throws Exception {
        System.out.println("Token usado en crearUsuario: " + token);
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);

        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("nombre", usuario.getNombre());
        jsonUsuario.put("apellido", usuario.getApellido());
        jsonUsuario.put("username", usuario.getUsername());
        jsonUsuario.put("pass", usuario.getPass());
        jsonUsuario.put("correo", usuario.getCorreo());
        jsonUsuario.put("rol", usuario.getRol());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonUsuario.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() != 201) {
            throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        conn.disconnect();

        JSONObject jsonObj = new JSONObject(response.toString());
        usuario.setIdUsuario(jsonObj.getInt("idUsuario"));

        return usuario;
    }

    // Nuevo método para registrar usuario públicamente (sin token)
    public ModeloUsuario registrarUsuarioPublico(ModeloUsuario usuario) throws Exception {
        URL url = new URL(BASE_URL + "/registro-publico");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("nombre", usuario.getNombre());
        jsonUsuario.put("apellido", usuario.getApellido());
        jsonUsuario.put("username", usuario.getUsername());
        jsonUsuario.put("pass", usuario.getPass());
        jsonUsuario.put("correo", usuario.getCorreo());
        jsonUsuario.put("rol", usuario.getRol());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonUsuario.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() != 201) {
            throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        conn.disconnect();

        JSONObject jsonObj = new JSONObject(response.toString());
        usuario.setIdUsuario(jsonObj.getInt("idUsuario"));

        return usuario;
    }

    public boolean actualizarUsuario(ModeloUsuario usuario) throws Exception {
        URL url = new URL(BASE_URL + "/" + usuario.getIdUsuario());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);

        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("idUsuario", usuario.getIdUsuario());
        jsonUsuario.put("nombre", usuario.getNombre());
        jsonUsuario.put("apellido", usuario.getApellido());
        jsonUsuario.put("username", usuario.getUsername());
        jsonUsuario.put("pass", usuario.getPass());
        jsonUsuario.put("correo", usuario.getCorreo());
        jsonUsuario.put("rol", usuario.getRol());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonUsuario.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204;
    }

    public boolean eliminarUsuario(int id) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204;
    }

    public boolean recuperarContrasena(String username, String nuevaContrasena) throws Exception {
        URL url = new URL(BASE_URL + "/RecuperarContrasena");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);

        JSONObject jsonData = new JSONObject();
        jsonData.put("username", username);
        jsonData.put("nuevaContrasena", nuevaContrasena);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonData.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 200;
    }
    public void enviarCorreoBienvenidaBackend(String destinatario, String usuario, String contrasena) throws Exception {
    URL url = new URL("http://localhost:5148/api/Email/enviar-bienvenida");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    if (token != null && !token.isEmpty()) {
        conn.setRequestProperty("Authorization", "Bearer " + token);
    }
    conn.setDoOutput(true);

    JSONObject json = new JSONObject();
    json.put("Destinatario", destinatario);
    json.put("Usuario", usuario);
    json.put("Contrasena", contrasena);

    try (OutputStream os = conn.getOutputStream()) {
        byte[] input = json.toString().getBytes("utf-8");
        os.write(input, 0, input.length);
    }

    int responseCode = conn.getResponseCode();
    if (responseCode != 200) {
        throw new RuntimeException("Error al enviar correo: HTTP " + responseCode);
    }
    conn.disconnect();
}
}