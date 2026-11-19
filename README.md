# API REST - Gestion de Gimnasio

API REST desarrollada con Spring Boot y Spring Data JPA para la administracion de clientes y rutinas de entrenamiento en un gimnasio, implementando una relacion de muchos a muchos (ManyToMany) con tabla intermedia, validacion de datos y documentacion interactiva con Swagger (SpringDoc OpenAPI).

---

## Descripcion del Proyecto

El sistema permite gestionar de manera integral el catalogo de rutinas y el registro de clientes del gimnasio. Su caracteristica principal es el modelado bidireccional Many-to-Many entre clientes y rutinas, permitiendo que un cliente tenga multiples rutinas asignadas y que una rutina sea compartida por multiples clientes mediante la tabla intermedia `cliente_rutina`.

La API esta disenada bajo un enfoque desacoplado utilizando Data Transfer Objects (DTO) para evitar ciclos de serializacion JSON, con respuestas HTTP consistentes (`ResponseEntity`) y sin controladores globales de excepciones complejos.

---

## Tecnologias Utilizadas

- Lenguaje: Java 17
- Framework: Spring Boot 4 / Spring Boot 3
- Persistencia: Spring Data JPA / Hibernate
- Motor de Base de Datos: MySQL 8+
- Driver JDBC: MySQL Connector/J
- Validacion: Jakarta Bean Validation (Hibernate Validator)
- Documentacion API: SpringDoc OpenAPI 3 (Swagger UI)
- Gestor de Dependencias: Apache Maven (Maven Wrapper)

---

## Arquitectura y Estructura del Proyecto

Estructura de paquetes base: `campus.randolh.gestion_gimnasio`

```text
campus.randolh.gestion_gimnasio
├── config
│   ├── DataLoader.java        (Precarga automatica de datos)
│   └── OpenAPIConfig.java     (Metadatos de documentacion Swagger)
├── controller
│   ├── ClienteController.java (Endpoints REST para clientes y relacion)
│   └── RutinaController.java  (Endpoints REST para rutinas)
├── dto
│   ├── ClienteRequestDTO.java
│   ├── ClienteResponseDTO.java
│   ├── RutinaRequestDTO.java
│   └── RutinaResponseDTO.java
├── entity
│   ├── Cliente.java           (Entidad propietaria de la relacion)
│   └── Rutina.java            (Entidad inversa)
├── repository
│   ├── ClienteRepository.java
│   └── RutinaRepository.java
├── service
│   ├── ClienteService.java
│   └── RutinaService.java
└── GestionGimnasioApplication.java
```

---

## Modelo de Datos (Relacion Many-to-Many)

- **Entidad `Cliente`:** Representa a los usuarios del gimnasio (`id`, `nombre`, `documento`, `activo`). Define la configuracion `@JoinTable` hacia la tabla `cliente_rutina`.
- **Entidad `Rutina`:** Representa los planes de entrenamiento (`id`, `nombre`, `nivel`). Mapeada de forma bidireccional mediante `mappedBy = "rutinas"`.
- **Tabla Intermedia `cliente_rutina`:** Almacena las claves foraneas `cliente_id` y `rutina_id` que vinculan a ambas entidades.

---

## Requisitos Previos

- Java Development Kit (JDK) 17 o superior instalado.
- Servidor MySQL activo y accesible en el puerto 3306.
- Git instalado en el sistema.

---

## Instrucciones de Instalacion y Despliegue

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Randolh/gestion_gimnasio.git
cd gestion_gimnasio
```

### 2. Configuracion de la Base de Datos

Accede a tu gestor de base de datos MySQL (consola, MySQL Workbench, DBeaver) y crea la base de datos:

```sql
CREATE DATABASE IF NOT EXISTS db_gimnasio CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configuracion de application.properties

Por seguridad, el archivo de configuracion local `application.properties` se encuentra ignorado en `.gitignore`. Debes crearlo a partir de la plantilla `application.properties.example`:

En Linux / macOS:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

En Windows (PowerShell):
```powershell
Copy-Item src/main/resources/application.properties.example src/main/resources/application.properties
```

A continuacion, edita `src/main/resources/application.properties` con tus credenciales de MySQL:

