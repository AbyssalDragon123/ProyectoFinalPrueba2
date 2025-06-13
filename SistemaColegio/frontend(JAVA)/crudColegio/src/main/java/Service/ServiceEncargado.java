package Service;

import Modelos.ModeloEncargado;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceEncargado {

    private static final String BASE_URL = "http://localhost:5148/api/Encargado";

    // CREAR ENCARGADO
    public ModeloEncargado crearEncargado(ModeloEncargado encargado, String token) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            // Crear JSON con todos los campos
            JSONObject jsonEncargado = new JSONObject();
            jsonEncargado.put("nombreEncargado", encargado.getNombreEncargado());
            jsonEncargado.put("apellidoEncargado", encargado.getApellidoEncargado());
            jsonEncargado.put("correoEncargado", encargado.getCorreoEncargado());
            jsonEncargado.put("telefonoEncargado", encargado.getTelefonoEncargado());
            jsonEncargado.put("direccionEncargado", encargado.getDireccionEncargado());
            jsonEncargado.put("dpiEncargado", encargado.getDpiEncargado());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonEncargado.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() != 201 && conn.getResponseCode() != 200) {
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(response.toString());
            ModeloEncargado nuevoEncargado = new ModeloEncargado();
            nuevoEncargado.setIdEncargado(jsonResponse.getInt("idEncargado"));
            nuevoEncargado.setNombreEncargado(jsonResponse.getString("nombreEncargado"));
            nuevoEncargado.setApellidoEncargado(jsonResponse.getString("apellidoEncargado"));
            nuevoEncargado.setCorreoEncargado(jsonResponse.getString("correoEncargado"));
            nuevoEncargado.setTelefonoEncargado(jsonResponse.getString("telefonoEncargado"));
            nuevoEncargado.setDireccionEncargado(jsonResponse.getString("direccionEncargado"));
            nuevoEncargado.setDpiEncargado(jsonResponse.getString("dpiEncargado"));

            return nuevoEncargado;

        } catch (Exception e) {
            System.out.println("Error al crear encargado: " + e.getMessage());
            return null;
        }
    }

    // OBTENER TODOS LOS ENCARGADOS
    public List<ModeloEncargado> obtenerEncargados(String token) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();

            JSONArray jsonArray = new JSONArray(response.toString());
            List<ModeloEncargado> lista = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                ModeloEncargado encargado = new ModeloEncargado();
                encargado.setIdEncargado(jsonObj.getInt("idEncargado"));
                encargado.setNombreEncargado(jsonObj.getString("nombreEncargado"));
                encargado.setApellidoEncargado(jsonObj.getString("apellidoEncargado"));
                encargado.setCorreoEncargado(jsonObj.getString("correoEncargado"));
                encargado.setTelefonoEncargado(jsonObj.getString("telefonoEncargado"));
                encargado.setDireccionEncargado(jsonObj.getString("direccionEncargado"));
                encargado.setDpiEncargado(jsonObj.getString("dpiEncargado"));
                lista.add(encargado);
            }

            return lista;

        } catch (Exception e) {
            System.out.println("Error al obtener encargados: " + e.getMessage());
            return null;
        }
    }

    // OBTENER UN ENCARGADO POR ID
    public ModeloEncargado getEncargado(int id, String token) {
        try {
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

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();

            JSONObject jsonObj = new JSONObject(response.toString());
            ModeloEncargado encargado = new ModeloEncargado();
            encargado.setIdEncargado(jsonObj.getInt("idEncargado"));
            encargado.setNombreEncargado(jsonObj.getString("nombreEncargado"));
            encargado.setApellidoEncargado(jsonObj.getString("apellidoEncargado"));
            encargado.setCorreoEncargado(jsonObj.getString("correoEncargado"));
            encargado.setTelefonoEncargado(jsonObj.getString("telefonoEncargado"));
            encargado.setDireccionEncargado(jsonObj.getString("direccionEncargado"));
            encargado.setDpiEncargado(jsonObj.getString("dpiEncargado"));

            return encargado;

        } catch (Exception e) {
            System.out.println("Error al obtener encargado por ID: " + e.getMessage());
            return null;
        }
    }

    // ACTUALIZAR ENCARGADO
    public boolean actualizarEncargado(ModeloEncargado encargado, String token) {
        try {
            URL url = new URL(BASE_URL + "/" + encargado.getIdEncargado());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            JSONObject jsonEncargado = new JSONObject();
            jsonEncargado.put("idEncargado", encargado.getIdEncargado());
            jsonEncargado.put("nombreEncargado", encargado.getNombreEncargado());
            jsonEncargado.put("apellidoEncargado", encargado.getApellidoEncargado());
            jsonEncargado.put("correoEncargado", encargado.getCorreoEncargado());
            jsonEncargado.put("telefonoEncargado", encargado.getTelefonoEncargado());
            jsonEncargado.put("direccionEncargado", encargado.getDireccionEncargado());
            jsonEncargado.put("dpiEncargado", encargado.getDpiEncargado());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonEncargado.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            conn.disconnect();
            return code == 200 || code == 204;

        } catch (Exception e) {
            System.out.println("Error al actualizar encargado: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR ENCARGADO
    public boolean eliminarEncargado(int idEncargado, String token) {
        try {
            URL url = new URL(BASE_URL + "/" + idEncargado);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int code = conn.getResponseCode();
            conn.disconnect();
            return code == 200 || code == 204;

        } catch (Exception e) {
            System.out.println("Error al eliminar encargado: " + e.getMessage());
            return false;
        }
    }
}