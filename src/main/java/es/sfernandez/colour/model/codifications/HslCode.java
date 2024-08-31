package es.sfernandez.colour.model.codifications;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static es.sfernandez.colour.model.utils.NumUtils.*;

public record HslCode(float hue, float saturation, float lightness, float alpha)
        implements AcceptedByCssColourCode {

    //---- Constants and Definitions ----
    public static final Pattern cssCodePattern = Pattern.compile("hsl\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})%\\s*,\\s*(\\d{1,3})%\\s*\\)");
    public static final Pattern cssCodeWithTransparencyPattern = Pattern.compile("hsla\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})%\\s*,\\s*(\\d{1,3})%\\s*,\\s*(1|0(\\.\\d{1,2})?)\\s*\\)");

    //---- Constructor ----
    public HslCode {
        assertIsNormalizedValue(hue, "Hue");
        assertIsNormalizedValue(saturation, "Saturation");
        assertIsNormalizedValue(lightness, "Lightness");
        assertIsNormalizedValue(alpha, "Alpha");
    }

    public HslCode(float hue, float saturation, float lightness) {
        this(hue, saturation, lightness, 1.0f);
    }

    public HslCode(int hue, int saturation, int lightness, int alpha) {
        this(normalizeDegrees(hue),
                normalizePercentage(saturation),
                normalizePercentage(lightness),
                normalizePercentage(alpha)
        );
    }

    public HslCode(int hue, int saturation, int lightness) {
        this(hue, saturation, lightness, 100);
    }

    public HslCode(String cssCode) {
        this(
                extractHueValueFromCssCode(cssCode),
                extractSaturationValueFromCssCode(cssCode),
                extractLightnessValueFromCssCode(cssCode),
                extractAlphaValueFromCssCode(cssCode)
        );
    }

    private static int extractHueValueFromCssCode(String cssCode) {
        return Integer.parseInt(matchCssCode(cssCode).group(1));
    }

    private static int extractSaturationValueFromCssCode(String cssCode) {
        return Integer.parseInt(matchCssCode(cssCode).group(2));
    }

    private static int extractLightnessValueFromCssCode(String cssCode) {
        return Integer.parseInt(matchCssCode(cssCode).group(3));
    }

    private static int extractAlphaValueFromCssCode(String cssCode) {
        Matcher matcher = cssCodeWithTransparencyPattern.matcher(cssCode);

        if(!matcher.matches())
            return 100;
        else
            return denormalize(0, 100, Float.parseFloat(matcher.group(4)));
    }

    private static Matcher matchCssCode(String cssCode) {
        Matcher matcher = cssCodeWithTransparencyPattern.matcher(cssCode);

        if(!matcher.matches())
            matcher = cssCodePattern.matcher(cssCode);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match CSS hsl code. (value='" + cssCode + "')");

        return matcher;
    }

    private static void assertIsNormalizedValue(float value, String propertyName) {
        if(isNotBetween(0f, 1f, value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0.0, 1.0]. (value=" + value + ")");
    }

    //---- Methods ----
    @Override
    public String toCssCode() {
        if(isOpaque())
            return String.format("hsl(%d, %d%%, %d%%)", hueDegrees(), saturationPercentage(), lightnessPercentage());
        else
            return String.format(Locale.US, "hsla(%d, %d%%, %d%%, %.2f)", hueDegrees(), saturationPercentage(), lightnessPercentage(), alpha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HslCode hslCode)) return false;

        return hueDegrees() == hslCode.hueDegrees()
                && saturationPercentage() == hslCode.saturationPercentage()
                && lightnessPercentage() == hslCode.lightnessPercentage()
                && alphaPercentage() == hslCode.alphaPercentage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(hueDegrees(), saturationPercentage(), lightnessPercentage(), alphaPercentage());
    }

    public int hueDegrees() {
        return denormalizeDegrees(hue);
    }

    public int saturationPercentage() {
        return denormalizePercentage(saturation);
    }

    public int lightnessPercentage() {
        return denormalizePercentage(lightness);
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
