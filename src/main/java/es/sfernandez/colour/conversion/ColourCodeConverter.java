package es.sfernandez.colour.conversion;

import es.sfernandez.colour.codifications.ColourCode;
import es.sfernandez.colour.conversion.cmyk.CmykToRgbCodeConversion;
import es.sfernandez.colour.conversion.cmyk.RgbToCmykCodeConversion;
import es.sfernandez.colour.conversion.hex.HexToRgbCodeConversion;
import es.sfernandez.colour.conversion.hex.RgbToHexCodeConversion;
import es.sfernandez.colour.conversion.hsv.HsvToRgbCodeConversion;
import es.sfernandez.colour.conversion.hsv.RgbToHsvCodeConversion;
import es.sfernandez.colour.conversion.hsl.HslToRgbCodeConversion;
import es.sfernandez.colour.conversion.hsl.RgbToHslCodeConversion;

import java.util.*;

/**
 * <p>A {@link ColourCodeConverter} performs the conversion from one {@link ColourCode} to another given ColourCode type.</p>
 * <p>To achieve that, it uses a set of {@link ColourCodeConversion} that can be specified during construction, if not it will
 * use {@link ColourCodeConverter#DEFAULT_AVAILABLE_CONVERSIONS}.</p>
 * <p>The algorithm that it uses to convert one ColourCode A to another B search in the available conversions the one which
 * can convert directly from A to B. If it can't be found, the converter will try to find another path to achieve the
 * conversion to type B, using 1..n middle conversions.</p>
 *
 * @see ColourCodeConversion
 *
 * @author Sergio Fernández
 */
public class ColourCodeConverter {

    //---- Constants and Definitions ----
    /**
     * <p>Unmodifiable list with the default available {@link ColourCodeConversion}.</p>
     * <p>It contains one conversion algorithm between every ColourCode defined in this library and
     * the {@link es.sfernandez.colour.codifications.RgbCode} (one in each direction). Allowing the converter
     * to convert any pair of two {@link ColourCode} in a maximum of two steps (A -> RGB -> B)</p>
     */
    public static final List<ColourCodeConversion<?,?>> DEFAULT_AVAILABLE_CONVERSIONS = List.of(
        new HexToRgbCodeConversion(), new RgbToHexCodeConversion(), // HexCode
        new CmykToRgbCodeConversion(), new RgbToCmykCodeConversion(), // CmykCode
        new HslToRgbCodeConversion(), new RgbToHslCodeConversion(), // HslCode
        new HsvToRgbCodeConversion(), new RgbToHsvCodeConversion() // HsvCode
    );

    //---- Attributes ----
    private final List<ColourCodeConversion<?,?>> availableConversions;

    //---- Constructor ----
    /**
     * <p>Creates a new {@link ColourCodeConverter} that uses the {@link ColourCodeConverter#DEFAULT_AVAILABLE_CONVERSIONS}.</p>
     */
    public ColourCodeConverter() {
        this.availableConversions = DEFAULT_AVAILABLE_CONVERSIONS;
    }

    /**
     * <p>Creates a new {@link ColourCodeConverter} that will use the available conversions passed as argument.</p>
     * @param availableConversions collection of available conversions
     * @throws IllegalArgumentException if available conversions is null or is empty
     * @implNote adding new conversions to availableConversions passed as argument (e.g.: if it's a List) after the
     * creation of the converter, will not have any effect on the converter's available conversions.
     */
    public ColourCodeConverter(final Iterable<ColourCodeConversion<?,?>> availableConversions) {
        if(availableConversions == null || !availableConversions.iterator().hasNext())
            throw new IllegalArgumentException("Error. You must provide a not empty iterable of available conversions");

        this.availableConversions = new ArrayList<>();
        availableConversions.forEach(this.availableConversions::add);
    }

    //---- Methods ----
    /**
     * @return an {@link Iterator} with all available conversions
     */
    public Iterator<ColourCodeConversion<?,?>> availableConversions() {
        return availableConversions.iterator();
    }

    /**
     * <p>Converts colourCode to targetColourCodeClass by searching in the available conversions the one which
     * can perform the conversion directly. If it can't be found, the converter will try to find another path to achieve the
     * conversion to targetColourCodeClass, using 1..n middle conversions.</p>
     * @param colourCode {@link ColourCode} to convert
     * @param targetColourCodeClass {@link ColourCode} type target of the conversion
     * @return <ul>
     *     <li>The corresponding codification of colourCode in targetColourCodeClass.</li>
     *     <li><code>null</code> if colourCode is null.</li>
     *     <li><code>colourCode</code> if targetColourCodeClass is equal to colourCode.class.</li>
     * </ul>
     * @param <A> ColourCode type to convert
     * @param <B> ColourCode type target of the conversion
     * @throws IllegalArgumentException if targetColourCodeClass is null or if it's not possible to find any path to convert
     * colourCode to targetColourCodeClass
     */
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
