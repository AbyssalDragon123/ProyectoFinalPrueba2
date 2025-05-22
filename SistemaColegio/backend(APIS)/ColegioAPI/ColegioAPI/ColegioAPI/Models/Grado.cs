using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using ColegioAPI.Models;

[Table("grado")]
public class Grado
{
    [Key]
    [Column("id_grado")]
    public int IdGrado { get; set; }

    [Required, MaxLength(50)]
    [Column("nombre_grado")]
    public string NombreGrado { get; set; } = string.Empty;
}
