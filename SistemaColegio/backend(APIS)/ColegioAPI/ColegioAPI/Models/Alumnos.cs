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

    [Required]
    [Column("fk_id_encargado")]
    public int FkIdEncargado { get; set; }

    [ForeignKey("FkIdEncargado")]
    public Encargado Encargado { get; set; } = null!;

    [Required]
    [Column("fk_id_grado_seccion")]
    public int FkIdGradoSeccion { get; set; }

    [ForeignKey("FkIdGradoSeccion")]
    public Grado_Seccion GradoSeccion { get; set; } = null!;
}
