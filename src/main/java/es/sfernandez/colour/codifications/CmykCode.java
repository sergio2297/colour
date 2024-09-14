package es.sfernandez.colour.codifications;

import es.sfernandez.colour.utils.NumUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>{@link CmykCode} represents colours based on the CMYK colour model.</p>
 *
 * @param cyan cyan value of the code (value between [0, 1])
 * @param magenta magenta value of the code (value between [0, 1])
 * @param yellow yellow value of the code (value between [0, 1])
 * @param black black value of the code (value between [0, 1])
 *
 * @see ColourCode
 * @see <a href="https://en.wikipedia.org/wiki/CMYK_color_model">CMYK color model in Wikipedia.</a>
 *
 * @author Sergio Fernández
 */
public record CmykCode(float cyan, float magenta, float yellow, float black)
        implements ColourCode {

    //---- Constants y Definitions ----
    /** {@link Pattern} that every CMYK representation must match */
    public static final Pattern CODE_PATTERN = Pattern.compile("C(\\d{1,3})\\s+M(\\d{1,3})\\s+Y(\\d{1,3})\\s+K(\\d{1,3})");

    //---- Constructor ---
    /**
     * <p>Creates a new {@link CmykCode}</p>
     * @param cyan property between [0, 1]
     * @param magenta property between [0, 1]
     * @param yellow property between [0, 1]
     * @param black property between [0, 1]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public CmykCode {
        assertIsValid(cyan, "Cyan");
        assertIsValid(magenta, "Magenta");
        assertIsValid(yellow, "Yellow");
        assertIsValid(black, "Black");
    }

    /**
     * <p>Creates a new {@link CmykCode}</p>
     * @param cyan property percentage between [0, 100]
     * @param magenta property percentage between [0, 100]
     * @param yellow property percentage between [0, 100]
     * @param black property percentage between [0, 100]
     * @throws IllegalArgumentException if any of the given property it's out of bounds
     */
    public CmykCode(int cyan, int magenta, int yellow, int black) {
        this(normalize(cyan), normalize(magenta), normalize(yellow), normalize(black));
    }

    /**
     * <p>Creates a new {@link CmykCode} from the given code.</p>
     * @param code a CMYK code representation (e.g.: "C25 M50 Y75 K100")
     * @throws IllegalArgumentException if code doesn't match {@link CmykCode#CODE_PATTERN} or any property in expression
     * it's out of bounds.
     */
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

        Matcher matcher = CODE_PATTERN.matcher(code);

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
    /**
     * @return its CMYK representation (e.g.: "C25 M50 Y75 K100")
     */
    public String toCode() {
        return String.format("C%d M%d Y%d K%d", cyanPercentage(), magentaPercentage(), yellowPercentage(), blackPercentage());
    }

    /**
     * @return the cyan percentage of the code (value between [0, 100])
     */
    public int cyanPercentage() {
        return denormalize(cyan);
    }

    /**
     * @return the magenta percentage of the code (value between [0, 100])
     */
    public int magentaPercentage() {
        return denormalize(magenta);
    }

    /**
     * @return the yellow percentage of the code (value between [0, 100])
     */
    public int yellowPercentage() {
        return denormalize(yellow);
    }

    /**
     * @return the black percentage of the code (value between [0, 100])
     */
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

    /**
     * @return the CMYK representation of this object (e.g.: "C25 M50 Y75 K100")
     */
    @Override
    public String toString() {
        return toCode();
    }
}
