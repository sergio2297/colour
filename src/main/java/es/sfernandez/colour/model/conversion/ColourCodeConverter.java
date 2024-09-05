package es.sfernandez.colour.model.conversion;

import es.sfernandez.colour.model.codifications.ColourCode;
import es.sfernandez.colour.model.conversion.cmyk.CmykToRgbCodeConversion;
import es.sfernandez.colour.model.conversion.cmyk.RgbToCmykCodeConversion;
import es.sfernandez.colour.model.conversion.hex.HexToRgbCodeConversion;
import es.sfernandez.colour.model.conversion.hex.RgbToHexCodeConversion;
import es.sfernandez.colour.model.conversion.hsv.HsvToRgbCodeConversion;
import es.sfernandez.colour.model.conversion.hsv.RgbToHsvCodeConversion;
import es.sfernandez.colour.model.conversion.hsl.HslToRgbCodeConversion;
import es.sfernandez.colour.model.conversion.hsl.RgbToHslCodeConversion;

import java.util.*;

public class ColourCodeConverter {

    //---- Constants and Definitions ----
    public static final List<ColourCodeConversion<?,?>> DEFAULT_AVAILABLE_CONVERSIONS = List.of(
        new HexToRgbCodeConversion(), new RgbToHexCodeConversion(), // HexCode
        new CmykToRgbCodeConversion(), new RgbToCmykCodeConversion(), // CmykCode
        new HslToRgbCodeConversion(), new RgbToHslCodeConversion(), // HslCode
        new HsvToRgbCodeConversion(), new RgbToHsvCodeConversion() // HsvCode
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

        ColourCodeConversion<A, B> conversion = (ColourCodeConversion<A, B>) searchConversion(colourCode.getClass(), targetColourCodeClass);
        if(conversion == null)
            throw new IllegalArgumentException("Error. There isn't any available conversion that converts "
                    + colourCode.getClass().getSimpleName() + " to " + targetColourCodeClass.getSimpleName());

        return conversion.convert(colourCode);
    }

    private <A extends ColourCode, B extends ColourCode> ColourCodeConversion<A, B> searchConversion(
            Class<A> inClass, Class<B> outClass) {
        ColourCodeConversion<A, B> directConversion = searchDirectConversion(inClass, outClass);

        return directConversion != null
                ? directConversion
                : searchDeepConversion(inClass, outClass, new HashSet<>());
    }

    private <A extends ColourCode, B extends ColourCode> ColourCodeConversion<A, B> searchDirectConversion(
            Class<A> inClass, Class<B> outClass) {
        for(ColourCodeConversion<?,?> conversion : availableConversions)
            if(conversion.inColourCodeClass().equals(inClass)
                    && conversion.outColourCodeClass().equals(outClass))
                return (ColourCodeConversion<A, B>) conversion;

        return null;
    }

    private <A extends ColourCode, B extends ColourCode> ColourCodeConversion<A, B> searchDeepConversion(
            Class<A> inClass, Class<B> outClass, Set<Class<ColourCode>> searchedInputs) {

        for(ColourCodeConversion<?,?> conversion : availableConversions) {
            if(searchedInputs.contains(conversion.outColourCodeClass())) // Avoid infinite loops
                continue;

            if (conversion.inColourCodeClass().equals(inClass)) {
                if(conversion.outColourCodeClass().equals(outClass)) {
                    return (ColourCodeConversion<A, B>) conversion;
                } else {
                    searchedInputs.add((Class<ColourCode>) conversion.inColourCodeClass());
                    ColourCodeConversion<?, B> middleConversion = searchDeepConversion(conversion.outColourCodeClass(), outClass, searchedInputs);
                    if(middleConversion != null)
                        return ((ColourCodeConversion) conversion).andThen(middleConversion);
                    else
                        searchedInputs.remove(conversion.inColourCodeClass());
                }
            }
        }

        return null;
    }

}
