using ColegioAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace ColegioAPI.Data
{
    public class AppDbContext : DbContext

    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) // conectar la base de datos con el appsettings json
        {
        }
        public DbSet<Alumno> alumno { get; set; }
        public DbSet<Usuario> usuario { get; set; }



    }
}
