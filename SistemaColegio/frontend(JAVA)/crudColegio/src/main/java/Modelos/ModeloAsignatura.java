/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Admin
 */
public class ModeloAsignatura {


    private int idAsignatura;
    private String nombreAsignatura;
    private String descripcionAsignatura;
    private int fkidDocente;
    
    //controladores

    public ModeloAsignatura(int idAsignatura, String nombreAsignatura, String descripcionAsignatura, int  fkidDocente) {
                         
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.descripcionAsignatura = descripcionAsignatura;
        this.fkidDocente = fkidDocente;
    }
    // Agregar Getter y Setter
    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getDescripcionAsignatura() {
        return descripcionAsignatura;
    }

    public void setDescripcionAsignatura(String descripcionAsignatura) {
        this.descripcionAsignatura = descripcionAsignatura;
    }

    public int getFkidDocente() {
        return fkidDocente;
    }

    public void setFkidDocente(int fkidDocente) {
        this.fkidDocente = fkidDocente;
    }
}

