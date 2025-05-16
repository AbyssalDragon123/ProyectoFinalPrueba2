using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using ColegioAPI.Models;

[Table("asignatura")]
public class Asignatura
{
    [Key]
    [Column("id_asignatura")]
    public int IdAsignatura { get; set; }

    [Required]
    [MaxLength(50)]
    [Column("nombre_asignatura")]
    public string NombreAsignatura { get; set; } = string.Empty;

    [Column("descripcion_asignatura")]
    public string? DescripcionAsignatura { get; set; }

    [Required]
    [Column("fk_id_docente")]
    public int FkIdDocente { get; set; }

    [ForeignKey("FkIdDocente")]
    public Docente Docente { get; set; } = null!;
}
