-- 1) Setup inicial: países + bodegas (Ecuador, Colombia, Perú)
-- Países
INSERT INTO countries (iso2, name) VALUES
                                       ('EC','Ecuador'),
                                       ('CO','Colombia'),
                                       ('PE','Perú');

-- Bodegas (1 por país, por ahora)
INSERT INTO warehouses (country_id, code, name, city, address, is_active, is_default)
SELECT id, 'UIO-01', 'Bodega Quito Principal', 'Quito', 'Parque Industrial Quito', 1, 1
FROM countries WHERE iso2='EC';

INSERT INTO warehouses (country_id, code, name, city, address, is_active, is_default)
SELECT id, 'BOG-01', 'Bodega Bogotá Principal', 'Bogotá', 'Zona Industrial Bogotá', 1, 1
FROM countries WHERE iso2='CO';

INSERT INTO warehouses (country_id, code, name, city, address, is_active, is_default)
SELECT id, 'LIM-01', 'Bodega Lima Principal', 'Lima', 'Zona Industrial Lima', 1, 1
FROM countries WHERE iso2='PE';




-- 2) Catálogo: marcas, categorías, productos (HU1)
-- Marcas
INSERT INTO brands (name) VALUES ('ArkaBasics'), ('TechNova');

-- Categorías (ejemplo simple)
INSERT INTO categories (parent_id, name, slug, is_active) VALUES
                                                              (NULL, 'Electrónica', 'electronica', 1),
                                                              (NULL, 'Hogar', 'hogar', 1);

-- Productos
INSERT INTO products (sku, name, description, price, brand_id, is_active, min_stock, reorder_point, lead_time_days)
VALUES
    ('SKU-001', 'Audífonos Inalámbricos', 'Bluetooth, cancelación de ruido', 35.90,
     (SELECT id FROM brands WHERE name='TechNova'), 1, 10, 15, 7),

    ('SKU-002', 'Lámpara LED', 'Lámpara para escritorio, luz cálida/fría', 12.50,
     (SELECT id FROM brands WHERE name='ArkaBasics'), 1, 20, 30, 5);

-- Multi-categoría (ambos en Electrónica, lámpara también en Hogar)
INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products p
         JOIN categories c ON c.slug='electronica'
WHERE p.sku IN ('SKU-001','SKU-002');

INSERT INTO product_categories (product_id, category_id)
SELECT p.id, c.id
FROM products p
         JOIN categories c ON c.slug='hogar'
WHERE p.sku='SKU-002';



-- 3) Inventario inicial por bodega + umbrales (HU1/HU3)
-- Saldos iniciales (EC)
INSERT INTO inventory_balances (product_id, warehouse_id, on_hand, reserved)
SELECT p.id, w.id,
       CASE p.sku WHEN 'SKU-001' THEN 25 WHEN 'SKU-002' THEN 40 END,
       0
FROM products p
         JOIN warehouses w ON w.code='UIO-01'
WHERE p.sku IN ('SKU-001','SKU-002');

-- Saldos iniciales (CO)
INSERT INTO inventory_balances (product_id, warehouse_id, on_hand, reserved)
SELECT p.id, w.id,
       CASE p.sku WHEN 'SKU-001' THEN 8 WHEN 'SKU-002' THEN 12 END,
       0
FROM products p
         JOIN warehouses w ON w.code='BOG-01'
WHERE p.sku IN ('SKU-001','SKU-002');

-- Saldos iniciales (PE)
INSERT INTO inventory_balances (product_id, warehouse_id, on_hand, reserved)
SELECT p.id, w.id,
       CASE p.sku WHEN 'SKU-001' THEN 18 WHEN 'SKU-002' THEN 5 END,
       0
FROM products p
         JOIN warehouses w ON w.code='LIM-01'
WHERE p.sku IN ('SKU-001','SKU-002');

-- Umbral configurable por bodega (HU3)
-- (ejemplo: alertar si disponible < threshold)
INSERT INTO reorder_settings (product_id, warehouse_id, threshold)
SELECT p.id, ib.warehouse_id,
       CASE p.sku WHEN 'SKU-001' THEN 10 WHEN 'SKU-002' THEN 15 END
FROM products p
         JOIN inventory_balances ib ON ib.product_id = p.id
WHERE p.sku IN ('SKU-001','SKU-002');



-- 4) Selección de bodega por país del cliente (tu regla)
--Supón:

