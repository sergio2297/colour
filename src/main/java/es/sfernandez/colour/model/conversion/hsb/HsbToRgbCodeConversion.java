package es.sfernandez.colour.model.conversion.hsb;

import es.sfernandez.colour.model.codifications.HsbCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

public class HsbToRgbCodeConversion
        implements ColourCodeConversion<HsbCode, RgbCode> {

    @Override
    public Class<HsbCode> inColourCodeClass() {
        return HsbCode.class;
    }

    @Override
    public Class<RgbCode> outColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public RgbCode convert(HsbCode hsb) {
        float r = 0, g = 0, b = 0;

        float chroma = hsb.brightness() * hsb.saturation();
        float segment = (hsb.hueDegrees() / 60f) % 6f;
        float fX = chroma * (1f - Math.abs(segment % 2f - 1f));
        float fM = hsb.brightness() - chroma;

        if(0 <= segment && segment < 1) {
            r = chroma;
            g = fX;
            b = 0;
        } else if(1 <= segment && segment < 2) {
            r = fX;
            g = chroma;
            b = 0;
        } else if(2 <= segment && segment < 3) {
            r = 0;
            g = chroma;
            b = fX;
        } else if(3 <= segment && segment < 4) {
            r = 0;
            g = fX;
            b = chroma;
        } else if(4 <= segment && segment < 5) {
            r = fX;
            g = 0;
            b = chroma;
        } else if(5 <= segment && segment < 6) {
            r = chroma;
            g = 0;
            b = fX;
        }

        r += fM;
        g += fM;
        b += fM;

        return new RgbCode(r, g, b, hsb.alpha());
    }

}
