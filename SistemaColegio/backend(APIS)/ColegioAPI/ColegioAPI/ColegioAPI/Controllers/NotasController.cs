using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ColegioAPI.Data;
using ColegioAPI.Models;
using Microsoft.AspNetCore.Authorization;

namespace ColegioAPI.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class NotasController : ControllerBase
    {
        private readonly AppDbContext _context;

        public NotasController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Notas
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Notas>>> GetNotas()
        {
            return await _context.Notas.ToListAsync();
        }

        // GET: api/Notas/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Notas>> GetNotas(int id)
        {
            var notas = await _context.Notas.FindAsync(id);

            if (notas == null)
            {
                return NotFound();
            }

            return notas;
        }

        // PUT: api/Notas/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutNotas(int id, Notas notas)
        {
            if (id != notas.IdNotas)
            {
                return BadRequest();
            }

            _context.Entry(notas).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!NotasExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Notas
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Notas>> PostNotas(Notas notas)
        {
            _context.Notas.Add(notas);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetNotas", new { id = notas.IdNotas }, notas);
        }
        // GET: api/Notas/alumno/5
        [HttpGet("alumno/{idAlumno}")]
        public async Task<ActionResult<IEnumerable<Notas>>> GetNotasPorAlumno(int idAlumno)
        {
            var notasAlumno = await _context.Notas
                .Where(n => n.FkIdAlumno == idAlumno)
                .ToListAsync();

            if (notasAlumno == null || notasAlumno.Count == 0)
            {
                return NotFound();
            }

            return notasAlumno;
        }
        // DELETE: api/Notas/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteNotas(int id)
        {
            var notas = await _context.Notas.FindAsync(id);
            if (notas == null)
            {
                return NotFound();
            }

            _context.Notas.Remove(notas);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool NotasExists(int id)
        {
            return _context.Notas.Any(e => e.IdNotas == id);
        }
    }
}
