package Service;

import Modelos.ModeloEstudiantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



public class ServiceAlumno {
   
    private static final String Alumnos = "http://localhost:5148/api/Alumno"; // URL corregida (http)
    private final Gson gson = new Gson();

    // Obtener lista de Alumnos
    public List<Modelos.ModeloEstudiantes> obtenerAlumnos() throws Exception {
        URL url = new URL(Alumnos);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Error al obtener Alumnos. Código: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Type listType = new TypeToken<List<ModeloEstudiantes>>() {}.getType();
        return gson.fromJson(response.toString(), listType);
    }

    // Agregar Alumno
    
    public boolean agregarAlumno(ModeloEstudiantes alumnos) throws Exception {
        URL url = new URL(Alumnos);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput = gson.toJson(alumnos);
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
            System.out.println("Error al agregar Alumno: " + errorResponse.toString());
        }

        return responseCode == 201 || responseCode == 200;
    }

    // Actualizar cliente
    public boolean actualizarCliente(ModeloEstudiantes alumnos) throws Exception {
        URL url = new URL(Alumnos + "/" + alumnos.getIdAlumno());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput = gson.toJson(alumnos);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.writeBytes(jsonInput);
            wr.flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            System.out.println("Error al actualizar Alumno. Código: " + responseCode);
        }

        return responseCode == 200;
    }

    // Eliminar cliente
    public boolean eliminarAlumno(int idAlumno) throws Exception {
        URL url = new URL(Alumnos + "/" + idAlumno );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200 && responseCode != 204) {
            System.out.println("Error al eliminar Alumno. Código: " + responseCode);
        }

        return responseCode == 200 || responseCode == 204;
    }
    
}