package es.sfernandez.colour;

import es.sfernandez.colour.codifications.AcceptedByCssColourCode;
import es.sfernandez.colour.codifications.HexCode;
import es.sfernandez.colour.codifications.HslCode;
import es.sfernandez.colour.codifications.RgbCode;
import es.sfernandez.colour.conversion.ColourCodeConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * <p>WebColour is a Colour that accept exclusively {@link AcceptedByCssColourCode}.</p>
 *
 * @see Colour
 * @see AcceptedByCssColourCode
 *
 * @author Sergio Fernández
 */
public class WebColour
        extends Colour {

    //---- Constructor ----
    /**
     * <p>Creates a new WebColour.</p>
     * @param code ColourCode
     */
    public WebColour(AcceptedByCssColourCode code) {
        super(code);
    }

    /**
     * <p>Creates a new WebColour with the given converter.</p>
     * @param code ColourCode
     * @param converter {@link ColourCodeConverter} that will be used to cast the colour to different codifications.
     */
    public WebColour(AcceptedByCssColourCode code, ColourCodeConverter converter) {
        super(code, converter);
    }

    //---- Methods ----
    /**
     * @return the CSS code of this {@link WebColour}
     */
    public String toCssCode() {
        return ((AcceptedByCssColourCode) code).toCssCode();
    }

    /**
     * <p>Checks if the given param is a valid CSS colour code and returns true if it is.</p>
     * @param cssColourCode param to check
     * @return true if the given param is a valid CSS colour code, false otherwise
     */
    public static boolean isCssColourCode(final String cssColourCode) {
        return colourCodeFrom(cssColourCode).isPresent();
    }

    /**
     * <p>Creates a new WebColour from the given CSS colour code.</p>
     * @param cssColourCode CSS colour code
     * @return a new WebColour created from the given CSS colour code
     * @throws IllegalArgumentException if the given cssColourCode isn't a valid CSS code ({@link WebColour#isCssColourCode(String)})
     */
    public static WebColour from(final String cssColourCode) {
        return colourCodeFrom(cssColourCode)
                .map(WebColour::new)
                .orElseThrow(() -> new IllegalArgumentException("Error. '" + cssColourCode + "' is not a valid CSS colour code."));
    }

    private static Optional<AcceptedByCssColourCode> colourCodeFrom(final String cssColourCode) {
        for(Class<? extends AcceptedByCssColourCode> clazz : acceptedByCssColourCodeClasses()) {
            try {
                return Optional.of(clazz.getConstructor(String.class).newInstance(cssColourCode));
            } catch (Exception ex) {
                // If exception comes from invocation, and it is an IllegalArgumentException, it means that the
                // cssColourCode is not accepted by CSS. Otherwise, class won't be correctly developed
                if(!(ex instanceof InvocationTargetException invocationException)
                        || !(invocationException.getTargetException() instanceof IllegalArgumentException))
                    throw new RuntimeException(ex);
            }
        }

        return Optional.empty();
    }

    private static Iterable<Class<? extends AcceptedByCssColourCode>> acceptedByCssColourCodeClasses() {
        return List.of(
                RgbCode.class, HexCode.class, HslCode.class
        );
    }
}
