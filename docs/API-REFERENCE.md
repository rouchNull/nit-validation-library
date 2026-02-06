# Referencia de API - Librería NIT

## Módulo Core

### `NitService` (Interface)
Servicio principal para la gestión de NITs.
- `validate(String nit)`: Valida un NIT y retorna el resultado detallado.
- `isValid(String nit)`: Validación booleana rápida.
- `parse(String nit)`: Crea un objeto `Nit` si es válido, lanza excepción si no.
- `detectType(String baseNumber)`: Identifica si es Persona Natural, Jurídica, etc.

### `Nit` (Value Object)
Representación inmutable del NIT.
- `getBaseNumber()`: Retorna el número base.
- `getDigit()`: Retorna el dígito de verificación.
- `getType()`: Retorna el `NitType`.
- `getFormatted()`: Retorna el NIT formateado (Ej: `800030988-1`).

### `NitType` (Enum)
- `NATURAL`: Personas naturales (Cédulas).
- `JURIDICA`: Empresas.
- `EXTRANJERO`: NITs de extranjería.
- `NUIP`: Número Único de Identificación Personal.

## Módulo Spring Boot Starter

### Anotación `@Nit`
Validador de Bean Validation para campos o parámetros.
- `message`: Mensaje de error personalizado.
- `strict`: Si es `true`, rechaza formatos con puntos o espacios.
- `sanitize`: Si es `true`, limpia el input antes de validar.
- `allowedTypes`: Lista de tipos permitidos (Ej: `NitType.JURIDICA`).

### Manejo de Errores
La librería intercepta automáticamente `NitValidationException` y retorna un `ProblemDetail` (RFC 9457) con estado HTTP 400.

### Métricas
Se exponen automáticamente métricas en Prometheus/Micrometer bajo el prefijo `nit.validation.*`.
- `nit.validation.total`: Contador de validaciones.
- `nit.validation.duration`: Tiempo de respuesta.
