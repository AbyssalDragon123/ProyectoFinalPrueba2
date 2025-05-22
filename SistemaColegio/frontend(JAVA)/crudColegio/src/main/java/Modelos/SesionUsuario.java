/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Admin
 */
public class SesionUsuario {
        // Variables estáticas para mantener la información de sesión
    public static int idUsuario;
    public static String nombreUsuario;
    public static String rol;
    
    // Método para limpiar la sesión al cerrar
    public static void limpiarSesion() {
        idUsuario = 0;
        nombreUsuario = null;
        rol = null;
    }
            
}
