package es.sfernandez.colour.model.codifications;

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
    void createWith_negativeFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0f, 0f, 0f, -1f));
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
    void createWith_biggerThanOneFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0f, 0f, 0f, 1.1f));
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
    void createWith_negativePercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0, 0, 0, -20));
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
    void createWith_biggerThan100PercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsbCode(0, 0, 0, 150));
    }

    @Test
    void createWithoutSpecifyOpacity_assignsFullOpacityTest() {
        HsbCode hsb = new HsbCode(0, 0, 0);

        assertThat(hsb.isOpaque()).isTrue();
    }

    @Test
    void hueValue_createdFromDegrees_isCorrectlyNormalizedTest() {
        HsbCode hsb = new HsbCode(90, 0, 0);

        assertThat(hsb.hue()).isCloseTo(0.25f, Offset.offset(0.005f));
    }

    @Test
    void saturationValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsbCode hsb = new HsbCode(0, 50, 0);

        assertThat(hsb.saturation()).isCloseTo(0.5f, Offset.offset(0.005f));
    }

    @Test
    void brightnessValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsbCode hsb = new HsbCode(0, 0, 75);

        assertThat(hsb.brightness()).isCloseTo(0.75f, Offset.offset(0.005f));
    }

    @Test
    void alphaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsbCode hsb = new HsbCode(0, 0, 0, 85);

        assertThat(hsb.alpha()).isEqualTo(0.85f);
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

    @Test
    void alphaValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HsbCode hsb = new HsbCode(0, 0, 0, 0.33f);

        assertThat(hsb.alphaPercentage()).isEqualTo(33);
    }

    @Test
    void hsbCodes_areEqual_iffTheyHaveTheSameRedGreenBlueAlphaValuesTest() {
        HsbCode hsbA = new HsbCode(0.88f, 0.44f, 0.22f, 0.11f);
        HsbCode hsbB = new HsbCode(317, 44, 22, 11);
        HsbCode hsbC = new HsbCode(316, 44, 22, 11);
        HsbCode hsbD = new HsbCode(317, 43, 22, 11);
        HsbCode hsbE = new HsbCode(317, 44, 21, 11);
        HsbCode hsbF = new HsbCode(317, 44, 22, 10);

        assertThat(hsbA.equals(hsbB)).isTrue();

        assertThat(hsbA.equals(hsbC)).isFalse();
        assertThat(hsbA.equals(hsbD)).isFalse();
        assertThat(hsbA.equals(hsbE)).isFalse();
        assertThat(hsbA.equals(hsbF)).isFalse();
    }

    @Test
    void hsbCodes_haveSameHashCode_iffTheyAreEqualTest() {
        HsbCode hsbA = new HsbCode(0.88f, 0.44f, 0.22f, 0.11f);
        HsbCode hsbB = new HsbCode(317, 44, 22, 11);
        HsbCode hsbC = new HsbCode(316, 44, 22, 11);

        assertThat(hsbA.hashCode()).isEqualTo(hsbB.hashCode());

        assertThat(hsbA.hashCode()).isNotEqualTo(hsbC.hashCode());
    }
    
}