package es.sfernandez.colour.model.conversion.hex;

import es.sfernandez.colour.model.codifications.HexCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

public class RgbToHexCodeConversion
        implements ColourCodeConversion<RgbCode, HexCode> {

    @Override
    public Class<RgbCode> inColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public Class<HexCode> outColourCodeClass() {
        return HexCode.class;
    }

    @Override
    public HexCode convert(RgbCode codification) {
        return new HexCode("#"
                + extractRedFrom(codification)
                + extractGreenFrom(codification)
                + extractBlueFrom(codification)
        );
    }

    private String extractRedFrom(RgbCode codification) {
        return castIntToHex(codification.red255());
    }

    private String extractGreenFrom(RgbCode codification) {
        return castIntToHex(codification.green255());
    }

    private String extractBlueFrom(RgbCode codification) {
        return castIntToHex(codification.blue255());
    }

    private String castIntToHex(int number) {
        return String.format("%02x", number);
    }

}
