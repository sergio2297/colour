package es.sfernandez.colour.model.codifications;

import java.util.Objects;

import static es.sfernandez.colour.model.utils.NumUtils.*;
import static es.sfernandez.colour.model.utils.NumUtils.denormalize;

public record HsbCode(float hue, float saturation, float brightness, float alpha)
        implements ColourCode, HasOpacity {

    //---- Constructor ----
    public HsbCode {
        assertIsNormalizedValue(hue, "Hue");
        assertIsNormalizedValue(saturation, "Saturation");
        assertIsNormalizedValue(brightness, "Brightness");
        assertIsNormalizedValue(alpha, "Alpha");
    }

    public HsbCode(float hue, float saturation, float brightness) {
        this(hue, saturation, brightness, 1.0f);
    }

    public HsbCode(int hue, int saturation, int brightness, int alpha) {
        this(normalizeDegrees(hue),
                normalizePercentage(saturation),
                normalizePercentage(brightness),
                normalizePercentage(alpha)
        );
    }

    public HsbCode(int hue, int saturation, int brightness) {
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
        if (!(o instanceof HsbCode hsbCode)) return false;

        return hueDegrees() == hsbCode.hueDegrees()
                && saturationPercentage() == hsbCode.saturationPercentage()
                && brightnessPercentage() == hsbCode.brightnessPercentage()
                && alphaPercentage() == hsbCode.alphaPercentage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(hueDegrees(), saturationPercentage(), brightnessPercentage(), alphaPercentage());
    }

    public int hueDegrees() {
        return denormalizeDegrees(hue);
    }

    public int saturationPercentage() {
        return denormalizePercentage(saturation);
    }

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
