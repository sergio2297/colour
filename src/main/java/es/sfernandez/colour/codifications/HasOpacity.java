package es.sfernandez.colour.codifications;

import static es.sfernandez.colour.utils.NumUtils.denormalize;

/**
 * <p>Indicates that a {@link ColourCode} has a property that indicates de opacity/transparency of it.</p>
 *
 * @author Sergio Fernández
 */
public interface HasOpacity {

    /**
     * @return alpha value between [0, 1]
     */
    float alpha();

    /**
     * @return alpha value as percentage (between [0, 100])
     */
    default int alphaPercentage() {
        return denormalize(0, 100, alpha());
    }

    /**
     * @return true if alpha value is 1
     */
    default boolean isOpaque() {
        return alpha() == 1.0f;
    }

}
