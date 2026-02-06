# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-02-06

### Added
- **Core:**
    - Implementation of DIAN Module 11 algorithm with zero-allocation optimization.
    - Immutable `Nit` Value Object.
    - `NitType` detection (NATURAL, JURIDICA, EXTRANJERO, NUIP).
    - `NitCache` LRU implementation for high performance.
- **Spring Boot Starter:**
    - Auto-configuration for Spring Boot 3.x.
    - `@Nit` validation annotation with `strict` and `sanitize` modes.
    - `NitRestControllerAdvice` for RFC 9457 error handling.
    - Reactive support via `ReactiveNitService`.
    - Micrometer metrics integration (`nit.validation.*`).
- **Documentation:**
    - Comprehensive architecture and API documentation.
    - JMH Benchmarks.

### Changed
- Refactored `DefaultNitService` to support caching and metrics.
- Optimized `DianNitValidator` to use char arrays instead of Strings/Regex.

### Fixed
- Fixed recursion issue in AOP aspect.
- Fixed i18n message loading in Spring Boot starter.
