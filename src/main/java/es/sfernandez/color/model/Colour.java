package es.sfernandez.color.model;

public class Colour {

    //---- Constants and Definitions ----

    record HexCode(String code) {}

    record HsbCode(float hue, float saturation, float brightness) {}
    record HslCode(float hue, float saturation, float lightness) {}
    record CmykCode(float cyan, float magenta, float yellow, float key) {}
    record PantoneCode(float code) {}

    //---- Attributes ----
    private final RgbCode rgb;

    //---- Constructor ----
    public Colour(ColourCode code) {
        rgb = (RgbCode) code;
    }

    //---- Methods ----

}
