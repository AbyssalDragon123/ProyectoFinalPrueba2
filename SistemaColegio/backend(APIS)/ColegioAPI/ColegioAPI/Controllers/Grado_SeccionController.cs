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
    public class Grado_SeccionController : ControllerBase
    {
        private readonly AppDbContext _context;

        public Grado_SeccionController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Grado_Seccion
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Grado_Seccion>>> GetGradosSecciones()
        {
            return await _context.GradosSecciones.ToListAsync();
        }

        // GET: api/Grado_Seccion/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Grado_Seccion>> GetGrado_Seccion(int id)
        {
            var grado_Seccion = await _context.GradosSecciones.FindAsync(id);

            if (grado_Seccion == null)
            {
                return NotFound();
            }

            return grado_Seccion;
        }

        // PUT: api/Grado_Seccion/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGrado_Seccion(int id, Grado_Seccion grado_Seccion)
        {
            if (id != grado_Seccion.IdGradoSeccion)
            {
                return BadRequest();
            }

            _context.Entry(grado_Seccion).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!Grado_SeccionExists(id))
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

        // POST: api/Grado_Seccion
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Grado_Seccion>> PostGrado_Seccion(Grado_Seccion grado_Seccion)
        {
            _context.GradosSecciones.Add(grado_Seccion);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetGrado_Seccion", new { id = grado_Seccion.IdGradoSeccion }, grado_Seccion);
        }

        // DELETE: api/Grado_Seccion/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGrado_Seccion(int id)
        {
            var grado_Seccion = await _context.GradosSecciones.FindAsync(id);
            if (grado_Seccion == null)
            {
                return NotFound();
            }

            _context.GradosSecciones.Remove(grado_Seccion);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool Grado_SeccionExists(int id)
        {
            return _context.GradosSecciones.Any(e => e.IdGradoSeccion == id);
        }
    }
}
