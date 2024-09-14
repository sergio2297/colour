package es.sfernandez.colour.codifications;

import java.util.Objects;

import static es.sfernandez.colour.utils.NumUtils.*;
import static es.sfernandez.colour.utils.NumUtils.denormalize;

/**
 * <p>{@link HsvCode} represents colours based on the HSV colour model.</p>
 *
 * @param hue hue value of the code (value between [0, 1])
 * @param saturation saturation value of the code (value between [0, 1])
 * @param brightness brightness value of the code (value between [0, 1])
 * @param alpha alpha value of the code (value between [0, 1])
 *
 * @see ColourCode
 * @see HasOpacity
 * @see <a href="https://en.wikipedia.org/wiki/HSL_and_HSV">HSV color model in Wikipedia.</a>
 *
 * @author Sergio Fernández
 */
public record HsvCode(float hue, float saturation, float brightness, float alpha)
        implements ColourCode, HasOpacity {

    //---- Constructor ----
    /**
     * <p>Creates a new {@link HsvCode}.</p>
     * @param hue property between [0, 1]
     * @param saturation property between [0, 1]
     * @param brightness property between [0, 1]
     * @param alpha property between [0, 1]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HsvCode {
        assertIsNormalizedValue(hue, "Hue");
        assertIsNormalizedValue(saturation, "Saturation");
        assertIsNormalizedValue(brightness, "Brightness");
        assertIsNormalizedValue(alpha, "Alpha");
    }

    /**
     * <p>Creates a new opaque {@link HsvCode}.</p>
     * @param hue property between [0, 1]
     * @param saturation property between [0, 1]
     * @param brightness property between [0, 1]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HsvCode(float hue, float saturation, float brightness) {
        this(hue, saturation, brightness, 1.0f);
    }

    /**
     * <p>Creates a new {@link HslCode}.</p>
     * @param hue property degrees between [0, 360]
     * @param saturation property percentage between [0, 100]
     * @param brightness property percentage between [0, 100]
     * @param alpha property percentage between [0, 100]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HsvCode(int hue, int saturation, int brightness, int alpha) {
        this(normalizeDegrees(hue),
                normalizePercentage(saturation),
                normalizePercentage(brightness),
                normalizePercentage(alpha)
        );
    }

    /**
     * <p>Creates a new opaque {@link HslCode}.</p>
     * @param hue property degrees between [0, 360]
     * @param saturation property percentage between [0, 100]
     * @param brightness property percentage between [0, 100]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HsvCode(int hue, int saturation, int brightness) {
        this(hue, saturation, brightness, 100);
    }

    private static void assertIsNormalizedValue(float value, String propertyName) {
        if(isNotBetween(0f, 1f, value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0.0, 1.0]. (value=" + value + ")");
    }

    //---- Methods ----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HsvCode hsvCode)) return false;

        return hueDegrees() == hsvCode.hueDegrees()
                && saturationPercentage() == hsvCode.saturationPercentage()
                && brightnessPercentage() == hsvCode.brightnessPercentage()
                && alphaPercentage() == hsvCode.alphaPercentage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(hueDegrees(), saturationPercentage(), brightnessPercentage(), alphaPercentage());
    }

    /**
     * @return the hue degrees of the code (value between [0, 360])
     */
    public int hueDegrees() {
        return denormalizeDegrees(hue);
    }

    /**
     * @return the saturation percentage of the code (value between [0, 100])
     */
    public int saturationPercentage() {
        return denormalizePercentage(saturation);
    }

    /**
     * @return the brightness percentage of the code (value between [0, 100])
     */
    public int brightnessPercentage() {
        return denormalizePercentage(brightness);
    }

    private static float normalizeDegrees(int value) {
        return normalize(0, 360, value);
    }

    private static float normalizePercentage(int value) {
        return normalize(0, 100, value);
    }

    private int denormalizeDegrees(final float value) {
        return denormalize(0, 360, value);
    }

    private int denormalizePercentage(final float value) {
        return denormalize(0, 100, value);
    }

}
