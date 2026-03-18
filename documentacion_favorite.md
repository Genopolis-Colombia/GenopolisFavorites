# Documentación del Microservicio: Favorites

---

## 1. Introducción

El microservicio **Favorites** es parte de la plataforma **Genopolis Colombia** y tiene como propósito gestionar los favoritos de los usuarios dentro del sistema. Permite a cada usuario marcar, consultar y eliminar proteínas de su lista de favoritos, identificadas por su nombre FASTA.

Este servicio está construido siguiendo la **arquitectura hexagonal (Ports and Adapters)**, lo que garantiza una separación clara entre la lógica de negocio y los detalles de infraestructura (base de datos, HTTP, etc.). Está implementado con **Spring Boot 3.2.4** y **Java 17**, usando **MySQL** como base de datos relacional.

---

## 2. Descripción General de la API

| Propiedad         | Valor                                      |
|-------------------|--------------------------------------------|
| Protocolo         | HTTP/REST                                  |
| Puerto            | `8084`                                     |
| URL base          | `http://localhost:8084`                    |
| Formato de datos  | JSON                                       |
| Identificadores   | UUID v4                                    |
| Base de datos     | MySQL — base de datos `favorites_genopolis` |
| CORS habilitado   | `http://localhost:5173`                    |

### Métodos HTTP soportados

`GET` · `POST` · `DELETE`

---

## 3. Arquitectura

El microservicio sigue el patrón de **Arquitectura Hexagonal (Ports and Adapters)** con dos módulos Gradle:

```
┌─────────────────────────────────────────────────────┐
│                    Cliente HTTP                      │
└────────────────────────┬────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────┐
│              Capa de Infraestructura (infra)         │
│  ┌──────────────────────────────────────────────┐   │
│  │         FavoriteControllerAdapter            │   │
│  │              (REST Controller)               │   │
│  └──────────────────┬───────────────────────────┘   │
│                     │                               │
│  ┌──────────────────▼───────────────────────────┐   │
│  │              Handlers                        │   │
│  │  (CreateFavoriteHandler, GetFavoriteHandler, │   │
│  │  DeleteFavoriteHandler, FindByUserHandler)   │   │
│  └──────────────────┬───────────────────────────┘   │
└─────────────────────┼───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│                  Capa de Dominio (domain)            │
│  ┌──────────────────────────────────────────────┐   │
│  │               Use Cases                      │   │
│  │  (Create, Get, Delete, FindByUser)           │   │
│  └──────────────────┬───────────────────────────┘   │
│                     │                               │
│  ┌──────────────────▼───────────────────────────┐   │
│  │            RepositoryPort (interface)        │   │
│  └──────────────────┬───────────────────────────┘   │
└─────────────────────┼───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│              Adaptador de Persistencia               │
│       MysqlFavoriteRepositoryImpl + JPA              │
│              Base de datos MySQL                     │
└─────────────────────────────────────────────────────┘
```

---

## 4. Modelo de Dominio

### Entidad `Favorite`

Representa un favorito guardado por un usuario.

| Campo       | Tipo   | Descripción                                  |
|-------------|--------|----------------------------------------------|
| `id`        | UUID   | Identificador único del favorito             |
| `userId`    | UUID   | Identificador del usuario propietario        |
| `proteinId` | UUID   | Identificador de la proteína marcada         |
| `fastaName` | String | Nombre FASTA asociado a la proteína          |

---

## 5. Esquema de Base de Datos

**Tabla:** `favorite_entity`

| Columna      | Tipo           | Restricciones         | Descripción                      |
|--------------|----------------|-----------------------|----------------------------------|
| `id`         | BINARY(16)/UUID | PK, NOT NULL, AUTO   | Identificador único (UUID auto)  |
| `user_id`    | BINARY(16)/UUID | NOT NULL             | Referencia al usuario            |
| `protein_id` | BINARY(16)/UUID | NOT NULL             | Referencia a la proteína         |
| `fasta_name` | VARCHAR(255)   | -                     | Nombre FASTA de la proteína      |

> El esquema es gestionado automáticamente por Hibernate con la estrategia `ddl-auto=update`.

---

## 6. Documentación de Endpoints

### 6.1 Crear un Favorito

Registra una nueva proteína como favorita para un usuario.

**`POST /favorites`**

