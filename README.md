# springboot-escalab-backend

Backend API REST para gestionar personas, categorias, productos y consultas.

Proyecto academico de Escalab, actualizado a Spring Boot 3.

## Stack

- Java 17
- Spring Boot 3.4.4
- Spring Security 6 + JWT (resource server)
- Spring Data JPA (Hibernate 6)
- PostgreSQL (Neon recomendado)
- OpenAPI/Swagger UI (`springdoc`)
- Maven Wrapper

## Requisitos

- JDK 17 instalado
- Maven no es obligatorio (se usa `mvnw`)
- Base de datos PostgreSQL (local o Neon)

## Configuracion

La configuracion principal esta en:

- `src/main/resources/application.properties`

Valores importantes:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `security.jwt.client-id`
- `security.jwt.client-secret`
- `app.security.jwt-secret`
- `app.security.jwt-expiration-seconds`

## Ejecutar proyecto

### Windows (PowerShell)

```powershell
.\mvnw.cmd spring-boot:run
```

Si el puerto `8080` esta ocupado:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

## Build

Compilar (sin correr tests):

```powershell
.\mvnw.cmd clean test -DskipTests
```

Empaquetar:

```powershell
.\mvnw.cmd clean package
```

## Swagger / OpenAPI

Con la app corriendo:

- UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Si ejecutas en `8081`, cambia el puerto en las URLs.

## Autenticacion

El endpoint de token es:

- `POST /oauth/token`

Formato esperado:

- Header `Authorization: Basic base64(clientId:clientSecret)`
- Params:
  - `grant_type=password`
  - `username=<usuario>`
  - `password=<clave>`

Respuesta:

- `access_token` (Bearer JWT)

Luego usa:

- `Authorization: Bearer <token>`

en endpoints protegidos (por ejemplo `/persona/**`, `/categoria/**`, `/producto/**`).

## Notas

- El endpoint `GET /tokens/anular/{tokenId}` actualmente informa que la revocacion server-side no aplica con JWT stateless.
- Si ves error de puerto en uso, ejecuta en otro puerto.
- Se recomienda mover credenciales sensibles a variables de entorno para produccion.
