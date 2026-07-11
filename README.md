# DocuCloud

Sistema de Gestión Documental Empresarial (API REST) para la administración de archivos, flujos de trabajo y auditoría.

---

## Resumen técnico

| Componente | Tecnología |
|---|---|
| **Backend** | Spring Boot (Java 21) |
| **Frontend** | No incluido (Solo API) |
| **Base de datos** | PostgreSQL |
| **ORM** | Spring Data JPA / Hibernate |
| **Autenticación** | JWT (JSON Web Tokens) |
| **Despliegue** | Local (Base de datos alojada en Supabase) |

---

## Características

* **Gestión de Archivos**: Creación y organización de documentos, carpetas y versiones de documentos.
* **Flujos de Trabajo (Workflows)**: Definición de flujos, pasos y asignación de tareas a flujos documentales.
* **Gestión de Acceso y Usuarios**: Manejo de usuarios, organizaciones y roles.
* **Auditoría**: Registro de acciones y cambios mediante un módulo de auditoría integrado.
* **Seguridad**: Autenticación mediante tokens JWT y encriptación de contraseñas.
* **Documentación de API**: Interfaz de Swagger/OpenAPI integrada.

---

## Arquitectura

El backend se organiza en tres paquetes fundamentales: `bussinesLayer` (contiene interfaces, implementaciones de servicios y DTOs), `config` (configuración de CORS, OpenAPI y seguridad) y `persistenceLayer` (agrupa de forma conjunta controladores REST, entidades, DAOs, repositorios y mappers). El sistema funciona como una API REST independiente (sin frontend acoplado). 

El flujo principal de datos sigue este patrón: la solicitud HTTP ingresa a través del filtro de seguridad, es despachada al controlador correspondiente en la capa de persistencia, se transfiere al servicio en la capa de negocio para aplicar las reglas de validación y finalmente interactúa con los repositorios de Spring Data JPA para consultar la base de datos PostgreSQL.

---

## Flujo de la aplicación

1. El cliente realiza una petición HTTP adjuntando un token en el encabezado `Authorization`.
2. El `JwtAuthenticationFilter` intercepta la petición, verifica la firma del token y carga el contexto de seguridad.
3. La solicitud llega al controlador (ej. `DocumentoController`) el cual recibe los parámetros y delega la responsabilidad.
4. Un servicio en la capa de negocio (`bussinesLayer`) ejecuta la lógica correspondiente y utiliza MapStruct para convertir DTOs a Entidades.
5. El servicio se comunica con la base de datos PostgreSQL a través de las interfaces de repositorio (Spring Data JPA).
6. Los resultados obtenidos realizan el proceso inverso (Entidad a DTO) y el controlador emite una respuesta JSON al cliente.

---

## Tecnologías utilizadas

* **Lenguaje**: Java 21
* **Framework Principal**: Spring Boot 4.0.5
* **Módulos de Spring**: Spring WebMVC, Spring Data JPA, Spring Security
* **Base de datos**: PostgreSQL (Runtime) / H2 (Testing)
* **Seguridad**: jjwt (0.12.6) para JWT
* **Mapeo de Datos**: MapStruct (1.6.3)
* **Utilidades**: Lombok
* **Documentación API**: SpringDoc OpenAPI 3.0.2
* **Testing y Reportes**: JUnit, Allure (2.24.0), JaCoCo

---

## Módulos principales

* **Autenticación (`auth`)**: Login y emisión de JSON Web Tokens.
* **Organizaciones y Usuarios**: Gestión multi-inquilino simple y perfiles de usuario.
* **Roles**: Asignación de permisos mediante perfiles.
* **Archivos (`carpeta`, `documento`, `tipo_documento`, `version_documento`)**: Almacenamiento estructurado de los documentos y su historial.
* **Procesos (`flujo`, `flujo_paso`, `tarea_flujo`, `documento_flujo`)**: Aprobaciones y ciclo de vida de los documentos.
* **Auditoría (`auditoria`)**: Trazabilidad de operaciones críticas.

---

## Buenas prácticas implementadas

* **Separación por capas funcionales**: Lógica separada en controladores, servicios y repositorios.
* **Autenticación sin estado**: Uso de JWT para las sesiones.
* **Repository Pattern**: Abstracción de acceso a datos utilizando interfaces de JPA.
* **Mapeo de Objetos DTO**: Separación de entidades de dominio y objetos expuestos al cliente mediante MapStruct.
* **ORM**: Mapeo automático a base de datos con Hibernate.
* **Documentación autogenerada**: Integración de OpenAPI/Swagger.

---

## Estructura del proyecto

```text
src/main/java/com/eam/demo/
├── bussinesLayer/
│   ├── dto/
│   ├── impl/
│   └── service/
├── config/
│   └── security/
└── persistenceLayer/
    ├── controller/
    ├── dao/
    ├── entity/
    ├── mapper/
    └── repository/
```

---

## Requisitos previos

* Java 21 JDK (o superior)
* Gradle (incluido como Wrapper en el proyecto)

---

## Instalación

1. Clona el repositorio en tu máquina local.
2. Abre una terminal y navega a la carpeta raíz del proyecto.
3. Descarga las dependencias iniciales usando el wrapper de Gradle:

```bash
./gradlew build -x test
```

---

## Ejecución

El proyecto está configurado para ejecutarse en entorno local apuntando directamente a la base de datos PostgreSQL alojada.

Para iniciar el servidor, ejecuta:

```bash
./gradlew bootRun
```

La API estará disponible por defecto en el puerto `8080`.

---

## Scripts disponibles

A continuación se listan las tareas de ejecución de Gradle principales disponibles en este proyecto:

| Comando | Descripción |
|---|---|
| `./gradlew bootRun` | Inicia la aplicación Spring Boot en entorno de desarrollo. |
| `./gradlew build` | Compila el código, ejecuta las pruebas y empaqueta el artefacto. |
| `./gradlew test` | Ejecuta la batería de pruebas y genera reportes (JaCoCo/Allure). |
| `./gradlew clean` | Elimina el directorio `build/` y archivos compilados anteriores. |
