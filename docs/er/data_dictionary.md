# Data Dictionary (ER v2)

## countries
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del país. |
| iso2 | CHAR(2) | Código ISO2 (único). |
| name | VARCHAR(100) | Nombre del país. |
| created_at | TIMESTAMP | Fecha de creación. |

## warehouses
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de bodega. |
| country_id | BIGINT | País al que pertenece. |
| code | VARCHAR(30) | Código de bodega por país. |
| name | VARCHAR(120) | Nombre de bodega. |
| city | VARCHAR(120) | Ciudad. |
| address | VARCHAR(255) | Dirección. |
| is_active | TINYINT | Flag de activación. |
| is_default | TINYINT | Flag de default por país. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Última actualización. |

## brands
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de marca. |
| name | VARCHAR(120) | Nombre único de marca. |
| created_at | TIMESTAMP | Creación. |

## categories
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de categoría. |
| parent_id | BIGINT | Categoría padre (jerarquía). |
| name | VARCHAR(120) | Nombre. |
| slug | VARCHAR(160) | Slug único. |
| is_active | TINYINT | Flag de activación. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## products
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de producto. |
| sku | VARCHAR(64) | SKU único. |
| name | VARCHAR(180) | Nombre comercial. |
| description | TEXT | Descripción extendida. |
| price | DECIMAL(12,2) | Precio unitario. |
| brand_id | BIGINT | Marca asociada. |
| is_active | TINYINT | Flag de activación. |
| min_stock | INT | Stock mínimo sugerido. |
| reorder_point | INT | Punto de reorden. |
| lead_time_days | INT | Días de entrega esperada. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## product_categories
| Campo | Tipo | Descripción |
| --- | --- | --- |
| product_id | BIGINT | Producto. |
| category_id | BIGINT | Categoría. |

## attributes
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de atributo. |
| name | VARCHAR(120) | Nombre de atributo. |
| data_type | ENUM | Tipo de dato (STRING/NUMBER/BOOLEAN). |
| is_filterable | TINYINT | Flag de filtro. |
| created_at | TIMESTAMP | Creación. |

## product_attribute_values
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del valor. |
| product_id | BIGINT | Producto. |
| attribute_id | BIGINT | Atributo. |
| value_string | VARCHAR(255) | Valor texto. |
| value_number | DECIMAL(18,6) | Valor numérico. |
| value_boolean | TINYINT | Valor booleano. |
| created_at | TIMESTAMP | Creación. |

## inventory_balances
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del saldo. |
| product_id | BIGINT | Producto. |
| warehouse_id | BIGINT | Bodega. |
| on_hand | INT | Disponible físico. |
| reserved | INT | Reservado. |
| updated_at | TIMESTAMP | Última actualización. |

## reorder_settings
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del umbral. |
| product_id | BIGINT | Producto. |
| warehouse_id | BIGINT | Bodega. |
| threshold | INT | Umbral para abastecimiento. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## inventory_movements
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del movimiento. |
| product_id | BIGINT | Producto. |
| warehouse_id | BIGINT | Bodega. |
| movement_type | ENUM | Tipo de movimiento. |
| quantity | INT | Cantidad positiva. |
| ref_type | ENUM | Tipo de referencia (order/cart/etc). |
| ref_id | BIGINT | Identificador de referencia. |
| note | VARCHAR(255) | Nota opcional. |
| created_at | TIMESTAMP | Creación. |

## stock_reservations
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de reserva. |
| product_id | BIGINT | Producto. |
| warehouse_id | BIGINT | Bodega. |
| order_id | BIGINT | Orden relacionada. |
| cart_id | BIGINT | Carrito relacionado. |
| quantity | INT | Cantidad reservada. |
| status | ENUM | Estado (ACTIVE/RELEASED/CONSUMED/EXPIRED). |
| expires_at | DATETIME | Expiración. |
| created_at | TIMESTAMP | Creación. |

