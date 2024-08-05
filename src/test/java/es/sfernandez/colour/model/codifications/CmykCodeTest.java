package es.sfernandez.colour.model.codifications;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CmykCodeTest {

    @Test
    void createWith_negativeFloatCyanValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(-1f, 0f, 0f, 0f));
    }

    @Test
    void createWith_negativeFloatMagentaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0f, -1.0f, 0f, 0f));
    }

    @Test
    void createWith_negativeFloatYellowValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0f, 0f, -1f, 0f));
    }

    @Test
    void createWith_negativeFloatBlackValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0f, 0f, 0f, -1f));
    }

    @Test
    void createWith_biggerThanOneCyanValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(1.1f, 0f, 0f, 0f));
    }

    @Test
    void createWith_biggerThanOneMagentaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0f, 1.1f, 0f, 0f));
    }

    @Test
    void createWith_biggerThanOneYellowValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0f, 0f, 1.1f, 0f));
    }

    @Test
    void createWith_biggerThanOneBlackValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0f, 0f, 0f, 1.1f));
    }

    @Test
    void createWith_negativeIntCyanValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(-1, 0, 0, 0));
    }

    @Test
    void createWith_negativeIntMagentaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0, -1, 0, 0));
    }

    @Test
    void createWith_negativeIntYellowValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0, 0, -1, 0));
    }

    @Test
    void createWith_negativeIntBlackValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0, 0, 0, -1));
    }

    @Test
    void createWith_biggerThan100CyanValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(101, 0, 0, 0));
    }

    @Test
    void createWith_biggerThan100MagentaValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0, 101, 0, 0));
    }

    @Test
    void createWith_biggerThan100YellowValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0, 0, 101, 0));
    }

    @Test
    void createWith_biggerThan100BlackValue_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode(0, 0, 0, 101));
    }
    
    @Test
    void cyanValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        CmykCode cmyk = new CmykCode(63, 0, 0, 0);

        assertThat(cmyk.cyan()).isCloseTo(0.63f, Offset.offset(0.005f));
    }

    @Test
    void magentaValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        CmykCode cmyk = new CmykCode(0, 27, 0, 0);

        assertThat(cmyk.magenta()).isCloseTo(0.27f, Offset.offset(0.005f));
    }

    @Test
    void yellowValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        CmykCode cmyk = new CmykCode(0, 0, 91, 0);

        assertThat(cmyk.yellow()).isCloseTo(0.91f, Offset.offset(0.005f));
    }

    @Test
    void blackValue_createdFromPercentage_isCorrectlyNormalizedTest() {
        CmykCode cmyk = new CmykCode(0, 0, 0, 10);

        assertThat(cmyk.black()).isCloseTo(0.1f, Offset.offset(0.005f));
    }

    @Test
    void cyanValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        CmykCode cmyk = new CmykCode(0.25f, 0f, 0f, 0f);

        assertThat(cmyk.cyanPercentage()).isEqualTo(25);
    }

    @Test
    void magentaValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        CmykCode cmyk = new CmykCode(0f, 0.5f, 0f, 0f);

        assertThat(cmyk.magentaPercentage()).isEqualTo(50);
    }

    @Test
    void yellowValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        CmykCode cmyk = new CmykCode(0f, 0f, 0.75f, 0f);

        assertThat(cmyk.yellowPercentage()).isEqualTo(75);
    }

    @Test
    void blackValue_createdFromNormalizedValue_isCorrectlyNormalizedToPercentageTest() {
        CmykCode cmyk = new CmykCode(0f, 0f, 0f, 0.3f);

        assertThat(cmyk.blackPercentage()).isEqualTo(30);
    }

    @Test
    void code_isCorrectlyGenerated_fromNormalizedCodeTest() {
        CmykCode cmyk = new CmykCode(0.25f, 0.5f, 0.75f, 0.44f);

        assertThat(cmyk.toCode()).isEqualTo("C25 M50 Y75 K44");
    }

    @Test
    void code_isCorrectlyGenerated_fromNormalizedPercentageCodeTest() {
        CmykCode cmyk = new CmykCode(3, 0, 19, 100);

        assertThat(cmyk.toCode()).isEqualTo("C3 M0 Y19 K100");
    }

    @Test
    void createFromIncorrectCssCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new CmykCode("C10M20Y30K40"));
        assertThrows(IllegalArgumentException.class, () -> new CmykCode("c10 m20 y30 k40"));
        assertThrows(IllegalArgumentException.class, () -> new CmykCode("C110 M20 Y30 K40"));
        assertThrows(IllegalArgumentException.class, () -> new CmykCode("C-10 M20 Y30 K40"));
        assertThrows(IllegalArgumentException.class, () -> new CmykCode("C1.6 M20 Y30 K40"));
        assertThrows(IllegalArgumentException.class, () -> new CmykCode("10C 20M 30Y 40K"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"C63 M27 Y88 K100", "C63  M27      Y88   K100"})
    void createFromCssCode_worksTest(String cssCode) {
        CmykCode cmyk = new CmykCode(cssCode);

        assertThat(cmyk.cyanPercentage()).isEqualTo(63);
        assertThat(cmyk.magentaPercentage()).isEqualTo(27);
        assertThat(cmyk.yellowPercentage()).isEqualTo(88);
        assertThat(cmyk.blackPercentage()).isEqualTo(100);
    }

}