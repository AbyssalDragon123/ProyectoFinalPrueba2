package Modelos;

public class ModeloRecuperarContrasena {
    private String correo;
    private String pass;
    private String resetCode; // Código para recuperación

    // Constructor vacío (recomendado para deserialización)
    public ModeloRecuperarContrasena() {
    }

    // Constructor con todos los campos
    public ModeloRecuperarContrasena(String correo, String pass, String resetCode) {
        this.correo = correo;
        this.pass = pass;
        this.resetCode = resetCode;
    }

    // Constructor con correo y pass (por si solo quieres cambiar contraseña sin código)
    public ModeloRecuperarContrasena(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }

    // Getters y setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }
}