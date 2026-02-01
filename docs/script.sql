-- =========================================================
--  THEONOVA - Multi-almacén / Multi-país (MySQL 8.0+)
-- =========================================================
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- -------------------------
-- 1) Catálogo base
-- -------------------------

CREATE TABLE countries (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  iso2          CHAR(2) NOT NULL UNIQUE,
  name          VARCHAR(100) NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE warehouses (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  country_id    BIGINT UNSIGNED NOT NULL,
  code          VARCHAR(30) NOT NULL,
  name          VARCHAR(120) NOT NULL,
  city          VARCHAR(120) NULL,
  address       VARCHAR(255) NULL,
  is_active     TINYINT(1) NOT NULL DEFAULT 1,
  is_default    TINYINT(1) NOT NULL DEFAULT 0,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_warehouses_country
    FOREIGN KEY (country_id) REFERENCES countries(id),
  UNIQUE KEY uq_warehouse_country_code (country_id, code),
  KEY idx_warehouse_country (country_id)
) ENGINE=InnoDB;

CREATE TABLE brands (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(120) NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_brand_name (name)
) ENGINE=InnoDB;

CREATE TABLE categories (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  parent_id     BIGINT UNSIGNED NULL,
  name          VARCHAR(120) NOT NULL,
  slug          VARCHAR(160) NOT NULL,
  is_active     TINYINT(1) NOT NULL DEFAULT 1,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uq_category_slug (slug),
  KEY idx_category_parent (parent_id),
  CONSTRAINT fk_category_parent
    FOREIGN KEY (parent_id) REFERENCES categories(id)
) ENGINE=InnoDB;

-- Nota: users no la defino aquí porque suele venir de tu sistema de auth.
-- Si necesitas users, la agregamos. Por ahora referencio user_id como BIGINT.

CREATE TABLE products (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  sku           VARCHAR(64) NOT NULL,
  name          VARCHAR(180) NOT NULL,
  description   TEXT NULL,
  price         DECIMAL(12,2) NOT NULL,
  brand_id      BIGINT UNSIGNED NULL,
  is_active     TINYINT(1) NOT NULL DEFAULT 1,
  min_stock     INT NOT NULL DEFAULT 0,
  reorder_point INT NOT NULL DEFAULT 0,
  lead_time_days INT NOT NULL DEFAULT 0,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_products_brand
    FOREIGN KEY (brand_id) REFERENCES brands(id),
  UNIQUE KEY uq_product_sku (sku),
  KEY idx_products_brand (brand_id),
  CHECK (price >= 0),
  CHECK (min_stock >= 0),
  CHECK (reorder_point >= 0),
  CHECK (lead_time_days >= 0)
) ENGINE=InnoDB;

-- Multi-categoría (recomendado)
CREATE TABLE product_categories (
  product_id    BIGINT UNSIGNED NOT NULL,
  category_id   BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (product_id, category_id),
  CONSTRAINT fk_pc_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
  CONSTRAINT fk_pc_category
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
  KEY idx_pc_category (category_id)
) ENGINE=InnoDB;

-- Atributos flexibles para filtros (color, talla, material, etc.)
CREATE TABLE attributes (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(120) NOT NULL,
  data_type     ENUM('STRING','NUMBER','BOOLEAN') NOT NULL,
  is_filterable TINYINT(1) NOT NULL DEFAULT 1,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_attr_name (name)
) ENGINE=InnoDB;

CREATE TABLE product_attribute_values (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  product_id    BIGINT UNSIGNED NOT NULL,
  attribute_id  BIGINT UNSIGNED NOT NULL,
  value_string  VARCHAR(255) NULL,
  value_number  DECIMAL(18,6) NULL,
  value_boolean TINYINT(1) NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_pav_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
  CONSTRAINT fk_pav_attribute
    FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE RESTRICT,
  UNIQUE KEY uq_pav_product_attribute (product_id, attribute_id),
  KEY idx_pav_attribute (attribute_id)
) ENGINE=InnoDB;

-- -------------------------
-- 2) Inventario (saldo + movimientos + reorden)
-- -------------------------

CREATE TABLE inventory_balances (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  product_id    BIGINT UNSIGNED NOT NULL,
  warehouse_id  BIGINT UNSIGNED NOT NULL,
  on_hand       INT NOT NULL DEFAULT 0,
  reserved      INT NOT NULL DEFAULT 0,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_ib_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
  CONSTRAINT fk_ib_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE RESTRICT,
  UNIQUE KEY uq_ib_product_warehouse (product_id, warehouse_id),
  KEY idx_ib_warehouse (warehouse_id),
  CHECK (on_hand >= 0),
  CHECK (reserved >= 0)
) ENGINE=InnoDB;

