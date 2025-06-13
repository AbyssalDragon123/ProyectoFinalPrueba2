using ColegioAPI.Models;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

[Table("alumno")] // Table name in the database 
public class Alumnos
{
    [Key]
    [Column("id_alumno")]
    public int IdAlumno { get; set; }

    [Required]
    [MaxLength(50)]
    [Column("nombre_alumno")]
    public string NombreAlumno { get; set; } = string.Empty;

    [Required]
    [MaxLength(50)]
    [Column("apellido_alumno")]
    public string ApellidoAlumno { get; set; } = string.Empty;

    [Required]
    [MaxLength(50)]
    [Column("genero_alumno")]
    public string GeneroAlumno { get; set; } = string.Empty;

    [Column("fk_id_encargado")]
    public int? FkIdEncargado { get; set; } // Changed to nullable int



    [Required]
    [Column("fk_id_aula")]
    public int FkIdAula { get; set; }
}