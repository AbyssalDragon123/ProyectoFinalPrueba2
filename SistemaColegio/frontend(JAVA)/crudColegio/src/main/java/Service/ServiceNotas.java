package Service;

import Modelos.ModeloNotas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceNotas {
    private static final String BASE_URL = "http://localhost:5148/api/Notas";

    // Ahora todos los métodos reciben el token como parámetro

    public List<ModeloNotas> getNotas(String token) throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

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

        List<ModeloNotas> notas = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            ModeloNotas nota = new ModeloNotas();
            nota.setIdNotas(jsonObj.getInt("idNotas"));
            nota.setNotaValor(jsonObj.getDouble("notaValor"));
            nota.setDescripcion(jsonObj.getString("descripcion"));
            nota.setFkIdAlumno(jsonObj.getInt("fkIdAlumno"));
            nota.setFkIdAula(jsonObj.getInt("fkIdAula"));
            nota.setFkIdDocente(jsonObj.getInt("fkIdDocente"));
            nota.setFkIdAsignatura(jsonObj.getInt("fkIdAsignatura"));
            notas.add(nota);
        }

        return notas;
    }

    public ModeloNotas getNota(int id, String token) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

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
        ModeloNotas nota = new ModeloNotas();
        nota.setIdNotas(jsonObj.getInt("idNotas"));
        nota.setNotaValor(jsonObj.getDouble("notaValor"));
        nota.setDescripcion(jsonObj.getString("descripcion"));
        nota.setFkIdAlumno(jsonObj.getInt("fkIdAlumno"));
        nota.setFkIdAula(jsonObj.getInt("fkIdAula"));
        nota.setFkIdDocente(jsonObj.getInt("fkIdDocente"));
        nota.setFkIdAsignatura(jsonObj.getInt("fkIdAsignatura"));

        return nota;
    }

    public ModeloNotas crearNota(ModeloNotas nota, String token) throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);

        JSONObject jsonNota = new JSONObject();
        jsonNota.put("notaValor", nota.getNotaValor());
        jsonNota.put("descripcion", nota.getDescripcion());
        jsonNota.put("fkIdAlumno", nota.getFkIdAlumno());
        jsonNota.put("fkIdAula", nota.getFkIdAula());
        jsonNota.put("fkIdDocente", nota.getFkIdDocente());
        jsonNota.put("fkIdAsignatura", nota.getFkIdAsignatura());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonNota.toString().getBytes("utf-8");
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
        nota.setIdNotas(jsonObj.getInt("idNotas"));

        return nota;
    }

    public boolean actualizarNota(ModeloNotas nota, String token) throws Exception {
        URL url = new URL(BASE_URL + "/" + nota.getIdNotas());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);

        JSONObject jsonNota = new JSONObject();
        jsonNota.put("idNotas", nota.getIdNotas());
        jsonNota.put("notaValor", nota.getNotaValor());
        jsonNota.put("descripcion", nota.getDescripcion());
        jsonNota.put("fkIdAlumno", nota.getFkIdAlumno());
        jsonNota.put("fkIdAula", nota.getFkIdAula());
        jsonNota.put("fkIdDocente", nota.getFkIdDocente());
        jsonNota.put("fkIdAsignatura", nota.getFkIdAsignatura());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonNota.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204;
    }

    public boolean eliminarNota(int id, String token) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204;
    }

    public List<ModeloNotas> buscarNotas(String termino, String token) throws Exception {
        List<ModeloNotas> todasNotas = getNotas(token);
        List<ModeloNotas> notasEncontradas = new ArrayList<>();

        for (ModeloNotas nota : todasNotas) {
            if (nota.getDescripcion().toLowerCase().contains(termino.toLowerCase())) {
                notasEncontradas.add(nota);
            }
        }

        return notasEncontradas;
    }

    public List<ModeloNotas> getNotasPorAlumno(int idAlumno, String token) throws Exception {
        URL url = new URL(BASE_URL + "/alumno/" + idAlumno);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

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

        List<ModeloNotas> notas = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            ModeloNotas nota = new ModeloNotas();
            nota.setIdNotas(jsonObj.getInt("idNotas"));
            nota.setNotaValor(jsonObj.getDouble("notaValor"));
            nota.setDescripcion(jsonObj.getString("descripcion"));
            nota.setFkIdAlumno(jsonObj.getInt("fkIdAlumno"));
            nota.setFkIdAula(jsonObj.getInt("fkIdAula"));
            nota.setFkIdDocente(jsonObj.getInt("fkIdDocente"));
            nota.setFkIdAsignatura(jsonObj.getInt("fkIdAsignatura"));
            notas.add(nota);
        }

        return notas;
    }
}