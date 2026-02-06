package co.com.dian.nit.core.validation;

/**
 * Motor matemático para cálculo del dígito de verificación DIAN.
 */
public final class DigitCalculator {

    private static final int[] MULTIPLIERS = {
        3, 7, 13, 17, 19, 23, 29, 37, 41, 43, 47, 53, 59, 67, 71
    };

    private DigitCalculator() {
    }

    /**
     * Calcula el dígito de verificación oficial DIAN.
     * Optimizado para zero-allocation y alto rendimiento.
     *
     * @param baseNumber Número base sin dígito
     * @return dígito calculado
     */
    public static char calculate(String baseNumber) {
        if (baseNumber == null) {
            return '0';
        }
        
        int sum = 0;
        int len = baseNumber.length();
        
        for (int i = 0; i < len; i++) {
            char c = baseNumber.charAt(len - 1 - i);
            if (c >= '0' && c <= '9') {
                sum += (c - '0') * MULTIPLIERS[i];
            }
        }

        int mod = sum % 11;

        if (mod <= 1) {
            return (char) (mod + '0');
        }
        
        return (char) ((11 - mod) + '0');
    }

    /**
     * Calcula el dígito de verificación oficial DIAN desde un array de caracteres.
     * 
     * @param digits array de caracteres
     * @param offset inicio
     * @param length longitud
     * @return dígito calculado
     */
    public static char calculate(char[] digits, int offset, int length) {
        if (digits == null || length <= 0) {
            return '0';
        }
        
        int sum = 0;
        for (int i = 0; i < length; i++) {
            char c = digits[offset + length - 1 - i];
            sum += (c - '0') * MULTIPLIERS[i];
        }

        int mod = sum % 11;
        if (mod <= 1) {
            return (char) (mod + '0');
        }
        return (char) ((11 - mod) + '0');
    }

}
