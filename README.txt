# 🏫 Sistema de Colegio LCT

Este es un proyecto completo de sistema escolar desarrollado para la gestión académica de un colegio. Permite el manejo de alumnos, encargados, docentes, asignaturas, notas, calificaciones y más. Utiliza tecnologías modernas como C# (API REST), Java (NetBeans) y MySQL.

---

## 🚀 Tecnologías Utilizadas

- **Java (NetBeans)** – Para la interfaz de usuario (cliente).
- **C# (.NET Core / ASP.NET)** – Para crear la API RESTful.
- **MySQL** – Como sistema de gestión de base de datos relacional.
- **Postman** – Para pruebas de endpoints de la API.

---

## 📋 Requisitos Previos

Antes de instalar el sistema, asegúrate de tener instalado:

- [Java JDK 8 o superior](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Apache NetBeans IDE](https://netbeans.apache.org/)
- [.NET SDK 6.0 o superior](https://dotnet.microsoft.com/download)
- [Visual Studio 2022](https://visualstudio.microsoft.com/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- Git (opcional, para clonar el repositorio)

---

## 🛠️ Instalación del Proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/AbyssalDragon123/ProyectoFinalPrueba2.git

2. Configurar la Base de Datos
Abrir MySQL Workbench.

Crear una base de datos nueva llamada dbcrudcolegio.

Ejecutar el script SQL 

3. Backend – API en C#
Abre la carpeta ColegioAPI en Visual Studio.

Verifica el archivo appsettings.json y configura la cadena de conexión a tu base de datos.

Ejecuta la API (presiona F5 o botón "Iniciar").

Deberías ver que se ejecuta en https://localhost:5148.

4. Frontend – Java NetBeans
Abre NetBeans y carga el proyecto ubicado en la carpeta ProyectoFinalPrueba2.

Asegúrate de que las clases de servicio (ServiceXXX.java) tengan la URL correcta de la API.

Ejecuta la aplicación desde la clase principal.

📄 Licencia
Este proyecto es de uso académico y está libre para su estudio y mejora con fines educativos. Para uso comercial, se requiere autorización previa.
