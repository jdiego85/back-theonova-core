# agent.md (v2) — Construcción del ecommerce desde arquitectura vacía (MySQL 8 + RouterFunctions)

## Objetivo

Implementar un ecommerce robusto (hexagonal + clean code) donde:

* El **modelo entidad-relación** se define primero (tablas, claves, índices, constraints).
* Todo CRUD de datos administrables se hace vía **servicios web (RouterFunctions)**.
* Los flujos de compra (carrito/reservas/orden/pago) también se realizan vía API.
* Se habilita base para un futuro **front de administración**.

---

# 0) Convenciones de arquitectura (Scaffold de Bancolombia)

* **domain/model**: El núcleo sagrado.
  * `*Entidades` Objetos de negocio puros (POJOs).
  * `*Gateways` Aquí van las Interfaces (Puertos de Salida).
  * Diferencia clave: En este scaffold, las interfaces de repositorios viven dentro del model, no en application.
* **domain/usecase:** La orquestación.
  * Aquí vive la Lógica de Negocio.
  * Las clases aquí (ej. CrearUsuarioUseCase) implementan la lógica y llaman a las interfaces definidas en model/gateways.
* **infrastructure/entry-points**: La entrada (Driving Adapter).
  * api-rest: Aquí vive la capa web funcional.
    * Routes (RouterFunction): Definen qué URL va a qué método (el mapeo). Reemplazan a las anotaciones @GetMapping, @PostMapping.
    * Handlers: Contienen la lógica de orquestación de la petición (validar request $\to$ llamar caso de uso $\to$ crear response). Reemplazan a los métodos dentro de un @RestController.
  * Convierte DTOs (Request) $\to$ llama al UseCase $\to$ devuelve DTOs (Response).
* **infrastructure/driven-adapters**: La salida (Driven Adapter).
  * jpa-repository / jpa-repository: Implementación de base de datos.
  * http-client: Consumo de APIs externas.
  * Estas clases implementan las interfaces que definiste en domain/model/gateways.
* **applications/app-service:** El arranque
  * Configuración (Beans): Aquí unes todo. Creas los @Bean de tus casos de uso inyectando los repositorios.
  * MainApplication: La clase que arranca Spring Boot.
      
---

# 1) Modelo Entidad–Relación (ER) y reglas de datos (PRIORIDAD 1)

## Tarea 1.1 — Definir entidades "administrables" (CRUD por administración)

**Administrables (CRUD completo):**

* `countries` (catálogo de países)
* `warehouses` (bodegas por país; default por país)
* `brands`
* `categories` (con posible jerarquía)
* `products`
* `product_categories` (relación N–N)
* `inventory_balances` (stock por bodega)  ✅ administrable por backoffice
* `reorder_settings` (umbral por producto/bodega) ✅ administrable por backoffice

**No administrables (solo por flujo de negocio / sistema):**

* `carts`, `cart_items`
* `stock_reservations`
* `inventory_movements`
* `orders`, `order_items`
* `order_status_history`
* `notifications`

## Tarea 1.2 — Definir claves, constraints e índices (MySQL 8)

**Reglas mínimas recomendadas:**

* `countries.iso2` UNIQUE (EC/CO/PE)
* `warehouses.code` UNIQUE
* `warehouses`: UNIQUE(country_id, is_default) donde is_default=1 (1 default por país) *(resolver con constraint lógica en app + índice)*
* `brands.name` UNIQUE
* `categories.slug` UNIQUE
* `products.sku` UNIQUE
* `product_categories`: UNIQUE(product_id, category_id)
* `inventory_balances`: UNIQUE(product_id, warehouse_id), CHECK on_hand>=0, reserved>=0
* `reorder_settings`: UNIQUE(product_id, warehouse_id), threshold>=0
* `stock_reservations`: índice por (status, expires_at) para jobs/cleanup
* `orders.order_number` UNIQUE
* `order_items`: índice (order_id), y (product_id)

## Tarea 1.3 — Documento ER (entrega)

* Crear diagrama ER (DBML o Mermaid ER) como artefacto del repositorio: `docs/er/er_v2.md`.
* Acompañar con `docs/er/data_dictionary.md` (campos + significado).

---

# 2) Puertos y repositorios (PRIORIDAD 2)

## Tarea 2.1 — Definir ports OUT (persistencia) por agregado

**Catalog**

* CountryRepositoryPort: create/update/delete/find/list
* WarehouseRepositoryPort: create/update/delete/find/list + findDefaultByCountry(iso2)
* BrandRepositoryPort
* CategoryRepositoryPort
* ProductRepositoryPort (+ findBySku)

**Inventory**

* InventoryBalanceRepositoryPort (+ lockByProductWarehouse for reservas)
* ReorderSettingsRepositoryPort

**Checkout**

* CartRepositoryPort
* CartItemRepositoryPort
* StockReservationRepositoryPort
* OrderRepositoryPort
* OrderItemRepositoryPort
* InventoryMovementRepositoryPort
* OrderStatusHistoryRepositoryPort
* NotificationRepositoryPort

## Tarea 2.2 — Implementar adapters MySQL (repositorios)

