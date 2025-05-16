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
    public class EncargadoController : ControllerBase
    {
        private readonly AppDbContext _context;

        public EncargadoController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Encargado
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Encargado>>> GetEncargados()
        {
            return await _context.Encargados.ToListAsync();
        }

        // GET: api/Encargado/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Encargado>> GetEncargado(int id)
        {
            var encargado = await _context.Encargados.FindAsync(id);

            if (encargado == null)
            {
                return NotFound();
            }

            return encargado;
        }

        // PUT: api/Encargado/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutEncargado(int id, Encargado encargado)
        {
            if (id != encargado.IdEncargado)
            {
                return BadRequest();
            }

            _context.Entry(encargado).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EncargadoExists(id))
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

        // POST: api/Encargado
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Encargado>> PostEncargado(Encargado encargado)
        {
            _context.Encargados.Add(encargado);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetEncargado", new { id = encargado.IdEncargado }, encargado);
        }

        // DELETE: api/Encargado/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteEncargado(int id)
        {
            var encargado = await _context.Encargados.FindAsync(id);
            if (encargado == null)
            {
                return NotFound();
            }

            _context.Encargados.Remove(encargado);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool EncargadoExists(int id)
        {
            return _context.Encargados.Any(e => e.IdEncargado == id);
        }
    }
}