-- Umbral configurable por producto y bodega (HU3)
CREATE TABLE reorder_settings (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  product_id    BIGINT UNSIGNED NOT NULL,
  warehouse_id  BIGINT UNSIGNED NOT NULL,
  threshold     INT NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_rs_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
  CONSTRAINT fk_rs_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE CASCADE,
  UNIQUE KEY uq_rs_product_warehouse (product_id, warehouse_id),
  CHECK (threshold >= 0)
) ENGINE=InnoDB;

-- Ledger / Kardex para historial de stock (HU2)
CREATE TABLE inventory_movements (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  product_id    BIGINT UNSIGNED NOT NULL,
  warehouse_id  BIGINT UNSIGNED NOT NULL,
  movement_type ENUM('IN','OUT','RESERVE','RELEASE','ADJUST_IN','ADJUST_OUT','RETURN_IN') NOT NULL,
  quantity      INT NOT NULL,
  ref_type      ENUM('ORDER','PURCHASE_ORDER','CART','ADJUSTMENT','RETURN') NOT NULL,
  ref_id        BIGINT UNSIGNED NULL,
  note          VARCHAR(255) NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_im_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
  CONSTRAINT fk_im_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE RESTRICT,
  KEY idx_im_product (product_id),
  KEY idx_im_warehouse (warehouse_id),
  KEY idx_im_ref (ref_type, ref_id),
  CHECK (quantity > 0)
) ENGINE=InnoDB;

-- Reservas para evitar sobreventa (HU4/HU5)
CREATE TABLE stock_reservations (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  product_id    BIGINT UNSIGNED NOT NULL,
  warehouse_id  BIGINT UNSIGNED NOT NULL,
  order_id      BIGINT UNSIGNED NULL,
  cart_id       BIGINT UNSIGNED NULL,
  quantity      INT NOT NULL,
  status        ENUM('ACTIVE','RELEASED','CONSUMED','EXPIRED') NOT NULL DEFAULT 'ACTIVE',
  expires_at    DATETIME NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_sr_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
  CONSTRAINT fk_sr_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE RESTRICT,
  KEY idx_sr_order (order_id),
  KEY idx_sr_cart (cart_id),
  KEY idx_sr_status_expires (status, expires_at),
  CHECK (quantity > 0)
) ENGINE=InnoDB;

-- -------------------------
-- 3) Carrito (HU8)
-- -------------------------

CREATE TABLE carts (
  id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id         BIGINT UNSIGNED NOT NULL,
  status          ENUM('ACTIVE','CHECKED_OUT','ABANDONED') NOT NULL DEFAULT 'ACTIVE',
  last_activity_at DATETIME NOT NULL,
  abandoned_at    DATETIME NULL,
  created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_carts_user (user_id),
  KEY idx_carts_status (status),
  KEY idx_carts_abandoned_at (abandoned_at)
) ENGINE=InnoDB;

CREATE TABLE cart_items (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  cart_id       BIGINT UNSIGNED NOT NULL,
  product_id    BIGINT UNSIGNED NOT NULL,
  warehouse_id  BIGINT UNSIGNED NOT NULL,
  quantity      INT NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_ci_cart
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
  CONSTRAINT fk_ci_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
  CONSTRAINT fk_ci_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE RESTRICT,
  UNIQUE KEY uq_ci_cart_product_wh (cart_id, product_id, warehouse_id),
  CHECK (quantity > 0)
) ENGINE=InnoDB;

-- -------------------------
-- 4) Órdenes de cliente (venta) (HU4/HU5/HU6/HU7)
-- -------------------------