#### Request

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "proteinId": "7cb12d34-1234-4abc-b3fc-9a8b7c6d5e4f",
  "fastaName": "P53_HUMAN"
}
```

| Campo       | Tipo   | Requerido | Descripción                          |
|-------------|--------|-----------|--------------------------------------|
| `userId`    | UUID   | Sí        | ID del usuario que guarda el favorito |
| `proteinId` | UUID   | Sí        | ID de la proteína a marcar como favorita |
| `fastaName` | String | Sí        | Nombre FASTA de la proteína          |

#### Response

**Status `201 Created`**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

| Campo | Tipo | Descripción                              |
|-------|------|------------------------------------------|
| `id`  | UUID | ID generado para el favorito recién creado |

---

### 6.2 Obtener un Favorito por ID

Retorna la información de un favorito específico.

**`GET /favorites/{favorite_id}`**

#### Path Parameters

| Parámetro     | Tipo | Descripción                   |
|---------------|------|-------------------------------|
| `favorite_id` | UUID | ID del favorito a consultar   |

#### Response

**Status `200 OK`**
```json
{
  "favoriteId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "proteinId": "7cb12d34-1234-4abc-b3fc-9a8b7c6d5e4f",
  "fastaName": "P53_HUMAN"
}
```

| Campo        | Tipo   | Descripción                        |
|--------------|--------|------------------------------------|
| `favoriteId` | UUID   | ID del favorito                    |
| `userId`     | UUID   | ID del usuario propietario         |
| `proteinId`  | UUID   | ID de la proteína favorita         |
| `fastaName`  | String | Nombre FASTA de la proteína        |

**Status `404 Not Found`**
```json
{
  "failure": "Favorite not found",
  "detail": "No favorite exists with the given ID"
}
```

---

### 6.3 Obtener los Favoritos de un Usuario

Retorna la lista de todos los favoritos asociados a un usuario.

**`GET /favorites/user/{user_id}`**

#### Path Parameters

| Parámetro | Tipo | Descripción                        |
|-----------|------|------------------------------------|
| `user_id` | UUID | ID del usuario a consultar         |

#### Response

**Status `200 OK`**
```json
{
  "favorites": [
    {
      "favoriteId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
      "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "proteinId": "7cb12d34-1234-4abc-b3fc-9a8b7c6d5e4f",
      "fastaName": "P53_HUMAN"
    },
    {
      "favoriteId": "b2c3d4e5-f6a7-8901-bcde-f12345678901",
      "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "proteinId": "8dc23e45-2345-5bcd-c4gd-0b9c8d7e6f5g",
      "fastaName": "BRCA1_HUMAN"
    }
  ]
}
```

| Campo       | Tipo                    | Descripción                            |
|-------------|-------------------------|----------------------------------------|
| `favorites` | Array\<FavoriteResponse\> | Lista de favoritos del usuario         |

**Status `500 Internal Server Error`**
```json
{
  "failure": "Internal server error",
  "detail": "An unexpected error occurred while retrieving favorites"
}
```

---

### 6.4 Eliminar un Favorito

Elimina un favorito del sistema de forma permanente.

**`DELETE /favorites/{favorite_id}`**

#### Path Parameters

| Parámetro     | Tipo | Descripción                   |
|---------------|------|-------------------------------|
| `favorite_id` | UUID | ID del favorito a eliminar    |

#### Response

**Status `204 No Content`**

> No retorna cuerpo en la respuesta.

---

## 7. Resumen de Endpoints

| Método   | Endpoint                     | Descripción                            | Status exitoso |
|----------|------------------------------|----------------------------------------|----------------|
| `POST`   | `/favorites`                 | Crear un nuevo favorito                | `201 Created`  |
| `GET`    | `/favorites/{favorite_id}`   | Obtener un favorito por su ID          | `200 OK`       |
| `GET`    | `/favorites/user/{user_id}`  | Obtener todos los favoritos de un usuario | `200 OK`    |
| `DELETE` | `/favorites/{favorite_id}`   | Eliminar un favorito                   | `204 No Content` |

---

## 8. Códigos de Respuesta HTTP

| Código | Significado             | Cuándo ocurre                                               |
|--------|-------------------------|-------------------------------------------------------------|
| `201`  | Created                 | El favorito fue creado exitosamente                         |
| `200`  | OK                      | Consulta exitosa                                            |
| `204`  | No Content              | El favorito fue eliminado exitosamente                      |
| `404`  | Not Found               | No se encontró el favorito con el ID proporcionado          |
| `500`  | Internal Server Error   | Error inesperado en el servidor al procesar la solicitud    |

---

## 9. Estructura del Proyecto

```
GenopolisFavorite/
├── domain/                          # Capa de dominio (lógica de negocio)
│   └── src/main/java/org/gpc/template/
│       ├── kernel/
│       │   └── Favorite.java        # Entidad de dominio (record)
│       ├── port/
│       │   └── RepositoryPort.java  # Puerto de repositorio (interfaz)
│       └── usecase/
│           ├── UseCase.java
│           ├── CreateFavoriteUseCaseImpl.java
│           ├── GetFavoriteUseCaseImpl.java
│           ├── DeleteFavoriteUseCaseImpl.java
│           └── FindFavoritesByUserUseCaseImpl.java
│
├── infra/                           # Capa de infraestructura
│   └── src/main/java/org/gpc/template/
│       ├── Application.java         # Punto de entrada Spring Boot
│       ├── configuration/
│       │   └── AppConfig.java       # Configuración de beans de Spring
│       ├── adapters/
│       │   ├── in/http/
│       │   │   ├── FavoriteControllerAdapter.java   # Controlador REST
│       │   │   └── dto/             # DTOs de entrada y salida HTTP
│       │   └── out/mysql/
│       │       ├── FavoriteRepository.java          # Repositorio JPA
│       │       ├── MysqlFavoriteRepositoryImpl.java # Implementación del puerto
│       │       ├── model/
│       │       │   └── FavoriteEntity.java          # Entidad JPA
│       │       └── transformers/
│       │           └── FavoriteTransformer.java     # Transformador dominio ↔ JPA
│       └── handlers/                # Manejadores de cada operación
│           ├── CreateFavoriteHandler.java
│           ├── GetFavoriteHandler.java
│           ├── DeleteFavoriteHandler.java
│           └── FindFavoritesByUserHandler.java
│   └── src/main/resources/
│       └── application.properties   # Configuración de Spring Boot
│
├── build.gradle
├── settings.gradle
├── gradlew
└── README.md
```

---

## 10. Configuración

### application.properties

| Propiedad                              | Valor                         |
|----------------------------------------|-------------------------------|
| `server.port`                          | `8084`                        |
| `spring.datasource.url`               | `jdbc:mysql://localhost:3306/favorites_genopolis` |
| `spring.datasource.username`          | `springuser`                  |
| `spring.datasource.password`          | `p4t1App`                     |
| `spring.jpa.hibernate.ddl-auto`       | `update`                      |
| `spring.datasource.driver-class-name` | `com.mysql.cj.jdbc.Driver`    |

