package es.sfernandez.colour.conversion.hex;

import es.sfernandez.colour.codifications.HexCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.ColourCodeConversion;

import static es.sfernandez.colour.utils.NumUtils.denormalize;

/**
 * <p>{@link ColourCodeConversion} that converts a {@link RgbCode} into an {@link HexCode}.</p>
 *
 * @author Sergio Fernández
 */
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

        if(opacity.equalsIgnoreCase("FF"))
            return new HexCode("#" + hexColour);
        else
            return new HexCode("#" + hexColour + opacity);
    }

    private String castIntToHex(int number) {
        return Integer.toHexString(number);
    }

}
