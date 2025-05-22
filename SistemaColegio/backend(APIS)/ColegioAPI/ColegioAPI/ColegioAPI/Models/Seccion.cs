using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using ColegioAPI.Models;

[Table("seccion")]
public class Seccion
{
    [Key]
    [Column("id_seccion")]
    public int IdSeccion { get; set; }

    [Required, MaxLength(10)]
    [Column("nombre_seccion")]
    public string NombreSeccion { get; set; } = string.Empty;
}
