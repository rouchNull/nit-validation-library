package co.com.dian.nit.core.validation;

/**
 * Motor matemático para cálculo del dígito de verificación DIAN.
 */
public final class DigitCalculator {

    private DigitCalculator() {
    }

    /**
     * Calcula el dígito de verificación oficial DIAN.
     *
     * @param baseNumber Número base sin dígito
     * @return dígito calculado
     */
    public static char calculate(String baseNumber) {

        int sum = 0;
        int multiplierIndex = 0;

        for (int i = baseNumber.length() - 1; i >= 0; i--) {

            int digit = Character.getNumericValue(baseNumber.charAt(i));
            int multiplier = MultiplierSequence.getForPosition(multiplierIndex);

            sum += digit * multiplier;
            multiplierIndex++;
        }

        int mod = sum % 11;

        int result = (mod > 1) ? (11 - mod) : mod;

        return Character.forDigit(result, 10);
    }

}
