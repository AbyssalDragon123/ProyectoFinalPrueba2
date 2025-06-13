package Service;

import Modelos.ModeloAsignatura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import Service.TokenService;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServiceAsignatura {
    private static final String ASIGNATURA = "http://localhost:5148/api/Asignatura";
    private final Gson gson = new Gson();

    // Obtener lista de asignaturas
    public List<ModeloAsignatura> obtenerAsignatura() throws Exception {
        String token = Modelos.SesionUsuario.token;  // obtiene token de la sesión
        HttpURLConnection conn = null;
        try {
            URL url = new URL(ASIGNATURA);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error al obtener Asignatura. Código: " + responseCode);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
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
        String token = Modelos.SesionUsuario.token;  // obtiene token de la sesión
        HttpURLConnection conn = null;
        try {
            URL url = new URL(ASIGNATURA);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
            String jsonInput = gson.toJson(asignatura);
            System.out.println("JSON enviado (agregar): " + jsonInput); // Depuración

            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
                writer.write(jsonInput);
                writer.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 201 && responseCode != 200) {
                InputStream errorStream = conn.getErrorStream();
                if (errorStream != null) {
                    try (BufferedReader err = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"))) {
                        StringBuilder errorResponse = new StringBuilder();
                        String line;
                        while ((line = err.readLine()) != null) {
                            errorResponse.append(line);
                        }
                        System.out.println("Error al agregar Asignatura: " + errorResponse.toString());
                    }
                } else {
                    System.out.println("Error al agregar Asignatura: No hay mensaje de error disponible.");
                }
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
// Actualizar asignatura
public boolean actualizarAsignatura(ModeloAsignatura asignatura) throws Exception {
    HttpURLConnection conn = null;
    String token = Modelos.SesionUsuario.token;  // obtiene token de la sesión
    try {
        // Validar datos antes de enviar
        System.out.println("Datos a enviar:");
        System.out.println("ID: " + asignatura.getIdAsignatura());
        System.out.println("Nombre: " + asignatura.getNombreAsignatura());
        System.out.println("Descripción: " + asignatura.getDescripcionAsignatura());
        System.out.println("ID Docente: " + asignatura.getFkIdDocente());

        URL url = new URL(ASIGNATURA + "/" + asignatura.getIdAsignatura());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
       if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        String jsonInput = gson.toJson(asignatura);
        System.out.println("JSON enviado (actualizar): " + jsonInput);

        try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")) {
            writer.write(jsonInput);
            writer.flush();
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Código de respuesta HTTP: " + responseCode);

        // Aceptar 200 y 204 como éxito
        if (responseCode != 200 && responseCode != 204) {
            InputStream errorStream = conn.getErrorStream();
            if (errorStream != null) {
                try (BufferedReader err = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = err.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    System.out.println("Error al actualizar Asignatura: " + errorResponse.toString());
                }
            } else {
                System.out.println("Error al actualizar Asignatura: No hay mensaje de error disponible.");
            }
            return false;
        }

        return true;

    } finally {
        if (conn != null) {
            conn.disconnect();
        }
    }
}
public ModeloAsignatura obtenerAsignaturaPorId(int idAsignatura) throws Exception {
    String token = Modelos.SesionUsuario.token;
    HttpURLConnection conn = null;
    try {
        URL url = new URL(ASIGNATURA + "/" + idAsignatura);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Error al obtener Asignatura por ID. Código: " + responseCode);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return gson.fromJson(response.toString(), ModeloAsignatura.class);
        }
    } finally {
        if (conn != null) {
            conn.disconnect();
        }
    }
}
    // Eliminar asignatura
    public boolean eliminarAsignatura(int idAsignatura) throws Exception {
        HttpURLConnection conn = null;
        String token = Modelos.SesionUsuario.token;  // obtiene token de la sesión
        try {
            URL url = new URL(ASIGNATURA + "/" + idAsignatura);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

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