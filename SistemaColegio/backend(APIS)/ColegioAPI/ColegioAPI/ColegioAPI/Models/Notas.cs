using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace ColegioAPI.Models
{
    [Table("notas")]
    public class Notas
    {
        [Key]
        [Column("id_notas")]
        public int IdNotas { get; set; }

        [Required]
        [Column("nota")]
        public decimal NotaValor { get; set; }

        [Required]
        [Column("descripcion")]
        public string Descripcion { get; set; } = string.Empty;

        [Required]
        [Column("fk_id_alumno")]
        public int FkIdAlumno { get; set; }

        [Required]
        [Column("fk_id_aula")]
        public int FkIdAula { get; set; }

        [Required]
        [Column("fk_id_docente")]
        public int FkIdDocente { get; set; }

        [Required]
        [Column("fk_id_asignatura")]
        public int FkIdAsignatura { get; set; }

        [Required]
        [Column("fk_id_unidad")]
        public int FkIdUnidad { get; set; }
    }
}
