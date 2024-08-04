package es.sfernandez.color.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record HslaCode(HslCode hsl, float alpha)
        implements AcceptedByCssColourCode {

    //---- Constants and Definitions ----
    public static final Pattern cssCodePattern = Pattern.compile("hsla\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})%\\s*,\\s*(\\d{1,3})%\\s*,\\s*(1|0(\\.\\d{1,2})?)\\s*\\)");

    //---- Constructor ----
    public HslaCode {
        if(hsl == null)
            throw new IllegalArgumentException("You must specify an HSL code. It can be null");

        if(isNotBetween0And1(alpha))
            throw new IllegalArgumentException("Alpha value is out of range [0.0, 1.0]. (value=" + alpha + ")");
    }

    public HslaCode(float hue, float saturation, float lightness, float alpha) {
        this(new HslCode(hue, saturation, lightness), alpha);
    }

    public HslaCode(HslCode hsl, int alpha) {
        this(hsl, normalize(alpha));
    }

    public HslaCode(int hue, int saturation, int lightness, int alpha) {
        this(new HslCode(hue, saturation, lightness), normalize(alpha));
    }

    public HslaCode(String cssCode) {
        this(extractHslFromCssCode(cssCode), extractAlphaFromCssCode(cssCode));
    }

    private static HslCode extractHslFromCssCode(String cssCode) {
        Matcher matcher = matchCssCode(cssCode);
        return new HslCode(
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
            throw new IllegalArgumentException("Given expression doesn't match CSS hsla code. (value='" + cssCode + "')");

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
        return String.format("hsla(%d, %d%%, %d%%, %s)", hsl.hueDegrees(), hsl.saturationPercentage(), hsl.lightnessPercentage(), alpha);
    }
}
