/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;


public class ModeloNotas {

    private int idNotas;
    private double notaValor;
    private String descripcion;
    
    private int fkIdAlumno;
    private int fkIdAula;
    private int fkIdDocente;
    private int fkIdAsignatura;

    public ModeloNotas() {
        // Constructor vacío
    }

    public ModeloNotas(int idNotas, double notaValor, String descripcion, int fkIdAlumno, int fkIdAula, int fkIdDocente, int fkIdAsignatura) {
        this.idNotas = idNotas;
        this.notaValor = notaValor;
        this.descripcion = descripcion;
        
        this.fkIdAlumno = fkIdAlumno;
        this.fkIdAula = fkIdAula;
        this.fkIdDocente = fkIdDocente;
        this.fkIdAsignatura = fkIdAsignatura;
    }

    public int getIdNotas() {
        return idNotas;
    }

    public void setIdNotas(int idNotas) {
        this.idNotas = idNotas;
    }

    public double getNotaValor() {
        return notaValor;
    }

    public void setNotaValor(double notaValor) {
        this.notaValor = notaValor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

 
    public int getFkIdAlumno() {
        return fkIdAlumno;
    }

    public void setFkIdAlumno(int fkIdAlumno) {
        this.fkIdAlumno = fkIdAlumno;
    }

    public int getFkIdAula() {
        return fkIdAula;
    }

    public void setFkIdAula(int fkIdAula) {
        this.fkIdAula = fkIdAula;
    }

    public int getFkIdDocente() {
        return fkIdDocente;
    }

    public void setFkIdDocente(int fkIdDocente) {
        this.fkIdDocente = fkIdDocente;
    }

    public int getFkIdAsignatura() {
        return fkIdAsignatura;
    }

    public void setFkIdAsignatura(int fkIdAsignatura) {
        this.fkIdAsignatura = fkIdAsignatura;
    }
}