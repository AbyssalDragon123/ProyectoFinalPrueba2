package Service;

import Modelos.ModeloAlumno;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceAlumno {
    private static final String BASE_URL = "http://localhost:5148/api/Alumnos";

    /**
     * Obtiene todos los alumnos desde la API
     * @param token Token JWT para autorización
     * @return Lista de alumnos
     * @throws Exception Si ocurre un error en la comunicación
     */
    public List<ModeloAlumno> getAlumnos(String token) throws Exception {
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

        List<ModeloAlumno> alumnos = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            ModeloAlumno alumno = new ModeloAlumno();
            alumno.setIdAlumno(jsonObj.getInt("idAlumno"));
            alumno.setNombreAlumno(jsonObj.getString("nombreAlumno"));
            alumno.setApellidoAlumno(jsonObj.getString("apellidoAlumno"));
            alumno.setGeneroAlumno(jsonObj.getString("generoAlumno"));
            alumno.setFkIdEncargado(jsonObj.getInt("fkIdEncargado"));
            alumno.setFkIdAula(jsonObj.getInt("fkIdAula"));
            alumnos.add(alumno);
        }

        return alumnos;
    }

    /**
     * Obtiene un alumno por su ID
     * @param id ID del alumno
     * @param token Token JWT para autorización
     * @return Alumno encontrado o null si no existe
     * @throws Exception Si ocurre un error en la comunicación
     */
    public ModeloAlumno getAlumno(int id, String token) throws Exception {
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
        ModeloAlumno alumno = new ModeloAlumno();
        alumno.setIdAlumno(jsonObj.getInt("idAlumno"));
        alumno.setNombreAlumno(jsonObj.getString("nombreAlumno"));
        alumno.setApellidoAlumno(jsonObj.getString("apellidoAlumno"));
        alumno.setGeneroAlumno(jsonObj.getString("generoAlumno"));
        alumno.setFkIdEncargado(jsonObj.getInt("fkIdEncargado"));
        alumno.setFkIdAula(jsonObj.getInt("fkIdAula"));

        return alumno;
    }

    /**
     * Crea un nuevo alumno
     * @param alumno Alumno a crear
     * @param token Token JWT para autorización
     * @return Alumno creado con su ID asignado
     * @throws Exception Si ocurre un error en la comunicación
     */
    public ModeloAlumno crearAlumno(ModeloAlumno alumno, String token) throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);

        JSONObject jsonAlumno = new JSONObject();
        jsonAlumno.put("nombreAlumno", alumno.getNombreAlumno());
        jsonAlumno.put("apellidoAlumno", alumno.getApellidoAlumno());
        jsonAlumno.put("generoAlumno", alumno.getGeneroAlumno());
        jsonAlumno.put("fkIdEncargado", alumno.getFkIdEncargado());
        jsonAlumno.put("fkIdAula", alumno.getFkIdAula());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonAlumno.toString().getBytes("utf-8");
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
        alumno.setIdAlumno(jsonObj.getInt("idAlumno"));

        return alumno;
    }

    /**
     * Actualiza un alumno existente
     * @param alumno Alumno con los datos actualizados
     * @param token Token JWT para autorización
     * @return true si se actualizó correctamente, false en caso contrario
     * @throws Exception Si ocurre un error en la comunicación
     */
    public boolean actualizarAlumno(ModeloAlumno alumno, String token) throws Exception {
        URL url = new URL(BASE_URL + "/" + alumno.getIdAlumno());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);

        JSONObject jsonAlumno = new JSONObject();
        jsonAlumno.put("idAlumno", alumno.getIdAlumno());
        jsonAlumno.put("nombreAlumno", alumno.getNombreAlumno());
        jsonAlumno.put("apellidoAlumno", alumno.getApellidoAlumno());
        jsonAlumno.put("generoAlumno", alumno.getGeneroAlumno());
        jsonAlumno.put("fkIdEncargado", alumno.getFkIdEncargado());
        jsonAlumno.put("fkIdAula", alumno.getFkIdAula());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonAlumno.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204;
    }

    /**
     * Elimina un alumno por su ID
     * @param id ID del alumno a eliminar
     * @param token Token JWT para autorización
     * @return true si se eliminó correctamente, false en caso contrario
     * @throws Exception Si ocurre un error en la comunicación
     */
    public boolean eliminarAlumno(int id, String token) throws Exception {
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

    /**
     * Busca alumnos por nombre o apellido
     * @param termino Término de búsqueda
     * @param token Token JWT para autorización
     * @return Lista de alumnos que coinciden con el término
     * @throws Exception Si ocurre un error en la comunicación
     */
    public List<ModeloAlumno> buscarAlumnos(String termino, String token) throws Exception {
        List<ModeloAlumno> todosAlumnos = getAlumnos(token);
        List<ModeloAlumno> alumnosEncontrados = new ArrayList<>();

        for (ModeloAlumno alumno : todosAlumnos) {
            if (alumno.getNombreAlumno().toLowerCase().contains(termino.toLowerCase()) ||
                alumno.getApellidoAlumno().toLowerCase().contains(termino.toLowerCase())) {
                alumnosEncontrados.add(alumno);
            }
        }

        return alumnosEncontrados;
    }

    /**
     * Obtiene alumnos por aula
     * @param idAula ID del aula
     * @param token Token JWT para autorización
     * @return Lista de alumnos que pertenecen al aula
     * @throws Exception Si ocurre un error en la comunicación
     */
    public List<ModeloAlumno> getAlumnosPorAula(int idAula, String token) throws Exception {
        List<ModeloAlumno> todosAlumnos = getAlumnos(token);
        List<ModeloAlumno> alumnosPorAula = new ArrayList<>();

        for (ModeloAlumno alumno : todosAlumnos) {
            if (alumno.getFkIdAula() == idAula) {
                alumnosPorAula.add(alumno);
            }
        }

        return alumnosPorAula;
    }

    /**
     * Obtiene alumnos por encargado
     * @param idEncargado ID del encargado
     * @param token Token JWT para autorización
     * @return Lista de alumnos que tienen el mismo encargado
     * @throws Exception Si ocurre un error en la comunicación
     */
    public List<ModeloAlumno> getAlumnosPorEncargado(int idEncargado, String token) throws Exception {
        List<ModeloAlumno> todosAlumnos = getAlumnos(token);
        List<ModeloAlumno> alumnosPorEncargado = new ArrayList<>();

        for (ModeloAlumno alumno : todosAlumnos) {
            if (alumno.getFkIdEncargado() == idEncargado) {
                alumnosPorEncargado.add(alumno);
            }
        }

        return alumnosPorEncargado;
    }
}