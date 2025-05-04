
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

    git clone https://github.com/julianm996/InditexApi.git
    cd InditexApi

2. Compilar el proyecto:

    mvn clean install

3. Ejecutar la aplicación:

    mvn spring-boot:run

- La API estará disponible en: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
  (JDBC URL: jdbc:h2:mem:prices_db, usuario: sa, sin contraseña)

---

## Ejecución con Docker

El Dockerfile se encuentra en la carpeta raíz del proyecto.

1. Construir la imagen:

    docker build -t price-api .

2. Ejecutar el contenedor:

    docker run -p 8080:8080 price-api

Aplicación desplegada en: http://localhost:8080

---

## Tests

Ejecutar todos los tests:

    mvn test

Incluye:
- Tests unitarios
- Tests de integración
- Tests funcionales / E2E con MockMvc

---

## Ejemplos de uso con curl

### 1. Obtener el precio aplicable el 14 de junio a las 10:00h

    curl -X GET "http://localhost:8080/prices/applicable?productId=35455&brandId=1&date=2020-06-14T10:00:00" -H "accept: application/json"

### 2. Obtener el precio con mayor prioridad el 14 de junio a las 16:00h

    curl -X GET "http://localhost:8080/prices/applicable?productId=35455&brandId=1&date=2020-06-14T16:00:00" -H "accept: application/json"

### 3. Producto no existente

    curl -X GET "http://localhost:8080/prices/applicable?productId=99999&brandId=1&date=2020-06-14T10:00:00" -H "accept: application/json"

### 4. Formato de fecha inválido

    curl -X GET "http://localhost:8080/prices/applicable?productId=35455&brandId=1&date=not-a-date" -H "accept: application/json"

---

## Cómo probar la API con Postman

1. Abrir Postman
2. Crear una nueva petición `GET`
3. Usar esta URL:

    http://localhost:8080/prices/applicable?productId=35455&brandId=1&date=2020-06-14T10:00:00

4. Seleccionar el método `GET`
5. Asegúrate de establecer los headers:
    - Key: `accept`
    - Value: `application/json`

6. Haz clic en **Send**

Puedes probar los demás casos cambiando el parámetro `date`, `productId` o `brandId` como en los ejemplos anteriores.

---

## Gestión de errores

La API gestiona errores comunes como:

- Precio no encontrado (404)
- Fecha con formato inválido (400)
- Parámetros faltantes (400)

Se devuelve un objeto con:

- timestamp
- message
- status

---

## Autor

Julian Melgarejo  
Repositorio: https://github.com/julianm996/InditexApi
