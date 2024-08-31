package es.sfernandez.colour.model.codifications;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        this.value = inferValueFrom(code);
    }

    private String inferValueFrom(String code) {
        String value = matchHexCode(code).group(1).toUpperCase();

        if(hasUnnecessaryExplicitOpacity(value))
            value = removeOpacity(value);

        return "#" + value;
    }

    private Matcher matchHexCode(String code) {
        Matcher matcher = hexCodePattern.matcher(code);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match an HexCode. (value='" + code + "')");

        return matcher;
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

    //---- Methods ----
    public String value() {
        return value;
    }

    @Override
    public String toCssCode() {
        return value;
    }

    @Override
    public String toString() {
        return toCssCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HexCode hexCode)) return false;
        return Objects.equals(value, hexCode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
