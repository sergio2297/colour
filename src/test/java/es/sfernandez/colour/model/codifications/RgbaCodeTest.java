package es.sfernandez.colour.model.codifications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RgbaCodeTest {

    //---- Fixtures ----
    private final RgbCode aRgbCode = new RgbCode(255, 255, 255);

    //---- Tests ----
    @Test
    void createWith_nullRgbCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode(null, 1));
    }

    @Test
    void createWith_negativeFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode(aRgbCode, -1f));
    }

    @Test
    void createWith_biggerThanOneFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode(aRgbCode, 1.1f));
    }

    @Test
    void createWith_negativePercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode(aRgbCode, -20));
    }

    @Test
    void createWith_biggerThan100PercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode(aRgbCode, 150));
    }

    @Test
    void alphaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        RgbaCode rgba = new RgbaCode(aRgbCode, 85);

        assertThat(rgba.alpha()).isEqualTo(0.85f);
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedCodeTest() {
        RgbaCode rgb = new RgbaCode(0.25f, 0.5f, 0.75f, 0.5f);

        assertThat(rgb.toCssCode()).isEqualTo("rgba(63, 127, 191, 0.5)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromPercentageTest() {
        RgbaCode rgba = new RgbaCode(3, 9, 19, 95);

        assertThat(rgba.toCssCode()).isEqualTo("rgba(3, 9, 19, 0.95)");
    }

    @Test
    void createFromIncorrectCssCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode("rgv(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode("rgba[0,0,0,0]"));
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode("rgba(0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode("rgb(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode("rgba(0,0,0,-0.5)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbaCode("rgba(0,0,0,0,5)"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"rgba(63, 127, 88, 0.18)", "rgba(63   , 127, 88  ,    0.18  )", "rgba(63,127,88,0.18)"})
    void createFromCssCode_worksTest(String cssCode) {
        RgbaCode rgba = new RgbaCode(cssCode);

        assertThat(rgba.rgb().red255()).isEqualTo(63);
        assertThat(rgba.rgb().green255()).isEqualTo(127);
        assertThat(rgba.rgb().blue255()).isEqualTo(88);
        assertThat(rgba.alpha()).isEqualTo(0.18f);
    }

}