--Cliente Ecuador: user_id=101 (país EC)

--Cliente Colombia: user_id=202 (país CO)

--Cliente Perú: user_id=303 (país PE)

--Cómo obtienes la bodega default por país (consulta base que usarás en tu app):

-- Obtener bodega default para un país (ej: Ecuador)
SELECT w.id, w.code, w.name
FROM warehouses w
         JOIN countries c ON c.id = w.country_id
WHERE c.iso2='EC' AND w.is_default=1 AND w.is_active=1
    LIMIT 1;





-- 5) Flujo de compra con carrito + reserva anti-sobreventa (HU4/HU5/HU8)
-- 5.1 Crear carrito y agregar items (cliente Ecuador, user_id=101)
-- Carrito
INSERT INTO carts (user_id, status, last_activity_at)
VALUES (101, 'ACTIVE', NOW());

-- Item 1: SKU-001 x2 en bodega Ecuador (UIO-01)
INSERT INTO cart_items (cart_id, product_id, warehouse_id, quantity)
SELECT c.id, p.id, w.id, 2
FROM carts c, products p, warehouses w
WHERE c.user_id=101 AND c.status='ACTIVE'
  AND p.sku='SKU-001'
  AND w.code='UIO-01'
ORDER BY c.id DESC
    LIMIT 1;

-- Item 2: SKU-002 x1
INSERT INTO cart_items (cart_id, product_id, warehouse_id, quantity)
SELECT c.id, p.id, w.id, 1
FROM carts c, products p, warehouses w
WHERE c.user_id=101 AND c.status='ACTIVE'
  AND p.sku='SKU-002'
  AND w.code='UIO-01'
ORDER BY c.id DESC
    LIMIT 1;


--5.2 Reservar stock (anti-sobreventa)

--Ejemplo: reservas por 15 minutos.

-- Reserva para SKU-001
INSERT INTO stock_reservations (product_id, warehouse_id, cart_id, quantity, status, expires_at)
SELECT p.id, w.id, c.id, 2, 'ACTIVE', DATE_ADD(NOW(), INTERVAL 15 MINUTE)
FROM products p
         JOIN warehouses w ON w.code='UIO-01'
         JOIN carts c ON c.user_id=101 AND c.status='ACTIVE'
WHERE p.sku='SKU-001'
ORDER BY c.id DESC
    LIMIT 1;

-- Aumentar reserved en inventory_balances
UPDATE inventory_balances ib
    JOIN products p ON p.id=ib.product_id
    JOIN warehouses w ON w.id=ib.warehouse_id
    SET ib.reserved = ib.reserved + 2
WHERE p.sku='SKU-001' AND w.code='UIO-01';

-- Ledger RESERVE
INSERT INTO inventory_movements (product_id, warehouse_id, movement_type, quantity, ref_type, ref_id, note)
SELECT p.id, w.id, 'RESERVE', 2, 'CART', c.id, 'Reserva por carrito (checkout)'
FROM products p
         JOIN warehouses w ON w.code='UIO-01'
         JOIN carts c ON c.user_id=101 AND c.status='ACTIVE'
WHERE p.sku='SKU-001'
ORDER BY c.id DESC
    LIMIT 1;

--(Repite igual para SKU-002 x1.)

--5.3 Crear la orden desde el carrito (HU4)

-- Crear orden PENDING
INSERT INTO orders (order_number, user_id, warehouse_id, status, payment_status, currency, subtotal, shipping, total,
                    shipping_name, shipping_phone, shipping_address)
SELECT CONCAT('EC-', DATE_FORMAT(NOW(), '%Y%m%d'), '-', LPAD(FLOOR(RAND()*99999),5,'0')),
       101,
       w.id,
       'PENDING',
       'PENDING',
       'USD',
       0, 0, 0,
       'Cliente EC', '0990000000', 'Quito - Dirección 123'
FROM warehouses w
WHERE w.code='UIO-01'
    LIMIT 1;

-- Pasar items del carrito a order_items con precio "congelado"
INSERT INTO order_items (order_id, product_id, quantity, unit_price, line_total)
SELECT o.id, ci.product_id, ci.quantity, p.price, (ci.quantity * p.price)
FROM orders o
         JOIN carts c ON c.user_id=o.user_id AND c.status='ACTIVE'
         JOIN cart_items ci ON ci.cart_id=c.id
         JOIN products p ON p.id=ci.product_id
WHERE o.user_id=101
ORDER BY o.id DESC;

