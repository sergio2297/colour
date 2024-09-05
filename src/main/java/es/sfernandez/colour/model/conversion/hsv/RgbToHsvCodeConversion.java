package es.sfernandez.colour.model.conversion.hsv;

import es.sfernandez.colour.model.codifications.HsvCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

import static es.sfernandez.colour.model.utils.NumUtils.*;

public class RgbToHsvCodeConversion
        implements ColourCodeConversion<RgbCode, HsvCode> {

    @Override
    public Class<RgbCode> inColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public Class<HsvCode> outColourCodeClass() {
        return HsvCode.class;
    }

    @Override
    public HsvCode convert(RgbCode rgb) {
        float h = 0, s, v;
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

            s = delta / max;

        } else {
            h = 0;
            s = 0;
        }

        v = max;

        if(h < 0) {
            h = 360 + h;
        }

        s = denormalize(0, 100, s);
        v = denormalize(0, 100, v);
        
        return new HsvCode((int) h, (int) s, (int) v, rgb.alphaPercentage());
    }

}
