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
    public class UnidadsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public UnidadsController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Unidads
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Unidad>>> GetUnidad()
        {
            return await _context.Unidad.ToListAsync();
        }

        // GET: api/Unidads/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Unidad>> GetUnidad(int id)
        {
            var unidad = await _context.Unidad.FindAsync(id);

            if (unidad == null)
            {
                return NotFound();
            }

            return unidad;
        }

        // PUT: api/Unidads/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUnidad(int id, Unidad unidad)
        {
            if (id != unidad.IdUnidad)
            {
                return BadRequest();
            }

            _context.Entry(unidad).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UnidadExists(id))
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

        // POST: api/Unidads
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Unidad>> PostUnidad(Unidad unidad)
        {
            _context.Unidad.Add(unidad);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetUnidad", new { id = unidad.IdUnidad }, unidad);
        }

        // DELETE: api/Unidads/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUnidad(int id)
        {
            var unidad = await _context.Unidad.FindAsync(id);
            if (unidad == null)
            {
                return NotFound();
            }

            _context.Unidad.Remove(unidad);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool UnidadExists(int id)
        {
            return _context.Unidad.Any(e => e.IdUnidad == id);
        }
    }
}
