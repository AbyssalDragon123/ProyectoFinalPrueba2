using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace ColegioAPI.Models
{
    [Table("docentes")]
    public class Docente
    {
        [Key]
        [Column("id_docente")]
        public int IdDocente { get; set; }

        [Required]
        [Column("cedula")]
        [StringLength(50)]
        public string Cedula { get; set; } = string.Empty;

        [Required]
        [Column("nombre_docente")]
        [StringLength(50)]
        public string NombreDocente { get; set; } = string.Empty;

        [Required]
        [Column("apellido_docente")]
        [StringLength(50)]
        public string ApellidoDocente { get; set; } = string.Empty;

        [Required]
        [Column("email_docente")]
        [StringLength(100)]
        [EmailAddress]
        public string EmailDocente { get; set; } = string.Empty;

        [Required]
        [Column("telefono_docente")]
        [StringLength(15)]
        public string TelefonoDocente { get; set; } = string.Empty;

        [Required]
        [Column("genero_docente")]
        [StringLength(50)]
        public string GeneroDocente { get; set; } = string.Empty;

        [Required]
        [Column("direccion_docente")]
        [StringLength(100)]
        public string DireccionDocente { get; set; } = string.Empty;

        [Required]
        [Column("dpi_docente")]
        [StringLength(20)]
        public string DpiDocente { get; set; } = string.Empty;

        [Required]
        [Column("especialidad")]
        [StringLength(100)]
        public string Especialidad { get; set; } = string.Empty;

        [ForeignKey("Grado")]
        [Column("fk_id_grado")]
        public int FkIdGrado { get; set; }

        [Column("fk_id_usuario")]
        public int FkIdUsuario { get; set; }

        public Grado Grado { get; set; } = null!;
    }

}
