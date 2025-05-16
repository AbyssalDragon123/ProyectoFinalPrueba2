using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    public class Docente
    {
        [Key]
        [Column("id_docente")]
        public int IdDocente { get; set; }

        [Required]
        [MaxLength(50)]
        [Column("nombre_docente")]
        public string NombreDocente { get; set; } = string.Empty;

        [Required]
        [MaxLength(50)]
        [Column("apellido_docente")]
        public string ApellidoDocente { get; set; } = string.Empty;

        [Required]
        [MaxLength(100)]
        [EmailAddress]
        [Column("correo_docente")]
        public string CorreoDocente { get; set; } = string.Empty;

        [Required]
        [MaxLength(15)]
        [Phone]
        [Column("telefono_docente")]
        public string TelefonoDocente { get; set; } = string.Empty;

        [Required]
        [ForeignKey("Usuario")]
        [Column("fk_id_usuario")]
        public int FkIdUsuario { get; set; }

        public Usuario Usuario { get; set; } = null!;
    }
}
