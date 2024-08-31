package es.sfernandez.colour.model.conversion.hex;

import es.sfernandez.colour.model.codifications.HexCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

import static es.sfernandez.colour.model.utils.NumUtils.denormalize;
import static es.sfernandez.colour.model.utils.NumUtils.normalize;

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
        if(!hexCode.hasExplicitOpacity())
            return 100;

        int opacity = hexCode.isSimplified()
            ? castHexToInt(hexCode.value().substring(4, 5).repeat(2))
            : castHexToInt(hexCode.value().substring(7, 9));
        
        return denormalize(0, 100, 
                normalize(0, 255, opacity)
        );
    }

    private int castHexToInt(String hexNumber) {
        return Integer.parseInt(hexNumber,16);
    }

}
