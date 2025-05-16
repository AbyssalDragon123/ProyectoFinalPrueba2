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
        public decimal Nota { get; set; }

        [Required]
        [Column("descripcion")]
        public string Descripcion { get; set; } = string.Empty;

        [Required]
        [Column("fk_id_alumno")]
        public int FkIdAlumno { get; set; }

        [Required]
        [Column("fk_id_grado_seccion")]
        public int FkIdGradoSeccion { get; set; }

        [Required]
        [Column("fk_id_docente")]
        public int FkIdDocente { get; set; }

        [Required]
        [Column("fk_id_asignatura")]
        public int FkIdAsignatura { get; set; }

        [Required]
        [Column("fk_id_unidad")]
        public int FkIdUnidad { get; set; }

        // Propiedades de navegación (relaciones)
        [ForeignKey("FkIdAlumno")]
        [JsonIgnore]
        public virtual Alumnos? Alumno { get; set; } = null!;

        [ForeignKey("FkIdGradoSeccion")]
        [JsonIgnore]
        public virtual Grado_Seccion? GradoSeccion { get; set; } = null!;

        [ForeignKey("FkIdDocente")]
        [JsonIgnore]
        public virtual Docente? Docente { get; set; } = null!;

        [ForeignKey("FkIdAsignatura")]
        [JsonIgnore]
        public virtual Asignatura? Asignatura { get; set; } = null!;

        [ForeignKey("FkIdUnidad")]
        [JsonIgnore]
        public virtual Unidad? Unidad { get; set; } = null!;
    }
}
