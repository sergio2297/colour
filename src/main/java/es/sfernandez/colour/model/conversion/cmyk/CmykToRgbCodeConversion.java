package es.sfernandez.colour.model.conversion.cmyk;

import es.sfernandez.colour.model.codifications.CmykCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

public class CmykToRgbCodeConversion
        implements ColourCodeConversion<CmykCode, RgbCode> {

    @Override
    public Class<CmykCode> inColourCodeClass() {
        return CmykCode.class;
    }

    @Override
    public Class<RgbCode> outColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public RgbCode convert(CmykCode cmyk) {
        float red = (1.0f - cmyk.cyan()) * (1.0f - cmyk.black());
        float green = (1.0f - cmyk.magenta()) * (1.0f - cmyk.black());
        float blue = (1.0f - cmyk.yellow()) * (1.0f - cmyk.black());

        return new RgbCode(red, green, blue);
    }

}
