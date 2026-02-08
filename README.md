# Librería Java para Validación de NIT (Colombia)

[![Java Version](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)](https://docs.oracle.com/en/java/javase/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![Coverage](https://img.shields.io/badge/coverage-95%25-brightgreen)]()

> Librería profesional de alto rendimiento para validación y procesamiento de Números de Identificación Tributaria (NIT) colombianos, optimizada para entornos empresariales y procesamiento masivo.

## 🚀 Descripción Corta
Esta librería proporciona una solución robusta, segura y eficiente para validar NITs colombianos en aplicaciones Java. Implementa el algoritmo oficial **Módulo 11 de la DIAN**, soporta validaciones masivas (batch) con procesamiento paralelo y se integra nativamente con **Spring Boot** y **Jakarta Validation**.

## 💡 El Problema Real
En el ecosistema de desarrollo colombiano, la validación de NITs suele implementarse mediante fragmentos de código copiados de internet ("copy-paste"), plagados de malas prácticas como el uso ineficiente de Regex, falta de pruebas unitarias y validaciones incompletas de los casos borde (residuos 0 y 1).

Esto resulta en:
*   ❌ **Deuda Técnica:** Lógica duplicada en múltiples microservicios.
*   ❌ **Falsos Positivos/Negativos:** Errores en facturación electrónica y reportes DIAN.
*   ❌ **Bajo Rendimiento:** Validaciones lentas en procesos de carga masiva (ETLs).

**Esta librería centraliza esa lógica crítica en un componente reutilizable, inmutable, thread-safe y probado exhaustivamente.**

## ✨ Características Principales
*   ✅ **Algoritmo DIAN Optimizado:** Implementación "Zero-Allocation" del Módulo 11 (latencia < 100ns).
*   ✅ **Validación Masiva (Batch):** Procesamiento paralelo automático para listas grandes.
*   ✅ **Spring Boot Starter:** Auto-configuración y anotación `@Nit` para DTOs.
*   ✅ **Caché Inteligente:** Cache LRU integrado para reducir la carga de CPU en NITs recurrentes.
*   ✅ **Observabilidad:** Métricas listas para Prometheus/Micrometer (`nit.validation.*`).
*   ✅ **Internacionalización (i18n):** Mensajes de error en Español e Inglés.

## 📦 Instalación

Añade la dependencia a tu `pom.xml`:

```xml
<dependency>
    <groupId>io.github.rouchnull.nit</groupId>
    <artifactId>nit-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

*(Nota: Actualmente requiere instalación local con `mvn install` o configuración de repositorio privado)*

## 💻 Ejemplo de Uso

### 1. Spring Boot (Recomendado)
Usa la anotación `@Nit` en tus DTOs para validación declarativa y sanitización automática:

```java
public class ProveedorDTO {
    
    @NotBlank
    private String razonSocial;

    @Nit(message = "El NIT es inválido", sanitize = true)
    private String nit; // Acepta "800.123.456-7" y lo limpia automáticamente
}
```

### 2. Uso Programático (Java Puro)
Ideal para lógica de negocio o procesos batch:

```java
// Inyección o Factory
NitService service = NitServiceFactory.defaultService();

// Validación simple
boolean isValid = service.isValid("900123456-1");

// Parsing seguro (Value Object inmutable)
try {
    Nit nit = service.parse("900.123.456-1");
    System.out.println(nit.getType()); // JURIDICA
    System.out.println(nit.getBaseNumber()); // "900123456"
} catch (NitValidationException e) {
    // Manejo de error específico
}
```

### 3. Validación Masiva (Batch)
```java
List<String> nits = List.of("900123456", "800987654-1", "invalid");
BatchValidationResult result = batchValidator.validate(nits);

System.out.println("Válidos: " + result.getValidCount());
System.out.println("Inválidos: " + result.getInvalidCount());
```

## 🏗️ Arquitectura y Diseño
La librería sigue principios de **Clean Architecture** y **DDD**:

*   **Core Puro:** El módulo `nit-core` no tiene dependencias de frameworks (ni Spring), garantizando portabilidad.
*   **Inmutabilidad:** La clase `Nit` es un Value Object inmutable, garantizando seguridad en entornos concurrentes.
*   **Strategy Pattern:** La validación está desacoplada de la implementación, permitiendo cambiar reglas a futuro sin romper contratos.
*   **Extensibilidad:** Diseñada para ser extendida mediante Decoradores (e.g., para añadir validación contra RUT en el futuro).

## 🧪 Calidad y Testing
*   **Cobertura:** >95% de cobertura de código (JaCoCo).
*   **Análisis Estático:** Validada con **Checkstyle**, **PMD** y **Spotbugs**.
*   **Benchmarks:** Pruebas de rendimiento con **JMH** para garantizar latencias mínimas.
*   **Data-Driven Tests:** Validada contra una base de datos de NITs reales y casos borde oficiales.

## ⚠️ Limitaciones Actuales
*   La validación es puramente algorítmica (formato y dígito de verificación). No consulta la base de datos del RUT/DIAN para verificar existencia real.
*   Diseñada principalmente para aplicaciones Backend (Java 17+).

## 🗺️ Roadmap
*   🔜 Publicación en Maven Central.
*   🔜 Módulo de integración opcional con API DIAN (consulta de estado).
*   🔜 Soporte nativo para Kotlin.
*   🔜 Validaciones extendidas para Cédulas de Extranjería.

## 📄 Licencia
Distribuido bajo la licencia **MIT**. Ver `LICENSE` para más detalles.

## 👨‍💻 Autor
**Josue M. Sinisterra** (rouchNull)
*Ingeniero de Software & Arquitecto Java*
