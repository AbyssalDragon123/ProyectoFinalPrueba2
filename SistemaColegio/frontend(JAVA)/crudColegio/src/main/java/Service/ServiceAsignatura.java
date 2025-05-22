package Service;

import Modelos.ModeloAsignatura;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName; // Importa esto si lo necesitas
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter; // Importa esto
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServiceAsignatura {
    private static final String ASIGNATURA = "http://localhost:5148/api/Asignatura";
    private final Gson gson = new Gson();

    // Obtener lista de asignaturas
    public List<ModeloAsignatura> obtenerAsignatura() throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(ASIGNATURA);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error al obtener Asignatura. Código: " + responseCode);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Type listType = new TypeToken<List<ModeloAsignatura>>() {}.getType();
                return gson.fromJson(response.toString(), listType);
            }

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    // Agregar Asignatura
    public boolean agregarAsignatura(ModeloAsignatura asignatura) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(ASIGNATURA);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = gson.toJson(asignatura);
            System.out.println("JSON enviado (agregar): " + jsonInput); // Depuración

            // Usar OutputStreamWriter con UTF-8
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
                writer.write(jsonInput);
                writer.flush();
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
                return false;
            }

            return true;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    // Actualizar asignatura
    public boolean actualizarAsignatura(ModeloAsignatura asignatura) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(ASIGNATURA + "/" + asignatura.getIdAsignatura());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = gson.toJson(asignatura);
            System.out.println("JSON enviado (actualizar): " + jsonInput); // Depuración

            // Usar OutputStreamWriter con UTF-8
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
                writer.write(jsonInput);
                writer.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = err.readLine()) != null) {
                    errorResponse.append(line);
                }
                err.close();
                System.out.println("Error al actualizar Asignatura: " + errorResponse.toString());
                return false;
            }

            return true;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    // Eliminar asignatura
    public boolean eliminarAsignatura(int idAsignatura) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(ASIGNATURA + "/" + idAsignatura);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200 && responseCode != 204) {
                System.out.println("Error al eliminar Asignatura. Código: " + responseCode);
                return false;
            }

            return true;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}