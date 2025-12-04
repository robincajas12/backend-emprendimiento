# Requerimientos para JWT en Códigos QR de Entradas a Eventos

## Objetivo
Implementar un sistema de tickets digitales seguro y verificable utilizando JSON Web Tokens (JWT) codificados en Códigos QR. Esto reemplazará el uso actual de UUIDs simples para el `codigoQrUnico`, proporcionando mayor seguridad, integridad de datos y facilidad de verificación en el punto de acceso al evento.

Un JSON Web Token (JWT) es un estándar abierto que define una forma compacta y autocontenida para transmitir información de forma segura entre partes como un objeto JSON.

### Beneficios Clave:
*   **Seguridad:** Un JWT firmado digitalmente garantiza que la información no ha sido alterada.
*   **Verificabilidad:** Permite verificar la autenticidad y validez del token en el punto de acceso sin necesidad de consultar una base de datos de inmediato.
*   **Contenido Rico:** El token puede encapsular información esencial sobre la entrada y el usuario, agilizando el proceso de check-in.

## Información Necesaria en el JWT (Payload)
El JWT contendrá las siguientes afirmaciones (claims), mapeadas directamente desde nuestras entidades JPA:

1.  **`jti` (JWT ID):**
    *   **Propósito:** Identificador único global del token/entrada. Es crucial para el seguimiento y para prevenir el re-escaneo de entradas.
    *   **Datos Fuente:** El campo `id` de la entidad `Entrada` (`Entrada.id`). Este ID debe estar disponible una vez que la `Entrada` ha sido persistida en la base de datos.

2.  **`sub` (Subject):**
    *   **Propósito:** Identifica al propietario o sujeto de la entrada.
    *   **Datos Fuente:** El campo `id` del usuario asistente, accesible a través de la relación: `Entrada.asistente.id`.

3.  **`aud` (Audience):**
    *   **Propósito:** Identifica el receptor del JWT, en este caso, el evento al que la entrada da acceso.
    *   **Datos Fuente:** El campo `id` del evento, accesible a través de la relación: `Entrada.tipoEntrada.evento.id`.

4.  **`exp` (Expiration Time):**
    *   **Propósito:** Define la fecha y hora a partir de la cual el token ya no debe ser aceptado. Es una medida de seguridad fundamental.
    *   **Datos Fuente:** La fecha y hora de finalización del evento: `Entrada.tipoEntrada.evento.fechaFin`.

5.  **Claim Personalizado: `ticketType`:**
    *   **Propósito:** Proporciona una descripción legible del tipo de entrada.
    *   **Datos Fuente:** El campo `nombre` del tipo de entrada: `Entrada.tipoEntrada.nombre`.

6.  **Claim Personalizado: `userName`:**
    *   **Propósito:** Nombre completo del titular de la entrada para facilitar la identificación visual en el check-in.
    *   **Datos Fuente:** El campo `nombreCompleto` (o equivalente) de la entidad `Usuario`, accesible a través de: `Entrada.asistente.nombreCompleto`.

## Requerimientos Adicionales

*   **Clave Secreta (Secret Key):** Será necesaria una clave secreta robusta para firmar los JWTs. Esta clave debe ser generada y gestionada de forma segura, **nunca debe ser hardcodeada ni expuesta en el control de versiones**. Deberá cargarse desde un entorno seguro (ej. variables de entorno, almacén de secretos).

*   **Librería JWT:** Se requerirá el uso de una librería Java que facilite la creación y verificación de JWTs (ej. JJWT).

*   **Integración de Lógica:** La lógica para generar el JWT deberá integrarse en el proceso de creación de entradas. Un punto lógico para esto es dentro del método `crearPedido` en `PedidoService.java`, asegurando que la entidad `Entrada` ya tenga su `id` asignado antes de generar el token.

*   **Generación de QR:** Una vez generado el JWT como una cadena de texto, esta cadena será la entrada para cualquier librería o servicio que genere el Código QR visualmente.

*   **Validación:** Se necesitará un mecanismo en el backend para validar los JWTs (verificar la firma, expiración y `jti`) cuando los Códigos QR sean escaneados en el evento.


