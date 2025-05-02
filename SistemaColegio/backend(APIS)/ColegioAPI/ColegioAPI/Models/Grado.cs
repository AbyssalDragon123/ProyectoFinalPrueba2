using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace ColegioAPI.Models
{
    [Table("grado")]
    public class Grado
    {
        [Key]
        [Column("id_grado")]
        public int IdGrado { get; set; }

        [Required]
        [Column("grado")]
        [StringLength(50)]
        public string NombreGrado { get; set; } = string.Empty;

        [Required]
        [Column("seccion")]
        [StringLength(10)]
        public string Seccion { get; set; } = string.Empty;
    }

}