* Implementar `...adapter/out/mysql/*RepositoryMySql`.
* Considerar transacciones en capa de usecase (no en handlers).
* Para anti-sobreventa:

    * Query tipo `SELECT ... FOR UPDATE` sobre `inventory_balances` (por product_id + warehouse_id)
    * o `UPDATE ... SET reserved = reserved + ? WHERE (on_hand - reserved) >= ?` y verificar rows affected.

---

# 3) CRUD vía API para administración (PRIORIDAD 3)

## Tarea 3.1 — RouterFunctions + Handlers: Countries

* `GET /admin/countries`
* `GET /admin/countries/{id}`
* `POST /admin/countries`
* `PUT /admin/countries/{id}`
* `DELETE /admin/countries/{id}`

Reglas:

* iso2 uppercase, length=2
* no permitir delete si tiene warehouses asociadas (409)

## Tarea 3.2 — Warehouses (incluye default por país)

* `GET /admin/warehouses`
* `GET /admin/warehouses/{id}`
* `POST /admin/warehouses`
* `PUT /admin/warehouses/{id}`
* `DELETE /admin/warehouses/{id}`
* `GET /warehouses/default?country=EC`  (público para runtime)

Reglas:

* En POST/PUT si `is_default=1`, garantizar que no exista otro default para ese país (transaccional).
* code UNIQUE.

## Tarea 3.3 — Brands / Categories / Products

* Brands:

    * `GET/POST/PUT/DELETE /admin/brands...`
* Categories:

    * `GET/POST/PUT/DELETE /admin/categories...`
* Products:

    * `GET /admin/products`
    * `GET /admin/products/{id}`
    * `POST /admin/products`
    * `PUT /admin/products/{id}`
    * `DELETE /admin/products/{id}`

## Tarea 3.4 — Product-Categories (asignación)

* `PUT /admin/products/{id}/categories` (reemplaza lista completa)
* Alternativa:

    * `POST /admin/product-categories`
    * `DELETE /admin/product-categories/{id}`

## Tarea 3.5 — Inventory admin (stock por bodega)

* `GET /admin/inventory-balances?warehouseId=...&sku=...`
* `POST /admin/inventory-balances` (crear fila product+warehouse)
* `PUT /admin/inventory-balances/{id}` (ajustar on_hand, NO reserved)
* `POST /admin/inventory-balances/{id}/adjust` (movimiento IN/OUT manual, genera inventory_movements)

Reglas:

* `reserved` NO se toca por administración (solo por reservas/checkout).
* Ajustes generan `inventory_movements` con `ref_type='ADMIN'`.

## Tarea 3.6 — Reorder settings admin

* CRUD `GET/POST/PUT/DELETE /admin/reorder-settings`
* Regla: threshold >= 0

---

# 4) Flujos de negocio vía API (PRIORIDAD 4)

## Tarea 4.1 — Carrito (runtime)

* `POST /carts` (crea carrito ACTIVE para user)
* `GET /carts/{id}`
* `POST /carts/{id}/items` (agrega item; aplica bodega según país del cliente)
* `PUT /carts/{id}/items/{itemId}` (cambia qty)
* `DELETE /carts/{id}/items/{itemId}`
* `POST /carts/{id}/abandon` (marcar abandonado manual o por job)

## Tarea 4.2 — Reserva anti-sobreventa (HU4 runtime)

* `POST /carts/{id}/reserve`

    * valida available = on_hand - reserved
    * crea `stock_reservations` ACTIVE con expires_at
    * incrementa reserved de `inventory_balances`
    * crea `inventory_movements` RESERVE

Reglas:

* operación transaccional
* idempotencia opcional (si ya hay reservas ACTIVE para el carrito, renovar expires o devolver estado)

## Tarea 4.3 — Checkout (carrito -> orden PENDING)

* `POST /orders/checkout`

    * input: cart_id + datos envío + país cliente
    * crea order PENDING
    * copia items a order_items (congelar price)
    * vincula reservas del carrito a la orden
    * marca carrito CHECKED_OUT

## Tarea 4.4 — Confirmación de pago (orden -> CONFIRMED/PAID)

* `POST /orders/{id}/confirm-payment`

    * cambia order a CONFIRMED y PAID
    * consume stock: on_hand -= qty y reserved -= qty
    * inventory_movements OUT
    * stock_reservations -> CONSUMED
    * order_status_history
    * notifications (ORDER_STATUS_CHANGED)

---

# 5) Consultas/reportes vía API (PRIORIDAD 5)

## Tarea 5.1 — HU3 abastecimiento

* `GET /reports/reorder?country=EC&warehouseCode=...`
  Devuelve lista donde available < threshold.

## Tarea 5.2 — HU7 ventas y tops

* `GET /reports/sales/weekly`
* `GET /reports/products/top?days=7&limit=10`
* `GET /reports/customers/top?days=7&limit=10`

## Tarea 5.3 — HU8 carritos abandonados

* `GET /reports/carts/abandoned?days=7`

---

# 6) Seguridad y preparación para front admin (PRIORIDAD 6)

* Prefijo `/admin/*` protegido (JWT/Basic temporal).
* Validaciones DTO (Bean Validation).
* Manejo de errores uniforme (Problem+JSON o estructura estándar).
* Paginación en listados admin.
* Auditoría mínima: created_at/updated_at en tablas administrables.
