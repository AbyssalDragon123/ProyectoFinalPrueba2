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
            [Column("nombre_encargado")]
            [StringLength(50)]
            public string NombreEncargado { get; set; } = string.Empty;

            [Required]
            [Column("apellido_encargado")]
            [StringLength(50)]
            public string ApellidoEncargado { get; set; } = string.Empty;

            [Required]
            [Column("correo_encargado")]
            [StringLength(100)]
            [EmailAddress]
            public string CorreoEncargado { get; set; } = string.Empty;

            [Required]
            [Column("telefono_encargado")]
            [StringLength(20)]
            public string TelefonoEncargado { get; set; } = string.Empty;

            [Required]
            [Column("direccion_encargado")]
            [StringLength(50)]
            public string DireccionEncargado { get; set; } = string.Empty;

            [Required]
            [Column("dpi_encargado")]
            [StringLength(20)]
            public string DpiEncargado { get; set; } = string.Empty;
        }

    }
