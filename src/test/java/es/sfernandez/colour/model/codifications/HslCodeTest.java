package es.sfernandez.colour.model.codifications;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HslCodeTest {

    @Test
    void createWith_negativeFloatHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(-1f, 0f, 0f));
    }

    @Test
    void createWith_negativeFloatSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0f, -1.0f, 0f));
    }

    @Test
    void createWith_negativeFloatLightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0f, 0f, -1f));
    }

    @Test
    void createWith_negativeFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0f, 0f, 0f, -1f));
    }

    @Test
    void createWith_biggerThanOneHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(1.1f, 0f, 0f));
    }

    @Test
    void createWith_biggerThanOneSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0f, 1.1f, 0f));
    }

    @Test
    void createWith_biggerThanOneLightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0f, 0f, 1.1f));
    }

    @Test
    void createWith_biggerThanOneFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0f, 0f, 0f, 1.1f));
    }

    @Test
    void createWith_negativeIntHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(-1, 0, 0));
    }

    @Test
    void createWith_negativeIntSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0, -1, 0));
    }

    @Test
    void createWith_negativeIntLightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0, 0, -1));
    }

    @Test
    void createWith_negativePercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0, 0, 0, -20));
    }

    @Test
    void createWith_biggerThan360HueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(361, 0, 0));
    }

    @Test
    void createWith_biggerThan100SaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0, 101, 0));
    }

    @Test
    void createWith_biggerThan100LightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0, 0, 101));
    }

    @Test
    void createWith_biggerThan100PercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode(0, 0, 0, 150));
    }

    @Test
    void createWithoutSpecifyOpacity_assignsFullOpacityTest() {
        HslCode hsl = new HslCode(0, 0, 0);

        assertThat(hsl.isOpaque()).isTrue();
    }

    @Test
    void hueValue_createdFromDegrees_isCorrectlyNormalizedTest() {
        HslCode hsl = new HslCode(90, 0, 0);

        assertThat(hsl.hue()).isCloseTo(0.25f, Offset.offset(0.005f));
    }

    @Test
    void saturationValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HslCode hsl = new HslCode(0, 50, 0);

        assertThat(hsl.saturation()).isCloseTo(0.5f, Offset.offset(0.005f));
    }

    @Test
    void lightnessValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HslCode hsl = new HslCode(0, 0, 75);

        assertThat(hsl.lightness()).isCloseTo(0.75f, Offset.offset(0.005f));
    }

    @Test
    void alphaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HslCode hsl = new HslCode(0, 0, 0, 85);

        assertThat(hsl.alpha()).isEqualTo(0.85f);
    }

    @Test
    void hueValue_createdFromNormalizedValue_isCorrectlyNormalizedToDegreesTest() {
        HslCode hsl = new HslCode(0.75f, 0, 0);

        assertThat(hsl.hueDegrees()).isEqualTo(270);
    }

    @Test
    void saturationValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HslCode hsl = new HslCode(0, 0.5f, 0);

        assertThat(hsl.saturationPercentage()).isEqualTo(50);
    }

    @Test
    void lightnessValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HslCode hsl = new HslCode(0, 0, 0.75f);

        assertThat(hsl.lightnessPercentage()).isEqualTo(75);
    }

    @Test
    void alphaValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HslCode hsl = new HslCode(0, 0, 0, 0.33f);

        assertThat(hsl.alphaPercentage()).isEqualTo(33);
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedCodeTest() {
        HslCode hsl = new HslCode(0.25f, 0.5f, 0.75f);

        assertThat(hsl.toCssCode()).isEqualTo("hsl(90, 50%, 75%)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedPercentageCodeTest() {
        HslCode hsl = new HslCode(230, 15, 88);

        assertThat(hsl.toCssCode()).isEqualTo("hsl(230, 15%, 88%)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedCodeWithTransparencyTest() {
        HslCode hsl = new HslCode(0.25f, 0.5f, 0.75f, 0.33f);

        assertThat(hsl.toCssCode()).isEqualTo("hsla(90, 50%, 75%, 0.33)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedPercentageCodeWithTransparencyTest() {
        HslCode hsl = new HslCode(3, 9, 19, 95);

        assertThat(hsl.toCssCode()).isEqualTo("hsla(3, 9%, 19%, 0.95)");
    }

    @Test
    void createFromIncorrectCssCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hls(0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl[0,0,0]"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(-1,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(0,0.5,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(90,0%,45)"));
    }

    @Test
    void createFromIncorrectCssCode_withTransparency_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hlsa(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsla[0,0,0,0]"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsla(0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsla(0,0,0,-0.5)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsla(0,0,0,0,5)"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hsl(63, 27%, 88%)", "hsl(63   , 27%, 88%    )", "hsl(63,27%,88%)"})
    void createFromCssCode_worksTest(String cssCode) {
        HslCode hsl = new HslCode(cssCode);

        assertThat(hsl.hueDegrees()).isEqualTo(63);
        assertThat(hsl.saturationPercentage()).isEqualTo(27);
        assertThat(hsl.lightnessPercentage()).isEqualTo(88);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hsla(63, 27%, 88%, 0.18)", "hsla(63   , 27%, 88%  ,    0.18  )", "hsla(63,27%,88%,0.18)"})
    void createFromCssCode_withTransparency_worksTest(String cssCode) {
        HslCode hsla = new HslCode(cssCode);

        assertThat(hsla.hueDegrees()).isEqualTo(63);
        assertThat(hsla.saturationPercentage()).isEqualTo(27);
        assertThat(hsla.lightnessPercentage()).isEqualTo(88);
        assertThat(hsla.alpha()).isEqualTo(0.18f);
    }

    @Test
    void hslCodes_areEqual_iffTheyHaveTheSameRedGreenBlueAlphaValuesTest() {
        HslCode hslA = new HslCode(0.88f, 0.44f, 0.22f, 0.11f);
        HslCode hslB = new HslCode(317, 44, 22, 11);
        HslCode hslC = new HslCode(316, 44, 22, 11);
        HslCode hslD = new HslCode(317, 43, 22, 11);
        HslCode hslE = new HslCode(317, 44, 21, 11);
        HslCode hslF = new HslCode(317, 44, 22, 10);

        assertThat(hslA.equals(hslB)).isTrue();

        assertThat(hslA.equals(hslC)).isFalse();
        assertThat(hslA.equals(hslD)).isFalse();
        assertThat(hslA.equals(hslE)).isFalse();
        assertThat(hslA.equals(hslF)).isFalse();
    }

    @Test
    void hslCodes_haveSameHashCode_iffTheyAreEqualTest() {
        HslCode hslA = new HslCode(0.88f, 0.44f, 0.22f, 0.11f);
        HslCode hslB = new HslCode(317, 44, 22, 11);
        HslCode hslC = new HslCode(316, 44, 22, 11);

        assertThat(hslA.hashCode()).isEqualTo(hslB.hashCode());

        assertThat(hslA.hashCode()).isNotEqualTo(hslC.hashCode());
    }
    
}