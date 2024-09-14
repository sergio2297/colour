package es.sfernandez.colour.conversion;

import es.sfernandez.colour.codifications.ColourCode;

/**
 * <p>{@link RuntimeException} specifically used for ColourCode conversions that can't be done.</p>
 *
 * @see ColourCode
 * @see ColourCodeConversion
 */
public class ColourCodeConversionUnreachableException extends RuntimeException {

    /**
     * <p>Creates a new ColourCodeConversionUnreachableException.</p>
     * @param inClass Class of the colour code that was trying to convert
     * @param targetColourClass ColourCode class that was the target of the conversion
     */
    public ColourCodeConversionUnreachableException(Class<? extends ColourCode> inClass, Class<? extends ColourCode> targetColourClass) {
        super("Error. It's not possible to convert given colour code of type " + inClass.getSimpleName() +
                " to " + targetColourClass.getSimpleName() + ".");
    }

}
