using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace ColegioAPI.Models
{
    [Table("unidad")]
    public class Unidad
    {
        [Key]
        [Column("id_unidad")]
        public int IdUnidad { get; set; }

        [Required]
        [Column("unidad")]
        [StringLength(50)]
        public string NombreUnidad { get; set; } = string.Empty;
    }

}