## carts
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de carrito. |
| user_id | BIGINT | Usuario. |
| status | ENUM | Estado (ACTIVE/CHECKED_OUT/ABANDONED). |
| last_activity_at | DATETIME | Última actividad. |
| abandoned_at | DATETIME | Marca abandono. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## cart_items
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del ítem. |
| cart_id | BIGINT | Carrito. |
| product_id | BIGINT | Producto. |
| warehouse_id | BIGINT | Bodega. |
| quantity | INT | Cantidad. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## orders
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de orden. |
| order_number | VARCHAR(40) | Número de orden único. |
| user_id | BIGINT | Usuario. |
| warehouse_id | BIGINT | Bodega. |
| status | ENUM | Estado de orden. |
| payment_status | ENUM | Estado de pago. |
| currency | CHAR(3) | Moneda. |
| subtotal | DECIMAL(12,2) | Subtotal. |
| shipping | DECIMAL(12,2) | Costo de envío. |
| total | DECIMAL(12,2) | Total. |
| shipping_name | VARCHAR(160) | Destinatario. |
| shipping_phone | VARCHAR(40) | Teléfono. |
| shipping_address | VARCHAR(255) | Dirección. |
| notes | VARCHAR(255) | Notas. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## order_items
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del ítem de orden. |
| order_id | BIGINT | Orden. |
| product_id | BIGINT | Producto. |
| quantity | INT | Cantidad. |
| unit_price | DECIMAL(12,2) | Precio unitario. |
| line_total | DECIMAL(12,2) | Total de línea. |
| created_at | TIMESTAMP | Creación. |

## order_status_history
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del historial. |
| order_id | BIGINT | Orden. |
| from_status | ENUM | Estado previo. |
| to_status | ENUM | Estado nuevo. |
| changed_by | BIGINT | Usuario que cambió. |
| changed_at | TIMESTAMP | Fecha del cambio. |

## notifications
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de notificación. |
| type | ENUM | Tipo de notificación. |
| channel | ENUM | Canal. |
| user_id | BIGINT | Usuario destinatario. |
| order_id | BIGINT | Orden asociada. |
| cart_id | BIGINT | Carrito asociado. |
| payload | JSON | Payload flexible. |
| status | ENUM | Estado envío. |
| scheduled_at | DATETIME | Programada. |
| sent_at | DATETIME | Enviada. |
| created_at | TIMESTAMP | Creación. |

## suppliers
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del proveedor. |
| name | VARCHAR(180) | Nombre. |
| email | VARCHAR(180) | Email. |
| phone | VARCHAR(40) | Teléfono. |
| tax_id | VARCHAR(40) | Identificación tributaria. |
| is_active | TINYINT | Activo. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## purchase_orders
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador de orden compra. |
| supplier_id | BIGINT | Proveedor. |
| warehouse_id | BIGINT | Bodega. |
| status | ENUM | Estado de orden compra. |
| order_date | DATE | Fecha de orden. |
| expected_date | DATE | Fecha esperada. |
| received_date | DATE | Fecha recibida. |
| total_cost | DECIMAL(12,2) | Costo total. |
| created_at | TIMESTAMP | Creación. |
| updated_at | TIMESTAMP | Actualización. |

## purchase_order_items
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del ítem. |
| purchase_order_id | BIGINT | Orden compra. |
| product_id | BIGINT | Producto. |
| quantity_ordered | INT | Cantidad ordenada. |
| quantity_received | INT | Cantidad recibida. |
| unit_cost | DECIMAL(12,2) | Costo unitario. |
| created_at | TIMESTAMP | Creación. |

## report_runs
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | BIGINT | Identificador del reporte. |
| report_type | ENUM | Tipo (WEEKLY_SALES/WEEKLY_RESTOCK). |
| period_start | DATE | Inicio del periodo. |
| period_end | DATE | Fin del periodo. |
| status | ENUM | Estado (GENERATED/FAILED). |
| generated_at | TIMESTAMP | Generado. |
| file_ref | VARCHAR(255) | Referencia a archivo. |
| meta | JSON | Metadata adicional. |
