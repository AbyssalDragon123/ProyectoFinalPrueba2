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

        [Required]
        [MaxLength(50)]
        [Column("unidad")]
        public string NombreUnidad { get; set; } = string.Empty;
    }
}

