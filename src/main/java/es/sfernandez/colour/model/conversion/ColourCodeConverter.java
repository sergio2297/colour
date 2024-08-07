package es.sfernandez.colour.model.conversion;

import es.sfernandez.colour.model.codifications.ColourCode;
import es.sfernandez.colour.model.conversion.hex.HexToRgbCodeConversion;
import es.sfernandez.colour.model.conversion.hex.RgbToHexCodeConversion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ColourCodeConverter {

    //---- Constants and Definitions ----
    public static final List<ColourCodeConversion<?,?>> DEFAULT_AVAILABLE_CONVERSIONS = List.of(
        new HexToRgbCodeConversion(), new RgbToHexCodeConversion() // HexCode
    );

    //---- Attributes ----
    private final List<ColourCodeConversion<?,?>> availableConversions;

    //---- Constructor ----
    public ColourCodeConverter() {
        this.availableConversions = DEFAULT_AVAILABLE_CONVERSIONS;
    }

    public ColourCodeConverter(final Iterable<ColourCodeConversion<?,?>> availableConversions) {
        if(availableConversions == null || !availableConversions.iterator().hasNext())
            throw new IllegalArgumentException("Error. You must provide a not empty iterable of available conversions");

        this.availableConversions = new ArrayList<>();
        availableConversions.forEach(this.availableConversions::add);
    }

    //---- Methods ----
    public Iterator<ColourCodeConversion<?,?>> availableConversions() {
        return availableConversions.iterator();
    }

    public <A extends ColourCode, B extends ColourCode> B convert(final A colourCode, final Class<B> targetColourCodeClass) {
        if(colourCode == null)
            return null;

        if(targetColourCodeClass == null)
            throw new IllegalArgumentException("Error. You must indicate the target ColourCode of the conversion.");

        if(colourCode.getClass().equals(targetColourCodeClass))
            return (B) colourCode;

        ColourCodeConversion<A, B> conversion = searchConversion(colourCode.getClass(), targetColourCodeClass);
        if(conversion == null)
            throw new IllegalArgumentException("Error. There isn't any available conversion that converts "
                    + colourCode.getClass().getSimpleName() + " to " + targetColourCodeClass.getSimpleName());

        return conversion.convert(colourCode);
    }

    private <A extends ColourCode, B extends ColourCode> ColourCodeConversion<A, B> searchConversion(
            Class<? extends ColourCode> inClass, Class<B> outClass) {
        for(ColourCodeConversion<?,?> conversion : availableConversions)
            if(conversion.inColourCodeClass().equals(inClass)
                    && conversion.outColourCodeClass().equals(outClass))
                return (ColourCodeConversion<A, B>) conversion;

        return null;
    }

    // TODO: Deep Convert for conversions that arent direct like Hex to Hsl

}
