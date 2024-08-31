package es.sfernandez.colour.model.conversion.hsl;

import es.sfernandez.colour.model.codifications.HslCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;

import static es.sfernandez.colour.model.utils.NumUtils.*;

public class RgbToHslCodeConversion
        implements ColourCodeConversion<RgbCode, HslCode> {

    @Override
    public Class<RgbCode> inColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public Class<HslCode> outColourCodeClass() {
        return HslCode.class;
    }

    @Override
    public HslCode convert(RgbCode rgb) {
        float h, s, l;

        float max = max(rgb.red(), rgb.green(), rgb.blue());
        float min = min(rgb.red(), rgb.green(), rgb.blue());
        float delta = max - min;

        h = calculateHue(rgb, max, delta);
        s = calculateSaturation(max, min, delta);
        l = calculateLightness(max, min);

        return new HslCode((int) h, (int) s, (int) l, rgb.alphaPercentage());
    }

    private int calculateHue(RgbCode rgb, float max, float delta) {
        float h;

        if(delta == 0)
            h = 0;
        else if(max == rgb.red())
            h = ((rgb.green() - rgb.blue()) / delta) % 6f;
        else if(max == rgb.green())
            h = (rgb.blue()) - rgb.red() / delta + 2f;
        else
            h = (rgb.red() - rgb.green()) / delta + 4f;

        h = round(h * 60f, 0);

        if(h < 0)
            h += 360;

        return (int) h;
    }

    private int calculateSaturation(float max, float min, float delta) {
        float l = (max + min) / 2f;
        float s = delta == 0
                ? 0f
                : delta / (1 - Math.abs(2f * l - 1f));

        return denormalize(0, 100, s);
    }

    private int calculateLightness(float max, float min) {
        float l = (max + min) / 2f;

        return denormalize(0, 100, l);
    }

}
