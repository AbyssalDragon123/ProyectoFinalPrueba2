use dbcrudcolegio;
SELECT 
    CONCAT(a.nombre_alumno, ' ', a.apellido_alumno) AS alumno,
    CONCAT(au.grado, ' ', au.seccion) AS aula,
    asi.nombre_asignatura,
    n.nota,
    n.descripcion,
    CONCAT(d.nombre_docente, ' ', d.apellido_docente) AS docente
FROM notas n
INNER JOIN alumno a ON n.fk_id_alumno = a.id_alumno
INNER JOIN aula au ON n.fk_id_aula = au.id_aula
INNER JOIN asignatura asi ON n.fk_id_asignatura = asi.id_asignatura
INNER JOIN docente d ON n.fk_id_docente = d.id_docente
WHERE a.id_alumno = 1
ORDER BY asi.nombre_asignatura;


