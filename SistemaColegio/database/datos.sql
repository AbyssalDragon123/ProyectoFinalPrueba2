-- Insertar usuarios
INSERT INTO usuario (username, pass, email, rol) VALUES
('director1', 'pass123', 'dir1@colegio.com', 'director'),
('docente1', 'pass123', 'doc1@colegio.com', 'docente'),
('admin1', 'pass123', 'admin1@colegio.com', 'admin'),
('docente2', 'pass123', 'doc2@colegio.com', 'docente'),
('docente3', 'pass123', 'doc3@colegio.com', 'docente'),
('docente4', 'pass123', 'doc4@colegio.com', 'docente'),
('docente5', 'pass123', 'doc5@colegio.com', 'docente'),
('docente6', 'pass123', 'doc6@colegio.com', 'docente'),
('docente7', 'pass123', 'doc7@colegio.com', 'docente'),
('docente8', 'pass123', 'doc8@colegio.com', 'docente');

-- Insertar encargados
INSERT INTO encargado (nombre_encargado, apellido_encargado, correo_encargado, telefono_encargado, direccion_encargado, dpi_encargado) VALUES
('Carlos', 'Gomez', 'cgomez@correo.com', '5551111', 'Zona 1', '1234567890101'),
('Ana', 'Ramirez', 'aramirez@correo.com', '5552222', 'Zona 2', '1234567890102'),
('Luis', 'Perez', 'lperez@correo.com', '5553333', 'Zona 3', '1234567890103'),
('Marta', 'Lopez', 'mlopez@correo.com', '5554444', 'Zona 4', '1234567890104'),
('Jorge', 'Diaz', 'jdiaz@correo.com', '5555555', 'Zona 5', '1234567890105'),
('Laura', 'Soto', 'lsoto@correo.com', '5556666', 'Zona 6', '1234567890106'),
('Pedro', 'Juarez', 'pjuarez@correo.com', '5557777', 'Zona 7', '1234567890107'),
('Sofia', 'Martinez', 'smartinez@correo.com', '5558888', 'Zona 8', '1234567890108'),
('Ricardo', 'Ortiz', 'rortiz@correo.com', '5559999', 'Zona 9', '1234567890109'),
('Gabriela', 'Castillo', 'gcastillo@correo.com', '5550000', 'Zona 10', '1234567890110');

-- Insertar grados
INSERT INTO grado (grado, seccion) VALUES
('Primero Básico', 'A'),
('Segundo Básico', 'B'),
('Tercero Básico', 'A'),
('Cuarto Bachillerato', 'C'),
('Quinto Bachillerato', 'A'),
('Sexto Bachillerato', 'B'),
('Primero Primaria', 'A'),
('Segundo Primaria', 'B'),
('Tercero Primaria', 'C'),
('Cuarto Primaria', 'A');

-- Insertar alumnos
INSERT INTO alumno (nombre_alumno, apellido_alumno, gmail_alumno, telefono_alumno, genero_alumno, fk_id_encargado, fk_id_grado) VALUES
('Diego', 'López', 'dlopez@gmail.com', '5011111', 'M', 1, 1),
('Valeria', 'Hernández', 'vhernandez@gmail.com', '5022222', 'F', 2, 2),
('Mateo', 'Cruz', 'mcruz@gmail.com', '5033333', 'M', 3, 3),
('Camila', 'Reyes', 'creyes@gmail.com', '5044444', 'F', 4, 4),
('Sebastián', 'Ruiz', 'sruiz@gmail.com', '5055555', 'M', 5, 5),
('Isabella', 'Mejía', 'imejia@gmail.com', '5066666', 'F', 6, 6),
('Emilio', 'García', 'egarcia@gmail.com', '5077777', 'M', 7, 7),
('Renata', 'Flores', 'rflores@gmail.com', '5088888', 'F', 8, 8),
('Samuel', 'Mendoza', 'smendoza@gmail.com', '5099999', 'M', 9, 9),
('Lucía', 'Pérez', 'lperez@gmail.com', '5000000', 'F', 10, 10);

