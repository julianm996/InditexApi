# Inditex Price API

API REST construida para consultar el precio aplicable de un producto según marca, fecha y prioridad. Proyecto basado en arquitectura hexagonal.

---

## Tecnologías

- Java 21 + Spring Boot 3.1.5
- Spring Web, Spring Data JPA, H2 in-memory
- Swagger / OpenAPI 3
- Docker
- JUnit 5, MockMvc, Tests E2E
- MapStruct, Lombok

---

## Ejecución local

1. Clonar el repositorio:

```bash
git clone https://github.com/julianm996/InditexApi.git
cd InditexApi
```

2. Compilar el proyecto:

```bash
mvn clean install
```

3. Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

La API estará disponible en:  
http://localhost:8080

Swagger UI disponible en:  
http://localhost:8080/swagger-ui.html

Consola H2 (base de datos en memoria):  
http://localhost:8080/h2-console  
- JDBC URL: `jdbc:h2:mem:prices_db`  
- User: `sa`  
- Password: (vacío)

---

## Ejecución con Docker

El `Dockerfile` se encuentra en la carpeta raíz del proyecto.

### 1. Construir la imagen:

```bash
docker build -t price-api .
```

### 2. Ejecutar el contenedor:

```bash
docker run -p 8080:8080 price-api
```

Esto desplegará la aplicación en:  
http://localhost:8080

---

## Tests

Ejecutar todos los tests:

```bash
mvn test
```

Incluye:

- Tests unitarios
- Tests de integración
- Tests funcionales/E2E

---

## Gestión de errores

La API gestiona errores comunes como:

- Precio no encontrado
- Fecha con formato inválido
- Parámetros faltantes

Se devuelven respuestas con estructura estándar: timestamp, mensaje y código de estado HTTP.

---

## Autor
*Julian Melgarejo*
Repositorio: https://github.com/julianm996/InditexApi
