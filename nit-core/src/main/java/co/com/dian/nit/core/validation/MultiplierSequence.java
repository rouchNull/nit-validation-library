package co.com.dian.nit.core.validation;

/**
 * Secuencia oficial DIAN para cálculo del dígito de verificación.
 *
 * Orden aplicado de derecha a izquierda.
 */
public final class MultiplierSequence {

    private static final int[] SEQUENCE = {
            3, 7, 13, 17, 19, 23, 29, 37, 41, 43, 47, 53, 59, 67, 71
    };

    private MultiplierSequence() {
    }

    public static int[] get() {
        return SEQUENCE;
    }

    public static int getForPosition(int indexFromRight) {

        if (indexFromRight < 0 || indexFromRight >= SEQUENCE.length) {
            throw new IllegalArgumentException("Index fuera de rango: " + indexFromRight);
        }

        return SEQUENCE[indexFromRight];
    }

}
