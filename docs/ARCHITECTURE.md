# Arquitectura de la Librería NIT

Este documento describe las decisiones de diseño y la estructura técnica de la librería de validación de NIT colombiano.

## Visión General

La librería está diseñada siguiendo principios de **Clean Architecture** y **Domain-Driven Design (DDD)**, separando la lógica pura del algoritmo de las integraciones con frameworks.

### Módulos

1.  **nit-core:** Contiene el modelo de dominio, el algoritmo de validación (Módulo 11) y servicios base. No tiene dependencias externas para garantizar portabilidad.
2.  **nit-spring-boot-starter:** Proporciona auto-configuración, integración con Bean Validation, manejo de errores REST y soporte reactivo para aplicaciones Spring Boot.
3.  **nit-spring-boot-sample:** Aplicación de ejemplo que demuestra el uso práctico.

## Decisiones Clave de Diseño

### 1. Inmutabilidad (Value Objects)
La clase `Nit` es un **Value Object inmutable**. Una vez creado, su estado no puede cambiar. Esto garantiza seguridad en entornos multihilo y facilita el uso de cachés.

### 2. Rendimiento (Zero-Allocation Path)
El algoritmo de validación ha sido optimizado para minimizar la creación de objetos en el "hot path". Se evita el uso de Expresiones Regulares (Regex) y se prefieren operaciones manuales sobre arrays de caracteres para validaciones de alto rendimiento (< 100ns).

### 3. Caché LRU
Se incluye un `NitCache` configurable para almacenar instancias de NITs validados frecuentemente, reduciendo la latencia en procesos masivos y el impacto en el Garbage Collector.

### 4. Manejo de Errores (Problem Details)
Para las APIs REST, se implementa el estándar **RFC 9457**, proporcionando respuestas de error estructuradas y legibles por máquinas.

## Flujo de Validación

1.  **Sanitización:** Se eliminan caracteres no numéricos.
2.  **Cálculo:** Se aplica el algoritmo Módulo 11 sobre la base.
3.  **Detección de Tipo:** Se verifica el rango numérico según la normativa DIAN.
4.  **Resultado:** Se retorna un `NitValidationResult` o se lanza una `NitValidationException`.

## Diagrama de Capas

```text
[ Aplicación Cliente ]
       |
[ Spring Boot Starter ] (@Nit, NitService)
       |
[ Nit Core Library ] (Algorithm, Domain)
```
