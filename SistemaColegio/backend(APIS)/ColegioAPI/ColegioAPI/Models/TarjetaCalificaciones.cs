using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace ColegioAPI.Models
{
    [Table("tarjeta_calificaciones")]
    public class TarjetaCalificaciones
    {
        [Key]
        [Column("id_calificaciones")]
        public int IdCalificaciones { get; set; }

        [Column("fk_id_unidad")]
        public int FkIdUnidad { get; set; }

        [Column("fk_id_alumno")]
        public int FkIdAlumno { get; set; }

        [Column("fk_id_grado")]
        public int FkIdGrado { get; set; }

        [Column("fk_id_docente")]
        public int FkIdDocente { get; set; }

        [Column("fk_id_asignatura")]
        public int FkIdAsignatura { get; set; }

        [Column("fk_id_notas")]
        public int FkIdNotas { get; set; }

        [Required]
        [Column("descripcion")]
        public string Descripcion { get; set; } = string.Empty;

        public Unidad Unidad { get; set; } = null!;
        public Alumno Alumno { get; set; } = null!;
        public Grado Grado { get; set; } = null!;
        public Docente Docente { get; set; } = null!;
        public Asignatura Asignatura { get; set; } = null!;
        public Notas Notas { get; set; } = null!;
    }

}
