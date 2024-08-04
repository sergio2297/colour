package es.sfernandez.color.model;

public record HsbCode(float hue, float saturation, float brightness)
        implements ColourCode {

    //---- Constructor ----
    public HsbCode {
        assertIsValid(hue, "Hue");
        assertIsValid(saturation, "Saturation");
        assertIsValid(brightness, "Brightness");
    }

    public HsbCode(int hue, int saturation, int brightness) {
        this(normalizeDegrees(hue, "Hue"), normalizePercentage(saturation, "Saturation"), normalizePercentage(brightness, "Brightness"));
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
    public int hueDegrees() {
        return normalizeToDegrees(hue);
    }

    public int saturationPercentage() {
        return normalizeToPercentage(saturation);
    }

    public int brightnessPercentage() {
        return normalizeToPercentage(brightness);
    }

    private int normalizeToDegrees(final float value) {
        return (int) (value * 360);
    }

    private int normalizeToPercentage(final float value) {
        return (int) (value * 100);
    }

}
