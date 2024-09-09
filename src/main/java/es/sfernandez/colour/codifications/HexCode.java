package es.sfernandez.colour.codifications;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static es.sfernandez.colour.utils.NumUtils.castHexToInt;
import static es.sfernandez.colour.utils.NumUtils.normalize;

public final class HexCode
        implements AcceptedByCssColourCode {

    //---- Constants and Definitions ----
    public static final Pattern hexCodePattern = Pattern.compile("#?([0-9a-fA-F]{8}|[0-9a-fA-F]{6}|[0-9a-fA-F]{4}|[0-9a-fA-F]{3})");

    //---- Attributes ----
    private final String value;

    //---- Constructor ----
    public HexCode(final String code) {
        if(code == null || code.isBlank())
            throw new IllegalArgumentException("Can not create an HexCode with null/blank value.");

        this.value = extractValueFrom(code);
    }

    private String extractValueFrom(String code) {
        return matchHexCode(code).group(1).toUpperCase();
    }

    private Matcher matchHexCode(String code) {
        Matcher matcher = hexCodePattern.matcher(code);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match an HexCode. (value='" + code + "')");

        return matcher;
    }

    //---- Methods ----
    public String value() {
        return "#" + value;
    }

    @Override
    public String toCssCode() {
        return value();
    }

    @Override
    public String toString() {
        return toCssCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HexCode hexCode)) return false;

        return Objects.equals(
                reduceAsMuchAsPossible(value),
                reduceAsMuchAsPossible(hexCode.value)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reduceAsMuchAsPossible(value));
    }

    private String reduceAsMuchAsPossible(final String hexCode) {
        String value = hexCode;

        if(hasUnnecessaryExplicitOpacity(value))
            value = removeOpacity(value);

        if(canBeSimplified(value))
            value = simplify(value);

        return value;
    }

    public boolean isSimplified() {
        return value.length() == 3 || value.length() == 4;
    }

    private boolean canBeSimplified(String value) {
        if(value.length() == 6 || value.length() == 8) {
            for(int i = 0; i < value.length(); i+=2)
                if(value.charAt(i) != value.charAt(i +1))
                    return false;

            return true;
        }

        return false;
    }

    private String simplify(String value) {
        StringBuilder simplifiedValue = new StringBuilder();

        for(int i = 0; i < value.length(); i+=2)
            simplifiedValue.append(value.charAt(i));

        return simplifiedValue.toString();
    }

    @Override
    public float alpha() {
        return hasExplicitOpacity()
                ? getOpacityFromHexCode()
                : 1.0f;
    }

    private float getOpacityFromHexCode() {
        int opacity = isSimplified()
                ? castHexToInt(value.substring(3, 4).repeat(2))
                : castHexToInt(value.substring(6, 8));

        return normalize(0, 255, opacity);
    }

    public boolean hasExplicitOpacity() {
        return value.length() == 4 || value.length() == 8;
    }

    private boolean hasUnnecessaryExplicitOpacity(String value) {
        return (value.length() == 4 && value.charAt(3) == 'F')
                || (value.length() == 8 && value.endsWith("FF"));
    }

    private String removeOpacity(String value) {
        return value.length() == 4
                ? value.substring(0, 3)
                : value.substring(0, 6);
    }
}
