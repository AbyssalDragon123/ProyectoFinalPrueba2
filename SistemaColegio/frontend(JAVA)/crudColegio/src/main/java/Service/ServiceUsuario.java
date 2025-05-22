package Service;

import Modelos.ModeloUsuario;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceUsuario {
    private static final String BASE_URL = "http://localhost:5148/api/Usuario";
    
    /**
     * Obtiene todos los usuarios desde la API
     * @return Lista de usuarios
     * @throws Exception Si ocurre un error en la comunicación
     */
    public List<ModeloUsuario> getUsuarios() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
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
        
        // Parsear JSON a lista de usuarios
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
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     * @throws Exception Si ocurre un error en la comunicación
     */
    public ModeloUsuario getUsuario(int id) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
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
        
        // Parsear JSON a usuario
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
    
    /**
     * Crea un nuevo usuario
     * @param usuario Usuario a crear
     * @return Usuario creado con su ID asignado
     * @throws Exception Si ocurre un error en la comunicación
     */
    public ModeloUsuario crearUsuario(ModeloUsuario usuario) throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        
        // Crear JSON del usuario
        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("nombre", usuario.getNombre());
        jsonUsuario.put("apellido", usuario.getApellido());
        jsonUsuario.put("username", usuario.getUsername());
        jsonUsuario.put("pass", usuario.getPass());
        jsonUsuario.put("correo", usuario.getCorreo());
        jsonUsuario.put("rol", usuario.getRol());
        
        // Enviar JSON
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonUsuario.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // Verificar respuesta
        if (conn.getResponseCode() != 201) {
            throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
        }
        
        // Leer respuesta
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        conn.disconnect();
        
        // Parsear JSON a usuario
        JSONObject jsonObj = new JSONObject(response.toString());
        usuario.setIdUsuario(jsonObj.getInt("idUsuario"));
        
        return usuario;
    }
    
    /**
     * Actualiza un usuario existente
     * @param usuario Usuario con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     * @throws Exception Si ocurre un error en la comunicación
     */
    public boolean actualizarUsuario(ModeloUsuario usuario) throws Exception {
        URL url = new URL(BASE_URL + "/" + usuario.getIdUsuario());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        
        // Crear JSON del usuario
        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("idUsuario", usuario.getIdUsuario());
        jsonUsuario.put("nombre", usuario.getNombre());
        jsonUsuario.put("apellido", usuario.getApellido());
        jsonUsuario.put("username", usuario.getUsername());
        jsonUsuario.put("pass", usuario.getPass());
        jsonUsuario.put("correo", usuario.getCorreo());
        jsonUsuario.put("rol", usuario.getRol());
        
        // Enviar JSON
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonUsuario.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // Verificar respuesta
        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204; // 204 No Content
    }
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     * @throws Exception Si ocurre un error en la comunicación
     */
    public boolean eliminarUsuario(int id) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        
        // Verificar respuesta
        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204; // 204 No Content
    }
    
    /**
     * Recupera la contraseña de un usuario
     * @param username Nombre de usuario
     * @param nuevaContrasena Nueva contraseña
     * @return true si se recuperó correctamente, false en caso contrario
     * @throws Exception Si ocurre un error en la comunicación
     */
    public boolean recuperarContrasena(String username, String nuevaContrasena) throws Exception {
        URL url = new URL(BASE_URL + "/RecuperarContrasena");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        
        // Crear JSON para recuperar contraseña
        JSONObject jsonData = new JSONObject();
        jsonData.put("username", username);
        jsonData.put("nuevaContrasena", nuevaContrasena);
        
        // Enviar JSON
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonData.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // Verificar respuesta
        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 200; // 200 OK
    }
    
    /**
     * Verifica las credenciales de un usuario para el login
     * @param username Nombre de usuario
     * @param password Contraseña
     * @return Usuario si las credenciales son correctas, null en caso contrario
     * @throws Exception Si ocurre un error en la comunicación
     */
    public ModeloUsuario login(String username, String password) throws Exception {
        // Obtener todos los usuarios
        List<ModeloUsuario> usuarios = getUsuarios();
        
        // Buscar coincidencia de usuario y contraseña
        for (ModeloUsuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPass().equals(password)) {
                return usuario; // Usuario encontrado
            }
        }
        
        return null; // Usuario no encontrado o credenciales incorrectas
    }
}