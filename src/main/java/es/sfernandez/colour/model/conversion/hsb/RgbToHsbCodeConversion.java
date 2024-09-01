package es.sfernandez.colour.model.conversion.hsb;

import es.sfernandez.colour.model.codifications.HsbCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

import static es.sfernandez.colour.model.utils.NumUtils.*;

public class RgbToHsbCodeConversion
        implements ColourCodeConversion<RgbCode, HsbCode> {

    @Override
    public Class<RgbCode> inColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public Class<HsbCode> outColourCodeClass() {
        return HsbCode.class;
    }

    @Override
    public HsbCode convert(RgbCode rgb) {
        float h = 0, s, b;
        float max = max(rgb.red(), rgb.green(), rgb.blue());
        float min = min(rgb.red(), rgb.green(), rgb.blue());
        float delta = max - min;

        if(delta > 0) {
            if(max == rgb.red()) {
                h = 60 * (((rgb.green() - rgb.blue()) / delta) % 6);
            } else if(max == rgb.green()) {
                h = 60 * (((rgb.blue() - rgb.red()) / delta) + 2);
            } else if(max == rgb.blue()) {
                h = 60 * (((rgb.red() - rgb.green()) / delta) + 4);
            }

            if(max > 0) {
                s = delta / max;
            } else {
                s = 0;
            }

            b = max;
        } else {
            h = 0;
            s = 0;
            b = max;
        }

        if(h < 0) {
            h = 360 + h;
        }

        s = denormalize(0, 100, s);
        b = denormalize(0, 100, b);
        
        return new HsbCode((int) h, (int) s, (int) b, rgb.alphaPercentage());
    }

}
