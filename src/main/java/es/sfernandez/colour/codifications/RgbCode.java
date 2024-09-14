package es.sfernandez.colour.codifications;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static es.sfernandez.colour.utils.NumUtils.*;

/**
 * <p>{@link RgbCode} represents colours based on the RGB colour model.</p>
 *
 * @param red red value of the code (value between [0, 1])
 * @param green green value of the code (value between [0, 1])
 * @param blue blue value of the code (value between [0, 1])
 * @param alpha alpha value of the code (value between [0, 1])
 *
 * @see AcceptedByCssColourCode
 * @see <a href="https://en.wikipedia.org/wiki/RGB_color_model">RGB color model in Wikipedia.</a>
 *
 * @author Sergio Fernández
 */
public record RgbCode(float red, float green, float blue, float alpha)
        implements AcceptedByCssColourCode {

    //---- Constants y Definitions ----
    /** {@link Pattern} that every RGB CSS code must match */
    public static final Pattern CSS_CODE_PATTERN = Pattern.compile("rgb\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)");
    /** {@link Pattern} that every RGB CSS code with transparency (RGBA) must match */
    public static final Pattern CSS_CODE_WITH_TRANSPARENCY_PATTERN = Pattern.compile("rgba\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(1|0(\\.\\d{1,2})?)\\s*\\)");

    //---- Constructor ---
    /**
     * <p>Creates a new {@link RgbCode}.</p>
     * @param red property between [0, 1]
     * @param green property between [0, 1]
     * @param blue property between [0, 1]
     * @param alpha property between [0, 1]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public RgbCode {
        assertIsNormalizedValue(red, "Red");
        assertIsNormalizedValue(green, "Green");
        assertIsNormalizedValue(blue, "Blue");
        assertIsNormalizedValue(alpha, "Alpha");
    }

    /**
     * <p>Creates a new opaque {@link RgbCode}.</p>
     * @param red property between [0, 1]
     * @param green property between [0, 1]
     * @param blue property between [0, 1]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public RgbCode(float red, float green, float blue) {
        this(red, green, blue, 1.0f);
    }

    /**
     * <p>Creates a new {@link RgbCode}.</p>
     * @param red property between [0, 255]
     * @param green property between [0, 255]
     * @param blue property between [0, 255]
     * @param alpha property between [0, 100]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public RgbCode(int red, int green, int blue, int alpha) {
        this(normalize255(red),
                normalize255(green),
                normalize255(blue),
                normalizePercentage(alpha)
        );
    }

    /**
     * <p>Creates a new opaque {@link RgbCode}.</p>
     * @param red property between [0, 255]
     * @param green property between [0, 255]
     * @param blue property between [0, 255]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public RgbCode(int red, int green, int blue) {
        this(red, green, blue, 100);
    }

    /**
     * <p>Creates a new {@link RgbCode} from the given CSS code.</p>
     * @param cssCode an RGB (RGBA) code representation (e.g.: "rgb(30, 90, 220)")
     * @throws IllegalArgumentException if code doesn't match neither of {@link RgbCode#CSS_CODE_PATTERN}
     * {@link RgbCode#CSS_CODE_WITH_TRANSPARENCY_PATTERN} or any property in expression it's out of bounds.
     */
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
            throw new IllegalArgumentException("Given expression doesn't match CSS rgb code. (value='" + cssCode + "')");

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

    /**
     * @return the red property of the code (value between [0, 255])
     */
    public int red255() {
        return denormalize255(red);
    }

    /**
     * @return the green property of the code (value between [0, 255])
     */
    public int green255() {
        return denormalize255(green);
    }

    /**
     * @return the blue property of the code (value between [0, 255])
     */
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
