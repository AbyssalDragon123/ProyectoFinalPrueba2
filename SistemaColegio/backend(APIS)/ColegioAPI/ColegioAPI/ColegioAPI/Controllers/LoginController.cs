using ColegioAPI.Data;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity.Data;
using Microsoft.AspNetCore.Mvc;
using ColegioAPI.DTOs;

namespace ColegioAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly AppDbContext _context;
        public LoginController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost("login")]

        public IActionResult Login([FromBody] DTOs.LoginRequest loginRequest)
        {
            if (loginRequest == null || string.IsNullOrEmpty(loginRequest.UserName) || string.IsNullOrEmpty(loginRequest.Password))
            {
                return BadRequest("Faltan datos para la validación...");
            }
            var user = _context.Usuario.FirstOrDefault(u => u.Username == loginRequest.UserName && u.Pass == loginRequest.Password);
            if (user == null)
            {
                return Unauthorized("Usuario o Contraseña incorrectos.");
            }
            // Aquí puedes generar un token JWT y devolverlo al cliente
            // Por simplicidad, solo devolvemos el nombre de usuario
            return Ok(new { user.Username, user.Rol });
        }
    }
}
