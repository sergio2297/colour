package es.sfernandez.colour.model.conversion.hsl;

import es.sfernandez.colour.model.codifications.HslCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

import static es.sfernandez.colour.model.utils.NumUtils.*;

public class HslToRgbCodeConversion
        implements ColourCodeConversion<HslCode, RgbCode> {

    @Override
    public Class<HslCode> inColourCodeClass() {
        return HslCode.class;
    }

    @Override
    public Class<RgbCode> outColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public RgbCode convert(HslCode hsl) {
        float r = 0, g = 0, b = 0;
        int h = hsl.hueDegrees();
        float c = (1f - Math.abs(2f * hsl.lightness() - 1f)) * hsl.saturation();
        float x = c * (1f - Math.abs((h / 60f) % 2f - 1f));
        float m = hsl.lightness() - c / 2f;

        if (0 <= h && h < 60) {
            r = c; g = x; b = 0;
        } else if (60 <= h && h < 120) {
            r = x; g = c; b = 0;
        } else if (120 <= h && h < 180) {
            r = 0; g = c; b = x;
        } else if (180 <= h && h < 240) {
            r = 0; g = x; b = c;
        } else if (240 <= h && h < 300) {
            r = x; g = 0; b = c;
        } else if (300 <= h && h < 360) {
            r = c; g = 0; b = x;
        }

        r = denormalize(0, 255, r + m);
        g = denormalize(0, 255, g + m);
        b = denormalize(0, 255, b + m);

        return new RgbCode((int) r, (int) g, (int) b, hsl.alphaPercentage());
    }

}