```properties
spring.application.name=gestion_gimnasio

# Configuracion base de datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/db_gimnasio
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuracion JPA Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Rutas Swagger UI y OpenAPI
springdoc.swagger-ui.path=/swagger-ui/index.html
springdoc.api-docs.path=/v3/api-docs
```

### 4. Compilar y Ejecutar

Utiliza el Maven Wrapper incluido para compilar y levantar el proyecto:

En Linux / macOS:
```bash
./mvnw clean compile
./mvnw spring-boot:run
```

En Windows:
```cmd
mvnw.cmd clean compile
mvnw.cmd spring-boot:run
```

La aplicacion iniciara por defecto en el puerto `8080`.

---

## Documentacion Interactiva (Swagger UI)

Una vez levantada la aplicacion, la documentacion interactiva de la API con Swagger OpenAPI esta disponible en la siguiente URL:

```text
http://localhost:8080/swagger-ui/index.html
```

La especificacion OpenAPI en formato JSON se encuentra en:

```text
http://localhost:8080/v3/api-docs
```

---

## Resumen de Endpoints de la API

### Modulo: Rutinas (`/api/rutinas`)

| Metodo | Endpoint | Descripcion | Codigos HTTP |
| :--- | :--- | :--- | :--- |
| POST | `/api/rutinas` | Registrar una nueva rutina | 201 Created, 400 Bad Request |
| GET | `/api/rutinas` | Listar todas las rutinas | 200 OK |
| GET | `/api/rutinas/{id}` | Obtener rutina por identificador | 200 OK, 404 Not Found |
| PUT | `/api/rutinas/{id}` | Actualizar datos de una rutina | 200 OK, 404 Not Found |
| DELETE | `/api/rutinas/{id}` | Eliminar una rutina | 204 No Content, 404 Not Found |
| GET | `/api/rutinas/{rutinaId}/clientes` | Listar clientes asignados a una rutina | 200 OK, 404 Not Found |

### Modulo: Clientes (`/api/clientes`)

| Metodo | Endpoint | Descripcion | Codigos HTTP |
| :--- | :--- | :--- | :--- |
| POST | `/api/clientes` | Registrar un nuevo cliente | 201 Created, 400 Bad Request |
| GET | `/api/clientes` | Listar todos los clientes | 200 OK |
| GET | `/api/clientes/{id}` | Obtener cliente por identificador | 200 OK, 404 Not Found |
| PUT | `/api/clientes/{id}` | Actualizar datos de un cliente | 200 OK, 404 Not Found |
| DELETE | `/api/clientes/{id}` | Eliminar un cliente | 204 No Content, 404 Not Found |
| POST | `/api/clientes/{clienteId}/rutinas/{rutinaId}` | Asignar una rutina a un cliente | 200 OK, 404 Not Found |
| GET | `/api/clientes/{clienteId}/rutinas` | Listar rutinas asignadas a un cliente | 200 OK, 404 Not Found |
| DELETE | `/api/clientes/{clienteId}/rutinas/{rutinaId}` | Desvincular una rutina de un cliente | 200 OK, 404 Not Found |

---

## Datos Precargados de Prueba

La aplicacion incluye la clase `DataLoader` (`CommandLineRunner`), la cual inicializa automaticamente datos de prueba al arrancar el servicio por primera vez si la base de datos se encuentra vacia:

- **Rutinas iniciales:**
  - `ID 1`: Pierna Hipertrofia (Nivel: INTERMEDIO)
  - `ID 2`: Cardio Funcional (Nivel: BASICO)
  - `ID 3`: Espalda y Biceps (Nivel: AVANZADO)
- **Clientes iniciales:**
  - `ID 1`: Carlos Perez (Documento: 1001, Activo: true) - Rutinas asignadas: 1 y 2.
  - `ID 2`: Maria Gomez (Documento: 1002, Activo: true) - Rutina asignada: 1.
  - `ID 3`: Juan Rodriguez (Documento: 1003, Activo: false) - Sin rutinas asignadas.

---

## Demostracion en Video

A continuacion se presenta el enlace con la grabacion demostrativa del funcionamiento de la API, explicando la estructura del proyecto, la relacion Many-to-Many en base de datos y la ejecucion de pruebas de cada endpoint en Swagger UI:

- [VIDEO]()