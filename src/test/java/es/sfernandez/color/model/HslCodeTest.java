package es.sfernandez.color.model;

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
    void cssCode_isCorrectlyGenerated_fromNormalizedCodeTest() {
        HslCode hsl = new HslCode(0.25f, 0.5f, 0.75f);

        assertThat(hsl.toCssCode()).isEqualTo("hsl(90, 50%, 75%)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalized255CodeTest() {
        HslCode hsl = new HslCode(230, 15, 88);

        assertThat(hsl.toCssCode()).isEqualTo("hsl(230, 15%, 88%)");
    }

    @Test
    void createFromIncorrectCssCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hls(0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl[0,0,0]"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsla(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(-1,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(0,0.5,0)"));
        assertThrows(IllegalArgumentException.class, () -> new HslCode("hsl(90,0%,45)"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hsl(63, 27%, 88%)", "hsl(63   , 27%, 88%    )", "hsl(63,27%,88%)"})
    void createFromCssCode_worksTest(String cssCode) {
        HslCode hsl = new HslCode(cssCode);

        assertThat(hsl.hueDegrees()).isEqualTo(63);
        assertThat(hsl.saturationPercentage()).isEqualTo(27);
        assertThat(hsl.lightnessPercentage()).isEqualTo(88);
    }

}