using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace ColegioAPI.Models
{
    [Table("asignatura")]
    public class Asignatura
    {
        [Key]
        [Column("id_asignatura")]
        public int IdAsignatura { get; set; }

        [Required]
        [Column("nombre_asignatura")]
        [StringLength(50)]
        public string NombreAsignatura { get; set; } = string.Empty;

        [Column("descripcion_asignatura")]
        public string? DescripcionAsignatura { get; set; }

        [ForeignKey("Docente")]
        [Column("fk_id_docente")]
        public int FkIdDocente { get; set; }

        public Docente Docente { get; set; } = null!;
    }

}
