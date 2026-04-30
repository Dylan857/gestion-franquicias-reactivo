# Api robusta con programacion reactiva

API robusta desarrollada con **Spring Boot WebFlux** para la gestión de franquicias, sucursales y productos. El proyecto implementa programación reactiva, arquitectura limpia, contenerización y aprovisionamiento de infraestructura mediante código.

---

## 🛠 Tecnologías Utilizadas

* **Java 17** & **Spring Boot 4**
* **Spring WebFlux** (Programación reactiva no bloqueante)
* **R2DBC** & **PostgreSQL**
* **Maven** (Gestión de dependencias)
* **Docker** & **Docker Compose**
* **Terraform** (Infrastructure as Code)
* **JUnit 5** & **Mockito** (Unit Testing)

---

## 🏗 Arquitectura del Proyecto

Se ha implementado una estructura basada en los principios de **Clean Architecture**, asegurando un bajo acoplamiento y una alta cohesión entre capas.



```text
features
 ├── franchise
 │    ├── application    (Casos de uso / Services)
 │    ├── domain         (Entidades de negocio)
 │    └── infrastructure (Controllers, DTOs, Repositories)
 ├── branch
 │    ├── application
 │    ├── domain
 │    └── infrastructure
 ├── product
 │    ├── application
 │    ├── domain
 │    └── infrastructure
 └── shared
      ├── exception      (Manejo global de errores)
      └── response       (Estructura estandarizada de API)
```

## 🚀 Funcionalidades

### Franquicias
- Crear franquicia
- Actualizar nombre de franquicia

### Sucursales
- Agregar sucursal a una franquicia
- Actualizar nombre de sucursal

### Productos
- Crear producto
- Asociar producto a sucursal con stock
- Eliminar producto de una sucursal
- Actualizar stock de producto
- Actualizar nombre de producto

### Consultas Especializadas
Punto 7: Obtener el producto con mayor stock por cada sucursal de una franquicia específica.

# Programación Reactiva
La aplicación utiliza un paradigma no bloqueante de extremo a extremo (desde el Controller hasta la Base de Datos con R2DBC).

```java
public Mono<ApiResponse<ProductBranchStock>> createProductBranchStock(CreateProductBranchStockDto dto) {
    return validateIfProductExists(dto.getProductId())
            .then(branchService.findById(dto.getBranchId()))
            .then(productBranchStockRepository.save(
                    new ProductBranchStock(
                            dto.getProductId(),
                            dto.getBranchId(),
                            dto.getStock())))
            .map(saved -> new ApiResponse<>(200, "0000", "NO ERROR", saved));
}
```

# 🧪 Pruebas Unitarias
Se han implementado pruebas unitarias enfocadas en la capa de Application para garantizar la integridad de la lógica de negocio.

Herramientas: JUnit 5, Mockito, Reactor Test (StepVerifier).

Ubicación: src/test/java.

Cobertura: Lógica de BranchService y ProductService.

Para ejecutar los tests manualmente:
```bash
mvn test
o
./mvnw test
# Recuerde que es todo en la raiz del proyecto
```

Los tests se enfocan en validar la lógica de negocio de manera aislada,
mockeando las dependencias externas.

# 🐳 Contenerización y Despliegue
Dockerfile
Se utiliza una estrategia multi-stage build para minimizar el tamaño de la imagen final:

Stage 1 (Build): Compilación y empaquetado con Maven.

Stage 2 (Run): Ejecución sobre una imagen ligera de JRE.

Infraestructura con Terraform (IaC)
La base de datos PostgreSQL se aprovisiona automáticamente. Terraform se encarga de:

Descargar la imagen de PostgreSQL.

Configurar volúmenes y variables de entorno.

Levantar el contenedor listo para recibir conexiones.

# 📦 Instrucciones de Ejecución
## 1️⃣ Levantar Infraestructura (Base de Datos)
```bash
cd terraform
terraform init
terraform apply -auto-approve
```
## 2️⃣ Ejecutar la Aplicación
Regresa a la raíz del proyecto y ejecuta
```bash
docker-compose up --build
```
La aplicación estará disponible en: http://localhost:8080

# 📍 Endpoints Principales
### POST	/franchises	Crear una nueva franquicia
```json
{
    "name": "Es una prueba 9"
}
```
### PUT	/franchises/{id}	Actualizar franquicia
```json
{
    "name": "Es una prueba 9"
}
```

### POST	/branch	Crear una sucursal
```json
{
    "name": "Sucursal de prueba"
}
```
### PUT	/branch/{id}	Actualizar Sucursal
```json
{
    "name": "Es una prueba 9"
}
```

### POST /branch/branch-franchise Agregar sucursal a una franquicia
```json
{
    "branchId": 1,
    "franchiseId": 1
}
```
### POST /product Crear producto
```json
{
    "name": "Zapatos"
}
```
### PUT	/product/{id}	Actualizar producto
```json
{
    "name": "Es una prueba 9"
}
```

### POST /product/branch-stock Añadir producto a una sucursal con su stock
```json
{
    "productId": 1,
    "branchId": 1,
    "stock": 50
}
```
### PUT /product/branch-stock/{productId}/{branchId} Eliminar producto de una sucursal
### PUT /product/branch-stock/{branchId} Modificar stock de un producto en una sucursal
```json
{
    "productId": 1,
    "stock": 30
}
```
### GET /product/branch-stock/top/{franchiseId} Buscar los productos con mas stock por sucursal y franquicia

# ✨ Extras Implementados
✅ WebFlux: Pipeline 100% reactivo.

✅ IaC: Uso de Terraform para recursos locales.

✅ Docker Compose: Orquestación completa.

✅ Arquitectura Modular: Basada en dominios funcionales.

✅ Validaciones: Control de excepciones personalizado.

## Autor

Dylan Vasquez Ochoa  
Backend Developer
