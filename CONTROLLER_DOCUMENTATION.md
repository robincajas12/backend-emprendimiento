# Controller Documentation

A continuación se detallan los endpoints de la API, con descripciones mejoradas y las estructuras de datos necesarias tanto para las solicitudes como para las respuestas.

---

## EventoController
**Base Path:** `/api/eventos`

Controla todas las operaciones relacionadas con los eventos, desde su creación hasta su consulta y eliminación.

### Endpoints

#### 1. Crear Evento
- **HTTP Method:** `POST`
- **Path:** `/`
- **Description:** Registra un nuevo evento en el sistema. Requiere el ID de un usuario existente como organizador. El evento se crea con el estado `PUBLICADO` por defecto. Devuelve el objeto completo del evento recién creado, incluyendo su ID asignado por la base de datos.
- **Request Body:** `EventoDTO`
  <details>
  <summary>Ejemplo de JSON de Solicitud</summary>
  
  ```json
  {
    "organizadorId": 1,
    "nombre": "Concierto Acústico de Verano",
    "descripcion": "Una noche mágica con artistas locales e invitados especiales.",
    "fechaInicio": "2025-07-20T20:00:00.000+00:00",
    "fechaFin": "2025-07-20T23:30:00.000+00:00",
    "capacidadAforo": 150,
    "categoria": "MUSICA",
    "ubicacion": {
      "nombreLugar": "Teatro del Sol",
      "direccion": "Avenida Siempreviva 742",
      "ciudad": "Capital",
      "latitud": 40.7128,
      "longitud": -74.0060
    },
    "tiposDeEntrada": [
      {
        "nombre": "Entrada General",
        "precio": 75.50,
        "cantidadTotal": 100
      }
    ],
    "permisos": [
      {
        "tipoPermiso": "MUNICIPAL",
        "estado": "APROBADO"
      }
    ]
  }
  ```
  </details>
- **Response:** `Evento`
  <details>
  <summary>Ejemplo de JSON de Respuesta</summary>
  
  ```json
  {
    "id": 1,
    "organizador": { /* Objeto Usuario del organizador */ },
    "nombre": "Concierto Acústico de Verano",
    "descripcion": "Una noche mágica con artistas locales e invitados especiales.",
    "fechaInicio": "2025-07-20T20:00:00.000+00:00",
    "fechaFin": "2025-07-20T23:30:00.000+00:00",
    "capacidadAforo": 150,
    "categoria": "MUSICA",
    "estado": "PUBLICADO",
    "ubicacion": {
      "id": 1,
      "nombreLugar": "Teatro del Sol",
      "direccion": "Avenida Siempreviva 742",
      "ciudad": "Capital",
      "latitud": 40.7128,
      "longitud": -74.0060
    },
    "tiposDeEntrada": [
      {
        "id": 1,
        "nombre": "Entrada General",
        "precio": 75.50,
        "cantidadTotal": 100,
        "cantidadVendida": 0
      }
    ],
    "permisos": [
      {
        "id": 1,
        "tipoPermiso": "MUNICIPAL",
        "estado": "APROBADO",
        "urlDocumento": null,
        "notas": null
      }
    ]
  }
  ```
  </details>

#### 2. Obtener Todos los Eventos
- **HTTP Method:** `GET`
- **Path:** `/`
- **Description:** Devuelve una lista con todos los eventos registrados en el sistema.
- **Response:** `List<Evento>`
  <details>
  <summary>Ejemplo de JSON de Respuesta</summary>
  
  ```json
  [
    {
      "id": 1,
      "organizador": { /* ... */ },
      "nombre": "Concierto Acústico de Verano",
      "descripcion": "...",
      "fechaInicio": "2025-07-20T20:00:00.000+00:00",
      "fechaFin": "2025-07-20T23:30:00.000+00:00",
      "capacidadAforo": 150,
      "categoria": "MUSICA",
      "estado": "PUBLICADO",
      "ubicacion": { /* ... */ },
      "tiposDeEntrada": [ /* ... */ ],
      "permisos": [ /* ... */ ]
    }
  ]
  ```
  </details>

#### 3. Obtener Evento por ID
- **HTTP Method:** `GET`
- **Path:** `/{id}`
- **Description:** Busca y devuelve un único evento basado en su ID. Si no se encuentra, devuelve 404 Not Found.
- **Path Variable:** `id` (Long)
- **Response:** `Evento` (La estructura es la misma que en la creación de un evento).

#### 4. Eliminar Evento
- **HTTP Method:** `DELETE`
- **Path:** `/{id}`
- **Description:** Elimina un evento del sistema utilizando su ID.
- **Path Variable:** `id` (Long)
- **Response:** `204 No Content`.

---

## PedidoController
**Base Path:** `/api/pedidos`

Gestiona la creación y consulta de pedidos de entradas.

### Endpoints

#### 1. Crear Pedido
- **HTTP Method:** `POST`
- **Path:** `/`
- **Description:** Genera un nuevo pedido para un asistente, asociándolo con tipos de entrada específicos.
- **Request Body:** `PedidoDTO`
  <details>
  <summary>Ejemplo de JSON de Solicitud</summary>
  
  ```json
  {
    "asistenteId": 2,
    "tipoEntradaIds": [1, 1] 
  }
  ```
  </details>
