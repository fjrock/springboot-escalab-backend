# Springboot Escalab Backend - API REST "Ofrecelo"

Backend API REST para gestionar personas, categorias, productos y consultas.
Plataforma de intercambio de productos. Proyecto academico de Escalab, actualizado a Spring Boot 3.

---

## Tabla de Contenidos

1. [Que necesitas antes de empezar](#1-que-necesitas-antes-de-empezar)
2. [Clonar el proyecto](#2-clonar-el-proyecto)
3. [Configurar la base de datos PostgreSQL](#3-configurar-la-base-de-datos-postgresql)
4. [Configurar application.properties](#4-configurar-applicationproperties)
5. [Levantar el proyecto](#5-levantar-el-proyecto)
6. [Verificar que funciona](#6-verificar-que-funciona)
7. [Autenticacion - Como obtener un token JWT](#7-autenticacion---como-obtener-un-token-jwt)
8. [Endpoints de la API](#8-endpoints-de-la-api)
9. [Ejemplos con cURL](#9-ejemplos-con-curl)
10. [Swagger / OpenAPI](#10-swagger--openapi)
11. [Correr los tests](#11-correr-los-tests)
12. [Estructura del proyecto](#12-estructura-del-proyecto)
13. [Modelo de datos](#13-modelo-de-datos)
14. [Stack tecnologico](#14-stack-tecnologico)
15. [Problemas comunes](#15-problemas-comunes)

---

## 1. Que necesitas antes de empezar

Antes de tocar cualquier cosa, asegurate de tener instalado:

| Herramienta | Version | Como verificar |
|---|---|---|
| **JDK** | 17 o superior | `java -version` |
| **Git** | cualquiera | `git --version` |
| **PostgreSQL** | 12 o superior | `psql --version` |

> **NO necesitas instalar Maven.** El proyecto incluye Maven Wrapper (`mvnw`), que descarga Maven automaticamente.

### Instalar JDK 17

**Windows:**
1. Descarga desde https://adoptium.net/ (Eclipse Temurin 17)
2. Ejecuta el instalador
3. Marca la opcion "Set JAVA_HOME variable"
4. Reinicia la terminal

**Mac:**
```bash
brew install openjdk@17
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

Verifica:
```bash
java -version
# Debe mostrar algo como: openjdk version "17.x.x"
```

---

## 2. Clonar el proyecto

```bash
git clone https://github.com/fjrock/springboot-escalab-backend.git
cd springboot-escalab-backend
```

---

## 3. Configurar la base de datos PostgreSQL

### Opcion A: PostgreSQL local

1. Abre una terminal de PostgreSQL (`psql`):

```bash
psql -U postgres
```

2. Crea la base de datos:

```sql
CREATE DATABASE escalab_db;
```

3. (Opcional) Crea un usuario dedicado:

```sql
CREATE USER escalab_user WITH PASSWORD 'tu_password_aqui';
GRANT ALL PRIVILEGES ON DATABASE escalab_db TO escalab_user;
```

4. Sal de psql:

```sql
\q
```

### Opcion B: Neon (PostgreSQL en la nube, gratis)

1. Ve a https://neon.tech y crea una cuenta
2. Crea un nuevo proyecto
3. Copia el connection string que te dan (se ve asi):
   ```
   postgresql://usuario:password@ep-xxxx.us-east-2.aws.neon.tech/neondb?sslmode=require
   ```
4. Usaras esa URL en el siguiente paso

### Datos iniciales obligatorios

Una vez que la app levante por primera vez (Hibernate crea las tablas automaticamente), necesitas insertar datos en las tablas `usuario`, `rol` y `usuario_rol` manualmente para poder autenticarte.

Conectate a tu base de datos y ejecuta:

```sql
-- Crear roles
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (1, 'USER', 'Rol de usuario basico');
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (2, 'ADMIN', 'Rol de administrador');

-- Crear un usuario (password: 123456 encriptada con BCrypt)
INSERT INTO usuario (id_usuario, nombre, clave, estado)
VALUES (1, 'admin', '$2a$10$VZjGwkPOJC18TJk0MrKfg.FMKfbalNWwxMrvPBRiiUraKnxIXaQYy', true);

-- Asignar rol USER al usuario
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 1);

-- (Opcional) Asignar tambien rol ADMIN
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 2);
```

> La clave `$2a$10$VZjGwkPOJC18TJk0MrKfg.FMKfbalNWwxMrvPBRiiUraKnxIXaQYy` es "123456" encriptada con BCrypt. Puedes generar otra en https://bcrypt-generator.com/

---

## 4. Configurar application.properties

Abre el archivo `src/main/resources/application.properties` y configura tu conexion:

### Para PostgreSQL local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/escalab_db
spring.datasource.username=postgres
spring.datasource.password=tu_password_aqui
```

### Para Neon:

```properties
spring.datasource.url=jdbc:postgresql://ep-xxxx.us-east-2.aws.neon.tech/neondb?sslmode=require
spring.datasource.username=tu_usuario_neon
spring.datasource.password=tu_password_neon
```

### Configuracion JWT (ya viene por defecto, cambiar en produccion):

```properties
security.jwt.client-id=ofreceloapp
security.jwt.client-secret=ofrecelo2020
app.security.jwt-secret=ofrecelo-jwt-secret-key-change-this-please-2026
app.security.jwt-expiration-seconds=3600
```

---

## 5. Levantar el proyecto

### Linux / Mac:

```bash
./mvnw spring-boot:run
```

### Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

### Windows (CMD):

```cmd
mvnw.cmd spring-boot:run
```

Si todo esta bien, veras algo como:

```
Started SpringbootEscalabBackendApplication in X.XXX seconds
```

La app corre en **http://localhost:8080**

### Si el puerto 8080 esta ocupado:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

---

## 6. Verificar que funciona

Abre tu navegador y ve a:

```
http://localhost:8080/swagger-ui/index.html
```

Si ves la interfaz de Swagger, el proyecto esta corriendo correctamente.

---

## 7. Autenticacion - Como obtener un token JWT

Todos los endpoints (excepto `/oauth/token` y Swagger) requieren autenticacion JWT.

### Paso 1: Obtener el token

```bash
curl -X POST "http://localhost:8080/oauth/token?grant_type=password&username=admin&password=123456" \
  -H "Authorization: Basic b2ZyZWNlbG9hcHA6b2ZyZWNlbG8yMDIw"
```

> `b2ZyZWNlbG9hcHA6b2ZyZWNlbG8yMDIw` es `ofreceloapp:ofrecelo2020` codificado en Base64.

Respuesta:

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiJ9...",
  "token_type": "Bearer",
  "expires_in": 3600,
  "scope": "read write"
}
```

### Paso 2: Usar el token

Copia el valor de `access_token` y usalo asi en todas las peticiones:

```bash
curl -H "Authorization: Bearer TU_TOKEN_AQUI" http://localhost:8080/persona/1
```

### Como generar el Basic Auth manualmente

El header `Authorization: Basic ...` se construye asi:

1. Concatena: `clientId:clientSecret` -> `ofreceloapp:ofrecelo2020`
2. Codifica en Base64: `b2ZyZWNlbG9hcHA6b2ZyZWNlbG8yMDIw`
3. Header final: `Authorization: Basic b2ZyZWNlbG9hcHA6b2ZyZWNlbG8yMDIw`

En Linux/Mac puedes generarlo asi:

```bash
echo -n "ofreceloapp:ofrecelo2020" | base64
```

---

## 8. Endpoints de la API

Todos los endpoints requieren `Authorization: Bearer <token>` excepto los marcados como publicos.

### Autenticacion (publico)

| Metodo | Ruta | Descripcion |
|---|---|---|
| POST | `/oauth/token` | Obtener token JWT |

### Persona

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/persona/{id}` | Obtener persona por ID |
| POST | `/persona` | Crear persona |
| PUT | `/persona` | Actualizar persona |
| DELETE | `/persona/{id}` | Eliminar persona |

### Categoria

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/categoria/{id}` | Obtener categoria por ID |
| POST | `/categoria` | Crear categoria |
| PUT | `/categoria` | Actualizar categoria |
| DELETE | `/categoria/{id}` | Eliminar categoria |

### Producto

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/producto/{id}` | Obtener producto por ID |
| POST | `/producto` | Crear producto |
| PUT | `/producto` | Actualizar producto |
| DELETE | `/producto/{id}` | Eliminar producto |

### Consulta

| Metodo | Ruta | Descripcion |
|---|---|---|
| POST | `/consulta/buscartodoporrun` | Buscar consultas por RUN de persona |
| POST | `/consulta/buscartodoporcategoria` | Buscar consultas por categoria |
| POST | `/consulta/buscartodoporproducto` | Buscar consultas por producto |
| POST | `/consulta/registrarconsulta` | Registrar nueva consulta |

### Consulta-Persona

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/consultapersona/{idPersona}` | Listar consultas de una persona |
| POST | `/consultapersona/registrar` | Registrar relacion consulta-persona |

### Consulta-Categoria

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/consultacategoria/{idCategoria}` | Listar consultas de una categoria |
| POST | `/consultacategoria/registrar` | Registrar relacion consulta-categoria |

### Consulta-Producto

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/consultaproducto/{idProducto}` | Listar consultas de un producto |
| POST | `/consultaproducto/registrar` | Registrar relacion consulta-producto |

### Token (requiere rol ADMIN)

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/tokens/anular/{tokenId}` | Anular token (informativo, JWT es stateless) |

---

## 9. Ejemplos con cURL

### Obtener token:

```bash
curl -X POST "http://localhost:8080/oauth/token?grant_type=password&username=admin&password=123456" \
  -H "Authorization: Basic b2ZyZWNlbG9hcHA6b2ZyZWNlbG8yMDIw"
```

### Crear una persona:

```bash
curl -X POST http://localhost:8080/persona \
  -H "Authorization: Bearer TU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellidoPaterno": "Perez",
    "apellidoMaterno": "Lopez",
    "run": "12345678",
    "dv": "9",
    "telefono": "912345678",
    "tipoPersona": "NATURAL",
    "email": "juan@email.com",
    "banned": false
  }'
```

### Obtener persona por ID:

```bash
curl -H "Authorization: Bearer TU_TOKEN" http://localhost:8080/persona/1
```

### Crear una categoria:

```bash
curl -X POST http://localhost:8080/categoria \
  -H "Authorization: Bearer TU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Electronica"
  }'
```

### Crear un producto:

```bash
curl -X POST http://localhost:8080/producto \
  -H "Authorization: Bearer TU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop HP",
    "stock": 5,
    "tipoIntercambio": "TRUEQUE",
    "lugarEntrega": "Santiago Centro"
  }'
```

### Registrar una consulta:

```bash
curl -X POST http://localhost:8080/consulta/registrarconsulta \
  -H "Authorization: Bearer TU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "idPersona": 1,
    "idCategoria": 1,
    "idProducto": 1
  }'
```

### Buscar consultas por RUN:

```bash
curl -X POST http://localhost:8080/consulta/buscartodoporrun \
  -H "Authorization: Bearer TU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "run": "12345678"
  }'
```

---

## 10. Swagger / OpenAPI

Con la app corriendo, puedes explorar y probar TODOS los endpoints desde el navegador:

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |

### Como autenticarte en Swagger:

1. Primero obtene un token usando el endpoint `/oauth/token` desde Swagger o cURL
2. Copia el `access_token` de la respuesta
3. Haz clic en el boton "Authorize" (candado verde arriba a la derecha)
4. Escribe: `Bearer TU_TOKEN_AQUI`
5. Haz clic en "Authorize"
6. Ahora puedes probar todos los endpoints protegidos

---

## 11. Correr los tests

### Correr todos los tests:

```bash
./mvnw clean test
```

### En Windows:

```powershell
.\mvnw.cmd clean test
```

Los tests usan una base de datos H2 en memoria (no necesitas PostgreSQL para los tests).

### Generar reporte de cobertura (JaCoCo):

```bash
./mvnw clean verify
```

El reporte se genera en: `target/site/jacoco/index.html`

> El proyecto requiere 100% de cobertura de lineas y branches para pasar el build.

### Compilar sin tests:

```bash
./mvnw clean package -DskipTests
```

### Generar el JAR:

```bash
./mvnw clean package
```

El JAR se genera en: `target/springboot-escalab-backend-0.0.1-SNAPSHOT.jar`

### Ejecutar el JAR directamente:

```bash
java -jar target/springboot-escalab-backend-0.0.1-SNAPSHOT.jar
```

---

## 12. Estructura del proyecto

```
src/
├── main/
│   ├── java/com/escalab/
│   │   ├── SpringbootEscalabBackendApplication.java  # Clase principal
│   │   ├── SecurityConfig.java                       # Configuracion Spring Security + JWT
│   │   ├── SwaggerConfig.java                        # Configuracion OpenAPI/Swagger
│   │   ├── AuthException.java                        # Manejo de errores de autenticacion
│   │   │
│   │   ├── controller/                               # Controladores REST
│   │   │   ├── OAuthTokenController.java             #   POST /oauth/token
│   │   │   ├── PersonaController.java                #   CRUD /persona
│   │   │   ├── CategoriaController.java              #   CRUD /categoria
│   │   │   ├── ProductoController.java               #   CRUD /producto
│   │   │   ├── ConsultaController.java               #   Busquedas /consulta
│   │   │   ├── ConsultaPersonaController.java        #   /consultapersona
│   │   │   ├── ConsultaCategoriaController.java      #   /consultacategoria
│   │   │   ├── ConsultaProductoController.java       #   /consultaproducto
│   │   │   └── TokenController.java                  #   /tokens (admin)
│   │   │
│   │   ├── model/                                    # Entidades JPA
│   │   │   ├── Persona.java                          #   Personas (nombre, run, email...)
│   │   │   ├── Categoria.java                        #   Categorias de productos
│   │   │   ├── Producto.java                         #   Productos (nombre, stock, lugar...)
│   │   │   ├── Consulta.java                         #   Consultas (persona+categoria+producto)
│   │   │   ├── ConsultaPersona.java                  #   Relacion consulta-persona
│   │   │   ├── ConsultaCategoria.java                #   Relacion consulta-categoria
│   │   │   ├── ConsultaProducto.java                 #   Relacion consulta-producto
│   │   │   ├── ConsultaPersonaPK.java                #   Clave compuesta
│   │   │   ├── ConsultaCategoriaPK.java              #   Clave compuesta
│   │   │   ├── ConsultaProductoPK.java               #   Clave compuesta
│   │   │   ├── Usuario.java                          #   Usuarios del sistema
│   │   │   ├── Rol.java                              #   Roles (USER, ADMIN)
│   │   │   └── ResetToken.java                       #   Tokens de reset de password
│   │   │
│   │   ├── dto/                                      # Objetos de transferencia
│   │   │   └── FiltroConsultaDTO.java                #   Filtro para busquedas
│   │   │
│   │   ├── repo/                                     # Repositorios JPA
│   │   │   ├── IPersonaRepo.java
│   │   │   ├── ICategoriaRepo.java
│   │   │   ├── IProductoRepo.java
│   │   │   ├── IConsultaRepo.java
│   │   │   ├── IConsultaPersonaRepo.java
│   │   │   ├── IConsultaCategoriaRepo.java
│   │   │   ├── IConsultaProductoRepo.java
│   │   │   ├── IGuardaConsultaRepo.java
│   │   │   ├── IUsuarioRepo.java
│   │   │   └── IResetTokenRepo.java
│   │   │
│   │   ├── service/                                  # Interfaces de servicio
│   │   │   ├── ICRUD.java                            #   Interfaz base CRUD
│   │   │   ├── IPersonaService.java
│   │   │   ├── ICategoriaService.java
│   │   │   ├── IProductoService.java
│   │   │   ├── IConsultaService.java
│   │   │   ├── IConsultaPersonaService.java
│   │   │   ├── IConsultaCategoriaService.java
│   │   │   ├── IConsultaProductoService.java
│   │   │   └── IResetTokenService.java
│   │   │
│   │   ├── service/impl/                             # Implementaciones
│   │   │   ├── PersonaServiceImpl.java
│   │   │   ├── CategoriaServiceImpl.java
│   │   │   ├── ProductoServiceImpl.java
│   │   │   ├── ConsultaServiceImpl.java
│   │   │   ├── ConsultaPersonaServiceImpl.java
│   │   │   ├── ConsultaCategoriaServiceImpl.java
│   │   │   ├── ConsultaProductoServiceImpl.java
│   │   │   ├── ResetTokenServiceImpl.java
│   │   │   └── UsuarioServiceImpl.java               #   UserDetailsService (login)
│   │   │
│   │   ├── exception/                                # Manejo de errores
│   │   │   ├── ResponseExceptionHandler.java         #   Handler global
│   │   │   ├── ModeloNotFoundException.java           #   Error 404
│   │   │   └── ExceptionResponse.java                #   DTO de error
│   │   │
│   │   └── util/
│   │       └── CORS.java                             # Filtro CORS (permite todos los origenes)
│   │
│   └── resources/
│       └── application.properties                    # Configuracion principal
│
└── test/                                             # Tests unitarios e integracion
    ├── java/com/escalab/                             # 28 archivos de test
    └── resources/
        └── application.properties                    # Config de test (H2 en memoria)
```

---

## 13. Modelo de datos

### Diagrama de relaciones

```
┌──────────┐     ┌───────────┐     ┌───────────┐
│ Persona  │     │ Categoria │     │ Producto  │
│──────────│     │───────────│     │───────────│
│ id       │     │ id        │     │ id        │
│ nombre   │     │ nombre    │     │ nombre    │
│ apellidos│     └─────┬─────┘     │ stock     │
│ run / dv │           │           │ fechas    │
│ telefono │           │           │ tipo      │
│ email    │           │           │ lugar     │
│ tipo     │           │           └─────┬─────┘
│ banned   │           │                 │
└────┬─────┘           │                 │
     │                 │                 │
     │        ┌────────┴─────────┐       │
     └────────┤    Consulta      ├───────┘
              │──────────────────│
              │ id               │
              │ id_persona  (FK) │
              │ id_categoria(FK) │
              │ id_producto (FK) │
              └────────┬─────────┘
                       │
          ┌────────────┼────────────┐
          │            │            │
  ┌───────┴───────┐ ┌─┴──────────┐ ┌┴──────────────┐
  │ConsultaPersona│ │ConsultaCat.│ │ConsultaProduct.│
  │ id_persona    │ │ id_categ.  │ │ id_producto    │
  │ id_consulta   │ │ id_consulta│ │ id_consulta    │
  └───────────────┘ └────────────┘ └────────────────┘

┌──────────┐      ┌──────────┐
│ Usuario  │──M:N─│   Rol    │
│──────────│      │──────────│
│ id       │      │ id       │
│ username │      │ nombre   │
│ password │      │ descrip. │
│ enabled  │      └──────────┘
└────┬─────┘
     │ 1:1
┌────┴──────┐
│ResetToken │
│ token     │
│ expiracion│
└───────────┘
```

### Campos de cada entidad

**Persona:**
| Campo | Tipo | Requerido | Descripcion |
|---|---|---|---|
| idPersona | Integer | Auto | ID autoincremental |
| nombre | String(100) | Si | Nombre |
| apellidoPaterno | String(100) | Si | Apellido paterno |
| apellidoMaterno | String(100) | Si | Apellido materno |
| run | String(8) | Si | RUN sin digito verificador |
| dv | String(1) | Si | Digito verificador |
| telefono | String(12) | Si | Telefono |
| tipoPersona | String(100) | Si | Tipo (ej: NATURAL, JURIDICA) |
| email | String(100) | Si | Email (validado con @Email) |
| banned | boolean | Si | Si esta baneado (default: false) |

**Categoria:**
| Campo | Tipo | Requerido | Descripcion |
|---|---|---|---|
| idCategoria | Integer | Auto | ID autoincremental |
| nombre | String(50) | Si | Nombre de la categoria |

**Producto:**
| Campo | Tipo | Requerido | Descripcion |
|---|---|---|---|
| idProducto | Integer | Auto | ID autoincremental |
| nombre | String(50) | Si | Nombre del producto |
| stock | Integer | Si | Cantidad disponible |
| fechaCreacion | LocalDateTime | No | Fecha de creacion |
| fechaActualizacion | LocalDateTime | No | Fecha de actualizacion |
| tipoIntercambio | String | No | Tipo de intercambio |
| lugarEntrega | String(50) | No | Lugar de entrega |

**Consulta:**
| Campo | Tipo | Requerido | Descripcion |
|---|---|---|---|
| idConsulta | Integer | Auto | ID autoincremental |
| persona | Persona (FK) | Si | Persona asociada |
| categoria | Categoria (FK) | Si | Categoria asociada |
| producto | Producto (FK) | Si | Producto asociado |

**FiltroConsultaDTO (para busquedas):**
| Campo | Tipo | Descripcion |
|---|---|---|
| run | String | RUN para filtrar |
| nombre | String | Nombre para filtrar |
| idConsulta | Integer | ID de consulta |
| idCategoria | Integer | ID de categoria |
| idPersona | Integer | ID de persona |
| idProducto | Integer | ID de producto |

---

## 14. Stack tecnologico

| Tecnologia | Version | Para que se usa |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.4.4 | Framework base |
| Spring Security 6 | - | Autenticacion y autorizacion |
| Spring OAuth2 Resource Server | - | Validacion de tokens JWT |
| Spring Data JPA | - | Acceso a datos (Hibernate 6) |
| Spring Validation | - | Validacion de campos (@Valid, @Email) |
| Spring HATEOAS | - | Links en respuestas REST |
| Spring Mail | - | Soporte para envio de correos |
| Thymeleaf | - | Motor de templates |
| PostgreSQL | 12+ | Base de datos principal |
| H2 Database | - | Base de datos para tests |
| springdoc-openapi | 2.8.5 | Documentacion Swagger/OpenAPI |
| NimbusDS JOSE | - | Firma y verificacion de JWT (HS256) |
| BCrypt | - | Encriptacion de passwords |
| JaCoCo | 0.8.12 | Cobertura de codigo (100%) |
| Maven Wrapper | - | Build sin instalar Maven |
| JUnit 5 + Mockito | - | Testing |

---

## 15. Problemas comunes

### "Port 8080 already in use"

Otro proceso usa el puerto 8080. Opciones:

```bash
# Opcion 1: Usar otro puerto
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

# Opcion 2: Matar el proceso que usa el puerto (Linux/Mac)
lsof -i :8080
kill -9 <PID>

# Opcion 2: Matar el proceso (Windows)
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### "Connection refused" al conectar a PostgreSQL

- Verifica que PostgreSQL esta corriendo: `pg_isready`
- Verifica que el usuario y password son correctos en `application.properties`
- Verifica que la base de datos existe: `psql -U postgres -l`

### "Access Denied" o 401 en los endpoints

- Verifica que estas enviando el header `Authorization: Bearer <token>`
- El token expira en 1 hora (3600 segundos). Si expiro, obtene uno nuevo
- Verifica que el usuario existe en la tabla `usuario` y tiene roles asignados

### "invalid_client" al pedir token

- Verifica que el header Basic Auth es correcto
- El valor debe ser `ofreceloapp:ofrecelo2020` codificado en Base64
- Valor correcto: `Basic b2ZyZWNlbG9hcHA6b2ZyZWNlbG8yMDIw`

### "unsupported_grant_type"

- Asegurate de enviar `grant_type=password` como parametro

### Los tests fallan con error de cobertura

- El proyecto exige 100% de cobertura. Si agregaste codigo nuevo, necesitas agregar tests
- Revisa el reporte en `target/site/jacoco/index.html` para ver que falta cubrir

### "./mvnw: Permission denied" (Linux/Mac)

```bash
chmod +x mvnw
```

### Java version incorrecta

```bash
java -version
# Si no muestra 17, configura JAVA_HOME:
export JAVA_HOME=/path/to/jdk-17
```

---

## Resumen rapido (TL;DR)

```bash
# 1. Clonar
git clone https://github.com/fjrock/springboot-escalab-backend.git
cd springboot-escalab-backend

# 2. Configurar DB en src/main/resources/application.properties

# 3. Levantar
./mvnw spring-boot:run

# 4. Insertar datos iniciales (usuario, roles) en PostgreSQL

# 5. Obtener token
curl -X POST "http://localhost:8080/oauth/token?grant_type=password&username=admin&password=123456" \
  -H "Authorization: Basic b2ZyZWNlbG9hcHA6b2ZyZWNlbG8yMDIw"

# 6. Usar la API
curl -H "Authorization: Bearer <token>" http://localhost:8080/persona/1

# 7. Ver Swagger
# http://localhost:8080/swagger-ui/index.html
```
