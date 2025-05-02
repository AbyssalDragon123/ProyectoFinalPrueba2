using ColegioAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace ColegioAPI.Data
{
    public class AppDbContext : DbContext

    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) // conectar la base de datos con el appsettings json
        {
        }
        public DbSet<Usuario> usuario { get; set; }
        public DbSet<ColegioAPI.Models.Encargado> Encargado { get; set; } = default!;
        public DbSet<ColegioAPI.Models.Grado> Grado { get; set; } = default!;
        public DbSet<ColegioAPI.Models.Alumno> Alumno { get; set; } = default!;
        public DbSet<ColegioAPI.Models.Docente> Docente { get; set; } = default!;
        public DbSet<ColegioAPI.Models.Asignatura> Asignatura { get; set; } = default!;
        public DbSet<ColegioAPI.Models.Unidad> Unidad { get; set; } = default!;
        public DbSet<ColegioAPI.Models.Notas> Notas { get; set; } = default!;
        public DbSet<ColegioAPI.Models.TarjetaCalificaciones> TarjetaCalificaciones { get; set; } = default!;


    }
}
