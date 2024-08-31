package es.sfernandez.colour.model.conversion.hex;

import es.sfernandez.colour.model.codifications.HexCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

import static es.sfernandez.colour.model.utils.NumUtils.castHexToInt;
import static es.sfernandez.colour.model.utils.NumUtils.denormalize;

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
                extractBlueFrom(codification),
                extractAlphaFrom(codification)
        );
    }

    private int extractRedFrom(HexCode hexCode) {
        if(hexCode.isSimplified())
            return castHexToInt(hexCode.value().substring(1, 2).repeat(2));
        else
            return castHexToInt(hexCode.value().substring(1, 3));
    }

    private int extractGreenFrom(HexCode hexCode) {
        if(hexCode.isSimplified())
            return castHexToInt(hexCode.value().substring(2, 3).repeat(2));
        else
            return castHexToInt(hexCode.value().substring(3, 5));
    }

    private int extractBlueFrom(HexCode hexCode) {
        if(hexCode.isSimplified())
            return castHexToInt(hexCode.value().substring(3, 4).repeat(2));
        else
            return castHexToInt(hexCode.value().substring(5, 7));
    }

    private int extractAlphaFrom(HexCode hexCode) {
        return denormalize(0, 100, hexCode.alpha());
    }

}
