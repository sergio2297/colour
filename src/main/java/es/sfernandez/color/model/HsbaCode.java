package es.sfernandez.color.model;

public record HsbaCode(HsbCode hsb, float alpha)
        implements ColourCode {

    //---- Constructor ----
    public HsbaCode {
        if(hsb == null)
            throw new IllegalArgumentException("You must specify an HSB code. It can be null");

        if(isNotBetween0And1(alpha))
            throw new IllegalArgumentException("Alpha value is out of range [0.0, 1.0]. (value=" + alpha + ")");
    }

    public HsbaCode(float hue, float saturation, float brightness, float alpha) {
        this(new HsbCode(hue, saturation, brightness), alpha);
    }

    public HsbaCode(HsbCode hsb, int alpha) {
        this(hsb, normalize(alpha));
    }

    public HsbaCode(int hue, int saturation, int brightness, int alpha) {
        this(new HsbCode(hue, saturation, brightness), normalize(alpha));
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

}
