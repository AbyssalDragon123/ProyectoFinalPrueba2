using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

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
        public decimal Nota { get; set; }

        [Required]
        [Column("descripcion")]
        public string Descripcion { get; set; } = null!;

        // Clave foránea a Alumno
        [Required]
        [Column("fk_id_alumno")]
        public int FkIdAlumno { get; set; }

        [ForeignKey("FkIdAlumno")]
        public Alumnos Alumno { get; set; } = null!;

        // Clave foránea a Grado_Seccion
        [Required]
        [Column("fk_id_grado_seccion")]
        public int FkIdGradoSeccion { get; set; }

        [ForeignKey("FkIdGradoSeccion")]
        public Grado_Seccion GradoSeccion { get; set; } = null!;

        // Clave foránea a Docente
        [Required]
        [Column("fk_id_docente")]
        public int FkIdDocente { get; set; }

        [ForeignKey("FkIdDocente")]
        public Docente Docente { get; set; } = null!;

        // Clave foránea a Asignatura
        [Required]
        [Column("fk_id_asignatura")]
        public int FkIdAsignatura { get; set; }

        [ForeignKey("FkIdAsignatura")]
        public Asignatura Asignatura { get; set; } = null!;

        // Clave foránea a Unidad
        [Required]
        [Column("fk_id_unidad")]
        public int FkIdUnidad { get; set; }

        [ForeignKey("FkIdUnidad")]
        public Unidad Unidad { get; set; } = null!;
    }
}
