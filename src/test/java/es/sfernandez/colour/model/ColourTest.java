package es.sfernandez.colour.model;

import es.sfernandez.colour.model.codifications.HexCode;
import es.sfernandez.colour.model.codifications.HslCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;
import es.sfernandez.colour.model.conversion.ColourCodeConverter;
import es.sfernandez.colour.model.conversion.fixtures.HexCodeFixtures;
import es.sfernandez.colour.model.conversion.fixtures.HslCodeFixtures;
import es.sfernandez.colour.model.conversion.fixtures.RgbCodeFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ColourTest {

    //---- Fixtures ----
    private final HexCode greenHexCode = new HexCodeFixtures().green();
    private final RgbCode redRgbCode = new RgbCodeFixtures().red();
    private final RgbCode greenRgbCode = new RgbCodeFixtures().green();

    //---- Tests ----
    @Test
    void create_withNullColourCode_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new Colour(null));
    }

    @Test
    void create_withNullColourCodeConverter_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new Colour(new RgbCode(0, 0, 0), null));
    }

    @Test
    void getAs_theSameTypeOfColourCodePassedInCreation_returnsTheSameInstanceTest() {
        Colour colour = new Colour(greenHexCode);

        HexCode hexCode = colour.as(HexCode.class);

        assertThat(hexCode).isSameAs(greenHexCode);
    }

    @Test
    void getAs_theSameTypeOfColourCodePassedInCreation_returnsTheSameInstance_noMatterIfConverterDoesNotAcceptTypeTest() {
        ColourCodeConverter converter = new ColourCodeConverter(List.of(ColourCodeConversion.identity(RgbCode.class)));
        Colour colour = new Colour(greenHexCode, converter);

        HexCode hexCode = colour.as(HexCode.class);

        assertThat(hexCode).isSameAs(greenHexCode);
    }

    @Test
    void getAs_otherTypeAcceptedByTheColourCodeConverter_returnsCurrentCodeConversionTest() {
        Colour colour = new Colour(greenHexCode);

        HslCode hslCode = colour.as(HslCode.class);

        assertThat(hslCode).isEqualTo(new HslCodeFixtures().green());
    }

    @Test
    void getAs_otherTypeNotAcceptedByTheColourCodeConverter_throwsExceptionTest() {
        ColourCodeConverter converter = new ColourCodeConverter(List.of(ColourCodeConversion.identity(RgbCode.class)));
        Colour colour = new Colour(greenHexCode, converter);

        assertThrows(Exception.class, () -> colour.as(HslCode.class));
    }

    // This is to keep opacity iff the original colour has it
    @Test
    void afterGetAs_currentColourCodeIsNotUpdatedTest() {
        Colour colour = new Colour(greenHexCode);

        colour.as(RgbCode.class);

        assertThat(colour.as(HexCode.class)).isSameAs(greenHexCode);
    }

    @Test
    void coloursAreDifferent_ifTheyRepresentDifferentColoursInRgbTest() {
        Colour colourGreen = new Colour(greenHexCode);
        Colour colourRed = new Colour(redRgbCode);

        assertThat(colourGreen).isNotEqualTo(colourRed);
    }

    @Test
    void coloursAreEqual_iffTheyRepresentTheSameColourTest() {
        Colour colourGreenAsHexCode = new Colour(greenHexCode);
        Colour colourGreenAsRgbCode = new Colour(greenRgbCode);

        assertThat(colourGreenAsHexCode).isEqualTo(colourGreenAsRgbCode);
    }

    @Test
    void coloursAreEqual_iffTheyRepresentTheSameColour_noMattersWhatConverterTheyHaveTest() {
        Colour colourGreenAsHexCode = new Colour(greenHexCode);

        ColourCodeConverter converter = new ColourCodeConverter(List.of(ColourCodeConversion.identity(RgbCode.class)));
        Colour colourGreenAsRgbCode = new Colour(greenRgbCode, converter);

        assertThat(colourGreenAsHexCode).isEqualTo(colourGreenAsRgbCode);
    }

    @Test
    void coloursHaveSameHashCode_iffTheyAreEqualTest() {
        Colour colourGreenAsHexCode = new Colour(greenHexCode);
        Colour colourGreenAsRgbCode = new Colour(greenRgbCode);
        Colour colourRedAsRgbCode = new Colour(redRgbCode);

        assertThat(colourGreenAsHexCode.hashCode()).isEqualTo(colourGreenAsRgbCode.hashCode());
        assertThat(colourGreenAsHexCode.hashCode()).isNotEqualTo(colourRedAsRgbCode.hashCode());
    }

}