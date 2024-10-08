package es.sfernandez.colour.codifications;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HexCodeTest {

    @Test
    void createWith_nullCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HexCode(null));
    }

    @Test
    void createWith_emptyCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HexCode("   "));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createFromNullOrEmptyCssCode_throwsIllegalArgumentExceptionTest(final String notValidHexCode) {
        assertThrows(IllegalArgumentException.class, () -> new HexCode(notValidHexCode));
    }

    @ParameterizedTest
    @ValueSource(strings = {"#HAW345", "#12345", "2", "#12", "#FFAABBEE1"})
    void createFromIncorrectCode_throwsIllegalArgumentExceptionTest(final String notValidHexCode) {
        assertThrows(IllegalArgumentException.class, () -> new HexCode(notValidHexCode));
    }

    @Test
    void createFromValidCode_worksTest() {
        String code = "#AE12FA";
        HexCode hexCode = new HexCode(code);

        assertThat(hexCode.value()).isEqualTo(code);
    }

    @Test
    void createFromValidCode_withoutHashtag_worksTest() {
        String code = "AE12FA";
        HexCode hexCode = new HexCode(code);

        assertThat(hexCode.value()).isEqualTo("#" + code);
    }

    @Test
    void createWithOpacity_worksTest() {
        String code = "#B1C2D3FE";
        HexCode hexCode = new HexCode(code);

        assertThat(hexCode.value()).isEqualTo("#B1C2D3FE");
    }

    @Test
    void createSimplifiedWithExplicitFullOpacity_doesNotSuppressOpacityTest() {
        String code = "#FFFF";
        HexCode hexCode = new HexCode(code);

        assertThat(hexCode.value()).isEqualTo("#FFFF");
    }

    @Test
    void createWithExplicitFullOpacity_doesNotSuppressOpacityTest() {
        String code = "#B1C2D3FF";
        HexCode hexCode = new HexCode(code);

        assertThat(hexCode.value()).isEqualTo("#B1C2D3FF");
    }

    @ParameterizedTest
    @ValueSource(strings = {"#fff", "#123456", "123", "#aaff", "#AaBbCcDd"})
    void cssCodeIsEqualToValueTest(String cssCode) {
        HexCode hexCode = new HexCode(cssCode);

        assertThat(hexCode.value()).isEqualTo(hexCode.toCssCode());
    }

    @Test
    void codeIsAlwaysUpperCasedTest() {
        String code = "#b1c2D0fE";
        HexCode hexCode = new HexCode(code);

        assertThat(hexCode.value()).isEqualTo("#B1C2D0FE");
    }

    @Test
    void hexCode_isSimplified_ifItsLengthIsThreeOrFourTest() {
        assertThat(new HexCode("#AAB").isSimplified()).isTrue();
        assertThat(new HexCode("#AABF").isSimplified()).isTrue();
    }

    @Test
    void hexCode_isNotSimplified_ifItsLengthIsSixOrEightTest() {
        assertThat(new HexCode("#AABBCC").isSimplified()).isFalse();
        assertThat(new HexCode("#AABBCCDD").isSimplified()).isFalse();
    }

    @Test
    void hexCode_hasExplicitOpacity_ifItsLengthIsFourOrEightTest() {
        assertThat(new HexCode("#ABCF").hasExplicitOpacity()).isTrue();
        assertThat(new HexCode("#AABBCCFF").hasExplicitOpacity()).isTrue();
    }

    @Test
    void hexCode_hasNotExplicitOpacity_ifItsLengthIsThreeOrSixTest() {
        assertThat(new HexCode("#ABC").hasExplicitOpacity()).isFalse();
        assertThat(new HexCode("#AABBCC").hasExplicitOpacity()).isFalse();
    }

    @Test
    void getAlpha_fromImplicitOpacity_isOneTest() {
        assertThat(new HexCode("#ABC").alpha()).isEqualTo(1.0f);
        assertThat(new HexCode("#AABBCC").alpha()).isEqualTo(1.0f);
    }

    @Test
    void getAlpha_fromSimplifiedHexCode_worksTest() {
        assertThat(new HexCode("#ABC0").alpha()).isEqualTo(0f);
    }

    @Test
    void getAlpha_fromHexCode_worksTest() {
        assertThat(new HexCode("#AABBCC80").alpha()).isCloseTo(0.5f, Offset.offset(0.005f));
    }

    @Test
    void toString_returnsCssCodeTest() {
        HexCode code = new HexCode("abcd");

        assertThat(code.toCssCode()).isEqualTo(code.toString());
    }

    @Test
    void hexCodes_areEqual_iffTheyRepresentTheSameColour_noMatherIfOneOfThemIsSimplifiedTest() {
        HexCode code1 = new HexCode("abcf");
        HexCode code2 = new HexCode("#AABBCCFF");

        assertThat(code1).isEqualTo(code2);
    }

    @Test
    void hexCodes_areEqual_iffTheyRepresentTheSameColourTest() {
        HexCode code1 = new HexCode("aBcF05");
        HexCode code2 = new HexCode("#ABCF05");

        assertThat(code1).isEqualTo(code2);
    }

    @Test
    void hexCodes_areEqual_iffTheyRepresentTheSameColour_and_bothAreOpaqueTest() {
        HexCode code1 = new HexCode("aBcF05");
        HexCode code2 = new HexCode("#ABCF05FF");

        assertThat(code1).isEqualTo(code2);
    }

    @Test
    void hexCodes_haveSameHashCode_iffTheyAreEqualTest() {
        HexCode code1 = new HexCode("aBcF05");
        HexCode code2 = new HexCode("#ABCF05FF");
        HexCode code3 = new HexCode("#AaCF05FF");

        assertThat(code1.hashCode()).isEqualTo(code2.hashCode());

        assertThat(code1.hashCode()).isNotEqualTo(code3.hashCode());
    }

}