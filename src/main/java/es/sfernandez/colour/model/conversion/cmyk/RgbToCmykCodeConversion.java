package es.sfernandez.colour.model.conversion.cmyk;

import es.sfernandez.colour.model.codifications.CmykCode;
import es.sfernandez.colour.model.codifications.RgbCode;
import es.sfernandez.colour.model.conversion.ColourCodeConversion;
import es.sfernandez.colour.model.utils.NumUtils;

import static es.sfernandez.colour.model.utils.NumUtils.max;
import static es.sfernandez.colour.model.utils.NumUtils.round;

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
