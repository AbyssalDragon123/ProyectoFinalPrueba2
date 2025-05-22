package Modelos;

public class ModeloAsignatura {

    private int idAsignatura;
    private String nombreAsignatura;
    private String descripcionAsignatura;
    private int fkIdDocente;
    private ModeloDocente docente; // Nuevo campo

    public ModeloAsignatura() {}

    public ModeloAsignatura(int idAsignatura, String nombreAsignatura, String descripcionAsignatura, int fkIdDocente, ModeloDocente docente) {
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.descripcionAsignatura = descripcionAsignatura;
        this.fkIdDocente = fkIdDocente;
        this.docente = docente;
    }

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

    public int getFkIdDocente() {
        return fkIdDocente;
    }

    public void setFkIdDocente(int fkIdDocente) {
        this.fkIdDocente = fkIdDocente;
    }

    public ModeloDocente getDocente() {
        return docente;
    }

    public void setDocente(ModeloDocente docente) {
        this.docente = docente;
    }
}