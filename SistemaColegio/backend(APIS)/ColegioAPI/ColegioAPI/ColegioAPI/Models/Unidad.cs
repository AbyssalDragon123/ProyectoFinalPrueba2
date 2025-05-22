using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    [Table("unidad")]
    public class Unidad
    {
        [Key]
        [Column("id_unidad")]
        public int IdUnidad { get; set; }

        [Required, MaxLength(100)]
        [Column("nombre_unidad")]
        public string NombreUnidad { get; set; } = string.Empty;

        [Column("descripcion")]
        public string? Descripcion { get; set; }

        [Required]
        [Column("fk_id_asignatura")]
        public int FkIdAsignatura { get; set; }
    }
}