CREATE TABLE orders (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_number  VARCHAR(40) NOT NULL,
  user_id       BIGINT UNSIGNED NOT NULL,
  warehouse_id  BIGINT UNSIGNED NOT NULL,
  status        ENUM('PENDING','CONFIRMED','DISPATCHED','DELIVERED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  payment_status ENUM('PENDING','PAID','FAILED','REFUNDED') NOT NULL DEFAULT 'PENDING',
  currency      CHAR(3) NOT NULL DEFAULT 'USD',
  subtotal      DECIMAL(12,2) NOT NULL DEFAULT 0,
  shipping      DECIMAL(12,2) NOT NULL DEFAULT 0,
  total         DECIMAL(12,2) NOT NULL DEFAULT 0,
  shipping_name VARCHAR(160) NULL,
  shipping_phone VARCHAR(40) NULL,
  shipping_address VARCHAR(255) NULL,
  notes         VARCHAR(255) NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_orders_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE RESTRICT,
  UNIQUE KEY uq_orders_number (order_number),
  KEY idx_orders_user (user_id),
  KEY idx_orders_status (status),
  CHECK (subtotal >= 0),
  CHECK (shipping >= 0),
  CHECK (total >= 0)
) ENGINE=InnoDB;

CREATE TABLE order_items (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_id      BIGINT UNSIGNED NOT NULL,
  product_id    BIGINT UNSIGNED NOT NULL,
  quantity      INT NOT NULL,
  unit_price    DECIMAL(12,2) NOT NULL,
  line_total    DECIMAL(12,2) NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_oi_order
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  CONSTRAINT fk_oi_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
  KEY idx_oi_order (order_id),
  KEY idx_oi_product (product_id),
  CHECK (quantity > 0),
  CHECK (unit_price >= 0),
  CHECK (line_total >= 0)
) ENGINE=InnoDB;

-- Historial de estado (HU6)
CREATE TABLE order_status_history (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  order_id      BIGINT UNSIGNED NOT NULL,
  from_status   ENUM('PENDING','CONFIRMED','DISPATCHED','DELIVERED','CANCELLED') NULL,
  to_status     ENUM('PENDING','CONFIRMED','DISPATCHED','DELIVERED','CANCELLED') NOT NULL,
  changed_by    BIGINT UNSIGNED NULL,
  changed_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_osh_order
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  KEY idx_osh_order (order_id),
  KEY idx_osh_changed_at (changed_at)
) ENGINE=InnoDB;

-- Notificaciones (HU6/HU8 y reportes)
CREATE TABLE notifications (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  type          ENUM('ORDER_STATUS_CHANGED','CART_ABANDONED','RESTOCK_ALERT','WEEKLY_SALES_REPORT','WEEKLY_RESTOCK_REPORT') NOT NULL,
  channel       ENUM('EMAIL','IN_APP') NOT NULL,
  user_id       BIGINT UNSIGNED NULL,
  order_id      BIGINT UNSIGNED NULL,
  cart_id       BIGINT UNSIGNED NULL,
  payload       JSON NULL,
  status        ENUM('PENDING','SENT','FAILED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  scheduled_at  DATETIME NOT NULL,
  sent_at       DATETIME NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY idx_notif_status_sched (status, scheduled_at),
  KEY idx_notif_user (user_id),
  KEY idx_notif_order (order_id),
  KEY idx_notif_cart (cart_id)
) ENGINE=InnoDB;

-- -------------------------
-- 5) Abastecimiento a proveedores (para el plan de expansión)
-- -------------------------

CREATE TABLE suppliers (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(180) NOT NULL,
  email         VARCHAR(180) NULL,
  phone         VARCHAR(40) NULL,
  tax_id        VARCHAR(40) NULL,
  is_active     TINYINT(1) NOT NULL DEFAULT 1,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_suppliers_active (is_active)
) ENGINE=InnoDB;

CREATE TABLE purchase_orders (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  supplier_id   BIGINT UNSIGNED NOT NULL,
  warehouse_id  BIGINT UNSIGNED NOT NULL,
  status        ENUM('DRAFT','SENT','PARTIALLY_RECEIVED','RECEIVED','CANCELLED') NOT NULL DEFAULT 'DRAFT',
  order_date    DATE NOT NULL,
  expected_date DATE NULL,
  received_date DATE NULL,
  total_cost    DECIMAL(12,2) NOT NULL DEFAULT 0,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_po_supplier
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE RESTRICT,
  CONSTRAINT fk_po_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE RESTRICT,
  KEY idx_po_status (status),
  KEY idx_po_warehouse (warehouse_id),
  CHECK (total_cost >= 0)
) ENGINE=InnoDB;

CREATE TABLE purchase_order_items (
  id                 BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  purchase_order_id  BIGINT UNSIGNED NOT NULL,
  product_id         BIGINT UNSIGNED NOT NULL,
  quantity_ordered   INT NOT NULL,
  quantity_received  INT NOT NULL DEFAULT 0,
  unit_cost          DECIMAL(12,2) NOT NULL,
  created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_poi_po
    FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id) ON DELETE CASCADE,
  CONSTRAINT fk_poi_product
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
  KEY idx_poi_po (purchase_order_id),
  CHECK (quantity_ordered > 0),
  CHECK (quantity_received >= 0),
  CHECK (unit_cost >= 0)
) ENGINE=InnoDB;

-- -------------------------
-- 6) Reportes (trazabilidad de ejecuciones semanales)
-- -------------------------
CREATE TABLE report_runs (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  report_type   ENUM('WEEKLY_SALES','WEEKLY_RESTOCK') NOT NULL,
  period_start  DATE NOT NULL,
  period_end    DATE NOT NULL,
  status        ENUM('GENERATED','FAILED') NOT NULL,
  generated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  file_ref      VARCHAR(255) NULL,
  meta          JSON NULL,
  KEY idx_report_type_period (report_type, period_start, period_end)
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;
