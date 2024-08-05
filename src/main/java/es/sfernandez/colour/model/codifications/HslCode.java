package es.sfernandez.colour.model.codifications;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record HslCode(float hue, float saturation, float lightness)
        implements AcceptedByCssColourCode {

    //---- Constants and Definitions ----
    public static final Pattern cssCodePattern = Pattern.compile("hsl\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})%\\s*,\\s*(\\d{1,3})%\\s*\\)");

    //---- Constructor ----
    public HslCode {
        assertIsValid(hue, "Hue");
        assertIsValid(saturation, "Saturation");
        assertIsValid(lightness, "Lightness");
    }

    public HslCode(int hue, int saturation, int lightness) {
        this(normalizeDegrees(hue, "Hue"), normalizePercentage(saturation, "Saturation"), normalizePercentage(lightness, "Lightness"));
    }

    public HslCode(String cssCode) {
        this(extractHueValueFromCssCode(cssCode), extractSaturationValueFromCssCode(cssCode), extractLightnessValueFromCssCode(cssCode));
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

    private static Matcher matchCssCode(String cssCode) {
        Matcher matcher = cssCodePattern.matcher(cssCode);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match CSS hsl code. (value='" + cssCode + "')");

        return matcher;
    }

    private static void assertIsValid(float value, String propertyName) {
        if(isNotBetween0And1(value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0.0, 1.0]. (value=" + value + ")");
    }

    private static boolean isNotBetween0And1(float value) {
        return value < 0.0f || value > 1.0f;
    }

    private static float normalizeDegrees(int value, String propertyName) {
        if(isNotBetween0And360(value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0, 360]. (value=" + value + ")");

        return value / 360.0f;
    }

    private static boolean isNotBetween0And360(int value) {
        return value < 0 || value > 360;
    }

    private static float normalizePercentage(int value, String propertyName) {
        if(isNotBetween0And100(value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0, 100]. (value=" + value + ")");

        return value / 100.0f;
    }

    private static boolean isNotBetween0And100(int value) {
        return value < 0 || value > 100;
    }

    //---- Methods ----
    @Override
    public String toCssCode() {
        return String.format("hsl(%d, %d%%, %d%%)", hueDegrees(), saturationPercentage(), lightnessPercentage());
    }

    public int hueDegrees() {
        return normalizeToDegrees(hue);
    }

    public int saturationPercentage() {
        return normalizeToPercentage(saturation);
    }

    public int lightnessPercentage() {
        return normalizeToPercentage(lightness);
    }

    private int normalizeToDegrees(final float value) {
        return (int) (value * 360);
    }

    private int normalizeToPercentage(final float value) {
        return (int) (value * 100);
    }

}
