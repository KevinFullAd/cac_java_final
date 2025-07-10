---
# IMPORTANTE: Para que el programa se ejecute correctamente debe crear una base de datos mysql con el nombre "java_cac"
---
# 🛒 Backend API - E-Commerce

Este proyecto es un backend funcional para un sistema de e-commerce. Está desarrollado en **Java Spring Boot** y permite gestionar productos y pedidos de forma sencilla, robusta y escalable.

---

## 🚀 Endpoints disponibles

### 🧱 Productos

| Método | Endpoint               | Descripción                          |
|--------|------------------------|--------------------------------------|
| GET    | `/api/productos`       | Listar todos los productos           |
| GET    | `/api/productos/{id}`  | Obtener un producto por ID           |
| POST   | `/api/productos`       | Crear un nuevo producto              |
| PUT    | `/api/productos/{id}`  | Actualizar producto existente        |
| DELETE | `/api/productos/{id}`  | Eliminar producto por ID             |

---

### 📦 Pedidos

| Método | Endpoint                         | Descripción                           |
|--------|----------------------------------|---------------------------------------|
| GET    | `/api/pedidos`                  | Listar todos los pedidos              |
| GET    | `/api/pedidos/{id}`             | Obtener pedido por ID                 |
| GET    | `/api/pedidos/estado/{estado}`  | Filtrar pedidos por estado (ej: `PENDIENTE`) |
| POST   | `/api/pedidos`                  | Crear un nuevo pedido validando stock |

---

## ✅ Validaciones incluidas

### Productos
- `nombre`: no vacío
- `precio`: mayor que 0
- `stock`: mayor o igual a 0
- `categoria`: debe ser una categoría válida (enum)

### Pedidos
- `items`: obligatorio, no vacío
- Cada ID de producto debe ser válido y tener stock suficiente
- Cada cantidad debe ser > 0

---

## ⚠️ Errores controlados (todos en formato JSON)

- **400 Bad Request**: campos inválidos, stock insuficiente, categoría inválida
- **404 Not Found**: producto o pedido inexistente
- **500 Internal Server Error**: fallos no previstos
- **Error personalizado** para `categoria` con mensaje claro

---

## 🔧 Tecnología

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Lombok
- DevTools (opcional)

---

## 📂 Estructura del proyecto

com.ecommerce
├── controller
├── service
├── repository
├── model
├── dto
├── exception
└── EcommerceApplication.java

---

## 📬 Notas

- La API está lista para conectarse a un frontend mediante JSON.
- Exportá la colección de Postman para probar rápidamente (resources/static/proyecto java.postman_collection.json).
- Todos los errores relevantes devuelven respuestas claras y en formato JSON.
 
