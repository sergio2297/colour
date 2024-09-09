package es.sfernandez.colour.codifications;

import es.sfernandez.colour.utils.NumUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @param cyan value between [0, 1]
 * @param magenta value between [0, 1]
 * @param yellow value between [0, 1]
 */
public record CmykCode(float cyan, float magenta, float yellow, float black)
        implements ColourCode {

    //---- Constants y Definitions ----
    public static final Pattern codePattern = Pattern.compile("C(\\d{1,3})\\s+M(\\d{1,3})\\s+Y(\\d{1,3})\\s+K(\\d{1,3})");

    //---- Constructor ---
    public CmykCode {
        assertIsValid(cyan, "Cyan");
        assertIsValid(magenta, "Magenta");
        assertIsValid(yellow, "Yellow");
        assertIsValid(black, "Black");
    }

    public CmykCode(int cyan, int magenta, int yellow, int black) {
        this(normalize(cyan), normalize(magenta), normalize(yellow), normalize(black));
    }

    public CmykCode(String code) {
        this(extractCyanValueFromCode(code), extractMagentaValueFromCode(code), extractYellowValueFromCode(code), extractBlackValueFromCode(code));
    }

    private static int extractCyanValueFromCode(String code) {
        return Integer.parseInt(matchCode(code).group(1));
    }

    private static int extractMagentaValueFromCode(String code) {
        return Integer.parseInt(matchCode(code).group(2));
    }

    private static int extractYellowValueFromCode(String code) {
        return Integer.parseInt(matchCode(code).group(3));
    }

    private static int extractBlackValueFromCode(String code) {
        return Integer.parseInt(matchCode(code).group(4));
    }

    private static Matcher matchCode(String code) {
        if(code == null)
            throw new IllegalArgumentException("Code must not be null.");

        Matcher matcher = codePattern.matcher(code);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match CMYK code. (value='" + code + "')");

        return matcher;
    }

    private static void assertIsValid(float value, String propertyName) {
        if(isNotBetween0And1(value))
            throw new IllegalArgumentException(propertyName + " value is out of range [0.0, 1.0]. (value=" + value + ")");
    }

    private static boolean isNotBetween0And1(float value) {
        return value < 0.0f || value > 1.0f;
    }

    //---- Methods ----
    public String toCode() {
        return String.format("C%d M%d Y%d K%d", cyanPercentage(), magentaPercentage(), yellowPercentage(), blackPercentage());
    }

    public int cyanPercentage() {
        return denormalize(cyan);
    }

    public int magentaPercentage() {
        return denormalize(magenta);
    }

    public int yellowPercentage() {
        return denormalize(yellow);
    }

    public int blackPercentage() {
        return denormalize(black);
    }

    private static float normalize(int value) {
        return NumUtils.normalize(0, 100, value);
    }

    private int denormalize(final float value) {
        return NumUtils.denormalize(0, 100, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CmykCode cmykCode)) return false;

        return cyanPercentage() == cmykCode.cyanPercentage()
                && magentaPercentage() == cmykCode.magentaPercentage()
                && yellowPercentage() == cmykCode.yellowPercentage()
                && blackPercentage() == cmykCode.blackPercentage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cyanPercentage(), magentaPercentage(), yellowPercentage(), blackPercentage());
    }

    @Override
    public String toString() {
        return toCode();
    }
}
