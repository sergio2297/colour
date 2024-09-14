package es.sfernandez.colour.conversion.cmyk;

import es.sfernandez.colour.codifications.CmykCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.ColourCodeConversion;

import static es.sfernandez.colour.utils.NumUtils.max;

/**
 * <p>{@link ColourCodeConversion} that converts a {@link RgbCode} into a {@link CmykCode}.</p>
 *
 * @author Sergio Fernández
 */
public class RgbToCmykCodeConversion
        implements ColourCodeConversion<RgbCode, CmykCode> {

    @Override
    public Class<RgbCode> inColourCodeClass() {
        return RgbCode.class;
    }

    @Override
    public Class<CmykCode> outColourCodeClass() {
        return CmykCode.class;
    }

    @Override
    public CmykCode convert(RgbCode rgb) {
        float k = 1.0f - max(rgb.red(), rgb.green(), rgb.blue());

        if(k == 1.0f)
            return new CmykCode(0f, 0f, 0f, k);

        float c = (1.0f - rgb.red() - k) / (1.0f - k);
        float m = (1.0f - rgb.green() - k) / (1.0f - k);
        float y = (1.0f - rgb.blue() - k) / (1.0f - k);

        return new CmykCode(c, m, y, k);
    }

}
