/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Modelos.ModeloAsignatura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ServiceAsignatura {
    private static final String ASIGNATURA = "http://localhost:5148/api/Asignatura";
    private  final Gson gson = new Gson();
    
    // Obtener lista de clientes
    public List<ModeloAsignatura> obtenerAsignatura() throws Exception {
        URL url = new URL(ASIGNATURA);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Error al obtener Asignatura. Código: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Type listType = new TypeToken<List<ModeloAsignatura>>() {}.getType();
        return gson.fromJson(response.toString(), listType);
    }
    
    // Agregar Asignatura
    public boolean agregarAsignatura(ModeloAsignatura asignatura) throws Exception {
        URL url = new URL(ASIGNATURA);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput = gson.toJson(asignatura);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.writeBytes(jsonInput);
            wr.flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 201 && responseCode != 200) {
            BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = err.readLine()) != null) {
                errorResponse.append(line);
            }
            err.close();
            System.out.println("Error al agregar Asignatura: " + errorResponse.toString());
        }

        return responseCode == 201 || responseCode == 200;
    }
    
    // Actualizar cliente
    public boolean actualizarAsignatura(ModeloAsignatura asignatura) throws Exception {
        URL url = new URL(ASIGNATURA + "/" + asignatura.getIdAsignatura());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput = gson.toJson(asignatura);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.writeBytes(jsonInput);
            wr.flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            System.out.println("Error al actualizar Asignatura. Código: " + responseCode);
        }

        return responseCode == 200;
    }
    
    // Eliminar cliente
    public boolean eliminarAsignatura(int idAsignatura) throws Exception {
        URL url = new URL(ASIGNATURA + "/" + idAsignatura);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200 && responseCode != 204) {
            System.out.println("Error al eliminar Asignatura. Código: " + responseCode);
        }

        return responseCode == 200 || responseCode == 204;
    }
}
