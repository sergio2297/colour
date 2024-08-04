package es.sfernandez.color.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HsbaCodeTest {

    //---- Fixtures ----
    private final HsbCode anHsbCode = new HsbCode(360, 100, 100);

    //---- Tests ----
    @Test
    void createWith_nullHsbCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbaCode(null, 1));
    }

    @Test
    void createWith_negativeFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbaCode(anHsbCode, -1f));
    }

    @Test
    void createWith_biggerThanOneFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbaCode(anHsbCode, 1.1f));
    }

    @Test
    void createWith_negativePercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbaCode(anHsbCode, -20));
    }

    @Test
    void createWith_biggerThan100PercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbaCode(anHsbCode, 150));
    }

    @Test
    void alphaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsbaCode hsba = new HsbaCode(anHsbCode, 85);

        assertThat(hsba.alpha()).isEqualTo(0.85f);
    }

    @Test
    void createWith_floatValues_worksTest() {
        HsbaCode hsba = new HsbaCode(0.5f, 0.25f, 1f, 0.85f);

        assertThat(hsba.hsb().hue()).isEqualTo(0.5f);
        assertThat(hsba.hsb().saturation()).isEqualTo(0.25f);
        assertThat(hsba.hsb().brightness()).isEqualTo(1f);
        assertThat(hsba.alpha()).isEqualTo(0.85f);
    }

    @Test
    void createWith_intValues_worksTest() {
        HsbaCode hsba = new HsbaCode(270, 23, 11, 85);

        assertThat(hsba.hsb().hueDegrees()).isEqualTo(270);
        assertThat(hsba.hsb().saturationPercentage()).isEqualTo(23);
        assertThat(hsba.hsb().brightnessPercentage()).isEqualTo(11);
        assertThat(hsba.alpha()).isEqualTo(0.85f);
    }

}