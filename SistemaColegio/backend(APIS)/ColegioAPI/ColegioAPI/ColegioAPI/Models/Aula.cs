using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    [Table("aula")]
    public class Aula
    {
        [Key]
        [Column("id_aula")]
        public int IdAula { get; set; }

        [Required]
        [Column("grado")]
        public string Grado { get; set; }

        [Required]
        [Column("seccion")]
        public string Seccion { get; set; }
    }

}
