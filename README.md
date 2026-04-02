# 🚀 Task Management API

A Spring Boot REST API for managing users and tasks with OpenAPI documentation.

---

## 📌 Features

- Create / Update / Delete tasks
- Assign tasks to users
- Search tasks by title or status
- User management
- OpenAPI documentation (YAML-based)
- No Swagger dependency required (Spring Boot 4 compatible)

---

## 🧱 Tech Stack

- Java 17+
- Spring Boot 3 / 4
- Spring Web
- OpenAPI 3 (YAML)
- Maven

---

## 📂 Project Structure
src/main/resources/
└── openapi.yaml → API documentation

src/main/java/
└── controller/
└── service/
└── dto/



---

## 📖 API Documentation

### ▶️ View OpenAPI file in browser

After running the project:

```
http://localhost:8080/openapi.yaml
```

---

### ▶️ View in Swagger Editor (recommended)

https://editor.swagger.io/

Paste `openapi.yaml`

---

### ▶️ View in Redoc (beautiful UI)

https://redocly.github.io/redoc/

---

## 🔧 Optional Controller (to expose YAML)

```java
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

    @GetMapping(value = "/openapi.yaml", produces = "text/yaml")
    public Resource getOpenApi() {
        return new ClassPathResource("openapi.yaml");
    }
}


---

## 📖 API Documentation

### ▶️ View OpenAPI file in browser

After running the project:

```
http://localhost:8080/openapi.yaml
```

---

### ▶️ View in Swagger Editor (recommended)

https://editor.swagger.io/

Paste `openapi.yaml`

---

### ▶️ View in Redoc (beautiful UI)

https://redocly.github.io/redoc/

---

## 🔧 Optional Controller (to expose YAML)

```java
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

    @GetMapping(value = "/openapi.yaml", produces = "text/yaml")
    public Resource getOpenApi() {
        return new ClassPathResource("openapi.yaml");
    }
}
