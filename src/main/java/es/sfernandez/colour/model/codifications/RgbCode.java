package es.sfernandez.colour.model.codifications;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static es.sfernandez.colour.model.utils.NumUtils.denormalize;
import static es.sfernandez.colour.model.utils.NumUtils.normalize;

/**
 *
 * @param red value between [0, 1]
 * @param green value between [0, 1]
 * @param blue value between [0, 1]
 */
public record RgbCode(float red, float green, float blue, float alpha)
        implements AcceptedByCssColourCode, HasOpacity {

    //---- Constants y Definitions ----
    public static final Pattern cssCodePattern = Pattern.compile("rgb\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)");
    public static final Pattern cssCodeWithTransparencyPattern = Pattern.compile("rgba\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(1|0(\\.\\d{1,2})?)\\s*\\)");

    //---- Constructor ---
    public RgbCode {
        assertIsNormalizedValue(red, "Red");
        assertIsNormalizedValue(green, "Green");
        assertIsNormalizedValue(blue, "Blue");
        assertIsNormalizedValue(alpha, "Alpha");
    }

    public RgbCode(float red, float green, float blue) {
        this(red, green, blue, 1.0f);
    }

    public RgbCode(int red, int green, int blue, int alpha) {
        this(normalize255(red),
                normalize255(green),
                normalize255(blue),
                normalizePercentage(alpha)
        );
    }

    public RgbCode(int red, int green, int blue) {
        this(red, green, blue, 100);
    }

    public RgbCode(String cssCode) {
        this(
                extractRedValueFromCssCode(cssCode),
                extractGreenValueFromCssCode(cssCode),
                extractBlueValueFromCssCode(cssCode),
                extractAlphaValueFromCssCode(cssCode)
        );
    }

    private static int extractRedValueFromCssCode(String cssCode) {
        return Integer.parseInt(matchCssCode(cssCode).group(1));
    }

    private static int extractGreenValueFromCssCode(String cssCode) {
        return Integer.parseInt(matchCssCode(cssCode).group(2));
    }

    private static int extractBlueValueFromCssCode(String cssCode) {
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
            throw new IllegalArgumentException("Given expression doesn't match CSS rgb code. (value='" + cssCode + "')");

        return matcher;
    }

    private static void assertIsNormalizedValue(float value, String propertyName) {
        if(isNotBetween0And1(value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0.0, 1.0]. (value=" + value + ")");
    }

    private static boolean isNotBetween0And1(float value) {
        return value < 0.0f || value > 1.0f;
    }

    //---- Methods ----
    @Override
    public String toCssCode() {
        if(isOpaque())
            return String.format("rgb(%d, %d, %d)", red255(), green255(), blue255());
        else
            return String.format(Locale.US, "rgba(%d, %d, %d, %.2f)", red255(), green255(), blue255(), alpha());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RgbCode rgbCode)) return false;

        return red255() == rgbCode.red255()
                && green255() == rgbCode.green255()
                && blue255() == rgbCode.blue255()
                && alphaPercentage() == rgbCode.alphaPercentage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(red255(), green255(), blue255(), alphaPercentage());
    }

    public int red255() {
        return denormalize255(red);
    }

    public int green255() {
        return denormalize255(green);
    }

    public int blue255() {
        return denormalize255(blue);
    }

    private static float normalize255(int value) {
        return normalize(0, 255, value);
    }

    private static int denormalize255(final float value) {
        return denormalize(0, 255, value);
    }

    private static float normalizePercentage(int value) {
        return normalize(0, 100, value);
    }

}
