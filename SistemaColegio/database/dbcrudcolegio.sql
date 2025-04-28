-- crear base de datos
create database dbcrudcolegio;

-- llamar bd
use dbcrudcolegio;

-- tabla usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY not null,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    rol ENUM('director', 'docente','admin') DEFAULT 'docente',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table roles (
id_rol int auto_increment  primary key not null,
username char (50) not null,
fk_id_usuario INT not null,
rol varchar (50) not null,
FOREIGN KEY (fk_id_usuario) REFERENCES usuarios(id_usuario)
);

-- crear tabla de encargado
create table encargado(
id_encargado INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
nombre_encargado varchar (50) NOT NULL,
apellido_encargado varchar (50) NOT NULL,
correo_encargado varchar (100)NOT NULL,
telefono_encargado int NOT NULL,
direccion_encargado char (50) NOT NULL,
dpi_encargado int NOT NULL
);

-- crear tabla de grado
create table grado (
id_grado int auto_increment not null primary key,
grado varchar (50) not null,
seccion varchar (10) not null   
);

-- crear tabla de alumno
create table alumno (
id_alumno INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
nombre_alumno varchar (50) NOT NULL,
apellido_alumno varchar (50) NOT NULL,
gmail_alumno char (30) NULL,
telefono_alumno int NOT NULL,
genero_alumno char(1) NOT NULL,

fk_id_encargado int NOT NULL,
nombre_encargado varchar (50) NOT NULL,
telefono_encargado int NOT NULL,

fk_id_grado int not null,
grado varchar (50) not null,
seccion varchar (5) not null,

FOREIGN KEY (fk_id_grado) REFERENCES grado(id_grado),
FOREIGN KEY (fk_id_encargado) REFERENCES encargado(id_encargado)
);

-- crear tabla de docentes
CREATE TABLE docentes (
    id_docente INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    cedula varchar (50) NOT NULL,
    nombre_docente VARCHAR(50) NOT NULL, 
    apellido_docente VARCHAR(50) NOT NULL, 
    email_docente char(100)NOT NULL,
    telefono_docente int NOT NULL,
    genero_docente varchar(50) NOT NULL,
    direccion_docente varchar (100) NOT NULL,
    dpi_docente int NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    
    fk_id_grado int not null,
	grado varchar (50) not null,
	seccion varchar (5) not null,
    
    fk_id_rol int not null,
    username VARCHAR(50) NOT NULL,
    
	FOREIGN KEY (fk_id_grado) REFERENCES grado(id_grado),
    FOREIGN KEY (fk_id_rol) REFERENCES roles(id_rol)
);

-- crear tabla de asignatura
CREATE TABLE asignatura (
    id_asignatura INT AUTO_INCREMENT NOT NULL PRIMARY KEY, 
    nombre_asignatura VARCHAR(50) NOT NULL,
    descripcion_asignatura TEXT,
    
    fk_id_docente int not null,
    nombre_docente varchar(50) NOT NULL,
    apellido_docente varchar(50) NOT NULL,
    especialidad_docente varchar (50) not null,
    
    FOREIGN KEY (fk_id_docente) REFERENCES docentes(id_docente)
);

-- tabla unidad
create table unidad (
id_unidad int auto_increment not null primary key,
unidad char (50) not null
);

create table notas (
	id_notas int not null auto_increment primary key,
	id_docente int not null,
	nombre_docente varchar (50) not null,
	id_asignatura INT not null,
	nombre_asignatura VARCHAR(50) NOT NULL,
	nota VARCHAR(50) NOT NULL,
	id_unidad int not null,
	unidad int not null
);

-- crear tabla de tarjeta de calificaciones
CREATE TABLE tarjeta_calificaciones (
    id_calificaciones INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    
    fk_id_unidad int  not null,
	unidad varchar (50) not null,
    
    fk_id_alumno int not null,
    nombre_alumno varchar(50) NOT NULL,
    apellido_alumno varchar(50) not null,
    
    fk_id_grado int  NOT NULL,
	grado varchar (50) not null,
	seccion varchar (5) not null,
    
    fk_id_docente int not null,
    nombre_docente varchar(50) NOT NULL,
    apellido_docente varchar(50) NOT NULL,
    
    fk_id_asignatura int not null,
    nombre_asignatura varchar(50) NOT NULL,
    
    fk_id_notas int not null,
    nota VARCHAR(50) NOT NULL,
    
    descripcion text not null,
    
    FOREIGN KEY (fk_id_notas) REFERENCES notas(id_notas),
    FOREIGN KEY (fk_id_grado) REFERENCES grado(id_grado),    
	FOREIGN KEY (fk_id_unidad) REFERENCES unidad(id_unidad),
    FOREIGN KEY (fk_id_docente) REFERENCES docentes(id_docente),
    FOREIGN KEY (fk_id_asignatura) REFERENCES asignatura(id_asignatura),
	FOREIGN KEY (fk_id_alumno) REFERENCES alumno(id_alumno)
);




