package es.sfernandez.colour.codifications;

import static es.sfernandez.colour.utils.NumUtils.denormalize;

public interface HasOpacity {

    float alpha();

    default int alphaPercentage() {
        return denormalize(0, 100, alpha());
    }

    default boolean isOpaque() {
        return alpha() == 1.0f;
    }

}
