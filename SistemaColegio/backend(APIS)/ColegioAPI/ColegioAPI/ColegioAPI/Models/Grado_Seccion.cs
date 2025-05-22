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
        [Column("fk_id_grado")]
        public int FkIdGrado { get; set; }

        [Required]
        [Column("fk_id_seccion")]
        public int FkIdSeccion { get; set; }
    }

}
