/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author osbel
 */
public class ModeloAlumno {
    private int idAlumno;
    private String nombreAlumno;
    private String apellidoAlumno;
    private String generoAlumno;
    private int fkIdEncargado;
    private int fkIdAula;
    
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getApellidoAlumno() {
        return apellidoAlumno;
    }

    public void setApellidoAlumno(String apellidoAlumno) {
        this.apellidoAlumno = apellidoAlumno;
    }

    public String getGeneroAlumno() {
        return generoAlumno;
    }

    public void setGeneroAlumno(String generoAlumno) {
        this.generoAlumno = generoAlumno;
    }

    public int getFkIdEncargado() {
        return fkIdEncargado;
    }

    public void setFkIdEncargado(int fkIdEncargado) {
        this.fkIdEncargado = fkIdEncargado;
    }

    public int getFkIdAula() {
        return fkIdAula;
    }

    public void setFkIdAula(int fkIdAula) {
        this.fkIdAula = fkIdAula;
    }
}

