package es.sfernandez.colour.conversion.hsv;

import es.sfernandez.colour.codifications.HsvCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.ColourCodeConversion;

import static es.sfernandez.colour.utils.NumUtils.*;

/**
 * <p>{@link ColourCodeConversion} that converts a {@link RgbCode} into an {@link HsvCode}.</p>
 *
 * @author Sergio Fernández
 */
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
