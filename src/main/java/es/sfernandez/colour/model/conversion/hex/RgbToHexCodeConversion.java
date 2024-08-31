package es.sfernandez.colour.model.conversion.hex;

import es.sfernandez.colour.model.codifications.HexCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

import static es.sfernandez.colour.model.utils.NumUtils.denormalize;

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
    public HexCode convert(RgbCode rgbCode) {
        int rgb = ((denormalize(0, 255, rgbCode.alpha()) & 0xFF) << 24) |
                ((rgbCode.red255() & 0xFF) << 16) |
                ((rgbCode.green255() & 0xFF) << 8) |
                ((rgbCode.blue255() & 0xFF));

        String opacity = castIntToHex(rgb).substring(0, 2);
        String hexColour = castIntToHex(rgb).substring(2, 8);

        if(opacity.equals("FF"))
            return new HexCode("#" + hexColour);
        else
            return new HexCode("#" + hexColour + opacity);
    }

    private String castIntToHex(int number) {
        return Integer.toHexString(number);
    }

}