-- Calcular totales
UPDATE orders o
    JOIN (
    SELECT order_id, SUM(line_total) AS subtotal
    FROM order_items
    GROUP BY order_id
    ) x ON x.order_id=o.id
    SET o.subtotal=x.subtotal, o.total=(x.subtotal + o.shipping)
WHERE o.user_id=101
ORDER BY o.id DESC
    LIMIT 1;

-- Marcar carrito CHECKED_OUT
UPDATE carts
SET status='CHECKED_OUT', last_activity_at=NOW()
WHERE user_id=101 AND status='ACTIVE'
    ORDER BY id DESC
LIMIT 1;

-- Vincular reservas al order_id (si estabas reservando por cart_id)
UPDATE stock_reservations sr
    JOIN carts c ON c.id=sr.cart_id
    JOIN orders o ON o.user_id=c.user_id
    SET sr.order_id = (SELECT id FROM orders WHERE user_id=101 ORDER BY id DESC LIMIT 1)
WHERE c.user_id=101 AND sr.status='ACTIVE';


--5.4 Confirmación de orden (simula pago) + consumo de stock (HU4/HU2)
-- Cambiar estado a CONFIRMED
UPDATE orders
SET status='CONFIRMED', payment_status='PAID'
WHERE user_id=101
    ORDER BY id DESC
LIMIT 1;

-- Historial de estado
INSERT INTO order_status_history (order_id, from_status, to_status, changed_by)
SELECT id, 'PENDING', 'CONFIRMED', NULL
FROM orders
WHERE user_id=101
ORDER BY id DESC
    LIMIT 1;

-- Notificación HU6 (EMAIL)
INSERT INTO notifications (type, channel, user_id, order_id, payload, status, scheduled_at)
SELECT 'ORDER_STATUS_CHANGED', 'EMAIL', o.user_id, o.id,
       JSON_OBJECT('order_number', o.order_number, 'to_status', o.status),
       'PENDING', NOW()
FROM orders o
WHERE o.user_id=101
ORDER BY o.id DESC
    LIMIT 1;

-- Consumir reservas: (1) bajar on_hand, (2) bajar reserved, (3) marcar reservas CONSUMED
-- SKU-001 y SKU-002 en UIO-01 según order_items
UPDATE inventory_balances ib
    JOIN warehouses w ON w.id=ib.warehouse_id
    JOIN order_items oi ON oi.product_id=ib.product_id
    JOIN orders o ON o.id=oi.order_id
    SET ib.on_hand = ib.on_hand - oi.quantity,
        ib.reserved = ib.reserved - oi.quantity
WHERE w.code='UIO-01' AND o.user_id=101
  AND o.id = (SELECT id FROM orders WHERE user_id=101 ORDER BY id DESC LIMIT 1);

-- Ledger OUT
INSERT INTO inventory_movements (product_id, warehouse_id, movement_type, quantity, ref_type, ref_id, note)
SELECT oi.product_id, o.warehouse_id, 'OUT', oi.quantity, 'ORDER', o.id, 'Salida por venta confirmada'
FROM order_items oi
         JOIN orders o ON o.id=oi.order_id
WHERE o.user_id=101
  AND o.id = (SELECT id FROM orders WHERE user_id=101 ORDER BY id DESC LIMIT 1);

-- Marcar reservas consumidas
UPDATE stock_reservations
SET status='CONSUMED'
WHERE order_id = (SELECT id FROM orders WHERE user_id=101 ORDER BY id DESC LIMIT 1)
  AND status='ACTIVE';

--6) Modificar pedido antes de confirmar (HU5) — ejemplo rápido

--Simula un pedido PENDING y el cliente elimina un item: debes liberar reserva y actualizar reserved.

--(En tu caso ya confirmamos; aquí va el patrón HU5):
-- (Patrón) Si order está PENDING y quitas 1 unidad de SKU-001:
-- 1) ajustar order_items (cantidad o delete)
-- 2) liberar reserva (stock_reservations -> RELEASED)
-- 3) disminuir inventory_balances.reserved
-- 4) ledger RELEASE

-- Disminuir reserved en saldo (ejemplo: -1)
UPDATE inventory_balances ib
    JOIN products p ON p.id=ib.product_id
    JOIN warehouses w ON w.id=ib.warehouse_id
    SET ib.reserved = ib.reserved - 1
WHERE p.sku='SKU-001' AND w.code='UIO-01';

