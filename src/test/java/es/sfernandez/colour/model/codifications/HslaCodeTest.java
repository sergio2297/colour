package es.sfernandez.colour.model.codifications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HslaCodeTest {

    //---- Fixtures ----
    private final HslCode anHslCode = new HslCode(360, 100, 100);

    //---- Tests ----
    @Test
    void createWith_nullHslCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslaCode(null, 1));
    }

    @Test
    void createWith_negativeFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslaCode(anHslCode, -1f));
    }

    @Test
    void createWith_biggerThanOneFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslaCode(anHslCode, 1.1f));
    }

    @Test
    void createWith_negativePercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslaCode(anHslCode, -20));
    }

    @Test
    void createWith_biggerThan100PercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslaCode(anHslCode, 150));
    }

    @Test
    void alphaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HslaCode hsla = new HslaCode(anHslCode, 85);

        assertThat(hsla.alpha()).isEqualTo(0.85f);
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedCodeTest() {
        HslaCode hsl = new HslaCode(0.25f, 0.5f, 0.75f, 0.5f);

        assertThat(hsl.toCssCode()).isEqualTo("hsla(90, 50%, 75%, 0.5)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromPercentageTest() {
        HslaCode hsla = new HslaCode(3, 9, 19, 95);

        assertThat(hsla.toCssCode()).isEqualTo("hsla(3, 9%, 19%, 0.95)");
    }

    @Test
    void createFromIncorrectCssCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslaCode("hlsa(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslaCode("hsla[0,0,0,0]"));
        assertThrows(IllegalArgumentException.class, () -> new HslaCode("hsla(0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslaCode("hsl(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslaCode("hsla(0,0,0,-0.5)"));
        assertThrows(IllegalArgumentException.class, () -> new HslaCode("hsla(0,0,0,0,5)"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hsla(63, 27%, 88%, 0.18)", "hsla(63   , 27%, 88%  ,    0.18  )", "hsla(63,27%,88%,0.18)"})
    void createFromCssCode_worksTest(String cssCode) {
        HslaCode hsla = new HslaCode(cssCode);

        assertThat(hsla.hsl().hueDegrees()).isEqualTo(63);
        assertThat(hsla.hsl().saturationPercentage()).isEqualTo(27);
        assertThat(hsla.hsl().lightnessPercentage()).isEqualTo(88);
        assertThat(hsla.alpha()).isEqualTo(0.18f);
    }
    
}