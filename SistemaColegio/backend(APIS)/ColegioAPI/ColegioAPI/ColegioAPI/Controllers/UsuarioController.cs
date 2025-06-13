using ColegioAPI.Data;
using ColegioAPI.DTOs;
using ColegioAPI.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Net.Mail;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace ColegioAPI.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class UsuarioController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly IConfiguration _configuration;

        public UsuarioController(AppDbContext context, IConfiguration configuration)
        {
            _context = context;
            _configuration = configuration;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Usuario>>> GetUsuarios()
        {
            return await _context.Usuario.ToListAsync();
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Usuario>> GetUsuario(int id)
        {
            var usuario = await _context.Usuario.FindAsync(id);
            if (usuario == null)
                return NotFound();

            return usuario;
        }

        //modificar usuario
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUsuario(int id, Usuario usuario)
        {
            if (id != usuario.IdUsuario)
                return BadRequest();

            var usuarioActual = await _context.Usuario.AsNoTracking().FirstOrDefaultAsync(u => u.IdUsuario == id);
            if (usuarioActual == null)
                return NotFound();

            string passwordEnTextoPlano = usuario.Pass;

            // Enviar correo con contraseña en texto plano solo si se modificó la contraseña
            if (!string.IsNullOrEmpty(passwordEnTextoPlano))
            {
                bool correoEnviado = EnviarCorreoBienvenida(usuario.Correo, usuario.Nombre, usuario.Apellido, usuario.Username, passwordEnTextoPlano);
                if (!correoEnviado)
                    Console.WriteLine("Error enviando correo de bienvenida.");

                // Ahora sí hasheamos la contraseña para guardar
                usuario.Pass = HashPassword(passwordEnTextoPlano);
            }
            else
            {
                usuario.Pass = usuarioActual.Pass; // Conservar la contraseña anterior
            }

            _context.Entry(usuario).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UsuarioExists(id))
                    return NotFound();
                else
                    throw;
            }

            return NoContent();
        }

        // agregar nuevo usuario
        [HttpPost]
        [AllowAnonymous]
        public async Task<ActionResult<Usuario>> PostUsuario(Usuario usuario)
        {
            try
            {
                if (string.IsNullOrEmpty(usuario.Username) || string.IsNullOrEmpty(usuario.Pass) || string.IsNullOrEmpty(usuario.Nombre) || string.IsNullOrEmpty(usuario.Apellido))
                    return BadRequest("Usuario, contraseña, nombre y apellido son requeridos.");

                var existeUsuario = await _context.Usuario.AnyAsync(u => u.Username == usuario.Username);
                if (existeUsuario)
                    return BadRequest("El nombre de usuario ya está en uso.");

                string passwordEnTextoPlano = usuario.Pass;
                usuario.Pass = HashPassword(usuario.Pass);

                _context.Usuario.Add(usuario);
                await _context.SaveChangesAsync();

                bool correoEnviado = EnviarCorreoBienvenida(usuario.Correo, usuario.Nombre, usuario.Apellido, usuario.Username, passwordEnTextoPlano);
                if (!correoEnviado)
                    Console.WriteLine("Error enviando correo de bienvenida.");

                return CreatedAtAction("GetUsuario", new { id = usuario.IdUsuario }, usuario);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error al crear usuario: {ex.Message}\n{ex.StackTrace}");
                return StatusCode(500, new { mensaje = "Error interno al crear usuario", detalle = ex.Message });
            }
        }

        //enviar correo de usuario nuevo
        private bool EnviarCorreoBienvenida(string correo, string nombre, string apellido, string username, string password)
        {
            try
            {
                var smtpServer = _configuration["Smtp:Server"];
                var smtpPort = int.Parse(_configuration["Smtp:Port"]);
                var smtpUser = _configuration["Smtp:User"];
                var smtpPass = _configuration["Smtp:Pass"];

                var mail = new MailMessage();
                mail.From = new MailAddress(smtpUser, "Soporte Colegio LCT");
                mail.To.Add(correo);
                mail.Subject = "Bienvenido a Colegio LCT";
                mail.Body = $"Hola {nombre} {apellido},\n\nTu usuario es: {username}\nTu contraseña es: {password}\n\nPor seguridad, cambia tu contraseña en el primer inicio de sesión.\n\n¡NO COMPARTAS ESTE MENSAJE CON NADIE! \n\nSaludos.";
                mail.IsBodyHtml = false;

                using var smtp = new SmtpClient(smtpServer, smtpPort);
                smtp.Credentials = new System.Net.NetworkCredential(smtpUser, smtpPass);
                smtp.EnableSsl = true;
                smtp.Send(mail);

                return true;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error enviando correo: {ex.Message}\n{ex.StackTrace}");
                return false;
            }
        }

        // eliminar usuario
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUsuario(int id)
        {
            var usuario = await _context.Usuario.FindAsync(id);
            if (usuario == null)
                return NotFound();

            _context.Usuario.Remove(usuario);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        // Solicitar código de recuperación solo con correo
        [AllowAnonymous]
        [HttpPost("solicitar-recuperacion")]
        public async Task<IActionResult> SolicitarCodigoRecuperacion([FromBody] string correo)
        {
            if (string.IsNullOrEmpty(correo))
                return BadRequest("El correo es requerido.");

            var usuario = await _context.Usuario.FirstOrDefaultAsync(u => u.Correo == correo);
            if (usuario == null)
                return NotFound("Correo no registrado.");

            var random = new Random();
            var codigo = random.Next(10000000, 99999999).ToString();

            usuario.ResetCode = codigo;
            usuario.ResetCodeExpiration = DateTime.UtcNow.AddMinutes(1);
            await _context.SaveChangesAsync();

            var mail = new MailMessage();
            mail.From = new MailAddress(_configuration["Smtp:User"], "Soporte Colegio LCT");
            mail.To.Add(correo);
            mail.Subject = "Código de recuperación de contraseña Colegio LCT";
            mail.Body = $"Hola,\n\nTu código de recuperación es:\n\n{codigo}\n\nEste código expirará en 1 Minuto.\nNO COMPARTAS ESTE CODIGO CON NADIE.\n\nSaludos.";
            mail.IsBodyHtml = false;

            using var smtp = new SmtpClient(_configuration["Smtp:Server"], int.Parse(_configuration["Smtp:Port"]));
            smtp.Credentials = new System.Net.NetworkCredential(_configuration["Smtp:User"], _configuration["Smtp:Pass"]);
            smtp.EnableSsl = true;
            smtp.Send(mail);

            return Ok("Código de recuperación enviado por correo.");
        }

        // DTO para restablecer contraseña
        [AllowAnonymous]
        [HttpPost("restablecer-contrasena")]
        public async Task<IActionResult> RestablecerContrasena([FromBody] RecuperarContrasenaDto dto)
        {
            if (dto == null || string.IsNullOrEmpty(dto.Correo) || string.IsNullOrEmpty(dto.Codigo) || string.IsNullOrEmpty(dto.NuevaContrasena))
                return BadRequest("Correo, código y nueva contraseña son requeridos.");

            var usuario = await _context.Usuario.FirstOrDefaultAsync(u =>
                u.Correo == dto.Correo &&
                u.ResetCode == dto.Codigo &&
                u.ResetCodeExpiration > DateTime.UtcNow);

            if (usuario == null)
                return BadRequest("Correo o código inválido o expirado.");

            usuario.Pass = HashPassword(dto.NuevaContrasena);
            usuario.ResetCode = null;
            usuario.ResetCodeExpiration = null;

            await _context.SaveChangesAsync();

            return Ok("Contraseña restablecida correctamente.");
        }

        private bool UsuarioExists(int id)
        {
            return _context.Usuario.Any(e => e.IdUsuario == id);
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
    }
}