using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;

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
        [Required]
        [Column("correo")]
        [MaxLength(100)]
        public string? Correo { get; set; }

        [Required]
        [Column("rol")]
        [MaxLength(20)]
        public string Rol { get; set; } = "admin"; // ENUM simulado como string

        // Nueva propiedad para el código de recuperación
        [Column("reset_code")]
        [MaxLength(255)]
        public string? ResetCode { get; set; }

        // Nueva propiedad para la fecha de expiración del código
        [Column("reset_code_expiration")]
        public DateTime? ResetCodeExpiration { get; set; }
    }
}