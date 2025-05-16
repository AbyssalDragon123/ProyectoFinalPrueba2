using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    [Table("grado_seccion")]
    public class Grado_Seccion
    {
        [Key]
        [Column("id_grado_seccion")]
        public int IdGradoSeccion { get; set; }

        [Required]
        [MaxLength(50)]
        [Column("grado")]
        public string Grado { get; set; } = string.Empty;

        [Required]
        [MaxLength(10)]
        [Column("seccion")]
        public string Seccion { get; set; } = string.Empty;
    }
}