-- ledger RELEASE
INSERT INTO inventory_movements (product_id, warehouse_id, movement_type, quantity, ref_type, ref_id, note)
SELECT p.id, w.id, 'RELEASE', 1, 'ORDER',
       (SELECT id FROM orders WHERE user_id=101 ORDER BY id DESC LIMIT 1),
       'Liberación por modificación de pedido PENDING'
FROM products p
    JOIN warehouses w ON w.code='UIO-01'
WHERE p.sku='SKU-001'
    LIMIT 1;


-- Carrito CO (user_id=202)
INSERT INTO carts (user_id, status, last_activity_at)
VALUES (202, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO cart_items (cart_id, product_id, warehouse_id, quantity)
SELECT c.id, p.id, w.id, 1
FROM carts c, products p, warehouses w
WHERE c.user_id=202 AND c.status='ACTIVE'
  AND p.sku='SKU-001'
  AND w.code='BOG-01'
ORDER BY c.id DESC
    LIMIT 1;

-- Marcar abandonado (regla: inactivo > 24h)
UPDATE carts
SET status='ABANDONED', abandoned_at=NOW()
WHERE user_id=202 AND status='ACTIVE'
  AND last_activity_at < DATE_SUB(NOW(), INTERVAL 24 HOUR)
    ORDER BY id DESC
LIMIT 1;

-- Notificación recordatorio
INSERT INTO notifications (type, channel, user_id, cart_id, payload, status, scheduled_at)
SELECT 'CART_ABANDONED', 'EMAIL', c.user_id, c.id,
       JSON_OBJECT('cart_id', c.id, 'message', 'Tienes productos en tu carrito. ¿Deseas finalizar tu compra?'),
       'PENDING', NOW()
FROM carts c
WHERE c.user_id=202 AND c.status='ABANDONED'
ORDER BY c.id DESC
    LIMIT 1;


SELECT
    c.iso2 AS country,
    w.code AS warehouse_code,
    p.sku,
    p.name,
    ib.on_hand,
    ib.reserved,
    (ib.on_hand - ib.reserved) AS available,
    rs.threshold
FROM reorder_settings rs
         JOIN inventory_balances ib ON ib.product_id=rs.product_id AND ib.warehouse_id=rs.warehouse_id
         JOIN products p ON p.id=rs.product_id
         JOIN warehouses w ON w.id=rs.warehouse_id
         JOIN countries c ON c.id=w.country_id
WHERE (ib.on_hand - ib.reserved) < rs.threshold
ORDER BY c.iso2, w.code, available ASC;



SELECT
    YEARWEEK(o.created_at, 1) AS yearweek,
    COUNT(*) AS orders_count,
    SUM(o.total) AS total_sales
FROM orders o
WHERE o.payment_status='PAID'
  AND o.created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
GROUP BY YEARWEEK(o.created_at, 1)
ORDER BY yearweek DESC;


SELECT
    p.sku,
    p.name,
    SUM(oi.quantity) AS units_sold,
    SUM(oi.line_total) AS revenue
FROM order_items oi
         JOIN orders o ON o.id=oi.order_id
         JOIN products p ON p.id=oi.product_id
WHERE o.payment_status='PAID'
  AND o.created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
GROUP BY p.id
ORDER BY units_sold DESC
    LIMIT 10;


SELECT
    o.user_id,
    COUNT(*) AS orders_count,
    SUM(o.total) AS total_spent
FROM orders o
WHERE o.payment_status='PAID'
  AND o.created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
GROUP BY o.user_id
ORDER BY orders_count DESC, total_spent DESC
    LIMIT 10;


SELECT
    c.user_id,
    c.id AS cart_id,
    c.abandoned_at,
    GROUP_CONCAT(CONCAT(p.sku, ' x', ci.quantity) ORDER BY p.sku SEPARATOR ', ') AS items
FROM carts c
         JOIN cart_items ci ON ci.cart_id=c.id
         JOIN products p ON p.id=ci.product_id
WHERE c.status='ABANDONED'
GROUP BY c.id
ORDER BY c.abandoned_at DESC;


SELECT
    p.sku,
    w.code AS warehouse_code,
    (ib.on_hand - ib.reserved) AS available
FROM inventory_balances ib
         JOIN products p ON p.id=ib.product_id
         JOIN warehouses w ON w.id=ib.warehouse_id
WHERE p.sku='SKU-001' AND w.code='UIO-01';
