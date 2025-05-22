using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ColegioAPI.Models
{
    [Table("usuario")]
    public class Usuario
    {
        [Key]
        [Column("id_usuario")]
        public int IdUsuario { get; set; }

        [Required]
        [MaxLength(45)]
        [Column("nombre")]
        public string Nombre { get; set; } = string.Empty;

        [Required]
        [MaxLength(45)]
        [Column("apellido")]
        public string Apellido { get; set; } = string.Empty;
        [Required]
        [Column("username")]
        [MaxLength(50)]
        public string Username { get; set; } = null!;

        [Required]
        [Column("pass")]
        [MaxLength(255)]
        public string Pass { get; set; } = null!;

        [Column("correo")]
        [MaxLength(100)]
        public string? Correo { get; set; }

        [Required]
        [Column("rol")]
        [MaxLength(20)]
        public string Rol { get; set; } = "docente"; // ENUM simulado como string
    }
}
