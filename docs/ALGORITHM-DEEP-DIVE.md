# Algoritmo de Validación NIT (Módulo 11)

El Número de Identificación Tributaria (NIT) en Colombia utiliza una variante del algoritmo **Módulo 11** para el cálculo de su dígito de verificación (DV).

## Especificación Matemática

### 1. Secuencia de Multiplicadores (Pesos)
La DIAN define una secuencia fija de 15 pesos que se aplican de derecha a izquierda sobre la base del NIT:

`3, 7, 13, 17, 19, 23, 29, 37, 41, 43, 47, 53, 59, 67, 71`

### 2. Procedimiento de Cálculo

Dada una base numérica $B$ de longitud $n$:

1.  **Alineación:** Se alinea la base a la derecha.
2.  **Producto:** Cada dígito $d_i$ (empezando desde la derecha) se multiplica por su peso correspondiente $p_i$.
    $S = \sum_{i=1}^{n} (d_i \times p_i)$
3.  **Residuo:** Se calcula el residuo de la suma dividida por 11.
    $R = S \mod 11$
4.  **Dígito Verificador (DV):**
    - Si $R = 0$ o $R = 1$, el $DV = R$.
    - Si $R > 1$, el $DV = 11 - R$.

## Ejemplo Práctico: 800.030.988

**Base:** `800030988`

| Dígito | Peso | Producto |
| :--- | :--- | :--- |
| 8 | 3 | 24 |
| 8 | 7 | 56 |
| 9 | 13 | 117 |
| 0 | 17 | 0 |
| 3 | 19 | 57 |
| 0 | 23 | 0 |
| 0 | 29 | 0 |
| 0 | 37 | 0 |
| 8 | 41 | 328 |

**Suma (S):** $328 + 0 + 0 + 0 + 57 + 0 + 117 + 56 + 24 = 582$

**Residuo (R):** $582 \mod 11 = 10$

**DV:** Como $10 > 1$, $11 - 10 = 1$.

**NIT Completo:** `800.030.988-1`

## Implementación en la Librería

La implementación en la clase `DigitCalculator` utiliza primitivos y evita la conversión a `String` para maximizar el rendimiento:

```java
public static char calculate(char[] digits, int offset, int length) {
    int sum = 0;
    for (int i = 0; i < length; i++) {
        char c = digits[offset + length - 1 - i];
        sum += (c - '0') * MULTIPLIERS[i];
    }
    int mod = sum % 11;
    return (mod <= 1) ? (char)(mod + '0') : (char)((11 - mod) + '0');
}
```
