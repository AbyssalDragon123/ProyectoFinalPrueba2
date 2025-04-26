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
    genero_docente varchar(50) not null,
    asignatura VARCHAR(100) NOT NULL
);

CREATE TABLE asignatura (
    id_asignatura INT AUTO_INCREMENT NOT NULL PRIMARY KEY, 
    nombre_asignatura VARCHAR(50) NOT NULL,
    descripcion_asignatura TEXT,
	Horario_asignatura DATE NOT NULL,
    id_docente int not null,
    nombre_docente varchar(50) NOT NULL,
    apellido_docente varchar(50) not null,
    FOREIGN KEY (id_docente) REFERENCES docentes(id_docente)
);

CREATE TABLE notas (
    id_nota INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    id_alumno int not null,
    nombre_alumno varchar(50) NOT NULL,
    apellido_alumno varchar(50) not null,
    id_asignatura int not null,
    nombre_asignatura varchar(50) NOT NULL,
    nota DECIMAL(5,2) NOT NULL,
    fecha_evaluacion DATE NOT NULL,
    FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura),
     FOREIGN KEY (id_alumno) REFERENCES estudiantes(id_alumno)
);





