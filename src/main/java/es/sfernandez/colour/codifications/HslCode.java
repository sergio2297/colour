package es.sfernandez.colour.codifications;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static es.sfernandez.colour.utils.NumUtils.*;

/**
 * <p>{@link HslCode} represents colours based on the HSL colour model.</p>
 *
 * @param hue hue value of the code (value between [0, 1])
 * @param saturation saturation value of the code (value between [0, 1])
 * @param lightness lightness value of the code (value between [0, 1])
 * @param alpha alpha value of the code (value between [0, 1])
 *
 * @see AcceptedByCssColourCode
 * @see <a href="https://en.wikipedia.org/wiki/HSL_and_HSV">HSL color model in Wikipedia.</a>
 *
 * @author Sergio Fernández
 */
public record HslCode(float hue, float saturation, float lightness, float alpha)
        implements AcceptedByCssColourCode {

    //---- Constants and Definitions ----
    /** {@link Pattern} that every HSL CSS code must match */
    public static final Pattern CSS_CODE_PATTERN = Pattern.compile("hsl\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})%\\s*,\\s*(\\d{1,3})%\\s*\\)");
    /** {@link Pattern} that every HSL CSS code with transparency (HSLA) must match */
    public static final Pattern CSS_CODE_WITH_TRANSPARENCY_PATTERN = Pattern.compile("hsla\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})%\\s*,\\s*(\\d{1,3})%\\s*,\\s*(1|0(\\.\\d{1,2})?)\\s*\\)");

    //---- Constructor ----
    /**
     * <p>Creates a new {@link HslCode}.</p>
     * @param hue property between [0, 1]
     * @param saturation property between [0, 1]
     * @param lightness property between [0, 1]
     * @param alpha property between [0, 1]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HslCode {
        assertIsNormalizedValue(hue, "Hue");
        assertIsNormalizedValue(saturation, "Saturation");
        assertIsNormalizedValue(lightness, "Lightness");
        assertIsNormalizedValue(alpha, "Alpha");
    }

    /**
     * <p>Creates a new opaque {@link HslCode}.</p>
     * @param hue property between [0, 1]
     * @param saturation property between [0, 1]
     * @param lightness property between [0, 1]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HslCode(float hue, float saturation, float lightness) {
        this(hue, saturation, lightness, 1.0f);
    }

    /**
     * <p>Creates a new {@link HslCode}.</p>
     * @param hue property degrees between [0, 360]
     * @param saturation property percentage between [0, 100]
     * @param lightness property percentage between [0, 100]
     * @param alpha property percentage between [0, 100]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HslCode(int hue, int saturation, int lightness, int alpha) {
        this(normalizeDegrees(hue),
                normalizePercentage(saturation),
                normalizePercentage(lightness),
                normalizePercentage(alpha)
        );
    }

    /**
     * <p>Creates a new opaque {@link HslCode}.</p>
     * @param hue property degrees between [0, 360]
     * @param saturation property percentage between [0, 100]
     * @param lightness property percentage between [0, 100]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public HslCode(int hue, int saturation, int lightness) {
        this(hue, saturation, lightness, 100);
    }

    /**
     * <p>Creates a new {@link HslCode} from the given CSS code.</p>
     * @param cssCode an HSL (HSLA) code representation (e.g.: "hsl(300, 90, 20)")
     * @throws IllegalArgumentException if code doesn't match neither of {@link HslCode#CSS_CODE_PATTERN}
     * {@link HslCode#CSS_CODE_WITH_TRANSPARENCY_PATTERN} or any property in expression it's out of bounds.
     */
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
        if(cssCode == null)
            throw new IllegalArgumentException("Css code must not be null.");

        Matcher matcher = CSS_CODE_WITH_TRANSPARENCY_PATTERN.matcher(cssCode);

        if(!matcher.matches())
            return 100;
        else
            return denormalize(0, 100, Float.parseFloat(matcher.group(4)));
    }

    private static Matcher matchCssCode(String cssCode) {
        if(cssCode == null)
            throw new IllegalArgumentException("Css code must not be null.");

        Matcher matcher = CSS_CODE_WITH_TRANSPARENCY_PATTERN.matcher(cssCode);

        if(!matcher.matches())
            matcher = CSS_CODE_PATTERN.matcher(cssCode);

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
     * @return the lightness percentage of the code (value between [0, 100])
     */
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
