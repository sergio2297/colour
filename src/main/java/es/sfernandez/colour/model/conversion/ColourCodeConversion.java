package es.sfernandez.colour.model.conversion;

import es.sfernandez.colour.model.codifications.ColourCode;

import java.util.function.Function;

public interface ColourCodeConversion<IN extends ColourCode, OUT extends ColourCode>
        extends Function<IN, OUT> {

    Class<IN> inColourCodeClass();
    Class<OUT> outColourCodeClass();

    OUT convert(IN codification);

    @Override
    default OUT apply(IN in) {
        return convert(in);
    }

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
