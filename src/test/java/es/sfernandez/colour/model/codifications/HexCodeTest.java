package es.sfernandez.colour.model.codifications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

    @Test
    void createFromIncorrectCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new HexCode("#HAW345"));
        assertThrows(IllegalArgumentException.class, () -> new HexCode("#12345"));
        assertThrows(IllegalArgumentException.class, () -> new HexCode("2"));
        assertThrows(IllegalArgumentException.class, () -> new HexCode("#12"));
        assertThrows(IllegalArgumentException.class, () -> new HexCode("#FFAABBEE1"));
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
    void hexCodes_areEqual_iffTheyRepresentTheSameColourTest() {
        HexCode code1 = new HexCode("aBcF05");
        HexCode code2 = new HexCode("#ABCF05");

        assertThat(code1).isEqualTo(code2);
    }

}