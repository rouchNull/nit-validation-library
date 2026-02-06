# Guía de Contribución

¡Gracias por tu interés en contribuir a la librería NIT!

## Estándares de Desarrollo

Para garantizar la calidad de la librería, seguimos reglas estrictas:

1.  **Java 17+:** Se debe utilizar Java 17 o superior.
2.  **Inmutabilidad:** Todos los objetos de dominio deben ser inmutables.
3.  **Rendimiento:** Evitar el uso de Regex en el "hot path". Preferir operaciones sobre primitivos.
4.  **Calidad:** Cobertura de pruebas > 95%. No se aceptan PRs con fallos en Checkstyle, PMD o Spotbugs.

## Proceso de Desarrollo

1.  Haz un fork del repositorio.
2.  Crea una rama para tu funcionalidad (`feat/nueva-funcionalidad`) o corrección (`fix/bug-detectado`).
3.  Implementa tus cambios.
4.  Asegúrate de que todos los tests pasen: `mvn clean install -Pci`.
5.  Actualiza la documentación si es necesario.
6.  Envía un Pull Request.

## Estándares de Código

- Usamos **Checkstyle** para el estilo de código.
- Usamos **PMD** para detectar malas prácticas.
- Usamos **Spotbugs** para encontrar bugs potenciales.
- Usamos **JaCoCo** para medir la cobertura.

Puedes validar todo localmente con el perfil `ci`:
```bash
mvn clean install -Pci
```
