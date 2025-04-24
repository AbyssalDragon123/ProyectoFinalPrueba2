create database dbcrudcolegio;

use dbcrudcolegio;

create table estudiantes (
id_alumno INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
nombre_alumno varchar (50) NOT NULL,
apellido_alumno varchar (50) NOT NULL,
grado_alumno varchar (100)NOT NULL,
gmail_alumno char (30)NOT NULL,
telefono_alumno int NOT NULL,
genero char(1) NOT NULL
);

CREATE TABLE docentes (
    id_docente INT AUTO_INCREMENT NOT NULL PRIMARY KEY, 
    nombre_docente VARCHAR(50) NOT NULL, 
    apellido_docente VARCHAR(50) NOT NULL, 
    email_docente VARCHAR(100)NOT NULL,
    telefono_docente VARCHAR(15)NOT NULL,
    asignatura VARCHAR(100) NOT NULL
);

CREATE TABLE asignaturas (
    id_asignatura INT AUTO_INCREMENT NOT NULL PRIMARY KEY, 
    nombre_asignatura VARCHAR(50) NOT NULL,
    descripcion_asignatura TEXT, 
    id_docente INT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docentes(id_docente) 
);

CREATE TABLE notas (
    id_nota INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    id_alumno INT NOT NULL, 
    id_asignatura INT NOT NULL,
    nota DECIMAL(5,2) NOT NULL,
    fecha_evaluacion DATE NOT NULL,
    FOREIGN KEY (id_asignatura) REFERENCES asignaturas(id_asignatura),
    FOREIGN KEY (id_alumno) REFERENCES estudiantes(id_alumno)
);

