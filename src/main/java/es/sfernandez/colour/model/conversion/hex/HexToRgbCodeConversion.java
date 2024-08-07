package es.sfernandez.colour.model.conversion.hex;

import es.sfernandez.colour.model.codifications.HexCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

public class HexToRgbCodeConversion
        implements ColourCodeConversion<HexCode, RgbCode> {

    @Override
    public Class<HexCode> inColourCodeClass() {
        return HexCode.class;
    }

    @Override
    public Class<RgbCode> outColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public RgbCode convert(HexCode codification) {
        return new RgbCode(
                extractRedFrom(codification),
                extractGreenFrom(codification),
                extractBlueFrom(codification)
        );
    }

    private int extractRedFrom(HexCode codification) {
        return castHexToInt(codification.value().substring(1, 3));
    }

    private int extractGreenFrom(HexCode codification) {
        return castHexToInt(codification.value().substring(3, 5));
    }

    private int extractBlueFrom(HexCode codification) {
        return castHexToInt(codification.value().substring(5, 7));
    }

    private int castHexToInt(String hexNumber) {
        return Integer.parseInt(hexNumber,16);
    }

}