---

## 11. Pila Tecnológica

| Componente          | Tecnología                    | Versión   |
|---------------------|-------------------------------|-----------|
| Framework principal | Spring Boot                   | 3.2.4     |
| Lenguaje            | Java                          | 17        |
| Persistencia        | Spring Data JPA + Hibernate   | —         |
| Base de datos       | MySQL                         | 8.x       |
| Conector MySQL      | MySQL Connector/J             | 8.2.0     |
| Generación de código | Lombok                       | 1.18.32   |
| Build tool          | Gradle                        | 8.3       |
| Logging             | SLF4J                         | 1.7.25    |
| Testing             | JUnit 5 + TestContainers      | —         |

---

## 12. Instalación y Ejecución

### Prerrequisitos

- Java 17+
- Gradle 8.3+
- MySQL 8.x
- Docker (para pruebas con TestContainers)

### Configuración de la base de datos

```sql
CREATE DATABASE favorites_genopolis;
CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'p4t1App';
GRANT ALL PRIVILEGES ON favorites_genopolis.* TO 'springuser'@'localhost';
FLUSH PRIVILEGES;
```

### Ejecutar la aplicación

```bash
./gradlew bootRun
```

La aplicación estará disponible en `http://localhost:8084`.

### Ejecutar pruebas

```bash
./gradlew test
```

> Las pruebas de integración requieren Docker para levantar una instancia de MySQL mediante TestContainers.

---

## 13. CORS

El microservicio tiene configurado CORS para permitir peticiones desde el frontend de desarrollo:

| Configuración     | Valor                              |
|-------------------|------------------------------------|
| Origen permitido  | `http://localhost:5173`            |
| Métodos           | `GET`, `POST`, `PUT`, `DELETE`     |
| Headers           | `Content-Type`, `Authorization`    |

---

## 14. Notas y Limitaciones

- **Autenticación:** No está implementada actualmente. El header `Authorization` está permitido en CORS pero no es validado por el servicio.
- **Validación de entrada:** No se aplica validación explícita (`@Valid`) en los DTOs de entrada.
- **Versioning de API:** No implementado.
- **Paginación:** El endpoint de favoritos por usuario retorna todos los registros sin paginación.
- **Índices en BD:** No hay índices explícitos definidos; se recomienda agregar un índice sobre `user_id` para optimizar consultas con grandes volúmenes de datos.
