package es.sfernandez.colour.conversion;

import es.sfernandez.colour.codifications.ColourCode;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class ColourCodeConversionTest {

    //---- Constants and Definitions ----
    private static class DummyColourCode implements ColourCode {}
    private static class A implements ColourCode {}
    private static class B implements ColourCode {}
    private static class C implements ColourCode {}

    //---- Methods ----
    public static <A extends ColourCode, B extends ColourCode> ColourCodeConversion<A, B> dummyConversion(Class<A> in, Class<B> out) {
        return new ColourCodeConversion<>() {
            @Override
            public Class<A> inColourCodeClass() {
                return in;
            }

            @Override
            public Class<B> outColourCodeClass() {
                return out;
            }

            @Override
            public B convert(A codification) {
                try {
                    return out.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    //---- Test ----
    @Test
    void inClassOf_identityConversion_isClassPassedAsArgumentTest() {
        Class<DummyColourCode> colourCodeClass = DummyColourCode.class;
        ColourCodeConversion<DummyColourCode, DummyColourCode> conversion = ColourCodeConversion.identity(colourCodeClass);

        assertThat(conversion.inColourCodeClass()).isEqualTo(colourCodeClass);
    }

    @Test
    void outClassOf_identityConversion_isClassPassedAsArgumentTest() {
        Class<DummyColourCode> colourCodeClass = DummyColourCode.class;
        ColourCodeConversion<DummyColourCode, DummyColourCode> conversion = ColourCodeConversion.identity(colourCodeClass);

        assertThat(conversion.outColourCodeClass()).isEqualTo(colourCodeClass);
    }

    @Test
    void identityConversion_returnsTheSameInputTest() {
        DummyColourCode in = new DummyColourCode();

        DummyColourCode out = ColourCodeConversion.identity(DummyColourCode.class).convert(in);

        assertThat(out).isSameAs(in);
    }

    @Test
    void byDefault_callApplyGeneratesTheSameResultAsConvertTest() {
        DummyColourCode colourCode = new DummyColourCode();
        ColourCodeConversion<DummyColourCode, DummyColourCode> conversion = ColourCodeConversion.identity(DummyColourCode.class);

        assertThat(conversion.convert(colourCode)).isEqualTo(colourCode);
        assertThat(conversion.apply(colourCode)).isEqualTo(colourCode);
    }

    @Test
    void byDefault_callAndThenResultsInAComposeConversionTest() {
        ColourCodeConversion<A,B> a2b = dummyConversion(A.class, B.class);
        ColourCodeConversion<B,C> b2c = dummyConversion(B.class, C.class);

        ColourCodeConversion<A, C> a2c = a2b.andThen(b2c);

        assertThat(a2c.inColourCodeClass()).isEqualTo(A.class);
        assertThat(a2c.outColourCodeClass()).isEqualTo(C.class);
        assertThat(a2c.convert(new A())).isInstanceOf(C.class);
    }

}