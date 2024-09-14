package es.sfernandez.colour;

import es.sfernandez.colour.codifications.ColourCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.ColourCodeConverter;

import java.util.Objects;

/**
 * <p>A Colour is the representation of a colour as a concept and it can be created from any {@link ColourCode}.
 * This means that two Colours are equal iff they represent the same colour, no matter what codification they use.</p>
 *
 * @see ColourCode
 *
 * @author Sergio Fernández
 */
public class Colour {

    //---- Attributes ----
    protected final ColourCode code;
    private final ColourCodeConverter converter;

    //---- Constructor ----
    /**
     * <p>Creates a new Colour.</p>
     * @param code ColourCode
     */
    public Colour(ColourCode code) {
        this(code, new ColourCodeConverter());
    }

    /**
     * <p>Creates a new Colour with the given converter.</p>
     * <p>Converter is necessary to perform "castings" between different ColourCodes. If it's not specified it will use
     * the default {@link ColourCodeConverter}.</p>
     * @param code ColourCode
     * @param converter {@link ColourCodeConverter} that will be used to cast the colour to different codifications.
     * @throws IllegalArgumentException if code or converter are null
     */
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
    /**
     * <p>Returns the representation of this colour in the given {@link ColourCode}.</p>
     * <p>To achieve that, it uses the {@link ColourCodeConverter} passed during construction. So, if the converter can't
     * perform the conversion an Exception will be thrown.</p>
     * @param colourCode target ColourCode class
     * @return this Colour with the given codification
     * @param <T> ColourCode to convert
     */
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
