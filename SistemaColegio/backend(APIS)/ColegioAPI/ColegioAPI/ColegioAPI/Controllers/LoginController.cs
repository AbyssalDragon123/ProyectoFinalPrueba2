using ColegioAPI.Data;
using ColegioAPI.DTOs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using TimeZoneConverter; // Asegúrate de instalar este paquete NuGet

namespace ColegioAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly IConfiguration _configuration;

        public LoginController(AppDbContext context, IConfiguration configuration)
        {
            _context = context;
            _configuration = configuration;
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginRequest loginRequest)
        {
            if (loginRequest == null || string.IsNullOrEmpty(loginRequest.UserName) || string.IsNullOrEmpty(loginRequest.Password))
            {
                return BadRequest("Faltan datos para la validación...");
            }

            var hashedPassword = HashPassword(loginRequest.Password);

            var user = await _context.Usuario.FirstOrDefaultAsync(u => u.Username == loginRequest.UserName && u.Pass == hashedPassword);
            if (user == null)
            {
                return Unauthorized("Usuario o Contraseña incorrectos.");
            }

            // Genera token y obtiene la fecha de expiración en UTC
            var (token, expirationUtc) = GenerateJwtToken(user);

            // Convierte la fecha de expiración a la zona horaria de Guatemala
            TimeZoneInfo guatemalaZone = TZConvert.GetTimeZoneInfo("America/Guatemala");
            DateTime expirationGuatemala = TimeZoneInfo.ConvertTimeFromUtc(expirationUtc, guatemalaZone);

            return Ok(new
            {
                token,
                username = user.Username,
                rol = user.Rol,
                expiration = expirationGuatemala // Fecha de expiración en hora local Guatemala
            });
        }

        private string HashPassword(string password)
        {
            using var sha256 = SHA256.Create();
            var bytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
            var builder = new StringBuilder();
            foreach (var b in bytes)
                builder.Append(b.ToString("x2"));
            return builder.ToString();
        }

        private (string, DateTime) GenerateJwtToken(Models.Usuario user)
        {
            var claims = new[]
            {
                new Claim(ClaimTypes.Name, user.Username),
                new Claim(ClaimTypes.Role, user.Rol)
            };

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]));
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            var expiration = DateTime.UtcNow.AddMinutes(30);

            var token = new JwtSecurityToken(
                issuer: _configuration["Jwt:Issuer"],
                audience: _configuration["Jwt:Audience"],
                claims: claims,
                expires: expiration,
                signingCredentials: creds);

            return (new JwtSecurityTokenHandler().WriteToken(token), expiration);
        }
    }
}