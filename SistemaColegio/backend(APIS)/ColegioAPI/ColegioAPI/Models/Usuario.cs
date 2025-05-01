using System;
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
        [Column("username")]
        [StringLength(50)]
        public string Username { get; set; } = string.Empty;

        [Required]
        [Column("pass")]
        [StringLength(255)]
        public string Pass { get; set; } = string.Empty;

        [Column("email")]
        [StringLength(100)]
        [EmailAddress]
        public string? Email { get; set; }

        [Column("rol")]
        [StringLength(10)] // Máximo largo de 'director'
        public string Rol { get; set; } = "docente";

        [Column("fecha_registro")]
        public DateTime FechaRegistro { get; set; } = DateTime.Now;
    }
}

