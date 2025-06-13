using ColegioAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace ColegioAPI.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Asignatura> Asignatura { get; set; } = null!;
        public DbSet<Docente> Docente { get; set; } = null!;
        public DbSet<Encargado> Encargado { get; set; } = null!;
        public DbSet<Aula> Grados_Seccion { get; set; } = null!;
        public DbSet<Usuario> Usuario { get; set; } = null!;
        public DbSet<Alumnos> Alumnos { get; set; } = null!;
        public DbSet<Notas> Notas { get; set; } = default!;
    }
}
