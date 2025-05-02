using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace ColegioAPI.Models
{
    [Table("notas")]
    public class Notas
    {
        [Key]
        [Column("id_notas")]
        public int IdNotas { get; set; }

        [Column("id_docente")]
        public int IdDocente { get; set; }

        [Column("id_asignatura")]
        public int IdAsignatura { get; set; }

        [Column("id_unidad")]
        public int IdUnidad { get; set; }

        [Column("nota")]
        public decimal Nota { get; set; }

        public Docente Docente { get; set; } = null!;
        public Asignatura Asignatura { get; set; } = null!;
        public Unidad Unidad { get; set; } = null!;
    }

}
