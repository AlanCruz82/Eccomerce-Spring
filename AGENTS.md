# AGENTS.md — Eccomerce

## Stack
Java 21, Spring Boot 4.1.0, Thymeleaf (`thymeleaf-extras-springsecurity6`), Spring Data JPA (Hibernate), Spring Security, MySQL, Lombok, Maven.

## Dev commands
```bash
./mvnw compile           # compile only (runs annotation processors: Lombok)
./mvnw spring-boot:run   # run dev server (default port 8081, profile dev)
./mvnw test              # run tests (currently a single context-load test)
```

## Environment variables (via `application.yaml` / `application-dev.yaml`)
```
DB_URL=jdbc:mysql://localhost:3306/eccomerce
DB_USER=root
DB_PASSWORD=your_password
SERVER_PORT=8081
```

## Project structure
```
com.spring.eccomerce
├── config/            # SecurityConfig, WebConfig (sirve /uploads/**), StorageProperties
├── controller/        # 7 public MVC controllers + admin/ (3: Categoria, Producto, Pedido)
├── dto/               # 22 DTOs across 9 subdirs (carrito/, categoria/, checkout/,
│                      #   detallesPedido/, pedido/, permiso/, producto/, rol/, usuario/)
├── entity/            # 7 entities + base/Bitacora (audit) + enums/ (EstadoPedido, NombreRol)
├── exception/         # 11 concrete + 2 abstract bases + @ControllerAdvice handler
├── mapper/            # 8 mapper components (entity ↔ DTO)
├── repository/        # 7 JPA repositories + projection/ + specification/
├── service/           # 7 interfaces + impl/ (incl. storage/ y security/)
└── util/              # no existe
```

## Architecture conventions
- **Lombok** on all entities/DTOs: `@Builder @AllArgsConstructor @NoArgsConstructor @Setter @Getter`
- **DTOs** follow pattern: `*RequestDTO` (input), `*ResponseDTO` (full), `*ResumenDTO` (list summary)
- **Mappers** are `@Component` classes, not MapStruct. Each entity has a dedicated mapper.
- **Services**: interface + implementation (`*ServiceImpl`), both in separate packages.
- **Controllers**: return view names (MVC), not REST. Use `@RequiredArgsConstructor` for DI. Admin controllers live under `controller/admin/` and map `/admin/**`.
- **Repository queries**: derived method names, `@Query` (JPQL), `nativeQuery`, and `JpaSpecificationExecutor` for dynamic filters (`ProductoSpecification`, `PedidoSpecification`).

## Exception pattern
- Exceptions extend `ResourceNotFoundException` or `DuplicateResourceException` (both abstract, extend `RuntimeException`); business errors extend `RuntimeException` directly (`CarritoVacioException`, `StockInsuficienteException`, `StorageExcepcion`)
- `GlobalExceptionHandler` catches each → `ra.addFlashAttribute("error", message)` → redirect
- All views include `fragments/comun/mensaje-error :: mensaje-error` to display flash errors

## Template architecture
- 14 view templates + 23 fragments under `templates/` (`auth/`, `carrito/`, `categoria/`, `checkout/`, `home/`, `pedido/`, `producto/`)
- Layout fragments: `fragments/layout/header|navbar|footer|scripts`
- Business fragments: `fragments/home/`, `fragments/producto/`, `fragments/carrito/`, etc.
- Each view uses: `header :: header(title)` → `navbar :: navbar` → `<main>` → `footer :: footer` → `scripts :: scripts`
- Fragment inclusion path: `fragments/<dir>/<name> :: <fragment>`
- Role-based content uses `sec:authorize` / `sec:authentication` (Spring Security dialect)

## Key implementation details
- **Cart**: stored in HTTP session (@SessionScope bean `CarritoDTO`), not persisted in DB
- **Checkout**: creates `Pedido` + `DetallePedido`, assigns the authenticated `Usuario`, deducts stock, clears session cart. Uses `@Transactional`. New orders start as `EstadoPedido.PENDIENTE`
- **Spring Security (implementado)**: form login (`GET/POST /login`), registration (`/registro` hashes with BCrypt and assigns `CLIENTE` role). Authorization: `/admin/**` → `ROLE_ADMIN`; `/pedidos/usuario`, `/pedidos/**` → authenticated; public for `/`, `/productos`, `/categorias`, `/carrito/**`, `/login`, `/registro`. `UserDetailsImpl` (UserDetailsService por `correoElectronico`), `UsuarioSecurity` (authority `ROLE_<nombre>`)
- **Role/Permiso (parcial)**: entities `Rol`/`Permiso` y tabla `roles_permisos` existen, pero solo se usa autorización por rol; los permisos finos no están cableados
- **Featured products**: `ProductoRepository.findTop8ByOrderByIdDesc()` — latest 8
- **Home top-4 categories**: native query `findTop4CategoriasConMasProductos()` + projection `ICategoriaConCantidad` (top 4 por cantidad de productos)
- **Product filtering**: `ProductoSpecification` (JPA Criteria) with `JpaSpecificationExecutor`; applies category, name prefix, price min/max range y `existenciaGreaterThan` (stock mínimo, input en `fragments/producto/filtros.html`)
- **Pagination**: `PageRequest.of(pagina, tamano)` with `@RequestParam(defaultValue = "0") int pagina`
- **Image storage**: `LocalStorageService` guarda en `uploads/productos` con nombre UUID; `WebConfig` sirve `/uploads/**`; borra imagen al actualizar/eliminar producto
- **Redirect after create/update**: `redirect:/<entity>` (list page), flash attributes for errors/success

## DB
- Hibernate `ddl-auto: update` — schema auto-managed
- Tables in Spanish plural: `categorias, productos, usuarios, roles, pedidos, detalles_pedido` + join `roles_permisos`