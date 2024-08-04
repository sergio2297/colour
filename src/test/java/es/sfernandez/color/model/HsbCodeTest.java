package es.sfernandez.color.model;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HsbCodeTest {

    @Test
    void createWith_negativeFloatHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(-1f, 0f, 0f));
    }

    @Test
    void createWith_negativeFloatSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0f, -1.0f, 0f));
    }

    @Test
    void createWith_negativeFloatBrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0f, 0f, -1f));
    }

    @Test
    void createWith_biggerThanOneHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(1.1f, 0f, 0f));
    }

    @Test
    void createWith_biggerThanOneSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0f, 1.1f, 0f));
    }

    @Test
    void createWith_biggerThanOneBrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0f, 0f, 1.1f));
    }

    @Test
    void createWith_negativeIntHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(-1, 0, 0));
    }

    @Test
    void createWith_negativeIntSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0, -1, 0));
    }

    @Test
    void createWith_negativeIntBrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0, 0, -1));
    }

    @Test
    void createWith_biggerThan360HueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(361, 0, 0));
    }

    @Test
    void createWith_biggerThan100SaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0, 101, 0));
    }

    @Test
    void createWith_biggerThan100BrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0, 0, 101));
    }

    @Test
    void hueValue_createdFromDegrees_isCorrectlyNormalizedTest() {
        HsbCode hsl = new HsbCode(90, 0, 0);

        assertThat(hsl.hue()).isCloseTo(0.25f, Offset.offset(0.005f));
    }

    @Test
    void saturationValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsbCode hsl = new HsbCode(0, 50, 0);

        assertThat(hsl.saturation()).isCloseTo(0.5f, Offset.offset(0.005f));
    }

    @Test
    void brightnessValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsbCode hsl = new HsbCode(0, 0, 75);

        assertThat(hsl.brightness()).isCloseTo(0.75f, Offset.offset(0.005f));
    }

    @Test
    void hueValue_createdFromNormalizedValue_isCorrectlyNormalizedToDegreesTest() {
        HsbCode hsb = new HsbCode(0.75f, 0, 0);

        assertThat(hsb.hueDegrees()).isEqualTo(270);
    }

    @Test
    void saturationValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HsbCode hsb = new HsbCode(0, 0.5f, 0);

        assertThat(hsb.saturationPercentage()).isEqualTo(50);
    }

    @Test
    void brightnessValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HsbCode hsb = new HsbCode(0, 0, 0.75f);

        assertThat(hsb.brightnessPercentage()).isEqualTo(75);
    }

}