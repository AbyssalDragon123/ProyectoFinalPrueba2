using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using ColegioAPI.Models;

[Table("asignatura")]
public class Asignatura
{
    [Key] // llave primaria
    [Column("id_asignatura")] // nombre de la columna en la base de datos
    public int IdAsignatura { get; set; } // id de la asignatura

    [Required] // campo requerido
    [MaxLength(50)] // longitud maxima
    [Column("nombre_asignatura")]
    public string NombreAsignatura { get; set; } = string.Empty; 

    [Column("descripcion_asignatura")]
    public string? DescripcionAsignatura { get; set; }

    [Required]
    [Column("fk_id_docente")]
    public int FkIdDocente { get; set; }

    [ForeignKey("FkIdDocente")] // llave foranea
    public Docente Docente { get; set; } = null!; // docente que imparte la asignatura
}
