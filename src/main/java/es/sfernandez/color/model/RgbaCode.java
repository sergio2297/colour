package es.sfernandez.color.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RgbaCode(RgbCode rgb, float alpha)
        implements AcceptedByCssColourCode {

    //---- Constants and Definitions ----
    public static final Pattern cssCodePattern = Pattern.compile("rgba\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(1|0(\\.\\d{1,2})?)\\s*\\)");

    //---- Constructor ----
    public RgbaCode {
        if(rgb == null)
            throw new IllegalArgumentException("You must specify an RGB code. It can be null");

        if(isNotBetween0And1(alpha))
            throw new IllegalArgumentException("Alpha value is out of range [0.0, 1.0]. (value=" + alpha + ")");
    }

    public RgbaCode(float red, float green, float blue, float alpha) {
        this(new RgbCode(red, green, blue), alpha);
    }

    public RgbaCode(RgbCode rgb, int alpha) {
        this(rgb, normalize(alpha));
    }

    public RgbaCode(int red, int green, int blue, int alpha) {
        this(new RgbCode(red, green, blue), normalize(alpha));
    }

    public RgbaCode(String cssCode) {
        this(extractRgbFromCssCode(cssCode), extractAlphaFromCssCode(cssCode));
    }

    private static RgbCode extractRgbFromCssCode(String cssCode) {
        Matcher matcher = matchCssCode(cssCode);
        return new RgbCode(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3))
        );
    }

    private static float extractAlphaFromCssCode(String cssCode) {
        return Float.parseFloat(matchCssCode(cssCode).group(4));
    }

    private static Matcher matchCssCode(String cssCode) {
        Matcher matcher = cssCodePattern.matcher(cssCode);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match CSS rgba code. (value='" + cssCode + "')");

        return matcher;
    }

    private static boolean isNotBetween0And1(float value) {
        return value < 0.0f || value > 1.0f;
    }

    private static float normalize(int alpha) {
        if(isNotBetween0And100(alpha))
            throw new IllegalArgumentException("Alpha value is out of range [0, 100]. (value=" + alpha + ")");

        return alpha / 100.0f;
    }

    private static boolean isNotBetween0And100(int value) {
        return value < 0 || value > 100;
    }

    @Override
    public String toCssCode() {
        return String.format("rgba(%d, %d, %d, %s)", rgb.red255(), rgb.green255(), rgb.blue255(), alpha);
    }
}
