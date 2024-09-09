package es.sfernandez.colour.codifications;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HsvCodeTest {

    @Test
    void createWith_negativeFloatHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(-1f, 0f, 0f));
    }

    @Test
    void createWith_negativeFloatSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0f, -1.0f, 0f));
    }

    @Test
    void createWith_negativeFloatBrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0f, 0f, -1f));
    }

    @Test
    void createWith_negativeFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0f, 0f, 0f, -1f));
    }

    @Test
    void createWith_biggerThanOneHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(1.1f, 0f, 0f));
    }

    @Test
    void createWith_biggerThanOneSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0f, 1.1f, 0f));
    }

    @Test
    void createWith_biggerThanOneBrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0f, 0f, 1.1f));
    }

    @Test
    void createWith_biggerThanOneFloatAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0f, 0f, 0f, 1.1f));
    }

    @Test
    void createWith_negativeIntHueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(-1, 0, 0));
    }

    @Test
    void createWith_negativeIntSaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0, -1, 0));
    }

    @Test
    void createWith_negativeIntBrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0, 0, -1));
    }

    @Test
    void createWith_negativePercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0, 0, 0, -20));
    }

    @Test
    void createWith_biggerThan360HueValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(361, 0, 0));
    }

    @Test
    void createWith_biggerThan100SaturationValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0, 101, 0));
    }

    @Test
    void createWith_biggerThan100BrightnessValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0, 0, 101));
    }

    @Test
    void createWith_biggerThan100PercentageAlphaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HsvCode(0, 0, 0, 150));
    }

    @Test
    void createWithoutSpecifyOpacity_assignsFullOpacityTest() {
        HsvCode hsv = new HsvCode(0, 0, 0);

        assertThat(hsv.isOpaque()).isTrue();
    }

    @Test
    void hueValue_createdFromDegrees_isCorrectlyNormalizedTest() {
        HsvCode hsv = new HsvCode(90, 0, 0);

        assertThat(hsv.hue()).isCloseTo(0.25f, Offset.offset(0.005f));
    }

    @Test
    void saturationValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsvCode hsv = new HsvCode(0, 50, 0);

        assertThat(hsv.saturation()).isCloseTo(0.5f, Offset.offset(0.005f));
    }

    @Test
    void brightnessValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsvCode hsv = new HsvCode(0, 0, 75);

        assertThat(hsv.brightness()).isCloseTo(0.75f, Offset.offset(0.005f));
    }

    @Test
    void alphaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        HsvCode hsv = new HsvCode(0, 0, 0, 85);

        assertThat(hsv.alpha()).isEqualTo(0.85f);
    }
    
    @Test
    void hueValue_createdFromNormalizedValue_isCorrectlyNormalizedToDegreesTest() {
        HsvCode hsv = new HsvCode(0.75f, 0, 0);

        assertThat(hsv.hueDegrees()).isEqualTo(270);
    }

    @Test
    void saturationValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HsvCode hsv = new HsvCode(0, 0.5f, 0);

        assertThat(hsv.saturationPercentage()).isEqualTo(50);
    }

    @Test
    void brightnessValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HsvCode hsv = new HsvCode(0, 0, 0.75f);

        assertThat(hsv.brightnessPercentage()).isEqualTo(75);
    }

    @Test
    void alphaValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        HsvCode hsv = new HsvCode(0, 0, 0, 0.33f);

        assertThat(hsv.alphaPercentage()).isEqualTo(33);
    }

    @Test
    void hsvCodes_areEqual_iffTheyHaveTheSameRedGreenBlueAlphaValuesTest() {
        HsvCode hsvA = new HsvCode(0.88f, 0.44f, 0.22f, 0.11f);
        HsvCode hsvB = new HsvCode(317, 44, 22, 11);
        HsvCode hsvC = new HsvCode(316, 44, 22, 11);
        HsvCode hsvD = new HsvCode(317, 43, 22, 11);
        HsvCode hsvE = new HsvCode(317, 44, 21, 11);
        HsvCode hsvF = new HsvCode(317, 44, 22, 10);

        assertThat(hsvA.equals(hsvB)).isTrue();

        assertThat(hsvA.equals(hsvC)).isFalse();
        assertThat(hsvA.equals(hsvD)).isFalse();
        assertThat(hsvA.equals(hsvE)).isFalse();
        assertThat(hsvA.equals(hsvF)).isFalse();
    }

    @Test
    void hsvCodes_haveSameHashCode_iffTheyAreEqualTest() {
        HsvCode hsvA = new HsvCode(0.88f, 0.44f, 0.22f, 0.11f);
        HsvCode hsvB = new HsvCode(317, 44, 22, 11);
        HsvCode hsvC = new HsvCode(316, 44, 22, 11);

        assertThat(hsvA.hashCode()).isEqualTo(hsvB.hashCode());

        assertThat(hsvA.hashCode()).isNotEqualTo(hsvC.hashCode());
    }
    
}