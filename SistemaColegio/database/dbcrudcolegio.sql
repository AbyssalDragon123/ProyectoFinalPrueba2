-- Crear base de datos
CREATE DATABASE IF NOT EXISTS dbcrudcolegio;
USE dbcrudcolegio;

-- Tabla usuario
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    pass VARCHAR(255) NOT NULL,
    correo VARCHAR(100) UNIQUE,
    rol ENUM('docente', 'admin') DEFAULT 'docente'
);

-- Tabla encargado
CREATE TABLE IF NOT EXISTS encargado (
    id_encargado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_encargado VARCHAR(50) NOT NULL,
    apellido_encargado VARCHAR(50) NOT NULL,
    correo_encargado VARCHAR(100) NOT NULL,
    telefono_encargado VARCHAR(20) NOT NULL,
    direccion_encargado VARCHAR(50) NOT NULL,
    dpi_encargado VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla aula
CREATE TABLE IF NOT EXISTS aula (
    id_aula INT AUTO_INCREMENT PRIMARY KEY,
    grado VARCHAR(50) NOT NULL,
    seccion VARCHAR(50) NOT NULL
);

-- Tabla alumno
CREATE TABLE IF NOT EXISTS alumno (
    id_alumno INT AUTO_INCREMENT PRIMARY KEY,
    nombre_alumno VARCHAR(50) NOT NULL,
    apellido_alumno VARCHAR(50) NOT NULL,
    genero_alumno VARCHAR(10) NOT NULL,
    fk_id_encargado INT NOT NULL,
    fk_id_aula INT NOT NULL,
    FOREIGN KEY (fk_id_encargado) REFERENCES encargado(id_encargado) ON DELETE CASCADE,
    FOREIGN KEY (fk_id_aula) REFERENCES aula(id_aula) ON DELETE CASCADE
);

-- Tabla docente
CREATE TABLE IF NOT EXISTS docente (
    id_docente INT AUTO_INCREMENT PRIMARY KEY,
    nombre_docente VARCHAR(50) NOT NULL,
    apellido_docente VARCHAR(50) NOT NULL,
    correo_docente VARCHAR(100) NOT NULL,
    telefono_docente VARCHAR(15) NOT NULL,
    fk_id_usuario INT NOT NULL,
    FOREIGN KEY (fk_id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla asignatura
CREATE TABLE IF NOT EXISTS asignatura (
    id_asignatura INT AUTO_INCREMENT PRIMARY KEY,
    nombre_asignatura VARCHAR(50) NOT NULL,
    descripcion_asignatura TEXT,
    fk_id_docente INT NOT NULL,
    FOREIGN KEY (fk_id_docente) REFERENCES docente(id_docente) ON DELETE CASCADE
);

-- Tabla unidad
CREATE TABLE IF NOT EXISTS unidad (
    id_unidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_unidad VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fk_id_asignatura INT NOT NULL,
    FOREIGN KEY (fk_id_asignatura) REFERENCES asignatura(id_asignatura) ON DELETE CASCADE
);

-- Tabla notas
CREATE TABLE IF NOT EXISTS notas (
    id_notas INT AUTO_INCREMENT PRIMARY KEY,
    nota DECIMAL(5,2) NOT NULL,
    descripcion TEXT,
    fk_id_alumno INT NOT NULL,
    fk_id_aula INT NOT NULL,
    fk_id_docente INT NOT NULL,
    fk_id_asignatura INT NOT NULL,
    fk_id_unidad INT NOT NULL,
    FOREIGN KEY (fk_id_docente) REFERENCES docente(id_docente) ON DELETE CASCADE,
    FOREIGN KEY (fk_id_asignatura) REFERENCES asignatura(id_asignatura) ON DELETE CASCADE,
    FOREIGN KEY (fk_id_unidad) REFERENCES unidad(id_unidad) ON DELETE CASCADE,
    FOREIGN KEY (fk_id_alumno) REFERENCES alumno(id_alumno) ON DELETE CASCADE,
    FOREIGN KEY (fk_id_aula) REFERENCES aula(id_aula) ON DELETE CASCADE
);