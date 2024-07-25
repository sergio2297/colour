package es.sfernandez.color.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @param red value between [0, 1]
 * @param green value between [0, 1]
 * @param blue value between [0, 1]
 */
public record RgbCode(float red, float green, float blue)
        implements AcceptedByCssColourCode {

    //---- Constants y Definitions ----
    public static final Pattern cssCodePattern = Pattern.compile("rgb\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)");

    //---- Constructor ---
    public RgbCode {
        assertIsValid(red, "Red");
        assertIsValid(green, "Green");
        assertIsValid(blue, "Blue");
    }

    public RgbCode(int red, int green, int blue) {
        this(normalize(red, "Red"), normalize(green, "Green"), normalize(blue, "Blue"));
    }

    public RgbCode(String cssCode) {
        this(extractRedValueFromCssCode(cssCode), extractGreenValueFromCssCode(cssCode), extractBlueValueFromCssCode(cssCode));
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

    private static Matcher matchCssCode(String cssCode) {
        Matcher matcher = cssCodePattern.matcher(cssCode);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match CSS rgb code. (value='" + cssCode + "')");

        return matcher;
    }

    private static void assertIsValid(float value, String propertyName) {
        if(isNotBetween0And1(value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0.0, 1.0]. (value=" + value + ")");
    }

    private static boolean isNotBetween0And1(float value) {
        return value < 0.0f || value > 1.0f;
    }

    private static float normalize(int value, String propertyName) {
        if(isNotBetween0And255(value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0, 255]. (value=" + value + ")");

        return value / 255.0f;
    }

    private static boolean isNotBetween0And255(int value) {
        return value < 0 || value > 255;
    }

    //---- Methods ----
    @Override
    public String toCssCode() {
        return String.format("rgb(%d, %d, %d)", red255(), green255(), blue255());
    }

    public int red255() {
        return normalizeTo255(red);
    }

    public int green255() {
        return normalizeTo255(green);
    }

    public int blue255() {
        return normalizeTo255(blue);
    }

    private int normalizeTo255(final float value) {
        return (int) (value * 255);
    }

}
