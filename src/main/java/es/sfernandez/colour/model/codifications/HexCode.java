package es.sfernandez.colour.model.codifications;

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
        Matcher matcher = matchHexCode(code);

        return "#" + matcher.group(1).toUpperCase();
    }

    private Matcher matchHexCode(String code) {
        Matcher matcher = hexCodePattern.matcher(code);

        if(!matcher.matches())
            throw new IllegalArgumentException("Given expression doesn't match an HexCode. (value='" + code + "')");

        return matcher;
    }

    //---- Methods ----
    public String value() {
        return value;
    }

    @Override
    public String toCssCode() {
        return value;
    }

}
