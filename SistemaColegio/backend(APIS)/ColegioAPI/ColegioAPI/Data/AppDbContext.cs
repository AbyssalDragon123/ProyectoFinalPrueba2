using ColegioAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace ColegioAPI.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Asignatura> Asignaturas { get; set; } = null!;
        public DbSet<Docente> Docentes { get; set; } = null!;
        public DbSet<Encargado> Encargados { get; set; } = null!;
        public DbSet<Grado_Seccion> GradosSecciones { get; set; } = null!;
        public DbSet<Usuario> Usuarios { get; set; } = null!;
        public DbSet<Notas> Notas { get; set; } = null!;
        public DbSet<Alumnos> Alumnos { get; set; } = null!;
        public DbSet<ColegioAPI.Models.Unidad> Unidad { get; set; } = default!;
    }
}