-- Insertar docentes
INSERT INTO docentes (cedula, nombre_docente, apellido_docente, email_docente, telefono_docente, genero_docente, direccion_docente, dpi_docente, especialidad, fk_id_grado, fk_id_usuario) VALUES
('CD001', 'Mario', 'Velásquez', 'mvelasquez@correo.com', '4411111', 'M', 'Zona 11', '3333333330001', 'Matemáticas', 1, 2),
('CD002', 'Paola', 'Estrada', 'pestrada@correo.com', '4422222', 'F', 'Zona 12', '3333333330002', 'Lenguaje', 2, 4),
('CD003', 'Luis', 'Cardona', 'lcardona@correo.com', '4433333', 'M', 'Zona 13', '3333333330003', 'Ciencias', 3, 5),
('CD004', 'Ingrid', 'Mena', 'imena@correo.com', '4444444', 'F', 'Zona 14', '3333333330004', 'Historia', 4, 6),
('CD005', 'Oscar', 'Bolaños', 'obolanos@correo.com', '4455555', 'M', 'Zona 15', '3333333330005', 'Física', 5, 7),
('CD006', 'Carmen', 'Salguero', 'csalguero@correo.com', '4466666', 'F', 'Zona 16', '3333333330006', 'Inglés', 6, 8),
('CD007', 'Daniel', 'Marín', 'dmarin@correo.com', '4477777', 'M', 'Zona 17', '3333333330007', 'Computación', 7, 9),
('CD008', 'Estela', 'Orozco', 'eorozco@correo.com', '4488888', 'F', 'Zona 18', '3333333330008', 'Biología', 8, 10),
('CD009', 'Roberto', 'Zamora', 'rzamora@correo.com', '4499999', 'M', 'Zona 19', '3333333330009', 'Química', 9, 3),
('CD010', 'María', 'Morales', 'mmorales@correo.com', '4400000', 'F', 'Zona 20', '3333333330010', 'Música', 10, 1);

-- Insertar asignaturas
INSERT INTO asignatura (nombre_asignatura, descripcion_asignatura, fk_id_docente) VALUES
('Matemáticas I', 'Operaciones básicas y álgebra', 1),
('Lenguaje I', 'Ortografía y redacción', 2),
('Ciencias Naturales I', 'Física y química básica', 3),
('Historia I', 'Historia de Guatemala', 4),
('Física I', 'Leyes del movimiento', 5),
('Inglés I', 'Gramática básica', 6),
('Computación I', 'Uso de software educativo', 7),
('Biología I', 'Clasificación de seres vivos', 8),
('Química I', 'Estructura de la materia', 9),
('Música I', 'Notas musicales y ritmo', 10);

-- Insertar unidades
INSERT INTO unidad (unidad) VALUES
('Unidad 1'), ('Unidad 2'), ('Unidad 3'), ('Unidad 4'), ('Unidad 5'),
('Unidad 6'), ('Unidad 7'), ('Unidad 8'), ('Unidad 9'), ('Unidad 10');

-- Insertar notas
INSERT INTO notas (id_docente, id_asignatura, id_unidad, nota) VALUES
(1, 1, 1, 85.5), (2, 2, 2, 88.0), (3, 3, 3, 90.0), (4, 4, 4, 91.2), (5, 5, 5, 92.3),
(6, 6, 6, 84.4), (7, 7, 7, 87.7), (8, 8, 8, 89.9), (9, 9, 9, 93.1), (10, 10, 10, 95.0);

-- Insertar tarjeta de calificaciones
INSERT INTO tarjeta_calificaciones (fk_id_unidad, fk_id_alumno, fk_id_grado, fk_id_docente, fk_id_asignatura, fk_id_notas, descripcion) VALUES
(1, 1, 1, 1, 1, 1, 'Buen desempeño en Matemáticas I'),
(2, 2, 2, 2, 2, 2, 'Participativa en Lenguaje I'),
(3, 3, 3, 3, 3, 3, 'Excelente en Ciencias Naturales I'),
(4, 4, 4, 4, 4, 4, 'Historiadora destacada'),
(5, 5, 5, 5, 5, 5, 'Curioso en Física I'),
(6, 6, 6, 6, 6, 6, 'Progreso en Inglés I'),
(7, 7, 7, 7, 7, 7, 'Habilidad en Computación'),
(8, 8, 8, 8, 8, 8, 'Buen conocimiento biológico'),
(9, 9, 9, 9, 9, 9, 'Analiza fórmulas químicas'),
(10, 10, 10, 10, 10, 10, 'Talento musical desarrollado');
