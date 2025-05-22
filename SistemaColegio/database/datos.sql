-- ===================
-- INSERCIÓN DE DATOS
-- ===================
use dbcrudcolegio;
-- Usuarios
INSERT INTO usuario (nombre, apellido, username, pass, email, rol) VALUES
('alex','leon', 'alekey','alex2004', 'alexanderdeleon0431@gmail.com', 'admin'),
('jose','lopez','dir1', '12345', 'dir1@colegio.com', 'docente'),
('david','perez','doc1', '12345', 'doc1@colegio.com', 'docente'),
('juan','ramirez','doc2', '12345', 'doc2@colegio.com', 'docente'),
('luis','montana','doc3', '12345', 'doc3@colegio.com', 'docente'),
('sucy','velasquez','doc4', '12345', 'doc4@colegio.com', 'docente'),
('flor','raymundo','doc5', '12345', 'doc5@colegio.com', 'docente'),
('amanda','perez','doc6', '12345', 'doc6@colegio.com', 'docente'),
('anderson','tojin','doc7', '12345', 'doc7@colegio.com', 'docente'),
('mike','cotzo','doc8', '12345', 'doc8@colegio.com', 'docente');

-- Encargados
INSERT INTO encargado (nombre_encargado, apellido_encargado, correo_encargado, telefono_encargado, direccion_encargado, dpi_encargado) VALUES
('Carlos', 'Gómez', 'cgomez@mail.com', '50110001', 'Zona 1', '123456789001'),
('Ana', 'Pérez', 'aperez@mail.com', '50110002', 'Zona 2', '123456789002'),
('Luis', 'Martínez', 'lmartinez@mail.com', '50110003', 'Zona 3', '123456789003'),
('María', 'Rodríguez', 'mrodriguez@mail.com', '50110004', 'Zona 4', '123456789004'),
('José', 'Hernández', 'jhernandez@mail.com', '50110005', 'Zona 5', '123456789005'),
('Lucía', 'López', 'llopez@mail.com', '50110006', 'Zona 6', '123456789006'),
('Diego', 'Ramírez', 'dramirez@mail.com', '50110007', 'Zona 7', '123456789007'),
('Carmen', 'Torres', 'ctorres@mail.com', '50110008', 'Zona 8', '123456789008'),
('David', 'Flores', 'dflores@mail.com', '50110009', 'Zona 9', '123456789009'),
('Sandra', 'Ruiz', 'sruiz@mail.com', '50110010', 'Zona 10', '123456789010');

-- Grados
INSERT INTO grado (nombre_grado) VALUES
('1ro Básico'), ('2do Básico'), ('3ro Básico'), ('4to Bachillerato'), ('5to Bachillerato'),
('6to Bachillerato'), ('Primero Primaria'), ('Segundo Primaria'), ('Tercero Primaria'), ('Cuarto Primaria');

-- Secciones
INSERT INTO seccion (nombre_seccion) VALUES
('A'), ('B'), ('C'), ('D'), ('E'), ('F'), ('G'), ('H'), ('I'), ('J');

-- Grado_Sección
INSERT INTO grado_seccion (fk_id_grado, fk_id_seccion) VALUES
(1,1), (2,2), (3,3), (4,4), (5,5), (6,6), (7,7), (8,8), (9,9), (10,10);

-- Alumnos
INSERT INTO alumno (nombre_alumno, apellido_alumno, genero_alumno, fk_id_encargado, fk_id_grado_seccion) VALUES
('Juan', 'Pérez', 'Masculino', 1, 1),
('María', 'Gómez', 'Femenino', 2, 2),
('Luis', 'Ramírez', 'Masculino', 3, 3),
('Ana', 'López', 'Femenino', 4, 4),
('Carlos', 'Martínez', 'Masculino', 5, 5),
('Lucía', 'Hernández', 'Femenino', 6, 6),
('David', 'Torres', 'Masculino', 7, 7),
('Sandra', 'Flores', 'Femenino', 8, 8),
('Pedro', 'Ruiz', 'Masculino', 9, 9),
('Carmen', 'Castillo', 'Femenino', 10, 10);

-- Docentes
INSERT INTO docente (nombre_docente, apellido_docente, correo_docente, telefono_docente, fk_id_usuario) VALUES
('Mario', 'Cruz', 'mario.cruz@mail.com', '55100001', 1),
('Julia', 'Vega', 'julia.vega@mail.com', '55100002', 2),
('Pablo', 'Mendoza', 'pablo.m@mail.com', '55100003', 3),
('Rosa', 'Aguilar', 'rosa.a@mail.com', '55100004', 4),
('Andrés', 'Morales', 'andres.m@mail.com', '55100005', 5),
('Gabriela', 'Campos', 'gabriela.c@mail.com', '55100006', 6),
('Jorge', 'Navas', 'jorge.n@mail.com', '55100007', 7),
('Elena', 'Zamora', 'elena.z@mail.com', '55100008', 8),
('Sergio', 'Palma', 'sergio.p@mail.com', '55100009', 9),
('Diana', 'Reyes', 'diana.r@mail.com', '55100010', 10);

-- Asignaturas
INSERT INTO asignatura (nombre_asignatura, descripcion_asignatura, fk_id_docente) VALUES
('Matemática', 'Operaciones básicas y álgebra', 1),
('Lenguaje', 'Ortografía y gramática', 2),
('Ciencias Naturales', 'Biología básica', 3),
('Estudios Sociales', 'Historia y geografía', 4),
('Inglés', 'Vocabulario y estructuras', 5),
('Educación Física', 'Ejercicio y salud', 6),
('Arte', 'Técnicas básicas de dibujo', 7),
('Computación', 'Uso básico de computadoras', 8),
('Música', 'Notas y ritmo', 9),
('Moral y Cívica', 'Valores y normas sociales', 10);

-- Unidades
INSERT INTO unidad (nombre_unidad, descripcion, fk_id_asignatura) VALUES
('Unidad 1', 'Contenido de unidad 1', 1),
('Unidad 2', 'Contenido de unidad 2', 2),
('Unidad 3', 'Contenido de unidad 3', 3),
('Unidad 4', 'Contenido de unidad 4', 4),
('Unidad 5', 'Contenido de unidad 5', 5),
('Unidad 6', 'Contenido de unidad 6', 6),
('Unidad 7', 'Contenido de unidad 7', 7),
('Unidad 8', 'Contenido de unidad 8', 8),
('Unidad 9', 'Contenido de unidad 9', 9),
('Unidad 10', 'Contenido de unidad 10', 10);

-- Notas
INSERT INTO notas (nota, descripcion, fk_id_alumno, fk_id_grado_seccion, fk_id_docente, fk_id_asignatura, fk_id_unidad) VALUES
(80.50, 'Evaluación 1', 1, 1, 1, 1, 1),
(89.75, 'Evaluación 2', 2, 2, 2, 2, 2),
(70.20, 'Evaluación 3', 3, 3, 3, 3, 3),
(85.00, 'Evaluación 4', 4, 4, 4, 4, 4),
(78.90, 'Evaluación 5', 5, 5, 5, 5, 5),
(92.40, 'Evaluación 6', 6, 6, 6, 6, 6),
(74.30, 'Evaluación 7', 7, 7, 7, 7, 7),
(88.80, 'Evaluación 8', 8, 8, 8, 8, 8),
(69.90, 'Evaluación 9', 9, 9, 9, 9, 9),
(95.60, 'Evaluación 10', 10, 10, 10, 10, 10);