- **Response:** `Pedido`
  <details>
  <summary>Ejemplo de JSON de Respuesta</summary>
  
  ```json
  {
      "id": 1,
      "asistente": {
          "id": 2,
          "nombreCompleto": "Juan Pérez",
          "email": "juan.perez@example.com",
          "telefono": "555-1234",
          "fechaNacimiento": "1995-08-22T00:00:00.000+00:00",
          "rol": "ASISTENTE",
          "fechaCreacion": "2025-12-04T12:00:00.000+00:00"
      },
      "fechaPedido": "2025-12-04T13:00:00.000+00:00",
      "estado": "PAGADO",
      "total": 151.00,
      "entradas": [
          {
              "id": 1,
              "codigoQr": "...",
              "fechaUso": null,
              "estado": "NO_USADA"
          },
          {
              "id": 2,
              "codigoQr": "...",
              "fechaUso": null,
              "estado": "NO_USADA"
          }
      ]
  }
  ```
  </details>

#### 2. Obtener Pedido por ID
- **HTTP Method:** `GET`
- **Path:** `/{id}`
- **Description:** Recupera los detalles de un pedido específico.
- **Path Variable:** `id` (Long)
- **Response:** `Pedido` (La estructura es la misma que en la creación de un pedido).

---

## UsuarioController
**Base Path:** `/api/usuarios`

Administra los usuarios del sistema.

### Endpoints

#### 1. Crear Usuario
- **HTTP Method:** `POST`
- **Path:** `/`
- **Description:** Registra un nuevo usuario en la plataforma.
- **Request Body:** `Usuario`
  <details>
  <summary>Ejemplo de JSON de Solicitud</summary>
  
  ```json
  {
    "nombreCompleto": "Juan Pérez",
    "email": "juan.perez@example.com",
    "passwordHash": "contraseña_muy_segura_hasheada",
    "telefono": "555-1234",
    "fechaNacimiento": "1995-08-22T00:00:00.000+00:00",
    "rol": "ASISTENTE"
  }
  ```
  **Nota:** El `rol` puede ser `ASISTENTE` u `ORGANIZADOR`.
  </details>
- **Response:** `Usuario`
  <details>
  <summary>Ejemplo de JSON de Respuesta</summary>
  
  ```json
  {
    "id": 2,
    "nombreCompleto": "Juan Pérez",
    "email": "juan.perez@example.com",
    "passwordHash": "contraseña_muy_segura_hasheada", // NOTA: En una app real, este campo no debería exponerse.
    "telefono": "555-1234",
    "fechaNacimiento": "1995-08-22T00:00:00.000+00:00",
    "rol": "ASISTENTE",
    "fechaCreacion": "2025-12-04T12:00:00.000+00:00"
  }
  ```
  </details>

#### 2. Obtener Todos los Usuarios
- **HTTP Method:** `GET`
- **Path:** `/`
- **Description:** Devuelve una lista de todos los usuarios registrados.
- **Response:** `List<Usuario>`
  <details>
  <summary>Ejemplo de JSON de Respuesta</summary>
  
  ```json
  [
      {
          "id": 1,
          "nombreCompleto": "Admin",
          /* ... otros campos ... */
      },
      {
          "id": 2,
          "nombreCompleto": "Juan Pérez",
          /* ... otros campos ... */
      }
  ]
  ```
  </details>

#### 3. Obtener Usuario por ID
- **HTTP Method:** `GET`
- **Path:** `/{id}`
- **Description:** Busca un usuario por su ID.
- **Path Variable:** `id` (Long)
- **Response:** `Usuario` (La estructura es la misma que en la creación).

#### 4. Actualizar Usuario
- **HTTP Method:** `PUT`
- **Path:** `/{id}`
- **Description:** Actualiza los datos de un usuario existente.
- **Path Variable:** `id` (Long)
- **Request Body:** `Usuario` (la estructura es la misma que para la creación).
- **Response:** `Usuario` (con los datos actualizados).

#### 5. Eliminar Usuario
- **HTTP Method:** `DELETE`
- **Path:** `/{id}`
- **Description:** Elimina un usuario por su ID.
- **Path Variable:** `id` (Long)
- **Response:** `204 No Content`.

---

## ValidacionQrController
**Base Path:** `/api/validacion`

Endpoint para la validación de entradas mediante QR.

### Endpoints

#### 1. Validar QR de Entrada
- **HTTP Method:** `POST`
- **Path:** `/escanear`
- **Description:** Verifica la validez de un token JWT. Si es válido, devuelve los datos de la entrada. Si es inválido, devuelve un error 401.
- **Request Body:**
  <details>
  <summary>Ejemplo de JSON de Solicitud</summary>
  
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ..."
  }
  ```
  </details>
- **Response:**
  - **Éxito (200 OK):**
    ```json
    {
      "esValido": true,
      "mensaje": "Acceso Autorizado",
      "datos": {
        "asistente": "nombre_del_usuario",
        "tipoEntrada": "General",
        "eventoId": "1",
        "entradaId": "123"
      }
    }
    ```
  - **Error (401 Unauthorized):**
    ```json
    {
      "esValido": false,
      "mensaje": "Token inválido, expirado o manipulado."
    }
    ```
---
## HelloController
**Base Path:** `/`

Controlador de prueba para verificar que la aplicación está funcionando.

### Endpoints

#### 1. Hello World
- **HTTP Method:** `GET`
- **Path:** `/hello`
- **Description:** Endpoint de diagnóstico que simplemente devuelve un saludo.
- **Response:** `String` ("Hello, World!")
