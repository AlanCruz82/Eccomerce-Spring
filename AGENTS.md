# AGENTS.md — Eccomerce

## Stack
Java 21, Spring Boot 4.1.0, Thymeleaf, Spring Data JPA (Hibernate), MySQL, Lombok, Maven.

## Dev commands
```bash
./mvnw compile           # compile only (runs annotation processors: Lombok)
./mvnw spring-boot:run   # run dev server
./mvnw test              # run tests
```

## Environment variables (via `application.yaml`)
```
DB_URL=jdbc:mysql://localhost:3306/eccomerce
DB_USER=root
DB_PASSWORD=your_password
SERVER_PORT=8080
```

## Project structure
```
com.spring.eccomerce
├── config/           # empty — no @Configuration yet
├── controller/       # MVC @Controller (6 controllers)
├── dto/              # 22 DTOs across 9 subdirs (carrito/, categoria/, checkout/, etc.)
├── entity/           # 7 entities + base/Bitacora (audit) + enums/
├── exception/        # 8 custom exceptions + @ControllerAdvice handler
├── mapper/           # 8 mapper components (entity ↔ DTO)
├── repository/       # 7 JPA repositories + specification/
├── service/          # 5 interfaces + impl classes
└── util/             # empty
```

## Architecture conventions
- **Lombok** on all entities/DTOs: `@Builder @AllArgsConstructor @NoArgsConstructor @Setter @Getter`
- **DTOs** follow pattern: `*RequestDTO` (input), `*ResponseDTO` (full), `*ResumenDTO` (list summary)
- **Mappers** are `@Component` classes, not MapStruct. Each entity has a dedicated mapper.
- **Services**: interface + implementation (`*ServiceImpl`), both in separate packages.
- **Controllers**: return view names (MVC), not REST. Use `@RequiredArgsConstructor` for DI.
- **Repository queries**: derived method names, `@Query` (JPQL), `nativeQuery`, and `JpaSpecificationExecutor` for dynamic filters (`ProductoSpecification`).

## Exception pattern
- Exceptions extend `ResourceNotFoundException` or `DuplicateResourceException` (both abstract, extend `RuntimeException`)
- `GlobalExceptionHandler` catches each → `ra.addFlashAttribute("error", message)` → redirect
- All views include `fragments/comun/mensaje-error :: mensaje-error` to display flash errors

## Template architecture
- 36 Thymeleaf templates under `templates/`
- Layout fragments: `fragments/layout/header|navbar|footer|scripts`
- Business fragments: `fragments/home/`, `fragments/producto/`, `fragments/carrito/`, etc.
- Each view uses: `header :: header(title)` → `navbar :: navbar` → `<main>` → `footer :: footer` → `scripts :: scripts`
- Fragment inclusion path: `fragments/<dir>/<name> :: <fragment>`

## Key implementation details
- **Cart**: stored in HTTP session (`@SessionScope` bean `CarritoDTO`), not persisted in DB
- **Checkout**: creates `Pedido` + `DetallePedido` entities, deducts stock, clears session cart. Uses `@Transactional`
- **No Spring Security**: `pedido.setUsuario(null)` is a temporary placeholder. Db entities `Rol`, `Permiso`, `Usuario` exist but aren't wired.
- **Featured products**: `ProductoRepository.findTop8ByOrderByIdDesc()` — latest 8
- **Home categories top4**: `CategoriaRepository.findTop4ByOrderByNombreDesc()` — 4 categories Z→A
- **Product filtering**: `ProductoSpecification` (JPA Criteria) with `JpaSpecificationExecutor`; applies category, name prefix, price range filters
- **Pagination**: `PageRequest.of(page, size)` with `@RequestParam(defaultValue = "0") int pagina`
- **Redirect after create/update**: always `redirect:/<entity>` (list page), flash attributes for errors/success

## DB
- Hibernate `ddl-auto: update` — schema auto-managed
- Table names in Spanish plural: `categorias, productos, usuarios, roles, pedidos, detalles_pedido`
