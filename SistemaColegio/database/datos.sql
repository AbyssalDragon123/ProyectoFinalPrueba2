-- Tabla usuario
INSERT INTO usuario (id_usuario, username, pass, email, rol) VALUES
(1, 'director1', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'dir1@correo.com', 'director'),
(2, 'docente1', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'doc1@correo.com', 'docente'),
(3, 'docente2', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'doc2@correo.com', 'docente'),
(4, 'admin1', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'admin1@correo.com', 'admin'),
(5, 'docente3', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'doc3@correo.com', 'docente'),
(6, 'docente4', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'doc4@correo.com', 'docente'),
(7, 'docente5', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'doc5@correo.com', 'docente'),
(8, 'director2', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'dir2@correo.com', 'director'),
(9, 'admin2', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'admin2@correo.com', 'admin'),
(10, 'docente6', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'doc6@correo.com', 'docente');

-- Tabla encargado
INSERT INTO encargado (id_encargado, nombre_encargado, apellido_encargado, correo_encargado, telefono_encargado, direccion_encargado, dpi_encargado) VALUES
(1, 'Carlos', 'Ramirez', 'carlos@correo.com', '5551234567', 'Zona 1', '1234567890101'),
(2, 'Lucia', 'Gomez', 'lucia@correo.com', '5551234568', 'Zona 2', '1234567890102'),
(3, 'Mario', 'Lopez', 'mario@correo.com', '5551234569', 'Zona 3', '1234567890103'),
(4, 'Ana', 'Diaz', 'ana@correo.com', '5551234570', 'Zona 4', '1234567890104'),
(5, 'Luis', 'Fernandez', 'luis@correo.com', '5551234571', 'Zona 5', '1234567890105'),
(6, 'Laura', 'Martinez', 'laura@correo.com', '5551234572', 'Zona 6', '1234567890106'),
(7, 'Pedro', 'Reyes', 'pedro@correo.com', '5551234573', 'Zona 7', '1234567890107'),
(8, 'Sofia', 'Castillo', 'sofia@correo.com', '5551234574', 'Zona 8', '1234567890108'),
(9, 'Andres', 'Ortiz', 'andres@correo.com', '5551234575', 'Zona 9', '1234567890109'),
(10, 'Valeria', 'Ruiz', 'valeria@correo.com', '5551234576', 'Zona 10', '1234567890110');

-- Tabla grado_seccion
INSERT INTO grado_seccion (id_grado_seccion, grado, seccion) VALUES
(1, 'Primero Básico', 'A'),
(2, 'Segundo Básico', 'B'),
(3, 'Tercero Básico', 'C'),
(4, 'Cuarto Diversificado', 'A'),
(5, 'Quinto Diversificado', 'B'),
(6, 'Sexto Diversificado', 'C'),
(7, 'Primero Primaria', 'A'),
(8, 'Segundo Primaria', 'B'),
(9, 'Tercero Primaria', 'C'),
(10, 'Cuarto Primaria', 'A');

-- Tabla alumno
INSERT INTO alumno (id_alumno, nombre_alumno, apellido_alumno, genero_alumno, fk_id_encargado, fk_id_grado_seccion) VALUES
(1, 'Diego', 'Perez', 'M', 1, 1),
(2, 'María', 'Lopez', 'F', 2, 2),
(3, 'José', 'García', 'M', 3, 3),
(4, 'Ana', 'Martínez', 'F', 4, 4),
(5, 'Luis', 'Santos', 'M', 5, 5),
(6, 'Elena', 'Ramirez', 'F', 6, 6),
(7, 'Carlos', 'Gómez', 'M', 7, 7),
(8, 'Lucía', 'Torres', 'F', 8, 8),
(9, 'Juan', 'Cruz', 'M', 9, 9),
(10, 'Valentina', 'Morales', 'F', 10, 10);

-- Tabla docente
INSERT INTO docente (id_docente, nombre_docente, apellido_docente, correo_docente, telefono_docente, fk_id_usuario) VALUES
(1, 'Roberto', 'Alvarez', 'roberto@correo.com', '5558881111', 2),
(2, 'Andrea', 'Mendez', 'andrea@correo.com', '5558881112', 3),
(3, 'Sergio', 'Delgado', 'sergio@correo.com', '5558881113', 5),
(4, 'Patricia', 'Lopez', 'patricia@correo.com', '5558881114', 6),
(5, 'Miguel', 'Cano', 'miguel@correo.com', '5558881115', 7),
(6, 'Rosa', 'Perez', 'rosa@correo.com', '5558881116', 10),
(7, 'Hector', 'Jimenez', 'hector@correo.com', '5558881117', 2),
(8, 'Luisa', 'Rojas', 'luisa@correo.com', '5558881118', 3),
(9, 'David', 'Herrera', 'david@correo.com', '5558881119', 5),
(10, 'Claudia', 'Navarro', 'claudia@correo.com', '5558881120', 6);

-- Tabla asignatura
INSERT INTO asignatura (id_asignatura, nombre_asignatura, descripcion_asignatura, fk_id_docente) VALUES
(1, 'Matemática', 'Cálculo y álgebra', 1),
(2, 'Lenguaje', 'Ortografía y redacción', 2),
(3, 'Ciencias Naturales', 'Biología y química básica', 3),
(4, 'Sociales', 'Historia y geografía', 4),
(5, 'Inglés', 'Nivel básico', 5),
(6, 'Computación', 'Ofimática e internet', 6),
(7, 'Física', 'Mecánica y electricidad', 7),
(8, 'Música', 'Teoría y práctica musical', 8),
(9, 'Arte', 'Dibujo y pintura', 9),
(10, 'Educación Física', 'Actividades físicas y salud', 10);

-- Tabla unidad
INSERT INTO unidad (id_unidad, unidad) VALUES
(1, 'Unidad I'),
(2, 'Unidad II'),
(3, 'Unidad III'),
(4, 'Unidad IV'),
(5, 'Unidad V'),
(6, 'Unidad VI'),
(7, 'Unidad VII'),
(8, 'Unidad VIII'),
(9, 'Unidad IX'),
(10, 'Unidad X');

-- Tabla notas
INSERT INTO notas (id_notas, nota, descripcion, fk_id_alumno, fk_id_grado_seccion, fk_id_docente, fk_id_asignatura, fk_id_unidad) VALUES
(1, 85.00, 'Buen desempeño', 1, 1, 1, 1, 1),
(2, 90.00, 'Excelente trabajo', 2, 2, 2, 2, 2),
(3, 70.00, 'Puede mejorar', 3, 3, 3, 3, 3),
(4, 88.50, 'Participativa', 4, 4, 4, 4, 4),
(5, 95.00, 'Muy destacada', 5, 5, 5, 5, 5),
(6, 78.25, 'Regular', 6, 6, 6, 6, 6),
(7, 60.00, 'Reprobado', 7, 7, 7, 7, 7),
(8, 82.00, 'Cumplió con lo requerido', 8, 8, 8, 8, 8),
(9, 67.50, 'Necesita apoyo', 9, 9, 9, 9, 9),
(10, 91.00, 'Excelente rendimiento', 10, 10, 10, 10, 10);
