package es.sfernandez.colour.model.codifications;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RgbCodeTest {

    @Test
    void createWith_negativeFloatRedValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(-1f, 0f, 0f));
    }

    @Test
    void createWith_negativeFloatGreenValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0f, -1.0f, 0f));
    }

    @Test
    void createWith_negativeFloatBlueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0f, 0f, -1f));
    }

    @Test
    void createWith_negativeFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0f, 0f, 0f, -1f));
    }

    @Test
    void createWith_biggerThanOneRedValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(1.1f, 0f, 0f));
    }

    @Test
    void createWith_biggerThanOneGreenValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0f, 1.1f, 0f));
    }

    @Test
    void createWith_biggerThanOneBlueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0f, 0f, 1.1f));
    }

    @Test
    void createWith_biggerThanOneFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0f, 0f, 0f, 1.1f));
    }

    @Test
    void createWith_negativeIntRedValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(-1, 0, 0));
    }

    @Test
    void createWith_negativeIntGreenValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0, -1, 0));
    }

    @Test
    void createWith_negativeIntBlueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0, 0, -1));
    }

    @Test
    void createWith_negativePercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0, 0, 0, -20));
    }

    @Test
    void createWith_biggerThan255RedValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(256, 0, 0));
    }

    @Test
    void createWith_biggerThan255GreenValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0, 256, 0));
    }

    @Test
    void createWith_biggerThan255BlueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0, 0, 256));
    }

    @Test
    void createWith_biggerThan100PercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode(0, 0, 0, 150));
    }

    @Test
    void createWithoutSpecifyOpacity_assignsFullOpacityTest() {
        RgbCode rgb = new RgbCode(0, 0, 0);

        assertThat(rgb.isOpaque()).isTrue();
    }

    @Test
    void redValue_createdFrom255_isCorrectlyNormalizedTest() {
        RgbCode rgb = new RgbCode(63, 0, 0);

        assertThat(rgb.red()).isCloseTo(0.25f, Offset.offset(0.005f));
    }

    @Test
    void greenValue_createdFrom255_isCorrectlyNormalizedTest() {
        RgbCode rgb = new RgbCode(0, 128, 0);

        assertThat(rgb.green()).isCloseTo(0.5f, Offset.offset(0.005f));
    }

    @Test
    void blueValue_createdFrom255_isCorrectlyNormalizedTest() {
        RgbCode rgb = new RgbCode(0, 0, 191);

        assertThat(rgb.blue()).isCloseTo(0.75f, Offset.offset(0.005f));
    }

    @Test
    void alphaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        RgbCode rgba = new RgbCode(0, 0, 0, 85);

        assertThat(rgba.alpha()).isEqualTo(0.85f);
    }

    @Test
    void redValue_createdFromNormalizedValue_isCorrectlyNormalizedTo255Test() {
        RgbCode rgb = new RgbCode(0.25f, 0, 0);

        assertThat(rgb.red255()).isEqualTo(64);
    }

    @Test
    void greenValue_createdFromNormalizedValue_isCorrectlyNormalizedTo255Test() {
        RgbCode rgb = new RgbCode(0, 0.5f, 0);

        assertThat(rgb.green255()).isEqualTo(128);
    }

    @Test
    void blueValue_createdFromNormalizedValue_isCorrectlyNormalizedTo255Test() {
        RgbCode rgb = new RgbCode(0, 0, 0.75f);

        assertThat(rgb.blue255()).isEqualTo(191);
    }

    @Test
    void alphaValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        RgbCode rgb = new RgbCode(0, 0, 0, 0.33f);

        assertThat(rgb.alphaPercentage()).isEqualTo(33);
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedCodeTest() {
        RgbCode rgb = new RgbCode(0.25f, 0.5f, 0.75f);

        assertThat(rgb.toCssCode()).isEqualTo("rgb(64, 128, 191)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalized255CodeTest() {
        RgbCode rgb = new RgbCode(3, 9, 19);

        assertThat(rgb.toCssCode()).isEqualTo("rgb(3, 9, 19)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalizedCodeWithTransparencyTest() {
        RgbCode rgb = new RgbCode(0.25f, 0.5f, 0.75f, 0.33f);

        assertThat(rgb.toCssCode()).isEqualTo("rgba(64, 128, 191, 0.33)");
    }

    @Test
    void cssCode_isCorrectlyGenerated_fromNormalized255CodeWithTransparencyTest() {
        RgbCode rgb = new RgbCode(3, 9, 19, 33);

        assertThat(rgb.toCssCode()).isEqualTo("rgba(3, 9, 19, 0.33)");
    }

    @Test
    void createFromIncorrectCssCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgv(0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgb[0,0,0]"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgb(0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgb(-1,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgb(0,0.5,0)"));
    }

    @Test
    void createFromIncorrectCssCode_withTransparency_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgv(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgba[0,0,0,0]"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgba(0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgb(0,0,0,0)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgba(0,0,0,-0.5)"));
        assertThrows(IllegalArgumentException.class, () -> new RgbCode("rgba(0,0,0,0,5)"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"rgb(63, 127, 88)", "rgb(63   , 127, 88    )", "rgb(63,127,88)"})
    void createFromCssCode_worksTest(String cssCode) {
        RgbCode rgb = new RgbCode(cssCode);

        assertThat(rgb.red255()).isEqualTo(63);
        assertThat(rgb.green255()).isEqualTo(127);
        assertThat(rgb.blue255()).isEqualTo(88);
    }

    @ParameterizedTest
    @ValueSource(strings = {"rgba(63, 127, 88, 0.18)", "rgba(63   , 127, 88  ,    0.18  )", "rgba(63,127,88,0.18)"})
    void createFromCssCode_withTransparency_worksTest(String cssCode) {
        RgbCode rgb = new RgbCode(cssCode);

        assertThat(rgb.red255()).isEqualTo(63);
        assertThat(rgb.green255()).isEqualTo(127);
        assertThat(rgb.blue255()).isEqualTo(88);
        assertThat(rgb.alpha()).isEqualTo(0.18f);
    }

}