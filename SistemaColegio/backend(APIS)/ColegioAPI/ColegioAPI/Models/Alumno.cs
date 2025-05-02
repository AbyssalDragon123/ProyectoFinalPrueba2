using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    [Table("alumno")]
    public class Alumno
    {
        [Key]
        [Column("id_alumno")]
        public int IdAlumno { get; set; }

        [Required]
        [Column("nombre_alumno")]
        [StringLength(50)]
        public string NombreAlumno { get; set; } = string.Empty;

        [Required]
        [Column("apellido_alumno")]
        [StringLength(50)]
        public string ApellidoAlumno { get; set; } = string.Empty;

        [Column("gmail_alumno")]
        [StringLength(30)]
        [EmailAddress]
        public string? GmailAlumno { get; set; }

        [Required]
        [Column("telefono_alumno")]
        [StringLength(10)]
        public string TelefonoAlumno { get; set; } = string.Empty;

        [Required]
        [Column("genero_alumno")]
        [StringLength(1)]
        public string GeneroAlumno { get; set; } = string.Empty;

        [ForeignKey("Encargado")]
        [Column("fk_id_encargado")]
        public int FkIdEncargado { get; set; }

        [ForeignKey("Grado")]
        [Column("fk_id_grado")]
        public int FkIdGrado { get; set; }

        public Encargado Encargado { get; set; } = null!;
        public Grado Grado { get; set; } = null!;
    }

}
