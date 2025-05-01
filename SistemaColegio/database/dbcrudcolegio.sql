-- crear base de datos
create database IF NOT EXISTS dbcrudcolegio;

-- llamar bd
use dbcrudcolegio;

-- tabla usuarios
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    pass VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    rol ENUM('director', 'docente', 'admin') DEFAULT 'docente',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- crear tabla de encargado
create table IF NOT EXISTS encargado(
id_encargado INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
nombre_encargado varchar (50) NOT NULL,
apellido_encargado varchar (50) NOT NULL,
correo_encargado varchar (100)NOT NULL,
telefono_encargado int NOT NULL,
direccion_encargado char (50) NOT NULL,
dpi_encargado int NOT NULL
);

-- crear tabla de grado
create table IF NOT EXISTS grado (
id_grado int auto_increment not null primary key,
grado varchar (50) not null,
seccion varchar (10) not null   
);

-- crear tabla de alumno
create table IF NOT EXISTS alumno (
id_alumno INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
nombre_alumno varchar (50) NOT NULL,
apellido_alumno varchar (50) NOT NULL,
gmail_alumno char (30) NULL,
telefono_alumno int NOT NULL,
genero_alumno char(1) NOT NULL,

fk_id_encargado int NOT NULL,
fk_id_grado int not null,
FOREIGN KEY (fk_id_grado) REFERENCES grado(id_grado),
FOREIGN KEY (fk_id_encargado) REFERENCES encargado(id_encargado)
);

-- crear tabla de docentes
CREATE TABLE IF NOT EXISTS docentes (
    id_docente INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(50) NOT NULL,
    nombre_docente VARCHAR(50) NOT NULL, 
    apellido_docente VARCHAR(50) NOT NULL, 
    email_docente VARCHAR(100) NOT NULL,
    telefono_docente VARCHAR(15) NOT NULL,
    genero_docente VARCHAR(50) NOT NULL,
    direccion_docente VARCHAR(100) NOT NULL,
    dpi_docente VARCHAR(20) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    fk_id_grado INT NOT NULL,
    fk_id_usuario INT NOT NULL,
    FOREIGN KEY (fk_id_grado) REFERENCES grado(id_grado),
    FOREIGN KEY (fk_id_usuario) REFERENCES usuarios(id_usuario)
);


-- crear tabla de asignatura
CREATE TABLE IF NOT EXISTS asignatura (
    id_asignatura INT AUTO_INCREMENT PRIMARY KEY, 
    nombre_asignatura VARCHAR(50) NOT NULL,
    descripcion_asignatura TEXT,
    fk_id_docente INT NOT NULL,
    FOREIGN KEY (fk_id_docente) REFERENCES docentes(id_docente)
);


-- tabla unidad
create table IF NOT EXISTS unidad (
id_unidad int auto_increment not null primary key,
unidad varchar (50) not null
);

CREATE TABLE IF NOT EXISTS notas (
	id_notas INT AUTO_INCREMENT PRIMARY KEY,
	id_docente INT NOT NULL,
	id_asignatura INT NOT NULL,
	id_unidad INT NOT NULL,
	nota DECIMAL(5,2) NOT NULL,
	FOREIGN KEY (id_docente) REFERENCES docentes(id_docente),
	FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura),
	FOREIGN KEY (id_unidad) REFERENCES unidad(id_unidad)
);

-- crear tabla de tarjeta de calificaciones
CREATE TABLE IF NOT EXISTS tarjeta_calificaciones (
    id_calificaciones INT AUTO_INCREMENT PRIMARY KEY,
    fk_id_unidad INT NOT NULL,
    fk_id_alumno INT NOT NULL,
    fk_id_grado INT NOT NULL,
    fk_id_docente INT NOT NULL,
    fk_id_asignatura INT NOT NULL,
    fk_id_notas INT NOT NULL,
    descripcion TEXT NOT NULL,
    FOREIGN KEY (fk_id_unidad) REFERENCES unidad(id_unidad),
    FOREIGN KEY (fk_id_alumno) REFERENCES alumno(id_alumno),
    FOREIGN KEY (fk_id_grado) REFERENCES grado(id_grado),
    FOREIGN KEY (fk_id_docente) REFERENCES docentes(id_docente),
    FOREIGN KEY (fk_id_asignatura) REFERENCES asignatura(id_asignatura),
    FOREIGN KEY (fk_id_notas) REFERENCES notas(id_notas)
);



