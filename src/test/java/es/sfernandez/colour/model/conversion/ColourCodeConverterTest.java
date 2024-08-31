package es.sfernandez.colour.model.conversion;

import es.sfernandez.colour.model.codifications.AcceptedByCssColourCode;
import es.sfernandez.colour.model.codifications.ColourCode;
import es.sfernandez.colour.model.codifications.HexCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.fixtures.ColourCodeFixtures;
import es.sfernandez.colour.model.conversion.fixtures.HexCodeFixtures;
import es.sfernandez.colour.model.conversion.fixtures.RgbCodeFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColourCodeConverterTest {

    //---- Constants and Definitions ----
    public record TestCase<IN extends ColourCode, OUT extends ColourCode>(IN input, OUT expected) {

        public String name() {
            return representationOf(input) + " -> " + representationOf(expected);
        }

        private String representationOf(final ColourCode colourCode) {
            if(colourCode instanceof AcceptedByCssColourCode cssCode)
                return cssCode.toCssCode();
            else
                return colourCode.toString();
        }

    }

    //---- Attributes ----
    private final ColourCodeConverter converter = new ColourCodeConverter();

    //---- Fixtures ----
    private static final ColourCodeFixtures<?> rgbColourCodeFixture = new RgbCodeFixtures();
    private static final ColourCodeFixtures<?>[] notRgbColourCodeFixtures = {
        new HexCodeFixtures()
    };

    static Stream<Arguments> eachColourCodeConversionsToItselfTestCases() {
        List<Arguments> arguments = new ArrayList<>(
                createTestCasesOf(rgbColourCodeFixture, rgbColourCodeFixture)
        );

        for (ColourCodeFixtures<?> colourCodeFixture : notRgbColourCodeFixtures)
            arguments.addAll(createTestCasesOf(colourCodeFixture, colourCodeFixture));

        return arguments.stream();
    }

    static Stream<Arguments> directConversionsBetweenDefaultColourCodesAndRgbTestCases() {
        List<Arguments> arguments = new ArrayList<>();

        for (ColourCodeFixtures<?> colourCodeFixture : notRgbColourCodeFixtures) {
            arguments.addAll(createTestCasesOf(rgbColourCodeFixture, colourCodeFixture));
            arguments.addAll(createTestCasesOf(colourCodeFixture, rgbColourCodeFixture));
        }

        return arguments.stream();
    }

    static Stream<Arguments> deepConversionsBetweenAllColourCodesTestCases() {
        List<Arguments> arguments = new ArrayList<>();

        for(int i = 0; i < notRgbColourCodeFixtures.length - 1; ++i) {
            for(int j = i + 1; j < notRgbColourCodeFixtures.length; ++j) {
                arguments.addAll(createTestCasesOf(notRgbColourCodeFixtures[i], notRgbColourCodeFixtures[j]));
                arguments.addAll(createTestCasesOf(notRgbColourCodeFixtures[j], notRgbColourCodeFixtures[i]));
            }
        }

        return arguments.stream();
    }

    private static Collection<Arguments> createTestCasesOf(ColourCodeFixtures<?> fixture1, ColourCodeFixtures<?> fixture2) {
        return Stream.of(
                new TestCase<>(fixture1.black(), fixture2.black()),
                new TestCase<>(fixture1.white(), fixture2.white()),
                new TestCase<>(fixture1.grey(), fixture2.grey()),

                new TestCase<>(fixture1.red(), fixture2.red()),
                new TestCase<>(fixture1.green(), fixture2.green()),
                new TestCase<>(fixture1.blue(), fixture2.blue()),

                new TestCase<>(fixture1.cyan(), fixture2.cyan()),
                new TestCase<>(fixture1.magenta(), fixture2.magenta()),
                new TestCase<>(fixture1.yellow(), fixture2.yellow())
        ).map(testCase -> Arguments.of(testCase.name(), testCase))
        .toList();
    }

    //---- Tests ----
    @Test
    void create_usingDefaultConstructor_setsDefaultAvailableConversionsTest() {
        ColourCodeConverter converter = new ColourCodeConverter();

        assertThat(converter.availableConversions()).toIterable()
                .containsExactlyInAnyOrderElementsOf(ColourCodeConverter.DEFAULT_AVAILABLE_CONVERSIONS);
    }

    @Test
    void create_withoutAvailableConversions_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new ColourCodeConverter(null));
        assertThrows(IllegalArgumentException.class, () -> new ColourCodeConverter(new ArrayList<>()));
    }

    @Test
    void changeAvailableConversions_fromOutside_wontAffectAvailableConversionsInsideTest() {
        List<ColourCodeConversion<?,?>> availableConversions = new ArrayList<>();
        availableConversions.add(ColourCodeConversion.identity(HexCode.class));
        ColourCodeConverter converter = new ColourCodeConverter(availableConversions);
        assertThat(converter.availableConversions()).toIterable()
                .containsExactlyInAnyOrderElementsOf(availableConversions);

        availableConversions.clear();

        assertThat(converter.availableConversions()).toIterable()
                .isNotEmpty();
    }

    @Test
    void convert_nullColourCode_returnsNullTest() {
        assertThat(converter.convert(null, ColourCode.class)).isNull();
    }

    @Test
    void convertColourCode_withoutSpecifying_targetColourCodeClass_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(new RgbCode(0,0,0), null));
    }

    @Test
    void convertColourCodeOfNotAvailableSource_toAvailableTargetColourCodeClass_throwsIllegalArgumentExceptionTest() {
        ColourCodeConverter converter = new ColourCodeConverter(
                List.of(ColourCodeConversion.identity(HexCode.class))
        );

        assertThrows(IllegalArgumentException.class, () -> converter.convert(new RgbCode(0,0,0), HexCode.class));
    }

    @Test
    void convertColourCode_toNotAvailableTargetColourCodeClass_throwsIllegalArgumentExceptionTest() {
        ColourCodeConverter converter = new ColourCodeConverter(
                List.of(ColourCodeConversion.identity(HexCode.class))
        );

        assertThrows(IllegalArgumentException.class, () -> converter.convert(new HexCode("#000000"), RgbCode.class));
    }

    @Test
    void convertColourCode_toItsOwnColourCodeClass_doesNotNeedAnySpecialColourCodeConversionTest() {
        RgbCode anRgbCode = new RgbCode(0,0,0);
        ColourCodeConverter converter = new ColourCodeConverter(
                List.of(ColourCodeConversion.identity(HexCode.class))
        );

        RgbCode conversion = converter.convert(anRgbCode, RgbCode.class);

        assertThat(conversion).isEqualTo(anRgbCode);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("eachColourCodeConversionsToItselfTestCases")
    <IN extends ColourCode, OUT extends ColourCode>
    void conversion_fromEachColourCode_toItself_workTest(String name, TestCase<IN, OUT> testCase) {
        ColourCode out = converter.convert(testCase.input, testCase.expected.getClass());

        assertThat(out).isEqualTo(testCase.expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("directConversionsBetweenDefaultColourCodesAndRgbTestCases")
    <IN extends ColourCode, OUT extends ColourCode>
    void directConversion_betweenAvailableColourCodes_and_Rgb_workTest(String name, TestCase<IN, OUT> testCase) {
        ColourCode out = converter.convert(testCase.input, testCase.expected.getClass());

        assertThat(out).isEqualTo(testCase.expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("directConversionsBetweenDefaultColourCodesAndRgbTestCases")
    <IN extends ColourCode, OUT extends ColourCode>
    void directConversion_betweenAvailableColourCodes_and_Rgb_canBeInvertedTest(String name, TestCase<IN, OUT> testCase) {
        ColourCode out = converter.convert(testCase.input, testCase.expected.getClass());
        ColourCode reconvertedInput = converter.convert(out, testCase.input.getClass());

        assertThat(reconvertedInput).isEqualTo(testCase.input);
    }

//    @ParameterizedTest(name = "{0}")
//    @MethodSource("deepConversionsBetweenAllColourCodesTestCases")
//    <IN extends ColourCode, OUT extends ColourCode>
//    void deepConversion_betweenAllColourCodesAvailable_workTest(String name, TestCase<IN, OUT> testCase) {
//        ColourCode out = converter.convert(testCase.input, testCase.expected.getClass());
//
//        assertThat(out).isEqualTo(testCase.expected);
//     }

//    @ParameterizedTest(name = "{0}")
//    @MethodSource("deepConversionsBetweenAllColourCodesTestCases")
//    <IN extends ColourCode, OUT extends ColourCode>
//    void deepConversion_betweenAllColourCodesAvailable_canBeInvertedTest(String name, TestCase<IN, OUT> testCase) {
//        ColourCode out = converter.convert(testCase.input, testCase.expected.getClass());
//        ColourCode reconvertedInput = converter.convert(out, testCase.input.getClass());
//
//        assertThat(reconvertedInput).isEqualTo(testCase.input);
//     }

        assertThat(out).isEqualTo(testCase.expected);
    }

}
