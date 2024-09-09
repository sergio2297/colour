package es.sfernandez.colour;

import es.sfernandez.colour.codifications.ColourCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.ColourCodeConverter;

import java.util.Objects;

public class Colour {

    //---- Attributes ----
    protected final ColourCode code;
    private final ColourCodeConverter converter;

    //---- Constructor ----
    public Colour(ColourCode code) {
        this(code, new ColourCodeConverter());
    }

    public Colour(ColourCode code, final ColourCodeConverter converter) {
        if(code == null)
            throw new IllegalArgumentException("Error. The " + ColourCode.class.getSimpleName() + " is required to " +
                    "create a " + getClass().getSimpleName() + ".");

        if(converter == null)
            throw new IllegalArgumentException("Error. The " + ColourCodeConverter.class.getSimpleName() + " is " +
                    "required to create a " + getClass().getSimpleName() + ".\n" +
                    "You can use the default constructor of " + getClass().getSimpleName() + " so the default converter " +
                    "will be assigned.");

        this.code = code;
        this.converter = converter;
    }

    //---- Methods ----
    public final <T extends ColourCode> T as(Class<T> colourCode) {
        return converter.convert(code, colourCode);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Colour colour)) return false;
        return Objects.equals(as(RgbCode.class), colour.as(RgbCode.class));
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(as(RgbCode.class));
    }
}
