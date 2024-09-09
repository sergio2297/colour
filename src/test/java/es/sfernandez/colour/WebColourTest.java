package es.sfernandez.colour;

import es.sfernandez.colour.codifications.AcceptedByCssColourCode;
import es.sfernandez.colour.codifications.HexCode;
import es.sfernandez.colour.codifications.HslCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.fixtures.HexCodeFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WebColourTest {

    //---- Fixtures ----
    private final HexCode greenHexCode = new HexCodeFixtures().green();

    private static Stream<String> notValidCssColourCodes() {
        return Stream.of("#HAW345", "#12345", "2", "#12", "#FFAABBEE1", // hex
                "hls(0,0,0)", "hsl[0,0,0]", "hsl(0)", "hsl(-1,0,0)", "hsl(0,0.5,0)", "hsl(90,0%,45)", // hsl
                "hlsa(0,0,0,0)", "hsla[0,0,0,0]", "hsla(0)", "hsl(0,0,0,0)", "hsla(0,0,0,-0.5)", "hsla(0,0,0,0,5)", // hsla
                "rgv(0,0,0)", "rgb[0,0,0]", "rgb(0)", "rgb(-1,0,0)", "rgb(0,0.5,0)", // rgb
                "rgv(0,0,0,0)", "rgba[0,0,0,0]", "rgba(0)", "rgb(0,0,0,0)", "rgba(0,0,0,-0.5)", "rgba(0,0,0,0,5)" // rgba
        );
    }

    private static Stream<String> validCssColourCodes() {
        return Stream.of(validHexColourCodes(), validHslColourCodes(), validRgbColourCodes())
                .flatMap(Function.identity());
    }

    private static Stream<String> validHexColourCodes() {
        return Stream.of("#AE12FA", "#B1C2D3FE", "#FFFF", "#B1C2D3FF");
    }

    private static Stream<String> validHslColourCodes() {
        return Stream.of("hsl(63, 27%, 88%)", "hsl(63   , 27%, 88%    )", "hsl(63,27%,88%)", // hsl
                "hsla(63, 27%, 88%, 0.18)", "hsla(63   , 27%, 88%  ,    0.18  )", "hsla(63,27%,88%,0.18)" // hsla
        );
    }

    private static Stream<String> validRgbColourCodes() {
        return Stream.of("rgb(63, 127, 88)", "rgb(63   , 127, 88    )", "rgb(63,127,88)", // rgb
                "rgba(63, 127, 88, 0.18)", "rgba(63   , 127, 88  ,    0.18  )", "rgba(63,127,88,0.18)" // rgba
        );
    }

    private record ValidCssFixture(Stream<String> codes, Class<? extends AcceptedByCssColourCode> colourClass) {

        public Stream<Arguments> asTestArguments() {
            return codes.map(code -> {
                try {
                    return Arguments.of(code, colourClass.getConstructor(String.class).newInstance(code).toColour());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    private static Stream<Arguments> validCssColourCodesWithColourInstance() {
        return Stream.of(
                new ValidCssFixture(validHexColourCodes(), HexCode.class),
                new ValidCssFixture(validHslColourCodes(), HslCode.class),
                new ValidCssFixture(validRgbColourCodes(), RgbCode.class)
        ).flatMap(ValidCssFixture::asTestArguments);
    }

    //---- Tests ----
    @Test
    void create_withNullColourCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new WebColour(null));
    }

    @Test
    void create_withNullColourCodeConverter_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new WebColour(new RgbCode(0, 0, 0), null));
    }

    @Test
    void toCssCode_returnsCurrentColourCodeCssCodeTest() {
        WebColour colour = new WebColour(greenHexCode);

        assertThat(colour.toCssCode()).isEqualTo(greenHexCode.toCssCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isCssColourCode_returnsFalse_ifColourCodeNullOrEmptyTest(final String cssCode) {
        assertThat(WebColour.isCssColourCode(cssCode)).isFalse();
    }

    @ParameterizedTest
    @MethodSource(value = "notValidCssColourCodes")
    void isCssColourCode_returnsFalse_ifColourCodeIsNotValidTest(final String cssCode) {
        assertThat(WebColour.isCssColourCode(cssCode)).isFalse();
    }

    @ParameterizedTest
    @MethodSource(value = "validCssColourCodes")
    void isCssColourCode_returnsTrue_ifColourCodeIsValidTest(final String cssCode) {
        assertThat(WebColour.isCssColourCode(cssCode)).isTrue();
    }

    @ParameterizedTest
    @MethodSource(value = "notValidCssColourCodes")
    void from_notCssColourCode_throwsIllegalArgumentExceptionTest(final String cssCode) {
        assertThat(WebColour.isCssColourCode(cssCode)).isFalse();

        assertThrows(IllegalArgumentException.class, () -> WebColour.from(cssCode));
    }

    @ParameterizedTest
    @MethodSource(value = "validCssColourCodesWithColourInstance")
    void from_validCssColourCode_worksTest(final String cssCode, final Colour expectedColour) {
        assertThat(WebColour.from(cssCode)).isEqualTo(expectedColour);
    }

}