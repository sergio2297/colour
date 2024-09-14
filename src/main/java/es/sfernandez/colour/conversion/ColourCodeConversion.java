package es.sfernandez.colour.conversion;

import es.sfernandez.colour.codifications.ColourCode;

import java.util.function.Function;

/**
 * <p>A {@link ColourCodeConversion} is a {@link Function} that converts a ColourCode in one codification IN to its
 * corresponding codification in another ColourCode OUT.</p>
 *
 * @param <IN> ColourCode to convert
 * @param <OUT> ColourCode to get after conversion is applied
 *
 * @see ColourCode
 *
 * @author Sergio Fernández
 */
public interface ColourCodeConversion<IN extends ColourCode, OUT extends ColourCode>
        extends Function<IN, OUT> {

    /**
     * @return class of the {@link ColourCode} to convert
     */
    Class<IN> inColourCodeClass();

    /**
     * @return class of the {@link ColourCode} that would be obtained after conversion
     */
    Class<OUT> outColourCodeClass();

    /**
     * <p>Converts codification from {@link ColourCode} IN to type OUT.</p>
     * @param codification {@link ColourCode} to convert
     * @return the result of convert codification to type OUT.
     */
    OUT convert(IN codification);

    @Override
    default OUT apply(IN in) {
        return convert(in);
    }

    /**
     * <p>Generates a new {@link ColourCodeConversion} from concatenating this conversion and the given as argument.</p>
     * @param after conversion to concatenate after this
     * @return a new {@link ColourCodeConversion} created from the composition of applying first this conversion and then
     * the given as argument
     * @param <V> ColourCode
     */
    default <V extends ColourCode> ColourCodeConversion<IN, V> andThen(ColourCodeConversion<? super OUT, ? extends V> after) {
        Class<IN> inClass = inColourCodeClass();
        Class<? extends V> outClass = after.outColourCodeClass();
        ColourCodeConversion<IN, OUT> firstStep = this;

        return new ColourCodeConversion<>() {
            @Override
            public Class<IN> inColourCodeClass() {
                return inClass;
            }

            @Override
            public Class<V> outColourCodeClass() {
                return (Class<V>) outClass;
            }

            @Override
            public V convert(IN codification) {
                return after.convert(firstStep.convert(codification));
            }
        };
    }

    /**
     * <p>Generates a new {@link ColourCodeConversion} that acts as the identity of conversions. It means that it will
     * return always as result of conversion the given colour code to convert.</p>
     * @param colourCodeClass Class of the ColourCode
     * @return a {@link ColourCodeConversion} which, after calling convert with a ColourCode, returns the given ColourCode
     * @param <CODE> ColourCode
     */
    static <CODE extends ColourCode> ColourCodeConversion<CODE, CODE> identity(final Class<CODE> colourCodeClass) {
        return new ColourCodeConversion<>() {
            @Override
            public Class<CODE> inColourCodeClass() {
                return colourCodeClass;
            }

            @Override
            public Class<CODE> outColourCodeClass() {
                return colourCodeClass;
            }

            @Override
            public CODE convert(CODE codification) {
                return codification;
            }
        };
    }

}
