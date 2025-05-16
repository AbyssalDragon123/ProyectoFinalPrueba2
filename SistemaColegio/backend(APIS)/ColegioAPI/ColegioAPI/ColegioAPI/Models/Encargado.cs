using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    [Table("encargado")]
    public class Encargado
    {
        [Key]
        [Column("id_encargado")]
        public int IdEncargado { get; set; }

        [Required]
        [MaxLength(50)]
        [Column("nombre_encargado")]
        public string NombreEncargado { get; set; } = string.Empty;

        [Required]
        [MaxLength(50)]
        [Column("apellido_encargado")]
        public string ApellidoEncargado { get; set; } = string.Empty;

        [Required]
        [MaxLength(100)]
        [Column("correo_encargado")]
        public string CorreoEncargado { get; set; } = string.Empty;

        [Required]
        [MaxLength(20)]
        [Column("telefono_encargado")]
        public string TelefonoEncargado { get; set; } = string.Empty;

        [Required]
        [MaxLength(50)]
        [Column("direccion_encargado")]
        public string DireccionEncargado { get; set; } = string.Empty;

        [Required]
        [MaxLength(20)]
        [Column("dpi_encargado")]
        public string DpiEncargado { get; set; } = string.Empty;
    }
}
