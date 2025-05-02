using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ColegioAPI.Data;
using ColegioAPI.Models;

namespace ColegioAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TarjetaCalificacionesController : ControllerBase
    {
        private readonly AppDbContext _context;

        public TarjetaCalificacionesController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/TarjetaCalificaciones
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TarjetaCalificaciones>>> GetTarjetaCalificaciones()
        {
            return await _context.TarjetaCalificaciones.ToListAsync();
        }

        // GET: api/TarjetaCalificaciones/5
        [HttpGet("{id}")]
        public async Task<ActionResult<TarjetaCalificaciones>> GetTarjetaCalificaciones(int id)
        {
            var tarjetaCalificaciones = await _context.TarjetaCalificaciones.FindAsync(id);

            if (tarjetaCalificaciones == null)
            {
                return NotFound();
            }

            return tarjetaCalificaciones;
        }

        // PUT: api/TarjetaCalificaciones/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTarjetaCalificaciones(int id, TarjetaCalificaciones tarjetaCalificaciones)
        {
            if (id != tarjetaCalificaciones.IdCalificaciones)
            {
                return BadRequest();
            }

            _context.Entry(tarjetaCalificaciones).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TarjetaCalificacionesExists(id))
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

        // POST: api/TarjetaCalificaciones
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<TarjetaCalificaciones>> PostTarjetaCalificaciones(TarjetaCalificaciones tarjetaCalificaciones)
        {
            _context.TarjetaCalificaciones.Add(tarjetaCalificaciones);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetTarjetaCalificaciones", new { id = tarjetaCalificaciones.IdCalificaciones }, tarjetaCalificaciones);
        }

        // DELETE: api/TarjetaCalificaciones/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTarjetaCalificaciones(int id)
        {
            var tarjetaCalificaciones = await _context.TarjetaCalificaciones.FindAsync(id);
            if (tarjetaCalificaciones == null)
            {
                return NotFound();
            }

            _context.TarjetaCalificaciones.Remove(tarjetaCalificaciones);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool TarjetaCalificacionesExists(int id)
        {
            return _context.TarjetaCalificaciones.Any(e => e.IdCalificaciones == id);
        }
    }
}
