using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace ColegioAPI.DTOs
{
    public class RecuperarContrasenaDto
    {
        public string Correo { get; set; }
        public string Codigo { get; set; }
        public string NuevaContrasena { get; set; }
    }
}
