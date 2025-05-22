-- USUARIOS
INSERT INTO usuario (nombre, apellido, username, pass, correo, rol) VALUES
('Ana', 'García', 'ana.garcia', 'pass123', 'ana.garcia@lct.edu.gt', 'admin'),
('Luis', 'Martínez', 'luis.martinez', 'pass123', 'luis.martinez@lct.edu.gt', 'docente'),
('María', 'López', 'maria.lopez', 'pass123', 'maria.lopez@lct.edu.gt', 'docente'),
('Carlos', 'Pérez', 'carlos.perez', 'pass123', 'carlos.perez@lct.edu.gt', 'docente'),
('Sofía', 'Ramírez', 'sofia.ramirez', 'pass123', 'sofia.ramirez@lct.edu.gt', 'docente'),
('Jorge', 'Hernández', 'jorge.hernandez', 'pass123', 'jorge.hernandez@lct.edu.gt', 'docente'),
('Elena', 'Díaz', 'elena.diaz', 'pass123', 'elena.diaz@lct.edu.gt', 'docente'),
('Pedro', 'Santos', 'pedro.santos', 'pass123', 'pedro.santos@lct.edu.gt', 'docente'),
('Lucía', 'Castillo', 'lucia.castillo', 'pass123', 'lucia.castillo@lct.edu.gt', 'docente'),
('Miguel', 'Reyes', 'miguel.reyes', 'pass123', 'miguel.reyes@lct.edu.gt', 'docente');

-- ENCARGADOS
INSERT INTO encargado (nombre_encargado, apellido_encargado, correo_encargado, telefono_encargado, direccion_encargado, dpi_encargado) VALUES
('Juan', 'Gómez', 'juan.gomez@mail.com', '5551001', 'Zona 1', '1234567890101'),
('Marta', 'Ruiz', 'marta.ruiz@mail.com', '5551002', 'Zona 2', '1234567890102'),
('Pedro', 'Morales', 'pedro.morales@mail.com', '5551003', 'Zona 3', '1234567890103'),
('Laura', 'Vásquez', 'laura.vasquez@mail.com', '5551004', 'Zona 4', '1234567890104'),
('Carmen', 'Jiménez', 'carmen.jimenez@mail.com', '5551005', 'Zona 5', '1234567890105'),
('Roberto', 'Mendoza', 'roberto.mendoza@mail.com', '5551006', 'Zona 6', '1234567890106'),
('Patricia', 'Castro', 'patricia.castro@mail.com', '5551007', 'Zona 7', '1234567890107'),
('Alberto', 'Silva', 'alberto.silva@mail.com', '5551008', 'Zona 8', '1234567890108'),
('Gloria', 'Ortega', 'gloria.ortega@mail.com', '5551009', 'Zona 9', '1234567890109'),
('Rosa', 'Navarro', 'rosa.navarro@mail.com', '5551010', 'Zona 10', '1234567890110');

-- AULAS
INSERT INTO aula (grado, seccion) VALUES
('Primero', 'A'),
('Primero', 'B'),
('Segundo', 'A'),
('Segundo', 'B'),
('Tercero', 'A'),
('Tercero', 'B'),
('Cuarto', 'A'),
('Cuarto', 'B'),
('Quinto', 'A'),
('Quinto', 'B');

-- ALUMNOS
INSERT INTO alumno (nombre_alumno, apellido_alumno, genero_alumno, fk_id_encargado, fk_id_aula) VALUES
('Andrea', 'Gómez', 'Femenino', 1, 1),
('Diego', 'Ruiz', 'Masculino', 2, 2),
('Valeria', 'Morales', 'Femenino', 3, 3),
('Santiago', 'Vásquez', 'Masculino', 4, 4),
('Camila', 'Jiménez', 'Femenino', 5, 5),
('Mateo', 'Mendoza', 'Masculino', 6, 6),
('Isabella', 'Castro', 'Femenino', 7, 7),
('Emiliano', 'Silva', 'Masculino', 8, 8),
('Sofía', 'Ortega', 'Femenino', 9, 9),
('Gabriel', 'Navarro', 'Masculino', 10, 10);

