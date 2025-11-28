# Scripts SQL para la Creación de la Base de Datos (PostgreSQL)

Este documento contiene los scripts SQL `CREATE TABLE` para configurar la base de datos del proyecto, basados en las entidades JPA definidas.

---

### Tabla: `usuarios`

```sql
CREATE TYPE rol_usuario AS ENUM ('ASISTENTE', 'ORGANIZADOR');

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    rol rol_usuario NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

---

### Tabla: `ubicaciones`

```sql
CREATE TABLE ubicaciones (
    id BIGSERIAL PRIMARY KEY,
    nombre_lugar VARCHAR(255),
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    latitud DECIMAL(10, 8) NOT NULL,
    longitud DECIMAL(11, 8) NOT NULL
);
```

---

### Tabla: `eventos`

```sql
CREATE TYPE estado_evento AS ENUM ('BORRADOR', 'PUBLICADO', 'ACTIVO', 'FINALIZADO', 'CANCELADO');

CREATE TABLE eventos (
    id BIGSERIAL PRIMARY KEY,
    organizador_id BIGINT NOT NULL REFERENCES usuarios(id),
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_fin TIMESTAMP NOT NULL,
    ubicacion_id BIGINT REFERENCES ubicaciones(id),
    capacidad_aforo INT,
    categoria VARCHAR(100),
    estado estado_evento NOT NULL,
    imagen_url VARCHAR(255),
    es_destacado BOOLEAN NOT NULL DEFAULT FALSE
);
```

---

### Tabla: `tipos_entrada`

```sql
CREATE TABLE tipos_entrada (
    id BIGSERIAL PRIMARY KEY,
    evento_id BIGINT NOT NULL REFERENCES eventos(id),
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    cantidad_total INT NOT NULL,
    cantidad_vendida INT NOT NULL DEFAULT 0
);
```

---

### Tabla: `pedidos`

```sql
CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    asistente_id BIGINT NOT NULL REFERENCES usuarios(id),
    monto_total DECIMAL(10, 2) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

---

### Tabla: `entradas`

```sql
CREATE TYPE estado_entrada AS ENUM ('VALIDA', 'UTILIZADA', 'REEMBOLSADA');

CREATE TABLE entradas (
    id BIGSERIAL PRIMARY KEY,
    tipo_entrada_id BIGINT NOT NULL REFERENCES tipos_entrada(id),
    asistente_id BIGINT NOT NULL REFERENCES usuarios(id),
    pedido_id BIGINT NOT NULL REFERENCES pedidos(id),
    codigo_qr_unico VARCHAR(255) NOT NULL UNIQUE,
    estado estado_entrada NOT NULL,
    fecha_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

---

### Tabla: `transacciones`

```sql
CREATE TYPE estado_transaccion AS ENUM ('PENDIENTE', 'EXITOSA', 'FALLIDA', 'REEMBOLSADA');

CREATE TABLE transacciones (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedidos(id),
    gateway_id_externo VARCHAR(255),
    monto DECIMAL(10, 2) NOT NULL,
    estado estado_transaccion NOT NULL,
    fecha_procesamiento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

---

### Tabla: `permisos`

```sql
CREATE TYPE tipo_permiso AS ENUM ('MUNICIPAL', 'BOMBEROS', 'SEGURIDAD_PRIVADA');
CREATE TYPE estado_permiso AS ENUM ('NO_REQUERIDO', 'PENDIENTE', 'SOLICITADO', 'APROBADO', 'RECHAZADO');

CREATE TABLE permisos (
    id BIGSERIAL PRIMARY KEY,
    evento_id BIGINT NOT NULL REFERENCES eventos(id),
    tipo_permiso tipo_permiso NOT NULL,
    estado estado_permiso NOT NULL,
    url_documento VARCHAR(255),
    notas TEXT
);
```
