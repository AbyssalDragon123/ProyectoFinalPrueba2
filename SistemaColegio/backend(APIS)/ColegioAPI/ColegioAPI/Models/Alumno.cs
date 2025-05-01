using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    [Table("alumno")]
    public class Alumno
    {
        [Required]
        [Key]
        [Column("id_alumno")]
        public int IdAlumno { get; set; }
        //--------------------------------------------------------------------------
        [Required]
        [Column("nombre_alumno")]
        [StringLength(50)]
        public string NombreAlumno { get; set; } = string.Empty;
        //--------------------------------------------------------------------------
        [Required]
        [Column("apellido_alumno")]
        [StringLength(50)]
        public string ApellidoAlumno { get; set; } = string.Empty;  
        //--------------------------------------------------------------------------
        [Required]
        [Column("gmail_alumno")]
        [StringLength(50)]
        [EmailAddress]
        public string GmailAlumno { get; set; } = string.Empty;
        //--------------------------------------------------------------------------
        [Required]
        [Column("telefono_alumno")]
        [StringLength(8)]
        public string TelefonoAlumno { get; set; } = string.Empty;
        //--------------------------------------------------------------------------
        [Required]
        [Column("genero_alumno")]
        [StringLength(10)]
        public string GeneroAlumno { get; set; } = string.Empty;

    }
}
