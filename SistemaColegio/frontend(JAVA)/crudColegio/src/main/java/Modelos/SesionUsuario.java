package Modelos;

public class SesionUsuario {
    public static int idUsuario;
    public static String nombreUsuario;
    public static String rol;
    public static String token;

    public static void limpiarSesion() {
        idUsuario = 0;
        nombreUsuario = null;
        rol = null;
        token = null;
    }

}
