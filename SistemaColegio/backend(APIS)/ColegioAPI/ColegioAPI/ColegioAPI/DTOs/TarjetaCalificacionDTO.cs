namespace ColegioAPI.DTOs
{
    public class TarjetaCalificacionDTO
    {
        public string Alumno { get; set; }
        public string Aula { get; set; }
        public string Asignatura { get; set; }
        public decimal Nota { get; set; }
        public string Descripcion { get; set; }
        public string Docente { get; set; }
        public DateTime FechaEmision { get; set; }
    }
}
