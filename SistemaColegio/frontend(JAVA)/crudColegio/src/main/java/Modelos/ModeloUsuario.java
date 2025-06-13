package Modelos;

public class ModeloUsuario {
    public ModeloUsuario() {
    }

    public ModeloUsuario(int idUsuario, String nombre, String apellido, String username, String pass, String correo, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.pass = pass;
        this.correo = correo;
        this.rol = rol;
    }

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String username;
    private String pass;
    private String correo;
    private String rol;

    public ModeloUsuario(String nombre, String apellido, String username, String pass, String correo, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.pass = pass;
        this.correo = correo;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "ModeloUsuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}