-- DOCENTES (cada uno vinculado a un usuario diferente)
INSERT INTO docente (nombre_docente, apellido_docente, correo_docente, telefono_docente, fk_id_usuario) VALUES
('Luis', 'Martínez', 'luis.martinez@lct.edu.gt', '5552001', 2),
('María', 'López', 'maria.lopez@lct.edu.gt', '5552002', 3),
('Carlos', 'Pérez', 'carlos.perez@lct.edu.gt', '5552003', 4),
('Sofía', 'Ramírez', 'sofia.ramirez@lct.edu.gt', '5552004', 5),
('Jorge', 'Hernández', 'jorge.hernandez@lct.edu.gt', '5552005', 6),
('Elena', 'Díaz', 'elena.diaz@lct.edu.gt', '5552006', 7),
('Pedro', 'Santos', 'pedro.santos@lct.edu.gt', '5552007', 8),
('Lucía', 'Castillo', 'lucia.castillo@lct.edu.gt', '5552008', 9),
('Miguel', 'Reyes', 'miguel.reyes@lct.edu.gt', '5552009', 10),
('Ana', 'García', 'ana.garcia@lct.edu.gt', '5552010', 1); -- admin como docente

-- ASIGNATURAS (cada una con un docente diferente)
INSERT INTO asignatura (nombre_asignatura, descripcion_asignatura, fk_id_docente) VALUES
('Matemática', 'Matemática básica', 1),
('Lengua', 'Lengua y Literatura', 2),
('Ciencias', 'Ciencias Naturales', 3),
('Sociales', 'Ciencias Sociales', 4),
('Inglés', 'Inglés básico', 5),
('Computación', 'Informática básica', 6),
('Arte', 'Expresión artística', 7),
('Música', 'Educación musical', 8),
('Educación Física', 'Actividad física', 9),
('Moral y Cívica', 'Formación en valores', 10);

-- UNIDADES (cada una con una asignatura diferente)
INSERT INTO unidad (nombre_unidad, descripcion, fk_id_asignatura) VALUES
('Números Naturales', 'Introducción a los números', 1),
('Suma y Resta', 'Operaciones básicas', 1),
('Gramática', 'Reglas gramaticales', 2),
('Lectura', 'Comprensión lectora', 2),
('El cuerpo humano', 'Partes y funciones', 3),
('Ecosistemas', 'Tipos de ecosistemas', 3),
('Historia Antigua', 'Primeras civilizaciones', 4),
('Geografía', 'Mapas y territorios', 4),
('Saludos y Presentaciones', 'Vocabulario básico', 5),
('Colores y Números', 'Vocabulario esencial', 5);

-- NOTAS (cada nota para un alumno, aula, docente, asignatura y unidad coherente)
INSERT INTO notas (nota, descripcion, fk_id_alumno, fk_id_aula, fk_id_docente, fk_id_asignatura, fk_id_unidad) VALUES
(85.50, 'Buen desempeño', 1, 1, 1, 1, 1),
(90.00, 'Excelente', 2, 2, 2, 2, 3),
(78.25, 'Puede mejorar', 3, 3, 3, 3, 5),
(88.00, 'Muy bien', 4, 4, 4, 4, 7),
(92.75, 'Sobresaliente', 5, 5, 5, 5, 9),
(80.00, 'Aprobado', 6, 6, 6, 6, 6),
(95.00, 'Excelente', 7, 7, 7, 7, 7),
(70.50, 'Debe esforzarse', 8, 8, 8, 8, 8),
(89.00, 'Muy bien', 9, 9, 9, 9, 9),
(76.00, 'Satisfactorio', 10, 10, 10, 10, 10);