package Service;

import Modelos.ModeloAula;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceAula {
    private static final String BASE_URL = "http://localhost:5148/api/Aulas";
    
    public List<ModeloAula> getAulas(String token) throws Exception {
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
        
        List<ModeloAula> aulas = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response.toString());
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            ModeloAula aula = new ModeloAula();
            aula.setIdAula(jsonObj.getInt("idAula"));
            aula.setGrado(jsonObj.getString("grado"));
            aula.setSeccion(jsonObj.getString("seccion"));
            aulas.add(aula);
        }
        
        return aulas;
    }
    
    public ModeloAula getAula(int id, String token) throws Exception {
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
        ModeloAula aula = new ModeloAula();
        aula.setIdAula(jsonObj.getInt("idAula"));
        aula.setGrado(jsonObj.getString("grado"));
        aula.setSeccion(jsonObj.getString("seccion"));
        
        return aula;
    }
    
    public ModeloAula crearAula(ModeloAula aula, String token) throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);
        
        JSONObject jsonAula = new JSONObject();
        jsonAula.put("grado", aula.getGrado());
        jsonAula.put("seccion", aula.getSeccion());
        
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonAula.toString().getBytes("utf-8");
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
        aula.setIdAula(jsonObj.getInt("idAula"));
        
        return aula;
    }
    
    public boolean actualizarAula(ModeloAula aula, String token) throws Exception {
        URL url = new URL(BASE_URL + "/" + aula.getIdAula());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);
        
        JSONObject jsonAula = new JSONObject();
        jsonAula.put("idAula", aula.getIdAula());
        jsonAula.put("grado", aula.getGrado());
        jsonAula.put("seccion", aula.getSeccion());
        
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonAula.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204;
    }
    
    public boolean eliminarAula(int id, String token) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        
        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == 204;
    }
    
    public List<ModeloAula> buscarPorGrado(String grado, String token) throws Exception {
        List<ModeloAula> todasLasAulas = getAulas(token);
        List<ModeloAula> aulasFiltradas = new ArrayList<>();
        
        for (ModeloAula aula : todasLasAulas) {
            if (aula.getGrado().toLowerCase().contains(grado.toLowerCase())) {
                aulasFiltradas.add(aula);
            }
        }
        
        return aulasFiltradas;
    }
    
    public List<ModeloAula> buscarPorSeccion(String seccion, String token) throws Exception {
        List<ModeloAula> todasLasAulas = getAulas(token);
        List<ModeloAula> aulasFiltradas = new ArrayList<>();
        
        for (ModeloAula aula : todasLasAulas) {
            if (aula.getSeccion().toLowerCase().contains(seccion.toLowerCase())) {
                aulasFiltradas.add(aula);
            }
        }
        
        return aulasFiltradas;
    }
    
    public boolean existeAula(String grado, String seccion, String token) throws Exception {
        List<ModeloAula> aulas = getAulas(token);
        
        for (ModeloAula aula : aulas) {
            if (aula.getGrado().equalsIgnoreCase(grado) && 
                aula.getSeccion().equalsIgnoreCase(seccion)) {
                return true;
            }
        }
        
        return false;
    }
}