package Service;

import Modelos.ModeloDocente;
import Service.TokenService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceDocente {
    private static final String BASE_URL = "http://localhost:5148/api/Docente";

    // Obtener todos los docentes
    public List<ModeloDocente> getDocentes() throws Exception {
        String token = Modelos.SesionUsuario.token;
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

        List<ModeloDocente> docentes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            ModeloDocente docente = new ModeloDocente();
            docente.setIdDocente(jsonObj.getInt("idDocente"));
            docente.setNombreDocente(jsonObj.getString("nombreDocente"));
            docente.setApellidoDocente(jsonObj.getString("apellidoDocente"));
            docente.setCorreoDocente(jsonObj.getString("correoDocente"));
            docente.setTelefonoDocente(jsonObj.getString("telefonoDocente"));
            docente.setEspecialidadDocente(jsonObj.getString("especialidadDocente"));
            docentes.add(docente);
        }

        return docentes;
    }

    // Obtener docente por ID
    public ModeloDocente getDocente(int id) throws Exception {
        String token = Modelos.SesionUsuario.token;
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
        ModeloDocente docente = new ModeloDocente();
        docente.setIdDocente(jsonObj.getInt("idDocente"));
        docente.setNombreDocente(jsonObj.getString("nombreDocente"));
        docente.setApellidoDocente(jsonObj.getString("apellidoDocente"));
        docente.setCorreoDocente(jsonObj.getString("correoDocente"));
        docente.setTelefonoDocente(jsonObj.getString("telefonoDocente"));
        docente.setEspecialidadDocente(jsonObj.getString("especialidadDocente"));

        return docente;
    }

    // Crear nuevo docente
    public ModeloDocente crearDocente(ModeloDocente docente) throws Exception {
        String token = Modelos.SesionUsuario.token;
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);

        JSONObject jsonDocente = new JSONObject();
        jsonDocente.put("nombreDocente", docente.getNombreDocente());
        jsonDocente.put("apellidoDocente", docente.getApellidoDocente());
        jsonDocente.put("correoDocente", docente.getCorreoDocente());
        jsonDocente.put("telefonoDocente", docente.getTelefonoDocente());
        jsonDocente.put("especialidadDocente", docente.getEspecialidadDocente());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonDocente.toString().getBytes("utf-8");
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
        docente.setIdDocente(jsonObj.getInt("idDocente"));

        return docente;
    }

    // Actualizar docente existente
    public boolean actualizarDocente(ModeloDocente docente) throws Exception {
        String token = Modelos.SesionUsuario.token;
        URL url = new URL(BASE_URL + "/" + docente.getIdDocente());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);

        JSONObject jsonDocente = new JSONObject();
        jsonDocente.put("idDocente", docente.getIdDocente());
        jsonDocente.put("nombreDocente", docente.getNombreDocente());
        jsonDocente.put("apellidoDocente", docente.getApellidoDocente());
        jsonDocente.put("correoDocente", docente.getCorreoDocente());
        jsonDocente.put("telefonoDocente", docente.getTelefonoDocente());
        jsonDocente.put("especialidadDocente", docente.getEspecialidadDocente());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonDocente.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204; // 204 No Content
    }

    // Eliminar docente por ID
    public boolean eliminarDocente(int id) throws Exception {
        String token = Modelos.SesionUsuario.token;
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204; // 204 No Content
    }
}